#!/usr/bin/env groovy
//apm install language-groovy
//https://jenkins.io/doc/book/pipeline/getting-started
//http://localhost:8080/pipeline-syntax
//http://localhost:8080/pipeline-syntax/globals
node {
    echo "hello world"
}

pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'ls -la' 
            }
        }
    }
}
