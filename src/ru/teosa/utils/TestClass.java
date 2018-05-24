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
import ru.teosa.account.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSetMetaData;


public class TestClass extends Application{
	
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	
	public static void main(String[] args) {
	

//		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
//		String connectionURL = "jdbc:derby:HBBDB;create=true;";
		
		try {
//			Class.forName(driver).newInstance();
//		    Connection conn = null;
//		    Statement stmt = null;
		    
			  try {
			  
//				BasicDataSource bdSource = new BasicDataSource();
//				bdSource.setUrl(connectionURL);
//				bdSource.getConnection();
//				NamedParameterJdbcTemplate pstmt = new NamedParameterJdbcTemplate(bdSource);

//				int mcb = 10000;
//				int wcb = 4000;
//				int emb = 2510377;
//
//	            int mny;
//	            boolean started = false;
//
//	            long divisor = 1000000000;//(long)Math.pow(1000, maxPower);
//	            long integer = emb - mcb;//(long)Math.floor(num);
//
//	            System.out.println(divisor);
//	            System.out.println(integer);
//
//	            
//	            System.out.println("----------------------");
//	            for(int i = 8; i >= 0; i--){
//	            	
//
//	            	
//	            	divisor /= 10;
//	                mny = (int)(integer / divisor);
//	                integer %= divisor;
//	                
//	                System.out.println(divisor);
//	                System.out.println(mny);
//	                System.out.println(integer);
//	                
//	            	if(!started)
//	            	started = mny > 0;
//	                
//	                if(mny != 0){
//		                
//		                System.out.println("FOR SALE: " + mny * divisor);
//		                
//		                System.out.println("***********");	                    
//	                        
//	                }
//	                else if(started){
//
//		                
//		                System.out.println("zFOR SALE: " + divisor);
//		                
//		                System.out.println("***********");	
//	                }
//	                
//
//	            }
//                System.out.println("----------------------");
				  
				  int tosell = 10062;
				  int i;
				  
				  while (tosell > 0) {
					  i = Tools.getQtyForSale(tosell);
					  if(i > 0) {
						  System.out.println("selling:" + i); 
						  tosell =  tosell -i;
						  System.out.println("left:" + tosell);  
					  }
				  }
				  
//				  System.out.println(Tools.xxx(71));

			  }
			  catch(Exception e) {
				  e.printStackTrace();
			  }
			} catch (Exception e) {
			 e.printStackTrace();
			}
		
//		try {
//			DriverManager.getConnection("jdbc:derby:;shutdown=true");
//		}
//		catch(SQLException e) {System.out.println(e.getMessage());}

	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
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
