package com.oner365.elasticsearch.repository.entity;

import org.jspecify.annotations.NonNull;
import org.springframework.data.elasticsearch.annotations.Document.VersionType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.stereotype.Repository;

import com.oner365.elasticsearch.entity.SampleGene;
import com.oner365.sys.constants.SysConstants;

/**
 * SampleGene EntityInformation
 *
 * @author zhaoyong
 *
 */
@Repository
public class SampleGeneElasticsearchEntityInformation implements ElasticsearchEntityInformation<SampleGene, String> {

    @Override
    public boolean isNew(@NonNull SampleGene entity) {
        return false;
    }

    @Override
    public String getId(@NonNull SampleGene entity) {
        return entity.getId();
    }

    @Override
    public @NonNull Class<String> getIdType() {
        return String.class;
    }

    @Override
    public @NonNull Class<SampleGene> getJavaType() {
        return SampleGene.class;
    }

    @Override
    public @NonNull String getIdAttribute() {
        return SysConstants.ID;
    }

    @Override
    public @NonNull IndexCoordinates getIndexCoordinates() {
        return IndexCoordinates.of(IndexCoordinates.TYPE);
    }

    @Override
    public Long getVersion(@NonNull SampleGene entity) {
        return (long) 2.0;
    }

    @Override
    public VersionType getVersionType() {
        return VersionType.EXTERNAL;
    }

}
