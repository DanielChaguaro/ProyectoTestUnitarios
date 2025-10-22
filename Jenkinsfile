pipeline {
    agent any

    environment {
        DEPLOY_PATH = "C:\\deploy\\mi-app"
        GITHUB_REPO = 'DanielChaguaro/ProyectoTestUnitarios'
        GITHUB_CREDENTIALS = 'github-token'
    }

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
                echo ' Ejecutando análisis de estilo con Checkstyle...'
                // Genera reporte XML y HTML
                bat 'mvn checkstyle:checkstyle'
            }
            post {
                always {
                    // Guarda el reporte HTML como artefacto
                    archiveArtifacts artifacts: 'target/site/checkstyle.html', fingerprint: true

                    // Publica el reporte XML (si tienes el plugin Warnings Next Generation)
                    recordIssues tools: [checkStyle(pattern: 'target/checkstyle-result.xml')]
                }
            }
        }
        stage('Deploy Local') {
            steps {
                echo "Desplegando aplicación en entorno local..."
                // Elimina versiones previas del despliegue
                bat "if exist %DEPLOY_PATH% rmdir /S /Q %DEPLOY_PATH%"
                // Crea directorio de despliegue
                bat "mkdir %DEPLOY_PATH%"
                // Copia el .jar generado al entorno simulado
                bat "copy target\\*.jar %DEPLOY_PATH%\\deploy_integrador_calidad.jar"
            }
        }
    }
    post {
        success {
            githubNotify context: 'Jenkins CI', status: 'SUCCESS', description: 'Build exitoso'
        }
        failure {
            githubNotify context: 'Jenkins CI', status: 'FAILURE', description: 'Build fallido'
        }
        always {
            echo 'Notificación enviada a GitHub.'
        }
    }
}