package cn.qy.upload.storage.disk;

import cn.qy.upload.storage.IFileStorage;

import java.time.LocalDateTime;

/**
 * @description: 自定义存储
 * @author: ljh
 * @date: 2024/2/22 23:01
 * @version: 1.0
 */
public class DiskFileStorage implements IFileStorage {

    private final DiskFileStorageProperties storageProperties;

    public DiskFileStorage(DiskFileStorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public void save(String fileName, LocalDateTime time, String sha256, Long byteSize) {
        // ......
    }
}
