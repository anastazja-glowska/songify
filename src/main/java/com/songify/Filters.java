//package com.songify;
//
//
//import jakarta.servlet.Filter;
//import lombok.AllArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.web.FilterChainProxy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@AllArgsConstructor
//@Component
//@Log4j2
//public class Filters implements CommandLineRunner {
//
//    private final ApplicationContext applicationContext;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Map<String, Filter> filters = applicationContext.getBeansOfType(Filter.class);
//        filters.forEach((s, filter) -> {
//            log.info("Filter name " +  s + "filter class " + filter.getClass().getName());
//        });
//
//
//        Map<String, FilterRegistrationBean> beansOfType = applicationContext.getBeansOfType(FilterRegistrationBean.class);
//
//        beansOfType.forEach((s, filterRegistrationBean) -> {
//            log.info("Filter registration bean " + s + "filter class " + filterRegistrationBean.getClass().getName());
//        });
//
//        if(applicationContext.containsBean("springSecurityFilterChain")){
//            FilterChainProxy filterChainProxy = applicationContext.getBean(FilterChainProxy.class);
//            for(SecurityFilterChain securityFilterChain : filterChainProxy.getFilterChains()){
//                for(Filter filter : securityFilterChain.getFilters()){
//                    log.info("Spring security filter class " +  filter.getClass().getName());
//                }
//            }
//        }
//
//    }
//}
