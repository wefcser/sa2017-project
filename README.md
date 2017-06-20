## Description
This project has two parts:
- frontend project(angularjs framework) in dir frontend
- backend project(spring-boot framework) in dir backend

To run backend in a load-balanced cluster system ,please refer to my another project [haproxy-3-tomcat-2-memcached](https://github.com/wefcser/haproxy-3-tomcat-2-memcached.git)


## Installation
Update angular-cli to latest version (1.1 current)
as described on [angular-cli github readme.md](https://github.com/angular/angular-cli#updating-angular-cli)

````
npm uninstall -g angular-cli @angular/cli
npm cache clean
npm install -g @angular/cli@latest
````
Clone project from github
````
git clone https://github.com/wefcser/sa2017-project.git
````
Install local project package
````
cd frontend
npm install --save-dev @angular/cli@latest
if npm version > 5.0 delete package-lock.json file  ( bug - this file prevent correct packages install)
npm install
````

Now fronted project use Angular CLI v.1.1 and Angular v.4.1.
You can see current dependencies in package.json file.

## Frontend Development server
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Running sa2017 project locally
```
	cd backend
	./mvnw spring-boot:run
	cd frontend
	ng serve
```

## Database configuration

In its default configuration, sa2017 project uses an in-memory database (HSQLDB) which
gets populated at startup with data.
A similar setups is provided for MySql and PostgreSQL in case a persistent database configuration is needed.
To run petclinic locally using persistent database, it is needed to change profile defined in application.properties file.

For MySQL database, it is needed to change param "hsqldb" to "mysql" in string
```
spring.profiles.active=hsqldb,spring-data-jpa
```
 defined in application.properties file.

Before do this, would be good to check properties defined in application-mysql.properties file.

```
spring.datasource.url = jdbc:mysql://localhost:3306/scis?useUnicode=true
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.username=root 
spring.datasource.password=scis 
spring.datasource.driver-class-name=com.mysql.jdbc.Driver 
spring.jpa.database=MYSQL 
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=none
```      

You may also start a MySql database with docker:

```
docker run --name mysql-petclinic -e MYSQL_ROOT_PASSWORD=petclinic -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:5.7.8
```




