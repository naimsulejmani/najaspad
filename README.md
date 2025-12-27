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
- Java 17+
- Maven 3.9+

#### Run locally
```bash
./mvnw spring-boot:run
```

## Deploy to Render.com (Docker)

Render works great with this project using the **distroless Java 17 runtime image**.

This repo can run on Render **without Postgres** by using **H2 (file-based)** in the `prod` profile.
Note: on Render free tier the filesystem is typically **ephemeral**, so your notes may reset on redeploy unless you attach a persistent disk.

### Option A: Let Render build from your repo (recommended)

1. In Render, create a **New Web Service** and connect your GitHub repo.
2. Choose **Environment: Docker**.
3. Set **Dockerfile Path** to:
   - `Dockerfile.runtime`
4. Set **Port** to `8080` (the app also honors Render's `PORT` env var)
5. Add environment variables:
   - `SPRING_PROFILES_ACTIVE=prod`

Optional (only if you want to override defaults):
- `SPRING_DATASOURCE_URL` (defaults to `jdbc:h2:file:/data/najaspad;...`)
- `SPRING_DATASOURCE_USERNAME` (defaults to `sa`)
- `SPRING_DATASOURCE_PASSWORD` (defaults to `sa`)

Optional health check path:
- `/actuator/health`

### Option B: Build locally and push to a registry

Build runtime image:
- `docker build -f Dockerfile.runtime -t najaspad:runtime .`

Then push it to Docker Hub / GHCR and configure Render to deploy from that image.

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

- **Backend**: Spring Boot 4.0.1, Java 17
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
