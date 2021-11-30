package com.oner365.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.oner365.util.ClassesUtil;

/**
 * Service 父类
 * @author liutao
 *
 */
public interface BaseService {
	
	public static final String METHOD_NAME = "toDTO";
	
	@SuppressWarnings({ "unchecked"})
	default <T>List<T> convertDto(List<?> list){
		return (List<T>) list.stream().map(po -> {
			return ClassesUtil.invokeMethod(po, METHOD_NAME, new Object(){});
		}).collect(Collectors.toList());
	}
	
	@SuppressWarnings({"unchecked" })
	default <T>Page<T> convertDto(Page<?> page){
		return new PageImpl<T>((List<T>)convertDto(page.getContent()),page.getPageable(),page.getTotalElements());
	}
	
}
