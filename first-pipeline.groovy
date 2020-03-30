node {
    // This code work if jenkins is running on docker host or docker api exposed to jenkins agent.
	env.AWS_ECR_LOGIN=true
    def newApp
    //def registry = 'docker.com/tjilal/centos-web'
    def registry = "localhost:5000/centos-web"
    def registryCredential = ''
    //def registryCredential = 'docker-hub'
	
	stage('Git') {
		git 'https://github.com/talhajilal/node-js.git'
	}
	
	stage('Building image') {
        docker.withRegistry( 'https://' + registry, registryCredential ) {
		    def buildName = registry + ":$BUILD_NUMBER"
			newApp = docker.build buildName
			newApp.push()
        }
	}
	stage('Registring image') {
        docker.withRegistry( 'https://' + registry, registryCredential ) {
    		newApp.push 'latest'
        }
	}
    stage('Removing image') {
        sh "docker rmi $registry:$BUILD_NUMBER"
        sh "docker rmi $registry:latest"
    }
    
}
