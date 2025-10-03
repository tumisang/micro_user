pipeline {
    agent any
    tools {
        jdk 'jdk17' // Match the name you configured in Global Tools
        maven 'maven' // Match your Maven version
    }
    environment {
        // Customize these for your project
        APP_NAME = 'microuser'
        GIT_REPO = 'https://github.com/tumisang/micro_user.git'
        BRANCH = 'main'
        // For Windows paths
        BUILD_DIR = "${WORKSPACE}\\target"
        JAR_FILE = "${APP_NAME}.jar"
        DOCKER_IMAGE = "${APP_NAME}:latest"
        CONTAINER_NAME = "${APP_NAME}-container"
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH}",
                url: "${GIT_REPO}"
                echo 'Code checked out successfully'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
                echo 'Application compiled'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
                echo 'Tests completed'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
                echo 'Application packaged'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def jarPath = "${BUILD_DIR}\\${JAR_FILE}"
                    if (fileExists(jarPath)) {
                        echo "Building Docker image from JAR file: ${jarPath}"

                        bat """
                            docker build -t ${DOCKER_IMAGE} .
                        """
                        echo "Docker image ${DOCKER_IMAGE} built successfully"
                    } else {
                        error "No JAR file found at ${jarPath}. Cannot build Docker image."
                    }
                }
            }
        }

        stage('Deploy with Docker') {
            steps {
                script {
                    bat """
                        docker run -d \
                          --name ${CONTAINER_NAME} \
                          -p 8091:8091 \
                          --restart unless-stopped \
                          ${DOCKER_IMAGE}
                    """

                    echo "Container ${CONTAINER_NAME} started from image ${DOCKER_IMAGE}"

                    sleep 10
                    bat "docker ps -f name=${CONTAINER_NAME}"
                }
            }
        }
    }
    post {
        always {
            echo 'Pipeline execution completed'
            archiveArtifacts artifacts: 'target/*.jar, target/*.war, Dockerfile', allowEmptyArchive: true
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded! Docker container deployed successfully'
            bat "docker ps -f name=${CONTAINER_NAME}"
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
            bat 'echo Workspace preserved for debugging'
            bat "docker logs ${CONTAINER_NAME} 2>nul || echo 'No container logs available'"
        }
    }
}