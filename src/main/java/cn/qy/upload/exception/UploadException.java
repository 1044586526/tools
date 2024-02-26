package cn.qy.upload.exception;


/**
 * @description: 定义上传异常
 * @author: ljh
 * @date: 2024/2/22 22:37
 * @version: 1.0
 */
public class UploadException extends AbstractException {

    public UploadException(String message) {
        super(ErrorCode.A0001.getCode(), message);
    }
}
