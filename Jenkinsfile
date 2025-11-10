pipeline {
    agent any

    environment {
        DOCKER_HUB = "fahaddock"
        IMAGE_NAME = "myapp"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'spring-feature', url: 'https://github.com/fahad-khan786/springV2.git'
            }
        }

        stage('Build JAR') {
            steps {
                bat '.\\mvnw clean package -DskipTests'                    //'./mvnw clean package -DskipTests'
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

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-creds') {
                        docker.image("${DOCKER_HUB}/${IMAGE_NAME}:latest").push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
            bat 'docker-compose down || echo ok'
            bat 'docker-compose build'
            bat 'docker-compose up -d'

//                 sh 'docker-compose down || true'
//                 sh 'docker-compose up -d --build'
            }
        }
    }
     post {
        success {
          echo "Deployment finished: ${DOCKER_HUB}/${IMAGE_NAME}:latest"
        }
        failure {
          echo "Pipeline failed!"
        }
      }
}
