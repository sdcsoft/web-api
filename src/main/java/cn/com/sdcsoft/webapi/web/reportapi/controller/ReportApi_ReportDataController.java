package cn.com.sdcsoft.webapi.web.reportapi.controller;


import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.web.reportapi.entity.MockInfo;
import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
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

    private static final DecimalFormat df = new DecimalFormat("0.00");//保留两位小数点
    //exceptionInfo
    //mockInfo
    @RequestMapping(value = "/device/mock", method = RequestMethod.GET)
    public Result query(String deviceNo, String key,long timestamp,int day) throws Exception{
        List<Object> data=new ArrayList<Object>();
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
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate").gte(startTime).lte(endTime)),
                Aggregation.unwind(key),
                Aggregation.group("CreateDate").avg(key+".avg").as("avg").max(key+".max").as("max").min(key+".min").as("min"),
                Aggregation.sort(Sort.Direction.ASC, "_id")
        );
        AggregationResults<Map> results= mongoTemplate.aggregate(aggregation, "dayinfos", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        if (mappedResults != null && mappedResults.size() > 0) {
            for(int i=0;i<mappedResults.size();i++){
                dateList.add(mappedResults.get(i).get("_id"));
                avgList.add(mappedResults.get(i).get("max"));
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