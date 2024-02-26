package cn.qy.upload.entity;

/**
 * @author ljh
 * @version 1.0
 * @description
 * @date 2024/2/23 1:05
 */
public class FileUploadResponse {

    private Long totalSize;

    private String sha256;

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public FileUploadResponse(Long totalSize, String sha256) {
        this.totalSize = totalSize;
        this.sha256 = sha256;
    }
}
