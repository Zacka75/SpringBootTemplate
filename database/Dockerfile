FROM mysql:5.5
#FROM mysql:latest

MAINTAINER Andrea

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_ALLOW_EMPTY_PASSWORD=true
ENV MYSQL_DATABASE=andreaDB
ENV MYSQL_USER=andrea
ENV MYSQL_PASSWORD=andrea

#COPIATO I SCRIPT DI CREAZIONE DB DENTRO /docker-entrypoint-initdb.d/ DOVE VENGONO ESEGUITI IN ORDINE ALFABETICO
ADD schema.sql /docker-entrypoint-initdb.d/1-schema.sql
ADD data.sql /docker-entrypoint-initdb.d/2-data.sql
COPY schema_oauth2.sql /docker-entrypoint-initdb.d/3-schema_oauth2.sql
COPY data_oauth2.sql /docker-entrypoint-initdb.d/4-data_oauth2.sql

RUN chmod 777 /docker-entrypoint-initdb.d/1-schema.sql
RUN chmod 777 /docker-entrypoint-initdb.d/2-data.sql
RUN chmod 777 /docker-entrypoint-initdb.d/3-schema_oauth2.sql
RUN chmod 777 /docker-entrypoint-initdb.d/4-data_oauth2.sql

#ALTERNATIVE:
#RUN mysqld_safe & until mysqladmin ping; do sleep 1; done && \
#    mysql -uroot -e "CREATE DATABASE somedb;" && \
#    mysql -uroot -e "CREATE USER 'someuser'@'localhost' IDENTIFIED BY 'somepassword';" && \
#    mysql -uroot -e "GRANT ALL PRIVILEGES ON somedb.* TO 'someuser'@'localhost';"
# Change the working directory
#WORKDIR data
#CMD mysql -u $MYSQL_USER -p $MYSQL_PASSWORD $MYSQL_DATABASE < schema.sql

EXPOSE 3306

#ELENCO COMANDI:
#1) docker build --no-cache .                  -> Successfully built 59add0b1d63d
#2) docker run -p 3306:3306 -d 59add0b1d63d    -> e934ddedb965808f0feded7b6e57a339f466ed5e7b8bfe0a72e848be5612ed7b
#3) docker logs e934d
#4) docker stop/start e934d

#ALTRI COMANDI
#docker run -p 3306:3306 --name CONTAINER_NAME -e MYSQL_ROOT_PASSWORD=DB_ROOT_PASSWORD -e MYSQL_USER=DB_USER -e MYSQL_PASSWORD=DB_USER_PASSWORD -e MYSQL_DATABASE=DB_NAME -d mvpgomes/epcisdb
#docker run -d -p 3306:3306 -v /tmp:/tmp  -e STARTUP_SQL="/tmp/to_be_imported.mysql" tutum/mysql:5.5
#docker run -d -p 3306:3306 -v /tmp:/tmp -e MYSQL_PASS="admin" -e STARTUP_SQL="/tmp/mysqldump.mysql" tutum/mysql:5.5
#from the host: docker exec -it <my_image_name> bash -> #inside of the container: $ mysql -u root -> from the host: mysql -h 127.0.0.1 -P 3306 --protocol=tcp -u root
#docker run --detach --name=$MYSQL_SERVER_CONTAINER --net=$DB_DOCKER_NETWORK --env="MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD" -p ${MYSQL_PORT}:3306 mysql <- docker network create --driver bridge $DB_DOCKER_NETWORK
#docker run --detach --name=test-mysql -p host-port:container-port  --env="MYSQL_ROOT_PASSWORD=my-root-pswd" -v /my-host-dir/shared-dir:/container-dir mysql:latest
# docker cp ./data.sql mysql-container:/home
# 1) docker volume create mysql-volume
# 2) docker run --name=mk-mysql -p3306:3306 -v mysql-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql/mysql-server:8.0.20
# 3) docker ps -> docker logs mk-mysql -> docker exec -it mk-mysql bash -> bash-4.2# mysql -u root -p -> mysql> CREATE DATABASE MYSQLTEST; -> mysql> update mysql.user set host = ‘%’ where user=’root’;
# 3) docker volume create phpmyadmin-volume -> docker run --name mk-phpmyadmin -v phpmyadmin-volume:/etc/phpmyadmin/config.user.inc.php --link mk-mysql:db -p 82:80 -d phpmyadmin/phpmyadmin -> docker ps -f "name=mk-phpmyadmin" -> docker logs mk-phpmyadmin
# 4) Open your browser and visit http://localhost:82 to access phpMyAdmin UI:

