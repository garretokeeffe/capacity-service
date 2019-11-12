pipeline {
  agent any
 
  tools { 
     maven 'Maven_3_9_9'
  }

  
  environment {
     BRANCH_NAME = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
     CLUSTER_NAME = "https://console.rhos.agriculture.gov.ie"
	 PROJECT_DEV="fisheries-dev" 
	 GITLAB_PROJECT_PATH="https://rhosgitlab1.agriculture.gov.ie/api/v4/projects/fisheries-development%2Fservices%2Fcapacity-service"
	 CONFIG_SERVER="http://config-server-fisheries-dev.apps.rhos.agriculture.gov.ie"
	 OPENSHIFT_PROJECT_NAME="capacity-service"
	 sonar_project_name="capacity-service"
	 JAR="capacity-service-0.0.1-SNAPSHOT.jar"
     recipientList='joydip.kumar@agriculture.gov.ie,paul.forde@agriculture.gov.ie,Garret.OKeeffe@agriculture.gov.ie,Stephen.McCosker@agriculture.gov.ie'
	 
  }
	
 post {
  
		//Send email and send the status to gitlab on success and failure at the end of build
  
      failure {
		 withCredentials([string(credentialsId: 'token', variable: 'token')]) {
			sh 'curl --request POST --header "PRIVATE-TOKEN:  ${token}" $GITLAB_PROJECT_PATH"/statuses/$(git rev-parse HEAD)?state=failed"'
		}
		
		emailext body: "   ${env.JOB_NAME} build #${env.BUILD_NUMBER}  Status : ${currentBuild.currentResult} \n More info at: ${env.BUILD_URL} ",
                recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']],
				to: recipientList,
                subject: "Jenkins Build Job ${env.JOB_NAME} : ${currentBuild.currentResult} "
      }
      success {
		 withCredentials([string(credentialsId: 'token', variable: 'token')]) {
	    	sh 'curl --request POST --header "PRIVATE-TOKEN: ${token}" $GITLAB_PROJECT_PATH"/statuses/$(git rev-parse HEAD)?state=success"'
		}
		
		   script {
                if( BRANCH_NAME == 'development' ){
                   emailext body: "  ${env.JOB_NAME} build #${env.BUILD_NUMBER}  Status : ${currentBuild.currentResult} \n More info at: ${env.BUILD_URL} ",
					recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']],
					to: recipientList,
					subject: "Jenkins Build Job ${env.JOB_NAME} : ${currentBuild.currentResult} "
                }
               
            }
	  }
	  
    }


stages {
     

		// update the running status to gitlab commit 
		stage('Initiating'){
		  steps {
		   withCredentials([string(credentialsId: 'token', variable: 'token')]) {
				sh 'curl --request POST --header "PRIVATE-TOKEN: ${token}" $GITLAB_PROJECT_PATH"/statuses/$(git rev-parse HEAD)?state=running"'
			}
		  }
		
		}
  
		stage('Build') {
			steps {
					
					 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'IFIS_DB_CRED',
						usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
						configFileProvider([configFile(fileId: 'fisheries-settings', variable: 'MAVEN_SETTINGS')]) {
								echo "Config: $MAVEN_SETTINGS"
								sh   'mvn -s $MAVEN_SETTINGS clean package  -Ddatabase.user=$USERNAME -Ddatabase.password=$PASSWORD -Dspring.profiles.active=dev -Dspring.cloud.config.uri=$CONFIG_SERVER'
						}
					}
			}
		}
	
		/*
			
		 stage('Sonarqube') {
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


		 stage('Create Image Builder') {
			  when {
				expression {
				  openshift.withCluster(CLUSTER_NAME) {
					  openshift.withProject( PROJECT_DEV ) {
							return BRANCH_NAME == 'development' && currentBuild.currentResult !='FAILURE' && !openshift.selector("bc",OPENSHIFT_PROJECT_NAME).exists();
							}
				  }
				}
			  }
			  steps {
				script {
				  openshift.withCluster(CLUSTER_NAME) {
					  openshift.withProject( PROJECT_DEV ) {
							openshift.newBuild("--name=${OPENSHIFT_PROJECT_NAME}", "--image-stream=redhat-openjdk18-openshift:1.1", "--binary")
							}
				  }
				}
			  }
		}

	
		stage('Build Image') {
	
			when{
				expression {
					return BRANCH_NAME == 'development' && currentBuild.currentResult !='FAILURE' ;
				}
		   }
          
        steps {
           echo "Pushing The JAR Into OpenShift OpenJDK-Container"
			echo "${currentBuild.currentResult}"
            script {
                openshift.withCluster( CLUSTER_NAME ) {
                    openshift.withProject( PROJECT_DEV ) {
                        openshift.selector("bc", OPENSHIFT_PROJECT_NAME).startBuild("--from-file=target/" + JAR,"--wait")
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
			
		stage('Tagging the Image') {
		
			when{
				expression {
					return BRANCH_NAME == 'development' && currentBuild.currentResult !='FAILURE' ;
				}
		    }
		  steps {
			script {
			  openshift.withCluster( CLUSTER_NAME ) {
			      openshift.withProject( PROJECT_DEV ) {
						openshift.tag(OPENSHIFT_PROJECT_NAME+":latest", OPENSHIFT_PROJECT_NAME+":dev")
					}
			  }
			}
		  }
		}
		    stage(' Deploying in Dev ') {
		 	when{
				expression {
					openshift.withCluster( CLUSTER_NAME ) {
						openshift.withProject( PROJECT_DEV ) {
							return BRANCH_NAME == 'development' && currentBuild.currentResult !='FAILURE' && !openshift.selector("dc",OPENSHIFT_PROJECT_NAME).exists();
						}
					}
				}
		   }

		 
		  steps {
			script {
			  openshift.withCluster( CLUSTER_NAME ) {
			      openshift.withProject( PROJECT_DEV ) {
				  
						openshift.newApp(OPENSHIFT_PROJECT_NAME+":latest", "--name="+OPENSHIFT_PROJECT_NAME).narrow('svc').expose()
						withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'IFIS_DB_CRED',
							usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
							openshift.raw( 'set env dc/$OPENSHIFT_PROJECT_NAME', 'database.user=$USERNAME -e database.password=$PASSWORD -e spring.profiles.active=dev -e spring.cloud.config.uri=$CONFIG_SERVER ' )
						}
						
					}
			  }
			}
		  }
		}
		
		stage ('Verifying deployment'){
		
			 when {
				expression {
				  openshift.withCluster( CLUSTER_NAME ) {
					  openshift.withProject( PROJECT_DEV ) {
							return BRANCH_NAME == 'development' && currentBuild.currentResult !='FAILURE' && openshift.selector('dc', OPENSHIFT_PROJECT_NAME).exists() 
						}
				  }
				}
			  }
			  steps {
				script {
				  openshift.withCluster(CLUSTER_NAME) {
						openshift.withProject( PROJECT_DEV ){
							
					    	def latestDeploymentVersion = openshift.selector('dc',OPENSHIFT_PROJECT_NAME).object().status.latestVersion
							def rc = openshift.selector('rc', OPENSHIFT_PROJECT_NAME+"-${latestDeploymentVersion}")
							   timeout (time: 10, unit: 'MINUTES') {
									rc.untilEach(1){
										def rcMap = it.object()
										return (rcMap.status.replicas.equals(rcMap.status.readyReplicas))
									}
								}
						}
					}
				  
				  
				  
				}
			  }
		
		}
	
	
    }

}

