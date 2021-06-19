#!/bin/bash

## Install JDK 8 first
echo y | sudo apt-get install openjdk-8-jdk

## Install Mysql
echo y | sudo apt-get install mysql-server

## Install zip
echo y | sudo apt-get install unzip


## Create Mysql USER AND PASSWORD

sudo mysql -u root -e "DROP USER 'xmeme'@'localhost'";

sudo mysql -u root -e "CREATE USER 'xmeme'@'localhost' IDENTIFIED BY 'xmememysql';
GRANT ALL PRIVILEGES ON * . * TO 'xmeme'@'localhost';
FLUSH PRIVILEGES;"

## Drop and Create Database Xmeme in Mysql
sudo mysql -u xmeme -pxmememysql -e "DROP DATABASE xmeme;"

sudo mysql -u xmeme -pxmememysql -e  "CREATE DATABASE xmeme DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;use xmeme;"

## Create the table meme_creators
sudo mysql -u xmeme -pxmememysql -e "use xmeme;CREATE TABLE meme_creators (owner_id BIGINT NOT NULL AUTO_INCREMENT,owner_name varchar(50) NOT NULL, joined_time BIGINT NOT NULL, PRIMARY KEY (owner_id));"

## Create the table xmeme
sudo mysql -u xmeme -pxmememysql -e "use xmeme;CREATE TABLE memes (meme_id BIGINT NOT NULL AUTO_INCREMENT,caption varchar(100) NOT NULL, url varchar(3000) NOT NULL, created_time BIGINT NOT NULL,owner_id BIGINT NOT NULL, PRIMARY KEY (meme_id), FOREIGN KEY (owner_id) REFERENCES meme_creators(owner_id));"

## Download apache tomcat
wget -c https://mirrors.estointernet.in/apache/tomcat/tomcat-9/v9.0.43/bin/apache-tomcat-9.0.43.zip

## Unzip tomcat
unzip apache-tomcat-9.0.43.zip



## Build the war

## change directory
cd ./backend/

## Build war task
./gradlew  war

## Come back to the old director

cd ..
## deploy war to ROOT

## Remove existing files and folder
sudo rm -rf ./apache-tomcat-9.0.43/webapps/*

## Mkdir ROOT
mkdir ./apache-tomcat-9.0.43/webapps/ROOT

## Copy war
cp ./backend/build/libs/XMeme-Backend-1.0-XMEMEServer.war ./apache-tomcat-9.0.43/webapps/ROOT/

## Unzip war

unzip ./apache-tomcat-9.0.43/webapps/ROOT/XMeme-Backend-1.0-XMEMEServer.war -d apache-tomcat-9.0.43/webapps/ROOT/


## Conf server.xml for port changes
cp ./backend/additional_conf/server.xml ./apache-tomcat-9.0.43/conf/

## create webapps2
mkdir apache-tomcat-9.0.43/webapps2/

## Copy the api docs for swagger
cp -r ./docs/* ./apache-tomcat-9.0.43/webapps2/


## if we want client build, we should uncomment it and run
## client_script.sh


## Build is added into the commit version for demo purpose; so copy it
sudo cp -r ./frontend/index.html ./apache-tomcat-9.0.43/webapps/ROOT/

sudo cp -r ./frontend/dist ./apache-tomcat-9.0.43/webapps/ROOT/
