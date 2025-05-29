package com.oner365.elasticsearch.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.elasticsearch.dto.SampleLocationDto;
import com.oner365.elasticsearch.entity.SampleLocation;
import com.oner365.elasticsearch.repository.SampleLocationElasticsearchRepository;
import com.oner365.elasticsearch.service.ISampleLocationElasticsearchService;
import com.oner365.elasticsearch.vo.SampleLocationVo;

/**
 * 坐标信息 - 实现
 *
 * @author zhaoyong
 *
 */
@Service
public class SampleLocationElasticsearchServiceImpl implements ISampleLocationElasticsearchService {

    private final SampleLocationElasticsearchRepository repository;

    public SampleLocationElasticsearchServiceImpl(SampleLocationElasticsearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageInfo<SampleLocationDto> pageList(QueryCriteriaBean data) {
        Page<SampleLocation> pageList = repository.pageList(data);
        return convert(pageList, SampleLocationDto.class);
    }

    @Override
    public SampleLocationDto save(SampleLocationVo vo) {
        vo.setCreateTime(DateUtil.getDate());
        SampleLocation entity = repository.save(convert(vo, SampleLocation.class));
        return convert(entity, SampleLocationDto.class);
    }

    @Override
    public SampleLocationDto findById(String id) {
        SampleLocation entity = repository.findById(id).orElse(null);
        return convert(entity, SampleLocationDto.class);
    }

    @Override
    public Boolean deleteById(String id) {
        repository.deleteById(id);
        return Boolean.TRUE;
    }

}
