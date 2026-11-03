@echo off
setlocal
set "GRADLE_VERSION=8.13"
set "GRADLE_HOME_LOCAL=%USERPROFILE%\.gradle\manual-dists\gradle-%GRADLE_VERSION%"
set "GRADLE_EXE=%GRADLE_HOME_LOCAL%\bin\gradle.bat"
if not exist "%GRADLE_EXE%" (
  echo Gradle %GRADLE_VERSION% is not installed. Downloading it now...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $base=Join-Path $env:USERPROFILE '.gradle\manual-dists'; New-Item -ItemType Directory -Force -Path $base | Out-Null; $zip=Join-Path $base 'gradle-%GRADLE_VERSION%-bin.zip'; Invoke-WebRequest 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile $zip; Expand-Archive -LiteralPath $zip -DestinationPath $base -Force; Remove-Item $zip -Force"
  if errorlevel 1 exit /b 1
)
call "%GRADLE_EXE%" %*
endlocal
