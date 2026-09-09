#!/bin/bash

set -euo pipefail

LOG_FILE=/tmp/eventos-spring-boot.log
PID_FILE=/tmp/eventos-spring-boot.pid

service mysql start

until mysqladmin ping --silent; do
  sleep 1
done

if [ -f "$PID_FILE" ] && kill -0 "$(cat "$PID_FILE")" 2>/dev/null; then
  exit 0
fi

rm -f "$PID_FILE"

(
  cd eventos
  nohup bash ./mvnw spring-boot:run >"$LOG_FILE" 2>&1 &
  echo $! >"$PID_FILE"
)

echo "Aplicacao iniciada. Acompanhe com: tail -f $LOG_FILE"
