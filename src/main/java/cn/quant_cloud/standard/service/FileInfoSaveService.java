package cn.quant_cloud.standard.service;

import java.time.LocalDateTime;

public interface FileInfoSaveService {
    void save(String fileName, LocalDateTime uploadTime,String sha256,Long byteSize);
}
