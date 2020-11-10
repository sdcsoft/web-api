package cn.com.sdcsoft.webapi.web.enterprisemanage.service;

import cn.com.sdcsoft.webapi.mapper.Enterprise_DB.Enterprise_DB_UserMapper;
import cn.com.sdcsoft.webapi.web.entity.OrgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUserService {
    @Autowired
    Enterprise_DB_UserMapper userMapper;

    public void createUser(OrgUser user) {
        userMapper.createUser2(user);
    }

    public void createAdmin(OrgUser user) {
        userMapper.createAdmin(user);
    }
}
