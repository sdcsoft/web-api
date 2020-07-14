package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.entity.datacenter.DeviceDataMap;
import cn.com.sdcsoft.webapi.fegins.datacore.LAN_API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
@RequestMapping(value = "/wechat/DeviceDataMap")
public class Wechat_DeviceDataMapController {


    @Autowired
    LAN_API lan_api;


    @PostMapping("/create")
    public Result dataMapCreate(@RequestBody DeviceDataMap dataMap) {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        dataMap.setCreateDatetime(d);
        return lan_api.dataMapCreate(dataMap);
    }
    @PostMapping("/search")
    public Result dataMapSearch(@RequestParam("title") String title, @RequestParam("author") String author) {
        return lan_api.dataMapSearch(title,author);
    }
    @GetMapping("/search/author")
    public Result dataMapSearch(String author) {
        return lan_api.dataMapSearchByAuthor(author);
    }
    @GetMapping("/get")
    public Result dataMapGet(@RequestParam("id") Integer id) {
        return lan_api.dataMapGet(id);
    }

    @PostMapping("/modify/map")
    public Result dataMapModifyMap(@RequestParam("id") Integer id,@RequestParam("dataMap") String dataMap) {
        return lan_api.dataMapModifyMap(id,dataMap);
    }

    @PostMapping("/modify/other")
    public Result dataMapModifyOther(@RequestParam("id") Integer id,@RequestParam("pointIndexMap") String pointIndexMap,@RequestParam("dataLength") Integer dataLength) {
        return lan_api.dataMapModifyOther(id,pointIndexMap,dataLength);
    }
}

