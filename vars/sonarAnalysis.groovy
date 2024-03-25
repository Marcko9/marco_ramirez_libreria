def call(boolean abortPipeline){
    
    def scannerHome = tool 'sonar-scanner'
    
    withSonarQubeEnv(credentialsId: 'sonar-token-new'){
        sh """
            ${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=sonarqube \
            -Dsonar.sources=. \
            -Dsonar.host.url=http://localhost:9000 \
            -Dsonar.token=sqa_ef6b785d0c53d831a09f0670e3bc76c390659a3e
        """
    }

    timeout(time: 1, unit: 'MINUTES') {
        if (abortPipeline) {
            error 'Marcko - Abort pipeline.'       
        } else {
            echo 'Marcko - Pipeline OK'
        }
    }

}