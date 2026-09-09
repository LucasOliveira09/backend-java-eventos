#!/bin/bash

set -e

service mysql start

until mysqladmin ping --silent; do
  sleep 1
done

mysql <<EOF
CREATE DATABASE IF NOT EXISTS eventos;

CREATE USER IF NOT EXISTS 'aluno'@'%' IDENTIFIED BY '123@Mudar';

GRANT ALL PRIVILEGES ON eventos.* TO 'aluno'@'%';

FLUSH PRIVILEGES;
EOF
