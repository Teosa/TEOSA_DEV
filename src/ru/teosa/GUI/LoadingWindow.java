package ru.teosa.GUI;

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
import ru.teosa.utils.Msgs;

public class LoadingWindow {
	
	private static String customLoadingText;
	private static Stage window;
	
	public static boolean show() 
	{
		try {
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
				Stage dialog = new Stage();
				
				// Конфигурируем и добавляем в окно панель
				dialog.setResizable(false);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.setScene(new Scene(pane));
				dialog.initStyle(StageStyle.UNDECORATED);

				dialog.setAlwaysOnTop(true);

				window = dialog;
				
				dialog.showAndWait();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

    	  	
		return true;
	}
	
	public static boolean show(String loadingText) 
	{
		customLoadingText = loadingText;
		return show();
	}
	
	public static boolean hide() 
	{
		if(window != null) {
			window.close();
			window = null;
		}
		
		return true;
	}

	public static String getCustomLoadingText() {
		return customLoadingText;
	}

	public static Stage getWindow() {
		return window;
	}

	public static void setWindow(Stage window) {
		LoadingWindow.window = window;
	}
}
