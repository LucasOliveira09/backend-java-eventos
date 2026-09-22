#!/bin/bash

set -e

service mysql start

until mysqladmin ping --silent; do
  sleep 1
done

mysql <<EOF
CREATE DATABASE IF NOT EXISTS eventos;

CREATE USER IF NOT EXISTS 'lucas'@'%' IDENTIFIED BY 'lucas@123';

GRANT ALL PRIVILEGES ON eventos.* TO 'lucas'@'%';

FLUSH PRIVILEGES;
EOF
