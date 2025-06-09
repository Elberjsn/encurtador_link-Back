package org.elberjsn.encurtador_link.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica CORS a todas as rotas (/**) da sua API
                .allowedOrigins("http://localhost:4200") // A origem exata do seu aplicativo Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP que seu frontend usará
                .allowedHeaders("*") // Permite todos os cabeçalhos (Authorization, Content-Type, etc.)
                .allowCredentials(true)
                .exposedHeaders("Authorization","ID") 
                .maxAge(3600); // Define por quanto tempo os resultados do preflight (OPTIONS) podem ser armazenados em cache

    }

    // This class can be used to configure web-related settings if needed.
    // Currently, it does not contain any specific configurations.
    
    // You can override methods from WebMvcConfigurer to customize the behavior
    // of the Spring MVC framework, such as adding interceptors, view resolvers, etc.

}
