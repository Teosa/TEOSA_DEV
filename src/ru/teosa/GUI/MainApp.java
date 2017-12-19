package ru.teosa.GUI;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.teosa.GUI.model.LoginForm;
import ru.teosa.GUI.view.LoginController;

public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootLayout;
    private LoginForm mainInfoInitData;// = new LoginForm("0", "0", "No connection-");
    private WebDriver driver;
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public LoginForm getMainInfoInitData() {
		return mainInfoInitData;
	}
	
	public static void main(String[] args) {
		System.out.println("main");
		launch(args);
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public void start(Stage primaryStage) {
		System.out.println("start");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("HowrseHelperBot");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				System.out.println(driver);
				if(driver != null) driver.close();
			}
		});
        
        initRootLayout();
        showLoginForm();
	}

	
	
	
	
	/** Инициализирует корневой макет */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void showLoginForm() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane loginForm = (AnchorPane) loader.load();

            rootLayout.setCenter(loginForm);
        
            
            LoginController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void showMainForm() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainWindow.fxml"));
            AnchorPane loginForm = (AnchorPane) loader.load();

            rootLayout.setCenter(loginForm);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
