//@author Kayra Cansın Gökmen
package messageapplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatUIController implements Initializable {

    @FXML
    private TextField chatTextField;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private ListView<String> chatListView;

    int port;
    String host;
    String name;
    private Client client;
   
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chatTextArea.setEditable(false);
        chatTextField.setPromptText("Write here...");
        
    }

    public void sendServer(String host, int port, String name) {
        try {
            chatListView.getItems().add(name);
            client = new Client();
            client.setTextArea(chatTextArea);
            this.client.connectToServer(host, port, name);
        } catch (NumberFormatException e) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    public void onClickEnter(ActionEvent event) {
        client.sendMessage(chatTextField.getText());
        chatTextField.clear();
    }
    
    

}
