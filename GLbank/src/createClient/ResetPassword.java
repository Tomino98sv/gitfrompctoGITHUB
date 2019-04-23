package createClient;

import account.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.Global;
import persons.LoginClient;
import sample.Globals;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassword implements Initializable {
    public Label loginLabel;
    public Label passwordLabel;

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

    public void backToAcc(ActionEvent event) {
        closeScene(event);
        Parent backAcc = loadFXMLoader("../account/account.fxml");
        newStage(backAcc);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginClient loginClient = Globals.db.getLoginClient(Account.currentClient.getId());
        loginLabel.setText(loginClient.getLogin());
        passwordLabel.setText(loginClient.getPassword());
    }
}
