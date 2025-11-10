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
                    echo "Checking for existing MySQL container..."
                    docker rm -f mysql_db || echo "mysql_db not found"

                    echo "Starting application..."
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
