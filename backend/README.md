To run the application move in the current file location and run next command.

./gradlew clean build bootRun --args="--api.key=<my-api-key>"

or build and start the app with:

./gradlew clean build
java -jar -Dapi.key="......." -jar ./build/libs/backend-0.0.1-SNAPSHOT.jar

Where <my-api-key> is your OpenAI api key generated from https://platform.openai.com/account/api-keys
