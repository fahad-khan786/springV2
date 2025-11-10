pipeline {
    agent any

    environment {
        DOCKER_HUB = "fahaddock"
        IMAGE_NAME = "myapp"
        DOCKER_CONFIG = "C:\\Users\\fahad_khan\\.docker"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'spring-feature', url: 'https://github.com/fahad-khan786/springV2.git'
            }
        }

        stage('Build JAR') {
            steps {
                bat '.\\mvnw clean package -DskipTests'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_HUB}/${IMAGE_NAME}:latest")
                }
            }
        }

        stage('Push Docker Image') {
            environment {
                DOCKER_CONFIG = 'C:\\Users\\fahad_khan\\.docker'
            }
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-creds') {
                        docker.image("${DOCKER_HUB}/${IMAGE_NAME}:latest").push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                    echo "🔁 Full clean rebuild of Docker environment..."
                    docker-compose down -v --remove-orphans
                    docker system prune -af --volumes
                    docker-compose up -d --build
                '''
            }
        }
    }

    post {
        success {
            echo "Deployment finished successfully: ${DOCKER_HUB}/${IMAGE_NAME}:latest"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
