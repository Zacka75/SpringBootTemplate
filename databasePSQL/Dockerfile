#FROM postgres:11.2-alpine
#FROM postgres:12
FROM postgres:latest

MAINTAINER Andrea

ENV POSTGRES_USER=andrea
ENV POSTGRES_PASSWORD=andrea

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
# Change the working directory
#WORKDIR data
CMD ["docker-entrypoint.sh", "postgres"]

EXPOSE 5432

#ELENCO COMANDI:
#1) docker build --no-cache .                  -> Successfully built 59add0b1d63d
#2) docker run -p 5432:5432 -d 59add0b1d63d    -> e934ddedb965808f0feded7b6e57a339f466ed5e7b8bfe0a72e848be5612ed7b
#3) docker logs e934d
#4) docker stop/start e934d

