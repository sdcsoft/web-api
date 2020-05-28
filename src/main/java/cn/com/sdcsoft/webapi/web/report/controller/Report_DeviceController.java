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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/webapi/report/device")
public class Report_DeviceController {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @RequestMapping(value = "/wechat/mock", method = RequestMethod.GET)
    public Result wechatMock(String deviceNo, String begintime, String endtime, String key) throws Exception {
        List<Object> dataList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();


        Date startTime = strToDate(begintime + " 00:00:00");
        Date endTime = strToDate(endtime + " 00:00:00");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.unwind(key),
                Aggregation.project(key + ".date", key + ".avg").andExclude("_id")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
        List<Map> mappedResults = result.getMappedResults();
        DecimalFormat df = new DecimalFormat("#.00");

        if (mappedResults != null && mappedResults.size() > 0) {
            for (int i = 0; i < mappedResults.size(); i++) {
                dateList.add(mappedResults.get(i).get("date").toString());
                avgList.add(Double.parseDouble(df.format(mappedResults.get(i).get("avg"))));
            }
            dataList.add(avgList);
            Map<String, List> map = new HashMap<>();
            map.put("date", dateList);
            map.put("data", dataList);
            return Result.getSuccessResult(map);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }

    }

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
            calendar.add(Calendar.DATE, 1);
            endTime = calendar.getTime();
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                    Aggregation.unwind(key),
                    Aggregation.project(key + ".date", key + ".avg", key + ".max", key + ".min").andExclude("_id")
            );
            AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
            List<Map> mappedResults = result.getMappedResults();
            DecimalFormat df = new DecimalFormat("#.00");

            if (mappedResults != null && mappedResults.size() > 0) {
                for (int i = 0; i < mappedResults.size(); i++) {
                    dateList.add(mappedResults.get(i).get("date").toString());
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
        List<Object> dataList = new ArrayList<>();
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.group("DeviceNo").push("" + key).as("list")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);


        List<Map> mappedResults = result.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            for (int i = 0; i < mappedResults.size(); i++) {
                ArrayList list = (ArrayList) mappedResults.get(i).get("list");
                if (list.size() > 0) {
                    Map<Object, Object> map = new HashMap<>();
                    map.put("deviceNo", mappedResults.get(i).get("_id"));
                    ArrayList allList = new ArrayList();
                    for (int k = 0; k < list.size(); k++) {
                        allList.addAll((ArrayList) list.get(k));
                    }
                    map.put(key, allList);
                    dataList.add(map);
                }
            }
            return Result.getSuccessResult(dataList);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }

    @RequestMapping(value = "/exceptions", method = RequestMethod.GET)
    public Result exceptions(String deviceNo, long timestamp, int day) throws Exception {
        List<Object> exList = new ArrayList<>();
        List<Object> sumList = new ArrayList<>();
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);

        if (day == 0) {
            calendar.add(Calendar.DATE, 1);
            Date endTime = calendar.getTime();
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
            calendar.add(Calendar.DATE, day);
            Date endTime = calendar.getTime();
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

    public Date parse(String str, String pattern, Locale locale) {
        if (str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}