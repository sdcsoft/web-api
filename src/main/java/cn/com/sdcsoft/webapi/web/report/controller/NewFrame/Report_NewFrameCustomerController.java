package cn.com.sdcsoft.webapi.web.report.controller.NewFrame;


import cn.com.sdcsoft.webapi.web.report.controller.NewFrame.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/webapi/report/newframe/customer")
public class Report_NewFrameCustomerController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/wechat/mock", method = RequestMethod.GET)
    public Result wechatMock(String deviceNo, String begintime, String endtime, String key,String type) throws Exception {
        List<Object> dataList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        Date startTime = strToDate(begintime + " 00:00:00");
        Date endTime = strToDate(endtime + " 00:00:00");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.unwind(type),
                Aggregation.match(Criteria.where(type+".name").is(key)),
                Aggregation.project("DeviceNo","CreateDate",type+".v").andExclude("_id")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        DecimalFormat df = new DecimalFormat("#.00");
        if (mappedResults != null && mappedResults.size() > 0) {
            for (int i = 0; i < mappedResults.size(); i++) {
                dateList.add(mappedResults.get(i).get("CreateDate"));
                dataList.add(mappedResults.get(i).get("v"));
            }
            Map<String, List> result = new HashMap<>();
            result.put("date", dateList);
            result.put("data", dataList);
            return Result.getSuccessResult(result);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }

    @RequestMapping(value = "/mock", method = RequestMethod.GET)
    public Result mock(String deviceNo, String key, long timestamp, int day,String type) throws Exception {
        List<Object> dataList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.unwind(type),
                Aggregation.match(Criteria.where(type+".name").is(key)),
                Aggregation.project("DeviceNo","CreateDate",type+".v").andExclude("_id")
        );
            AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);
            List<Map> mappedResults = results.getMappedResults();
            if (mappedResults != null && mappedResults.size() > 0) {
                for (int i = 0; i < mappedResults.size(); i++) {
                    dateList.add(mappedResults.get(i).get("CreateDate"));
                    dataList.add(mappedResults.get(i).get("v"));
                }
                Map<String, List> result = new HashMap<>();
                result.put("date", dateList);
                result.put("data", dataList);
                return Result.getSuccessResult(result);
            } else {
                return Result.getFailResult("未能查询到符合条件的数据");
            }

    }

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public Result exception(String deviceNo,long timestamp, int day) throws Exception {
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.unwind("bj"),
                Aggregation.group("bj.name").count().as("Count")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);


        List<Map> mappedResults = result.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {

            return Result.getSuccessResult(mappedResults);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }

    @RequestMapping(value = "/exceptions", method = RequestMethod.GET)
    public Result exceptions(Integer orgId, long timestamp, int day) throws Exception {
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("EnterpriseId").is(orgId).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.unwind("bj"),
                Aggregation.group("bj.name").count().as("Count")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);
        List<Map> mappedResults = result.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            List<Object> exList = new ArrayList<>();
            List<Object> sumList = new ArrayList<>();
            for (int i = 0; i < mappedResults.size(); i++) {
                exList.add( mappedResults.get(i).get("_id"));
                sumList.add(mappedResults.get(i).get("Count"));
            }
            Map<String, List> map = new HashMap<>();
            map.put("exList", exList);
            map.put("sumList", sumList);
            return Result.getSuccessResult(map);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }

    }

    @RequestMapping(value = "/exception/devices", method = RequestMethod.GET)
    public Result customerDevicesCausedTheException(Integer orgId, String key, long timestamp, int day) throws Exception {
        Date startTime = new Date(timestamp);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, day);
        Date endTime = calendar.getTime();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("EnterpriseId").is(orgId).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.unwind("bj"),
                Aggregation.match(Criteria.where("bj.name").is(key)),
                Aggregation.project("DeviceNo","CreateDate","bj.name").andExclude("_id")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "newframehourdata", Map.class);

        List<Map> mappedResults = result.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            return Result.getSuccessResult(mappedResults);
        } else {
            return Result.getFailResult("未能查询到符合条件的数据");
        }
    }
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
}
