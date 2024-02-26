package cn.quant_cloud.upload.storage.disk;

import cn.quant_cloud.upload.entity.constant.CommonConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author: ljh
 * @date: 2024/2/22 23:03
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "quant.cloud.file.upload")
public class DiskFileStorageProperties {
    /**
     * 临时文件目录
     */
    private String tempDir;
    /**
     * 文件存储目录
     */
    private String uploadDir;

    @PostConstruct
    public void init() {
        this.tempDir = Objects.isNull(tempDir) ? CommonConstant.TEMP : tempDir;
        this.uploadDir = Objects.isNull(uploadDir) ?   CommonConstant.FORMAL : uploadDir;
    }
}
