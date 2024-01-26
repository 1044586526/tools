package cn.quant_cloud.standard.pojo;


import java.io.Serializable;

/**
 * @description: 响应对象
 * @author ljh
 * @date 2024/1/23 15:20
 * @version 1.0
 */
public class FileUploadRes implements Serializable {

    private String sha256;

    private Long byteSize;

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public void setByteSize(Long byteSize) {
        this.byteSize = byteSize;
    }

    public String getSha256() {
        return sha256;
    }

    public Long getByteSize() {
        return byteSize;
    }
}
