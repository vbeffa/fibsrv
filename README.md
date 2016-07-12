# Fibonacci Server

## Overview

This project provides a simple REST API to generate Fibonacci numbers
and lists of Fibonacci numbers. The calls are:

`GET /fib/:n`

`GET /fib_list/:n`

The approach taken was to maximize both _n_ and throughput (concurrent
requests). Thus only a subset of the numbers in the sequence are
memoized. The rest are (re)computed as needed. Additionally, requests
to `fib_list` are handled with a stream (see [FibInputStream.scala](https://github.com/vbeffa/fibsrv/blob/master/app/services/FibInputStream.scala)).
This allows for (theoretically) arbitrarily large (up to `n = Int.MaxValue`)
lists to be generated, as only _fib(n - 2)_, _fib(n - 1)_, and _fib(n)_
are stored in memory at any one time while _fib(n)_ is streamed to the client.

Max values and error messages can be configured in [application.conf](https://github.com/vbeffa/fibsrv/blob/master/conf/application.conf).

Note: a slight change to the problem requirements was made: `GET /fib_list/:n`
will return **up to** _fib(n)_ rather than the first _n_ Fibonacci
numbers. This allows the last element in the list returned to equal
_fib(n)_. Thus:

```
fib(0) = 0	fib_list(0) = [0]
fib(1) = 1	fib_list(1) = [0, 1]
fib(2) = 1	fib_list(2) = [0, 1, 1]
fib(3) = 2	fib_list(3) = [0, 1, 1, 2]
fib(4) = 3	fib_list(4) = [0, 1, 1, 2, 3]
fib(5) = 5	fib_list(5) = [0, 1, 1, 2, 3, 5]
...
```


## Reference Implementation

https://fibsrv.herokuapp.com

## Requirements

1. Scala 2.11.7
2. sbt 0.13.11

## Setup

1. Clone https://github.com/vbeffa/fibsrv.
2. If deploying to production:

   `activator playGenerateSecret`

   The `APPLICATION_SECRET` environment variable will need to be set in
   the production environment with the result of that command.

## Running

1. `activator run`
2. Open http://localhost:9000/ to test with browser. The page functions
   as a rudimentary interactive shell.
3. Try `curl http://localhost:9000/fib/100` or `curl http://localhost:9000/fib_list/20`
   to test with curl.

## Testing

1. `activator test` to run unit and integration tests
2. `./concurrent_test.sh` to run concurrent load test (against reference
    implementation)

## Known Issues

1. Reference implementation has minimal memory, thus querying for larger
   _n_ values may not succeed.
2. Inputing `fib n` or `fib_list n` in the browser shell where
   `n > Int.MaxValue` is not handled on the client side but on the server.
    Ideally a check would be made before submitting a request.
