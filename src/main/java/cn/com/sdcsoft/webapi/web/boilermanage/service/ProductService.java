package cn.com.sdcsoft.webapi.web.boilermanage.service;

import cn.com.sdcsoft.webapi.web.boilermanage.entity.Product;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductPartInfoMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.ProductUserMapper;
import cn.com.sdcsoft.webapi.web.boilermanage.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductUserMapper productUserMapper;

    @Autowired
    ProductPartInfoMapper productPartInfoMapper;

    @Autowired
    RoleMapper roleMapper;


    /**
     * 管理员创建产品
     * @param product
     * @return
     */
    public boolean createProduct(Product product){
        productMapper.createProduct(product);
        return true;
    }

    /**
     * 普通员工创建产品
     * @param product
     * @param userId
     * @return
     */
    public boolean createProduct(Product product,Integer userId){
        productMapper.createProduct(product);
        productUserMapper.createProductUser(userId,product.getId());
        return true;
    }

    public int deleteProduct(int id,String controllerNo){
        productUserMapper.clearProductMap(id);
        productPartInfoMapper.clearProductPartInfos(id);
        productMapper.removerProduct(id);
        return 0;
    }
}
