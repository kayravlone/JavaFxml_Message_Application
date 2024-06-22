//@author Kayra Cansın Gökmen
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PortUIController implements Initializable {

    @FXML
    private TextField PaTextField;
    @FXML
    private Button PaOk;
    @FXML
    private Button PaCancel;

    private int inputPort;
    String hostInfo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PaOk.setOnAction(event -> onAction((Stage) ((Node) event.getSource()).getScene().getWindow()));
        PaCancel.setOnAction(event -> onCancelAction((Stage) ((Node) event.getSource()).getScene().getWindow()));
    }

   public void onAction(Stage stage) {
    try {
        int inputPort = Integer.parseInt(PaTextField.getText().trim());
        this.inputPort = inputPort;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("userNameUI.fxml"));
        Parent root = loader.load();

        UserNameUIController userNameC = loader.getController();
        userNameC.sendData(hostInfo, inputPort);

        Scene scene = new Scene(root);
        stage.setMinHeight(291);
        stage.setMinWidth(390);
        stage.setScene(scene);
        stage.show();
    } catch (NumberFormatException e) {
        e.printStackTrace();  

        
        errorMessage("Invalid Port", "Please enter a valid Port Number!");
    } catch (IOException ex) {
        ex.printStackTrace();  
        Logger.getLogger(HostUIController.class.getName()).log(Level.SEVERE, null, ex);
    }
}



    private void errorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void sendData(String s){
        this.hostInfo=s;
    }
    public int getPort() {
        return inputPort;
    }
    
    private void onCancelAction(Stage stage) {
        System.exit(1);
    }
    
    
}
