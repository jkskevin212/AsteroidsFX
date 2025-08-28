package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {
    private AnnotationConfigApplicationContext ctx;

    @Override
    public void start(Stage stage) throws Exception {
        ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);
        Game game = ctx.getBean(Game.class);
        game.start(stage);
        game.render();
    }

    @Override
    public void stop() {
        if (ctx != null) ctx.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
