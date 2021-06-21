

## Create Mysql USER AND PASSWORD

sudo mysql -u root -e "DROP USER 'xmeme'@'localhost'";

sudo mysql -u root -e "CREATE USER 'xmeme'@'localhost' IDENTIFIED BY 'Xmeme_123';
GRANT ALL PRIVILEGES ON * . * TO 'xmeme'@'localhost';
FLUSH PRIVILEGES;"

## Drop and Create Database Xmeme in Mysql
sudo mysql -u xmeme -pXmeme_123 -e "DROP DATABASE xmeme;"

sudo mysql -u xmeme -pXmeme_123 -e  "CREATE DATABASE xmeme DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;use xmeme;"

## Create the table meme_creators
sudo mysql -u xmeme -pXmeme_123 -e "use xmeme;CREATE TABLE meme_creators (owner_id BIGINT NOT NULL AUTO_INCREMENT,owner_name varchar(50) NOT NULL, joined_time BIGINT NOT NULL, PRIMARY KEY (owner_id));"

## Create the table xmeme
sudo mysql -u xmeme -pXmeme_123 -e "use xmeme;CREATE TABLE memes (meme_id BIGINT NOT NULL AUTO_INCREMENT,caption varchar(100) NOT NULL, url varchar(3000) NOT NULL, created_time BIGINT NOT NULL,owner_id BIGINT NOT NULL, PRIMARY KEY (meme_id), FOREIGN KEY (owner_id) REFERENCES meme_creators(owner_id));"

