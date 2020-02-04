package cn.com.sdcsoft.webapi.wechat.services;

import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_DiscountStrategyMapper;
import cn.com.sdcsoft.webapi.wechat.entity.DiscountStrategy;
import cn.com.sdcsoft.webapi.wechat.interfaces.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiscountStrategyService {

    @Autowired
    Wechat_DB_DiscountStrategyMapper mapper;

    private DiscountStrategy getDiscountStrategy(float money){
        DiscountStrategy theStrategy = null;
        List<DiscountStrategy> discountStrategies =  mapper.getDiscountStrategies(new Date());

        if(discountStrategies.size()>0){
            for(DiscountStrategy d : discountStrategies){
                if(money < d.getBaseNumber())
                    break;
                if(money >= d.getBaseNumber()){
                    theStrategy = d;
                }
            }
        }

        if(null == theStrategy){
            theStrategy = new DiscountStrategy();
            theStrategy.setBaseNumber(0);
            theStrategy.setDiscount(0.0f);
            theStrategy.setStrategyType(DiscountStrategy.Type_Sub);
            //theStrategy.setStrategyName("无");
        }
        return theStrategy;
    }

    public void Discount(Order order){
        DiscountStrategy strategy = getDiscountStrategy(order.getTotal());
        order.setDiscount(strategy.getStrategyName());
        order.setPaymentAmount(strategy.Count(order.getTotal()));
    }
}
