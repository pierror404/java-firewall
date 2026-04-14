@echo off
setlocal enabledelayedexpansion

echo Compilazione DLL...

set VSWHERE="%ProgramFiles(x86)%\Microsoft Visual Studio\Installer\vswhere.exe"

for /f "usebackq delims=" %%i in (`%VSWHERE% -latest -property installationPath`) do (
    set VS_PATH=%%i
)

if not defined VS_PATH (
    echo [ERRORE] Visual Studio non trovato!
    exit /b 1
)

call "%VS_PATH%\VC\Auxiliary\Build\vcvars64.bat"

if /i not "%VSCMD_ARG_TGT_ARCH%"=="x64" (
    echo [ERRORE] Ambiente non x64!
    exit /b 1
)

:: Percorsi SDK x64 espliciti (sovrascrivono il default x86)
set SDK_UM=%WindowsSdkDir%lib\%WindowsSDKVersion%um\x64
set SDK_UCRT=%WindowsSdkDir%lib\%WindowsSDKVersion%ucrt\x64

echo SDK_UM: %SDK_UM%
echo SDK_UCRT: %SDK_UCRT%

cl /LD /MD firewall.c ^
   /I "%JAVA_HOME%\include" ^
   /I "%JAVA_HOME%\include\win32" ^
   /link ^
   /LIBPATH:. ^
   /LIBPATH:"%SDK_UM%" ^
   /LIBPATH:"%SDK_UCRT%" ^
   /NODEFAULTLIB:kernel32.lib ^
   WinDivert.lib ^
   "%SDK_UM%\kernel32.lib" ^
   /OUT:firewall.dll

if %errorlevel% neq 0 exit /b %errorlevel%

echo Copia DLL in target...
copy firewall.dll ..\target\

echo Done.