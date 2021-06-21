
wget -c https://cdn.azul.com/zulu/bin/zulu8.54.0.21-ca-jdk8.0.292-linux_x64.tar.gz -O jdk8.tar.gz

wget -c https://mirrors.estointernet.in/apache/tomcat/tomcat-9/v9.0.46/bin/apache-tomcat-9.0.46.tar.gz -O tomcat.tar.gz

mkdir ./tomcat

tar -xvf tomcat.tar.gz --strip 1 -C ./tomcat

rm -rf ./tomcat/webapps/*

## Mkdir ROOT
mkdir ./tomcat/webapps/ROOT

