pipeline {
    agent any

    environment {
        DOCKER_HUB = "fahaddock"
        IMAGE_NAME = "myapp"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/fahad-khan786/springV2.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_HUB}/${IMAGE_NAME}:latest")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-credentials-id') {
                        docker.image("${DOCKER_HUB}/${IMAGE_NAME}:latest").push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker-compose down || true'
                sh 'docker-compose up -d --build'
            }
        }
    }
}
