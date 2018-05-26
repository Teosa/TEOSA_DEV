package ru.teosa.GUI;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.teosa.GUI.view.LoginController;
import ru.teosa.GUI.view.MainWindowController;
import ru.teosa.utils.objects.MainAppHolderSingleton;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("HowrseHelperBot");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				if(driver != null) driver.quit();
				try {
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				}
				catch(SQLException e) {System.out.println(e.getMessage());}
			}
		});
        
        initRootLayout();
        
    	MainAppHolderSingleton.getInstance().setMainApp(this);
        
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
        	Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
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
        	Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
        }
    }
    
    /**
     * Инициализация и отображение главной формы
     * */
    public void showMainForm() {
        try {
        	// Загружаем главную панель
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainWindow.fxml"));            
            BorderPane mainForm = (BorderPane) loader.load();
            
            // Позиционирование и размеры
            rootLayout.setCenter(mainForm);           
            this.getPrimaryStage().sizeToScene();
        	this.getPrimaryStage().centerOnScreen();
        } 
        catch (Exception e) {
        	Logger.getLogger("error").error(ExceptionUtils.getStackTrace(e));
        }
    }
}
