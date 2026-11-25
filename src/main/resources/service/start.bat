
@echo off
cd %~dp0

start java -jar -Dspring.profiles.active=$ACTIVE -DONER365-HOST=localhost RESOURCE_NAME

