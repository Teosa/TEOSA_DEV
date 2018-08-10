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
			// ������ ��������
				ProgressBar progressBarr = new ProgressBar();
				progressBarr.setProgress(-1);
				
				//����� ��������
				Label loadingTextLabel = new Label();
				loadingTextLabel.setText(LoadingWindow.getCustomLoadingText() != null ? LoadingWindow.getCustomLoadingText() : Msgs.DEFAULT_LOADING_TEXT);
				loadingTextLabel.setContentDisplay(ContentDisplay.CENTER);
				
				// �������� ������
				AnchorPane pane = new AnchorPane();
				pane.setPrefWidth(200);
				pane.setMinHeight(50);
				
				// ������� ������ ��������
				AnchorPane.setTopAnchor(progressBarr, 20.0);
				AnchorPane.setLeftAnchor(progressBarr, 20.0);
				AnchorPane.setRightAnchor(progressBarr, 20.0);

				// ������� ������ ��������
				AnchorPane.setLeftAnchor(loadingTextLabel, 20.0);
				AnchorPane.setRightAnchor(loadingTextLabel, 20.0);
				
				// ��������� �������� �� ������
				pane.getChildren().add(progressBarr);
				pane.getChildren().add(loadingTextLabel);
				
				// ������ ���� ���� ������
			    Color color = Color.rgb(0, 0, 0, 0.35);
			    BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
			    Background background = new Background(fill);
			    pane.setBackground(background);
						
				// ������� ���������� ����
				//Stage
				Stage dialog = new Stage();
				
				// ������������� � ��������� � ���� ������
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
