pipeline {
    agent any

    environment {
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
            echo 'Build exitoso, notificando a GitHub...'
            step([
                $class: 'GitHubCommitStatusSetter',
                contextSource: [
                    $class: 'ManuallyEnteredCommitContextSource',
                    context: 'Jenkins CI'
                ],
                statusResultSource: [
                    $class: 'ConditionalStatusResultSource',
                    results: [[
                        $class: 'AnyBuildResult',
                        state: 'SUCCESS',
                    ]]
                ]
            ])
        }

        failure {
            echo 'Fallo: notificando a GitHub...'
            step([
                $class: 'GitHubCommitStatusSetter',
                contextSource: [
                    $class: 'ManuallyEnteredCommitContextSource',
                    context: 'Jenkins CI'
                ],
                statusResultSource: [
                    $class: 'ConditionalStatusResultSource',
                    results: [[
                        $class: 'AnyBuildResult',
                        state: 'FAILURE',
                    ]]
                ]
            ])
        }
    }
    post {
        success {
            githubNotify context: 'CI/CD', status: 'SUCCESS', description: 'Build succeeded', credentialsId: 'github-token'
            echo ' Pipeline completado correctamente.'
        }
        failure {
            githubNotify context: 'CI/CD', status: 'FAILURE', description: 'Build failed', credentialsId: 'github-token'
            echo ' El pipeline falló. Revisar logs.'
        }
    }
}