//@author Ahmet Emre Çakmak
package messageapplication;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server extends Application {

    private ArrayList<HandleAClient> clients = new ArrayList<>();
    private Label serverLabel;
    private int port = 8000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("serverUI.fxml").openStream());

        ServerUIController serverController = loader.getController();
        serverLabel = serverController.getServerLabel();
        serverLabel.setText("Sunucu port " + port + " üzerinde çalışıyor.");

        Scene scene = new Scene(root);
        stage.setMinHeight(500);
        stage.setMinWidth(600);
        stage.setScene(scene);
        stage.show();

        startServer(8000, stage);
    }

    public void startServer(int port, Stage stage) {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Port is working on " + port + " number port");

                while (true) {
                    Socket socket = serverSocket.accept();
                    HandleAClient client = new HandleAClient(socket);
                    clients.add(client);

                    new Thread(client).start();
                }
            } catch (IOException ex) {
                System.err.println(ex);
                handleException(ex);
            } catch (Exception ex) {
                System.err.println(ex);
                handleException(ex);
            }
        }).start();
    }

    private void handleException(Exception ex) {
        if (ex instanceof UnknownHostException) {
            System.err.println("Unknown host: " + ex.getMessage());
        } else {
            System.err.println("An error occurred: " + ex.getMessage());
        }
    }

    class HandleAClient implements Runnable {

        protected Socket socket;
        protected DataInputStream inputFromClient;
        protected DataOutputStream outputToClient;
        protected String username;

        public HandleAClient(Socket socket) {
            try {
                this.socket = socket;
                this.inputFromClient = new DataInputStream(socket.getInputStream());
                this.outputToClient = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        public void run() {
            try {
                username = inputFromClient.readUTF();
                broadcast(username + " sohbete katıldı.");

                while (true) {
                    String message = inputFromClient.readUTF();
                    broadcast(username + ": " + message);
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        public void broadcast(String message) {
            for (HandleAClient client : clients) {
                client.sendMessage(message);
            }
        }

        public void sendMessage(String message) {
            try {
                outputToClient.writeUTF(message);
                outputToClient.flush();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
