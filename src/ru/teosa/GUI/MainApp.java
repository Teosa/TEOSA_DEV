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
import ru.teosa.GUI.view.LoginController;
import ru.teosa.GUI.view.MainWindowController;
import org.apache.log4j.Logger;

public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootLayout;
    private WebDriver driver;
    
//************************************************************************************
//********************* GETTERS/SETTERS **********************************************
//************************************************************************************
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public WebDriver getDriver() {
		return driver;
	}
//************************************************************************************
//********************* METHODS ******************************************************
//************************************************************************************	
	public static void main(String[] args) {
		launch(args);
	}

	/** Инициализация корневого макета + */
	@Override
	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("HowrseHelperBot");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				if(driver != null) driver.quit();
			}
		});
        
        initRootLayout();
        showLoginForm();
	}
	
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Инициализация и отображение формы авторизации
     * */
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
    
    /**
     * Инициализация и отображение главной формы
     * */
    public void showMainForm() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainWindow.fxml"));
            
            AnchorPane loginForm = (AnchorPane) loader.load();
            MainWindowController controller = loader.getController();
           
            controller.setMainApp(this);
            
            rootLayout.setCenter(loginForm);
            
            this.getPrimaryStage().sizeToScene();
        	this.getPrimaryStage().centerOnScreen();
        	
        	controller.initWindow();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
