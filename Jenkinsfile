pipeline{
       environment{
              registryCredential = 'dockerHub'
             // DOCKERHUB_CREDENTIALS=credentials('dockerHub')
              dockerimagename = 'farahhachicha/jenkins:0.0.1-SNAPSHOT'
              dockerImage = ''
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
                                 //  sh ' sudo chmod 777 /var/run/docker.sock'
                                   dockerImage = docker.build dockerimagename
                                   // sh 'docker build -t farahhachicha/devops-integration .'
                            }
                     }
              }
              / stage ('Pushing Image'){
                     steps{
                            echo 'push success'
                     }
              }
                    /* environment{
                           registryCredential = 'dockerHub'
                     }
                     steps{
                            script{
                                   docker.withRegistry ('http://registry.hub.docker.com', registryCredential){
                                          dockerImage.push("0.0.1-SNAPSHOT")
                                   }
                            }
                     }
              }*/
              stage('Deploy Mysql') {
      steps {
        script {
             
          kubernetesDeploy(configs: "mysql-deploy.yaml", kubeconfigId: "kube")
   
        }
      }
    }
                      stage('Deploy Application') {
      steps {
        script {
             
          kubernetesDeploy(configs: "app-deploy.yaml", kubeconfigId: "kube")
   
        }
      }
    }
              

       }

}
