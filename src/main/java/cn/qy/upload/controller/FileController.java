package cn.qy.upload.controller;

import cn.qy.upload.storage.FileServer;
import cn.qy.upload.entity.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * @author ljh
 * @date 2024/1/23 15:07
 * @version 1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileServer fileServer;

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
     * @param file 上传文件
     * @return 文件上传结果
     */
    @PostMapping("/upload")
    public ResponseResult upload (
            @RequestParam("sha256") @NotBlank(message = "sha256不能为空") String sha256,
            @RequestParam("fileName") String fileName,
            @RequestParam("startSize") Long startSize,
            @RequestParam("totalSize") Long totalSize,
            @RequestParam("file") MultipartFile file) throws IOException {
        return fileServer.upload(sha256,fileName,totalSize,startSize,file);
    }
}
