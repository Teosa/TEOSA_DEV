package ru.teosa.utils;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.view.LoginController;
import ru.teosa.GUI.view.MainWindowController;

public class TestClass extends Application{
	
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TEST CLASS RUNNER");
        
        initRootLayout();
		showForm();
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

    public void showForm() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainWindow.fxml"));
            BorderPane form = (BorderPane) loader.load();
            
            TabPane t = (TabPane)form.getCenter();

            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("view/InfoTab.fxml"));
            AnchorPane form1 = (AnchorPane) loader1.load();
            t.getTabs().get(0).setContent(form1);
           
            
            loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("view/ECTab.fxml"));
            AnchorPane form2 = (AnchorPane) loader1.load();
            
            
//            form2.getProperties().addListener(new ChangeListener<Number>() {
//                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
//                        vb.setLayoutY(-new_val.doubleValue());
//                    }
//            });
            
            
            t.getTabs().get(1).setContent(form2);
            
            rootLayout.setCenter(form);
            primaryStage.sizeToScene();
        
            
            
            
//            MainWindowController controller = loader.getController();
//            controller.setMainApp(this);  
             
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
