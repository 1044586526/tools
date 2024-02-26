package cn.qy.upload.configuration;

import cn.qy.upload.storage.FileServer;
import cn.qy.upload.controller.FileController;
import cn.qy.upload.storage.IFileStorage;
import cn.qy.upload.storage.disk.DiskFileStorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

/**
 * @author ljh
 * @version 1.0
 * @date 2024/2/22 22:13
 */
@Configuration
public class FileAutoConfiguration {

    @Resource
    private DiskFileStorageProperties diskFileStorageProperties;

    @Bean
    public FileServer fileServer(IFileStorage fileStorage) {
        FileServer fileServer = new FileServer();
        fileServer.setStorage(fileStorage);
        fileServer.setProperties(diskFileStorageProperties);
        return fileServer;
    }

    @Bean
    public FileController fileController(FileServer fileServer) {
        return new FileController(fileServer);
    }

}
