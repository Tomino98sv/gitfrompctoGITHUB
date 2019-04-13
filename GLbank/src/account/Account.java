package account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

    private ObservableList<Client> listClients;
    private Client currentClient;
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

    public void logOutMethod(ActionEvent actionEvent) {
        Node node = (Node)actionEvent.getSource();
        Stage dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../controller/sample.fxml"));
        Parent accountView = null;
        try {
            accountView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(accountView));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listClients = FXCollections
                .observableArrayList(
                        Globals.db.getAllClients());
        currentClient = listClients.get(0);
        dropdownMenu();
        dropdownMenuAcc();
        clientfname.setText(currentClient.getFname());
        clientlname.setText(currentClient.getLname());
        clientemail.setText(currentClient.getEmail());

    }

    public void dropdownMenu(){
        for (Client client:listClients) {
            comboBox.getItems().add(client.getFname()+" "+client.getLname());
        }
        comboBox.getSelectionModel().select(0);
    }

    public void dropdownMenuAcc(){
        for (ClientAccount clAccount: currentClient.getListAccount()) {
            accComboBox.getItems().add(clAccount.getAccNum());
        }
        accComboBox.getSelectionModel().select(0);
        accountAcc = currentClient.getListAccount().get(0);

        sccId.setText(String.valueOf(accountAcc.getIdAcc()));
        accNumLab.setText(accountAcc.getAccNum());
        amountLab.setText(String.valueOf(accountAcc.getAmount()));
    }

    public void comboChange(ActionEvent event){
            selectClientId = comboBox.getSelectionModel().getSelectedIndex();
            accComboBox.getItems().clear();
            currentClient = listClients.get(selectClientId);
        System.out.println(currentClient.getFname()+" "+ currentClient.getLname());
        clientfname.setText(currentClient.getFname());
        clientlname.setText(currentClient.getLname());
        clientemail.setText(currentClient.getEmail());
        for (ClientAccount cacc: currentClient.getListAccount()) {
            accComboBox.getItems().add(cacc.getAccNum());
        }
        accComboBox.getSelectionModel().select(0);
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
            Double depotNumDouble=Double.parseDouble(depotInput.getText());

            Globals.db.changeAmount(Math.round(depotNumDouble*100.0)/100.0,accountAcc.getIdAcc());
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
            Double withdrawNumDouble=Double.parseDouble(withdrawInput.getText());
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
        currentClient=listClients.get(selectClientId);
        accountAcc=currentClient.getListAccount().get(selectedAccountId);
    }
}
