package cn.quant_cloud.upload.storage.disk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
    /**
     * 指定存储目录请求头
     */
    private String uploadDirHeader = "quant-cloud-dir";
}
