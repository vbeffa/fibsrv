# Fibonacci Server

## Reference Implementation

https://fibsrv.herokuapp.com

## Requirements

1. Scala 2.11.7
2. sbt 0.13.11

## Setup

1. Clone https://github.com/vbeffa/fibsrv.
2. If deploying to production: `activator playGenerateSecret`. The
   `APPLICATION_SECRET` environment variable will need to be set in the
   production environment with the result of that command.

## Running

1. `activator run`
2. Open http://localhost:9000/ to test with browser.
3. Try `curl http://localhost:9000/fib/100` or `curl http://localhost:9000/fib_list/20`
   to test with curl.

## Testing

1. `activator test` to run unit and integration tests
2. `./concurrent_test.sh` to run concurrent load test (against reference
    implementation)

## Info

This project provides a simple REST API to generate Fibonacci numbers
and lists of Fibonacci numbers. The calls are:

`GET /fib/:n`

`GET /fib_list/:n`

The approach taken was to maximize both `n` and throughput (concurrent
requests). Thus only a subset of the numbers in the sequence are
memoized. The rest are (re)computed as needed. Additionally, requests
to `fib_list` are handled with a stream (see [FibInputStream.scala](https://github.com/vbeffa/fibsrv/blob/master/app/services/FibInputStream.scala)).
This allows for (theoretically) arbitrarily large (up to `n = Int.MaxValue`)
lists to be generated, as only `fib(n - 2)`, `fib(n - 1)`, and `fib(n)`
are stored in memory at a time while `fib(n)` is streamed to the client.
