package banque.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Autorise toutes les routes sous /api
                .allowedOrigins("http://localhost:4200") // Autorise Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes HTTP permises
                .allowedHeaders("*") // Tous les en-têtes
                .allowCredentials(true); // Optionnel, si tu utilises des cookies
    }
}