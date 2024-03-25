def call(boolean abortPipeline, boolean qgResult){
    
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

    timeout(time: 5, unit: 'MINUTES') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            qgResult = true
            error "Pipeline aborted due to quality gate failure: ${qg.status}"
        }

        waitForQualityGate abortPipeline: true
        if (abortPipeline) {
            error 'Marcko - Abort pipeline.'       
        } else {
            echo 'Marcko - Pipeline OK'
        }
    }

}