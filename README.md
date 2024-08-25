# Books Social Network

## Overview

The Books Social Netowork Project is a robust, Java-based digital library system with integrated DevOps practices. This project allows users to add, share, and manage books, provide feedback, and handle book transactions securely.

## Key Features

- **User Management**: Secure token-based authentication and authorization.
- **Book Management**: Add, update, delete, and archive books.
- **Book Sharing**: Facilitate book sharing among users.
- **Feedback System**: Users can leave feedback and reviews on books.
- **Transaction Tracking**: Monitor book transactions and sharing history.

## Technology Stack

- **Backend**: Java, Spring Boot, JPA
- **Database**: PostgreSQL
- **CI/CD**: GitHub Actions
- **Containerization**: Docker
- **Cloud Deployment**: AWS Elastic Beanstalk
- **Notifications**: Slack Integration

## DevOps and CI/CD

I implemented a Continuous Deployment (CD) pipeline using GitHub Actions to automate the entire process from build to deployment.

### CD Pipeline Overview

The pipeline is triggered on:

- Manual dispatch from the GitHub Actions tab
- Pushes to the main branch that include changes in the `backend/` directory

Key steps in the pipeline:

1. **Environment Setup**:
   - Uses Ubuntu latest as the runner.
   - Sets up JDK 17 with the Temurin distribution.

2. **Docker Integration**:
   - Logs in to Docker Hub using secure credentials.
   - Builds and pushes a Docker image with a unique tag based on the current date and time.

3. **AWS Deployment**:
   - Updates the `Dockerrun.aws.json` file with the new Docker image tag.
   - Deploys the application to AWS Elastic Beanstalk.

4. **Version Control**:
   - Commits and pushes the updated `Dockerrun.aws.json` file back to the repository.

5. **Notifications**:
   - Sends Slack notifications at various stages of the deployment process.


