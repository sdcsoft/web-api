package cn.com.sdcsoft.webapi.wechat.config;

public class WeChatPayConfig {
    //微信小程序appid
    public static String appId = "wxec5096c52525bffb";
    //微信小程序appsecret
    public static String appSecret = "b23d65a821c165ed7cc9b7e56b501b6e";
    //微信支付主体
    public static String title = "简洁软件-服务产品";
   // public static String orderNo = "";
    //微信商户号
    public static String mch_id="1558559941";
    //微信支付的商户密钥
    public static final String key = "jianjieruanjianyouxiangongsi6666";
    //获取微信Openid的请求地址
    public static String WxGetOpenIdUrl = "";
    //支付成功后的服务器回调url
    public static final String notify_url="https://apis.sdcsoft.com.cn/webapi/wechat/PayOrder/callback";
    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
