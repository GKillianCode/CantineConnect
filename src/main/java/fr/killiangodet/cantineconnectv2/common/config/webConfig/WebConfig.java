package fr.killiangodet.cantineconnectv2.common.config.webConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api/v1", config ->
                config.isAnnotationPresent(RestController.class)
                        && config.getPackage().getName().matches("fr\\.killiangodet\\.cantineconnectv2\\..*\\.controller")
        );
    }
}
