package ru.teosa.GUI.view;

import javafx.fxml.FXML;

public abstract class AbstractController {

    @FXML
    protected abstract void initialize();
    public abstract void customizeContent();
}
