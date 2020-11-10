package cn.com.sdcsoft.webapi.web.report.controller.NewFrame;


import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_ProductMapper;
import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_ProductMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.entity.Product;
import cn.com.sdcsoft.webapi.web.report.controller.NewFrame.entity.Result;
import cn.com.sdcsoft.webapi.web.report.controller.NewFrame.entity.TypeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/webapi/report/baidudata")
public class Report_BaiDuDataController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    Customer_DB_ProductMapper productMapper;


    @Autowired
    Enterprise_DB_ProductMapper enterprise_db_productMapper;
    @GetMapping("/enterprise/product/orgId")
    public Result findEnterpriseProductByOrgId(int orgId) {
        List<cn.com.sdcsoft.webapi.web.enterprisemanage.entity.Product> list=enterprise_db_productMapper.findProductsByOrgId(orgId);
        List<Object> mapData=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Map<String, Object> map = new HashMap<>();
            List<Double> coord=new ArrayList<>();
            if(list.get(i).getLatitude()!=null){
                coord.add(Double.parseDouble(list.get(i).getLongitude()));
                coord.add(Double.parseDouble(list.get(i).getLatitude()));
                map.put("coord",coord);
                map.put("name",list.get(i).getBoilerNo());
                mapData.add(map);
            }
        }
        Map<String, List> mapResult = new HashMap<>();
        mapResult.put("mapData",mapData);
        return Result.getSuccessResult(mapResult);
    }

    @GetMapping("/enterprise/product/online")
    public Result findEnterpriseProductOnline(int orgId) {
        Map<String, Object> result = new HashMap<>();
        result.put("name","在线");
        result.put("value",enterprise_db_productMapper.findProductsByOrgId(orgId).size());
        Map<String, Object> result1 = new HashMap<>();
        result1.put("name","离线");
        result1.put("value",0);
        List<Object> list = new ArrayList<>();
        list.add(result);
        list.add(result1);
        return Result.getSuccessResult(list);
    }
    @GetMapping("/enterprise/Customer/orgId")
    public Result findCustomerByOrgId(int orgId) {
        List<TypeResult> list=enterprise_db_productMapper.findCustomerByOrgId(orgId);
        List<Object> dataList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName()!=null){
                dateList.add(list.get(i).getName());
                dataList.add(list.get(i).getValue());
            }

        }
        Map<String, Object> serie = new HashMap<>();
        serie.put("data", dataList);
        serie.put("name", "设备数量");
        List<Object> series = new ArrayList<>();
        series.add(serie);
        Map<String, List> result = new HashMap<>();
        result.put("series", series);
        result.put("categories", dateList);
        return Result.getSuccessResult(result);
    }
    @GetMapping("/enterprise/product/type")
    public Result findEnterpriseProductType(int orgId) {
        List<TypeResult> list= enterprise_db_productMapper.findProductByType(orgId);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName()==null){
                list.remove(i);
            }
        }
        return Result.getSuccessResult(list);
    }

    @GetMapping("/product/orgId")
    public Result findProductByOrgId(int orgId) {
        List<Product> list=productMapper.findProductsByOrgId(orgId);
        List<Object> mapData=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Map<String, Object> map = new HashMap<>();
            List<Double> coord=new ArrayList<>();
            if(list.get(i).getLatitude()!=null){
                coord.add(Double.parseDouble(list.get(i).getLongitude()));
                coord.add(Double.parseDouble(list.get(i).getLatitude()));
                map.put("coord",coord);
                map.put("name",list.get(i).getBoilerNo());
                mapData.add(map);
            }
        }
        Map<String, List> mapResult = new HashMap<>();
        mapResult.put("mapData",mapData);
        return Result.getSuccessResult(mapResult);
    }

    @GetMapping("/product/online")
    public Result findProductOnline(int orgId) {
        Map<String, Object> result = new HashMap<>();
        result.put("name","在线");
        result.put("value",productMapper.findProductsByOrgId(orgId).size());
        Map<String, Object> result1 = new HashMap<>();
        result1.put("name","离线");
        result1.put("value",0);
        List<Object> list = new ArrayList<>();
        list.add(result);
        list.add(result1);
        return Result.getSuccessResult(list);
    }

    @GetMapping("/product/type")
    public Result findProductType(int orgId) {

        return Result.getSuccessResult(productMapper.findProductByType(orgId));
    }

    @RequestMapping(value = "/orgId/bj", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Result bj(Integer orgId) throws Exception {
        List<Object> dataList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("CustomerId").is(orgId)),
                Aggregation.unwind("bj"),
                Aggregation.group("bj.name").count().as("data"),
                Aggregation.project("data").and("_id").as("name").andExclude("_id")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            for (int i = 0; i < mappedResults.size(); i++) {
                dateList.add(mappedResults.get(i).get("name"));
                dataList.add(mappedResults.get(i).get("data"));
            }
            Map<String, Object> serie = new HashMap<>();
            serie.put("data", dataList);
            serie.put("name", "报警次数");
            List<Object> series = new ArrayList<>();
            series.add(serie);
            Map<String, List> result = new HashMap<>();
            result.put("series", series);
            result.put("categories", dateList);
            return Result.getSuccessResult(result);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }


    @RequestMapping(value = "/test/mock", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Result mocktest(String deviceNo, String type) throws Exception {
        List<Object> dateList = new ArrayList<>();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("DeviceNo").is(deviceNo)),
                Aggregation.unwind(type),
                Aggregation.group(type+".name").push(type+".v").as("data"),
                Aggregation.project("data").and("_id").as("name").andExclude("_id")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo)),
                    Aggregation.sort(Sort.Direction.ASC, "CreateDate"),
                    Aggregation.project("CreateDate").andExclude("_id")
            );
            results = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);
            List<Map> mappedResults1 = results.getMappedResults();
            Map<String, List> result = new HashMap<>();
            SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
            for (int i = 0; i < mappedResults1.size(); i++) {
                dateList.add(sfEnd.format(sfStart.parse(mappedResults1.get(i).get("CreateDate").toString())));
            }
            result.put("categories", dateList);
            result.put("series", mappedResults);
            return Result.getSuccessResult(result);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }
}
