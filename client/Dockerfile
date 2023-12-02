FROM node:21-alpine

RUN mkdir -p /var/www/client
WORKDIR /var/www/client

COPY package*.json /var/www/client/
RUN npm install 
CMD "npm" "run" "dev"