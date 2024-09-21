
# Step 1: Build the Spring Boot application
echo "Building Spring Boot application..."
./mvnw clean install

# Step 2: Start Docker Compose
echo "Starting PostgreSQL with Docker..."
docker-compose up -d

# Step 3: Run Spring Boot application
echo "Running Spring Boot application..."
java -jar target/PhoneContactApp-0.0.1-SNAPSHOT.jar