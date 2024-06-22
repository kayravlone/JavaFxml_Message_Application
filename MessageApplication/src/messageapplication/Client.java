//@author Kayra Cansın Gökmen
package messageapplication ;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static javafx.application.Application.launch;
import javafx.scene.control.TextArea;

public class Client extends Application {

    private DataInputStream inputFromServer;
    private DataOutputStream outputToServer;
    private TextArea area;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
    }

    public void connectToServer(String ipAddress, int port, String username) {
    try {
        System.out.println(ipAddress);
        System.out.println(port);

       
        Socket socket = new Socket(ipAddress, port);

        System.out.println("connected");
        inputFromServer = new DataInputStream(socket.getInputStream());
        outputToServer = new DataOutputStream(socket.getOutputStream());

        
        outputToServer.writeUTF(username);
        outputToServer.flush();

        
        new Thread(() -> {
            try {
                while (true) {
                    String message = inputFromServer.readUTF();
                    
                    
                    area.appendText(message + "\n");
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }).start();

    } catch (IOException e) {
        System.err.println(e);
    }
}

    public void sendMessage(String message) {
        try {
            outputToServer.writeUTF(message);
            outputToServer.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public void setTextArea(TextArea area){
        this.area=area;
    }
}
