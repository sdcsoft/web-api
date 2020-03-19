package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_JinRong_OrderItemMapper;
import cn.com.sdcsoft.webapi.wechat.entity.JinRong_OrderItem;
import cn.com.sdcsoft.webapi.wechat.entity.Role_Resource;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/webapi/wechat/JinRong_OrderItem")
public class Wechat_JinRong_OrderItemController {

    @Autowired
    private Wechat_DB_JinRong_OrderItemMapper wechat_db_jinRong_orderItemMapper;





    @GetMapping(value = "/list")
    public Result getJinRong_OrderItemListByOrderId(@Param("OrderId") String OrderId) {
        return Result.getSuccessResult(wechat_db_jinRong_orderItemMapper.getJinRong_OrderItemListByOrderId(OrderId));
    }
    @PostMapping("/wx/create/many")
    public Result insertManyJinRong_OrderItem(String jinRong_OrderItemList){
        List<JinRong_OrderItem> list= JSON.parseArray(jinRong_OrderItemList,JinRong_OrderItem.class);
        if(list.size()>0){
            wechat_db_jinRong_orderItemMapper.insertJinRong_OrderItem(list);
            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }

    @PostMapping("/create/many")
    public Result insertManyJinRong_OrderItem( @RequestBody List<JinRong_OrderItem> jinRong_OrderItemList){
        // List<JinRong_OrderItem> list= JSON.parseArray(jinRong_OrderItemList,JinRong_OrderItem.class);
        if(jinRong_OrderItemList.size()>0){
            wechat_db_jinRong_orderItemMapper.insertJinRong_OrderItem(jinRong_OrderItemList);
            return Result.getSuccessResult();
        }
        return Result.getFailResult("插入失败");
    }

}

