def call(String serverid,String artifactorypattern,String repo)
   {
   def SERVER_ID = "${serverid}"
   def server = Artifactory.server SERVER_ID
   def uploadSpec =
   """
    {
    "files": [
        {
            "pattern": "${artifactorypattern}",
            "target": "${repo}/${BUILD_NUMBER}/"
        }
      ]
    }
    """
    def buildInfo = Artifactory.newBuildInfo() 
    buildInfo.env.capture = true 
    buildInfo=server.upload(uploadSpec) 
    server.publishBuildInfo(buildInfo) 
   }
