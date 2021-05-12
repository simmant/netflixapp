From openjdk:11.0.11
VOLUME /tmp
RUN mkdir /app
ADD ./target/FirstApp-1.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
ADD netflix_titles.csv /app/data.csv
CMD ["java","-jar","/app/app.jar"]
