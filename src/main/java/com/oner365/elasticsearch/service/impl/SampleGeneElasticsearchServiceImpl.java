package com.oner365.elasticsearch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dto.SampleGeneDto;
import com.oner365.elasticsearch.entity.SampleGene;
import com.oner365.elasticsearch.repository.SampleGeneElasticsearchRepository;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.elasticsearch.vo.SampleGeneVo;

/**
 * SampleGeneElasticsearch实现类
 * 
 * @author zhaoyong
 */
@Service
public class SampleGeneElasticsearchServiceImpl implements ISampleGeneElasticsearchService {

  private final SampleGeneElasticsearchRepository repository;

  public SampleGeneElasticsearchServiceImpl(SampleGeneElasticsearchRepository repository) {
    this.repository = repository;
  }

  @Override
  public PageInfo<SampleGeneDto> pageList(QueryCriteriaBean data) {
    Page<SampleGene> pageList = repository.pageList(data);
    return convert(pageList, SampleGeneDto.class);
  }

  @Override
  public List<SampleGeneDto> saveAll(List<SampleGeneVo> voList) {
    Iterable<SampleGene> iterable = repository.saveAll(convert(voList, SampleGene.class));
    List<SampleGene> list = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    return convert(list, SampleGeneDto.class);
  }

  @Override
  public SampleGeneDto save(SampleGeneVo vo) {
    SampleGene entity = repository.save(convert(vo, SampleGene.class));
    return convert(entity, SampleGeneDto.class);
  }

  @Override
  public SampleGeneDto findById(String id) {
    Optional<SampleGene> optional = repository.findById(id);
    return convert(optional.orElse(null), SampleGeneDto.class);
  }

  @Override
  public Boolean deleteById(String id) {
    repository.deleteById(id);
    return Boolean.TRUE;
  }

}
