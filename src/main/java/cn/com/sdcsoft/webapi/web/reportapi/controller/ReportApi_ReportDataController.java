package cn.com.sdcsoft.webapi.web.reportapi.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Document;
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
@RequestMapping(value = "/webapi/report")
public class ReportApi_ReportDataController {

    @Autowired
    private  MongoTemplate mongoTemplate;

    //exceptionInfo
    //mockInfo

    @RequestMapping(value = "/device/mock", method = RequestMethod.GET)
    public Result finddevicebymock(String deviceNo, String key,long timestamp,int day) throws Exception{
        List<Object> dataList=new ArrayList<Object>();
        List<Object> dateList=new ArrayList<Object>();
        List<Object> avgList=new ArrayList<Object>();
        List<Object> maxList=new ArrayList<Object>();
        List<Object> minList=new ArrayList<Object>();
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =format.parse(result1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE,day);
        Date endTime = calendar.getTime();
        if(day==0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.unwind(key),
                    Aggregation.project(key+".date",key+".avg",key+".max",key+".min").andExclude("_id")
            );
            AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
            if (mappedResults != null && mappedResults.size() > 0) {
                SimpleDateFormat formatter = new SimpleDateFormat( "HH:00");
                for(int i=0;i<mappedResults.size();i++){
                    dateList.add(formatter.format(sdf.parse(mappedResults.get(i).get("date").toString())));
                    avgList.add(Double.parseDouble(df.format(mappedResults.get(i).get("avg"))));
                    maxList.add(mappedResults.get(i).get("max"));
                    minList.add(mappedResults.get(i).get("min"));
                }
                dataList.add(avgList);
                dataList.add(maxList);
                dataList.add(minList);
                Map<String, List> result = new HashMap<String, List>();
                result.put("date",dateList);
                result.put("data",dataList);
                return Result.getSuccessResult(result);
            }else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }else{
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.unwind(key),
                    Aggregation.group("CreateDate").avg(key+".avg").as("avg").max(key+".max").as("max").min(key+".min").as("min"),
                    Aggregation.sort(Sort.Direction.ASC, "_id")
            );
            AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
            if (mappedResults != null && mappedResults.size() > 0) {
                for(int i=0;i<mappedResults.size();i++){
                    dateList.add(mappedResults.get(i).get("_id"));
                    avgList.add(Double.parseDouble(df.format((Double)mappedResults.get(i).get("avg"))));
                    maxList.add(mappedResults.get(i).get("max"));
                    minList.add(mappedResults.get(i).get("min"));
                }
                dataList.add(avgList);
                dataList.add(maxList);
                dataList.add(minList);
                Map<String, List> result = new HashMap<String, List>();
                result.put("date",dateList);
                result.put("data",dataList);
                return Result.getSuccessResult(result);
            }else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }
    }

    @RequestMapping(value = "/device/exception", method = RequestMethod.GET)
    public Result finddevicesbyexception(String deviceNo, String key,long timestamp,int day) throws Exception{
        List<Object> dateList=new ArrayList<Object>();
        List<Object> sumList=new ArrayList<Object>();
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =format.parse(result1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE,day);
        Date endTime = calendar.getTime();
        if(day==0){
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.sort(Sort.Direction.ASC, "CreateDate")
            );
            AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            if (mappedResults != null && mappedResults.size() > 0) {
                ArrayList map=(ArrayList)mappedResults.get(0).get(key);
                if(map!=null){
                    for(int i=0;i<map.size();i++){
                        sumList.add(map.get(i));
                    }
                    Map<String, List> result = new HashMap<String, List>();
                    result.put("data",sumList);
                    return Result.getSuccessResult(result);
                }else{
                    return Result.getFailResult("未能查询到符合条件的数据");
                }
            }else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }else{
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.sort(Sort.Direction.ASC, "CreateDate")
            );
            AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            if (mappedResults != null && mappedResults.size() > 0) {
                for(int i=0;i<mappedResults.size();i++){
                    ArrayList  map=(ArrayList)mappedResults.get(i).get(key);
                    if(map!=null){
                        dateList.add(mappedResults.get(i).get("CreateDate"));
                        sumList.add(map.size());
                    }
                }
                Map<String, List> result = new HashMap<String, List>();
                result.put("date",dateList);
                result.put("data",sumList);
                return Result.getSuccessResult(result);
            }else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }
    }

    @RequestMapping(value = "/device/exceptions", method = RequestMethod.GET)
    public Result finddevicesbyexceptions(String deviceNo, long timestamp,int day) throws Exception{
        List<Object> exList=new ArrayList<Object>();
        List<Object> sumList=new ArrayList<Object>();
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =format.parse(result1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE,day);
        Date endTime = calendar.getTime();
        if(day==0){
            Query query = Query.query(new Criteria().where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime));
            String map = "function(){for(var key in this){if(key.substr(0, 2)==\"ex\"){emit(key,this[key]);}}}";
            String reduce = "function(key,val){return Array}";
            MapReduceResults<Map> results = mongoTemplate.mapReduce(query,"dayinfos", map, reduce, Map.class);
            for (Map map1 : results) {
                exList.add(map1.get("_id"));
                sumList.add(map1.get("value"));
            }
            Map<String, List> result = new HashMap<String, List>();
            result.put("exList",exList);
            result.put("sumList",sumList);
            return Result.getSuccessResult(result);
        }else {
            Query query = Query.query(new Criteria().where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime));
            String map = "function(){for(var key in this){if(key.substr(0, 2)==\"ex\"){emit(key,this[key].length);}}}";
            String reduce = "function(key,val){return Array.sum(val)}";
            MapReduceResults<Map> results = mongoTemplate.mapReduce(query,"dayinfos", map, reduce, Map.class);
            for (Map map1 : results) {
                exList.add(map1.get("_id"));
                sumList.add(map1.get("value"));
            }
            Map<String, List> result = new HashMap<String, List>();
            result.put("exList",exList);
            result.put("sumList",sumList);
            return Result.getSuccessResult(result);
        }

        }

    @RequestMapping(value = "/Customer/exception", method = RequestMethod.GET)
    public Result findCustomerbyexception(int CustomerId, String key, long timestamp,int day) throws Exception{
        List<Object> dateList=new ArrayList<Object>();
        List<Object> sumList=new ArrayList<Object>();
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =format.parse(result1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE,day);
        Date endTime = calendar.getTime();
        if(day==0){
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime))
                    //Aggregation.group("ex_jixiangaoshuiweibaojing.size").count().as("count")
            );
            AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            if (mappedResults != null && mappedResults.size() > 0) {
                int esCount=0;
                for(int i=0;i<mappedResults.size();i++){
                    ArrayList map=(ArrayList)mappedResults.get(i).get(key);
                    if(map!=null){
                        esCount=esCount+map.size();
                    }
                }
                Map<String, Integer> result = new HashMap<String, Integer>();
                result.put(key,esCount);
                return Result.getSuccessResult(result);
            }else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }else{
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime))
            );
            AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            if (mappedResults != null && mappedResults.size() > 0) {
                int esCount=0;
                for(int i=0;i<mappedResults.size();i++){
                    ArrayList map=(ArrayList)mappedResults.get(i).get(key);
                    if(map!=null){
                        esCount=esCount+map.size();
                    }
                }
                Map<String, Integer> result = new HashMap<String, Integer>();
                result.put(key,esCount);
                return Result.getSuccessResult(result);
            }else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }
        }
    }

    @RequestMapping(value = "/Customer/exceptions", method = RequestMethod.GET)
    public Result findCustomerbyexceptions(int CustomerId, long timestamp,int day) throws Exception{
        List<Object> exList=new ArrayList<Object>();
        List<Object> sumList=new ArrayList<Object>();
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =format.parse(result1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE,day);
        Date endTime = calendar.getTime();
        if(day==0){
            Query query = Query.query(new Criteria().where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime));
            String map = "function(){for(var key in this){if(key.substr(0, 2)==\"ex\"){emit(key,this[key].length);}}}";
            String reduce = "function(key,val){return Array.sum(val)}";
            MapReduceResults<Map> results = mongoTemplate.mapReduce(query,"dayinfos", map, reduce, Map.class);
            for (Map map1 : results) {
                exList.add(map1.get("_id"));
                sumList.add(map1.get("value"));
            }
            Map<String, List> result = new HashMap<String, List>();
            result.put("exList",exList);
            result.put("sumList",sumList);
            return Result.getSuccessResult(result);
        }else {
            Query query = Query.query(new Criteria().where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime));
            String map = "function(){for(var key in this){if(key.substr(0, 2)==\"ex\"){emit(key,this[key].length);}}}";
            String reduce = "function(key,val){return Array.sum(val)}";
            MapReduceResults<Map> results = mongoTemplate.mapReduce(query,"dayinfos", map, reduce, Map.class);
            for (Map map1 : results) {
                exList.add(map1.get("_id"));
                sumList.add(map1.get("value"));
            }
            Map<String, List> result = new HashMap<String, List>();
            result.put("exList",exList);
            result.put("sumList",sumList);
            return Result.getSuccessResult(result);
        }

    }

    @RequestMapping(value = "/Customer/exception/devices", method = RequestMethod.GET)
    public Result findCustomerexceptionbydevices(int CustomerId, String key, long timestamp,int day) throws Exception{
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =format.parse(result1);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE,day);
        Date endTime = calendar.getTime();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("CustomerId").is(CustomerId).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.group("DeviceNo").addToSet(""+key).as("list")
        );
        AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            Map<Object, Object> result = new HashMap<Object, Object>();
            for(int i=0;i<mappedResults.size();i++){
                ArrayList map=(ArrayList)mappedResults.get(i).get("list");
                if(map.size()>0){
                    result.put(mappedResults.get(i).get("_id"),mappedResults.get(i).get("list"));
                }
            }
            return Result.getSuccessResult(result);
        }else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }
}