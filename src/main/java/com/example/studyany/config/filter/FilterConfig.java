package com.example.studyany.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	@Bean
    public FilterRegistrationBean<FirstFilter> firstFilterRegister()  {
        FilterRegistrationBean<FirstFilter> registrationBean = new FilterRegistrationBean<>(new FirstFilter());
//        registrationBean.setFilter(new FirstFilter());
        // 필터를 적용할 URL 패턴 지정
//        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<SecondFilter> secondFilterRegister()  {
//        FilterRegistrationBean<SecondFilter> registrationBean = new FilterRegistrationBean<>(new SecondFilter());
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }
}
