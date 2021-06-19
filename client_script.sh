## Install Nodejs and npm
curl -sL https://deb.nodesource.com/setup_10.x | sudo -E bash -

echo y | sudo apt install nodejs

## Create client build
cd ./frontend/

sudo npm install

sudo npm run build

cd ../



sudo cp -r ./frontend/index.html ./apache-tomcat-9.0.43/webapps/ROOT/

sudo cp -r ./frontend/dist ./apache-tomcat-9.0.43/webapps/ROOT/

