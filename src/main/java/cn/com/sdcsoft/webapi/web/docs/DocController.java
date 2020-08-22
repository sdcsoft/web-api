package cn.com.sdcsoft.webapi.web.docs;

import cn.com.sdcsoft.webapi.entity.Result;
import cn.com.sdcsoft.webapi.web.docs.mapper.DeviceFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi/docs")
public class DocController {

    @Autowired
    DeviceFileMapper deviceFileMapper;

    @RequestMapping("/device")
    public Result getDeviceDocs(String deviceNo){
        try{
            return Result.getSuccessResult(deviceFileMapper.getDeviceDocs(deviceNo));
        }
        catch (Exception ex){
            return  Result.getFailResult(ex.getMessage());
        }
    }

    @RequestMapping("/comms")
    public Result getCommsDocs(){
        try{
            return Result.getSuccessResult(deviceFileMapper.getCommDocs());
        }
        catch (Exception ex){
            return  Result.getFailResult(ex.getMessage());
        }
    }

}
