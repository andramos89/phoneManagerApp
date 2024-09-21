

# Step 1: Start Docker Compose
echo "Starting PostgreSQL with Docker..."
docker-compose up -d

# Step 2: Build the Spring Boot application (needs connection for running test ContextLoads)
echo "Building Spring Boot application..."
./mvnw clean install

# Step 3: Run Spring Boot application
echo "Running Spring Boot application..."
java -jar target/PhoneContactApp-0.0.1-SNAPSHOT.jar