# Budgee
Application developed to track and monitor budget

## Authors

- [@Dletkiewicz](https://www.github.com/dletkiewicz) - Backend
- [@Zevcore](https://www.github.com/Zevcore) - Frontend

## Run application using Docker

The application uses the docker tool to virtualize individual elements in containers.
### Run
```docker-compose up --build``` (first run)

```docker-compose up``` (add the -d flag to open the process in memory)

## Docker composer bash script

Bash script for managing Docker containers for the Budgee project. The script allows you to run all containers defined in the Docker Compose file or only the "budgee_api" and "budgee_database" containers using the `--api` flag.

### Usage

1. Build all containers:
   ```./run.sh --build``` / ```./run.sh --b```

2. Run client container:
    ```./run.sh --client``` / ```./run.sh --c```

3. Run rest api container:
    ```./run.sh --rest``` / ```./run.sh --r```

4. Run all containers:
    ```./run.sh```
