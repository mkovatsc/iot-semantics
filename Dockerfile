FROM maven:3.3-jdk-8
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/
RUN git clone https://github.com/eclipse/californium.git
WORKDIR /usr/src/californium
RUN mvn -Dmaven.test.skip=true  install

RUN apt-get update && apt-get install yap
COPY . /usr/src/app
WORKDIR /usr/src/app/euler
RUN bash install.sh

WORKDIR /usr/src/app
RUN mvn install
EXPOSE 8080
CMD ["/usr/bin/java", "-jar","semantics-ide-1.0-SNAPSHOT.jar"]