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
import ru.teosa.herdSettings.HerdRunSettings;
import ru.teosa.mainapp.pojo.BreedingFarm;
import ru.teosa.utils.Customizer;
import ru.teosa.utils.Sleeper;
import ru.teosa.utils.objects.MainAppHolderSingleton;
import ru.teosa.utils.objects.RedirectingComboRecordExt;

public class FarmsTreeViewController extends AbstractController{

	private MainApp mainApp;
	private WebDriver driver;
	
	private static List<RedirectingComboRecordExt> farms = new ArrayList<RedirectingComboRecordExt>();
	
	@FXML
	private TreeView<RedirectingComboRecordExt> tree;
	
	@Override
    protected void initialize() {
		MainAppHolderSingleton.getInstance().getMainApp().getController().setFarmsTreeController(this);
    	mainApp = MainAppHolderSingleton.getInstance().getMainApp();
    	driver = mainApp.getDriver();
    	
    	if(driver != null) {
        	// Переходим на страницу с заводами и заполняем массив заводов контроллера
        	getBreedingFarms();
        	// Загружаем дерево из массива заводов
        	loadFarmsTree();	
    	}
    }
	

	@Override
	public void customizeContent() {
		new Customizer().customizeTree(tree);
	}

	private void getBreedingFarms(){
		
		goToBreedingFarm();

		Sleeper.waitVisibility("//*[@id=\"tab-all-breeding\"]/li");
		List<WebElement> farms = driver.findElements(By.xpath("//*[@id=\"tab-all-breeding\"]/li"));
		int recordid = 0;
		FarmsTreeViewController.farms.clear();
		
		Logger.getLogger("debug").debug("FARMS QTY: " + farms.size());	
		
		for(int i = 0; i < farms.size(); ++i) {
			WebElement farm = driver.findElement(By.xpath("//*[@id=\"tab-all-breeding\"]/li[" + (i+1) + "]"));
//			Logger.getLogger("debug").debug("FARM " + i + " : " + farm);
			
			Actions builder = new Actions(driver);
			builder.moveToElement(farm).build().perform();

			RedirectingComboRecordExt record = new RedirectingComboRecordExt();

			String farmURL = "";
			String name = "";

			// Если мы не находимся на последней вкладке заводов, то продолжаем заполнение списка
			if(i != farms.size() -1) {
				Sleeper.turnOffImplicitlyWait();
				List<WebElement> subFarms = farm.findElements(By.tagName("li"));
//				Logger.getLogger("debug").debug("SUB FARMS QTY: " + subFarms.size());
				Sleeper.setStandartImplicitlyWait();
				
				// Если у завода есть подзаводы, получаем их список
				if(subFarms.size() > 0) {
					// Создаем список подзаводов
					List<RedirectingComboRecordExt> subFarmsRecordsList = new ArrayList<RedirectingComboRecordExt>();
					
					// Заполняем его
					for(int k = 0; k < subFarms.size(); ++k) {
						RedirectingComboRecordExt subFarmRecord = new RedirectingComboRecordExt();
						String subname = "";
						String subURL = "";
						
						subname = subFarms.get(k).findElement(By.tagName("a")).getText();
						subURL = subFarms.get(k).findElement(By.tagName("a")).getAttribute("href");
						subURL = subURL.replace("#tab-", "elevage=");
						
						subFarmRecord.setId(recordid++);
						subFarmRecord.setName(subname);
						subFarmRecord.setUrl(subURL);
						
						// Создаем и заполняем объект Завод
						BreedingFarm breedingFarm = new BreedingFarm();
						breedingFarm.setName(subname);
						breedingFarm.setURL(subURL);
						breedingFarm.setSettings(new HerdRunSettings());
						
						// Устанавливаем его в качестве доп. объекта в записи
						subFarmRecord.setData(breedingFarm);						
						
						// Добавляем запись в список подзаводов
						subFarmsRecordsList.add(subFarmRecord);
					}
					// Получаем название основного завода
					name = farm.findElement(By.className("groupes")).getText();
					
					// Устанавливаем список подзаводов в качестве доп. объекта в записи основного завода
					record.setData(subFarmsRecordsList);
				}
				// Иначе - получаем информацию о заводе
				else {
					name = farm.findElement(By.className("tab-action")).getText();
					farmURL = farm.findElement(By.className("tab-action")).getAttribute("href");
					farmURL = farmURL.replace("#tab-", "elevage=");
				}
			}
			// Иначе - получаем инфу о последней вкладке
			else {
				name = farm.findElement(By.tagName("a")).getText();
				farmURL = driver.getCurrentUrl();
			}
			
			record.setId(recordid++);
			record.setName(name);
			record.setUrl(farmURL);
			
			// Если доп. объект в записи еще не проинициализирован ( подзаводами ), то заполняем его новым оюъектом Завод
			if(record.getData() == null) {
				BreedingFarm breedingFarm = new BreedingFarm();
				breedingFarm.setName(name);
				breedingFarm.setURL(farmURL);
				breedingFarm.setSettings(new HerdRunSettings());
				record.setData(breedingFarm);
			}

			
//			Logger.getLogger("debug").debug("FARM " + i + " : " + name);
//			Logger.getLogger("debug").debug("FARM " + i + " : " + farmURL);
			
			FarmsTreeViewController.farms.add(record);	
		}
		
//		Logger.getLogger("debug").debug(ECTreeViewController.getFarms().size());	
	}
	
    private void loadFarmsTree() {
    	//Добавляем корневую ноду
    	TreeItem<RedirectingComboRecordExt> rootItem0 = new TreeItem<RedirectingComboRecordExt> (new RedirectingComboRecordExt(-1, "Farms", "", null));
    	rootItem0.setExpanded(true);
    	
    	for(int i = 0; i < FarmsTreeViewController.getFarms().size(); ++i) {
    		RedirectingComboRecordExt record = FarmsTreeViewController.getFarms().get(i);
            TreeItem<RedirectingComboRecordExt> farm = new TreeItem<RedirectingComboRecordExt> (record);
            System.out.println("FARM ID: " + record.getId());
            if(record.getData() != null) {
            	// Так как объектом может быть BreedingFarm, ловим ClassCastException
            	try {
                	List<RedirectingComboRecordExt> subfarms = (List<RedirectingComboRecordExt>) record.getData();
                	for(int k = 0; k < subfarms.size(); ++k) {
                		farm.getChildren().add(new TreeItem<RedirectingComboRecordExt> (subfarms.get(k)));
                	}
            	}
            	catch(ClassCastException e){}
            }
            
            rootItem0.getChildren().add(farm);
    	}
    	
    	tree.setRoot(rootItem0);
    }
      
	private  void goToBreedingFarm(){	
		Logger.getLogger("debug").debug("goToBreedingFarm");
//		Sleeper.waitVisibility("//*[@id=\"header-menu\"]/div[1]/ul/li[1]");
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
	public TreeView<RedirectingComboRecordExt> getTree() {
		return tree;
	}






	
	
	
	
	
}
