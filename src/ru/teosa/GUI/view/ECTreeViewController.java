package ru.teosa.GUI.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ru.teosa.GUI.MainApp;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecordExt;

public class ECTreeViewController extends AbstractController{

	private MainApp mainApp;
	private WebDriver driver;
	
	private static List<RedirectingComboRecordExt> farms = new ArrayList<RedirectingComboRecordExt>();
	
	@FXML
	private TreeView<RedirectingComboRecordExt> tree;
	
	@Override
    protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().setECTreeController(this);
    	mainApp = MainAppHolderSingleton.getInstance().getMainApp();
    	driver = mainApp.getDriver();
    	
    	// ѕереходим на страницу с заводами и заполн€ем массив заводов контроллера
    	getBreedingFarms();
    	// «агружаем дерево из массива заводов
    	loadFarmsTree();
    }
	

	@Override
	public void customizeContent() {
		new Customizer().customizeTree(tree);
	}

	private void getBreedingFarms(){
		
		goToBreedingFarm();

		Sleeper.waitVisibility("//*[@id=\"tab-all-breeding\"]/li");
		List<WebElement> farms = driver.findElements(By.xpath("//*[@id=\"tab-all-breeding\"]/li"));
		ECTreeViewController.farms.clear();
		
		Logger.getLogger("debug").debug("FARMS QTY: " + farms.size());	
		
		for(int i = 0; i < farms.size(); ++i) {
			WebElement farm = driver.findElement(By.xpath("//*[@id=\"tab-all-breeding\"]/li[" + (i+1) + "]"));
			Logger.getLogger("debug").debug("FARM " + i + " : " + farm);
			
			Actions builder = new Actions(driver);
			builder.moveToElement(farm).build().perform();

			RedirectingComboRecordExt record = new RedirectingComboRecordExt();

			String farmURL = "";
			String name = "";
			Logger.getLogger("debug").debug("04");
			if(i != farms.size() -1) {
				Sleeper.turnOffImplicitWaits();
				List<WebElement> subFarms = farm.findElements(By.tagName("li"));
				Logger.getLogger("debug").debug("SUB FARMS QTY: " + subFarms.size());
				Sleeper.turnOnImplicitWaits();
				
				if(subFarms.size() > 0) {
					
					List<RedirectingComboRecordExt> subFarmsRecordsList = new ArrayList<RedirectingComboRecordExt>();
					for(int k = 0; k < subFarms.size(); ++k) {
						RedirectingComboRecordExt subFarmRecord = new RedirectingComboRecordExt();
						String subname = "";
						String subURL = "";
						
						subname = subFarms.get(k).findElement(By.tagName("a")).getText();
						subURL = subFarms.get(k).findElement(By.tagName("a")).getAttribute("href");
						subURL = subURL.replace("#tab-", "elevage=");
						
						subFarmRecord.setName(subname);
						subFarmRecord.setUrl(subURL);
						
						Logger.getLogger("debug").debug("SUB FARM " + k + " : " + subname);
						Logger.getLogger("debug").debug("SUB FARM " + k + " : " + subURL);
						
						subFarmsRecordsList.add(subFarmRecord);
					}
					name = farm.findElement(By.className("groupes")).getText();
					record.setData(subFarmsRecordsList);
				}
				else {
					name = farm.findElement(By.className("tab-action")).getText();
					farmURL = farm.findElement(By.className("tab-action")).getAttribute("href");
					farmURL = farmURL.replace("#tab-", "elevage=");
				}
			}
			else {
				name = farm.findElement(By.tagName("a")).getText();
				farmURL = driver.getCurrentUrl();
			}
			
			record.setName(name);
			record.setUrl(farmURL);
			
			Logger.getLogger("debug").debug("FARM " + i + " : " + name);
			Logger.getLogger("debug").debug("FARM " + i + " : " + farmURL);
			
			ECTreeViewController.farms.add(record);	
		}
		
		Logger.getLogger("debug").debug(ECTreeViewController.getFarms().size());	
	}
	
    private void loadFarmsTree() {
    	//ƒобавл€ем корневую ноду
    	TreeItem<RedirectingComboRecordExt> rootItem0 = new TreeItem<RedirectingComboRecordExt> (new RedirectingComboRecordExt(-1, "Farms", "", null));
    	rootItem0.setExpanded(true);
    	
    	for(int i = 0; i < ECTreeViewController.getFarms().size(); ++i) {
    		RedirectingComboRecordExt record = ECTreeViewController.getFarms().get(i);
            TreeItem<RedirectingComboRecordExt> farm = new TreeItem<RedirectingComboRecordExt> (record);
            
            if(record.getData() != null) {
            	List<RedirectingComboRecordExt> subfarms = (List<RedirectingComboRecordExt>) record.getData();
            	for(int k = 0; k < subfarms.size(); ++k) {
            		farm.getChildren().add(new TreeItem<RedirectingComboRecordExt> (subfarms.get(k)));
            	}
            }
            
            rootItem0.getChildren().add(farm);
    	}
    	
    	tree.setRoot(rootItem0);
    }
      
	private  void goToBreedingFarm(){	
		Logger.getLogger("debug").debug("goToBreedingFarm");
		WebElement breedingFarmMenu = driver.findElement(By.xpath("//*[@id=\"header-menu\"]/div[1]/ul/li[1]"));

		Actions builder = new Actions(driver);
		builder.moveToElement(breedingFarmMenu).build().perform();
	
		driver.findElement(By.xpath("//*[@id=\"header-menu\"]/div[1]/ul/li[1]/ul/li[2]/a")).click();
	}
	
//*****************************************************************************************************************************	
//*****************************************************************************************************************************	
	public static List<RedirectingComboRecordExt> getFarms() {
		return farms;
	}






	
	
	
	
	
}
