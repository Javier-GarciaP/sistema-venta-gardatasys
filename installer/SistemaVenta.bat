@echo off
setlocal

set "APP_DIR=%~dp0"
if "%APP_DIR:~-1%"=="\" set "APP_DIR=%APP_DIR:~0,-1%"

set "JAR=%APP_DIR%\SistemaVenta.jar"
set "LIB_DIR=%APP_DIR%\lib"

rem Buscar Java (usamos javaw para que no abra la terminal)
set "JAVA_EXE="
for /f "delims=" %%i in ('where javaw.exe 2^>nul') do set "JAVA_EXE=%%i"
if not defined JAVA_EXE (
    if defined JAVA_HOME set "JAVA_EXE=%JAVA_HOME%\bin\javaw.exe"
)

if not defined JAVA_EXE (
    echo [ERROR] No se encontro Java.
    pause
    exit /b 1
)

rem Ejecutar de forma silenciosa (sin terminal)
start "" "%JAVA_EXE%" -Xms128m -Xmx512m -Dfile.encoding=UTF-8 -Dapp.dir="%APP_DIR%" -cp "%JAR%;%LIB_DIR%\*" Main.Main

endlocal
