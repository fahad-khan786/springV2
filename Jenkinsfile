pipeline {
    agent any

    environment {
        DOCKER_HOST = 'npipe:////./pipe/docker_engine'   // Required for Windows
        DOCKER_HUB = "fahaddock"
        IMAGE_NAME = "myapp"
        DOCKER_CONFIG = "C:\\Users\\fahad_khan\\.docker"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'spring-feature',
                    url: 'https://github.com/fahad-khan786/springV2.git'
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
                    echo Cleaning old containers...

                    docker ps -a -q --filter "name=springboot_myapp" | findstr . && docker rm -f springboot_myapp || echo "springboot_myapp not found"
                    docker ps -a -q --filter "name=mysql_db" | findstr . && docker rm -f mysql_db || echo "mysql_db not found"

                    docker-compose down -v --remove-orphans || echo "No previous compose setup"

                    echo Starting new containers...
                    docker-compose up -d --build

                    echo Deployment done!
                '''
            }
        }
    }

    post {
        success {
            echo "Deployment successful → ${DOCKER_HUB}/${IMAGE_NAME}:latest"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
