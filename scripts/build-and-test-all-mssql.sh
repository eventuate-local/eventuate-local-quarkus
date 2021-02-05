#! /bin/bash

set -e

export database=mssql
export EVENTUATEDATABASE=mssql
export QUARKUS_PROFILE=mssql
export QUARKUS_TEST_PROFILE=mssql

./scripts/_build-and-test-all.sh