#!/bin/bash
set -e

if ! psql -U "$POSTGRES_USER" -tAc "SELECT 1 FROM pg_database WHERE datname='keycloak'" | grep -q 1; then
    echo "Creating database 'keycloak'"
    psql -U "$POSTGRES_USER" -c "CREATE DATABASE keycloak;"
else
    echo "Database 'keycloak' already exists"
fi