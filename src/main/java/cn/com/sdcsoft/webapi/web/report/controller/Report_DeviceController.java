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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/webapi/report/device")
public class Report_DeviceController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/mock", method = RequestMethod.GET)
    public Result mock(String deviceNo, String key, long timestamp, int day) throws Exception {
        List<Object> dataList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        List<Object> maxList = new ArrayList<>();
        List<Object> minList = new ArrayList<>();

        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();
        if (day == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.unwind(key),
                    Aggregation.project(key + ".date", key + ".avg", key + ".max", key + ".min").andExclude("_id")
            );
            AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = result.getMappedResults();
            DecimalFormat df = new DecimalFormat("#.00");
            if (mappedResults != null && mappedResults.size() > 0) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:00");
                for (int i = 0; i < mappedResults.size(); i++) {
                    dateList.add(formatter.format(sdf.parse(mappedResults.get(i).get("date").toString())));
                    avgList.add(Double.parseDouble(df.format(mappedResults.get(i).get("avg"))));
                    maxList.add(mappedResults.get(i).get("max"));
                    minList.add(mappedResults.get(i).get("min"));
                }
                dataList.add(avgList);
                dataList.add(maxList);
                dataList.add(minList);
                Map<String, List> map = new HashMap<>();
                map.put("date", dateList);
                map.put("data", dataList);
                return Result.getSuccessResult(map);
            } else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        } else {
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.unwind(key),
                    Aggregation.group("CreateDate").avg(key + ".avg").as("avg").max(key + ".max").as("max").min(key + ".min").as("min"),
                    Aggregation.sort(Sort.Direction.ASC, "_id")
            );
            AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            DecimalFormat df = new DecimalFormat("#.00");
            if (mappedResults != null && mappedResults.size() > 0) {
                for (int i = 0; i < mappedResults.size(); i++) {
                    dateList.add(mappedResults.get(i).get("_id"));
                    avgList.add(Double.parseDouble(df.format((Double) mappedResults.get(i).get("avg"))));
                    maxList.add(mappedResults.get(i).get("max"));
                    minList.add(mappedResults.get(i).get("min"));
                }
                dataList.add(avgList);
                dataList.add(maxList);
                dataList.add(minList);
                Map<String, List> result = new HashMap<>();
                result.put("date", dateList);
                result.put("data", dataList);
                return Result.getSuccessResult(result);
            } else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }
    }

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public Result exception(String deviceNo, String key, long timestamp, int day) throws Exception {
        List<Object> dateList = new ArrayList<>();
        List<Object> sumList = new ArrayList<>();
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();
        if (day == 0) {
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.sort(Sort.Direction.ASC, "CreateDate")
            );
            AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = result.getMappedResults();
            if (mappedResults != null && mappedResults.size() > 0) {
                ArrayList list = (ArrayList) mappedResults.get(0).get(key);
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        sumList.add(list.get(i));
                    }
                    Map<String, List> map = new HashMap<>();
                    map.put("data", sumList);
                    return Result.getSuccessResult(map);
                } else {
                    return Result.getFailResult("未能查询到符合条件的数据");
                }
            } else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        } else {
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.sort(Sort.Direction.ASC, "CreateDate")
            );
            AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            if (mappedResults != null && mappedResults.size() > 0) {
                for (int i = 0; i < mappedResults.size(); i++) {
                    ArrayList map = (ArrayList) mappedResults.get(i).get(key);
                    if (map != null) {
                        dateList.add(mappedResults.get(i).get("CreateDate"));
                        sumList.add(map.size());
                    }
                }
                Map<String, List> result = new HashMap<>();
                result.put("date", dateList);
                result.put("data", sumList);
                return Result.getSuccessResult(result);
            } else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }
    }

    @RequestMapping(value = "/exceptions", method = RequestMethod.GET)
    public Result exceptions(String deviceNo, long timestamp, int day) throws Exception {
        List<Object> exList = new ArrayList<>();
        List<Object> sumList = new ArrayList<>();
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();
        if (day == 0) {
            Query query = Query.query(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime));
            String mapFuncString = "function(){for(var key in this){if(key.substr(0, 2)==\"ex\"){emit(key,this[key]);}}}";
            String reduceFuncString = "function(key,val){return Array}";
            MapReduceResults<Map> result = mongoTemplate.mapReduce(query, "dayinfos",
                    mapFuncString,
                    reduceFuncString,
                    Map.class);
            for (Map m : result) {
                exList.add(m.get("_id"));
                sumList.add(m.get("value"));
            }
            Map<String, List> map = new HashMap<>();
            map.put("exList", exList);
            map.put("sumList", sumList);
            return Result.getSuccessResult(map);
        } else {
            Query query = Query.query(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime));
            String mapFuncString = "function(){for(var key in this){if(key.substr(0, 2)==\"ex\"){emit(key,this[key].length);}}}";
            String reduceFuncString = "function(key,val){return Array.sum(val)}";
            MapReduceResults<Map> result = mongoTemplate.mapReduce(query, "dayinfos", mapFuncString, reduceFuncString, Map.class);
            for (Map m : result) {
                exList.add(m.get("_id"));
                sumList.add(m.get("value"));
            }
            Map<String, List> map = new HashMap<String, List>();
            map.put("exList", exList);
            map.put("sumList", sumList);
            return Result.getSuccessResult(map);
        }

    }
}