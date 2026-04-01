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

// REMEMBER USE THE TOKEN ON TESTING ALL THE ENDPOINTS
1. ParkingLotController

End point - POST /parking-lots
Request Body
{
"lotId": "LOT-1",
"location": "QC",
"capacity": 100,
"costPerMinute": 20.20
}

Response Body
{
"capacity": 100,
"location": "QC",
"lotId": "LOT-1",
"occupiedSpaces": 0
}

End point - GET /{lotId}/status
Response Body
{
"capacity": 100,
"occupied": 0,
"available": 100
}

End point - GET /{lotId}/is-full
Response Body
false

------------------------------------------------------------------------------
2. VehicleController
End point - /api/vehicles

Request Body
{
  "licensePlate": "ABC-123",
  "type": "CAR",
  "ownerName": "Juan Dela Cruz"
}

Response Body
{
  "licensePlate": "ABC-123",
  "type": "CAR",
  "ownerName": "Juan Dela Cruz"
}

End point -  /api/vehicles/{licensePlate}
Response Body
{
    "licensePlate": "ABC-123",
    "ownerName": "Juan Dela Cruz",
    "type": "CAR"
}

End point - vehicles/exists/{licensePlate}
Response
True
------------------------------------------------------------------------------
3. ParkingRecordController
End point - /parking-records/check-in
{
    "entryTime": "2026-04-01T18:31:07.8901266",
    "exitTime": null,
    "licensePlate": "ABC-123",
    "lotId": "LOT-1",
    "status": "PARKED"
}

End point - /parking-records/active/{lotId}
Response
[
  {
    "licensePlate": "ABC-123",
    "lotId": "LOT-1",
    "entryTime": "...",
    "exitTime": null,
    "status": "PARKED"
  }
]
End point - /parking-records/check-out?licensePlate={licensePlate}

{
  "licensePlate": "ABC-123",
  "lotId": "LOT-1",
  "entryTime": "...",
  "exitTime": "2026-04-01T13:45:00",
  "status": "COMPLETED"
}

End point - /parking-records/expired