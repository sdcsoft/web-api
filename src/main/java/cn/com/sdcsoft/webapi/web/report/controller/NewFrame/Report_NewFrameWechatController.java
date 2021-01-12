package cn.com.sdcsoft.webapi.web.report.controller.NewFrame;


import cn.com.sdcsoft.webapi.web.report.controller.NewFrame.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping(value = "/webapi/report/newframe")
public class Report_NewFrameWechatController {

    @Autowired
    @Qualifier(value = "primaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/wechat/mock", method = RequestMethod.GET)
    public Result wechatMock(String deviceNo, String begintime, String endtime, String key,String type) throws Exception {
        List<Object> dataList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        Date startTime = strToDate(begintime + " 00:00:00");
        Date endTime = strToDate(endtime + " 00:00:00");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("deviceNo").is(deviceNo).and("createDate").gte(startTime).lte(endTime)),
                Aggregation.unwind(type),
                Aggregation.match(Criteria.where(type+".name").is(key)),
                Aggregation.project("deviceNo","createDate",type+".v").andExclude("_id")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "deviceruninfo", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        DecimalFormat df = new DecimalFormat("#.00");
        if (mappedResults != null && mappedResults.size() > 0) {
            for (int i = 0; i < mappedResults.size(); i++) {
                dateList.add(mappedResults.get(i).get("createDate"));
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
