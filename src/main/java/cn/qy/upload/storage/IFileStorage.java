package cn.qy.upload.storage;

import java.time.LocalDateTime;

/**
 * @description: 文件存储器
 * @author: ljh
 * @date: 2024/2/22 22:52
 * @version: 1.0
 */
public interface IFileStorage {
    void save(String fileName, LocalDateTime time,String sha256,Long byteSize);
}
