package com.oner365.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.oner365.common.constants.PublicConstants;

/**
 * ????????????????????????
 *
 * @author liutao
 */
public class DataUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

  private static final String HEADER_UNKNOWN = "unknown";

  private static final LRUMap<String, Integer> CACHE_MAP = new LRUMap<>(100);

  private static final int BUFFER_SIZE = PublicConstants.BYTE_SIZE * 8;
  private static final int IP_LENGTH = 15;
  private static final int IP_PART = 4;
  private static final String IP_LOCALHOST = "0:0:0:0:0:0:0:1";

  public static final String PARENT_FILE = "..";
  public static final String EMPTY_JSON = "{}";
  public static final char C_BACKSLASH = '\\';
  public static final char C_DELIMITER_START = '{';
  public static final char C_DELIMITER_END = '}';
  public static final String C_QUARTER = ",";
  public static final String KB = "KB";
  public static final String MB = "MB";
  public static final String GB = "GB";

  private DataUtils() {

  }

  /**
   * ???????????????
   *
   * @param id  ??????
   * @param obj ??????
   * @return boolean
   */
  public static boolean judge(String id, Object obj) {
    Object object = obj;
    synchronized (object) {
      // ??????????????????
      if (CACHE_MAP.containsKey(id)) {
        // ????????????
        return false;
      }
      // ?????????????????????????????? ID
      CACHE_MAP.put(id, 1);
    }
    return true;
  }

  /**
   * ????????????
   *
   * @param clazz ???
   * @return List<Map<String, Object>>
   */
  public static List<Map<String, Object>> getBeanAnnotationCol(Class<?> clazz) {
    List<Map<String, Object>> list = new ArrayList<>();
    try {
      Field[] fields = clazz.getDeclaredFields();

      for (Field value : fields) {
        Field field = clazz.getDeclaredField(value.getName());
        Column column = field.getAnnotation(Column.class);
        Method[] methods = Column.class.getDeclaredMethods();
        Map<String, Object> map = new HashMap<>(10);
        map.put(Field.class.getSimpleName().toLowerCase(), field.getName());
        if (column != null) {
          for (Method method : methods) {
            map.put(method.getName(), method.invoke(column));
          }
        }
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        Method[] joinMethods = JoinColumn.class.getDeclaredMethods();
        if (joinColumn != null) {
          for (Method method : joinMethods) {
            map.put(method.getName(), method.invoke(joinColumn));
          }
        }
        list.add(map);
      }
    } catch (Exception e) {
      LOGGER.error("Error getBeanAnnotationCol: ", e);
    }
    return list;
  }

  /**
   * ????????????????????????
   *
   * @param fileName ????????????
   * @return String
   */
  public static String getExtension(String fileName) {
    if (fileName != null) {
      int i = fileName.lastIndexOf('.');
      if (i > 0 && i < fileName.length() - 1) {
        return fileName.substring(i + 1).toLowerCase();
      }
    }
    return "";
  }

  /**
   * ????????????
   *
   * @param folderPath ??????
   */
  public static void createFolder(String folderPath) {
    try {
      File myFilePath = new File(folderPath);
      if(!myFilePath.exists()) {
        FileUtils.forceMkdir(myFilePath);
      }
    } catch (Exception e) {
      LOGGER.error("Error createFolder:", e);
    }
  }

  /**
   * ????????????
   *
   * @param filePath    ????????????
   * @param fileContent ???????????? ??????????????? ??????
   */
  public static void createFile(String filePath, String fileContent) {
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        FileUtils.forceMkdir(new File(file.getParent()));
      }

      if (DataUtils.isEmpty(fileContent)) {
        boolean b = file.createNewFile();
        LOGGER.info("createFile: {}", b);
      } else {
        try (FileWriter fileWriter = new FileWriter(file); PrintWriter printWriter = new PrintWriter(fileWriter)) {
          printWriter.println(fileContent);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Error createFile:", e);
    }
  }

  /**
   * ????????????
   *
   * @param filePath   ???????????????
   * @param targetPath ??????????????????
   */
  public static void copyDirectory(String filePath, String targetPath) {
    try {
      File srcFile = new File(filePath);
      File targetFile = new File(targetPath);
      if (srcFile.exists()) {
        FileUtils.copyDirectoryToDirectory(srcFile, targetFile);
      }
    } catch (Exception e) {
      LOGGER.error("Error copyDirectory:", e);
    }
  }

  /**
   * ????????????
   *
   * @param filePath   ???????????????
   * @param targetPath ??????????????????
   */
  public static void copyFile(String filePath, String targetPath) {
    try {
      File srcFile = new File(filePath);
      File targetFile = new File(targetPath);
      if (srcFile.exists()) {
        FileUtils.copyFileToDirectory(srcFile, targetFile);
      }
    } catch (Exception e) {
      LOGGER.error("Error copyDirectory:", e);
    }
  }

  /**
   * ????????????
   *
   * @param filePath ????????????
   */
  public static void deleteFile(String filePath) {
    try {
      File file = new File(filePath);
      if (file.exists()) {
        FileUtils.forceDelete(file);
      }
    } catch (IOException e) {
      LOGGER.error("Error deleteFile:", e);
    }
  }

  /**
   * ??????????????????
   *
   * @param filePath ????????????
   * @param fileName ????????????
   * @return File ??????
   */
  public static File getFile(String filePath, String fileName) {
    return getFile(filePath + File.separator + fileName);
  }

  /**
   * ??????????????????
   *
   * @param filePath ????????????
   * @return File ??????
   */
  public static File getFile(String filePath) {
    // ????????????????????????
    if (StringUtils.contains(filePath, PARENT_FILE)) {
      return null;
    }

    File file = new File(filePath);
    if (file.exists()) {
      return file;
    }
    return null;
  }

  /**
   * ??????????????????
   *
   * @param filePath ????????????
   * @param fileName ????????????
   * @return FileOutputStream ??????
   * @throws FileNotFoundException ????????????
   */
  public static FileOutputStream getFileOutputStream(String filePath, String fileName) throws FileNotFoundException {
    createFolder(filePath);
    return getFileOutputStream(filePath + File.separator + fileName);
  }

  /**
   * ??????????????????
   *
   * @param filePath ????????????
   * @return FileOutputStream ??????
   * @throws FileNotFoundException ????????????
   */
  public static FileOutputStream getFileOutputStream(String filePath) throws FileNotFoundException {
    // ????????????????????????
    if (StringUtils.contains(filePath, PARENT_FILE)) {
      return null;
    }
    return new FileOutputStream(filePath);
  }

  /**
   * ?????????????????????
   *
   * @param f ????????????
   * @return String
   */
  public static String getFileName(String f) {
    if (f != null) {
      String fileName = f.replace("\\", PublicConstants.DELIMITER);
      if (fileName.lastIndexOf(PublicConstants.DELIMITER) > 0) {
        fileName = fileName.substring(fileName.lastIndexOf(PublicConstants.DELIMITER) + 1);
      }
      int i = fileName.lastIndexOf('.');
      if (i > 0 && i < fileName.length() - 1) {
        return fileName.substring(0, i);
      }
    }
    return "";
  }

  /**
   * ?????????????????????
   *
   * @param obj ?????????
   * @return String
   */
  public static String getString(Object obj) {
    String str = StringUtils.EMPTY;
    if (obj != null) {
      if (obj instanceof String) {
        str = (String) obj;
      } else {
        str = obj.toString();
      }
    }
    return str;
  }

  public static String format(String template, Object... params) {
    if (isEmpty(params) || isEmpty(template)) {
      return template;
    }
    return formatString(template, params);
  }

  /**
   * ????????????Object ????????????
   *
   * @param obj ??????
   * @return boolean
   */
  public static boolean isEmpty(Object obj) {
    if (obj == null) {
      return true;
    }

    if (obj instanceof Optional) {
      return !((Optional<?>) obj).isPresent();
    }
    if (obj instanceof CharSequence) {
      return ((CharSequence) obj).length() == 0;
    }
    if (obj.getClass().isArray()) {
      return Array.getLength(obj) == 0;
    }
    if (obj instanceof Collection) {
      return ((Collection<?>) obj).isEmpty();
    }
    if (obj instanceof Map) {
      return ((Map<?, ?>) obj).isEmpty();
    }

    // else
    return false;
  }

  /**
   * ?????????????????? ?????? Null
   *
   * @param str ?????????
   * @return String
   */
  public static String trimToNull(String str) {
    return isEmpty(str) ? null : str.trim();
  }

  /**
   * ?????????????????? ??????????????????
   *
   * @param str ?????????
   * @return String
   */
  public static String trimToEmpty(String str) {
    return isEmpty(str) ? "" : str.trim();
  }

  /**
   * ????????????
   *
   * @param size ????????????
   * @return ????????????
   */
  public static String convertFileSize(long size) {
    long kb = PublicConstants.BYTE_SIZE;
    long mb = kb * PublicConstants.BYTE_SIZE;
    long gb = mb * PublicConstants.BYTE_SIZE;
    if (size >= gb) {
      return String.format("%.1f GB", (float) size / gb);
    } else if (size >= mb) {
      float f = (float) size / mb;
      return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
    } else if (size >= kb) {
      float f = (float) size / kb;
      return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
    } else {
      return String.format("%d B", size);
    }
  }

  /**
   * ????????????
   *
   * @param size ??????
   * @return long
   */
  public static long convertSize(String size) {
    long kb = PublicConstants.BYTE_SIZE;
    long mb = kb * PublicConstants.BYTE_SIZE;
    long gb = mb * PublicConstants.BYTE_SIZE;
    long b = Long.parseLong(size.substring(0, size.length() - 2));
    if (size.contains(KB)) {
      return b * kb;
    }
    if (size.contains(MB)) {
      return b * mb;
    }
    if (size.contains(GB)) {
      return b * gb;
    }
    return b;
  }

  public static String formatString(final String strPattern, final Object... argArray) {
    if (isEmpty(strPattern) || isEmpty(argArray)) {
      return strPattern;
    }
    final int strPatternLength = strPattern.length();

    // ???????????????????????????????????????????????????
    StringBuilder builder = new StringBuilder(strPatternLength + 50);

    int handledPosition = 0;
    int delimiterIndex;// ?????????????????????
    for (Object o : argArray) {
      delimiterIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
      if (delimiterIndex == -1) {
        if (handledPosition == 0) {
          return strPattern;
        } else { // ????????????????????????????????????????????????????????????????????????????????????
          builder.append(strPattern, handledPosition, strPatternLength);
          return builder.toString();
        }
      } else {
        if (delimiterIndex > 0 && strPattern.charAt(delimiterIndex - 1) == C_BACKSLASH) {
          if (delimiterIndex > 1 && strPattern.charAt(delimiterIndex - 2) == C_BACKSLASH) {
            // ????????????????????????????????????????????????????????????
            builder.append(strPattern, handledPosition, delimiterIndex - 1);
            builder.append(ConvertString.utf8Str(o));
            handledPosition = delimiterIndex + 2;
          } else {
            // ??????????????????
            builder.append(strPattern, handledPosition, delimiterIndex - 1);
            builder.append(C_DELIMITER_START);
            handledPosition = delimiterIndex + 1;
          }
        } else {
          // ???????????????
          builder.append(strPattern, handledPosition, delimiterIndex);
          builder.append(ConvertString.utf8Str(o));
          handledPosition = delimiterIndex + 2;
        }
      }
    }
    // ?????????????????????????????????????????????
    builder.append(strPattern, handledPosition, strPattern.length());

    return builder.toString();
  }

  /**
   * ??????????????? ??????????????????????????????
   *
   * @param name ??????
   * @return String
   */
  public static String builderName(String name) {
    if (DataUtils.isEmpty(name)) {
      return StringUtils.EMPTY;
    }
    StringBuilder temp = new StringBuilder();
    String[] str = name.split("_");
    if (str.length == 1) {
      temp.append(str[0].substring(0, 1).toUpperCase()).append(str[0].substring(1));
    } else {
      for (String s : str) {
        temp.append(s.substring(0, 1).toUpperCase());
        temp.append(s.substring(1).toLowerCase());
      }
    }
    return temp.toString();
  }

  /**
   * ????????????????????????????????? ??????????????????????????????
   *
   * @param name ??????
   * @return String
   */
  public static String builderName(String name, String splitName) {
    if (DataUtils.isEmpty(name)) {
      return StringUtils.EMPTY;
    }

    String split;
    if (splitName == null) {
      split = " ";
    } else {
      split = splitName;
    }
    StringBuilder temp = new StringBuilder();
    String[] str = name.split("_");
    String result;
    if (str.length == 1) {
      temp.append(str[0].substring(0, 1).toUpperCase()).append(str[0].substring(1));
      result = temp.toString();
    } else {
      for (String s : str) {
        temp.append(s.substring(0, 1).toUpperCase());
        temp.append(s.substring(1).toLowerCase());
        temp.append(split);
      }
      result = temp.substring(0, temp.length() - 1);
    }
    return result;
  }

  /**
   * ??????????????? ??????????????????????????????
   *
   * @param name ??????
   * @return String
   */
  public static String coderName(String name) {
    String result = builderName(name);
    result = result.substring(0, 1).toLowerCase() + result.substring(1);
    return result;
  }

  /**
   * ??????????????? ??????????????????????????????
   *
   * @param name ??????
   * @return String
   */
  public static String coderName(String name, String splitName) {
    String result = builderName(name, splitName);
    result = result.substring(0, 1).toLowerCase() + result.substring(1);
    return result;
  }

  /**
   * ??????
   *
   * @param obj ??????
   * @return Object
   */
  public static Object clone(Object obj) {
    try (
        // ???????????????????????????,???????????????????????????????????????????????????????????????????????????JVM???????????????????????????????????????????????????????????????
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        // ????????????????????????
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis)) {

      oos.writeObject(obj);
      return ois.readObject();
    } catch (Exception e) {
      throw new CloneFailedException(e);
    }
  }

  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (IP_LOCALHOST.equals(ip)) {
      ip = getLocalhost();
    }
    if (ip != null && ip.length() > IP_LENGTH && ip.contains(C_QUARTER)) {
      ip = StringUtils.substringAfterLast(ip, C_QUARTER);
    }
    return ip;
  }

  /**
   * ????????????ip
   *
   * @return String
   */
  public static String getLocalhost() {
    try {
      InetAddress inet = InetAddress.getLocalHost();
      return inet.getHostAddress();
    } catch (UnknownHostException e) {
      LOGGER.error("Error getLocalhost:", e);
    }
    return null;
  }

  /**
   * ??????????????????
   *
   * @return String
   */
  public static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      LOGGER.error("Error getHostName:", e);
    }
    return "??????";
  }

  private static long getIp2long(String ip) {
    ip = ip.trim();
    String[] ips = ip.split("\\.");
    long ip2long = 0L;
    for (int i = 0; i < IP_PART; ++i) {
      ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
    }
    return ip2long;
  }

  /**
   * ??????ip???????????????
   *
   * @param ip        ip
   * @param ipSection ip???
   * @return boolean
   */
  public static boolean ipExistsInRange(String ip, String ipSection) {
    ipSection = ipSection.trim();
    ip = ip.trim();
    int idx = ipSection.indexOf('-');
    String beginIp = ipSection.substring(0, idx);
    String endIp = ipSection.substring(idx + 1);
    return getIp2long(beginIp) <= getIp2long(ip) && getIp2long(ip) <= getIp2long(endIp);
  }

  /**
   * ????????????
   *
   * @param file     ??????
   * @param fileName ????????????
   * @return ResponseEntity
   */
  public static ResponseEntity<byte[]> download(File file, String fileName) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentLength(file.length());
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, Charset.defaultCharset().name()));
      return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    } catch (IOException e) {
      LOGGER.error("Error download", e);
    }
    return null;
  }

  /**
   * ????????????????????????????????? ?????????????????????????????????(?????????????????????)
   *
   * @param readFile  ??????????????????
   * @param writeFile ??????????????????
   * @param items     ?????????????????????key?????????value, value?????????null, ?????????????????????
   * @return boolean
   */
  public static boolean replaceContextFileCreate(String readFile, String writeFile, Map<String, Object> items) {
    try (FileInputStream fis = new FileInputStream(readFile)) {
      writeFile(fis, writeFile, items);
      LOGGER.debug("createFile success: {}", writeFile);
      return true;
    } catch (IOException e) {
      LOGGER.error("replaceContextFileCreate Error!", e);
    }
    return false;
  }

  private static void writeFile(InputStream is, String writeFile, Map<String, Object> items) {
    try (InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());
        BufferedReader in = new BufferedReader(isr);
        FileOutputStream fos = new FileOutputStream(writeFile);
        OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.defaultCharset());
        BufferedWriter out = new BufferedWriter(osw)) {

      // ????????????
      String s;
      while ((s = in.readLine()) != null) {
        String context = replaceContext(items, s);
        out.write(context);
        out.newLine();
      }
      out.flush();
    } catch (IOException e) {
      LOGGER.error("writeFile Error!", e);
    }

  }

  /**
   * ????????????
   *
   * @param items ??????
   * @param s     ??????
   */
  private static String replaceContext(Map<String, Object> items, String s) {
    String result = s;
    if (items != null) {
      for (Map.Entry<String, Object> entry : items.entrySet()) {
        final String key = entry.getKey();
        String value = StringUtils.EMPTY;
        if (items.get(key) != null) {
          value = items.get(key).toString();
        }
        result = result.replace(key, value);
      }
    }
    return result;
  }

  /**
   * File ????????? MultipartFile
   *
   * @param file ??????
   * @return MultipartFile
   */
  public static MultipartFile convertMultipartFile(File file) {
    if (file == null) {
      return null;
    }
    FileItemFactory factory = new DiskFileItemFactory(16, null);
    FileItem item = factory.createItem(file.getName(), MediaType.TEXT_PLAIN_VALUE, true, file.getName());
    int bytesRead;
    byte[] buffer = new byte[BUFFER_SIZE];
    try (FileInputStream fis = new FileInputStream(file); OutputStream os = item.getOutputStream()) {
      while ((bytesRead = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
        os.write(buffer, 0, bytesRead);
      }
    } catch (IOException e) {
      LOGGER.error("Error convertMultipartFile:", e);
    }
    return new CommonsMultipartFile(item);
  }

  /**
   * ????????????????????? ????????????????????????
   *
   * @param sourceObject ?????????
   * @param targetObject ????????????
   * @param ignoreProperties ???????????????
   * @return Map<String, List<Object>>
   */
  public static Map<String, List<String>> diffBeanProperties(Object sourceObject, Object targetObject,
      List<String> ignoreProperties) {
    Map<String, List<String>> map = new HashMap<>(10);
    if (sourceObject.getClass().equals(targetObject.getClass())) {
      try {
        Class<?> clazz = sourceObject.getClass();
        PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
          String name = pd.getName();
          if (ignoreProperties.stream().noneMatch(name::equals)) {

            Method readMethod = pd.getReadMethod();
            Object o1 = readMethod.invoke(sourceObject);
            Object o2 = readMethod.invoke(targetObject);
            // Date
            if (o1 instanceof Date) {
              o1 = DateUtil.dateToString((Date) o1, DateUtil.FULL_TIME_FORMAT);
              o2 = DateUtil.dateToString((Date) o2, DateUtil.FULL_TIME_FORMAT);
            }
            // LocalDateTime
            if (o1 instanceof LocalDateTime) {
              Date d1 = DateUtil.localDateTimeToDate((LocalDateTime) o1);
              Date d2 = DateUtil.localDateTimeToDate((LocalDateTime) o2);
              o1 = DateUtil.dateToString(d1, DateUtil.FULL_TIME_FORMAT);
              o2 = DateUtil.dateToString(d2, DateUtil.FULL_TIME_FORMAT);
            }
            // BigDecimal
            if (o1 instanceof BigDecimal) {
              o1 = ((BigDecimal)o1).setScale(4, RoundingMode.UP);
              o2 = ((BigDecimal)o2).setScale(4, RoundingMode.UP);
            }
            // Enum
            if (o1 instanceof Enum) {
              o1 = ((Enum<?>)o1).name();
              o2 = ((Enum<?>)o2).name();
            }

            if (o1 != null && o2 != null && !o1.equals(o2)) {
              List<String> list = new ArrayList<>();
              list.add(o1.toString());
              list.add(o2.toString());
              map.put(name, list);
            }
          }
        }
      } catch (Exception e) {
        LOGGER.error("Error diffBeanProperties:", e);
      }
    }
    return map;
  }
}
