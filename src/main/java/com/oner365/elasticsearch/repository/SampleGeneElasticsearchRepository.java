package com.oner365.elasticsearch.repository;

import java.util.Objects;

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

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.elasticsearch.entity.SampleGene;
import com.oner365.elasticsearch.repository.entity.SampleGeneElasticsearchEntityInformation;

/**
 * SampleGeneElasticsearchRepository
 * 
 * SimpleElasticsearchRepository
 * 
 * @author zhaoyong
 */
@Repository
public class SampleGeneElasticsearchRepository extends SimpleElasticsearchRepository<SampleGene, String> {

  private final ElasticsearchRestTemplate elasticsearchTemplate;

  public SampleGeneElasticsearchRepository(SampleGeneElasticsearchEntityInformation metadata,
      ElasticsearchOperations elasticsearchOperations) {
    super(metadata, elasticsearchOperations);
    elasticsearchTemplate = (ElasticsearchRestTemplate) super.operations;
  }

  @SuppressWarnings({ "unchecked" })
  public Page<SampleGene> pageList(QueryCriteriaBean data) {
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

    data.getWhereList().forEach(entity -> {
      if (!DataUtils.isEmpty(entity.getVal())) {
        queryBuilder.filter(QueryBuilders.termQuery(entity.getKey(), entity.getVal()));
      }
    });

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
        .withPageable(QueryUtils.buildPageRequest(data)).withSort(Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder()))).build();
    SearchHits<SampleGene> searchHits = elasticsearchTemplate.search(searchQuery, SampleGene.class);
    SearchPage<SampleGene> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
    return (Page<SampleGene>) SearchHitSupport.unwrapSearchHits(page);
  }

}
