package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_JinRong_OrderMapper;
import cn.com.sdcsoft.webapi.wechat.entity.JinRong_Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping(value = "/wechat/JinRong_Order")
public class Wechat_JinRong_OrderController {

    @Autowired
    private Wechat_DB_JinRong_OrderMapper wechat_db_jinRong_orderMapper;


    @GetMapping(value = "/list")
    public Result getJinRong_OrderListByOpenid(@Param("openId") String openId) {
        return Result.getSuccessResult(wechat_db_jinRong_orderMapper.getJinRong_OrderListByOpenid(openId));
    }

    @GetMapping(value = "/all/list")
    public Result getJinRong_OrderList() {
        return Result.getSuccessResult(wechat_db_jinRong_orderMapper.getJinRong_OrderList());
    }
    @PostMapping("/create")
    public Result insertJinRong_Order(@RequestBody JinRong_Order jinRong_order) {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        jinRong_order.setCreateDate(d);
        jinRong_order.setPayforDate(d);
        String random = String.valueOf(Math.random()).substring(2,8);
        SimpleDateFormat dmDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateran = dmDate.format(new Date());
        String orderNo = dateran+random;
        orderNo = orderNo.substring(orderNo.length()-16, orderNo.length());
        jinRong_order.setOutTradeNo(orderNo);
        wechat_db_jinRong_orderMapper.insertJinRong_Order(jinRong_order);
        return Result.getSuccessResult(jinRong_order);
    }


}

