package com.oner365.elasticsearch.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.elasticsearch.autoconfigure.ElasticsearchProperties;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexInformation;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.cluster.ClusterHealth;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.web.controller.BaseController;
import com.oner365.elasticsearch.dto.ClusterDto;
import com.oner365.elasticsearch.dto.ClusterMappingDto;
import com.oner365.elasticsearch.dto.TransportClientDto;

import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.indices.stats.ShardRoutingState;
import co.elastic.clients.util.ApiTypeHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;

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
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private WebClient webClient;

    /**
     * Elasticsearch 信息
     * @return TransportClientDto
     */
    @Operation(summary = "1.首页")
    @ApiOperationSupport(order = 1)
    @GetMapping("/index")
    public TransportClientDto index() {
        if (DataUtils.isEmpty(elasticsearchProperties.getUris())) {
            logger.error("elasticsearchProperties is empty: {}", elasticsearchProperties);
            return null;
        }
        // 创建客户端
        String uri = StringUtils.substringAfter(elasticsearchProperties.getUris().get(0), PublicConstants.FILE_HTTP);
        TransportClientDto result = new TransportClientDto();
        setHealth(uri, result);
        setShards(result);
        return result;
    }

    /**
     * health
     */
    private void setHealth(String uri, @NotNull TransportClientDto result) {
        result.setHostname(StringUtils.substringBefore(uri, PublicConstants.COLON));
        result.setPort(Integer.parseInt(StringUtils.substringAfter(uri, PublicConstants.COLON)));

        // Elasticsearch health
        try (ApiTypeHelper.DisabledChecksHandle h = ApiTypeHelper.DANGEROUS_disableRequiredPropertiesCheck(true)) {
            ClusterHealth healthResponse = elasticsearchTemplate.cluster().health();
            if (healthResponse != null) {
                result.setClusterName(healthResponse.getClusterName());
                result.setNumberOfDataNodes(healthResponse.getNumberOfDataNodes());
                result.setActiveShards(healthResponse.getActiveShards());
                result.setStatus(HealthStatus.valueOf(DataUtils.builderName(healthResponse.getStatus().toLowerCase())));
                result.setTaskMaxWaitingTime(healthResponse.getTaskMaxWaitingTimeMillis() + "");
            }
        }
    }

    /**
     * IndexInformation
     * @param result TransportClientDto
     */
    private void setShards(@NotNull TransportClientDto result) {

        IndexOperations indexOps = elasticsearchTemplate.indexOps(IndexCoordinates.of("*"));
        List<IndexInformation> indexInformations = indexOps.getInformation();

        if (DataUtils.isEmpty(indexInformations)) {
            result.setClusterList(Collections.emptyList());
            return;
        }

        List<ClusterDto> clusterList = indexInformations.stream()
            .map(this::buildInfomation)
            .filter(Objects::nonNull)
            .toList();
        result.setClusterList(clusterList);
    }

    /**
     * IndexInformation properties
     * @param information IndexInformation
     * @return ClusterDto
     */
    private ClusterDto buildInfomation(IndexInformation information) {
        Object indexObject = information.getSettings().get("index");
        if (indexObject instanceof Map<?, ?> map) {
            ClusterDto dto = new ClusterDto(information.getName(),
                    Integer.parseInt(map.get("number_of_shards").toString()),
                    Integer.parseInt(map.get("number_of_replicas").toString()), ShardRoutingState.Started);

            List<ClusterMappingDto> mappingList = new ArrayList<>();
            Object propertiesObject = information.getMapping().get("properties");
            if (propertiesObject instanceof Map<?, ?> properties) {
                properties
                    .forEach((key, value) -> mappingList.add(new ClusterMappingDto(key.toString(), value.toString())));
                dto.setMappingList(mappingList);
            }
            return dto;
        }
        return null;
    }

}
