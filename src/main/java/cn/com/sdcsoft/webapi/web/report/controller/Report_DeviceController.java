package cn.com.sdcsoft.webapi.web.report.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
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

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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

    @RequestMapping("/excel")
    public void getExcel(String deviceNo,String begintime,String endtime,Integer newframe,HttpServletResponse response) {
        HashMap<String, String> nameMap = new HashMap<>();

        nameMap.put("mo_zhenkongyali", "真空压力");
        nameMap.put("mo_zhengqiyali", "蒸汽压力");
        nameMap.put("mo_paiyanwendu", "排烟温度");
        nameMap.put("mo_chukouwendu", "出口温度");
        nameMap.put("mo_huiliuwendu", "入口温度");
        nameMap.put("mo_shenghuochukouwendu", "生活出口温度");
        nameMap.put("mo_shenghuohuiliuwendu", "生活入口温度");
        nameMap.put("mo_cainuanchukouwendu", "采暖出口温度");
        nameMap.put("mo_cainuanhuiliuwendu", "采暖入口温度");

        nameMap.put("ex_lubichaowenbaojing", "炉壁超温报警");
        nameMap.put("ex_chaoyabaojing", "超压报警");
        nameMap.put("ex_diyabaojing", "低压报警");
        nameMap.put("ex_qianyabaojing", "欠压报警");
        nameMap.put("ex_chukouwendugaobaojing", "出口温度高报警");
        nameMap.put("ex_waibuliansuoduankaibaojing", "外部连锁断开报警");
        nameMap.put("ex_jixiandishuiweibaojing", "极限低水位报警");
        nameMap.put("ex_jixiangaoshuiweibaojing", "极限高水位报警");
        nameMap.put("ex_ranqixieloubaojing", "燃气泄漏报警");
        nameMap.put("ex_paiyanwenduchaogaobaojing", "排烟温度超高报警");
        nameMap.put("ex_queyoubaojing", "缺油报警");

        Date startTime = strToDate(begintime + " 00:00:00");
        Date endTime = strToDate(endtime + " 00:00:00");

        Query query = Query.query(Criteria.where("DeviceNo").is(deviceNo).and("CreateDate")
                .gte(startTime)
                .lte(endTime));
        //query.limit(1);
        List<HashMap> ls = mongoTemplate.find(query, HashMap.class, "dayinfos");

        //实例化HSSFWorkbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //建立一个模拟量sheet
        //创建一个Excel表单，参数为sheet的名字
        HSSFSheet mo_sheet = workbook.createSheet("运行信息");
        HSSFRow device_mock_title_row = mo_sheet.createRow(0);//模拟量标题行
        device_mock_title_row.createCell(0).setCellValue("设备");
        device_mock_title_row.createCell(1).setCellValue("时间");
        //建立一个异常sheet
        HSSFSheet ex_sheet = workbook.createSheet("异常报警");
        HSSFRow device_ex_title_row = ex_sheet.createRow(0);//设备编号行
        device_ex_title_row.createCell(0).setCellValue("设备");
        device_ex_title_row.createCell(1).setCellValue("异常报警");
        device_ex_title_row.createCell(2).setCellValue("发生时间");
        device_ex_title_row.createCell(3).setCellValue("消除时间");
        //
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        short dateFormat = createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss");
        cellStyle.setDataFormat(dateFormat);

        int dayMockRowStartIndex = 1;
        int exRowIndex = 1;
        int count  = 0;
        List<HSSFRow> rows = new ArrayList<>(10);//模拟量数值行
        for (HashMap map : ls) {
            int mockColIndex = 1;//模拟量当前有效列索引
            //String deviceNo = map.get("DeviceNo").toString();

            for (Object k : map.keySet()) {
                String key = k.toString();
                if (key.startsWith("mo_")) {
                    mockColIndex++;
                    Object obj = nameMap.get(key);
                    device_mock_title_row.createCell(mockColIndex).setCellValue(obj.toString());
                    ArrayList<HashMap> items = (ArrayList<HashMap>) map.get(key);
                    count = items.size();
                    if (rows.size() == 0) {
                        int currentRowIndex = dayMockRowStartIndex;
                        for (int i = 0; i < count; i++) {
                            HSSFRow r = mo_sheet.createRow(currentRowIndex);
                            r.createCell(0).setCellValue(deviceNo);
                            r.createCell(1).setCellValue(items.get(i).get("date").toString());
                            rows.add(r);
                            currentRowIndex++;
                        }
                    }
                    for (int i = 0; i < count; i++) {
                        rows.get(i).createCell(mockColIndex).setCellValue(items.get(i).get("avg").toString());
                    }
                } else if (key.startsWith("ex_")) {
                    String exName = nameMap.get(key);
                    ArrayList<HashMap> items = (ArrayList<HashMap>) map.get(key);
                    for (int i = 0; i < items.size(); i++) {
                        HSSFRow row = ex_sheet.createRow(exRowIndex);
                        row.createCell(0).setCellValue(deviceNo);
                        row.createCell(1).setCellValue(exName);
                        HSSFCell cell =  row.createCell(2);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue((Date) items.get(i).get("startTime"));
                        cell =  row.createCell(3);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue((Date) items.get(i).get("endTime"));
                        exRowIndex++;
                    }
                } else {
                    continue;
                }
                //System.out.println(k + "->" + map.get(k));
            }
            dayMockRowStartIndex += count;
            rows.clear();
        }

        try {
            exportExcelDocument(deviceNo,workbook,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void exportExcelDocument(String filename,HSSFWorkbook workbook,HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename, "utf-8")+".xls");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}