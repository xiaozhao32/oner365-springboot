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

    private final ElasticsearchTemplate elasticsearchTemplate;

    public SampleGeneElasticsearchRepository(SampleGeneElasticsearchEntityInformation metadata,
            ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
        elasticsearchTemplate = (ElasticsearchTemplate) super.operations;
    }

    @SuppressWarnings({ "unchecked" })
    public Page<SampleGene> pageList(QueryCriteriaBean data) {
        Criteria criteria = new Criteria();
        data.getWhereList().forEach(entity -> {
            if (!DataUtils.isEmpty(entity.getVal())) {
                criteria.subCriteria(new Criteria(entity.getKey()).is(entity.getVal()));
            }
        });

        CriteriaQuery searchQuery = CriteriaQuery.builder(criteria).build();
        searchQuery.setPageable(QueryUtils.buildPageRequest(data));
        searchQuery.addSort(QueryUtils.buildSortRequest(data.getOrder()));

        SearchHits<SampleGene> searchHits = elasticsearchTemplate.search(searchQuery, SampleGene.class);
        SearchPage<SampleGene> page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
        return (Page<SampleGene>) SearchHitSupport.unwrapSearchHits(page);
    }

}
