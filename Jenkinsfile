pipeline{
       environment{
              registryCredential = 'dockerHub'
             // DOCKERHUB_CREDENTIALS=credentials('dockerHub')
              dockerimagename = 'devops-integration:0.0.1-SNAPSHOT'
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
                  
                 //   echo "sonar"
                  // sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=6be0f04e6c06ea44b7047838d04ea07882f576d5'
               
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=demoapp-project"
    
        

              }

              }

              }
         
             stage('Build image'){
                     steps{
                            script{
                               
                                 //  dockerImage = docker.build dockerimagename
                                    sh 'docker build -t farahhachicha/devops-integration:0.0.1-SNAPSHOT .'
                            }
                     }
              }
               stage ('Pushing Image'){
                 
          
                     environment{
                           registryCredential = 'dockerHub'
                     }
                     steps{
                            script{
                                // docker.withRegistry (registryCredential){
                                    //    dockerImage.push("0.0.1-SNAPSHOT")
                                          sh 'docker login -u farahhachicha -p dckr_pat_DAFLAXhhIzvM8VFy_VwetgStuaA'
                                          sh 'docker push farahhachicha/devops-integration:0.0.1-SNAPSHOT '
                                //  }
                            }
                     }
              }
              stage('Pull image'){
                    steps{
                           script{
                    sh 'docker pull farahhachicha/devops-integration:0.0.1-SNAPSHOT'
                           }}}
              stage('Deploy Mysql') {
      steps {
        script {
              
            // echo "okk"
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
