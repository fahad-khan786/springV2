pipeline {
    agent any

    environment {
        IMAGE_NAME = "fahaddock/demo"
        CONTAINER_NAME = "demo-app"
        APP_PORT = "9999"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/yourusername/demo.git'  // change to your repo URL
            }
        }

        stage('Build WAR') {
            steps {
                echo "Building WAR file..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }

        stage('Push Docker Image') {
            when {
                expression { return input(message: 'Do you want to push image to Docker Hub?', ok: 'Push Now') }
            }
            steps {
                echo "Pushing image to Docker Hub..."
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh '''
                        echo $PASS | docker login -u $USER --password-stdin
                        docker push $IMAGE_NAME:latest
                    '''
                }
            }
        }

        stage('Deploy Container') {
            when {
                expression { return input(message: 'Do you want to deploy the container?', ok: 'Deploy Now') }
            }
            steps {
                echo "Deploying container..."
                sh '''
                    docker rm -f $CONTAINER_NAME || true
                    docker run -d -p $APP_PORT:8080 --name $CONTAINER_NAME $IMAGE_NAME:latest
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful! Visit http://<your-server-ip>:$APP_PORT"
        }
        failure {
            echo " Build or deployment failed. Check logs."
        }
    }
}
