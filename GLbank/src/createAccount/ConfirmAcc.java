package createAccount;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmAcc {

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

    public void keepCreating(ActionEvent event) {
        closeScene(event);
        Parent createAcc = loadFXMLoader("../createAccount/createAccount.fxml");
        newStage(createAcc);
    }

    public void returnToAcc(ActionEvent event) {
        closeScene(event);
        Parent returnAcc = loadFXMLoader("../account/Account.fxml");
        newStage(returnAcc);
    }
}
