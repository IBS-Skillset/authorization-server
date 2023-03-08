# authorization-server

docker build -t oauth-server:latest .

docker run -p 9000:9000 -e HOST_IP=host.docker.internal  --name oauth-server oauth-docker:latest .