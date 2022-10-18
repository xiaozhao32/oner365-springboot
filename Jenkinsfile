 
pipeline {
    agent {label 'master'}
    
    tools {
        maven 'maven'
    }
    
    environment {
    	GIT_AUTH = "79885bd0-30f0-013b-defd-1ab2cc80ca33"
		GIT_URL = "https://gitee.com/xiaozhao32/oner365-springboot.git"
		
		PROJECT_NAME = "oner365-springboot"
		PROJECT_VERSION = "2.0.2"
    }
 
    stages {
        stage('checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: "${GIT_AUTH}", url: "${GIT_URL}"]]])
            }
        }
        stage('clean package') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }
        stage('build') {
            steps {
                sh 'mvn docker:build'
            }
        }
        stage('run') {
            steps {
                sh '''
                	container_id=`docker ps|grep ${PROJECT_NAME}:${PROJECT_VERSION}|awk '{print $1}'`
                    if [ -n "${container_id}" ]; then
                    	docker rm -f "${container_id}"
                    fi
                    old_pid=`ps -ef|grep ${PROJECT_NAME}|grep ${PROJECT_VERSION}|grep -v grep|awk '{print $2}'`
                    if [[ -n $old_pid ]]; then
                        kill -9 $old_pid
                    fi
                    old_image=`docker images|grep ${PROJECT_NAME}|grep ${PROJECT_VERSION}`
                    if [[ -n $old_image ]]; then
                        old_image_id=`echo ${old_image}|awk '{print $3}'`
                        docker rmi -f ${old_image_id}
                    fi
                    sudo docker run -d --name ${PROJECT_NAME}-${PROJECT_VERSION} -p 8704:8704 --restart=always --privileged=true --restart=always ${PROJECT_NAME}:${PROJECT_VERSION}
                '''
            }
        }
        
    }
}
