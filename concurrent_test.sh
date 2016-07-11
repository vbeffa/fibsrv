#!/usr/bin/env bash

for i in {1..100}
do
    curl https://fibsrv.herokuapp.com/fib_list/10000 > "/tmp/run-$i.out" &
done
