package com.oner365.elasticsearch.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dto.ApplicationLogDto;
import com.oner365.elasticsearch.entity.ApplicationLog;
import com.oner365.elasticsearch.repository.ApplicationLogElasticsearchRepository;
import com.oner365.elasticsearch.service.IApplicationLogElasticsearchService;

/**
 * SampleGeneElasticsearch实现类
 * 
 * @author zhaoyong
 */
@Service
public class ApplicationLogElasticsearchServiceImpl implements IApplicationLogElasticsearchService {

  private final ApplicationLogElasticsearchRepository repository;

  public ApplicationLogElasticsearchServiceImpl(ApplicationLogElasticsearchRepository repository) {
    this.repository = repository;
  }

  @Override
  public PageInfo<ApplicationLogDto> pageList(QueryCriteriaBean data) {
    Page<ApplicationLog> pageList = repository.pageList(data);
    return convert(pageList, ApplicationLogDto.class);
  }

  @Override
  public ApplicationLogDto findById(String id) {
    Optional<ApplicationLog> optional = repository.findById(id);
    return convert(optional.orElse(null), ApplicationLogDto.class);
  }

  @Override
  public Boolean deleteById(String id) {
    repository.deleteById(id);
    return Boolean.TRUE;
  }

}
