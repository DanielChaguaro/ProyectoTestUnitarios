pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Compilando el proyecto con Maven...'
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                echo ' Ejecutando tests de JUnit...'
                bat 'mvn test'
            }

            post {
                always {
                    echo 'Publicando resultados de pruebas...'
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Code Style - Checkstyle') {
            steps {
                echo ' Verificando estilo de código con Checkstyle...'
                bat 'mvn checkstyle:check'
            }
        }
    }
}