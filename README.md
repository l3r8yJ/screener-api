# Screener REST API. 
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)


[![Maintainability](https://api.codeclimate.com/v1/badges/1bdaab2672525370693d/maintainability)](https://codeclimate.com/github/l3r8yJ/screener-api/maintainability)
[![codecov](https://codecov.io/gh/l3r8yJ/screener-api/branch/master/graph/badge.svg?token=16IQ8G5KOD)](https://codecov.io/gh/l3r8yJ/screener-api)
[![Hits-of-Code](https://hitsofcode.com/github/l3r8yJ/screener-api?branch=master)](https://hitsofcode.com/github/l3r8yJ/screener-api/view?branch=master)
![issues](https://img.shields.io/github/issues/l3r8yJ/screener-api)
![license](https://img.shields.io/github/license/l3r8yJ/screener-api)


## User guide.
> ### 1. Registration
>> URI `http://api/user/registration`
>> 
>> Request type: ***POST***
>> 
>> Request must contain **email** and **password**
>>
>> Response type: ***JSON***
>>
>> HTTP statuses:
>>  - **CREATED**
>>  - **CONFLICT**
>>  - **BAD_REQUEST**
>>
>> Response contains UserEntity
>>
>> ```json
>> {
>>  "email":"example@gmail.com",
>>  "password": "example",
>>  "rate": "free",
>>  "expiration": null
>> }
>> ``` 
>> 
> ### 2. Authentication
>> URI `http://api/user/authentication`
>>
>> Request type ***POST***
>>
>> Request must contain **email** and **password**
>>
>> Response type: ***JSON***
>>
>> HTTP statuses:
>>  - **OK**
>>  - **NOT_FOUND**
>>  - **UNAUTHORIZED**
>>  - **BAD_REQUEST**
>> 
>> Response contains UserEntity
>> ```json
>> {
>>  "email":"example@gmail.com",
>>  "password": "example",
>>  "rate": "free",
>>  "expiration": null
>> }
>> ``` 

> # Contribution 
> ### How to contibute? 
>
> - Create a *fork*
> - Create a *branch*  
> - Do *improvments*
> - Send *pull request*
>
> Run `mvn clean install` before sending a pull request to avoid confusing situations.
>

> # Docker image
> [Link to DockerHub.](https://hub.docker.com/repository/docker/l3r8y/screener_api_image)
