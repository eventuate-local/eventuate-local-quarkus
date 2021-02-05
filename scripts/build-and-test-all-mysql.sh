#! /bin/bash

set -e

export database=mysql
export EVENTUATEDATABASE=mysql

./scripts/_build-and-test-all.sh