pipeline{
       environment{
              registryCredential = 'dockerHub'
              dockerimagename = 'farahhachicha/jenkins'
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
            /*  stage ('Unit test') {
                     steps{
                            sh 'mvn test'
                            echo " Unit test success"
                     }
                     post{
                            always{
                                   junit (testResults:'target/surefire-reports/*.xml',allowEmptyResults: true)
                            }
                            success{
                                   stash(name : 'artifact' , includes :'target/*.jar')
                                   stash(name : 'pom' , includes :'pom.xml')
                                   archiveArtifacts 'target/*.jar'
                            }
              } }*/
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
                                
                                   sh 'docker build -t jenkinsback .'
                            }
                          
                     }
                            
              }
              stage ('Push Docker Image') {
                     steps{
                            script{
                                   sh 'docker login -u farahhachicha -p dckr_pat_DAFLAXhhIzvM8VFy_VwetgStuaA'
                                   sh 'docker tag jenkinsback farahhachicha/jenkinsback '
                                   sh 'docker push farahhachicha/jenkinsback '
                                  // docker.withRegistry ('http://registry.hub.docker.com/',registryCredential){
                                        //  dockerImage.push("latest")
                                  // //}
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
