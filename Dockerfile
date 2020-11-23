FROM adoptopenjdk/openjdk11
MAINTAINER Qamber Mehdi
COPY com/udacity/jwdnd/course1/cloudstorage/0.0.1-SNAPSHOT/cloudstorage-0.0.1-20201121.223907-1.jar /cloudstorage.jar


#COPY files/spring-cloud-config-server.jar /opt/spring-cloud/lib/
#COPY files/spring-cloud-config-server-entrypoint.sh /opt/spring-cloud/bin/
#ENV SPRING_APPLICATION_JSON= \ 
#  '{"spring": {"cloud": {"config": {"server": \
#  {"git": {"uri": "/var/lib/spring-cloud/config-repo", \
#  "clone-on-start": true}}}}}}'
#ENTRYPOINT ["/usr/bin/java"]
CMD ["java", "-jar", "/cloudstorage.jar"]
#VOLUME /var/lib/spring-cloud/config-repo
EXPOSE 8080
