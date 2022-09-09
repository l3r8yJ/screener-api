# Screener REST API. 
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