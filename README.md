# Najaspad - Online Notepad

A secure, password-protected online notepad built with Spring Boot and Thymeleaf, featuring rich text editing with Quill.js.

## Features

- 📝 **Rich Text Editor** - Full-featured WYSIWYG editor with formatting options
- 🔒 **Password Protection** - Optional password protection for your notes
- 🔗 **Shareable URLs** - Easy sharing with unique URLs for each note
- 💾 **Auto-save** - Your notes are saved securely
- 🐳 **Docker Ready** - Easy deployment with Docker and Docker Compose

## Quick Start

### Using Docker (Recommended)

#### Development Mode (H2 Database)
```bash
docker-compose -f docker-compose.dev.yml up --build
```

#### Production Mode (PostgreSQL Database)
```bash
docker-compose up --build
```

Access the application at: **http://localhost:8080**

### Local Development

#### Prerequisites
- Java 21+
- Maven 3.9+

#### Run locally
```bash
./mvnw spring-boot:run
```

## Docker Deployment

### Available Docker Compose Configurations

1. **docker-compose.dev.yml** - Development mode with H2 in-memory database
2. **docker-compose.yml** - Production mode with PostgreSQL

### Production Deployment

1. Clone the repository
2. Configure environment variables (optional)
3. Start the services:
   ```bash
   docker-compose up -d
   ```

### Environment Variables

Edit `docker-compose.yml` to customize:

```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/najaspad
  - SPRING_DATASOURCE_USERNAME=najaspad
  - SPRING_DATASOURCE_PASSWORD=your_secure_password
```

### Docker Commands

**View logs:**
```bash
docker-compose logs -f najaspad
```

**Stop application:**
```bash
docker-compose down
```

**Remove all data (including database):**
```bash
docker-compose down -v
```

**Rebuild after changes:**
```bash
docker-compose up --build
```

## Usage

1. **Create a Note**: Visit the root URL - a unique ID will be generated
2. **Write Content**: Use the rich text editor to format your note
3. **Set Password** (Optional): Add a password to protect your note
4. **Save**: Click the Save button or use Ctrl+S
5. **Share**: Copy the shareable URL to share with others
6. **Access Protected Notes**: Enter the password when prompted

## Architecture

- **Backend**: Spring Boot 4.0.1, Java 21
- **Frontend**: Thymeleaf, Bootstrap 5, Quill.js
- **Database**: H2 (dev) / PostgreSQL (prod)
- **Deployment**: Docker & Docker Compose

## Project Structure

```
najaspad/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── dev/naimsulejmani/najaspad/
│   │   │       ├── NajaspadApplication.java
│   │   │       ├── NotepadController.java
│   │   │       ├── NotepadService.java
│   │   │       └── ...
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-prod.properties
│   │       └── templates/
│   │           └── home.html
├── Dockerfile
├── docker-compose.yml
├── docker-compose.dev.yml
└── pom.xml
```

## Development

### Build the project
```bash
./mvnw clean package
```

### Run tests
```bash
./mvnw test
```

### Build Docker image manually
```bash
docker build -t najaspad:latest .
```

## Security Notes

- Passwords are stored as plain text in the database (for demo purposes)
- For production use, implement password hashing (BCrypt recommended)
- Use HTTPS in production
- Change default PostgreSQL credentials in docker-compose.yml

## License

This project is created for educational purposes.

## Contributing

Feel free to submit issues and enhancement requests!

