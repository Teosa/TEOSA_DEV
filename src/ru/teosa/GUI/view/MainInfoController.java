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
     * �����������.
     * ����������� ���������� ������ ������ initialize().
     */
    public MainInfoController() {
    }

    /**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
        // ������������� ������� ��������� � ����� ���������.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }
    
    /**
     * ���������� ������� �����������, ������� ��� �� ���� ������.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // ���������� � ������� ������ �� ������������ ������
//        personTable.setItems(mainApp.getPersonData());
//        MainInfoPanel.
    }
	
	
	
	
}
