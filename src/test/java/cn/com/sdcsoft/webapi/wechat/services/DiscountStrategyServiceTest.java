package cn.com.sdcsoft.webapi.wechat.services;

import cn.com.sdcsoft.webapi.WebApiApplication;
import cn.com.sdcsoft.webapi.wechat.interfaces.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = WebApiApplication.class)
//public class DiscountStrategyServiceTest {
//
////    @Autowired
////    DiscountStrategyService service;
////    float amount = 0;
//
////    @Test
////    public void discount() {
////
////        Order order = new Order() {
////            float total = 500;
////
////            String discount;
////            @Override
////            public float getTotal() {
////                return total;
////            }
////
////            @Override
////            public void setDiscount(String discount) {
////                this.discount = discount;
////            }
////
////            @Override
////            public void setPaymentAmount(float money) {
////                amount = money;
////            }
////        };
////        service.Discount(order);
////        Assert.assertTrue(amount == 475);
////    }
//}