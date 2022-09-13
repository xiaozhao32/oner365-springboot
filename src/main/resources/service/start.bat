
@echo off
cd %~dp0

start java -jar -Dspring.profiles.active=$ACTIVE RESOURCE_NAME

