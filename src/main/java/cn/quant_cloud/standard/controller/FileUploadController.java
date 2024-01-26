package cn.quant_cloud.standard.controller;

import cn.quant_cloud.standard.pojo.FileUploadReq;
import cn.quant_cloud.standard.service.FileInfoSaveService;
import cn.quant_cloud.standard.service.FileUploadService;
import cn.quant_cloud.standard.utils.AppResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description: 文件上传控制器
 * @author ljh
 * @date 2024/1/23 15:07
 * @version 1.0
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Resource(name = "fileUploadServiceImpl")
    private FileUploadService currentService;

    /**
     * 文件预检查
     */
    @PostMapping("/checkSha256")
    public AppResult<?> checkSha256(@RequestBody FileUploadReq req){
        return currentService.checkSha256(req);
    }


    @PostMapping("/fileResumeUpload")
    public AppResult<?> fileResumeUpload(@RequestBody FileUploadReq req){
        return currentService.fileResumeUpload(req);
    }
}
