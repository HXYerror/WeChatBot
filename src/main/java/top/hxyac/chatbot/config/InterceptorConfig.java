package top.hxyac.chatbot.config;

import top.hxyac.chatbot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.hxyac.chatbot.interceptor.*;


/**
 * 拦截器配置
 *
 *  需要权限   /api/v1/pri/
 *  不需要权限 /api/v1/pub/
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private final RedisUtils redisUtils;

    public InterceptorConfig(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor(redisUtils);
    }

    @Bean
    CorsInterceptor corsInterceptor(){return new CorsInterceptor();}

    @Bean
    UuidInterceptor uuidInterceptor(){return new UuidInterceptor();}



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(uuidInterceptor()).addPathPatterns("/**");
          registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                    .excludePathPatterns("/api/v1/pri/user/login")
                  .excludePathPatterns("/api/v1/pri/user/register");
//                .excludePathPatterns("/api/v1/pri/topic/all")
//                .excludePathPatterns("/api/v1/pri/answer/get_post")
//                .excludePathPatterns("/api/v1/pri/post/new")
//                .excludePathPatterns("/api/v1/pri/post/top")
//                .excludePathPatterns("/api/v1/pri/post/topic")
//                .excludePathPatterns("/api/v1/pri/post/show")
//                .excludePathPatterns("/api/v1/pri/post/search")
//                        .excludePathPatterns("/api/v1/pri/user/my_token_dev");
//        registry.addInterceptor(adminInterceptor()).addPathPatterns("/api/v1/pri/user/admin/**").addPathPatterns("/api/v1/pri/post/admin/**");
//        registry.addInterceptor(rootInterceptor()).addPathPatterns("/api/v1/pri/user/root/**").addPathPatterns("api/v1/pri/topic/root/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
