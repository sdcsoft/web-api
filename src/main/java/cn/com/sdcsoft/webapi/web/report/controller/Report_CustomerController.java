package cn.com.sdcsoft.webapi.web.report.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/webapi/report/customer")
public class Report_CustomerController {

    @Autowired
    private MongoTemplate mongoTemplate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public Result exception(int CustomerId, String key, long timestamp, int day) {
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime))
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
        List<Map> mappedResults = result.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            int esCount = 0;
            for (int i = 0; i < mappedResults.size(); i++) {
                ArrayList list = (ArrayList) mappedResults.get(i).get(key);
                if (list != null) {
                    esCount = esCount + list.size();
                }
            }
            Map<String, Integer> map = new HashMap<>();
            map.put(key, esCount);
            return Result.getSuccessResult(map);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }

    @RequestMapping(value = "/exceptions", method = RequestMethod.GET)
    public Result exceptions(int CustomerId, long timestamp, int day) throws Exception {
        List<Object> exList = new ArrayList<>();
        List<Object> sumList = new ArrayList<>();
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();

        Query query = Query.query(Criteria.where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime));
        String map = "function(){for(var key in this){if(key.substr(0, 2)==\"ex\"){emit(key,this[key].length);}}}";
        String reduce = "function(key,val){return Array.sum(val)}";
        MapReduceResults<Map> results = mongoTemplate.mapReduce(query, "dayinfos", map, reduce, Map.class);
        for (Map m : results) {
            exList.add(m.get("_id"));
            sumList.add(m.get("value"));
        }
        Map<String, List> result = new HashMap<>();
        result.put("exList", exList);
        result.put("sumList", sumList);
        return Result.getSuccessResult(result);

    }

    @RequestMapping(value = "/exception/devices", method = RequestMethod.GET)
    public Result customerDevicesCausedTheException(int CustomerId, String key, long timestamp, int day) throws Exception {
        List<Object> dataList = new ArrayList<>();
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.group("DeviceNo").addToSet("" + key).as("list")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);

        List<Map> mappedResults = result.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            for (int i = 0; i < mappedResults.size(); i++) {
                ArrayList list = (ArrayList) mappedResults.get(i).get("list");
                if (list.size() > 0) {
                    Map<Object, Object> map = new HashMap<>();
                    map.put("deviceNo",mappedResults.get(i).get("_id"));
                    map.put(key, list.get(0));
                    dataList.add(map);
                }
            }
            return Result.getSuccessResult(dataList);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }
}