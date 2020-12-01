package cn.com.sdcsoft.webapi.wechat.controller;


import cn.com.sdcsoft.webapi.mapper.Wechat_DB.Wechat_DB_JinRong_OrderMapper;
import cn.com.sdcsoft.webapi.wechat.config.WeChatPayConfig;
import cn.com.sdcsoft.webapi.wechat.controller.utils.WeChatPayUtil;
import cn.com.sdcsoft.webapi.wechat.entity.JinRong_Order;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


@RestController
@RequestMapping(value = "/wechat/PayOrder")
public class Wechat_PayOrderController {

    @Autowired
    private Wechat_DB_JinRong_OrderMapper wechat_db_jinRong_orderMapper;

    @PostMapping(value = "/createPayOrder")
    public JSONObject createPayOrder(String openId,String money,String orderNo,String title,HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try{
            //生成的随机字符串
            String nonce_str = WeChatPayUtil.getRandomStringByLength(32);
            //商品名称
            String body = new String(title.getBytes("UTF-8"),"UTF-8");
            //获取本机的ip地址
            String spbill_create_ip = WeChatPayUtil.getIpAddr(request);


            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", WeChatPayConfig.appId);
            packageParams.put("mch_id", WeChatPayConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo);//商户订单号
            packageParams.put("total_fee", money);
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WeChatPayConfig.notify_url);
            packageParams.put("trade_type", WeChatPayConfig.TRADETYPE);
            packageParams.put("openid", openId);


            // 除去数组中的空值和签名参数
            packageParams = Wechat_PayOrderController.paraFilter(packageParams);
            String prestr = Wechat_PayOrderController.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串


            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = Wechat_PayOrderController.sign(prestr, WeChatPayConfig.key, "utf-8").toUpperCase();
            //logger.info("=======================第一次签名：" + mysign + "=====================");
            System.out.println("mysign：" + mysign);

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml version='1.0' encoding='gbk'>" + "<appid>" + WeChatPayConfig.appId + "</appid>"
                    + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + WeChatPayConfig.mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + WeChatPayConfig.notify_url + "</notify_url>"
                    + "<openid>" + openId + "</openid>"
                    + "<out_trade_no>" + orderNo + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + money + "</total_fee>"
                    + "<trade_type>" + WeChatPayConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";


            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);


            //调用统一下单接口，并接受返回的结果
            String result = Wechat_PayOrderController.httpRequest(WeChatPayConfig.pay_url, "POST", xml);


            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);


            // 将解析结果存储在HashMap中
            Map map = Wechat_PayOrderController.doXMLParse(result);


            String return_code = (String) map.get("return_code");//返回状态码


            //返回给移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            if(return_code == "SUCCESS" || return_code.equals(return_code)){
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误


                String stringSignTemp = "appId=" + WeChatPayConfig.appId + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=" + WeChatPayConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = Wechat_PayOrderController.sign(stringSignTemp, WeChatPayConfig.key, "utf-8").toUpperCase();
                //logger.info("=======================第二次签名：" + paySign + "=====================");


                response.put("paySign", paySign);


                //更新订单信息
                //业务逻辑代码
            }


            response.put("appid", WeChatPayConfig.appId);
            json.put("errMsg", "OK");
            //json.setSuccess(true);
            json.put("data", response);
            //json.setData(response);
        }catch(Exception e){
            e.printStackTrace();
            json.put("errMsg", "Failed");
            //json.setSuccess(false);
            //json.setMsg("发起失败");
        }
        return json;
    }


    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        text = text + "&key=" + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + "&key=" + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        System.out.println("mysign"+mysign);
        System.out.println("sign"+sign);

        if (mysign.toUpperCase().equals(sign)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    /**
     * 生成6位或10位随机数 param codeLength(多少位)
     * @return
     */
    public static String createCode(int codeLength) {
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += (int) (Math.random() * 9);
        }
        return code;
    }
    @SuppressWarnings("unused")
    private static boolean isValidChar(char ch) {
        if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
            return true;
        if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))
            return true;// 简体中文汉字编码
        return false;
    }
    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法
     * @param outputStr 参数
     */
    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        // 创建SSLContext
        StringBuffer buffer = null;
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容
            if(null !=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }
            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static String urlEncodeUTF8(String source){
        String result=source;
        try {
            result=java.net.URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws Exception {
        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

//关闭流
        in.close();

        return m;
    }
    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }
    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }


    @GetMapping(value = "/hello")
    public  String  wxNotify() {
        return "hi";
    }

    /**
     * 微信小程序支付成功回调函数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/callback")
    public  void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);


        Map map = Wechat_PayOrderController.doXMLParse(notityXml);


        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            System.out.println(Wechat_PayOrderController.verify(Wechat_PayOrderController.createLinkString(Wechat_PayOrderController.paraFilter(map)), (String)map.get("sign"), WeChatPayConfig.key, "utf-8"));
            if(Wechat_PayOrderController.verify(Wechat_PayOrderController.createLinkString(Wechat_PayOrderController.paraFilter(map)), (String)map.get("sign"), WeChatPayConfig.key, "utf-8")){
                /**业务逻辑代码start**/
                JinRong_Order jo=wechat_db_jinRong_orderMapper.getJinRong_OrderListByOutTradeNo((String) map.get("out_trade_no"));
                String total_fee = (String) map.get("total_fee");

                jo.setStatus(1);
                jo.setWechatOrderId((String) map.get("transaction_id"));
                wechat_db_jinRong_orderMapper.updateJinRong_Order(jo);
                /**业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");


        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }





}

