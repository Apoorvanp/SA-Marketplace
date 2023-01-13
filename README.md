# Installation Steps
```shell
./gradlew clean build
docker build -t marketplace:1.0 .
docker run -p 8080:8080 marketplace:1.0
```
