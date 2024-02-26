package cn.qy.upload.storage;

import cn.qy.upload.entity.FileUploadResponse;
import cn.qy.upload.entity.ResponseResult;
import cn.qy.upload.entity.constant.CommonConstant;
import cn.qy.upload.exception.UploadException;
import cn.qy.upload.storage.disk.DiskFileStorageProperties;
import cn.qy.upload.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ljh
 * @version 1.0
 * @description 文件服务
 * @date 2024/2/22 22:26
 */
@Slf4j
public class FileServer {

    /**
     * 文件存储器
     */
    private IFileStorage storage;

    /**
     * 配置
     */
    private DiskFileStorageProperties diskFileStorageProperties;


    public ResponseResult preCheckFile(String sha256, String fileName, Long totalSize){
        // 构建目录列表
        List<Path> paths = getPaths(sha256,fileName,totalSize);
        // 文件预检查
        List<FileUploadResponse> result = IntStream.range(0, paths.size())
                .filter(m -> Files.exists(paths.get(m)))
                .mapToObj(m -> {
                    Path path = paths.get(m);
                    String fileSha256;
                    try {
                        fileSha256 = UploadUtils.getFileSha256(path);
                        if (path.toString().contains(fileName)) {
                            return new FileUploadResponse(Files.size(path),fileSha256);
                        }
                        return new FileUploadResponse(Files.size(path),fileSha256);
                    } catch (Exception e) {
                        log.error("文件预检查异常:",e);
                        throw new UploadException(e.getMessage());
                    }
                })
                .collect(Collectors.toList());
        // 文件存在/不存在
        return ResponseResult.ok(CollectionUtils.isEmpty(result) ? new FileUploadResponse(0L,null) : result.stream().findFirst().get());
    }


    public ResponseResult upload(String sha256, String fileName, Long totalSize, Long startSize, MultipartFile file){
        try {
            // 是否首次上传
            Path tempPath = Paths.get(diskFileStorageProperties.getTempDir(), sha256 + CommonConstant.TEMP_SUFFIX);
            if (!Files.exists(tempPath)){
                Files.createFile(tempPath);
            }
            // 写入文件
            UploadUtils.readFileThenWrite(tempPath, startSize, file.getBytes());

            // 文件完整性校验
            String tmpSha256 = UploadUtils.getFileSha256(tempPath);
            if (tmpSha256.equals(sha256) && Files.size(tempPath) == totalSize){
                // 移动临时文件到正式目录
                Path formalPath = Paths.get(diskFileStorageProperties.getUploadDir(),sha256 + CommonConstant.SPLIT + fileName);
                Files.move(tempPath, formalPath, StandardCopyOption.ATOMIC_MOVE);
                //记录指纹库
                storage.save(fileName, LocalDateTime.now(),sha256,totalSize);
                return ResponseResult.ok("文件上传成功");
            }
            return ResponseResult.fail("文件上传失败");
        }catch (Exception e){
            log.error("{}文件上传异常: ", fileName, e);
            return ResponseResult.fail("文件上传过程中出现错误: " + e.getMessage());
        }
    }

    /**
     * 获取文件路径集
     * @param sha256 sha256
     * @param fileName 文件名
     */
    private List<Path> getPaths(String sha256,String fileName,Long totalSize){
        return Arrays.asList(
                Paths.get(diskFileStorageProperties.getUploadDir(), sha256 + CommonConstant.SPLIT + fileName),
                Paths.get(diskFileStorageProperties.getTempDir(), sha256 + CommonConstant.TEMP_SUFFIX)
        );
    }


    public void setStorage(IFileStorage storage) {
        this.storage = storage;
    }

    public void setProperties(DiskFileStorageProperties storageProperties) {
        this.diskFileStorageProperties = storageProperties;
    }
}
