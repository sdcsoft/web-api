package cn.com.sdcsoft.webapi.TianZhuangGaoSu.controller;


import cn.com.sdcsoft.webapi.TianZhuangGaoSu.entity.User;
import cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper.UserMapper;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;


@RestController
@RequestMapping(value = "/tzgs/user")
public class TZGS_UserController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping(value = "/unionId")
    public Result findUserByUnionId(String unionId) {
        return Result.getSuccessResult(userMapper.findUserByUnionId(unionId));
    }

    @GetMapping(value = "/openId")
    public Result findUserByOpenId(String openId) {
        return Result.getSuccessResult(userMapper.findUserByOpenId(openId));
    }
    /**
     *  生成推荐码
     * @param openId
     * @return
     */
    @RequestMapping("/invcode/create")
    public Result findUserByInvCode(String openId) {
        try{
            User user = userMapper.findUserByOpenId(openId);
            if (null != user) {
                if (user.getRoleId() != 1) {
                    return Result.getFailResult("邀请码只允许超级管理员生成！");
                }
                String uuid = UUID.randomUUID().toString().replace("-", "");
                userMapper.createInvCode(uuid);
                return Result.getSuccessResult("",uuid);
            } else {
                return Result.getFailResult("系统中不存在该邀请码！");
            }
        }
        catch (Exception ex){
            return Result.getFailResult(ex.getMessage());
        }
    }

    @RequestMapping("/create")
    public Result create(@RequestBody User user) {
        try {
            User u = userMapper.findUserByOpenId(user.getInvCode());

            if (null == u) {
                return Result.getFailResult("系统中不存在该邀请码！");
            }
            Timestamp d = new Timestamp(System.currentTimeMillis());
            user.setId(u.getId());
            if (userMapper.updateUser(user)>0) {
               return Result.getSuccessResult();
            } else {
                return Result.getFailResult("用户创建失败！");
            }
        } catch (Exception ex) {
            return Result.getFailResult(ex.getMessage());
        }
    }
}
