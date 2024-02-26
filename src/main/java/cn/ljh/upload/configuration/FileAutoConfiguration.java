package cn.ljh.upload.configuration;

import cn.ljh.upload.storage.FileServer;
import cn.ljh.upload.controller.FileController;
import cn.ljh.upload.storage.IFileStorage;
import cn.ljh.upload.storage.disk.DiskFileStorageProperties;
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
