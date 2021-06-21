#!/bin/bash

## Install JDK 8 first
echo y | sudo apt-get install openjdk-8-jdk

## Install Mysql
echo y | sudo apt-get install mysql-server

## Install zip
echo y | sudo apt-get install unzip


chmod +x install.sh
./install.sh



chmod +x config_mysql.sh
./config_mysql.sh



## Build the war

## change directory
cd ./backend/

## Build war task
./gradlew  war

## Come back to the old director

cd ..
## deploy war to ROOT

## Remove existing files and folder
sudo rm -rf ./tomcat/webapps/*

## Mkdir ROOT
mkdir ./tomcat/webapps/ROOT

## Copy war
cp ./backend/build/libs/XMeme-Backend-1.0-XMEMEServer.war ./tomcat/webapps/ROOT/

## Unzip war

unzip ./tomcat/webapps/ROOT/XMeme-Backend-1.0-XMEMEServer.war -d tomcat/webapps/ROOT/


## Conf server.xml for port changes
cp ./backend/additional_conf/server.xml ./tomcat/conf/

## create webapps2
mkdir tomcat/webapps2/

## Copy the api docs for swagger
cp -r ./docs/* ./tomcat/webapps2/


## if we want client build, we should uncomment it and run
## client_script.sh


## Build is added into the commit version for demo purpose; so copy it
sudo cp -r ./frontend/index.html ./tomcat/webapps/ROOT/

sudo cp -r ./frontend/dist ./tomcat/webapps/ROOT/
