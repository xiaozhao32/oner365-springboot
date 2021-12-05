package com.oner365.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.oner365.util.ClassesUtil;

/**
 * Service 父类
 *
 * @author liutao
 */
public interface BaseService {

  String METHOD_NAME = "toDTO";

  /**
   * 转换po对象为dto
   *
   * @param list po集合
   * @return List<T>
   */
  @SuppressWarnings({"unchecked"})
  default <T> List<T> convertDto(List<?> list) {
    if (list.isEmpty()) {
      return Collections.emptyList();
    }
    return (List<T>) list.stream().map(po -> ClassesUtil.invokeMethod(po, METHOD_NAME)).collect(Collectors.toList());
  }

  /**
   * 转换分页对象
   *
   * @param page 分页po对象
   * @return Page<T>
   */
  default <T> Page<T> convertDto(Page<?> page) {
    if (page == null) {
      return null;
    }
    return new PageImpl<>(convertDto(page.getContent()), page.getPageable(), page.getTotalElements());
  }

}
