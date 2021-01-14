package cn.com.sdcsoft.webapi.web.enterprisemanage.service;

import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.*;
import cn.com.sdcsoft.webapi.web.enterprisemanage.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EnterpriseProductService {

    @Autowired
    Enterprise_DB_ProductMapper productMapper;

    @Autowired
    Enterprise_DB_ProductUserMapper productUserMapper;

    @Autowired
    Enterprise_DB_ProductPartInfoMapper productPartInfoMapper;

    @Autowired
    Enterprise_DB_RoleMapper roleMapper;


    public boolean checkProduct(String controllerNo) {
        return productMapper.CountControllerAmount(controllerNo) == 0;
    }

    /**
     * 管理员创建产品
     *
     * @param product
     * @return
     */
    public boolean createProduct(Product product) {
        productMapper.createProduct(product);
        return true;
    }

    /**
     * 重新配置设备信息
     * @param product
     * @return
     */
    public boolean resetProduct(Product product) {
        productMapper.resetProductInfo(product);
        return true;
    }

    /**
     * 普通员工创建产品
     *
     * @param product
     * @param userId
     * @return
     */
    public boolean createProduct(Product product, Integer userId) {
        productMapper.createProduct(product);

        productUserMapper.createProductUser(userId, product.getId());
        return true;
    }

    public int deleteProduct(int id, String controllerNo) {
        productUserMapper.clearProductMap(id);
        productPartInfoMapper.clearProductPartInfos(id);
        productMapper.removerProduct(id);
        return 0;
    }
}
