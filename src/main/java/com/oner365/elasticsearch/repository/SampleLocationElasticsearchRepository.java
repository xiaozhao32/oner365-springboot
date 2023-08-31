package com.oner365.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.elasticsearch.entity.SampleLocation;
import com.oner365.elasticsearch.repository.entity.SampleLocationElasticsearchEntityInformation;
import com.oner365.util.DataUtils;

/**
 * SampleLocationElasticsearchRepository
 * 
 * SimpleElasticsearchRepository
 * 
 * @author zhaoyong
 */
@Repository
public class SampleLocationElasticsearchRepository extends SimpleElasticsearchRepository<SampleLocation, String> {

  private final ElasticsearchTemplate elasticsearchTemplate;

  public SampleLocationElasticsearchRepository(SampleLocationElasticsearchEntityInformation metadata,
      ElasticsearchOperations elasticsearchOperations) {
    super(metadata, elasticsearchOperations);
    elasticsearchTemplate = (ElasticsearchTemplate) super.operations;
  }

  @SuppressWarnings({ "unchecked" })
  public Page<SampleLocation> pageList(QueryCriteriaBean data) {
    Criteria criteria = new Criteria();
    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        criteria.subCriteria(new Criteria(entity.getKey()).is(entity.getVal()));
      }
    });

    CriteriaQuery searchQuery = CriteriaQuery.builder(criteria).build();
    searchQuery.setPageable(QueryUtils.buildPageRequest(data));
    searchQuery.addSort(QueryUtils.buildSortRequest(data.getOrder()));
    
    SearchHits<SampleLocation> searchHits = elasticsearchTemplate.search(searchQuery, SampleLocation.class);
    SearchPage<SampleLocation> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    return (Page<SampleLocation>) SearchHitSupport.unwrapSearchHits(page);
  }

}
