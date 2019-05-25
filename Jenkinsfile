@Library('DevopsLiv')_
node(label:'master') {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/SuriyaRaj/MVC.git'
      // Get the Maven tool. 
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
     mvnHome = tool 'MAVEN_HOME'
   }

  stage('Build'){
   withSonarQubeEnv('SonarQube') {
     sh "'${mvnHome}/bin/mvn' clean install sonar:sonar -Dmaven.test.failure.ignore clean package  -Dsonar.host.url=http://23.96.41.202:9000/   -Dsonar.login=6fe03b350d2b961468e5d1fc5866c4d7ad11ce56"
   } 
}
   
   stage('Notify')
   {
   emailext (
      subject: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
      recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
   } 
   
   stage('Sonar Quality gate')
   {   withSonarQubeEnv('SonarQube') { 
      timeout(time: 1, unit: 'HOURS') { 
           def qg = waitForQualityGate() 
           if (qg.status != 'OK') {
             error "Pipeline aborted due to quality gate failure: ${qg.status}"
           }
        }
   }
   
   }
   stage('Build docker image for war file')
   {
     // sh "docker build -t suriaraj/project:${BUILD_NUMBER} ."
      docker.withRegistry('https://registry.hub.docker.com','dockerloign')
      {
      def image = docker.build("suriaraj/project:${BUILD_NUMBER}")
      image.push()
      }
      
   }
   
   stage('Deploy artifacts')
   {
    jfroglib "JFrogArt1","./target/*.war","local-snapshot"
   }
   
}
