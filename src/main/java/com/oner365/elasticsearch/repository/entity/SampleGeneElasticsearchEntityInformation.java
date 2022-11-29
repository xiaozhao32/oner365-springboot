package com.oner365.elasticsearch.repository.entity;

import org.springframework.data.elasticsearch.annotations.Document.VersionType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.stereotype.Repository;

import com.oner365.elasticsearch.entity.SampleGene;

@Repository
public class SampleGeneElasticsearchEntityInformation implements ElasticsearchEntityInformation<SampleGene, String> {

  @Override
  public boolean isNew(SampleGene entity) {
    return false;
  }

  @Override
  public String getId(SampleGene entity) {
    return entity.getId();
  }

  @Override
  public Class<String> getIdType() {
    return String.class;
  }

  @Override
  public Class<SampleGene> getJavaType() {
    return SampleGene.class;
  }

  @Override
  public String getIdAttribute() {
    return "id";
  }

  @Override
  public IndexCoordinates getIndexCoordinates() {
    return IndexCoordinates.of(IndexCoordinates.TYPE);
  }

  @Override
  public Long getVersion(SampleGene entity) {
    return (long) 2.0;
  }

  @Override
  public VersionType getVersionType() {
    return VersionType.EXTERNAL;
  }


}
