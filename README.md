# pastebox

This is a sevice, that allows to load text or code and to get a short link on the loaded "pastes". Also you can get "paste" using hash and you can get last 10 public "pastes". 

## Steps to setup
1. Clone the application
```
git clone https://github.com/lenaaMorozz/pastebox.git
```
2. Run the app using maven
```
mvn spring-boot:run
```
The app will start running at http://localhost:8080

## Explore Rest API
The app defines following requests:

 Method| URL | Decription |Request body |
|----------|----------|----------|----------|
| POST    | /  | Add "paste"   | JSON |
| GET  | /{hash}   | Get "paste" using hash   | |
| GET    | /  | Get last 10 public "pastes"   | |

## JSON Request Body
Add "paste":

Before load "paste" you need to specify time, during the "paste" will be available, and access. The pastes may be PUPLIC (available to everyone) and UNLISTED (link access).
```
{
    "data" : "my text",
    "expirationTimeSeconds" : 10,
    "publicStatus" : "PUBLIC"
}
```


