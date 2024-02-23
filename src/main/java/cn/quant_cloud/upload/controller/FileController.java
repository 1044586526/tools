package cn.quant_cloud.upload.controller;

import cn.quant_cloud.upload.storage.FileServer;
import cn.quant_cloud.upload.entity.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author ljh
 * @date 2024/1/23 15:07
 * @version 1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private  FileServer fileServer;

    public FileController(FileServer fileServer) {
        this.fileServer = fileServer;
    }


    /**
     * 文件预检查
     * @param sha256 文件sha256值
     * @param fileName 文件名
     * @param totalSize 文件大小
     * @return 预检查结果
     */
    @GetMapping("/preCheckFile")
    public ResponseResult preCheckFile(@RequestParam("sha256") @NotBlank(message = "sha256不能为空") String sha256,
                                       @RequestParam("fileName")@NotBlank(message = "文件名不能为空") String fileName,
                                       @RequestParam("totalBytes") Long totalSize){
        return fileServer.preCheckFile(sha256,fileName,totalSize);
    }


    /**
     * 文件上传（支持断点续传）
     * @param sha256 文件sha256
     * @param fileName 文件名
     * @param totalSize 文件大小
     * @param startSize 文件开始索引位置
     * @param bytes 字节流
     * @return 文件上传结果
     */
    @PostMapping("/fileResumeUpload")
    public ResponseResult upload(
            @RequestParam("sha256") @NotBlank(message = "sha256不能为空") String sha256,
            @RequestParam("fileName")@NotBlank(message = "文件名不能为空") String fileName,
            @RequestParam("totalSize") Long totalSize,
            @RequestParam("startSize") Long startSize,
            @RequestParam("bytes") byte[] bytes){
        return fileServer.upload(sha256,fileName,totalSize,startSize,bytes);
    }
}
