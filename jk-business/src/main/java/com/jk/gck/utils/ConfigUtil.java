package com.jk.gck.utils;

public class ConfigUtil {
	/**
	 * 小程序APP_ID
	 */
	public final static String APP_ID = "wx2fea86c00e3bd18e";
	/**
	 * 小程序APP_SECRET
	 */
	public final static String APP_SECRET = "3b4b6f322780f26b340d475425576d1d";



	/**
	 * 商户平台商户号
	 */
	public final static String MCH_ID = "1556747301";
	/**
	 * 商户平台API密钥
	 */
	public final static String API_KEY = "gzjjjskfqgzgs134679gzjjjskfqgzgs";
	/**
	 * 签名加密方式
	 */
	public final static String SIGN_TYPE = "MD5";
	/**
	 * openid地址
	 */
	public final static String OPEN_ID_URL = "https://api.weixin.qq.com/sns/jscode2session";
	/**
	 * 统一下单地址
	 */
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 统一退款地址
	 */
	public final static String REFUND_ORDER_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/**
	 * 下单描述
	 */
	public final static String ORDER_BODY = "经开国资-缴费订单";
	/**
	 * 支付回调地址
	 */
	public final static String CALLBACK_PAY_URL = "http://www.xizinet.com/jk-zc/api/wechat/callback/pay";
}
