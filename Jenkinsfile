#!/usr/bin/env groovy

def branchName = "oner365-springboot-2.1.0"
def credentialsId = "gitee auth id"
def gitUrl = "https://gitee.com/xiaozhao32/oner365-springboot.git"

def sshName = "sshServer"
def sshExecTimeout = 600000
def sshSourceFiles = "target/*.jar"
def sshRemovePrefix = "target/"
def sshRemoteDirectory = "oner365-springboot"
def sshExecCommand = "oner365-springboot/start.sh"

pipeline {
    agent any

    stages {
        stage('Pull Git') {
            steps {
            	checkout scmGit(
            		branches: [[name: "${branchName}"]], extensions: [], 
            		userRemoteConfigs: [
            			[credentialsId: "${credentialsId}", url: "${gitUrl}"]
            		]
            	)
                echo '拉取git仓库代码 - SUCCESS'
            }
        }

        stage('Maven Build') {
            steps {
            	sh 'mvn clean package -DskipTests'
                echo '通过 maven 构建项目 - SUCCESS'
            }            
        }

        stage('Docker Build') {
            steps {
            	sh 'mvn clean package -Dmaven.test.skip=true docker:build'
                echo '通过 docker 制作自定义镜像 - SUCCESS'
            }            
        }

        stage('Publish Server') {
            steps {
            	sshPublisher(publishers: [
            		sshPublisherDesc(
            			configName: "${sshName}", 
            			transfers: [sshTransfer(
            				cleanRemote: false, excludes: '', execTimeout: "${sshExecTimeout}", 
            				flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, 
            				patternSeparator: '[, ]+', remoteDirectorySDF: false, 
            				
            				sourceFiles: "${sshSourceFiles}",
            				removePrefix: "${sshRemovePrefix}",
            				remoteDirectory: "${sshRemoteDirectory}",
            				execCommand: "${sshExecCommand}",
            			)], 
            			usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false
            		)
            	])
                echo '通过 Publish Over SSH 目标服务器 - SUCCESS'
            }            
        }


    }
}
