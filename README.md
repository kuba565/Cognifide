# Building

    * To build the project use following command: `mvn clean install`

# Running

    * After building the application run following command to start it:

        - `java -Ddatasource=books.json -jar target/Cognifide-0.0.1-SNAPSHOT.jar`

    * to configure the data source change "-Ddatesource=>>YOUR_DATA_SOURCE_PATH<<" property with your path
    in the aforementioned command.

# Access

    * The application will run on localhost:8080