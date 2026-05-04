# StockAnalyzer Backend - Windows Server Auto Deploy Script
# Run as Administrator in PowerShell

$ErrorActionPreference = "Stop"

# ---------- Config ----------
$REPO_URL     = "https://github.com/szvanish/gupiaoDemo.git"
$DEPLOY_DIR   = "C:\apps\gupiao"
$BACKEND_DIR  = "$DEPLOY_DIR\backend"
$LOG_DIR      = "C:\apps\gupiao\logs"
$TOOLS_DIR    = "C:\tools"
$SERVICE_NAME = "StockAnalyzerBackend"
$APP_PORT     = 8000
$DEEPSEEK_KEY = "sk-3040cb67d6bc4d138fc378c20e8cdf33"
$REDIS_URL    = "redis://localhost:6379"
$NSSM_EXE     = "$TOOLS_DIR\nssm\nssm.exe"
# ----------------------------

function Write-Step { param($msg) Write-Host "`n>>> $msg" -ForegroundColor Cyan }
function Write-OK   { param($msg) Write-Host "    [OK] $msg" -ForegroundColor Green }
function Write-Warn { param($msg) Write-Host "    [!]  $msg" -ForegroundColor Yellow }
function Fail       { param($msg) Write-Host "`n[FAIL] $msg" -ForegroundColor Red; exit 1 }

# ============================================================
# 0. Admin check
# ============================================================
Write-Step "Checking administrator privileges"
if (-not ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Fail "Please run this script as Administrator (right-click PowerShell -> Run as Administrator)"
}
Write-OK "Running as Administrator"

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope LocalMachine -Force

New-Item -ItemType Directory -Path $TOOLS_DIR -Force | Out-Null
New-Item -ItemType Directory -Path $LOG_DIR   -Force | Out-Null

# ============================================================
# 1. Install Python 3.11
# ============================================================
Write-Step "Checking Python installation"
$pythonOk = $false
try {
    $ver = & python --version 2>&1
    if ($ver -match "Python 3\.1[01]") { $pythonOk = $true; Write-OK "Already installed: $ver" }
} catch {}

if (-not $pythonOk) {
    Write-Warn "Python not found. Downloading Python 3.11.9..."
    $pyInstaller = "$TOOLS_DIR\python-3.11.9-amd64.exe"
    Invoke-WebRequest -Uri "https://www.python.org/ftp/python/3.11.9/python-3.11.9-amd64.exe" `
        -OutFile $pyInstaller -UseBasicParsing
    Write-Warn "Installing Python silently..."
    Start-Process -FilePath $pyInstaller `
        -ArgumentList "/quiet InstallAllUsers=1 PrependPath=1 Include_test=0" `
        -Wait
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" +
                [System.Environment]::GetEnvironmentVariable("Path","User")
    $ver = & python --version 2>&1
    Write-OK "Installed: $ver"
}

# ============================================================
# 2. Install Git
# ============================================================
Write-Step "Checking Git installation"
$gitOk = $false
try {
    $ver = & git --version 2>&1
    if ($ver -match "git version") { $gitOk = $true; Write-OK "Already installed: $ver" }
} catch {}

if (-not $gitOk) {
    Write-Warn "Git not found. Downloading..."
    $gitInstaller = "$TOOLS_DIR\Git-2.44.0-64-bit.exe"
    Invoke-WebRequest -Uri "https://github.com/git-for-windows/git/releases/download/v2.44.0.windows.1/Git-2.44.0-64-bit.exe" `
        -OutFile $gitInstaller -UseBasicParsing
    Write-Warn "Installing Git silently..."
    Start-Process -FilePath $gitInstaller `
        -ArgumentList "/VERYSILENT /SUPPRESSMSGBOXES /NORESTART /NOCANCEL" `
        -Wait
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" +
                [System.Environment]::GetEnvironmentVariable("Path","User")
    $ver = & git --version 2>&1
    Write-OK "Installed: $ver"
}

# ============================================================
# 3. Install Redis for Windows
# ============================================================
Write-Step "Checking Redis installation"
$redisDir = "C:\Redis"
$redisExe = "$redisDir\redis-server.exe"

if (-not (Test-Path $redisExe)) {
    Write-Warn "Redis not found. Downloading..."
    $redisZip = "$TOOLS_DIR\Redis-x64-5.0.14.1.zip"
    Invoke-WebRequest -Uri "https://github.com/tporadowski/redis/releases/download/v5.0.14.1/Redis-x64-5.0.14.1.zip" `
        -OutFile $redisZip -UseBasicParsing
    Write-Warn "Extracting Redis..."
    Expand-Archive -Path $redisZip -DestinationPath $redisDir -Force
    Write-OK "Redis extracted to $redisDir"
} else {
    Write-OK "Redis already exists: $redisExe"
}

$redisSvc = Get-Service -Name "Redis" -ErrorAction SilentlyContinue
if ($null -eq $redisSvc) {
    Write-Warn "Registering Redis as Windows service..."
    & $redisExe --service-install --service-name Redis --loglevel notice
    Set-Service -Name "Redis" -StartupType Automatic
    Start-Service -Name "Redis"
    Write-OK "Redis service started"
} else {
    if ($redisSvc.Status -ne "Running") { Start-Service -Name "Redis" }
    Write-OK "Redis service is running"
}

Start-Sleep -Seconds 2
$pong = & "$redisDir\redis-cli.exe" ping 2>&1
if ($pong -eq "PONG") { Write-OK "Redis ping -> PONG" } else { Write-Warn "Redis response: $pong" }

# ============================================================
# 4. Clone / update repository
# ============================================================
Write-Step "Deploying code from GitHub"
New-Item -ItemType Directory -Path "C:\apps" -Force | Out-Null

if (Test-Path "$DEPLOY_DIR\.git") {
    Write-Warn "Repository exists. Running git pull..."
    Set-Location $DEPLOY_DIR
    & git pull origin master 2>&1 | Write-Host
} else {
    if (Test-Path $DEPLOY_DIR) {
        Write-Warn "Directory exists but is not a git repo. Removing and re-cloning..."
        Remove-Item $DEPLOY_DIR -Recurse -Force
    }
    Write-Warn "Cloning repository..."
    & git clone $REPO_URL $DEPLOY_DIR 2>&1 | Write-Host
}

if (-not (Test-Path "$BACKEND_DIR\main.py")) {
    Fail "Clone failed: $BACKEND_DIR\main.py not found. Check the repository URL."
}
Write-OK "Code ready at $BACKEND_DIR"

# ============================================================
# 5. Create virtualenv and install dependencies
# ============================================================
Write-Step "Creating Python virtual environment"
Set-Location $BACKEND_DIR

if (-not (Test-Path "$BACKEND_DIR\venv\Scripts\python.exe")) {
    & python -m venv venv
    Write-OK "Virtual environment created"
} else {
    Write-OK "Virtual environment already exists"
}

$pip    = "$BACKEND_DIR\venv\Scripts\pip.exe"
$python = "$BACKEND_DIR\venv\Scripts\python.exe"

Write-Step "Installing Python dependencies (Tsinghua mirror)"
& $pip install --upgrade pip -q
& $pip install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple --trusted-host pypi.tuna.tsinghua.edu.cn
if ($LASTEXITCODE -ne 0) { Fail "Dependency installation failed. See errors above." }
Write-OK "All dependencies installed"

# ============================================================
# 6. Create .env config file
# ============================================================
Write-Step "Writing .env configuration"
$envContent = "DEEPSEEK_API_KEY=$DEEPSEEK_KEY`nREDIS_URL=$REDIS_URL"
[System.IO.File]::WriteAllText("$BACKEND_DIR\.env", $envContent, [System.Text.Encoding]::UTF8)
Write-OK ".env written"

$cfgTest = & $python -c "from config import settings; print(settings.redis_url)" 2>&1
Write-OK "Config check: redis_url = $cfgTest"

# ============================================================
# 7. Install NSSM and register Windows service
# ============================================================
Write-Step "Installing NSSM"

if (-not (Test-Path $NSSM_EXE)) {
    $nssmZip = "$TOOLS_DIR\nssm-2.24.zip"
    Write-Warn "Downloading NSSM..."
    Invoke-WebRequest -Uri "https://nssm.cc/release/nssm-2.24.zip" `
        -OutFile $nssmZip -UseBasicParsing
    Expand-Archive -Path $nssmZip -DestinationPath "$TOOLS_DIR\nssm_tmp" -Force
    New-Item -ItemType Directory -Path "$TOOLS_DIR\nssm" -Force | Out-Null
    Copy-Item "$TOOLS_DIR\nssm_tmp\nssm-2.24\win64\nssm.exe" $NSSM_EXE -Force
    Remove-Item "$TOOLS_DIR\nssm_tmp" -Recurse -Force
    Write-OK "NSSM installed"
} else {
    Write-OK "NSSM already exists"
}

$existingSvc = Get-Service -Name $SERVICE_NAME -ErrorAction SilentlyContinue
if ($null -ne $existingSvc) {
    Write-Warn "Removing existing service..."
    & $NSSM_EXE stop $SERVICE_NAME confirm 2>&1 | Out-Null
    & $NSSM_EXE remove $SERVICE_NAME confirm 2>&1 | Out-Null
    Start-Sleep -Seconds 2
}

Write-Step "Registering Windows service: $SERVICE_NAME"
$uvicornExe = "$BACKEND_DIR\venv\Scripts\uvicorn.exe"

& $NSSM_EXE install $SERVICE_NAME $uvicornExe
& $NSSM_EXE set $SERVICE_NAME AppParameters "main:app --host 0.0.0.0 --port $APP_PORT"
& $NSSM_EXE set $SERVICE_NAME AppDirectory $BACKEND_DIR
& $NSSM_EXE set $SERVICE_NAME Start SERVICE_AUTO_START
& $NSSM_EXE set $SERVICE_NAME AppStdout "$LOG_DIR\stdout.log"
& $NSSM_EXE set $SERVICE_NAME AppStderr "$LOG_DIR\stderr.log"
& $NSSM_EXE set $SERVICE_NAME AppRotateFiles 1
& $NSSM_EXE set $SERVICE_NAME AppRotateBytes 10485760

Write-Warn "Starting service..."
& $NSSM_EXE start $SERVICE_NAME
Start-Sleep -Seconds 5

$svc = Get-Service -Name $SERVICE_NAME
Write-OK "Service status: $($svc.Status)"

# ============================================================
# 8. Configure Windows Firewall
# ============================================================
Write-Step "Configuring Windows Firewall (port $APP_PORT)"
$existingRule = Get-NetFirewallRule -DisplayName "StockAnalyzer Port $APP_PORT" -ErrorAction SilentlyContinue
if ($null -eq $existingRule) {
    New-NetFirewallRule `
        -DisplayName "StockAnalyzer Port $APP_PORT" `
        -Direction Inbound `
        -Protocol TCP `
        -LocalPort $APP_PORT `
        -Action Allow | Out-Null
    Write-OK "Firewall rule added"
} else {
    Write-OK "Firewall rule already exists"
}

# ============================================================
# 9. Final verification
# ============================================================
Write-Step "Final verification"
Start-Sleep -Seconds 3

try {
    $resp = Invoke-WebRequest -Uri "http://localhost:$APP_PORT/health" -UseBasicParsing -TimeoutSec 10
    if ($resp.StatusCode -eq 200) {
        Write-OK "/health response: $($resp.Content)"
    }
} catch {
    Write-Warn "Health check failed. Service may still be starting."
    Write-Warn "Check logs: Get-Content '$LOG_DIR\stderr.log' -Tail 30"
}

# ============================================================
# 10. Summary
# ============================================================
Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Deployment Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Service   : $SERVICE_NAME"
Write-Host "  Local URL : http://localhost:$APP_PORT"
Write-Host "  Public URL: http://8.136.186.44:$APP_PORT"
Write-Host "  Health    : http://8.136.186.44:$APP_PORT/health"
Write-Host "  Logs      : $LOG_DIR"
Write-Host ""
Write-Host "  Useful commands:" -ForegroundColor Yellow
Write-Host "    View logs   : Get-Content '$LOG_DIR\stderr.log' -Tail 50 -Wait"
Write-Host "    Restart svc : C:\tools\nssm\nssm.exe restart $SERVICE_NAME"
Write-Host "    Update code : cd $DEPLOY_DIR; git pull; C:\tools\nssm\nssm.exe restart $SERVICE_NAME"
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "  IMPORTANT: Add inbound TCP rule for port $APP_PORT in Aliyun Security Group!" -ForegroundColor Yellow
