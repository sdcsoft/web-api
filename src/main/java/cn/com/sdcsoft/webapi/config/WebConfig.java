package cn.com.sdcsoft.webapi.config;

import cn.com.sdcsoft.webapi.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    AuthInterceptor authInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(authInterceptor).
                addPathPatterns("/webapi/**").
                excludePathPatterns("/account/**","/cache/**","/test/**");
        super.addInterceptors(registry);
    }

    /**
     * 配置URL大小写不敏感
     *
     * @param configurer
     */
    @Override
    protected void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

//    @Value("${xxxx}")
//    private String boilerPath;
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        /** 图片传路径 */
//        registry.addResourceHandler("/boiler/**").addResourceLocations("file:" +boilerPath );
//        super.addResourceHandlers(registry);
//    }
}
