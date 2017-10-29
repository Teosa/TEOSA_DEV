package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ru.teosa.GUI.MainApp;

public class MainInfoController {
	
	@FXML
    private Pane MainInfoPanel;
	@FXML
	private Label HorsesCount;
	@FXML
	private Label FarmsCount;
	@FXML
	private Label UserName;
	
	private MainApp mainApp;
	
	
	
    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public MainInfoController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }
    
    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
//        personTable.setItems(mainApp.getPersonData());
//        MainInfoPanel.
    }
	
	
	
	
}
