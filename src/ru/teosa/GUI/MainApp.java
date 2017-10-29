package ru.teosa.GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.teosa.GUI.model.MainInfo;

public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MainInfo mainInfoInitData = new MainInfo("0", "0", "No connection-");
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public MainInfo getMainInfoInitData() {
		return mainInfoInitData;
	}

	@Override
	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("HowrseHelperBot");

        initRootLayout();

        showPersonOverview();
	}

	public static void main(String[] args) {
		launch(args);
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
    
    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainInfo.fxml"));
            AnchorPane mainInfo = (AnchorPane) loader.load();

            rootLayout.setCenter(mainInfo);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
