#!/bin/bash

if [ "$1" == "--rest" ] || [ "$1" == "--rest" ];; then
    docker-compose up -d budgee_api budgee_database
elif [ "$1" == "--client" ] || [ "$1" == "--c" ]; then
    docker-compose up -d budgee_client
elif [ "$1" == "--build" ] || [ "$1" == "--b" ]; then
    docker-compose up -d --build
else
    docker-compose up -d
fi