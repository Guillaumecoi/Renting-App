package coigniez.rentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

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
        Parent root = fxWeaver.loadView(MainController.class);
        if (root == null) {
            System.out.println("Failed to load view for MainController");
            return;
        }
        stage.setScene(new Scene(root));
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
