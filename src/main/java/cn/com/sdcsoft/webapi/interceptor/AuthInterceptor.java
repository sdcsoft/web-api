package cn.com.sdcsoft.webapi.interceptor;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.annotation.Permission;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * 身份认证拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Autowired
    CookieService cookieService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {//无需拦截
            return true;
        }
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();
        final Class<?> clazz = method.getDeclaringClass();
        if (clazz.isAnnotationPresent(Auth.class) || method.isAnnotationPresent(Auth.class)) {
            if (isLogin(request)) {
                return true;
            } else {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("{\"code\":-1,\"msg\":\"请登录，再次执行该功能！\"}");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 判断一个方法是否需要登录
     *
     * @param method
     * @return
     */
    private boolean isLoginRequired(Method method) {
        boolean result = true;
        if (method.isAnnotationPresent(Permission.class)) {
            result = method.getAnnotation(Permission.class).loginReqired();
        }
        return result;
    }

    //判断是否已经登录
    private boolean isLogin(HttpServletRequest request) {
        //return cookieService.verifyUserToken(request);
        try {
            return CookieService.verifyUserInfo(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
