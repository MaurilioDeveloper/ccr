sudo apt-get install docker docker.io

(1) Create a systemd drop-in directory:

$ mkdir /etc/systemd/system/docker.service.d

(2) Add proxy in /etc/systemd/system/docker.service.d/http-proxy.conf file:

     [Service]
     Environment="http_proxy=http://localhost:3128"
     Environment="https_proxy=http://localhost:3128"
     Environment="ftp_proxy=http://localhost:3128"
     Environment="HTTP_PROXY=http://localhost:3128"
     Environment="HTTPS_PROXY=http://localhost:3128"
     Environment="FTP_PROXY=http://localhost:3128"


(3) Flush changes:

$ systemctl daemon-reload

(4) Restart Docker:

$ systemctl restart docker 

Instalar oracle XE: 

sudo docker run -d --shm-size=2g -p 1521:1521 -p 8090:8080 alexeiled/docker-oracle-xe-11g 

https://hub.docker.com/r/alexeiled/docker-oracle-xe-11g/

create user ccr IDENTIFIED BY tivit123;

grant connect to ccr;

grant all privileges to ccr; 