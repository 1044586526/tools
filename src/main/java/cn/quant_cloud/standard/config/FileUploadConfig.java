package cn.quant_cloud.standard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 文件上传通用配置
 * @author ljh
 * @date 2024/1/23 15:06
 * @version 1.0
 */
@Configuration("fileUploadConfig")
@ConfigurationProperties(prefix = "upload")
public class FileUploadConfig {

    /**
     * 文件上传临时目录
     */
    private String pathTemp;

    /**
     * 文件上传正式目录
     */
    private String pathFormal;

    public String getPathTemp() {
        return pathTemp;
    }

    public void setPathTemp(String pathTemp) {
        this.pathTemp = pathTemp;
    }

    public String getPathFormal() {
        return pathFormal;
    }

    public void setPathFormal(String pathFormal) {
        this.pathFormal = pathFormal;
    }
}
