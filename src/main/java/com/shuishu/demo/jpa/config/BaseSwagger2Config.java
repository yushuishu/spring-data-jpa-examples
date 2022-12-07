package com.shuishu.demo.jpa.config;


import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author ：shuishu
 * @date   ：2022/10/31 8:23
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Configuration
@EnableSwagger2WebMvc
@ConditionalOnProperty(prefix = "knife4j", name = "enable", havingValue = "true")
public class BaseSwagger2Config {
    /**
     * 引入 Knife4j提供的扩展类
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public BaseSwagger2Config(OpenApiExtensionResolver openApiExtensionResolver){
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    private ApiInfo apiInfo() {
        // 定义标题
        return new ApiInfoBuilder().title("jpa基础工程1.0 文档")
                // 描述
                .description("1.0接口文档，http://localhost:8080/doc.html")
                // （不可见）条款地址
                .termsOfServiceUrl("http://www.gtifile.top/")
                // 作者信息
                .contact(new Contact("shuishu", "https://xxx/", "shuishu@qq.com"))
                // 版本号
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createFileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("测试")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shuishu.demo.jpa.controller"))
                .paths(PathSelectors.any()).build();
    }

}
