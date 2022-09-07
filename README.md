# Screener REST API. 
## User guide.
> ### 1. Registration
>> URI `http:/api/registration`
>> 
>> Request type: ***POST***
>> 
>> Request must contain **email** and **password**
>>
>> Response type: ***JSON***
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
>> 
