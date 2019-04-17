package account;
import createAccount.CreateAccount;
import createClient.CreateClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import persons.Client;
import persons.ClientAccount;
import persons.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Globals;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Account implements Initializable {

    public Label fnameShow;
    public Label lnameShow;
    public Label positShow;
    public Button logOut;
    public ComboBox<String> comboBox;

    public Label clientfname;
    public Label clientlname;
    public Label clientemail;

    public Label accNumLab;
    public Label amountLab;
    public ComboBox<String> accComboBox;
    public Label sccId;

    public TextField withdrawInput;
    public TextField depotInput;
    public Button buttonDraw;
    public Button buttonDepot;
    public Label labelDraw;
    public Label labelDepot;

    public Button buttonCreateAcc;
    public Button buttonAddAcc;

    public static ObservableList<Client> listClients;
    public static Client currentClient;
    private ComboBox comboBoxCards;
    private Button buttonAddCard;
    private ClientAccount accountAcc;
    private boolean depFalse=false;
    private boolean drawFalse=false;
    private int selectedAccountId;
    private int selectClientId;

    public void showDataMethod(Employee employee) {
        fnameShow.setText(employee.getFname());
        lnameShow.setText(employee.getLname());
        positShow.setText(employee.getNameposit());
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

    public void logOutMethod(ActionEvent actionEvent) {
        closeScene(actionEvent);
        Parent accountView=loadFXMLoader("../controller/sample.fxml");
        newStage(accountView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listClients = FXCollections
                .observableArrayList(
                        Globals.db.getAllClients());
        currentClient = listClients.get(0);
        buttonCreateAcc.setVisible(false);
        buttonCreateAcc.setDisable(true);
        buttonAddAcc.setVisible(true);
        buttonAddAcc.setDisable(false);
        dropdownMenu();
        clientfname.setText(currentClient.getFname());
        clientlname.setText(currentClient.getLname());
        clientemail.setText(currentClient.getEmail());
        updateCurrentClientAndAccount();

    }

    public void dropdownMenu(){
        for (Client client:listClients) {
            comboBox.getItems().add(client.getFname()+" "+client.getLname());
        }
        comboBox.getSelectionModel().select(0);
        comboChange(new ActionEvent());
    }



    public void comboChange(ActionEvent event){
            selectClientId = comboBox.getSelectionModel().getSelectedIndex();
            accComboBox.getItems().clear();
            currentClient = listClients.get(selectClientId);
        System.out.println(currentClient.getFname()+" "+ currentClient.getLname());
        clientfname.setText(currentClient.getFname());
        clientlname.setText(currentClient.getLname());
        clientemail.setText(currentClient.getEmail());

        if (!currentClient.getListAccount().isEmpty()){
            for (ClientAccount cacc: currentClient.getListAccount()) {
                accComboBox.getItems().add(cacc.getAccNum());
            }
            accComboBox.getSelectionModel().select(0);
            noAccount(false);
        }else{
            noAccount(true);
        }
    }



    public void accComboBoxAction(ActionEvent event) {
        selectedAccountId = accComboBox.getSelectionModel().getSelectedIndex();
        if (selectedAccountId<0){
            selectedAccountId++;
        }

        accountAcc = currentClient.getListAccount().get(selectedAccountId);
            sccId.setText(String.valueOf(accountAcc.getIdAcc()));
            accNumLab.setText(accountAcc.getAccNum());
            amountLab.setText(String.valueOf(accountAcc.getAmount()));

    }

    public void confirmDepot(ActionEvent event) {   //depot=vklad
        try {
            updateCurrentClientAndAccount();
            double depotNumDouble=Double.parseDouble(depotInput.getText());
            depotNumDouble = Math.round(depotNumDouble*100.0)/100.0;
            Globals.db.changeAmount(depotNumDouble,accountAcc.getIdAcc());
            updateCurrentClientAndAccount();
            amountLab.setText(String.valueOf(accountAcc.getAmount()));
            depotInput.setText("");
        }catch (NumberFormatException e){
            depFalse=true;
            depotInput.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            depotInput.setText("Wrong valid number");
            e.printStackTrace();
        }
    }

    public void confirmWithdraw(ActionEvent event) {  //withdraw=vyber
        try {
            updateCurrentClientAndAccount();
            double withdrawNumDouble=Double.parseDouble(withdrawInput.getText());
            withdrawNumDouble = Math.round(withdrawNumDouble*100.0)/100.0;
            Globals.db.changeAmount(withdrawNumDouble*-1,accountAcc.getIdAcc());
            updateCurrentClientAndAccount();
            amountLab.setText(String.valueOf(accountAcc.getAmount()));
            withdrawInput.setText("");
        }catch (NumberFormatException e){
            drawFalse=true;
            withdrawInput.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            withdrawInput.setText("Wrong valid number");
            e.printStackTrace();
        }
    }

    public void eraseDepText(MouseEvent mouseEvent) {
        if (depFalse){
            depotInput.setText("");
            depotInput.setStyle("-fx-text-fill: black; -fx-font-size: 22px;");
            depFalse=false;
        }
    }

    public void eraseDrawText(MouseEvent mouseEvent) {
        if (drawFalse){
            withdrawInput.setText("");
            withdrawInput.setStyle("-fx-text-fill: black; -fx-font-size: 22px;");
            drawFalse=false;
        }
    }

    public void updateCurrentClientAndAccount(){
        listClients = FXCollections
                .observableArrayList(
                        Globals.db.getAllClients());

        selectClientId=comboBox.getSelectionModel().getSelectedIndex();
        currentClient=listClients.get(selectClientId);

        selectedAccountId=accComboBox.getSelectionModel().getSelectedIndex();
        accountAcc=currentClient.getListAccount().get(selectedAccountId);

        sccId.setText(String.valueOf(accountAcc.getIdAcc()));
        accNumLab.setText(accountAcc.getAccNum());
        amountLab.setText(String.valueOf(accountAcc.getAmount()));
    }


    public void addClient(ActionEvent event) {
        closeScene(event);
        Parent accountView=loadFXMLoader("../createClient/createClient.fxml");
        newStage(accountView);

    }

    private void noAccount(boolean bool){
        accComboBox.setDisable(bool);
        accComboBox.setVisible(!bool);

        buttonCreateAcc.setDisable(!bool);
        buttonCreateAcc.setVisible(bool);

        withdrawInput.setDisable(bool);
        withdrawInput.setVisible(!bool);

        depotInput.setDisable(bool);
        depotInput.setVisible(!bool);

        buttonDraw.setDisable(bool);
        buttonDraw.setVisible(!bool);

        buttonDepot.setDisable(bool);
        buttonDepot.setVisible(!bool);

        labelDraw.setDisable(bool);
        labelDraw.setVisible(!bool);

        labelDepot.setDisable(bool);
        labelDepot.setVisible(!bool);

        buttonAddAcc.setVisible(!bool);
        buttonAddAcc.setDisable(bool);
        if (bool){
            sccId.setText("NONE Account Id");
            accNumLab.setText("NONE Account Number");
            amountLab.setText("NONE Account Amount");
        }
    }

    public void addAccount(ActionEvent event) throws IOException {
        closeScene(event);
        Parent createAcc = loadFXMLoader("../createAccount/confirmAcc.fxml");
        newStage(createAcc);
    }

    public void cardsComboBoxAction(ActionEvent event) {
    }

    public void buttonAddCardAction(ActionEvent event) {
        
    }
}
