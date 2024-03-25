def call(boolean abortPipeline, boolean qgResult, String branch ){
    
    def scannerHome = tool 'sonar-scanner'

    echo branch
    
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
        //def qg = waitForQualityGate()
        /*if (qg.status != 'OK') {
            qgResult = true
            error "Pipeline aborted due to quality gate failure: ${qg.status}"
        }*/
    }

    if (abortPipeline) {
        error 'Boolean variable - Abort pipeline by user'       
    } else {
        if(branch == 'master'){
            error 'Master branch selected, abort pipeline.'
        }
        
        if (branchName.startsWith('hotfix/')) {
            error 'Hotfix branch selected, abort pipeline.'        
        }
        
        
        echo 'Marcko - Pipeline OK'
    }

}

