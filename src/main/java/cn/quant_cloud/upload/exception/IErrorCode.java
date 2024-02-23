package cn.quant_cloud.upload.exception;

/**
 * @author: ljh
 * @date: 2024/2/22 22:35
 * @version: 1.0
 */
public interface IErrorCode {

    /**
     * 获取错误码
     * @return 错误码
     */
    String getCode();

    /**
     * 获取错误描述
     * @return 错误描述
     */
    String getDesc();
}
