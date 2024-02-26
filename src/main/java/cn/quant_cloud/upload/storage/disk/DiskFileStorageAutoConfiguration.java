package cn.quant_cloud.upload.storage.disk;


import cn.quant_cloud.upload.configuration.FileAutoConfiguration;
import cn.quant_cloud.upload.storage.IFileStorage;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ljh
 * @date: 2024/2/22 23:03
 * @version: 1.0
 */
@Configuration
@AutoConfigureBefore(FileAutoConfiguration.class)
@EnableConfigurationProperties(DiskFileStorageProperties.class)
public class DiskFileStorageAutoConfiguration {

    private final DiskFileStorageProperties diskFileStorageProperties;

    public DiskFileStorageAutoConfiguration(DiskFileStorageProperties diskFileStorageProperties) {
        this.diskFileStorageProperties = diskFileStorageProperties;
    }

    @Bean
    @ConditionalOnMissingBean(IFileStorage.class)
    public IFileStorage fileStorage() {
        return new DiskFileStorage(diskFileStorageProperties);
    }
}
