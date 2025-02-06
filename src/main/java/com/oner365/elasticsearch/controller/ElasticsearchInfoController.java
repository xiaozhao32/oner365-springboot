package com.oner365.elasticsearch.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.Base64Utils;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.web.controller.BaseController;
import com.oner365.elasticsearch.dto.ClusterDto;
import com.oner365.elasticsearch.dto.ClusterMappingDto;
import com.oner365.elasticsearch.dto.TransportClientDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.indices.GetAliasResponse;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import co.elastic.clients.elasticsearch.indices.get_alias.IndexAliases;
import co.elastic.clients.elasticsearch.indices.get_mapping.IndexMappingRecord;
import co.elastic.clients.elasticsearch.indices.stats.ShardRoutingState;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

/**
 * Elasticsearch 信息
 *
 * @author zhaoyong
 *
 */
@RestController
@Tag(name = "Elasticsearch 信息")
@RequestMapping("/elasticsearch/info")
public class ElasticsearchInfoController extends BaseController {

  @Resource
  private ElasticsearchProperties elasticsearchProperties;

  @Resource
  private WebClient webClient;

  /**
   * Elasticsearch 信息
   *
   * @return TransportClientDto
   */
  @Operation(summary = "1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public TransportClientDto index() {
    if (ObjectUtils.isEmpty(elasticsearchProperties.getUris())) {
      logger.error("elasticsearchProperties is empty: {}", elasticsearchProperties);
      return null;
    }
    // 创建客户端
    String uri = StringUtils.substringAfter(elasticsearchProperties.getUris().get(0), PublicConstants.FILE_HTTP);

    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    // 设置密码 xpack.security.enabled: true
    if (elasticsearchProperties.getUsername() != null && elasticsearchProperties.getPassword() != null) {
      credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
          elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));
    }

    HttpClientConfigCallback httpClientConfigCallback = httpClientBuilder -> httpClientBuilder
        .setDefaultHeaders(
            Collections.singleton(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())))
        .setDefaultCredentialsProvider(credentialsProvider).addInterceptorLast(
            (HttpResponseInterceptor) (response, context) -> response.addHeader("X-Elastic-Product", "Elasticsearch"));

    try (RestClient restClient = RestClient.builder(HttpHost.create(elasticsearchProperties.getUris().get(0)))
        .setHttpClientConfigCallback(httpClientConfigCallback).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport)) {

      TransportClientDto result = new TransportClientDto();
      setHealth(uri, result);
      setShards(client, result);
      setMappingList(client, result.getClusterList());
      return result;
    } catch (Exception e) {
      logger.error("index error:", e);
    }
    return null;
  }

  /**
   * 返回结果对象
   */
  private void setHealth(String uri, @NotNull TransportClientDto result) {
    result.setHostname(StringUtils.substringBefore(uri, PublicConstants.COLON));
    result.setPort(Integer.parseInt(StringUtils.substringAfter(uri, PublicConstants.COLON)));

    // Elasticsearch health
    JSONObject healthResponse = request("/_cluster/health");
    if (healthResponse != null) {
      result.setClusterName(healthResponse.getString("cluster_name"));
      result.setNumberOfDataNodes(healthResponse.getInteger("number_of_data_nodes"));
      result.setActiveShards(healthResponse.getInteger("active_shards"));
      result.setStatus(HealthStatus.valueOf(DataUtils.builderName(healthResponse.getString("status"))));
      result.setTaskMaxWaitingTime(healthResponse.getString("task_max_waiting_in_queue_millis"));
    }
  }

  /**
   * 索引信息
   */
  private void setShards(@NotNull ElasticsearchClient client, @NotNull TransportClientDto result) throws IOException {
    GetAliasResponse aliasResponse = client.indices().getAlias();
    Map<String, IndexAliases> aliasMap = aliasResponse.result();
    Map<String, ShardRoutingState> stateMap = new HashMap<>(10);
    Map<String, Integer> shardsMap = new HashMap<>(10);

    // Elasticsearch shards
    JSONObject shardsResponse = request("/_search_shards");
    if (shardsResponse != null) {
      JSONArray shards = shardsResponse.getJSONArray("shards");
      IntStream.range(0, shards.size()).mapToObj(i -> shards.getJSONArray(i).getJSONObject(0)).forEachOrdered(shard -> {
        stateMap.put(shard.getString("index"), ShardRoutingState.valueOf(DataUtils.builderName(shard.getString("state").toLowerCase())));
        shardsMap.merge(shard.getString("index"), 1, Integer::sum);
      });
  
      List<ClusterDto> clusterList = new ArrayList<>();
      aliasMap.forEach((key, value) -> {
        ClusterDto clusterDto = new ClusterDto();
        clusterDto.setIndex(key);
        clusterDto.setNumberOfShards(shardsMap.get(key));
        clusterDto.setNumberOfReplicas(1);
        clusterDto.setStatus(stateMap.get(key));
        clusterList.add(clusterDto);
      });
      result.setClusterList(clusterList);
    }
  }

  /**
   * mapping
   */
  private void setMappingList(@NotNull ElasticsearchClient client, @NotNull List<ClusterDto> clusterList) throws IOException {
    GetMappingResponse mappingResponse = client.indices().getMapping();
    Map<String, IndexMappingRecord> mappings = mappingResponse.result();
    clusterList.forEach(cluster -> {
      IndexMappingRecord mappingRecord = mappings.get(cluster.getIndex());
      List<ClusterMappingDto> mappingList = new ArrayList<>();
      if (mappingRecord != null) {
        mappingRecord.mappings().properties().forEach((key, value) -> {
          ClusterMappingDto mapping = new ClusterMappingDto();
          mapping.setName(key);
          mapping.setType(value._get().getClass().getSimpleName());
          mappingList.add(mapping);
        });
      }
      cluster.setMappingList(mappingList);
    });
  }

  private String getAuthorization() {
    String auth = elasticsearchProperties.getUsername() + PublicConstants.COLON + elasticsearchProperties.getPassword();
    return "Basic " + Base64Utils.encodeBase64String(auth.getBytes());
  }

  private JSONObject request(String uri) {
    Mono<JSONObject> mono = webClient.get().uri(elasticsearchProperties.getUris().get(0) + uri)
        .header(HttpHeaders.AUTHORIZATION, getAuthorization()).retrieve().bodyToMono(JSONObject.class);
    return mono.block();
  }

}
