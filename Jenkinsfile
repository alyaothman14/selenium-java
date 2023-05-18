pipeline{
    agent any
    stages{
        stage("Clean Up") //good practice to add a cleanup step at the begining to ensure env is clean
        {
            steps{
                echo "Running ${env.BUILD_NUMBER}"
                deleteDir()
            }
        }
       
        stage("Test"){
            steps{
                dir("selenium-java"){
                    
                    sh "mvn clean test"
                }
            }
        }
        
    }
}