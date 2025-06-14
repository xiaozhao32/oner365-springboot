package com.oner365.deploy.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.DeployServer;
import com.oner365.deploy.entity.ServerEntity;

import ch.ethz.ssh2.Connection;

/**
 * 部署工具类
 *
 * @author zhaoyong
 */
public class DeployMethod {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployMethod.class);

    private static final String FILE_LIB = "lib";

    private static final String FILE_TARGET = "target";

    private static final String FILE_RESOURCES = "resources";

    private static final String PARAM_SERVICE_NAME = "SERVICE_NAME=";

    private static final String PARAM_VERSION = "VERSION=";

    private static final String PARAM_ACTIVE = "ACTIVE=";

    private static final String PARAM_RESOURCE_NAME = "RESOURCE_NAME";

    private DeployMethod() {
    }

    /**
     * 停止服务
     * @param con 连接对象
     * @param targetServer 目标服务
     */
    public static void stop(Connection con, String targetServer) {
        // kill tomcat
        String cmd = "kill -9 `cat " + targetServer + ".pid 2>/dev/null` 2>/dev/null || true";
        execCommands(con, Collections.singletonList(cmd));
    }

    /**
     * 启动tomcat服务
     * @param con 连接对象
     * @param targetServer 目标服务
     */
    public static void start(Connection con, String targetServer) {
        // tomcat启动找不到java_home,需要设置 ln -s /opt/jdk1.8/bin/java /bin/java
        String cmd = targetServer + "/bin/startup.sh";
        execCommands(con, Collections.singletonList(cmd));
    }

    /**
     * 上传文件到目标服务器
     */
    public static void deploy(DeployServer server, String localFile, String targetPath) {
        // upload
        String a1 = "scp -P " + server.getPort() + " -r " + localFile + " " + server.getUsername() + "@"
                + server.getIp() + PublicConstants.COLON + targetPath;
        LOGGER.info("> {}", a1);
        DeployUtils.execExecute(a1);
    }

    /**
     * 执行命令
     * @param con 连接对象
     * @param commands 命令
     */
    public static void execCommands(Connection con, List<String> commands) {
        List<List<String>> execList = DeployUtils.execCommand(con, commands);
        execList.stream().flatMap(Collection::stream).forEach(s -> LOGGER.info("> {}", s));
    }

    /**
     * 打包部署到指定 目录中
     * @param deployEntity 部署对象
     */
    public static void deployNative(DeployEntity deployEntity) {
        deployEntity.getProjects().forEach(projectName -> {
            // jar包全路径
            String path = deployEntity.getLocation() + File.separator + projectName + File.separator + FILE_TARGET
                    + File.separator + projectName + "-" + deployEntity.getVersion() + "." + deployEntity.getSuffix();
            String resourcePath = deployEntity.getLocation() + File.separator + projectName + File.separator
                    + FILE_TARGET + File.separator + FILE_RESOURCES;
            String libPath = deployEntity.getLocation() + File.separator + projectName + File.separator + FILE_TARGET
                    + File.separator + FILE_LIB;
            // 目标目录
            String targetPath = deployEntity.getName();
            // 拷贝相关文件
            DataUtils.createFolder(targetPath);
            DataUtils.copyFile(path, targetPath);
            DataUtils.copyDirectory(resourcePath, targetPath);
            DataUtils.copyDirectory(libPath, targetPath);

            // 制作 Linux 启动脚本
            URL shUrl = DeployMethod.class.getResource("/service/start.sh");
            if (shUrl != null) {
                String readFile = shUrl.getPath();
                String writeFile = targetPath + File.separator + "start.sh";
                Map<String, Object> items = new HashMap<>(3);
                items.put(PARAM_SERVICE_NAME, PARAM_SERVICE_NAME + projectName);
                items.put(PARAM_VERSION, PARAM_VERSION + deployEntity.getVersion());
                items.put(PARAM_ACTIVE, PARAM_ACTIVE + deployEntity.getActive());
                DataUtils.replaceContextFileCreate(readFile, writeFile, items);
            }
            URL stopUrl = DeployMethod.class.getResource("/service/stop.sh");
            if (stopUrl != null) {
                String readFile = stopUrl.getPath();
                String writeFile = targetPath + File.separator + "stop.sh";
                Map<String, Object> items = new HashMap<>(3);
                items.put(PARAM_SERVICE_NAME, PARAM_SERVICE_NAME + projectName);
                DataUtils.replaceContextFileCreate(readFile, writeFile, items);
            }

            // 制作 Windows 启动脚本
            URL batUrl = DeployMethod.class.getResource("/service/start.bat");
            if (batUrl != null) {
                String readFile = batUrl.getPath();
                String writeFile = targetPath + File.separator + "start.bat";
                Map<String, Object> items = new HashMap<>(1);
                items.put(PARAM_RESOURCE_NAME,
                        projectName + "-" + deployEntity.getVersion() + "." + deployEntity.getSuffix());
                DataUtils.replaceContextFileCreate(readFile, writeFile, items);
            }
        });

    }

    /**
     * 部署到所有服务器
     * @param deployEntity 部署对象
     * @param serverEntity 服务器对象
     */
    public static void deployServer(DeployEntity deployEntity, ServerEntity serverEntity) {
        try {
            // 多个目标进行部署
            // get connection
            // 认证
            // close
            serverEntity.getServerList().forEach(server -> {
                Connection con = DeployUtils.getConnection(server.getIp(), server.getPort());
                boolean auth = DeployUtils.auth(con, server.getUsername(), server.getPassword());
                LOGGER.info("Auth : {}", auth);
                if (auth) {
                    List<String> commands = deploy(con, server, deployEntity, serverEntity.getServerName());
                    DeployMethod.execCommands(con, commands);
                }
                DeployUtils.close(con, null, null);
            });
        }
        catch (Exception e) {
            LOGGER.error("deployServer error:", e);
        }
    }

    /**
     * 部署到单台服务器
     * @param con 连接对象
     * @param server 服务器对象
     * @param deployEntity 部署对象
     * @param targetRoot 部署根目录
     * @return List<String> 返回执行脚本列表
     */
    public static List<String> deploy(Connection con, DeployServer server, DeployEntity deployEntity,
            String targetRoot) {
        List<String> commands = new ArrayList<>(deployEntity.getProjects().size());
        deployEntity.getProjects().forEach(projectName -> {
            // 上传的文件
            String localFile = deployEntity.getName() + File.separator + projectName + "-" + deployEntity.getVersion()
                    + "." + deployEntity.getSuffix();
            // 上传的路径
            String targetPath = targetRoot + PublicConstants.DELIMITER + projectName + PublicConstants.DELIMITER;
            // 配置文件
            String resourcesFile = deployEntity.getName() + File.separator + FILE_RESOURCES;
            // 依赖包上传到lib
            if (DeployUtils.isMac()) {
                // mac scp方式
                DeployMethod.deploy(server, deployEntity.getName(), targetRoot);
                deployMac(server, deployEntity, targetRoot);
            }
            else {
                // windows scp方式
                DeployUtils.uploadFileMap(con, new String[] { localFile }, targetPath);
                deployWindows(con, deployEntity, targetPath, resourcesFile, targetRoot);
            }
            // 准备执行的命令
            commands.add("chmod 750 " + targetRoot + PublicConstants.DELIMITER + projectName + PublicConstants.DELIMITER
                    + "*.sh");
        });
        return commands;
    }

    private static void deployMac(DeployServer server, DeployEntity deployEntity, String targetRoot) {
        DeployMethod.deploy(server, deployEntity.getName() + File.separator + FILE_LIB,
                targetRoot + PublicConstants.DELIMITER);
    }

    private static void deployWindows(Connection con, DeployEntity deployEntity, String targetPath,
            String resourcesFile, String targetRoot) {
        File file = DataUtils.getFile(resourcesFile);
        if (file != null) {
            Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(f -> !f.isDirectory())
                .forEach(f -> DeployUtils.uploadFileMap(con, new String[] { f.getPath() },
                        targetPath + FILE_RESOURCES + PublicConstants.DELIMITER));
        }
        DeployUtils.uploadFileMap(con, new String[] { deployEntity.getName() + File.separator + FILE_LIB },
                targetRoot + PublicConstants.DELIMITER);
    }

}
