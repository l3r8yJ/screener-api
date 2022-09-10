# Screener REST API. 
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
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
