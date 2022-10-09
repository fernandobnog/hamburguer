
FROM node:18-alpine

RUN npm install -g http-server

RUN mkdir /app

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

CMD ["npx", "vite"]