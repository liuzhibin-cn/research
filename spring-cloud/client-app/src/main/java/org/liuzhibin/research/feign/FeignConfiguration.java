package org.liuzhibin.research.feign;

import org.liuzhibin.research.client.Application;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * Feign的定制化配置项
 * 
 * <p>
 * <strong style="color:red">注意：</strong>
 * <ol>
 * <li>Feign会建立自己独立的Spring Contex，与Spring主应用的Context隔离。<br/>
 *     在{@link Application}的注解中，通过{@link ComponentScan @ComponentScan}为Spring主应用指定了扫描路径，
 *     {@link EnableFeignClients @EnableFeignClients}则为Feign指定了扫描路径，这两个扫描路径不重叠。</li>
 * <li>如果将{@link FeignConfiguration}放到{@link ComponentScan @ComponentScan}扫描路径下，
 *     {@link FeignConfiguration}会应用到所有<code>FeignClient</code>(不需要使用{@link FeignClient#configuration() @FeignClient#configuration()}指定)。<br />
 *     把{@link FeignConfiguration}放在{@link EnableFeignClients @EnableFeignClients}的扫描路径下，
 *     可以实现定义多个不同的<code>XxxConfiguration</code>，应用于不同的<code>FeignClient</code>，
 *     每个<code>FeignClient</code>必须通过{@link FeignClient#configuration() @FeignClient#configuration()}指定配置类。<br />
 *     参考<a href="http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html#spring-cloud-feign">http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html#spring-cloud-feign</a>
 * </li>
 * </ol>
 * </p>
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 3, 2016
 */
@Configuration
public class FeignConfiguration {
    /**
     * 打开Feign的日志。
     * <p>参考<a href="http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html#_feign_logging">http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html#_feign_logging</a></p>
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}