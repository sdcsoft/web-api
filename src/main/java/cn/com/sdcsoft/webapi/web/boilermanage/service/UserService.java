package cn.com.sdcsoft.webapi.web.boilermanage.service;

import cn.com.sdcsoft.webapi.mapper.Customer_DB.Customer_DB_UserMapper;
import cn.com.sdcsoft.webapi.web.entity.OrgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    Customer_DB_UserMapper userMapper;

    public void createUser(OrgUser user) {
        userMapper.createUser(user);
    }

    public void createAdmin(OrgUser user) {
        userMapper.createAdmin(user);
    }
}
