//package com.tothefor.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
///**
// * 拦截器配置
// * @Author Steven
// * @Date 2022/08/31 13:28
// 
// */
//
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor())
//                .addPathPatterns("/**") //哪些url需要验证
//                .excludePathPatterns("/user/login"); //哪些不需要验证
//    }
//    @Bean
//    public JWTInterceptor jwtInterceptor(){
//        return new JWTInterceptor();
//    }
//}
