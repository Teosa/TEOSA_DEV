package ru.teosa.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private static Button BUTTON_OK;
    //private Stage dialog;
	
	/*public MsgWindow() 
	{
		System.out.println("---------------------");
		BUTTON_OK = new Button("OK");		
		BUTTON_OK.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override public void handle(ActionEvent e) 
			{
				if( dialog != null ) 
				{
					dialog.close();
					dialog = null;
				}
				
			}
		});
	}*/
	
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
		mainPane.setBottom(button);
		
		// Создаем диалоговое окно
		Stage dialog = new Stage();
		setDialog( dialog );
		
		// Конфигурируем и добавляем в окно панель
		dialog.setTitle("Ошибка");
		dialog.setResizable(false);
		dialog.setMaxHeight(300);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setScene(new Scene(mainPane));
		
		button.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override public void handle(ActionEvent e) 
			{
				if( dialog != null ) 
				{
					dialog.close();
				}
				
			}
		});
		
		dialog.showAndWait();
		
		return true;
	}

	public static String getErrorMsg() {
		return errorMsg;
	}

	public static void setErrorMsg(String errorMsg) 
	{		
		MsgWindow.errorMsg = "\n-------------------------------------\n" + errorMsg;
	}

	public static void setDialog(Stage dialog) {
		dialog = dialog;
	}
}


