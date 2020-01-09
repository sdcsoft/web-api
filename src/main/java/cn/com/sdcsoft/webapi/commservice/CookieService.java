package cn.com.sdcsoft.webapi.commservice;

import cn.com.sdcsoft.webapi.entity.Token;
import cn.com.sdcsoft.webapi.entity.datacenter.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

@Service
public class CookieService {


    public static final String COOKIE_NAME_USER_INFO = "sdcsoft.userinfo";

    private static final int USER_INFO_FIELD_COUNT = 2;
    private static final int USER_INFO_FIELD_INDEX_EmployeeID = 0;
    private static final int USER_INFO_FIELD_INDEX_OrgID = 1;

    public static final String USER_INFO_FIELD_NAME_EmployeeID = "EmployeeID";
    public static final String USER_INFO_FIELD_NAME_OrgID = "OrgID";

    @Value("${token.cookie.name}")
    private String userTokenName;
    @Value("${token.cookie.domain}")
    private String cookieDomain;
    @Value("${token.cookie.path}")
    private String cookiePath;

    public Cookie getUserToken(String userId) {
        Token token = Token.getInstance(userId);
        Cookie cookie = new Cookie(userTokenName, token.getTokenString());
        cookie.setDomain(cookieDomain);
        cookie.setPath(cookiePath);
        return cookie;
    }

    public boolean verifyUserToken(HttpServletRequest request) {
        return WebUtils.getCookie(request, userTokenName) != null;
    }


    public Cookie getUserCookie(Employee employee) throws UnsupportedEncodingException {
        String[] infos = getUseInfoString(employee);
        String infoString = String.join(":", infos);
        byte[] data = encryptKaiser(infoString, 8).getBytes("utf-8");
        infoString = Base64.getEncoder().encodeToString(data);

        Cookie cookie = new Cookie(COOKIE_NAME_USER_INFO, infoString);
        cookie.setDomain(cookieDomain);
        cookie.setPath(cookiePath);
        return cookie;
    }

    private static String[] getUseInfoString(Employee employee) {
        String[] infos = new String[2];
        infos[USER_INFO_FIELD_INDEX_EmployeeID] = String.format("%s", employee.getId());
        infos[USER_INFO_FIELD_INDEX_OrgID] = String.format("%s", employee.getOrgId());
        return infos;
    }

    public static boolean verifyUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME_USER_INFO);
        if (null == cookie) {
            return false;
        }
        String str = new String(Base64.getDecoder().decode(cookie.getValue()), "utf-8");//.encodeToString(data).split(":");
        String[] infos = decryptKaiser(str, 8).split(":");
        if (infos.length == USER_INFO_FIELD_COUNT) {
            request.setAttribute(USER_INFO_FIELD_NAME_EmployeeID, infos[USER_INFO_FIELD_INDEX_EmployeeID]);
            request.setAttribute(USER_INFO_FIELD_NAME_OrgID, infos[USER_INFO_FIELD_INDEX_OrgID]);
            return true;
        }
        return false;
    }

    public static String getUserInfo(HttpServletRequest request, int user_info_field_index) {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME_USER_INFO);
        if (null == cookie) {
            return null;
        }
        String[] infos = decryptKaiser(cookie.getValue(), 8).split(":");
        if (infos.length > user_info_field_index)
            return infos[user_info_field_index];
        return null;
    }


    /**
     * 使用凯撒加密方式加密数据
     *
     * @param orignal :原文
     * @param key     :密钥
     * @return :加密后的数据
     */
    private static String encryptKaiser(String orignal, int key) {
        // 将字符串转为字符数组
        char[] chars = orignal.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 遍历数组
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode += key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }

        return sb.toString();
    }

    /**
     * 使用凯撒加密方式解密数据
     *
     * @param encryptedData :密文
     * @param key           :密钥
     * @return : 源数据
     */
    private static String decryptKaiser(String encryptedData, int key) {
        // 将字符串转为字符数组
        char[] chars = encryptedData.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 遍历数组
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode -= key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }

        return sb.toString();
    }
}
