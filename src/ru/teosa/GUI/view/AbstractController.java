package ru.teosa.GUI.view;

import javafx.fxml.FXML;

public abstract class AbstractController {

	
	/** Основная инициализация и настройка элементов */
    @FXML protected abstract void initialize();
    
    /** Дополнитеольные настройки для элементов, требующие полной отрисовки формы */
    public abstract void customizeContent();
}
