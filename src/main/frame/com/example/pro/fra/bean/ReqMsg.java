package com.example.pro.fra.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Nebula
 * */
public class ReqMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull(message = "header不能为空")
	@Valid
	private Header header ;
	@NotNull(message = "content不能为空")
	private JSONObject content;

	private ReqMsg() {
	}
	/**
	 * 用于内部请求转发
	 */
	public static ReqMsg newInstance() {
		ReqMsg reqMsg = new ReqMsg();
		reqMsg.header = reqMsg.new Header();
		reqMsg.setContent(new JSONObject());
		return reqMsg;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public JSONObject getContent() {
		return content;
	}

	public void setContent(JSONObject content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return this.toJSONString();
	}

	public <T> T getContentObject(Class<T> clazz) {
		return JSON.parseObject(content.toJSONString(), clazz);
	}

	public static ReqMsg parseJSONString(String jsonString) {
		return JSON.parseObject(jsonString, ReqMsg.class);

	}


	public String toJSONString() {
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
	}

	public JSONObject toJSONObj(){
		return JSON.parseObject(this.toJSONString());
	}

	public class Header implements Serializable{

		private static final long serialVersionUID = -3469684736333563837L;
		/**
		 * 请求序列.
		 * */
		@NotNull(message = "reqSeq不能为空")
		private String reqSeq;

		/**
		 * 请求来源.
		 * */
		private String reqSource;

		/**
		 * 请求目标.
		 * */
		private String reqTarget;

		/**
		 * 请求时间.
		 * */
		private String reqTime;

		/**
		 * 请求描述.
		 * */
		private String reqDesc;

		/**
		 * 设备唯一标识.
		 * */
		private String deviceNo;

		/**
		 * 项目特色性参数.
		 * */
		private String sourceUrl;

		/**
		 * 前端访问token
		 */
		private String token;


		public Header() {
		}

		public String getReqSeq() {
			return reqSeq;
		}

		public void setReqSeq(String reqSeq) {
			this.reqSeq = reqSeq;
		}

		public String getReqSource() {
			return reqSource;
		}

		public void setReqSource(String reqSource) {
			this.reqSource = reqSource;
		}

		public String getReqTarget() {
			return reqTarget;
		}

		public void setReqTarget(String reqTarget) {
			this.reqTarget = reqTarget;
		}

		public String getReqTime() {
			return reqTime;
		}

		public void setReqTime(String reqTime) {
			this.reqTime = reqTime;
		}

		public String getReqDesc() {
			return reqDesc;
		}

		public void setReqDesc(String reqDesc) {
			this.reqDesc = reqDesc;
		}

		public String getDeviceNo() {
			return deviceNo;
		}

		public void setDeviceNo(String deviceNo) {
			this.deviceNo = deviceNo;
		}

		public String getSourceUrl() {
			return sourceUrl;
		}

		public void setSourceUrl(String sourceUrl) {
			this.sourceUrl = sourceUrl;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}

}
