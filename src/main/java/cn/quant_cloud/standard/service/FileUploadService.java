package cn.quant_cloud.standard.service;

import cn.quant_cloud.standard.pojo.FileUploadReq;
import cn.quant_cloud.standard.utils.AppResult;

import java.time.LocalDateTime;

/**
 * @description: 文件上传服务接口
 * @author ljh
 * @date 2024/1/23 15:10
 * @version 1.0
 */
public interface FileUploadService {
    AppResult<?> checkSha256(FileUploadReq req)  ;

    AppResult<?> fileResumeUpload(FileUploadReq req);
}
