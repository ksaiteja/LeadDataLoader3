# LeadDataLoader3

How to run:
1. install node modules in react-frontend
2. you can open the Backend folder in eclipse ide as an existing maven project
3. run FSDApp.java file in backend to start spring-boot app, it will start server on port no. 8081
4. create a database named LeadDataLoader in mongoDB
5. run the following in mongo shell
```
use LeadDataLoader
db.Users.insertOne({
   username: "exampleUser",
   password: "exampleUassword", 
   role: "admin"
});
```
6. login with the provided username and password at the frontend
