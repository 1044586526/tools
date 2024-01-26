package cn.quant_cloud.standard.base;

import java.io.Serializable;

/**
 * @description: 包装类
 * @author ljh
 * @date 2024/1/23 14:50
 * @version 1.0
 */
public abstract class AbstractResult<T> implements Serializable {

    private static final long serialVersionUID = -5016094401384456715L;

    /**
     * 响应代码
     */
    protected Integer code;

    /**
     * 响应信息
     */
    protected String message;


    /**
     * 响应数据
     */
    protected T data;
}
