#! /bin/bash

set -e

export database=postgres
export EVENTUATEDATABASE=postgresql
export QUARKUS_PROFILE=postgresql
export QUARKUS_TEST_PROFILE=postgresql

./scripts/_build-and-test-all.sh