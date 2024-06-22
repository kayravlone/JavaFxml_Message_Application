//@author Kayra Cansın Gökmen
package messageapplication;

import javafx.scene.control.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserNameUIController implements Initializable {

    @FXML
    private Label userNameLabel;
    @FXML
    private TextField userNameTF;
    @FXML
    private Button userNameOk;
    @FXML
    private Button userNameCancel;

    String hostInfo;
    int port;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userNameOk.setOnAction(event -> onAction((Stage) ((Node) event.getSource()).getScene().getWindow()));
        userNameCancel.setOnAction(event -> onCancelAction((Stage) ((Node) event.getSource()).getScene().getWindow()));
    }

    public void onAction(Stage stage) {
        userNameOk.setOnAction((event) -> {
            String inputUserName = userNameTF.getText().trim();

            
            if (inputUserName.isEmpty()) {
            
                showAlert("Error", "Please enter a username.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("chatUI.fxml"));
                Parent root = loader.load();
                ChatUIController chatC = loader.getController();
                chatC.sendServer(hostInfo, port, inputUserName);
                Scene scene = new Scene(root);
                stage.setMinHeight(500);
                stage.setMinWidth(600);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(HostUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void sendData(String host, int port) {
        this.port = port;
        this.hostInfo = host;
    }

    private void onCancelAction(Stage stage) {
        System.exit(1);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
