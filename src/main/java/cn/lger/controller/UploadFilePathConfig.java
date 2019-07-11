package cn.lger.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    public UploadFilePathConfig(){
               System.out.println("TestConfiguration 容器启动初始化...");
            }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
    }

}
