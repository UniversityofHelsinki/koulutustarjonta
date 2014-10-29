koulutustarjonta
================

Getting Started
---------------
Install [Vagrant](https://www.vagrantup.com/) and [Ansible](http://www.ansible.com/). 

Download Oracle XE 11g rpm from [here](http://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html) and place the zip file into the *oracle* folder.

Start up Vagrant
```
cd vagrant
vagrant up
```

Project structure
-----------------
**koulutustarjonta-query** Main service that provides an API for accessing education data

**koulutustarjonta-update** Update worker that handle integration to OPH

**koulutustarjonta-common** Common parts like DAO and domain packages that are used by both applications

Running locally
---------------
Project folder is mapped to */src* in Vagrant. In that folder applications can be run with gradle
```
./gradlew :koulutustarjonta-query:run
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
By default, tests are run agains local database runnin in Vagrant virtual machine. In order to run tests against another database, credentieals can be given as params
```
./gradlew test -Pdb.url=<jdbc url> -Pdb.user=<username> -Pdb.passwd=<password>
```



