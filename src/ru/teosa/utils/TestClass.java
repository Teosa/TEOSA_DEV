package ru.teosa.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import net.sourceforge.htmlunit.corejs.javascript.GeneratedClassLoader;
import ru.teosa.GUI.MainApp;
import ru.teosa.GUI.view.LoginController;
import ru.teosa.GUI.view.MainWindowController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSetMetaData;


public class TestClass extends Application{
	
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	
//		public static BasicDataSource  getConnection() throws ClassNotFoundException, SQLException{
//		       BasicDataSource bdSource = new BasicDataSource();
//		       bdSource.setDriverClassName(driver);
//			   bdSource.setUrl(url);
//			   bdSource.setUsername(user);
//			   bdSource.setPassword(password);
//			   bdSource.setMaxActive(20);
//			   bdSource.setMaxIdle(4);
//			   bdSource.setTestOnBorrow(true);
//			   bdSource.setTestOnReturn(true);
//			   bdSource.setDefaultTransactionIsolation(2);
//		       return bdSource;
//		    }
	public static void main(String[] args) {

		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		String connectionURL = "jdbc:derby:HBBDB;create=true;";
		
		try {
			Class.forName(driver).newInstance();
		    Connection conn = null;
		    Statement stmt = null;
		    
			  try {
//				  conn = DriverManager.getConnection(connectionURL); 
//				  stmt = conn.createStatement();
////
//		            ResultSet results = stmt.executeQuery("select * from testtable");
//		            ResultSetMetaData rsmd = results.getMetaData();
//		            int numberCols = rsmd.getColumnCount();
//		            System.out.println(numberCols);
//		            results.close();
//		            stmt.close();
				  
				  GridPane g = new GridPane();
				  
				BasicDataSource bdSource = new BasicDataSource();
				bdSource.setUrl(connectionURL);
				bdSource.getConnection();
				NamedParameterJdbcTemplate pstmt = new NamedParameterJdbcTemplate(bdSource);
				  
				System.out.println(ClassLoader.getSystemResource("INT.png"));
				HashMap params = new HashMap();
				
				File file = new File(ClassLoader.getSystemResource("INT.png").getPath());
                if(file.exists()){
                	final FileInputStream inStream = new FileInputStream(file);
                    byte[] bb = new byte[(int)file.length()];
                    inStream.read(bb);
                    params.put("img", bb);
                    inStream.close();
                    
                    pstmt.update("update GAMEVERSIONS set FLAGIMG = :img where id = 1", params);
                }
				
//				pstmt.queryForObject("select count(*) from testtable", new HashMap(), Integer.class);
				
				
			  }
			  catch(Exception e) {
				  e.printStackTrace();
			  }
			} catch (Exception e) {
			 e.printStackTrace();
			}
		
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		}
		catch(SQLException e) {System.out.println(e.getMessage());}
//		launch(args);
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
            ((ScrollPane)t.getTabs().get(0).getContent()).setContent(form1);
                     
            loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("view/ECTab.fxml"));
            AnchorPane form2 = (AnchorPane) loader1.load();
            ((ScrollPane)t.getTabs().get(1).getContent()).setContent(form2);
            
            loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("view/BreedingTab.fxml"));
            AnchorPane form3 = (AnchorPane) loader1.load();
            ((ScrollPane)t.getTabs().get(2).getContent()).setContent(form3);
                        
            rootLayout.setCenter(form);
            primaryStage.sizeToScene();    
             
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
