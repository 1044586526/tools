package cn.quant_cloud.standard.service.impl;

import cn.quant_cloud.standard.service.FileInfoSaveService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FileInfoSaveServiceImpl implements FileInfoSaveService {
    @Override
    public void save(String fileName, LocalDateTime uploadTime, String sha256, Long byteSize) {
        //do
    }


}
