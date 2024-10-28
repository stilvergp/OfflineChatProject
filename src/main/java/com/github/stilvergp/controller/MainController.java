package com.github.stilvergp.controller;

import com.github.stilvergp.App;
import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.entity.Conversation;
import com.github.stilvergp.model.entity.Message;
import com.github.stilvergp.model.manager.ConversationManager;
import com.github.stilvergp.view.Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    private ObservableList<Conversation> conversations;

    @FXML
    private ListView<String> chatListView;
    @FXML
    private VBox chatMessagesVBox;
    @FXML
    private TextField messageTextField;
    @FXML
    private TextField searchBox;

    private void searchConversations(String filter) {
        chatListView.getItems().clear();

        String loggedInUsername = Session.getInstance().getLoggedInUser().getUsername();
        conversations.stream()
                .filter(conversation -> {
                    String otherUser = loggedInUsername.equals(conversation.getUser1()) ? conversation.getUser2() : conversation.getUser1();
                    return otherUser.toLowerCase().contains(filter.toLowerCase());
                })
                .forEach(conversation -> {
                    String otherUser = loggedInUsername.equals(conversation.getUser1()) ? conversation.getUser2() : conversation.getUser1();
                    chatListView.getItems().add("Chat con " + otherUser);
                });
    }


    private void loadChatMessages(String chatName) {
        chatMessagesVBox.getChildren().clear();

        String loggedInUsername = Session.getInstance().getLoggedInUser().getUsername();

        Conversation selectedConversation = conversations.stream()
                .filter(convo -> {
                    String otherUser = loggedInUsername.equals(convo.getUser1()) ? convo.getUser2() : convo.getUser1();
                    return ("Chat con " + otherUser).equals(chatName);
                })
                .findFirst().orElse(null);

        if (selectedConversation != null) {
            List<Message> messages = selectedConversation.getMessages();

            for (Message message : messages) {
                HBox messageBox = new HBox();
                VBox messageContainer = new VBox();
                Label messageLabel = new Label(message.getText());
                Label dateLabel = new Label(message.getDate().getHour() + ":" + message.getDate().getMinute());

                if (message.getSender().equals(loggedInUsername)) {
                    messageLabel.setStyle("-fx-background-color: #1C8AFF;-fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 10px;");
                    messageBox.setAlignment(Pos.CENTER_RIGHT);
                    dateLabel.setAlignment(Pos.CENTER_RIGHT);
                } else {
                    messageLabel.setStyle("-fx-background-color: #E8E8EA;-fx-text-fill: black;-fx-padding: 10px; -fx-background-radius: 10px;");
                    messageBox.setAlignment(Pos.CENTER_LEFT);
                    dateLabel.setAlignment(Pos.CENTER_LEFT);
                }
                dateLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: black;");
                messageContainer.getChildren().addAll(messageLabel, dateLabel);
                VBox.setMargin(messageContainer, new Insets(5, 10, 5, 10));
                messageBox.getChildren().add(messageContainer);
                chatMessagesVBox.getChildren().add(messageBox);
            }
        }
    }


    @Override
    public void onOpen(Object input) throws IOException {
        reloadConversationsFromXml();
    }

    public void createConversation() throws IOException {
        App.currentController.openModal(Scenes.CREATECONVERSATION, "Creando conversación...", this, null);
    }

    public void reloadConversationsFromXml() {
        if (chatListView != null) chatListView.getItems().clear();
        List<Conversation> loadedConversations = ConversationManager.loadConversationsForUser(Session.getInstance().getLoggedInUser());
        this.conversations = FXCollections.observableArrayList(loadedConversations);
        String loggedInUsername = Session.getInstance().getLoggedInUser().getUsername();

        for (Conversation conversation : conversations) {
            String otherUsername;
            if (loggedInUsername.equals(conversation.getUser1())) {
                otherUsername = conversation.getUser2();
            } else {
                otherUsername = conversation.getUser1();
            }
            chatListView.getItems().add("Chat con " + otherUsername);
        }
    }

    public void sendMessage() {
        String messageText = messageTextField.getText();
        if (messageText == null || messageText.trim().isEmpty()) {
            return;
        }
        String selectedChat = chatListView.getSelectionModel().getSelectedItem();
        if (selectedChat == null) {
            return;
        }
        String loggedInUsername = Session.getInstance().getLoggedInUser().getUsername();
        Conversation selectedConversation = conversations.stream()
                .filter(conversation -> {
                    String otherUser = loggedInUsername.equals(conversation.getUser1()) ? conversation.getUser2() : conversation.getUser1();
                    return ("Chat con " + otherUser).equals(selectedChat);
                })
                .findFirst()
                .orElse(null);

        if (selectedConversation != null) {
            String receiver = loggedInUsername.equals(selectedConversation.getUser1())
                    ? selectedConversation.getUser2()
                    : selectedConversation.getUser1();
            Message newMessage = new Message(loggedInUsername, receiver, messageText);
            selectedConversation.getMessages().add(newMessage);
            ConversationManager.saveConversation(selectedConversation);
            loadChatMessages(selectedChat);
            messageTextField.clear();
        }
    }

    @Override
    public void onClose(Object output) {

    }

    public void selectConversationWithUser(String username) {
        String chatName = "Chat con " + username;
        chatListView.getSelectionModel().select(chatName);
        loadChatMessages(chatName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatListView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                loadChatMessages(newValue);
            }
        });
        searchBox.textProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                searchConversations(newValue);
            }
        });
    }
}
