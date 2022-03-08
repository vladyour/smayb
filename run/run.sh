./gradlew clean build
cd ./run
cp ../build/libs/smayb-1.jar ./
docker build -t smayb .
rm smayb-1.jar
docker-compose up -d
