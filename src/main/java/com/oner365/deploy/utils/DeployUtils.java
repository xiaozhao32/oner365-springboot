package com.oner365.deploy.utils;

import ch.ethz.ssh2.*;
import com.oner365.common.exception.ProjectRuntimeException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安装部署工具类
 *
 * @author zhaoyong
 *
 */
public class DeployUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeployUtils.class);

  public static final int POSIX_PERMISSIONS = 755;

  private DeployUtils() {

  }

  public static boolean isMac() {
    String os = System.getProperty("os.name");
    return !os.toLowerCase().startsWith("win");
  }

  /**
   * 创建连接
   *
   * @param ip   ip
   * @param port 端口
   * @return Connection 连接对象
   */
  public static Connection getConnection(String ip, int port) {
    try {
      /* Connection */
      Connection con = new Connection(ip, port);
      con.connect();
      return con;
    } catch (IOException e) {
      LOGGER.error("getConnection error:", e);
      throw new ProjectRuntimeException();
    }
  }

  /**
   * 获取Session
   *
   * @param con 连接对象
   * @return Session Session对象
   */
  public static Session getSession(Connection con) {
    try {
      return con.openSession();
    } catch (IOException e) {
      LOGGER.error("getSession error:", e);
      throw new ProjectRuntimeException();
    }
  }

  public static SFTPv3Client getClient(Connection con) {
    try {
      return new SFTPv3Client(con);
    } catch (IOException e) {
      LOGGER.error("getSFTPv3Client error:", e);
      throw new ProjectRuntimeException();
    }
  }

  /**
   * 关闭连接
   *
   * @param con        连接对象
   * @param session    Session对象
   * @param sftpClient sftpClient
   */
  public static void close(Connection con, Session session, SFTPv3Client sftpClient) {
    if (sftpClient != null) {
      sftpClient.close();
    }

    if (session != null) {
      session.close();
    }

    if (con != null) {
      con.close();
    }
  }

  /**
   * 验证帐号
   *
   * @param con      连接对象
   * @param user     账号
   * @param password 密码
   * @return boolean
   */
  public static boolean auth(Connection con, String user, String password) {
    boolean result = false;
    try {
      result = con.authenticateWithPassword(user, password);
    } catch (IOException e) {
      LOGGER.error("auth error:", e);
    }
    return result;
  }

  /**
   * 调用方式
   *
   * @param commands 执行命令
   * @return List<List<String>>
   */
  public static List<List<String>> execCommand(Connection con, List<String> commands) {
    List<List<String>> result = new ArrayList<>();
    try {
      for (String s : commands) {
        LOGGER.info("> {}", s);
        Session session = getSession(con);
        session.execCommand(s);

        InputStream eis = new StreamGobbler(session.getStderr());
        List<String> list = IOUtils.readLines(eis, Charset.defaultCharset());
        eis.close();

        if (list.isEmpty()) {
          InputStream is = new StreamGobbler(session.getStdout());
          list = IOUtils.readLines(is, Charset.defaultCharset());
          is.close();
        }
        result.add(list);
        close(null, session, null);
      }
    } catch (Exception e) {
      List<String> list = new ArrayList<>();
      list.add("ssh exec command error.");
      result.add(list);
      LOGGER.error("execCommand error:", e);
    }
    return result;
  }

  /**
   * 执行命令
   *
   * @param commands 命令
   * @return List<String>
   */
  public static List<String> execExecute(List<String> commands) {
    if (commands == null) {
      return Collections.emptyList();
    }

    return commands.stream().map(DeployUtils::execExecute).collect(Collectors.toList());
  }

  /**
   * 执行命令
   *
   * @param command 命令
   * @return String
   */
  public static String execExecute(String command) {
    if (command == null) {
      return null;
    }

    try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
      CommandLine commandLine = CommandLine.parse(command);

      DefaultExecutor exec = new DefaultExecutor();
      PumpStreamHandler streamHandler = new PumpStreamHandler(outStream);
      exec.setStreamHandler(streamHandler);
      exec.execute(commandLine);
      return outStream.toString(Charset.defaultCharset().name());
    } catch (IOException e) {
      LOGGER.error("execExecute error:", e);
    }
    return null;
  }

  /**
   * 非阻塞 执行命令
   *
   * @param commands 命令
   */
  public static void execExecuteCommand(List<String> commands) {
    if (commands == null) {
      return;
    }

    commands.forEach(DeployUtils::execExecuteCommand);
  }

  /**
   * 非阻塞 执行命令
   *
   * @param command 命令
   */
  public static void execExecuteCommand(String command) {
    if (command == null) {
      return;
    }

    try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
      CommandLine commandLine = CommandLine.parse(command);

      DefaultExecutor exec = new DefaultExecutor();
      DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();

      PumpStreamHandler streamHandler = new PumpStreamHandler(outStream);
      exec.setStreamHandler(streamHandler);
      exec.execute(commandLine, handler);
      handler.waitFor();
    } catch (IOException e) {
      LOGGER.error("IOException execExecuteCommand error:", e);
    } catch (InterruptedException e) {
      LOGGER.error("InterruptedException execExecuteCommand error:", e);
      Thread.currentThread().interrupt();
    }
  }

  public static void uploadFileMap(Connection con, String[] localFiles, String remoteTargetDirectory) {
    try {
      SFTPv3Client sftpClient = new SFTPv3Client(con);
      boolean isDirExists = forceDir(sftpClient, remoteTargetDirectory);
      LOGGER.info("> Directory: {} is not create: {}", remoteTargetDirectory, isDirExists);
      SCPClient scpClient = con.createSCPClient();
      LOGGER.info("> {} -> {}", localFiles, remoteTargetDirectory);
      scpClient.put(localFiles, remoteTargetDirectory);

      sftpClient.close();
    } catch (IOException e) {
      LOGGER.error("uploadFileMap error:", e);
    }
  }

  /**
   * 查询目录
   *
   * @param sftpClient 对象
   * @param directory  目录
   * @return boolean
   */
  public static boolean ls(SFTPv3Client sftpClient, String directory) {
    try {
      sftpClient.ls(directory);
      return true;
    } catch (IOException e) {
      // not exception
    }
    return false;
  }

  /**
   * 查询目录
   *
   * @param sftpClient 对象
   * @param directory  目录
   * @return boolean
   */
  @SuppressWarnings("unchecked")
  public static List<String> directoryList(SFTPv3Client sftpClient, String directory) {
    try {
      List<SFTPv3DirectoryEntry> vector = sftpClient.ls(directory);
      return vector.stream().map(c -> c.filename).collect(Collectors.toList());
    } catch (IOException e) {
      LOGGER.error("Error directoryList:", e);
    }
    return Collections.emptyList();
  }

  /**
   * 查询目录
   *
   * @param ip        ip
   * @param port      端口
   * @param user      账号
   * @param password  密码
   * @param directory 目录
   * @return boolean
   */
  @SuppressWarnings("unchecked")
  public static List<SFTPv3DirectoryEntry> directoryList(String ip, int port, String user, String password,
      String directory) {
    List<SFTPv3DirectoryEntry> result = new ArrayList<>();
    try {
      Connection con = getConnection(ip, port);
      SFTPv3Client sftpClient = null;
      if (DeployUtils.auth(con, user, password)) {
        sftpClient = getClient(con);
        result = sftpClient.ls(directory);
      }
      DeployUtils.close(con, null, sftpClient);
    } catch (IOException e) {
      LOGGER.error("directoryList error:", e);
    }
    return result;
  }

  /**
   * 创建目录
   *
   * @param sftpClient 对象
   * @param directory  目录
   * @return boolean
   */
  public static boolean forceDir(SFTPv3Client sftpClient, String directory) {
    boolean result = ls(sftpClient, directory);
    if (!result) {
      try {
        sftpClient.mkdir(directory, POSIX_PERMISSIONS);
        return true;
      } catch (IOException e) {
        LOGGER.error("Error mkdir:", e);
      }
    }
    return false;
  }

}
