package cn.com.sdcsoft.webapi.web.report.core;

import cn.com.sdcsoft.webapi.commcontroller.ExportExcelController;
import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.bson.BsonUndefined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;

@RestController
@RequestMapping("/webapi/report/core/devices")
public class DevicesController extends ExportExcelController {

    class OffLineInfo implements Serializable {

        public OffLineInfo(String date, String count) {
            this.date = date;
            this.count = count;
        }

        String date, count;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    private static final String Col_DeviceNo = "deviceNo";
    private static final String Col_Power = "power";
    private static final String Col_Media = "media";
    private static final String Col_Count = "count";
    private static final String Col_OnLineDate = "onLineDate";

    private static final String CollectionName= "deviceruninfo";

    private HashMap<Integer, String> PowerMap = new HashMap<>(6);
    private HashMap<Integer, String> MediaMap = new HashMap<>(5);

    public DevicesController() {
        PowerMap.put(0, "油气");
        PowerMap.put(1, "电");
        PowerMap.put(2, "煤");
        PowerMap.put(3, "生物质");
        PowerMap.put(4, "余热");
        PowerMap.put(5, "板换");

        MediaMap.put(0, "热水");
        MediaMap.put(1, "蒸汽");
        MediaMap.put(2, "导热油");
        MediaMap.put(3, "热风");
        MediaMap.put(4, "真空");
    }

    @Autowired
    @Qualifier(value = "primaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Autowired
    LAN_API lan_api;

//    /**
//     * 统计所有设备每天的上线次数
//     * 5分钟采集一次
//     *
//     * @return
//     */
//    @RequestMapping(value = "/online/all", method = RequestMethod.GET)
//    public Result allDevicesOnlineInfo() {
//        DateOperators.DateOperatorFactory f = new DateOperators.DateOperatorFactory("createDate");
//        f = f.withTimezone(DateOperators.Timezone.valueOf("Asia/Shanghai"));
//        DateOperators.DateToString dts = f.toString("%Y-%m-%d");
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.project("deviceNo", "power", "media", "createDate").and(dts).as("onLineDate"),
//                Aggregation.group("deviceNo", "power", "media", "onLineDate").count().as("count"),
//                Aggregation.project("deviceNo", "power", "media", "onLineDate", "count").and("group").previousOperation(),
//                Aggregation.sort(Sort.Direction.ASC, "onLineDate")
//        );
//        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "deviceruninfo0", Map.class);
//        List<Map> mappedResults = result.getMappedResults();
//        return Result.getSuccessResult(mappedResults);
//    }

    /**
     * 统计设备每天的上线次数,5分钟采样数据库
     *
     * @param deviceNo 设备编号，为空时统计全部设备信息
     * @return
     */
    @RequestMapping(value = {"/online", "/online/{deviceNo}"}, method = RequestMethod.GET)
    public Result deviceOnlineInfo(@PathVariable(value = "deviceNo",required = false) String deviceNo) {

        DateOperators.DateOperatorFactory f = new DateOperators.DateOperatorFactory("createDate");
        f = f.withTimezone(DateOperators.Timezone.valueOf("Asia/Shanghai"));
        DateOperators.DateToString dts = f.toString("%Y-%m-%d");

        Aggregation aggregation;

        if (StringUtils.isEmpty(deviceNo)) {
            aggregation = Aggregation.newAggregation(
                    Aggregation.project("deviceNo", "power", "media", "createDate").and(dts).as("onLineDate"),
                    Aggregation.group("deviceNo", "power", "media", "onLineDate").count().as("count"),
                    Aggregation.project("deviceNo", "power", "media", "onLineDate", "count").and("group").previousOperation(),
                    Aggregation.sort(Sort.Direction.ASC, "onLineDate")
            );
        } else {
            aggregation = Aggregation.newAggregation(
                    Aggregation.project("deviceNo", "createDate").and(dts).as("onLineDate"),
                    Aggregation.group("deviceNo", "onLineDate").count().as("count"),
                    Aggregation.project("deviceNo", "onLineDate", "count").and("group").previousOperation(),
                    Aggregation.sort(Sort.Direction.ASC, "onLineDate"),
                    Aggregation.match(Criteria.where("deviceNo").is(deviceNo))
            );
        }

        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, CollectionName, Map.class);
        List<Map> mappedResults = result.getMappedResults();
        return Result.getSuccessResult(mappedResults);
    }

    /**
     * 统计燃料、介质类型多于1的设备编号
     *
     * @return
     */
    @RequestMapping(value = "/online/ex", method = RequestMethod.GET)
    public Result ex() {
        DateOperators.DateOperatorFactory f = new DateOperators.DateOperatorFactory("createDate");
        f = f.withTimezone(DateOperators.Timezone.valueOf("Asia/Shanghai"));
        DateOperators.DateToString dts = f.toString("%Y-%m-%d");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("deviceNo", "power", "media", "createDate").and(dts).as("onLineDate"),
                Aggregation.group("deviceNo", "power", "media").count().as("count"),
                Aggregation.project("deviceNo", "power", "media", "count").and("group").previousOperation()
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, CollectionName, Map.class);
        List<Map> mappedResults = result.getMappedResults();

        Set<String> a = new HashSet<>();
        Set<String> b = new HashSet<>();
        for (Map m : mappedResults) {
            if (StringUtils.isEmpty(m.get(Col_DeviceNo))) {
                continue;
            }
            if (a.contains(m.get(Col_DeviceNo).toString())) {
                b.add(m.get(Col_DeviceNo).toString());
            } else {
                a.add(m.get(Col_DeviceNo).toString());
            }
        }

        return Result.getSuccessResult(b);
    }


    /**
     * 统计所有设备每天的上线次数
     * 5分钟采集一次
     *
     * @param response
     */
    @RequestMapping(value = "/online/all5/excel", method = RequestMethod.GET)
    public void allDevicesOnlineInfoExcel5(HttpServletResponse response) {
        Result result = deviceOnlineInfo(null);
        if (Result.RESULT_CODE_SUCCESS != result.getCode()) {
            return;
        }
        List<Map> list = (List<Map>) result.getData();
        if (null == list) {
            return;
        }
        getOnlineDevicesExcel("五分钟设备在线统计信息", list, response);
    }

    /**
     * 统计所有设备每天的上线次数
     * 1小时采集一次
     *
     * @param response
     */
    @RequestMapping(value = "/online/all60/excel", method = RequestMethod.GET)
    public void allDevicesOnlineInfoExcel60(HttpServletResponse response) {
        String mapFuncString = "function() {\n" +
                "var date = new Date(this.CreateDate)\n" +
                "    var y = date.getFullYear()\n" +
                "    var m = date.getMonth() + 1\n" +
                "    var d = date.getDate()\n" +
                "\n" +
                "    for (var key in this) {\n" +
                "        if (key.substr(0, 2) == 'mo') {\n" +
                "            emit(this._id, {\n" +
                "                \"deviceNo\": this.DeviceNo,\n" +
                "                \"power\": this.power == undefined?-1:this.power,\n" +
                "                \"media\": this.media == undefined?-1:this.media,\n" +
                "                \"onLineDate\": y + '-' + m + '-' + d,\n" +
                "                \"count\": this[key].length\n" +
                "            })\n" +
                "            break;\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String reduceFuncString = "function(k, vs) {\n" +
                "    return vs\n" +
                "}";
        MapReduceResults<Map> result = mongoTemplate.mapReduce("dayinfos",
                mapFuncString,
                reduceFuncString,
                Map.class);

        if (null == result) {
            return;
        }
        //exportDevicesOnlineInfoExcel("每小时设备在线统计信息", result, response);
        //实例化HSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet("在线统计");
        HSSFRow title_row = sheet.createRow(0);//模拟量标题行
        title_row.createCell(0).setCellValue("设备");
        title_row.createCell(1).setCellValue("燃料");
        title_row.createCell(2).setCellValue("介质");
        title_row.createCell(3).setCellValue("数据统计/次");
        title_row.createCell(4).setCellValue("时间");

        int startIndex = 1;

        for (Map map : result) {
            Map m = (Map) map.get("value");
            if (StringUtils.isEmpty(m.get(Col_DeviceNo))) {
                continue;
            }
            int power = (int) ((double) m.get(Col_Power));
            int media = (int) ((double) m.get(Col_Media));

            HSSFRow r = sheet.createRow(startIndex);
            r.createCell(0).setCellValue(m.get(Col_DeviceNo).toString());
            if (PowerMap.containsKey(power)) {
                r.createCell(1).setCellValue(PowerMap.get(power));
            } else {
                r.createCell(1).setCellValue("其他");
            }
            if (MediaMap.containsKey(media)) {
                r.createCell(2).setCellValue(MediaMap.get(media));
            } else {
                r.createCell(2).setCellValue("其他");
            }
            r.createCell(3).setCellValue(m.get(Col_Count).toString());
            r.createCell(4).setCellValue(m.get(Col_OnLineDate).toString());
            startIndex++;
        }
        try {
            exportExcelDocument("一小时设备在线统计信息", workbook, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化1小时mongodb数据的燃料与介质
     *
     * @param response
     */
    @RequestMapping(value = "/online/init/pm", method = RequestMethod.GET)
    public void initPm(HttpServletResponse response) {
        //
        Result result = lan_api.devicesList();
        List<Map> list = (List<Map>) result.getData();
        for (Map map : list) {
            try {
                String deviceSuffix = map.get("deviceSuffix").toString();
                Integer power = Integer.parseInt(map.get("power").toString());
                Integer media = Integer.parseInt(map.get("media").toString());

                Query query = new Query(Criteria.where("DeviceNo").is(deviceSuffix));
                Update update = new Update();
                update.set("power", power);
                update.set("media", media);
                //更新
                mongoTemplate.updateMulti(query, update, "dayinfos");
            } catch (Exception ex) {
                continue;
            }
        }
        //
    }


    /**
     * 统计最近days天的新上线的设备及上线次数
     *
     * @return
     */
    @RequestMapping(value = "/online/new/{days}", method = RequestMethod.GET)
    public Result newDeviceOnlineInfo(@PathVariable("days") Integer days) {
        if (null == days) {
            return Result.getFailResult("天数信息不能为空！");
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(calendar.DATE, -days);
        Date date = calendar.getTime();

        DateOperators.DateOperatorFactory f = new DateOperators.DateOperatorFactory("createDate");
        f = f.withTimezone(DateOperators.Timezone.valueOf("Asia/Shanghai"));
        DateOperators.DateToString dts = f.toString("%Y-%m-%d");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createDate").gte(date)),
                Aggregation.project("deviceNo", "createDate").and(dts).as("onLineDate"),
                Aggregation.group("deviceNo", "onLineDate").count().as("count"),
                Aggregation.project("deviceNo", "onLineDate", "count").and("group").previousOperation(),
                Aggregation.sort(Sort.Direction.ASC, "onLineDate")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, CollectionName, Map.class);
        //获取最近N的设备在线信息
        List<Map> nlist = result.getMappedResults();

        //获取设备编号集合
        Set<String> devices = new HashSet<>(nlist.size());
        for (Map map : nlist) {
            String s = map.get("deviceNo").toString();
            if (!StringUtils.isEmpty(s)) {
                devices.add(s);
            }
        }

        aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createDate").lte(date)),
                Aggregation.project("deviceNo", "createDate").and(dts).as("onLineDate"),
                Aggregation.group("deviceNo", "onLineDate").count().as("count"),
                Aggregation.project("deviceNo", "onLineDate", "count").and("group").previousOperation(),
                Aggregation.sort(Sort.Direction.ASC, "onLineDate")
        );
        result = mongoTemplate.aggregate(aggregation, CollectionName, Map.class);
        List<Map> olist = result.getMappedResults();

        List<Map> list = new ArrayList<>(nlist.size());
        List<String> okDevices = new ArrayList<>(devices.size());

        for (String no : devices) {
            long count = olist.stream().filter(item -> no.equals(item.get("deviceNo"))).count();
            if (count < 5) {
                okDevices.add(no);
            }
        }
        for (Map map : nlist) {
            if (okDevices.contains(map.get("deviceNo").toString())) {
                list.add(map);
            }
        }
        return Result.getSuccessResult(list);
    }

    /**
     * 统计最近days天的新上线的设备及上线次数的excel
     *
     * @param response
     */
    @RequestMapping(value = "/online/new/excel/{days}", method = RequestMethod.GET)
    public void newDeviceOnlineInfoExcel(@PathVariable("days") Integer days, HttpServletResponse response) {
        Result result = newDeviceOnlineInfo(days);
        if (Result.RESULT_CODE_SUCCESS != result.getCode()) {
            return;
        }

        List<Map> list = (List<Map>) result.getData();
        if (null == list) {
            return;
        }
        getNewOnlineDevicesExcel(String.format("近天%d上线统计信息", days), list, response);
    }

    /**
     * 查询几个月来售出后从未上线的设备
     *
     * @param month 向前推算的月份
     * @return
     */
    @RequestMapping(value = "/offline/all/{month}", method = RequestMethod.GET)
    public Result allDevicesOfflineInfo(@PathVariable("month") Integer month) {
        if (month <= 0) {
            return Result.getFailResult("月份信息无效！");
        }
        //获取售出设备列表
        Result res = lan_api.deviceIdList();
        if (Result.RESULT_CODE_FAIL == res.getCode()) {
            return Result.getFailResult("无法获取设备列表！");
        }
        ArrayList<String> allDevices = (ArrayList<String>) res.getData();

        return getOfflineDevices(month, allDevices);
    }

    /**
     * 查询几个月来售出后从未上线的设备
     *
     * @param month 向前推算的月份
     * @return
     */
    @RequestMapping(value = "/offline/dtu/{month}", method = RequestMethod.GET)
    public Result dtuDevicesOfflineInfo(@PathVariable("month") Integer month) {
        if (month <= 0) {
            return Result.getFailResult("月份信息无效！");
        }
        //获取售出设备列表
        Result res = lan_api.deviceDtuIdList();
        if (Result.RESULT_CODE_FAIL == res.getCode()) {
            return Result.getFailResult("无法获取设备列表！");
        }
        ArrayList<String> allDevices = (ArrayList<String>) res.getData();

        return getOfflineDevices(month, allDevices);
    }

    @RequestMapping(value = "/offline/all/excel/{month}", method = RequestMethod.GET)
    public void allDevicesOfflineInfoExcel(@PathVariable("month") Integer month, HttpServletResponse response) {
        Result result = allDevicesOfflineInfo(month);
        if (Result.RESULT_CODE_SUCCESS != result.getCode()) {
            return;
        }
        Map<String, OffLineInfo> data = (Map<String, OffLineInfo>) result.getData();
        if (null == data) {
            return;
        }
        getOfflineDevicesExcel(String.format("近%d月离线设备统计",month), data, response);
    }

    @RequestMapping(value = "/offline/dtu/excel/{month}", method = RequestMethod.GET)
    public void dtuDevicesOfflineInfo(@PathVariable("month") Integer month, HttpServletResponse response) {
        Result result = dtuDevicesOfflineInfo(month);
        if (Result.RESULT_CODE_SUCCESS != result.getCode()) {
            return;
        }
        Map<String, OffLineInfo> data = (Map<String, OffLineInfo>) result.getData();
        if (null == data) {
            return;
        }
        getOfflineDevicesExcel(String.format("近%d月离线DTU设备统计",month), data, response);
    }

    /**
     * 获取近几个月来未上线的设备编号列表
     *
     * @param month
     * @param allDevices 用来验证是否在线的设备编号列表
     * @return
     */
    private Result getOfflineDevices(int month, List<String> allDevices) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(calendar.MONTH, -month);
        calendar.add(calendar.DATE, 1);
        Date date = calendar.getTime();

        DateOperators.DateOperatorFactory f = new DateOperators.DateOperatorFactory("createDate");
        f = f.withTimezone(DateOperators.Timezone.valueOf("Asia/Shanghai"));
        DateOperators.DateToString dts = f.toString("%Y-%m-%d");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createDate").gte(date)),
                Aggregation.project("deviceNo", "createDate").and(dts).as("onLineDate"),
                Aggregation.group("deviceNo", "onLineDate").count().as("count"),
                Aggregation.project("deviceNo", "onLineDate", "count").and("group").previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, "onLineDate")
        );
        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, CollectionName, Map.class);
        List<Map> nlist = result.getMappedResults();

        //获取最近在线设备编号集合
        Set<String> onlineDevices = new HashSet<>(nlist.size());
        HashMap<String, OffLineInfo> onlineDevicesMap = new HashMap<>();
        for (Map map : nlist) {
            //String s = map.get("deviceNo").toString();
            Object obj = map.get("deviceNo");
            if (!StringUtils.isEmpty(obj)) {
                onlineDevices.add(obj.toString());
            }
            if (!onlineDevicesMap.containsKey(obj)) {
                OffLineInfo info = new OffLineInfo(
                        map.get(Col_OnLineDate).toString(),
                        map.get(Col_Count).toString());
                onlineDevicesMap.put(obj.toString(), info);
            }
        }
        ArrayList<String> ds = new ArrayList<>(onlineDevices);
        //取出差集
        allDevices.removeAll(ds);
        HashMap<String, OffLineInfo> offlineDevicesMap = new HashMap<>();
        for (String s : allDevices) {
            if (onlineDevicesMap.containsKey(s)) {
                OffLineInfo info = onlineDevicesMap.get(s);
                offlineDevicesMap.put(s, info);
            } else {
                offlineDevicesMap.put(s, null);
            }
        }
        return Result.getSuccessResult(offlineDevicesMap);
    }

    private void getOnlineDevicesExcel(String title, List<Map> data, HttpServletResponse response) {
        //实例化HSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet("在线统计");
        HSSFRow title_row = sheet.createRow(0);//模拟量标题行
        title_row.createCell(0).setCellValue("设备");
        title_row.createCell(1).setCellValue("燃料");
        title_row.createCell(2).setCellValue("介质");
        title_row.createCell(3).setCellValue("数据统计/次");
        title_row.createCell(4).setCellValue("时间");

        int startIndex = 1;

        for (Map map : data) {
            if (StringUtils.isEmpty(map.get(Col_DeviceNo))) {
                continue;
            }
            HSSFRow r = sheet.createRow(startIndex);
            r.createCell(0).setCellValue(map.get(Col_DeviceNo).toString());
            if (PowerMap.containsKey(map.get(Col_Power))) {
                r.createCell(1).setCellValue(PowerMap.get(map.get(Col_Power)));
            } else {
                r.createCell(1).setCellValue("其他");
            }
            if (MediaMap.containsKey(map.get(Col_Media))) {
                r.createCell(2).setCellValue(MediaMap.get(map.get(Col_Media)));
            } else {
                r.createCell(2).setCellValue("其他");
            }
            r.createCell(3).setCellValue((Integer) map.get(Col_Count));
            r.createCell(4).setCellValue(map.get(Col_OnLineDate).toString());
            startIndex++;
        }
        try {
            exportExcelDocument(title, workbook, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getNewOnlineDevicesExcel(String title, List<Map> data, HttpServletResponse response) {
        //实例化HSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet("在线统计");
        HSSFRow title_row = sheet.createRow(0);//模拟量标题行
        title_row.createCell(0).setCellValue("设备");
        title_row.createCell(1).setCellValue("数据统计/次");
        title_row.createCell(2).setCellValue("时间");

        int startIndex = 1;

        for (Map map : data) {
            if (StringUtils.isEmpty(map.get(Col_DeviceNo))) {
                continue;
            }
            HSSFRow r = sheet.createRow(startIndex);
            r.createCell(0).setCellValue(map.get(Col_DeviceNo).toString());
            r.createCell(1).setCellValue((Integer) map.get(Col_Count));
            r.createCell(2).setCellValue(map.get(Col_OnLineDate).toString());
            startIndex++;
        }
        try {
            exportExcelDocument(title, workbook, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void getOfflineDevicesExcel(String title, Map<String, OffLineInfo> data, HttpServletResponse response) {
        //实例化HSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet("离线统计");
        HSSFRow title_row = sheet.createRow(0);//模拟量标题行
        title_row.createCell(0).setCellValue("设备");
        title_row.createCell(1).setCellValue("最后在线时间");
        title_row.createCell(2).setCellValue("数据统计/次");

        int startIndex = 1;

        for (String deviceNo : data.keySet()) {
            if (StringUtils.isEmpty(deviceNo)) {
                continue;
            }
            HSSFRow r = sheet.createRow(startIndex);
            r.createCell(0).setCellValue(deviceNo);
            OffLineInfo info = data.get(deviceNo);
            if(null != info) {
                r.createCell(1).setCellValue(data.get(deviceNo).getDate());
                r.createCell(2).setCellValue(data.get(deviceNo).getCount());
            }
            startIndex++;
        }
        try {
            exportExcelDocument(title, workbook, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    @RequestMapping("/merge")
//    public Result mergeCollection(){
//        int step = 1000;
//        int length = 1000;
//        for(int i = 0;i< length;i++){
//            Query query = new Query().limit(step).skip(i*step);
//            List<Map> list = mongoTemplate.find(query,Map.class,CollectionName);
//            mongoTemplate.insert(list,"deviceruninfo");
//            if(list.size() < step){
//                break;
//            }
//        }
//        return Result.getSuccessResult();
//    }
}
