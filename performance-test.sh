#!/bin/bash

# Example performance test using Apache Bench
ab -n 100 -c 10 http://localhost:8080/ # Adjust the URL and parameters as needed
