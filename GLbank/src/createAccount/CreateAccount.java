package createAccount;

import account.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import persons.Client;
import persons.ClientAccount;
import sample.Globals;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateAccount implements Initializable {
    public Label fnameLabel;
    public Label lnameLabel;
    public Label emailLabel;

    public Label accoundId;
    public Label accNumberLabel;
    public TextField amountLabel;

    private ClientAccount newAccount;
    private boolean wrong=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fnameLabel.setText(Account.currentClient.getFname());
        lnameLabel.setText(Account.currentClient.getLname());
        emailLabel.setText(Account.currentClient.getEmail());
        newAccount = Globals.db.insertNewAccount(generateAccNumber(),0,Account.currentClient.getId());
        accoundId.setText(String.valueOf(newAccount.getIdAcc()));
        accNumberLabel.setText(newAccount.getAccNum());
    }

    private String generateAccNumber() {
        String generatedNum="";
        Random rand = new Random();

        while (true){
            for (int i=0;i<10;i++){
                int num=rand.nextInt(10);
                generatedNum+=String.valueOf(num);
            }
            if(Globals.db.isAccountNumberAlreadyUsed(generatedNum)){
                break;
            }
        }
        return generatedNum;
    }

    public void confirmNewAccount(ActionEvent event) {
        double startingAmount = 0;

        if (amountLabel.getText().isEmpty()){
            System.out.println("Nenasiel som ziadne cislo");
            backToAccount(event);
            return;
        }
        try {
            startingAmount = Double.parseDouble(amountLabel.getText());
            startingAmount = Math.round(startingAmount*100.0)/100.0;
            System.out.println("Nasiel som "+ startingAmount +" cislo");
            Globals.db.changeAmount(startingAmount,newAccount.getIdAcc());
            System.out.println("zapisal som pre accnum "+newAccount.getAccNum()+" "+newAccount.getIdAcc());

            backToAccount(event);
        }catch (NumberFormatException e){
            wrong=true;
            amountLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
            amountLabel.setText("Wrong valid number");
            e.printStackTrace();
        }
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

    public void backToAccount(ActionEvent event){
        closeScene(event);
        Parent account =loadFXMLoader("../account/account.fxml");
        newStage(account);
    }

    public void eraseWrongText(MouseEvent mouseEvent) {
        if (wrong){
            amountLabel.setText("");
            amountLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
            wrong=false;
        }
    }
}
