package ru.teosa.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

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
import ru.teosa.herdSettings.HerdRunSettings;

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
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSetMetaData;


public class TestClass extends Application{
	
	 private Stage primaryStage;
	 private BorderPane rootLayout;
	
	public static void main(String[] args) {
	

//		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		String connectionURL = "jdbc:derby:HBBDB;create=true;";
		
		try {
//			Class.forName(driver).newInstance();
//		    Connection conn = null;
//		    Statement stmt = null;
		    
			  try {
			  
//				BasicDataSource bdSource = new BasicDataSource();
//				bdSource.setUrl(connectionURL);
//				bdSource.getConnection();
//				NamedParameterJdbcTemplate pstmt = new NamedParameterJdbcTemplate(bdSource);


				

				  System.out.println(new HerdRunSettings());
				  
				  
				  
				  
				  

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
	
	private static void whatClass(Object o) {
		System.out.println(o.getClass().getSimpleName());		
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
    
    private void saveProgramtest() throws Exception{
    	
    	String connectionURL = "jdbc:derby:HBBDB;create=true;";
		BasicDataSource bdSource = new BasicDataSource();
		bdSource.setUrl(connectionURL);
		bdSource.getConnection();
		NamedParameterJdbcTemplate pstmt = new NamedParameterJdbcTemplate(bdSource);

//		EC_registerSettings testclass = new EC_registerSettings();
//		testclass.setCarrot(true);
//		testclass.setLocation('F');
		
		HerdRunSettings settings = new HerdRunSettings();
//		settings.setEC_registerSettings(testclass);
		
		
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		ObjectOutput out = null;
//		
//		try {
//		  out = new ObjectOutputStream(bos);   
//		  out.writeObject(testclass);
//		  out.flush();
//		  
//		  byte[] yourBytes = bos.toByteArray();
//		  
//		  HashMap params = new HashMap();
//
//		  params.put("name", "testObject");
//		  params.put("object", yourBytes);
//		  
//		  pstmt.update("INSERT INTO PROGRAMS VALUES (DEFAULT, :name, :object)", params);
//		
//		} finally {
//		  try {
//		    bos.close();
//		  } catch (IOException ex) {}
//		}
		
		byte[] bytes = SerializationUtils.serialize(settings);
		
		HashMap params = new HashMap();

		params.put("name", "Стандартные настройки");
		params.put("object", bytes);
						  
//		pstmt.update("INSERT INTO PROGRAMS VALUES (DEFAULT, :name, :object)", params);
//					
//		System.out.println("*************************");
		

		
//		pstmt.queryForObject("SELECT * FROM PROGRAMS WHERE ID = 101", new HashMap(), new RowMapper() {
//
//			@Override
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				byte[] bytes = rs.getBytes("settings");
//				HerdRunSettings o  = SerializationUtils.deserialize(bytes);
//
//				  System.out.println(o);
//				  System.out.println(o.getEC_registerSettings());
//				  System.out.println(o.getEC_registerSettings().getLocation());
//				  System.out.println(o.getEC_registerSettings().getTest());
//				
//				return null;
//			}
//		});
		
		
//		pstmt.queryForObject("SELECT * FROM PROGRAMS WHERE ID = 1", new HashMap(), new RowMapper() {
//			@Override
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				 byte[] bytes = rs.getBytes("settings");
//				 
//				 try {
//						ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//						ObjectInput in = null;
//						try {
//						  in = new ObjectInputStream(bis);
//						  EC_registerSettings o = (EC_registerSettings)in.readObject(); 
//						  
//						  System.out.println(o);
//						  System.out.println(o.getLocation());
//						  System.out.println(o.isCarrot());
//						  
//						} finally {
//						  try {
//						    if (in != null) {
//						      in.close();
//						    }
//						  } catch (IOException ex) {
//						    // ignore close exception
//						  }
//						}
//				 }
//				 catch(Exception e) {}
//
//				 
//				 
//				return null;
//			}
//		});
		

		
		
		
////	    FileOutputStream fos = new FileOutputStream("temp.out");
//	    ObjectOutputStream oos = new ObjectOutputStream();
//
//	    oos.writeObject(testclass);
//	    oos.flush();
//	    oos.close(); 
    }
}
