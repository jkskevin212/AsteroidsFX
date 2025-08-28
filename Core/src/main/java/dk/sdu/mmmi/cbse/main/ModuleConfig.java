package dk.sdu.mmmi.cbse.main;


import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;

import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
class ModuleConfig {
    
    public ModuleConfig() {
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList(){
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Bean
    public List<IAsteroidSplitter> asteroidSplitters() {
        return ServiceLoader.load(IAsteroidSplitter.class)
                .stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    @Bean
    public org.springframework.web.client.RestTemplate restTemplate() { return new org.springframework.web.client.RestTemplate(); }

    @Bean
    public ScoringClient scoringClient(org.springframework.web.client.RestTemplate rt,
                                       org.springframework.core.env.Environment env) {
        String base = env.getProperty("core.baseUrl", "http://localhost:8080");
        return new ScoringClient(rt, base);
    }

    @Bean
    public Game game(ScoringClient scoring) {
        return new Game(gamePluginServices(), entityProcessingServiceList(), postEntityProcessingServices(), scoring);
    }

}
