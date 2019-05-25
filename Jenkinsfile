@Library('DevopsLib')_
node (label:'Master'){

   def mvnHome

   stage('Preparation') { // for display purposes

           git 'https://github.com/SuriyaRaj/MVC.git '
             
      mvnHome = tool 'MAVEN_HOME'
	 

   }

   stage('Build') {

              if (isUnix()) {
                  

             sh "'${mvnHome}/bin/mvn' clean install sonar:sonar -Dmaven.test.failure.ignore clean package  -Dsonar.host.url=http://23.96.41.202:9000/   -Dsonar.login=6fe03b350d2b961468e5d1fc5866c4d7ad11ce56"
}}
   

   stage('Build docker image for war file'){

        sh "docker build -t suriaraj/project:${BUILD_NUMBER} ."

   }
stage('Deploy artifacts')
   {
    JfrogConf 'JFrog_Art1','./target/*.war'",'local-snapshot'
   }
} 
