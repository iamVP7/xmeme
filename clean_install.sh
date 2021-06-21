
procssid=`ps -ef | grep xmeme/tomcat/bin/bootstrap.jar |  grep '[j]ava' | awk '{print $2}'`

kill -9 $procssid


rm ./backend/build/libs/XMeme-Backend-1.0-XMEMEServer.war


## change directory
cd ./backend/

## Build war task
./gradlew  war

cd ..

rm -rf ./tomcat/webapps/*


## Mkdir ROOT
mkdir ./tomcat/webapps/ROOT

## Copy war
cp ./backend/build/libs/XMeme-Backend-1.0-XMEMEServer.war ./tomcat/webapps/ROOT/

## Unzip war

unzip ./tomcat/webapps/ROOT/XMeme-Backend-1.0-XMEMEServer.war -d tomcat/webapps/ROOT/


## Conf server.xml for port changes
cp ./backend/additional_conf/server.xml ./tomcat/conf/


chmod +x server_run.sh

./server_run.sh &

