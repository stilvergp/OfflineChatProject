package com.github.stilvergp.utils;

import javafx.scene.control.Alert;

public class Alerts {
    public static void showErrorAlert(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.show();
    }

    public static void showInformationAlert(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.show();
    }
}
