package com.oner365.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.elasticsearch.entity.SampleGene;
import com.oner365.elasticsearch.service.ISampleGeneElasticsearchService;
import com.oner365.elasticsearch.vo.SampleGeneVo;
import com.oner365.util.DataUtils;
import com.oner365.util.GeneTransFormUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch Controller
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/elasticsearch/sampleGene")
@Api(tags = "Elasticsearch 基因型信息")
public class SampleGeneController extends BaseController {

    @Autowired
    private ISampleGeneElasticsearchService service;

    /**
     * 保存
     *
     * @param sampleGeneVo 基因对象
     * @return ResponseResult<SampleGene>
     */
    @PutMapping("/save")
    @ApiOperation("保存")
    public ResponseResult<SampleGene> save(@RequestBody SampleGeneVo sampleGeneVo) {
        if (sampleGeneVo == null) {
            return ResponseResult.error("基因对象为空!");
        }
        SampleGene sampleGene = sampleGeneVo.toObject();
        if (sampleGene != null) {
            if (!sampleGene.getGeneList().isEmpty()) {
                // 基因型格式转换
                String jsonArray = JSON.toJSONString(sampleGene.getGeneList());
                sampleGene.setGeneInfo(GeneTransFormUtils.geneFormatString(jsonArray));
                String s = GeneTransFormUtils.geneTrimString(sampleGene.getGeneInfo().toJSONString());
                sampleGene.setMatchJson(JSON.parseObject(s));
            }
            SampleGene entity = service.save(sampleGene);
            return ResponseResult.success(entity);
        }
        return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
    }

    /**
     * id查询
     *
     * @param id 编号
     * @return SampleGene
     */
    @GetMapping("/get/{id}")
    @ApiOperation("按id查询")
    public SampleGene get(@PathVariable("id") String id) {
        SampleGene sampleGene = service.findById(id);
        if (sampleGene != null && !DataUtils.isEmpty(sampleGene.getGeneInfo())) {
            // 基因型格式转换
            sampleGene.setGeneList(GeneTransFormUtils.geneFormatList(sampleGene.getGeneInfo().toJSONString()));
        }
        return sampleGene;
    }

    /**
     * 删除
     *
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public Integer delete(@RequestBody String... ids) {
        Integer result = ResultEnum.SUCCESS.getCode();
        for (String id : ids) {
            service.deleteById(id);
        }
        return result;
    }

    /**
     * 列表
     *
     * @param data 查询条件参数
     * @return Page<SampleGene>
     */
    @PostMapping("/list")
    @ApiOperation("获取列表")
    public Page<SampleGene> list(@RequestBody QueryCriteriaBean data) {
        return this.service.findList(data);
    }

}
