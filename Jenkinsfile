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
              stage ('Unit test') {
                     steps{
                            sh 'mvn test'
                            echo " Unit test success"
                     }
              } 
              stage ('Integration test') {
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
                   
              stage ('SonarQube'){
                     steps{
                            withSonarQubeEnv('sonarqube'){
                                   sh "mvn clean verify sonar:sonar -Dsonar.projectKey=demoapp-project"
                            }
                     }
              }
              stage ('Build Image'){
                     steps{
                            script{
                                
                                   sh 'docker build -t jenkins .'
                            }
                          
                     }
                            
              }
              stage ('Pushing Image') {
                     steps{
                            script{
                                   sh 'docker login -u farahhachicha -p dckr_pat_DAFLAXhhIzvM8VFy_VwetgStuaA'
                                   sh 'docker tag jenkins farahhachicha/jenkins '
                                   sh 'docker push farahhachicha/jenkins '
                                  // docker.withRegistry ('http://registry.hub.docker.com/',registryCredential){
                                        //  dockerImage.push("latest")
                                   //}
                            }
                     }
                            
              }
              stage ('Pull Image'){
                     steps{
                            script{
                                   sh ' docker pull farahhachicha/jenkins '
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
