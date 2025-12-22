# Najaspad - Docker Deployment Guide

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Quick Start](#quick-start)
3. [Deployment Options](#deployment-options)
4. [Configuration](#configuration)
5. [Troubleshooting](#troubleshooting)

## Prerequisites

- Docker 20.10+
- Docker Compose 2.0+
- 512MB RAM minimum
- 1GB disk space

## Quick Start

### 1. Development Deployment (H2 Database)

Perfect for testing and development:

```bash
# Start the application
docker-compose -f docker-compose.dev.yml up -d

# View logs
docker-compose -f docker-compose.dev.yml logs -f

# Stop
docker-compose -f docker-compose.dev.yml down
```

**Note:** Data is stored in-memory and will be lost when the container stops.

### 2. Production Deployment (PostgreSQL Database)

For production use with persistent data:

```bash
# Start the application and database
docker-compose up -d

# View logs
docker-compose logs -f

# Stop (keeps data)
docker-compose down

# Stop and remove all data
docker-compose down -v
```

## Deployment Options

### Option 1: Docker Compose (Recommended)

**File Structure:**
```
najaspad/
├── Dockerfile
├── docker-compose.yml          # Production with PostgreSQL
└── docker-compose.dev.yml      # Development with H2
```

**Commands:**
```bash
# Production
docker-compose up -d

# Development
docker-compose -f docker-compose.dev.yml up -d

# View all running containers
docker ps

# Follow logs
docker-compose logs -f najaspad

# Restart
docker-compose restart najaspad

# Stop
docker-compose down
```

### Option 2: Standalone Docker Container

Build and run without Docker Compose:

```bash
# Build the image
docker build -t najaspad:latest .

# Run with H2 (development)
docker run -d \
  --name najaspad \
  -p 8080:8080 \
  najaspad:latest

# Run with external PostgreSQL
docker run -d \
  --name najaspad \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/najaspad \
  -e SPRING_DATASOURCE_USERNAME=najaspad \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  najaspad:latest
```

## Configuration

### Environment Variables

Edit `docker-compose.yml` to configure:

```yaml
services:
  najaspad:
    environment:
      # Active profile (dev or prod)
      - SPRING_PROFILES_ACTIVE=prod
      
      # Database connection
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/najaspad
      - SPRING_DATASOURCE_USERNAME=najaspad
      - SPRING_DATASOURCE_PASSWORD=change_me_in_production
      
      # JPA settings
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
      - SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
```

### PostgreSQL Configuration

Default PostgreSQL settings in `docker-compose.yml`:

```yaml
postgres:
  environment:
    - POSTGRES_DB=najaspad
    - POSTGRES_USER=najaspad
    - POSTGRES_PASSWORD=najaspad_password  # CHANGE IN PRODUCTION!
```

**⚠️ Security Warning:** Change the default password in production!

### Port Configuration

Change the application port:

```yaml
services:
  najaspad:
    ports:
      - "3000:8080"  # External:Internal
```

### Resource Limits

Add resource limits for production:

```yaml
services:
  najaspad:
    deploy:
      resources:
        limits:
          cpus: '1'
          memory: 512M
        reservations:
          cpus: '0.5'
          memory: 256M
```

## Monitoring

### Health Checks

The application includes health check endpoints:

```bash
# Check application health
curl http://localhost:8080/actuator/health

# Expected response:
# {"status":"UP"}
```

### Logs

```bash
# View all logs
docker-compose logs

# Follow logs (real-time)
docker-compose logs -f

# View only najaspad logs
docker-compose logs najaspad

# Last 100 lines
docker-compose logs --tail=100 najaspad
```

### Container Status

```bash
# List running containers
docker-compose ps

# Detailed container info
docker inspect najaspad-app
```

## Backup and Restore

### Backup PostgreSQL Data

```bash
# Create backup
docker exec najaspad-db pg_dump -U najaspad najaspad > backup_$(date +%Y%m%d).sql

# Or using docker-compose
docker-compose exec postgres pg_dump -U najaspad najaspad > backup.sql
```

### Restore PostgreSQL Data

```bash
# Restore from backup
cat backup.sql | docker exec -i najaspad-db psql -U najaspad najaspad

# Or using docker-compose
cat backup.sql | docker-compose exec -T postgres psql -U najaspad najaspad
```

## Troubleshooting

### Application won't start

**Check logs:**
```bash
docker-compose logs najaspad
```

**Common issues:**
- Database not ready: Wait 30 seconds and restart
- Port already in use: Change port in docker-compose.yml
- Out of memory: Increase Docker memory limits

### Database connection issues

**Check database is running:**
```bash
docker-compose ps postgres
```

**Test database connection:**
```bash
docker-compose exec postgres psql -U najaspad -d najaspad -c '\dt'
```

### Cannot access application

**Verify container is running:**
```bash
docker ps | grep najaspad
```

**Check port binding:**
```bash
docker port najaspad-app
```

**Test connection:**
```bash
curl http://localhost:8080
```

### Reset everything

```bash
# Stop and remove all containers and volumes
docker-compose down -v

# Remove images (optional)
docker rmi najaspad:latest

# Start fresh
docker-compose up --build
```

## Updating the Application

### Update to latest code

```bash
# Pull latest changes
git pull

# Rebuild and restart
docker-compose up --build -d

# Or with no cache
docker-compose build --no-cache
docker-compose up -d
```

## Production Checklist

- [ ] Change PostgreSQL password
- [ ] Configure external database (optional)
- [ ] Set resource limits
- [ ] Configure reverse proxy (nginx/traefik)
- [ ] Enable HTTPS
- [ ] Set up backup strategy
- [ ] Configure monitoring
- [ ] Review security settings
- [ ] Test disaster recovery

## Advanced Configuration

### Using External PostgreSQL

Modify `docker-compose.yml`:

```yaml
services:
  najaspad:
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-server:5432/najaspad
      - SPRING_DATASOURCE_USERNAME=your_username
      - SPRING_DATASOURCE_PASSWORD=your_password
```

Remove the `postgres` service and `depends_on` section.

### Running Behind a Reverse Proxy

Example nginx configuration:

```nginx
server {
    listen 80;
    server_name notepad.example.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

## Support

For issues and questions:
- Check logs: `docker-compose logs -f`
- Review documentation: `HELP.md`
- Test connectivity: `curl http://localhost:8080/actuator/health`

