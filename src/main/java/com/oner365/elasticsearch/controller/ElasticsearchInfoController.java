package com.oner365.elasticsearch.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.config.properties.CommonProperties;
import com.oner365.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch 信息
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "Elasticsearch 信息")
@SuppressWarnings({ "deprecation", "resource" })
@RequestMapping("/elasticsearch/info")
public class ElasticsearchInfoController extends BaseController {

  @Autowired
  private CommonProperties commonProperties;

  /**
   * Elasticsearch 信息
   *
   * @return Map<String, Object>
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public Map<String, Object> index() {
    String hostname = StringUtils.substringBefore(commonProperties.getUris(), ":");
    int port = 9300;
    // 指定集群
    Settings settings = Settings.builder().build();
    // 创建客户端
    Map<String, Object> result = new HashMap<>(10);
    try (final TransportClient client = new PreBuiltTransportClient(settings)
        .addTransportAddress(new TransportAddress(InetAddress.getByName(hostname), port))) {
      ClusterHealthResponse response = client.admin().cluster().prepareHealth().get();

      result.put("hostname", hostname);
      result.put("port", port);
      result.put("clusterName", response.getClusterName());
      result.put("numberOfDataNodes", response.getNumberOfDataNodes());
      result.put("activeShards", response.getActiveShards());
      result.put("status", response.getStatus().name());
      result.put("taskMaxWaitingTime", response.getTaskMaxWaitingTime());
      // 索引信息
      List<Map<String, Object>> clusterList = new ArrayList<>();
      response.getIndices().values().forEach(health -> {
        Map<String, Object> map = new HashMap<>(10);
        map.put("index", health.getIndex());
        map.put("numberOfShards", health.getNumberOfShards());
        map.put("numberOfReplicas", health.getNumberOfReplicas());
        map.put("status", health.getStatus().toString());
        clusterList.add(map);
      });
      result.put("clusterList", clusterList);
    } catch (UnknownHostException e) {
      logger.error("index error:", e);
    }
    return result;
  }
}
