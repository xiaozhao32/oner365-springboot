
@echo off
cd %~dp0

start java -jar -Dspring.profiles.active=dev -DONER365_HOST= RESOURCE_NAME

