pipeline{
       environment{
              dockerimagename = "farahhachicha/jenkins"
              dockerImage = ""
       }
       agent any

       tools{
              maven 'maven'
             // scannerHome 'sonarqube'

       }
       stages{
              stage('Build'){
                     steps{
                            sh 'mvn compile'
                            echo " Build success"
                     }
              }
              stage('Unit Test'){
                     steps{
                            sh 'mvn test'
                            echo " UNit test success"
                     }
                     post{
                           always{
                                  junit 'target/failsafe-reports/*.xml'
                            }
                     }


              }
              stage ('Integration Test'){
                     steps {
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
                  
                    echo "sonar"
        

              }

              }

              }
         
              stage('Build image'){
                     steps{
                            script{
                                   dockerImage = docker.build dockerimagename
                            }
                     }
              }
              stage ('Pushing Image'){
                     environment{
                            registryCredential = 'dockerHub'
                     }
                     steps{
                            script{
                                   docker.withRegistry ('http://registry.hub.docker.com', registryCredential){
                                          dockerImage.push("latest")
                                   }
                            }
                     }
              }
              

       }

}
