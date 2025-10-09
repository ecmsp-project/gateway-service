## How to retrieve orders from order-service locally

### Set up private and public key
Go to user-service
Run in root below commands.

> if you have any errors, you should probably install openssl as is not installed on your system
 
**Generate RSA keys:**
```bash
   cd src/main/resources/local/secrets/
   openssl genrsa > local.private.key
   openssl rsa -in local.private.key -pubout > local.public.key
```
It should create two files private and public key, private is used to sing jwt token while generating it.
Public one is to verify the signature. For that reason
```
1. Copy you generated public key
2. Go to gateway
3. Paste it into src/main/resources/keys
```

### Services to run - sequence is not important
1. user-service
2. order-service
3. gateway-service

> order-service and user-service need to run with profile local 
1. click 3 dots next to run and bug
2. under configuration choose edit
3. in Active profiles field enter "local"

Just click run in IJ for every service, data are added automatically as database is created.
So you don't need to run anything else.

When all of them is running you can run your client: fronted or e2e repo

### How to make requests
To make any request you need to first login - send request to login endpoint, you will get back jwt token in authorization
header. You should provide this token with every request (in authorization header) to order-service.
Order endpoints accessible in src/main/java/com/ecmsp/gatewayservice/api/rest/order/OrderController.java, 
data used by the order endpoints are in src/main/java/com/ecmsp/gatewayservice/api/rest/order/dto

### Examples how to retrieve data
Please look into e2e-test service - you can clone it from project repository

## Credentials

```properties
test.user.login=andy
test.user.password=password123
```

