package createCard;

import account.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Globals;

import java.io.IOException;

public class ChangePIN {
    public TextField inputOldPin;
    public TextField inputNewPin;
    public TextField inputRepeatedNewPin;
    public Label labelWrongOld;
    public Label labelWrongRepeated;

    private boolean isOldCorrect=false;
    private boolean isNewCorrect=false;

    public void changePin(ActionEvent event) {
        String oldPIN = inputOldPin.getText();
        String newPIN = inputNewPin.getText();
        String newRepatead = inputRepeatedNewPin.getText();

        if (oldPIN.length()==4){
            if (oldPIN.equals(Account.currentCard.getPin())){
                labelWrongOld.setVisible(false);
                isOldCorrect=true;
            }else{
                labelWrongOld.setVisible(true);
                isOldCorrect=false;
            }

        }else {
            labelWrongOld.setVisible(true);
            isOldCorrect=false;
        }

        if ( newRepatead.length()==4 && newPIN.length()==4){
            if (newPIN.equals(newRepatead)){
                try {
                    Integer.parseInt(newPIN);
                    labelWrongRepeated.setVisible(false);
                    isNewCorrect=true;
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    labelWrongRepeated.setVisible(true);
                    isNewCorrect=false;
                    labelWrongRepeated.setText("That input is not valid Number");
                }
            }else {
                labelWrongRepeated.setVisible(true);
                isNewCorrect=false;
                labelWrongRepeated.setText("New PIN and repeated PIN was compared and no match found");
            }
        }else {
            labelWrongRepeated.setVisible(true);
            isNewCorrect=false;
            labelWrongRepeated.setText("Wrong length of inputs must be only 4 digits");
        }

        if (isOldCorrect && isNewCorrect){
            changing(event,Account.currentCard.getId(),newPIN);
        }
    }

    public void changing(ActionEvent event,int idCard, String newPin){
        Globals.db.changePIN(idCard,newPin);
        closeScene(event);
        Parent account = loadFXMLoader("../account/account.fxml");
        newStage(account);
    }

    public void backToAccount(ActionEvent event){
        closeScene(event);
        Parent account = loadFXMLoader("../account/account.fxml");
        newStage(account);
    }

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
}
