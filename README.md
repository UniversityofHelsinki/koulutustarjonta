koulutustarjonta
================
Project structure
-----------------
**koulutustarjonta-service** Main service that provides an API for accessing education data

**koulutustarjonta-update** Update worker that handle integration to OPH

**koulutustarjonta-common** Common parts like DAO and domain packages that are used by both applications

Getting Started
---------------
Install [Vagrant](https://www.vagrantup.com/) and [Ansible](http://www.ansible.com/). 

Download Oracle XE 11g rpm from [here](http://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html) and place the zip file into the *oracle* folder.

Start up Vagrant
```
cd vagrant
vagrant up
```

Running locally
---------------
The project folder is mapped to */src* folder in Vagrant.
```
cd vagrant
vagrant ssh
cd /src
```
Run database migration to create tables
```
./gradlew clean :koulutustarjonta-service:shadowJar
java -jar koulutustarjonta-service/build/libs/koulutustarjonta-service-1.0-SNAPSHOT.jar db migrate koulutustarjonta-service/service-config.yml
```
Start up the applications
```
./gradlew :koulutustarjonta-service:run
./gradlew :koulutustarjonta-update:run
```

Running tests
-------------
Run all tests
````
./gradlew test
```
or for a single module eg.
```
./gradlew :koulutustarjonta-common:test
```
By default, tests are run agains local database runnin in Vagrant virtual machine. To run tests against some other database:
```
./gradlew test -Pdb.url=<jdbc url> -Pdb.user=<username> -Pdb.passwd=<password>
```

Building RPMs
-------------
```
./gradlew clean rpm -Pdb.url=<jdbc url> -Pdb.user=<db user> -Pdb.passwd=<db password> -Papi.endpoint=<api endpoitn>
```


