# SmartPark
SmartPark Test Examination
## Authentication (JWT)
This application uses JWT (JSON Web Token) for securing all API endpoints.
- Authentication is implemented using Spring Security + JWT
- A static username and password are used (as per requirements)
- All endpoints are protected except the login endpoint

End point - POST /auth/login

Request Body
{
"username": "admin",
"password": "password"
}

Response
{
"token": "your-jwt-token"
}