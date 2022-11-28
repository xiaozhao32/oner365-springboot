package com.oner365.elasticsearch.repository;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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

  private final ElasticsearchRestTemplate elasticsearchTemplate;

  public SampleLocationElasticsearchRepository(SampleLocationElasticsearchEntityInformation metadata,
      ElasticsearchOperations elasticsearchOperations) {
    super(metadata, elasticsearchOperations);
    elasticsearchTemplate = (ElasticsearchRestTemplate) super.operations;
  }

  @SuppressWarnings({ "unchecked" })
  public Page<SampleLocation> pageList(QueryCriteriaBean data) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        queryBuilder.filter(QueryBuilders.termQuery(entity.getKey(), entity.getVal()));
      }
    });

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
        .withPageable(QueryUtils.buildPageRequest(data)).withSort(QueryUtils.buildSortRequest(data.getOrder())).build();
    SearchHits<SampleLocation> searchHits = elasticsearchTemplate.search(searchQuery, SampleLocation.class);
    SearchPage<SampleLocation> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    return (Page<SampleLocation>) SearchHitSupport.unwrapSearchHits(page);
  }

}
