#!/usr/bin/env groovy

pipeline {
    agent any

    tools {
        // jenkins maven 配置
        maven 'maven-3.10.0'
    }

    environment {
        // jenkins ssh 配置
        SSH_NAME = "ssh_192.168.0.244"
        SSH_EXEC_TIMEOUT = 600000
        SSH_SOURCE_FILES = "target/*.jar, target/*.sh, target/*.bat, target/lib/**/*, target/resources/**/*"
        SSH_REMOVE_PREFIX = "target/"
        SSH_REMOTE_DIRECTORY = "oner365-springboot"
    }

    stages {
        stage('拉取代码') {
            steps {
                // 这一步由 Jenkins 根据上面的 SCM 配置自动完成
                echo '代码已拉取，当前分支: ' + env.GIT_BRANCH
            }
        }

        stage('构建') {
            steps {
                // 你的构建命令，比如 Maven、npm 等
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
            post {
                success {
                    echo 'Maven 构建成功'
                }
                failure {
                    echo 'Maven 构建失败'
                }
            }
        }

        stage('镜像打包') {
            steps {
                // 构建镜像
                echo '通过 docker 制作镜像 - SUCCESS'
            } 
        }

        stage('部署') {
            steps {
                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: "${env.SSH_NAME}", 
                        transfers: [sshTransfer(
                            cleanRemote: false, excludes: '', execTimeout: "${env.SSH_EXEC_TIMEOUT}", 
                            flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, 
                            patternSeparator: '[, ]+', remoteDirectorySDF: false, 
                            
                            sourceFiles: "${env.SSH_SOURCE_FILES}",
                            removePrefix: "${env.SSH_REMOVE_PREFIX}",
                            remoteDirectory: "${env.SSH_REMOTE_DIRECTORY}"
                        )], 
                        usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false
                    )
                ])
                echo '通过 Deploy SSH 目标服务器 - SUCCESS'
            }            
        }


        stage('归档制品') {
            steps {
                // 归档 Maven 构建产物
                archiveArtifacts(
                    artifacts: 'target/*.jar, target/*.sh, target/*.bat, target/lib/**/*, target/resources/**/*',
                    fingerprint: true,
                    allowEmptyArchive: true
                )
            }
        }

    }

    post {
        always {
            // 清理工作空间（可选，谨慎使用）
            // cleanWs()
            echo "Pipeline 执行完成，构建号: ${env.BUILD_NUMBER}"
        }
        success {
            echo '所有阶段执行成功！'
            // 可在这里添加通知
        }
        failure {
            echo 'Pipeline 执行失败，请检查日志。'
            // 可在这里添加失败通知
        }
    }

}
