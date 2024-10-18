package com.github.stilvergp.controller;

import com.github.stilvergp.model.entity.User;
import com.github.stilvergp.model.manager.UserManager;
import com.github.stilvergp.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormSignInController extends Controller implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField email;


    private LoginController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (LoginController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addUser(Event event) {
        User userExists = UserManager.findById(username.getText());
        if (userExists == null) {
            if (password.getText().equals(confirmPassword.getText())) {
                User user = new User(name.getText(), username.getText(),
                        password.getText(), email.getText());
                this.controller.saveUser(user);
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                Alerts.showErrorAlert("Error al crear el usuario", "Las contraseñas no coinciden");
            }
        } else {
            Alerts.showErrorAlert("Error al crear el usuario", "El usuario ya existe");
        }
    }
}
