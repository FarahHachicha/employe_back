pipeline{
       environment{
              registryCredential = 'dockerHub'
              dockerimagename = 'farahhachicha/jenkinsback'
              dockerImage = ''
       }
       agent any 
       tools {
              maven 'maven'
        }
       stages{
              stage ('Build') {
                     steps{
                            sh 'mvn compile'
                            echo ' Build success '
                     }
              }
              stage ('Test') {
                     steps{
                                   
                            sh 'mvn verify -Dsurefire.skip=true'
                            echo " Integration test success"
                     }
                     post{
                            always{
                                   junit (testResults:'target/failsafe-reports/*.xml',allowEmptyResults: true)
                            }
                            success{
                                   stash(name : 'artifact' , includes :'target/*.jar')
                                   stash(name : 'pom' , includes :'pom.xml')
                                   archiveArtifacts 'target/*.jar'
                            }
                                   
                     }
              }
                   
              stage ('SonarQube Analysis '){
                     steps{
                            withSonarQubeEnv('sonarqube'){
                                   sh "mvn clean verify sonar:sonar -Dsonar.projectKey=demoapp-project"
                            }
                     }
              }
              stage ('Build Docker Image'){
                     steps{
                            script{ 
                                dockerImage= docker.build dockerimagename
                            }           
                     }                         
              }
              stage ('Push Docker Image') {
                     steps{
                            script{
                                    docker.withRegistry ('http://registry.hub.docker.com/',registryCredential){
                                        dockerImage.push("latest")
                                }
                            }
                     }
                            
              }
              stage ('Remove Docker Image'){
                     steps{
                            script{
                                   sh ' docker rmi jenkinsback '
                                   sh ' docker rmi farahhachicha/jenkinsback '
                            }
                     }
              }
              stage ('Deploy MySQL') {
                     steps{
                            script {
                                   kubernetesDeploy(configs: "mysql-deploy.yaml", kubeconfigId: "kube")
                            }
                     }
              }
              stage ('Deploy Application'){
                     steps{
                            script {
                                    kubernetesDeploy(configs: "app-deploy.yaml", kubeconfigId: "kube")

                            }
                     }
              }
       }
}
