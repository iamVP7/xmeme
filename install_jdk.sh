
wget -c https://cdn.azul.com/zulu/bin/zulu8.54.0.21-ca-jdk8.0.292-linux_x64.tar.gz -O jdk8.tar.gz

mkdir ./jdk
tar -xvf jdk8.tar.gz --strip 1 -C ./jdk

