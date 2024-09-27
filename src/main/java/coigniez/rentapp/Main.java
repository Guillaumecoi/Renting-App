package coigniez.rentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import atlantafx.base.theme.PrimerLight;
import coigniez.rentapp.view.controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;

@SpringBootApplication
public class Main extends Application {

    private ConfigurableApplicationContext applicationContext;
    private FxWeaver fxWeaver;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(String[]::new);

        this.applicationContext = new SpringApplication(Main.class).run(args);
        this.fxWeaver = applicationContext.getBean(FxWeaver.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        Parent root = fxWeaver.loadView(MainController.class);
        if (root == null) {
            System.out.println("Failed to load view for MainController");
            return;
        }

        // Set the stylesheet for the entire scene
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("RentApp");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        this.applicationContext.close();
        Platform.exit();
    }

    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        return new SpringFxWeaver(applicationContext);
    }
}
