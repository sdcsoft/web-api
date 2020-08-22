package cn.com.sdcsoft.webapi.web.cache.devices.ids.runner;

import cn.com.sdcsoft.webapi.web.cache.devices.ids.service.DeviceIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DeviceIdListLoaderRunner implements ApplicationRunner {

    @Autowired
    DeviceIdService service;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        service.load();
    }
}