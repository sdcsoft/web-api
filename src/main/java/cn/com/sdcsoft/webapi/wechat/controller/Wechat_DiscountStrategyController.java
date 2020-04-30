package cn.com.sdcsoft.webapi.wechat.controller;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DiscountStrategyMapper;
import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_JinRong_OrderMapper;
import cn.com.sdcsoft.webapi.wechat.entity.DiscountStrategy;
import cn.com.sdcsoft.webapi.wechat.entity.JinRong_Order;
import cn.com.sdcsoft.webapi.wechat.services.DiscountStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/wechat/DiscountStrategy")
public class Wechat_DiscountStrategyController {

    @Autowired
    private Wechat_DB_DiscountStrategyMapper wechat_db_discountStrategyMapper;
    @Autowired
    private Wechat_DB_JinRong_OrderMapper wechat_db_jinRong_orderMapper;

    @Autowired
    DiscountStrategyService service;

    @GetMapping(value = "/discount")
    public Result discount(JinRong_Order order) {
        service.Discount(order);
        return Result.getSuccessResult(order);
    }



    @GetMapping(value = "/list")
    public Result getAll() {
        return Result.getSuccessResult(wechat_db_discountStrategyMapper.getAll());
    }


    @PostMapping("/create")
    public Result insertDiscountStrategy(@RequestBody DiscountStrategy discountStrategy) {
        wechat_db_discountStrategyMapper.insertDiscountStrategy(discountStrategy);
        return Result.getSuccessResult();
    }

    @PostMapping("/modify")
    public Result updateDiscountStrategy(@RequestBody DiscountStrategy discountStrategy) {
        wechat_db_discountStrategyMapper.updateDiscountStrategy(discountStrategy);
        return Result.getSuccessResult();
    }



}

