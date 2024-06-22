//@author Ahmet Emre Çakmak
package messageapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class HostUIController implements Initializable {

    @FXML
    private Label hostLabel;
    @FXML
    private TextField hostTextField;
    @FXML
    private Button hostOk;
    @FXML
    private Button hostCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hostOk.setOnAction(event -> onOkAction((Stage) ((Node) event.getSource()).getScene().getWindow()));
        hostCancel.setOnAction(event -> onCancelAction((Stage) ((Node) event.getSource()).getScene().getWindow()));
    }

    public void onOkAction(Stage stage) {
        String hostText = hostTextField.getText().trim(); 

        if (hostText.isEmpty()) {
            showAlert("Error", "You must enter IP Adress of Host!");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("PortUI.fxml"));
                Parent root = loader.load();
                PortUIController portc = loader.getController();
                System.out.println(hostText);
                portc.sendData(hostText);
                Scene scene = new Scene(root);
                stage.setMinHeight(291);
                stage.setMinWidth(390);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(HostUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void onCancelAction(Stage stage) {
        System.exit(1);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
