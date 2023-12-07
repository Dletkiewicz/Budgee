# CHANGELOG

## [0.0.1] 14-split-docker-images-in-docker-compose
### Added
- Added env file
- Added docker-compose file
- Added mysql:8 docker image
- Added api image to docker-compose

## [0.0.2] 13-create-nodejs-next-docker-image
### Added
- Added dockerfile for client package
- Added docker image for client in docker-compose
- Added networks in docker-compose
### Changed
- Change networks for all containers

## [0.0.3] 1-create-user-entity
### Added
- Added user entity
- Added user repository
- Added pom dependencies

## [0.0.4] 2-create-budget-entity
### Added
- Added budget entity
- Added budget repository

## [0.0.5] 3-create-income-entity
### Added
- Added income entity
- Added enum class for income/expense type
### Changed
- Changed audit entity

## [0.0.6] 4-create-expense-entity
### Added
- Added expense entity
- Added create expense/income usecases
### Changed
- Changed expense domain class |  String category -> enum ExpenseType 

## [0.0.7] 4-create-expense-entity
### Added
- Added expense entity
- Added create expense/income usecases
### Changed
- Changed expense domain class |  String category -> enum ExpenseType 

## [0.0.8] 28-create-bash-script-to-enable-front-or-back-or-both
### Added
- Added script to manage docker containers

## [0.0.9] 3-create-liquibase-scripts-for-database
### Added
- Added liquibase scripts
### Changed
- Changed DBMS to PostgreSQL

## [0.0.10] 6-create-user-crud
### Added
- Added user controller methods
- Added user exceptions
### Removed
- Removed GlobalExceptionHandler

## [0.0.11] 7-create-incomes-crud
### Added
- Added income creating usecase/controller methods/repository methods
- Added deleting income

## [0.0.12] 8-create-expenses-crud
### Added
- Added creating/deleting expense usecases
- Added creating/deleting controller methods/web models