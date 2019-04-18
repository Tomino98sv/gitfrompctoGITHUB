package createCard;

import account.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import persons.Card;
import sample.Globals;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateCard implements Initializable {
    public Label accLabel;
    public Label pinLabel;
    public Label expireLabel;

    private Card newCard;
    private int expY;
    private int expM;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setExpireDate();
        newCard = Globals.db.insertNewCard(generatePIN(),true,expM,expY, Account.accountAcc.getIdAcc());
        accLabel.setText(String.valueOf(newCard.getId()));
        pinLabel.setText(newCard.getPin());
        expireLabel.setText(String.valueOf(newCard.getExpireM())+". "+String.valueOf(newCard.getExpireY()));
    }

    public void backToAccount(ActionEvent event) {
        closeScene(event);
        Parent returnAcc = loadFXMLoader("../account/Account.fxml");
        newStage(returnAcc);
    }

    public String generatePIN(){
        String newPin="";
        for (int a=0;a<4;a++){
            Random rand = new Random();
            int num = rand.nextInt(10);
            newPin+=String.valueOf(num);
        }
        return newPin;
    }

    public void setExpireDate(){
        LocalDateTime now = LocalDateTime.now();
        expM=now.getMonthValue();
        expY=(now.getYear()+3)%100;

    }
}
