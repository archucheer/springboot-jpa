package com.example.pro.fra.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.example.pro.fra.configure.AppConfig;
import com.example.pro.fra.ex.ResultCode;
import com.example.pro.fra.ex.ResultException;
import com.example.pro.fra.util.TimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ccz
 */
public class RetMsg implements Serializable {
    private Header header=new Header();
    private Object content = "{}";

    @JsonIgnore
    @JSONField(serialize = false)
    private HeaderBuilder headerBuilder = new HeaderBuilder();
    @JsonIgnore
    @JSONField(serialize = false)
    private Map<String, Object> contentMap = new HashMap<>();

    public static RetMsg newInstance() {
        RetMsg retMsg = new RetMsg();
        Header header = retMsg.getHeaderBuilder()
                .withRespCode(String.valueOf(ResultCode.SUCCESS.getCode()))//默认填充成功code
                .withRespDesc(ResultCode.SUCCESS.getDesc())
                .withRespResult("")
                .build();
        retMsg.setHeader(header);
        return retMsg;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public JSONObject getHeaderJsonObj() {
        return JSON.parseObject(JSON.toJSONString(header));
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public JSONObject getContentJsonObj() {
        return JSON.parseObject(JSON.toJSONString(content));
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getContent() {
        return content;
    }

    public RetMsg setContent(Object content) {
        this.content = content;
        return this;
    }

    public HeaderBuilder getHeaderBuilder() {
        return headerBuilder;
    }

    public void setHeaderBuilder(HeaderBuilder headerBuilder) {
        this.headerBuilder = headerBuilder;
    }

    public RetMsg putContent(String key, Object value) {
        contentMap.put(key, value);
        this.content = contentMap;
        return this;
    }


    /**
     * 结果代码和描述
     */
    public RetMsg setRespCodeAndDesc(ResultCode resultCode, String respDesc) {
        this.header.respCode = String.valueOf(resultCode.getCode());
        this.header.respDesc = resultCode.getDesc();
        this.header.respResult = respDesc;
        return this;
    }

    /**
     * 结果代码和描述
     */
    public RetMsg setException(ResultException exception) {
        this.header.respCode = String.valueOf(exception.getResultCode().getCode());
        this.header.respDesc = exception.getResultCode().getDesc();
        this.header.respResult = exception.getExceptionMessage();
//        String msg = "".equals(exception.getErrorMsg()) ? exception.getExceptionMessage() : exception.getErrorMsg();
//        this.putContent("msg", msg);
        return this;
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    /**
     * 返回体头部
     */
    public class Header implements Serializable {
        private static final long serialVersionUID = -2145617659146748941L;

        /**
         * 响应状态码.
         */
        private String respCode;
        /**
         * 平台名.
         */
        private String platform;

        /**
         * 请求返回源.
         */

        private String respSource;

        /**
         * 请求返回类型.
         */
        private String respResult;

        /**
         * 请求响应描述.
         */
        private String respDesc;
        /**
         * 响应时间.
         */
        private String respTime;

        /**
         * 请求序列.
         */
        private String reqSeq;

        private String token;


        public Header() {
            this.respTime = TimeUtils.getSimpleDateTime();
            this.respSource = AppConfig.platformId + "-" + AppConfig.serverId;
            this.platform = AppConfig.platformName +"-"+ AppConfig.serverName;
        }

        public String getRespCode() {
            return respCode;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getRespSource() {
            return respSource;
        }

        public void setRespSource(String respSource) {
            this.respSource = respSource;
        }

        public void setRespCode(String respCode) {
            this.respCode = respCode;
        }

        public String getRespResult() {
            return respResult;
        }

        public void setRespResult(String respResult) {
            this.respResult = respResult;
        }

        public String getRespDesc() {
            return respDesc;
        }

        public void setRespDesc(String respDesc) {
            this.respDesc = respDesc;
        }

        public String getRespTime() {
            return respTime;
        }

        public void setRespTime(String respTime) {
            this.respTime = respTime;
        }

        public String getReqSeq() {
            return reqSeq;
        }

        public void setReqSeq(String reqSeq) {
            this.reqSeq = reqSeq;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    /**
     * 构造Header
     */
    public class HeaderBuilder {
        private String respCode;
        private String respResult;
        private String respDesc;
        private String reqSeq;

        private HeaderBuilder() {
        }

        public HeaderBuilder aHeader() {
            return new HeaderBuilder();
        }


        public HeaderBuilder withRespCode(String respCode) {
            this.respCode = respCode;
            return this;
        }

        public HeaderBuilder withRespResult(String respResult) {
            this.respResult = respResult;
            return this;
        }

        public HeaderBuilder withRespDesc(String respDesc) {
            this.respDesc = respDesc;
            return this;
        }

        public HeaderBuilder withReqSeq(String reqSeq) {
            this.reqSeq = reqSeq;
            return this;
        }


        public Header build() {
            Header header = RetMsg.this.getHeader();
            header.setReqSeq(reqSeq);
            header.setRespCode(respCode);
            header.setRespDesc(respDesc);
            header.setRespResult(respResult);
            return header;
        }
    }
}
