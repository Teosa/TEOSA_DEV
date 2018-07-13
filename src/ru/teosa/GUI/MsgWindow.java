package ru.teosa.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MsgWindow {

	public static String errorMsg;
	
	public static boolean showErrorWindow(String msg) 
	{
		Button button = new Button("OK");
		button.setTranslateX(190);
		
		Text text = new Text(msg);
		text.setWrappingWidth(360);
		
		// Основная панель
		AnchorPane pane = new AnchorPane();
		pane.setPrefWidth(400);
		
		// Позиция текста
		AnchorPane.setTopAnchor(text, 20.0);
		AnchorPane.setLeftAnchor(text, 20.0);
		AnchorPane.setRightAnchor(text, 20.0);
		AnchorPane.setBottomAnchor(text, 45.0);
		
		// Добавляем текст на панель
		pane.getChildren().add(text);
		
		
		ScrollPane centerPane = new ScrollPane();
		centerPane.setContent(pane);
		
		
		
		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(centerPane);
//		mainPane.getCenter().set
		mainPane.setBottom(button);
		
		// Создаем диалоговое окно
		Stage dialog = new Stage();
		
		// Конфигурируем и добавляем в окно панель
		dialog.setTitle("Ошибка");
		dialog.setResizable(false);
		dialog.setMaxHeight(300);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setScene(new Scene(mainPane));

		dialog.showAndWait();
		
		return true;
	}

	public static String getErrorMsg() {
		return errorMsg;
	}

	public static void setErrorMsg(String errorMsg) {
		
		MsgWindow.errorMsg = "\n-------------------------------------\n" + errorMsg;
	}
	
	
}
