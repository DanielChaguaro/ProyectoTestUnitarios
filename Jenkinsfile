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
        /*stage('Code Style - Checkstyle') {
            steps {
                echo ' Verificando estilo de código con Checkstyle...'
                bat 'mvn checkstyle:check'
            }
        }*/
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
    }
}