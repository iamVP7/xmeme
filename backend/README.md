# xmeme-backend
This project will be having the backend code of the x Meme


### Database Create Queries

**Meme Creators table**

CREATE TABLE `meme_creators` (`owner_id` BIGINT NOT NULL AUTO_INCREMENT,`owner_name` varchar(50) NOT NULL, `joined_time` BIGINT NOT NULL, PRIMARY KEY (`owner_id`));

This table will be holding the list of users who have uploaded the memes. 

**Memes table**

CREATE TABLE `memes` (`meme_id` BIGINT NOT NULL AUTO_INCREMENT,`caption` varchar(100) NOT NULL, `url` varchar(3000) NOT NULL, `created_time` BIGINT NOT NULL,`owner_id` BIGINT NOT NULL, PRIMARY KEY (`meme_id`), FOREIGN KEY (owner_id) REFERENCES meme_creators(owner_id));

This table will be holding the memes which are uploaded to the website. 
