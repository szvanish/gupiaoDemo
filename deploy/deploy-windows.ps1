# ============================================================
#  StockAnalyzer Backend - Windows Server 自动部署脚本
#  适用: 阿里云 Windows Server 空环境
#  运行方式: 以管理员身份在 PowerShell 中执行
# ============================================================

$ErrorActionPreference = "Stop"

# ---------- 配置区 ----------
$REPO_URL      = "https://github.com/szvanish/gupiaoDemo.git"
$DEPLOY_DIR    = "C:\apps\gupiao"
$BACKEND_DIR   = "$DEPLOY_DIR\backend"
$LOG_DIR       = "C:\apps\gupiao\logs"
$TOOLS_DIR     = "C:\tools"
$SERVICE_NAME  = "StockAnalyzerBackend"
$APP_PORT      = 8000
$DEEPSEEK_KEY  = "sk-3040cb67d6bc4d138fc378c20e8cdf33"
$REDIS_URL     = "redis://localhost:6379"
# ----------------------------

function Write-Step { param($msg) Write-Host "`n>>> $msg" -ForegroundColor Cyan }
function Write-OK   { param($msg) Write-Host "    [OK] $msg" -ForegroundColor Green }
function Write-Warn { param($msg) Write-Host "    [!]  $msg" -ForegroundColor Yellow }
function Fail       { param($msg) Write-Host "`n[FAIL] $msg" -ForegroundColor Red; exit 1 }

# ============================================================
# 0. 管理员权限检查
# ============================================================
Write-Step "检查管理员权限"
if (-not ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Fail "请以管理员身份运行此脚本 (右键 PowerShell → 以管理员身份运行)"
}
Write-OK "已获取管理员权限"

# 设置 TLS 1.2，确保 HTTPS 下载正常
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

# 允许脚本执行
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope LocalMachine -Force

# 创建工具目录
New-Item -ItemType Directory -Path $TOOLS_DIR -Force | Out-Null
New-Item -ItemType Directory -Path $LOG_DIR   -Force | Out-Null

# ============================================================
# 1. 安装 Python 3.11
# ============================================================
Write-Step "检查 Python 安装"
$pythonOk = $false
try {
    $ver = & python --version 2>&1
    if ($ver -match "Python 3\.1[01]") { $pythonOk = $true; Write-OK "已安装: $ver" }
} catch {}

if (-not $pythonOk) {
    Write-Warn "Python 未安装，开始下载 Python 3.11.9..."
    $pyInstaller = "$TOOLS_DIR\python-3.11.9-amd64.exe"
    Invoke-WebRequest -Uri "https://www.python.org/ftp/python/3.11.9/python-3.11.9-amd64.exe" `
        -OutFile $pyInstaller -UseBasicParsing
    Write-Warn "正在静默安装 Python..."
    Start-Process -FilePath $pyInstaller `
        -ArgumentList "/quiet InstallAllUsers=1 PrependPath=1 Include_test=0" `
        -Wait
    # 刷新 PATH
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + `
                [System.Environment]::GetEnvironmentVariable("Path","User")
    $ver = & python --version 2>&1
    Write-OK "安装完成: $ver"
}

# ============================================================
# 2. 安装 Git
# ============================================================
Write-Step "检查 Git 安装"
$gitOk = $false
try {
    $ver = & git --version 2>&1
    if ($ver -match "git version") { $gitOk = $true; Write-OK "已安装: $ver" }
} catch {}

if (-not $gitOk) {
    Write-Warn "Git 未安装，开始下载 Git..."
    $gitInstaller = "$TOOLS_DIR\Git-2.44.0-64-bit.exe"
    Invoke-WebRequest -Uri "https://github.com/git-for-windows/git/releases/download/v2.44.0.windows.1/Git-2.44.0-64-bit.exe" `
        -OutFile $gitInstaller -UseBasicParsing
    Write-Warn "正在静默安装 Git..."
    Start-Process -FilePath $gitInstaller `
        -ArgumentList "/VERYSILENT /SUPPRESSMSGBOXES /NORESTART /NOCANCEL /COMPONENTS=icons,ext\reg\shellhere,assoc,assoc_sh" `
        -Wait
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + `
                [System.Environment]::GetEnvironmentVariable("Path","User")
    $ver = & git --version 2>&1
    Write-OK "安装完成: $ver"
}

# ============================================================
# 3. 安装 Redis for Windows (tporadowski 5.0.14.1)
# ============================================================
Write-Step "检查 Redis 安装"
$redisDir = "C:\Redis"
$redisExe = "$redisDir\redis-server.exe"

if (-not (Test-Path $redisExe)) {
    Write-Warn "Redis 未安装，开始下载..."
    $redisZip = "$TOOLS_DIR\Redis-x64-5.0.14.1.zip"
    Invoke-WebRequest -Uri "https://github.com/tporadowski/redis/releases/download/v5.0.14.1/Redis-x64-5.0.14.1.zip" `
        -OutFile $redisZip -UseBasicParsing
    Write-Warn "解压 Redis..."
    Expand-Archive -Path $redisZip -DestinationPath $redisDir -Force
    Write-OK "Redis 解压到 $redisDir"
} else {
    Write-OK "Redis 已存在: $redisExe"
}

# 注册 Redis 为 Windows 服务
$redisSvc = Get-Service -Name "Redis" -ErrorAction SilentlyContinue
if ($null -eq $redisSvc) {
    Write-Warn "注册 Redis 为 Windows 服务..."
    & $redisExe --service-install --service-name Redis --loglevel notice
    Set-Service -Name "Redis" -StartupType Automatic
    Start-Service -Name "Redis"
    Write-OK "Redis 服务已启动"
} else {
    if ($redisSvc.Status -ne "Running") { Start-Service -Name "Redis" }
    Write-OK "Redis 服务已运行"
}

# 验证 Redis
Start-Sleep -Seconds 2
$pong = & "$redisDir\redis-cli.exe" ping 2>&1
if ($pong -eq "PONG") { Write-OK "Redis ping → PONG" } else { Write-Warn "Redis 响应: $pong" }

# ============================================================
# 4. 克隆 / 更新代码仓库
# ============================================================
Write-Step "部署代码"
New-Item -ItemType Directory -Path "C:\apps" -Force | Out-Null

if (Test-Path "$DEPLOY_DIR\.git") {
    Write-Warn "仓库已存在，执行 git pull..."
    Set-Location $DEPLOY_DIR
    & git pull origin master 2>&1 | Write-Host
} else {
    Write-Warn "克隆仓库..."
    & git clone $REPO_URL $DEPLOY_DIR 2>&1 | Write-Host
}

if (-not (Test-Path "$BACKEND_DIR\main.py")) {
    Fail "克隆失败：$BACKEND_DIR\main.py 不存在，请检查仓库地址"
}
Write-OK "代码已就绪: $BACKEND_DIR"

# ============================================================
# 5. 创建虚拟环境并安装依赖
# ============================================================
Write-Step "创建 Python 虚拟环境"
Set-Location $BACKEND_DIR

if (-not (Test-Path "$BACKEND_DIR\venv\Scripts\python.exe")) {
    & python -m venv venv
    Write-OK "虚拟环境已创建"
} else {
    Write-OK "虚拟环境已存在，跳过创建"
}

$pip    = "$BACKEND_DIR\venv\Scripts\pip.exe"
$python = "$BACKEND_DIR\venv\Scripts\python.exe"

Write-Step "安装 Python 依赖（使用清华镜像加速）"
& $pip install --upgrade pip -q
& $pip install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple --trusted-host pypi.tuna.tsinghua.edu.cn
if ($LASTEXITCODE -ne 0) { Fail "依赖安装失败，请查看上方错误信息" }
Write-OK "所有依赖安装成功"

# ============================================================
# 6. 创建 .env 配置文件
# ============================================================
Write-Step "生成 .env 配置文件"
$envContent = @"
DEEPSEEK_API_KEY=$DEEPSEEK_KEY
REDIS_URL=$REDIS_URL
"@
$envContent | Out-File -FilePath "$BACKEND_DIR\.env" -Encoding utf8 -Force
Write-OK ".env 已写入"

# 验证配置加载
$cfgTest = & $python -c "from config import settings; print(settings.redis_url)" 2>&1
Write-OK "配置验证: redis_url = $cfgTest"

# ============================================================
# 7. 安装 NSSM 并注册为 Windows 服务
# ============================================================
Write-Step "安装 NSSM"
$nssmExe = "$TOOLS_DIR\nssm\nssm.exe"

if (-not (Test-Path $nssmExe)) {
    $nssmZip = "$TOOLS_DIR\nssm-2.24.zip"
    Write-Warn "下载 NSSM..."
    Invoke-WebRequest -Uri "https://nssm.cc/release/nssm-2.24.zip" `
        -OutFile $nssmZip -UseBasicParsing
    Expand-Archive -Path $nssmZip -DestinationPath "$TOOLS_DIR\nssm_tmp" -Force
    New-Item -ItemType Directory -Path "$TOOLS_DIR\nssm" -Force | Out-Null
    Copy-Item "$TOOLS_DIR\nssm_tmp\nssm-2.24\win64\nssm.exe" "$TOOLS_DIR\nssm\nssm.exe" -Force
    Remove-Item "$TOOLS_DIR\nssm_tmp" -Recurse -Force
    Write-OK "NSSM 已安装"
} else {
    Write-OK "NSSM 已存在"
}

# 如果服务已存在，先停止并删除
$existingSvc = Get-Service -Name $SERVICE_NAME -ErrorAction SilentlyContinue
if ($null -ne $existingSvc) {
    Write-Warn "服务已存在，先删除旧服务..."
    & $nssmExe stop $SERVICE_NAME confirm 2>&1 | Out-Null
    & $nssmExe remove $SERVICE_NAME confirm 2>&1 | Out-Null
    Start-Sleep -Seconds 2
}

Write-Step "注册 Windows 服务: $SERVICE_NAME"
$uvicornExe = "$BACKEND_DIR\venv\Scripts\uvicorn.exe"

& $nssmExe install $SERVICE_NAME $uvicornExe
& $nssmExe set $SERVICE_NAME AppParameters "main:app --host 0.0.0.0 --port $APP_PORT"
& $nssmExe set $SERVICE_NAME AppDirectory $BACKEND_DIR
& $nssmExe set $SERVICE_NAME Start SERVICE_AUTO_START
& $nssmExe set $SERVICE_NAME AppStdout "$LOG_DIR\stdout.log"
& $nssmExe set $SERVICE_NAME AppStderr "$LOG_DIR\stderr.log"
& $nssmExe set $SERVICE_NAME AppRotateFiles 1
& $nssmExe set $SERVICE_NAME AppRotateBytes 10485760
& $nssmExe set $SERVICE_NAME AppEnvironmentExtra "PYTHONPATH=$BACKEND_DIR"

Write-Warn "启动服务..."
& $nssmExe start $SERVICE_NAME
Start-Sleep -Seconds 5

$svc = Get-Service -Name $SERVICE_NAME
Write-OK "服务状态: $($svc.Status)"

# ============================================================
# 8. 配置 Windows 防火墙
# ============================================================
Write-Step "配置 Windows 防火墙（开放端口 $APP_PORT）"
$existingRule = Get-NetFirewallRule -DisplayName "StockAnalyzer Port $APP_PORT" -ErrorAction SilentlyContinue
if ($null -eq $existingRule) {
    New-NetFirewallRule `
        -DisplayName "StockAnalyzer Port $APP_PORT" `
        -Direction Inbound `
        -Protocol TCP `
        -LocalPort $APP_PORT `
        -Action Allow | Out-Null
    Write-OK "防火墙规则已添加"
} else {
    Write-OK "防火墙规则已存在，跳过"
}

# ============================================================
# 9. 最终验证
# ============================================================
Write-Step "验证部署结果"
Start-Sleep -Seconds 3

try {
    $resp = Invoke-WebRequest -Uri "http://localhost:$APP_PORT/health" -UseBasicParsing -TimeoutSec 10
    if ($resp.StatusCode -eq 200) {
        Write-OK "/health 接口响应: $($resp.Content)"
    } else {
        Write-Warn "/health 返回状态码: $($resp.StatusCode)"
    }
} catch {
    Write-Warn "健康检查失败，服务可能还在启动中，请等待 10 秒后手动验证"
    Write-Warn "验证命令: Invoke-WebRequest -Uri 'http://localhost:$APP_PORT/health'"
    Write-Warn "查看日志: Get-Content '$LOG_DIR\stderr.log' -Tail 30"
}

# ============================================================
# 10. 部署摘要
# ============================================================
Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  部署完成!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host "  服务名称 : $SERVICE_NAME"
Write-Host "  本地地址 : http://localhost:$APP_PORT"
Write-Host "  公网地址 : http://8.136.186.44:$APP_PORT"
Write-Host "  健康检查 : http://8.136.186.44:$APP_PORT/health"
Write-Host "  日志目录 : $LOG_DIR"
Write-Host ""
Write-Host "  常用命令:" -ForegroundColor Yellow
Write-Host "    查看日志  : Get-Content '$LOG_DIR\stderr.log' -Tail 50 -Wait"
Write-Host "    重启服务  : & '$nssmExe' restart $SERVICE_NAME"
Write-Host "    更新代码  : cd $DEPLOY_DIR; git pull; & '$nssmExe' restart $SERVICE_NAME"
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "  [重要] 请登录阿里云控制台确认安全组已开放 TCP $APP_PORT 端口!" -ForegroundColor Yellow
