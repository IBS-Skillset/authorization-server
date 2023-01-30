# authorization-server

docker build -t oauth-docker-1.0:latest .

docker run -p 9000:9000 -e HOST_IP=host.docker.internal  --name oauth-server oauth-docker-1.0:latest .