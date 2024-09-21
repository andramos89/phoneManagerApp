# Phone Manager App

This is a simple project developed to store and get phone contacts.

This is a Springboot application to run and that can be used in multiple instances, connected to a postgresql database wich runs in a docker containerized environment.

To run the application please follow the following:

Pre-requisites:
 - ensure you have docker / docker-compose installed in the machine you are running this.

If you prefer to run a script instead of these commands, you can find this in a 'run.sh' file in the root of the project. In this case please consider executing the command: 'chmod +x run.sh', to give execution permissions to the script to be executed.


# Step 1: Build the Spring Boot application
echo "Building Spring Boot application..."
./mvnw clean install

# Step 2: Start Docker Compose
echo "Starting PostgreSQL with Docker..."
docker-compose up -d

# Step 3: Run Spring Boot application
echo "Running Spring Boot application..."
java -jar target/PhoneContactApp-0.0.1-SNAPSHOT.jar
