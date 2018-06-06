package ru.teosa.GUI.view;

import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.teosa.GUI.MainApp;
import ru.teosa.account.Account;
import ru.teosa.account.Resources;
import ru.teosa.utils.Tools;
import ru.teosa.utils.objects.MainAppHolderSingleton;

public class AccountInfoPanelController extends AbstractController{

	private MainApp mainApp;
	
	@FXML private MenuItem convertMoney;  // ������ �����������
	@FXML private MenuItem affixes;       // ������ �������
	@FXML private MenuItem switchUser;    // ������� ������������
	@FXML private MenuItem exit;          // �����
	
	@FXML private MenuItem addProgram;   // ����� ���������
	@FXML private MenuItem editProgram;  // �������������/������� ���������
	
//	@FXML
	// TODO ���� ������
	
	@FXML private Text breederName;
	@FXML private Text moneyBallance;
	
	private final static String ADD_PROGRAMM_WIN_HEADER  = "����� ���������";
	private final static String EDIT_PROGRAMM_WIN_HEADER = "�������������/������� ���������";
//************************************************************************************************************************************	
//************************************************************************************************************************************	
    @FXML
    protected void initialize() {
    	mainApp = MainAppHolderSingleton.getInstance().getMainApp();
    	
    	breederName.setText(Account.getUser().getUsername());
    	Account.getResources().setMoney(Resources.getMoneyBalFromForm());
    	moneyBallance.setText(Tools.numStringWithSpaces(Account.getResources().getMoney()));
    	
    	// ������������� ������ ����
    	setIcons();
    }
    
	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}
	
    //�������� ������� �����������
    @FXML
    public void convertMoneyHandler() throws Exception{ 	

    	Stage dialog = new Stage();
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/MoneyConvertor.fxml"));
        
        Scene scene = new Scene((VBox) loader.load());
       
        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.setTitle("�����������");
    	dialog.initOwner(mainApp.getPrimaryStage());
    	dialog.initModality(Modality.APPLICATION_MODAL);
    	dialog.getIcons().add(new Image("/icons/convertation.png"));
    	
    	dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				if (MainAppHolderSingleton.getMoneyConverterHandler().isRunning()) 		
				{   
		    		MainAppHolderSingleton.getMoneyConverterHandler().cancel();
		    		MainAppHolderSingleton.getMoneyConverterHandler().reset();
		    	}
			}
		});
    	
    	MainAppHolderSingleton.getMoneyConverterHandler().setConvertorWin(dialog);
    	
    	dialog.showAndWait();
    }
    
    // �������� ������� �������
    @FXML
    public void affixesHandler() throws Exception{
    	Stage dialog = new Stage();
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AffixesWindow.fxml"));
        
        Scene scene = new Scene((BorderPane) loader.load());
       
        dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.setTitle("�������");
    	dialog.initOwner(mainApp.getPrimaryStage());
    	dialog.initModality(Modality.APPLICATION_MODAL);
    	dialog.getIcons().add(new Image("/icons/affixes.png"));
    	
    	dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {		
			@Override
			public void handle(WindowEvent event) {
				// ���������� �������� � ���������?
			}
		});
    	
    	AffixesWindowController.setThisWindow(dialog);
    	dialog.showAndWait();
    }
    
    // �������� ������� �������� ����� ��������� �������
    @FXML
    public void addProgramHandler() throws Exception{ showProgramDialog(ADD_PROGRAMM_WIN_HEADER, 0); }
    
    // �������� ������� �������������� �������� �������
    @FXML
    public void editProgramHandler() throws Exception{ showProgramDialog(EDIT_PROGRAMM_WIN_HEADER, 1); }
    
    
    private void showProgramDialog(String dialogTitle, int mode) throws IOException {
    	Stage dialog = new Stage();
    	
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ProgramWindow.fxml"));

        ProgramWindowController.setMode(mode);
        
        Scene scene = new Scene((BorderPane) loader.load());

        dialog.setScene(scene);
        dialog.setTitle(dialogTitle);
    	dialog.initOwner(mainApp.getPrimaryStage());
    	dialog.initModality(Modality.APPLICATION_MODAL);
   	
    	dialog.showAndWait();
    }
    
    private void setIcons() {
    	// ������ ������ �����������
        ImageView convertICO = new ImageView(new Image("/icons/convertation.png"));
        convertICO.setFitHeight(25);
        convertICO.setFitWidth(25);
        convertMoney.setGraphic(convertICO);
    	
    	// ������ ������ �������
        ImageView affICO = new ImageView(new Image("/icons/affixes.png"));
        affICO.setFitHeight(25);
        affICO.setFitWidth(25);
        affixes.setGraphic(affICO);
        
    	// ������ ����� ������������
        ImageView switchICO = new ImageView(new Image("/icons/switch_user_icon.png"));
        switchICO.setFitHeight(25);
        switchICO.setFitWidth(25);
        switchUser.setGraphic(switchICO);
//        
    	// ������ ������
        ImageView exitICO = new ImageView(new Image("/icons/exit_icon.png"));
        exitICO.setFitHeight(25);
        exitICO.setFitWidth(25);
        exit.setGraphic(exitICO);
        
    }
}
