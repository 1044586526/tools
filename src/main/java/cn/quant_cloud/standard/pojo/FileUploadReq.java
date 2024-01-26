package cn.quant_cloud.standard.pojo;

import java.io.Serializable;

/**
 * @description: 请求对象
 * @author ljh
 * @date 2024/1/23 15:20
 * @version 1.0
 */
public class FileUploadReq implements Serializable {

    private String sha256;

    private Long totalSize;

    private Long startSize;

    private byte[] bytes;

    private String fileName;

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getStartSize() {
        return startSize;
    }

    public void setStartSize(Long startSize) {
        this.startSize = startSize;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
