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
./gradlew update
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
./gradlew clean rpm -Pdb.url=<jdbc url> -Pdb.user=<db user> -Pdb.passwd=<db password> -Papi.endpoint=<api endpoint> -Penv=<evnironment: dev|test|prod>
```

Deployment
----------
After RPMs have been built, the two applications can be deployed using ansible
```
 ansible-playbook -i <inventory file> ansible/application-server-apps.yml -U <sudo user> -u <remote user>
```

Test environment
----------------
Test environment doesn't update database automatically, it instead has to be updated by hand using command
```
./gradlew update -Pdb.url=jdbc:oracle:thin:@laukku.it.helsinki.fi:1521:tikeuni -Pdb.user=<db user> -Pdb.passwd=<db password>
```