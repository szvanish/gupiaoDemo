# Backend 阿里云 Windows 部署计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 FastAPI 后端部署到阿里云 Windows Server，并以 Windows 服务形式自动启动。

**Architecture:** 从 GitHub 拉取代码 → 安装 Python/Redis 依赖 → 配置 .env 环境变量 → 使用 NSSM 注册为 Windows 服务，开机自动运行。

**Tech Stack:** Python 3.11, FastAPI, Uvicorn, Redis (Memurai for Windows), NSSM (Windows 服务管理), Git for Windows

---

> **所有命令在阿里云 Windows Server 的 PowerShell（管理员模式）中执行。**

---

## 准备工作：本地检查 GitHub 仓库地址

在你自己的电脑上执行，确认 GitHub remote URL：

```powershell
git remote -v
```

记下 `origin` 的 URL（例如 `https://github.com/szvanish/gupiao.git`），后续步骤会用到。

---

## Task 1: 安装基础软件（Git、Python）

**Files:** 无代码文件，仅系统软件安装

- [ ] **Step 1: 下载并安装 Git for Windows**

在阿里云服务器上打开浏览器，访问 `https://git-scm.com/download/win`，下载 64-bit 版本安装包，全部默认选项安装。

安装完成后验证：
```powershell
git --version
```
预期输出：`git version 2.x.x.windows.x`

- [ ] **Step 2: 下载并安装 Python 3.11**

访问 `https://www.python.org/downloads/release/python-3119/`，下载 `Windows installer (64-bit)`。

安装时 **必须勾选**：
- `Add Python to PATH`
- `Install for all users`（可选但推荐）

安装完成后验证：
```powershell
python --version
pip --version
```
预期输出：`Python 3.11.x` 和 `pip 24.x`

- [ ] **Step 3: 提交（本步骤无代码提交，继续 Task 2）**

---

## Task 2: 安装 Redis（使用 Memurai Windows 版）

**Files:** 无代码文件

> Memurai 是 Redis 官方授权的 Windows 原生实现，与 Redis 协议完全兼容。

- [ ] **Step 1: 下载 Memurai**

访问 `https://www.memurai.com/get-memurai`，填写邮箱后下载 Windows 安装包（`.msi`）。

- [ ] **Step 2: 安装 Memurai**

运行安装包，全部默认选项。安装后 Memurai 会自动注册为 Windows 服务并启动。

验证 Redis 已运行：
```powershell
# 安装目录通常在 C:\Program Files\Memurai\
& "C:\Program Files\Memurai\memurai-cli.exe" ping
```
预期输出：`PONG`

- [ ] **Step 3: 确认 Memurai 服务开机自启**

```powershell
Get-Service -Name "Memurai"
```
预期输出：`Status: Running`，`StartType: Automatic`

如果 StartType 不是 Automatic，执行：
```powershell
Set-Service -Name "Memurai" -StartupType Automatic
```

---

## Task 3: 克隆代码仓库

**Files:** `C:\apps\gupiao\` 为部署目录

- [ ] **Step 1: 创建应用目录**

```powershell
New-Item -ItemType Directory -Path "C:\apps" -Force
Set-Location "C:\apps"
```

- [ ] **Step 2: 从 GitHub 克隆代码**

将 `<YOUR_GITHUB_URL>` 替换为你在准备工作中记下的 URL：
```powershell
git clone https://github.com/szvanish/gupiao.git
Set-Location "C:\apps\gupiao"
```
预期输出：`Cloning into 'gupiao'...` 然后 `done.`

- [ ] **Step 3: 验证 backend 目录存在**

```powershell
Get-ChildItem "C:\apps\gupiao\backend"
```
预期输出：应看到 `main.py`, `requirements.txt`, `config.py` 等文件。

---

## Task 4: 创建虚拟环境并安装依赖

**Files:** `C:\apps\gupiao\backend\venv\`（本地生成，不提交 git）

- [ ] **Step 1: 进入 backend 目录，创建虚拟环境**

```powershell
Set-Location "C:\apps\gupiao\backend"
python -m venv venv
```

- [ ] **Step 2: 激活虚拟环境**

```powershell
.\venv\Scripts\Activate.ps1
```
预期：命令行前缀变为 `(venv)`

如果报错 `execution policy`，先执行：
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```
再重新激活。

- [ ] **Step 3: 升级 pip 并安装依赖**

```powershell
python -m pip install --upgrade pip
pip install -r requirements.txt
```
预期：所有包安装成功，最后显示 `Successfully installed ...`

> 如果 `akshare` 或 `ta` 安装失败，先安装构建工具：
> ```powershell
> pip install wheel setuptools --upgrade
> ```
> 再重试 `pip install -r requirements.txt`

- [ ] **Step 4: 验证关键包已安装**

```powershell
python -c "import fastapi, uvicorn, redis, akshare; print('OK')"
```
预期输出：`OK`

---

## Task 5: 配置环境变量（.env 文件）

**Files:** `C:\apps\gupiao\backend\.env`（敏感文件，不提交 git）

- [ ] **Step 1: 创建 .env 文件**

```powershell
Set-Location "C:\apps\gupiao\backend"
@"
DEEPSEEK_API_KEY=your_deepseek_api_key_here
REDIS_URL=redis://localhost:6379
"@ | Out-File -FilePath ".env" -Encoding utf8
```

将 `your_deepseek_api_key_here` 替换为你的真实 DeepSeek API Key。

- [ ] **Step 2: 验证 .env 内容**

```powershell
Get-Content ".env"
```
预期输出：
```
DEEPSEEK_API_KEY=sk-xxxxxxxxxxxx
REDIS_URL=redis://localhost:6379
```

- [ ] **Step 3: 测试配置加载是否正常**

```powershell
.\venv\Scripts\python.exe -c "from config import settings; print(settings.redis_url)"
```
预期输出：`redis://localhost:6379`

---

## Task 6: 手动测试运行后端

**Files:** 无

- [ ] **Step 1: 手动启动服务器测试**

```powershell
Set-Location "C:\apps\gupiao\backend"
.\venv\Scripts\uvicorn.exe main:app --host 0.0.0.0 --port 8000
```
预期输出：
```
INFO:     Started server process [XXXX]
INFO:     Waiting for application startup.
INFO:     Application startup complete.
INFO:     Uvicorn running on http://0.0.0.0:8000
```

- [ ] **Step 2: 验证健康检查接口**

打开另一个 PowerShell 窗口：
```powershell
Invoke-WebRequest -Uri "http://localhost:8000/health" -UseBasicParsing
```
预期输出：`StatusCode: 200`，内容为 `{"status":"ok"}`

- [ ] **Step 3: 停止测试服务器**

回到运行服务器的窗口，按 `Ctrl+C` 停止。

---

## Task 7: 使用 NSSM 注册为 Windows 服务

**Files:** 无代码文件，服务配置

> NSSM（Non-Sucking Service Manager）将任意可执行程序注册为 Windows 服务，支持自动重启。

- [ ] **Step 1: 下载 NSSM**

访问 `https://nssm.cc/download`，下载最新版本（zip 包）。

解压到 `C:\tools\nssm\`：
```powershell
New-Item -ItemType Directory -Path "C:\tools\nssm" -Force
# 将下载的 nssm-2.24.zip 解压后，把 win64\nssm.exe 复制到 C:\tools\nssm\
```

- [ ] **Step 2: 注册 FastAPI 为 Windows 服务**

以管理员身份运行 PowerShell，执行：
```powershell
$nssm = "C:\tools\nssm\nssm.exe"
$serviceName = "StockAnalyzerBackend"
$uvicornPath = "C:\apps\gupiao\backend\venv\Scripts\uvicorn.exe"
$appDir = "C:\apps\gupiao\backend"

& $nssm install $serviceName $uvicornPath
& $nssm set $serviceName AppParameters "main:app --host 0.0.0.0 --port 8000"
& $nssm set $serviceName AppDirectory $appDir
& $nssm set $serviceName Start SERVICE_AUTO_START
& $nssm set $serviceName AppStdout "C:\apps\gupiao\logs\stdout.log"
& $nssm set $serviceName AppStderr "C:\apps\gupiao\logs\stderr.log"
& $nssm set $serviceName AppRotateFiles 1
& $nssm set $serviceName AppRotateBytes 10485760
```

- [ ] **Step 3: 创建日志目录**

```powershell
New-Item -ItemType Directory -Path "C:\apps\gupiao\logs" -Force
```

- [ ] **Step 4: 启动服务**

```powershell
& "C:\tools\nssm\nssm.exe" start StockAnalyzerBackend
```
预期输出：`StockAnalyzerBackend: START: The operation completed successfully.`

- [ ] **Step 5: 验证服务状态**

```powershell
Get-Service -Name "StockAnalyzerBackend"
```
预期输出：`Status: Running`

再次验证接口：
```powershell
Invoke-WebRequest -Uri "http://localhost:8000/health" -UseBasicParsing
```
预期输出：`StatusCode: 200`

---

## Task 8: 配置 Windows 防火墙（开放 8000 端口）

**Files:** 无

- [ ] **Step 1: 添加防火墙入站规则**

```powershell
New-NetFirewallRule `
    -DisplayName "StockAnalyzer Backend Port 8000" `
    -Direction Inbound `
    -Protocol TCP `
    -LocalPort 8000 `
    -Action Allow
```
预期输出：规则创建成功。

- [ ] **Step 2: 在阿里云控制台配置安全组**

登录阿里云控制台 → ECS → 实例 → 安全组 → 配置规则：

| 方向 | 协议 | 端口范围 | 授权对象 |
|------|------|----------|----------|
| 入方向 | TCP | 8000/8000 | 0.0.0.0/0（或限制特定 IP 更安全） |

- [ ] **Step 3: 从外部验证访问**

在你自己的电脑上，将 `<SERVER_IP>` 替换为阿里云服务器的公网 IP：
```powershell
Invoke-WebRequest -Uri "http://<SERVER_IP>:8000/health" -UseBasicParsing
```
预期输出：`StatusCode: 200`，内容 `{"status":"ok"}`

---

## Task 9: 更新 Android App 的后端地址

**Files:** `android/StockAnalyzer/app/src/main/java/com/stockanalyzer/di/NetworkModule.kt`（本地开发环境修改）

- [ ] **Step 1: 找到 NetworkModule 中的 BASE_URL 常量**

```powershell
Select-String -Path "android\StockAnalyzer\app\src\main\java\com\stockanalyzer\di\NetworkModule.kt" -Pattern "BASE_URL|baseUrl|http"
```

- [ ] **Step 2: 将 BASE_URL 改为阿里云公网 IP**

找到类似这行代码：
```kotlin
private const val BASE_URL = "http://192.168.x.x:8000/"
```

改为：
```kotlin
private const val BASE_URL = "http://<SERVER_IP>:8000/"
```

将 `<SERVER_IP>` 替换为阿里云服务器实际的公网 IP。

- [ ] **Step 3: 重新构建 Android APK**

```powershell
Set-Location "android\StockAnalyzer"
.\gradlew assembleDebug
```

---

## Task 10: 验证完整部署 & 日常维护命令

**Files:** 无

- [ ] **Step 1: 重启服务命令（更新代码后用）**

```powershell
# 拉取最新代码
Set-Location "C:\apps\gupiao"
git pull origin master

# 重启服务
& "C:\tools\nssm\nssm.exe" restart StockAnalyzerBackend
```

- [ ] **Step 2: 查看实时日志**

```powershell
Get-Content "C:\apps\gupiao\logs\stderr.log" -Tail 50 -Wait
```

- [ ] **Step 3: 停止/启动服务命令**

```powershell
# 停止
& "C:\tools\nssm\nssm.exe" stop StockAnalyzerBackend

# 启动
& "C:\tools\nssm\nssm.exe" start StockAnalyzerBackend

# 查看状态
Get-Service -Name "StockAnalyzerBackend"
```

- [ ] **Step 4: 最终端到端验证**

```powershell
# 健康检查
Invoke-WebRequest -Uri "http://<SERVER_IP>:8000/health" -UseBasicParsing

# 搜索接口
Invoke-WebRequest -Uri "http://<SERVER_IP>:8000/stock/search?q=平安&market=A" -UseBasicParsing
```
两个接口均返回 `StatusCode: 200` 即部署完成。

---

## 故障排查速查

| 现象 | 排查步骤 |
|------|----------|
| 服务无法启动 | 查看 `C:\apps\gupiao\logs\stderr.log`，常见原因：.env 缺失、端口被占用 |
| Redis 连接失败 | 执行 `Get-Service Memurai`，确认服务在运行；执行 `memurai-cli ping` |
| 依赖安装失败 | 检查网络，或换源：`pip install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple` |
| 外部无法访问 | 检查阿里云安全组规则是否已添加 8000 端口；检查 Windows 防火墙规则 |
| akshare 报错 | 确认服务器能访问国内网络（akshare 调用 A 股数据源） |
