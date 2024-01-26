package cn.quant_cloud.standard.service.impl;

import cn.quant_cloud.standard.config.FileUploadConfig;
import cn.quant_cloud.standard.pojo.FileUploadReq;
import cn.quant_cloud.standard.pojo.FileUploadRes;
import cn.quant_cloud.standard.service.FileInfoSaveService;
import cn.quant_cloud.standard.service.FileUploadService;
import cn.quant_cloud.standard.utils.AppResult;
import cn.quant_cloud.standard.utils.FileResumeUploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description: 文件上传实现类
 * @author ljh
 * @date 2024/1/23 15:10
 * @version 1.0
 */
@Service("fileUploadServiceImpl")
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Resource(name = "fileUploadConfig")
    private FileUploadConfig config;

    @Resource
    private FileInfoSaveService infoSaveService;

    /**
     * 文件预检查
     */
    @Override
    public AppResult<?> checkSha256(FileUploadReq req)  {
        try {
            if (StringUtils.isEmpty(req.getSha256()) ||StringUtils.isEmpty(req.getFileName()) || Objects.isNull(req.getTotalSize())){
                throw new Exception("请求参数值为空");
            }
            //目标路径文件
            List<Path> filePaths = getPaths(req.getSha256(),req.getFileName());
            //处理文件路径列表
            List<FileUploadRes> checkRet = IntStream.range(0, filePaths.size())
                    //过滤不存在文件
                    .filter(m -> Files.exists(filePaths.get(m)))
                    .mapToObj(m -> {
                        try {
                            Path curPath = filePaths.get(m);
                            String fileSha256 = FileResumeUploadUtils.getFileSha256(curPath);
                            //  正式文件
                            if (curPath.toString().contains(req.getFileName())) {
                                //清除已损坏文件
                                if (!fileSha256.equals(req.getSha256())){
                                    Files.delete(curPath);
                                    return getFileUploadRes("",0);
                                }
                                return getFileUploadRes(fileSha256,Files.size(curPath));
                            }
                            // 临时文件
                            return getFileUploadRes(fileSha256,Files.size(curPath));
                        } catch (Exception e) {
                            log.error("文件校验失败",e);
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            //设置初始值
            if (CollectionUtils.isEmpty(checkRet)){
                checkRet.add(getFileUploadRes("",0));
            }
            return AppResult.ok(checkRet);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AppResult.fail();
        }
    }

    /**
     * 获取文件路径集
     * @param sha256 sha256
     * @param fileName 文件名
     */
    private List<Path> getPaths(String sha256,String fileName ) throws Exception {
        if (StringUtils.isEmpty(config.getPathTemp()) || StringUtils.isEmpty(config.getPathFormal())){
            throw new Exception("配置文件中路径为空");
        }
        // 临时文件路径
        return Arrays.asList(
                Paths.get(config.getPathFormal(), fileName),
                Paths.get(config.getPathTemp(), sha256+".tmp")
        );
    }

    /**
     * 构造响应参数
     * @param sha256 sha256
     * @param byteSize 上传文件总字节大小
     */
    private FileUploadRes getFileUploadRes(String sha256, long byteSize) {
        FileUploadRes uploadRes = new FileUploadRes();
        uploadRes.setSha256(sha256);
        uploadRes.setByteSize(byteSize);
        return uploadRes;
    }

    /**
     * 文件上传-支持断点续传
     */
    @Override
    public AppResult<String> fileResumeUpload(FileUploadReq req) {
        try {
            if (StringUtils.isEmpty(req.getSha256()) ||StringUtils.isEmpty(req.getFileName()) || Objects.isNull(req.getTotalSize())){
                throw new Exception("请求参数值为空");
            }
            // 是否第一次上传
            Path tmpPath = Paths.get(config.getPathTemp(), req.getSha256()+".tmp");
            if (!Files.exists(tmpPath)){
                Files.createFile(tmpPath);
            }
            //  随机写入
            FileResumeUploadUtils.readFileThenWrite(tmpPath, req.getStartSize(), req.getBytes());
            //判断sha256是否一致
            String tmpSha256 = FileResumeUploadUtils.getFileSha256(tmpPath);
            if (tmpSha256.equals(req.getSha256()) && Files.size(tmpPath) == req.getTotalSize()){
                //文件上传完成，将临时文件移动到目标目录
                Path formalPath = Paths.get(config.getPathFormal(),req.getFileName());
                Files.move(tmpPath, formalPath, StandardCopyOption.ATOMIC_MOVE);
                //记录指纹库
                infoSaveService.save(req.getFileName(),LocalDateTime.now(),req.getSha256(),req.getTotalSize());
                return AppResult.ok();
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AppResult.fail();
        }
        return AppResult.ok("文件未全部上传");
    }
}
