pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Compilando el proyecto con Maven...'
                // Compila y empaqueta el código (sin ejecutar tests aún)
                bat 'mvn clean package -DskipTests'
            }
        }
        
    }

}