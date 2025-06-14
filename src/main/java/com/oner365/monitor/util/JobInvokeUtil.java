package com.oner365.monitor.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.entity.InvokeParam;

/**
 * 任务执行工具
 *
 * @author zhaoyong
 */
public class JobInvokeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobInvokeUtil.class);

    private static final String SYMBOL = "'";

    private static final String SYMBOL_LONG = "L";

    private static final String SYMBOL_DOUBLE = "D";

    private JobInvokeUtil() {

    }

    /**
     * 执行方法
     * @param sysTask 系统任务
     */
    public static void invokeMethod(SysTaskDto sysTask) {
        String invokeTarget = sysTask.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);
        try {
            if (!isValidClassName(beanName)) {
                Object bean = SpringUtils.getBean(beanName);
                invokeMethod(bean, methodName, methodParams);
            }
            else {
                Object bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
                invokeMethod(bean, methodName, methodParams);
            }
        }
        catch (Exception e) {
            LOGGER.error("invokeMethod error:", e);
        }
    }

    /**
     * 调用任务方法
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param param 方法参数
     */
    @SuppressWarnings("unused")
    private static void invokeMethod(Object bean, String methodName, InvokeParam param)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (param != null) {
            Method method = bean.getClass().getDeclaredMethod(methodName, InvokeParam.class);
            method.invoke(bean, param);
        }
        else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 调用任务方法
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (!DataUtils.isEmpty(methodParams)) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        }
        else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 校验是否为为class包名
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取bean名称
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 获取method方法参数相关列表
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (DataUtils.isEmpty(methodStr)) {
            return Collections.emptyList();
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> classList = new LinkedList<>();
        // String字符串类型，包含'
        Arrays.stream(methodParams).map(StringUtils::trimToEmpty).forEach(str -> {
            if (StringUtils.contains(str, SYMBOL)) {
                classList.add(new Object[] { StringUtils.replace(str, SYMBOL, ""), String.class });
            }
            // boolean布尔类型，等于true或者false
            else if (StringUtils.equals(str, Boolean.TRUE.toString())
                    || StringUtils.equalsIgnoreCase(str, Boolean.FALSE.toString())) {
                classList.add(new Object[] { Boolean.valueOf(str), Boolean.class });
            }
            // long长整形，包含L
            else if (StringUtils.containsIgnoreCase(str, SYMBOL_LONG)) {
                classList.add(
                        new Object[] { Long.valueOf(StringUtils.replaceIgnoreCase(str, SYMBOL_LONG, "")), Long.class });
            }
            // double浮点类型，包含D
            else if (StringUtils.containsIgnoreCase(str, SYMBOL_DOUBLE)) {
                classList.add(new Object[] { Double.valueOf(StringUtils.replaceIgnoreCase(str, SYMBOL_DOUBLE, "")),
                        Double.class });
            }
            // 其他类型归类为整形
            else {
                classList.add(new Object[] { Integer.valueOf(str), Integer.class });
            }
        });
        return classList;
    }

    /**
     * 获取参数类型
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classList = new Class<?>[methodParams.size()];
        IntStream.range(0, methodParams.size()).forEach(index -> {
            Object[] os = methodParams.get(index);
            classList[index] = (Class<?>) os[1];
        });
        return classList;
    }

    /**
     * 获取参数值
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] classList = new Object[methodParams.size()];
        IntStream.range(0, methodParams.size()).forEach(index -> {
            Object[] os = methodParams.get(index);
            classList[index] = os[0];
        });
        return classList;
    }

}
