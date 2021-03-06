package com.oner365.elasticsearch.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.config.properties.CommonProperties;
import com.oner365.controller.BaseController;
import com.oner365.elasticsearch.dto.ClusterDto;
import com.oner365.elasticsearch.dto.ClusterMappingDto;
import com.oner365.elasticsearch.dto.TransportClientDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch 信息
 *
 * @author zhaoyong
 *
 */
@SuppressWarnings("deprecation")
@RestController
@Api(tags = "Elasticsearch 信息")
@RequestMapping("/elasticsearch/info")
public class ElasticsearchInfoController extends BaseController {

  @Autowired
  private CommonProperties commonProperties;

  /**
   * Elasticsearch 信息
   *
   * @return TransportClientDto
   */
  @SuppressWarnings({ "unchecked" })
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public TransportClientDto index() {
    // 创建客户端
    String uri = StringUtils.substringAfter(commonProperties.getUris(), "http://");
    ClientConfiguration configuration = ClientConfiguration.builder().connectedTo(uri).build();
    try (RestHighLevelClient client = RestClients.create(configuration).rest()) {
      ClusterHealthResponse healthResponse = client.cluster().health(new ClusterHealthRequest(), RequestOptions.DEFAULT);

      TransportClientDto result = new TransportClientDto();
      result.setHostname(StringUtils.substringBefore(uri, ":"));
      result.setPort(Integer.parseInt(StringUtils.substringAfter(uri, ":")));
      result.setClusterName(healthResponse.getClusterName());
      result.setNumberOfDataNodes(healthResponse.getNumberOfDataNodes());
      result.setActiveShards(healthResponse.getActiveShards());
      result.setStatus(healthResponse.getStatus());
      result.setTaskMaxWaitingTime(healthResponse.getTaskMaxWaitingTime().getMillis());

      // 索引信息
      List<ClusterDto> clusterList = new ArrayList<>();
      GetAliasesResponse aliasResponse = client.indices().getAlias(new GetAliasesRequest(), RequestOptions.DEFAULT);
      Map<String, Set<AliasMetadata>> aliasMap = aliasResponse.getAliases();
      aliasMap.forEach((key, value) -> {
        try {
          SearchResponse search = client.search(new SearchRequest(key), RequestOptions.DEFAULT);

          ClusterDto clusterDto = new ClusterDto();
          clusterDto.setIndex(key);
          clusterDto.setNumberOfShards(search.getTotalShards());
          clusterDto.setNumberOfReplicas(search.getNumReducePhases());
          clusterDto.setStatus(search.status());
          clusterList.add(clusterDto);
        } catch (IOException e) {
          logger.error("SearchRequest error:", e);
        }
      });
      result.setClusterList(clusterList);

      // mapping信息
      GetMappingsResponse mappingResponse = client.indices().getMapping(new GetMappingsRequest(), RequestOptions.DEFAULT);
      Map<String, MappingMetadata> mappings = mappingResponse.mappings();
      clusterList.forEach(cluster -> {
        Map<String, Object> map = mappings.get(cluster.getIndex()).sourceAsMap();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");
        List<ClusterMappingDto> mappingList = new ArrayList<>();
        properties.forEach((key, value) -> {
          ClusterMappingDto mapping = new ClusterMappingDto();
          mapping.setName(key);
          Map<String, Object> valueMap = (Map<String, Object>) value;
          mapping.setType(valueMap.get("type") == null ? "Object" : valueMap.get("type").toString());
          mappingList.add(mapping);
        });
        cluster.setMappingList(mappingList);
      });
      return result;
    } catch (Exception e) {
      logger.error("index error:", e);
    }
    return null;
  }
}
