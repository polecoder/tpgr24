@echo off
setlocal enabledelayedexpansion

REM Leer archivo de propiedades
for /f "tokens=1,2 delims==" %%a in (config.properties) do (
    if "%%a"=="server.ip" set IP=%%b
)

echo Usando puerto: %PORT%


echo ============================

REM Ejecutar Maven en Tarea1
echo Compilando modulo Tarea1...
pushd Tarea1
call mvn clean install 
REM -Dserver.port=%PORT%
popd

echo ============================

echo Publicando...
REM Publicar servicios
xcopy config.properties Tarea1\target
pushd Tarea1\target
start "estacion_de_trabajo" java -jar EstacionDeTrabajo-jar-with-dependencies.jar
popd
echo ============================

REM Ejecutar Maven en Tarea2
echo Compilando modulo Tarea2...
pushd Tarea2
call mvn clean install -Dserver.ip=%IP%
popd

echo ============================

echo Artefactos generados:
dir Tarea1\target\*.jar
dir Tarea2\target\*.war

pause