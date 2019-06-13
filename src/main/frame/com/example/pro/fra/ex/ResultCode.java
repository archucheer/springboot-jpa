package com.example.pro.fra.ex;


/**
 * 返回code
 */

public enum ResultCode {
    //平台分类(2位)+服务标识(2位)+一级分类(1位)+二级分类(4位)

    /**
     * 请求成功
     */
    SUCCESS(100, "请求成功"),
    //================系统错误码=====================
    SYSTEM_ERROR(201, "未知系统错误"),
    ERROR_SQL(202, "数据库操作错误"),
    ERROR_NET_RPC(203, "内部网络请求失败"),
    ERROR_OF_NOSQL(204,"NOSQL请求失败"),
    ERROR_OF_SDF(205,"日期格式转换错误"),
    WXPAY_OP_ERROR(209,"内部调用微信服务失败"),

    //================业务错误码=====================
    REQREQUEST_SOURCE_ERROR(101,"非法请求的来源"),
    REQREQUEST_PARAM_ERROR(102,"请求参数错误"),
    REQREQUEST_PARAM_MISS(103, "参数缺失"),
    DATADICT_TPYE_ERROR(104, "字段类型错误"),
    FILE_TYPE_ERROR(105,"文件类型错误"),
    FILE_SAVE_ERROR(106,"文件存储失败"),
    FILE_IS_EMPTY(107,"缺少文件参数"),
    OAUTH2_ERROR(108,"微信用户未授权"),
    BUSINESS_CHECK_ERROR(109,"业务校验失败"),
    REQREQUEST_OTHOR_ERROR(199, "其他错误");

    private int code;
    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
