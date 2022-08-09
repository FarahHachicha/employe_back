pipeline{
       environment{
              registryCredential = 'dockerHub'
              dockerimagename = 'farahhachicha/jenkins'
              dockerImage = ''
       }
       agent any {
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
                                          archiveArtifacts 'target/*.jar
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
                                 dockerImage = docker.build dockerimagename
                            }
                            
                     }
                     stage ('Pushing Image') {
                            steps{
                                   docker.withRegistry ('http://registry.hub.docker.com',registryCredential){
                                           dockerImage.push("latest")
                                   }
                            }
                            
                     }
              }
       }
}
