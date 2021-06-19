# xmeme
This project has both backend and frotend code of XMeme Project. You can learn more about the Architecture of the whole Xmeme developer hear in [arch.md](arch.md) file

Contents

- [Tech Stack](#tech-stack)
- [How to Build and deploy](#how-to-build-and-deploy)
    - [Full build and deploy](#full-build-and-deploy)
    - [Backend Build](#backend-build)
        - [Create backend Build](#create-backend-build)
        - [Run Backend build](#run-backend-build)
    - [Client Build](#client-build)
- [How to Develop](#how-to-develop)
    - [How to develop backend](#how-to-develop-backend)
    - [Deploying newly created war](#deploying-newly-created-war)
        - [To change the port](#to-change-the-port)
    - [How to develop frontend](#how-to-develop-frontend)
        - [To change the port](#to-change-the-port)
        - [To Connect to your backend](#to-connect-to-your-backend)

### Tech Stack

- Font End  : VueJS
- Rest API  : Jersey
- Backend   : Java 8
- ORM       : Hibernate
- Database  : MySQL

Sample Demo is hosted at [AWS](http://ec2-18-218-95-245.us-east-2.compute.amazonaws.com:8081/) and the Swagger [docs](http://ec2-18-218-95-245.us-east-2.compute.amazonaws.com:8080/) hosted at the same instance.

### How to Build and deploy


#### Full build and deploy
To build and run this in your machine kindly execute the below command.

```shell
chmod +x test_server.sh
sh test_server.sh
```

#### Backend Build

##### Create backend Build

To deploy the backend build alone along with basic client part which was built as on Feb 14th 2021, execute the below command

```shell
chmod +x install.sh
./install.sh
```

##### Run Backend build

To simply run the backend build you have follow the following command. Make sure before this you have created backend build

```shell
chmod +x server_run.sh
./server_run.sh 

## incase if you want to run in back ground
./server_run.sh  &
```

#### Client Build

Since npm build (can  be called as client part build for first time developers) take so long, already client build is generated and checked-in. But you can also build the client part alone separately using the below command


```shell
chmod +x client_script.sh
sh client_script.sh
```

### How to Develop

The code is divided into two part *backend* and *frontend*. 

The backend components are available in the backend folder and frontend components are available in frontend folder.

#### How to develop backend

Will suggest using IntelliJ IDEA Community editor as your IDE. Download [page](https://www.jetbrains.com/idea/download/). 

Once you have downloaded the whole repo, open the backend part (open only backend folder) alone in the IntelliJ IDEA, it will be automatically detecting gradle and download the necessary dependencies mostly.  (File -> New -> Project from Existing sources)

*In case your dependencies are not downloaded, make sure to have the following*

- [Jersey container v2.33](https://repo1.maven.org/maven2/org/glassfish/jersey/containers/jersey-container-servlet-core/2.33/jersey-container-servlet-core-2.33.jar)
- [Jersey Media JSON v2.33](https://repo1.maven.org/maven2/org/glassfish/jersey/media/jersey-media-json-jackson/2.33/jersey-media-json-jackson-2.33.jar)
- [JSON v20201115](https://repo1.maven.org/maven2/org/json/json/20201115/json-20201115.jar)
- [MySQL v8.0.11](https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.11/mysql-connector-java-8.0.11.jar)
- [Hibernate commons annotations v5.1.2](https://repo1.maven.org/maven2/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar)
- [Hibernate core v5.4.27](https://repo1.maven.org/maven2/org/hibernate/hibernate-core/5.4.27.Final/hibernate-core-5.4.27.Final.jar)
- [Hibernate plugin](https://repo1.maven.org/maven2/org/hibernate/hibernate-gradle-plugin/5.4.27.Final/hibernate-gradle-plugin-5.4.27.Final.jar)
- [Jersey HK](https://repo1.maven.org/maven2/org/glassfish/jersey/inject/jersey-hk2/2.26/jersey-hk2-2.26.jar)
- [Jersey Commons](https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-common/2.26/jersey-common-2.26.jar)

and its dependencies.


Then in the IntelliJ terminal,  execute the below command

```shell
 ./gradlew jar
 ```

When the above command is execute we will be getting the war file in the build folder. the path is *build/libs/XMeme-Backend-1.0-XMEMEServer.war* 

So whenever we are making the changes in Java and if we want to see how it works, we will have to create the war.


#### Deploying newly created war

Download Apache tomcat V9.0.43 (recommended). To download follow the command in terminal 

```shell
wget -c https://mirrors.estointernet.in/apache/tomcat/tomcat-9/v9.0.43/bin/apache-tomcat-9.0.43.zip
```

The above command will be downloading the file apache-tomcat-9.0.43.zip. Next step is unzip it

```shell
unzip apache-tomcat-9.0.43.zip
```

Now once the above command is executed we will be seeing the folder apache-tomcat-9.0.43.

Go inside this folder by executing cd apache-tomcat-9.0.43/webapps

Now you will be seeing a folder named **ROOT** , remove all the contents in this folder *rm -rf * is the command*, now copy the war file(XMeme-Backend-1.0-XMEMEServer.war) into this folder, and execute *unzip XMeme-Backend-1.0-XMEMEServer.war*

Once unzipped, goto *bin* folder of apache-tomcat-9.0.43.

There execute *sh catalina.sh run* to start the server. Now the server will be running at the port 8080


##### To change the port

In case if you want to change the port. Goto apache-tomcat-9.0.43/conf folder, there server.xml will be there. 

Search for following line

```
<Connector port="8080" protocol="HTTP/1.1"
                   connectionTimeout="20000"
                   redirectPort="8443" />
```

In this you can change port=8081, in case you want to run in the port 8081

#### How to develop frontend

Will suggest using VSCode to develop frontend. Download [page](https://code.visualstudio.com/download)

Make sure node v10.19.0 is installed (always use LTS version) in your machine.

Now open the frontend folder in the VSCode File -> Open Folder and select front end

In VSCode Terminal, run the following command

```shell
npm install
```

This command will be downloading the dependencies to run the project.

Then to run the project, execute the following command

```shell
npm run dev
```

once this command is executed then in few seconds http://localhost:9090 will be opened up and the project will run.


##### To change the port

To Change the running port open the file config/index.js , search for *port: 9090* there you can change the port you want.


##### To Connect to your backend

As of now the backend is connected to my [AWS](http://ec2-18-218-95-245.us-east-2.compute.amazonaws.com:8081/), for development you have to connect to yours. In config/index.js search for *target* . Under module.exports -> dev -> proxyTable -> /memes -> target