package createClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Globals;

import java.io.IOException;

public class CreateClient {
    public TextField fNameInput;
    public TextField lNameInput;
    public TextField emailInput;


    public void closeScene(ActionEvent actionEventForClose){
        Node node = (Node)actionEventForClose.getSource();
        Stage dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
    }

    public Parent loadFXMLoader(String path){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path));
        Parent accountView = null;
        try {
            accountView = fxmlLoader.load();
            return accountView;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void newStage(Parent accountView){
        Stage stage = new Stage();
        stage.setScene(new Scene(accountView));
        stage.show();
    }

    public void createNewClient(ActionEvent event) {
        String fname=fNameInput.getText();
        String lname=lNameInput.getText();
        String email=emailInput.getText();
        Globals.db.insertNewClient(fname,lname,email);
        cancel(event);
    }

    public void cancel(ActionEvent event) {
        closeScene(event);
        Parent accountView=loadFXMLoader("../account/account.fxml");
        newStage(accountView);

    }
}
