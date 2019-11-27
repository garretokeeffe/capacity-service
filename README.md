# capacity-service

The Capacity Micro-service

### Purpose
Services to retrieve Customer Capacity information.

### Swagger
http://localhost:8080/swagger-ui.html#/
http://capacity-service-fisheries-dev.apps.rhos.agriculture.gov.ie/swagger-ui.html

#### Testing
You will need to either disable keycloak integration or generate a user token in Postman & request credentials

##### To test with KeyCloak & Postman 
Get Advanced token and fill these values:-
- Grant Type: Authorisation Code
- Callback URL: https://openidconnect.net/callback
- Auth URL: https://sso-keycloak-sso1.apps.rhos.agriculture.gov.ie/auth/realms/Staging-Realm/protocol/openid-connect/auth
- Access Token URL: https://sso-keycloak-sso1.apps.rhos.agriculture.gov.ie/auth/realms/Staging-Realm/protocol/openid-connect/token
- Client ID: 9d5d5361
- Client Secret: 8a6ad8d597f8915f43b31a912098c2ee
If a keyCloak popup appears, you will need to authenticate yourself with <usrname>/<password> e.g. fisherman/<password>

##### Postman URLs
- [GET] http://localhost:8080/sfos/capacity/ifis/{customer-ifis-id} - Retrieve customer capacity by CCS ID (ie: http://localhost:8080/sfos/capacity/ifis/2957).
- [GET] http://localhost:8080/sfos/capacity/ccs/{customer-ccs-id} - Retrieve customer capacity by CCS ID (ie: http://localhost:8080/sfos/capacity/ccs/FBY10086C).

- https://api-3scale-apicast-staging.apps.rhos.agriculture.gov.ie:443/sfos/.... - 3Scale url.