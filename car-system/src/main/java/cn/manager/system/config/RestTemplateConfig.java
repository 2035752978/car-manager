package cn.manager.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 单体项目 远程调用方法
 *
 * @author ljc
 * @description ok
 * @version 1.0.0
 */
@Configuration
public class RestTemplateConfig {
 
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }
 
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //客户端与服务端建立连接超时时间
        factory.setConnectTimeout(60000);
        //客户端从服务端读取数据的超时时间
        factory.setReadTimeout(60000);
        return factory;
    }
}