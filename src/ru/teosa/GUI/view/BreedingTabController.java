package ru.teosa.GUI.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ru.teosa.utils.ComboStores;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecordExt;

public class BreedingTabController extends AbstractController{

	
// *** БЛОК ЖЕРЕБЦЫ ***
	// Количество случек
          private ToggleGroup matingQty;
    @FXML private RadioButton matingQty_one;           // 1 
    @FXML private RadioButton matingQty_two;           // 2
    @FXML private RadioButton matingQty_three;         // 3
	
	@FXML private ComboBox<Integer> matingPrice;       // Цена за случку
	
	
// *** БЛОК КОБЫЛЫ ***
	// Крыть
          private ToggleGroup coverBy;
    @FXML private RadioButton coverBy_owner;          // Только моими 
    @FXML private RadioButton coverBy_any;            // Любыми
    @FXML private Label coverByLabel; 
    
    @FXML private ComboBox<Integer> maxCoverPrice;    // Максимальная цена за случку
    
    // Порода жеребца
          private ToggleGroup stallonBreed;
    @FXML private RadioButton stallonBreed_likeMare;  // Как у кобылы
    @FXML private RadioButton stallonBreed_any;       // Любая
    
    // ГП жеребца
          private ToggleGroup stallonGP;
	@FXML private RadioButton stallonGP_likeMare;     // Как у кобылы 
	@FXML private RadioButton stallonGP_any;          // Любое


// *** БЛОК ЖЕРЕБЯТА ***
	@FXML private TextField stallonNames;             // Кличка (жеребцы)
	@FXML private TextField mareNames;                // Кличка (кобылы)
	@FXML private ComboBox foalsAffix;                // Аффикс
	@FXML private ComboBox<RedirectingComboRecordExt> foalsFarm; // Завод
	
	
	@Override
	protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().getProgramWindowController().getHerdRunSettingsController().setBreedingTabController(this);
		
		matingQty = new ToggleGroup();
		coverBy = new ToggleGroup();
		stallonBreed = new ToggleGroup();
		stallonGP = new ToggleGroup();
		
		// Объединяем радиобатоны в группы
		matingQty.getToggles().addAll(matingQty_one, matingQty_two, matingQty_three);
		coverBy.getToggles().addAll(coverBy_owner, coverBy_any);
		stallonBreed.getToggles().addAll(stallonBreed_likeMare, stallonBreed_any);
		stallonGP.getToggles().addAll(stallonGP_likeMare, stallonGP_any);
		
		// Загрузка сторов цен за случки
		matingPrice.getItems().addAll(ComboStores.STALLON_MATING_PRICE);
		maxCoverPrice.getItems().addAll(ComboStores.STALLON_MATING_PRICE);
		
		foalsFarm.getItems().addAll(MainAppHolderSingleton.getInstance().getMainApp().getController().getFarmsTreeController().getFarms());
		
	}

	@Override
	public void customizeContent() {
		// TODO Auto-generated method stub
		
	}

}
