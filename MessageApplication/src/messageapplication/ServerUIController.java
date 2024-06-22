//@author Ahmet Emre Çakmak
package messageapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class ServerUIController implements Initializable {

    @FXML
    private Label serverLabel;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    
    public void setServerLabel(Label serverLabel) {
        this.serverLabel = serverLabel;
    }

    
    public Label getServerLabel() {
        return serverLabel;
    }
    
}
