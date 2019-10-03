

pipeline {
  agent any
 
  tools { 
     maven 'Maven_3_9_9'
  }
  environment {
     branchName = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
     clusterName = "https://console.rhos.agriculture.gov.ie"
	 project="fisheries-dev" 
	 gitlab_project_path="fisheries-development%2Fservices%2Fcapacity-service"
	 openshift_project_name="capacity-service"
	 sonar_project_name="capacity-service"
	 jar="capacity-service-0.0.1-SNAPSHOT.jar"
  }

  post {
      failure {
		 withCredentials([string(credentialsId: 'token', variable: 'token')]) {
        sh 'curl --request POST --header "PRIVATE-TOKEN:  ${token}" "https://rhosgitlab1.agriculture.gov.ie/api/v4/projects/"$gitlab_project_path"/statuses/$(git rev-parse HEAD)?state=failed"'
		}
      }
      success {
		 withCredentials([string(credentialsId: 'token', variable: 'token')]) {
	    	sh 'curl --request POST --header "PRIVATE-TOKEN: ${token}" "https://rhosgitlab1.agriculture.gov.ie/api/v4/projects/"$gitlab_project_path"/statuses/$(git rev-parse HEAD)?state=success"'
		}
	  }
	  
	     always {
            echo 'I will always say Hello again!'
            
            emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
            
        }
  
    }


stages {
     

	 
	stage('Initiating'){
	  steps {
	  
	   withCredentials([string(credentialsId: 'token', variable: 'token')]) {
	    	sh 'curl --request POST --header "PRIVATE-TOKEN: ${token}" "https://rhosgitlab1.agriculture.gov.ie/api/v4/projects/"$gitlab_project_path"/statuses/$(git rev-parse HEAD)?state=running"'
		}
	  }
	
	}
  
    
        
    stage('Build') {
        steps {
       
            configFileProvider([configFile(fileId: 'fisheries-settings', variable: 'MAVEN_SETTINGS')]) {
                echo "Config: $MAVEN_SETTINGS"
                sh   'mvn -s $MAVEN_SETTINGS clean package '
            }
           
        }
        
    }
	
	

    
 /*stage('Sonarqube') {
        environment {
            scannerHome = tool 'SonarQube Scanner'
        }
        steps {
            withSonarQubeEnv('sonarqube') {
                sh "${scannerHome}/bin/sonar-scanner  -Dsonar.projectName=${sonar_project_name} -Dsonar.projectVersion=1.0 -Dsonar.projectKey=${sonar_project_name} -Dsonar.sources=src/main/java/ -Dsonar.language=java -Dsonar.java.binaries=target/classes  "
            }
            
			script{
				timeout(time: 5, unit: 'MINUTES') {
				def qualitygate = waitForQualityGate()
				if (qualitygate.status != "OK") {
					error "Pipeline aborted due to quality gate coverage failure."
				}
			}
			}
        }
       
        
    }
 */
    stage('Deploy') {
	
		when{
		expression {
			return branchName == 'development';
		}
	   }
          
        steps {
           echo "Pushing The JAR Into OpenShift OpenJDK-Container"
            script {
                openshift.withCluster( clusterName ) {
                    openshift.withProject( project ) {
                        openshift.selector("bc", openshift_project_name).startBuild("--from-file=target/"+jar, "--wait")
                    }
               }
              }
          }
    	  
          post {
            success {
              archiveArtifacts artifacts: 'target/**.jar', fingerprint: true
            }
          }
        }
    }

  }
