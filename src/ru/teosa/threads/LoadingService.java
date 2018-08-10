package ru.teosa.threads;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.teosa.GUI.LoadingWindow;
import ru.teosa.utils.Msgs;

public class LoadingService extends Service<String>{
	
	private static Stage dialog;
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
			@Override
			protected String call() throws Exception 
			{
				
//				 Stage dialog = null;
				
	             Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                	 try {
	         				System.out.println("IN SERVICE");
	         				// Полоса загрузки
	         				ProgressBar progressBarr = new ProgressBar();
	         				progressBarr.setProgress(-1);
	         				
	         				//Теуст загрузки
	         				Label loadingTextLabel = new Label();
	         				loadingTextLabel.setText(LoadingWindow.getCustomLoadingText() != null ? LoadingWindow.getCustomLoadingText() : Msgs.DEFAULT_LOADING_TEXT);
	         				loadingTextLabel.setContentDisplay(ContentDisplay.CENTER);
	         				
	         				// Основная панель
	         				AnchorPane pane = new AnchorPane();
	         				pane.setPrefWidth(200);
	         				pane.setMinHeight(50);
	         				
	         				// Позиция полосы загрузки
	         				AnchorPane.setTopAnchor(progressBarr, 20.0);
	         				AnchorPane.setLeftAnchor(progressBarr, 20.0);
	         				AnchorPane.setRightAnchor(progressBarr, 20.0);

	         				// Позиция текста загрузки
	         				AnchorPane.setLeftAnchor(loadingTextLabel, 20.0);
	         				AnchorPane.setRightAnchor(loadingTextLabel, 20.0);
	         				
	         				// Добавляем элементы на панель
	         				pane.getChildren().add(progressBarr);
	         				pane.getChildren().add(loadingTextLabel);
	         				
	         				// Меняем цвет фона панели
	         			    Color color = Color.rgb(0, 0, 0, 0.35);
	         			    BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
	         			    Background background = new Background(fill);
	         			    pane.setBackground(background);
	         						
	         				// Создаем диалоговое окно
	         				//Stage
	         				dialog = new Stage();
	         				
	         				// Конфигурируем и добавляем в окно панель
	         				dialog.setResizable(false);
	         				dialog.initModality(Modality.APPLICATION_MODAL);
	         				dialog.setScene(new Scene(pane));
	         				dialog.initStyle(StageStyle.UNDECORATED);

	         				dialog.setAlwaysOnTop(true);
	         				System.out.println("IN SERVICE6666666666");
//	         				dialog.show();
	         				System.out.println("IN SERVICE2");
	         				dialog.showAndWait();
	     				}
	     				catch(Exception e) {e.printStackTrace();}
	                 }
	             });
				

				while (true) 
				{
					try {
						if(isCancelled()) {
							if(dialog != null)dialog.close();
							break;
						}					
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				System.out.println("IN SERVICE3");			
				return null;
			}
		};
	}
}
