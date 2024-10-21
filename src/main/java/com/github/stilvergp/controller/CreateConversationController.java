package com.github.stilvergp.controller;

import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.entity.Conversation;
import com.github.stilvergp.model.entity.User;
import com.github.stilvergp.model.manager.ConversationManager;
import com.github.stilvergp.model.manager.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateConversationController extends Controller implements Initializable {

    @FXML
    private ListView<User> userList;

    private ObservableList<User> users;

    private MainController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MainController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    public void createConversation(Event event) {
        String selectedUser = userList.getSelectionModel().getSelectedItem().getUsername();
        if (selectedUser != null) {
            Conversation newConversation = new Conversation(Session.getInstance().getLoggedInUser().getUsername(), selectedUser);
            ConversationManager.saveConversation(newConversation);
            controller.reloadConversationsFromXml();
            controller.selectConversationWithUser(selectedUser);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> users = getUsersExceptLoggedIn();
        this.users = FXCollections.observableList(users);
        this.userList.setItems(this.users);
        this.userList.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getUsername());
                }
            }
        });
    }

    private List<User> getUsersExceptLoggedIn() {
        List<User> users = UserManager.findAll();
        users.removeIf(user -> user.getUsername().equals(Session.getInstance().getLoggedInUser().getUsername()));
        return users;
    }
}
