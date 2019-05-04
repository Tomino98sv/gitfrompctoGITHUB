package account;
import createAccount.CreateAccount;
import createClient.CreateClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import persons.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Globals;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Account implements Initializable {
    public static Employee currentEmployee;

    //Client variables
    public Label fnameShow;
    public Label lnameShow;
    public Label positShow;
    public Button logOut;
    public ComboBox<String> comboBox;

    public Label clientfname;
    public Label clientlname;
    public Label clientemail;

    public static ObservableList<Client> listClients;
    public static Client currentClient;
    private int selectClientId;
    private boolean noClient = false;
    //Client variables

    //Account variables
    private boolean noAcc=true;
    public Label accNumLab;
    public Label amountLab;
    public ComboBox<String> accComboBox;
    public Label sccId;
    public Button buttonCreateAcc;
    public Button buttonAddAcc;
        //vklad a vyber z uctu
    public TextField withdrawInput;
    public TextField depotInput;
    public Button buttonDraw;
    public Button buttonDepot;
    public Label labelDraw;
    public Label labelDepot;
         //vklad a vyber z uctu
    private boolean depFalse=false;
    private boolean drawFalse=false;
    public static ClientAccount accountAcc;
    private int selectedAccountId;
    //Account variables

    //Card variables
    public Button buttonFirstAddCard;
    public VBox vBoxCardInfo;
    public ComboBox comboBoxCards;
    public Button buttonAddCard;
    public Label pinLabel;
    public Label dateLabel;
    public Label activeLabel;
    public Label accNumLabel;
    public static Card currentCard;
    private int selectedCardId;
    public CheckBox blockCard;
    public ComboBox comboBoxAccCards;
    //Card variables

    //Internet Banking variables
    public Label labelLoginName;
    public CheckBox blockIB;
    public static LoginClient currentLoginClient;
    //Internet Banking variables

    //Transaction variables
    public TextField targetAccount;
    public TextField amountToSend;
    public Label labelIdEmp;
    public Label labelFname;
    public Label labelLname;
    public Label labelPos;
    public Label successfulProc;

    public boolean eraseTargetAcc=false;
    public boolean eraseSendAmount=false;

    public Label labelClF;
    public Label labelClL;
    public Label labelAccLast;

    public Label transDate;
    public Label transAmountLast;
    //Transaction variables

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
        if (listClients.isEmpty()){
            noClient(true);
        }else{
            noClient(false);
            currentClient = listClients.get(0);
        }
        fnameShow.setText(currentEmployee.getFname());
        lnameShow.setText(currentEmployee.getLname());
        positShow.setText(currentEmployee.getNameposit());
        updateCurrentClientAndAccountAndCard();

    }

    public void dropdownMenu(){
        comboBox.getItems().clear();
        for (Client client:listClients) {
            comboBox.getItems().add(client.getFname()+" "+client.getLname());
        }
        comboBox.getSelectionModel().select(0);
        comboChange(new ActionEvent());
    }



    public void comboChange(ActionEvent event){
            selectClientId = comboBox.getSelectionModel().getSelectedIndex();
            if (selectClientId<0){
                selectedCardId++;
            }
            currentClient = listClients.get(selectClientId);
        clientfname.setText(currentClient.getFname());
        clientlname.setText(currentClient.getLname());
        clientemail.setText(currentClient.getEmail());

        if (!currentClient.getListAccount().isEmpty()){
            accComboBox.getItems().clear();
            comboBoxAccCards.getItems().clear();
            noAcc=false;
            for (ClientAccount cacc: currentClient.getListAccount()) {
                accComboBox.getItems().add(cacc.getAccNum());
                comboBoxAccCards.getItems().add(cacc.getAccNum());
            }
            accComboBox.getSelectionModel().select(0);
            comboBoxAccCards.getSelectionModel().select(0);

            accComboBoxAction(new ActionEvent());
            noAccount(false);


        }else{
            noAccount(true);
            noCard(true);
        }
        currentLoginClient=Globals.db.getLoginClient(currentClient.getId());
        labelLoginName.setText(currentLoginClient.getLogin());
        if (Globals.db.isIBblocked(currentLoginClient.getId())){
            blockIB.setSelected(true);
        }else {
            blockIB.setSelected(false);
        }

    }



    public void accComboBoxAction(ActionEvent event) {
        selectedAccountId = accComboBox.getSelectionModel().getSelectedIndex();
        if (selectedAccountId<0){
            selectedAccountId++;
        }

        comboBoxAccCards.getSelectionModel().select(selectedAccountId);

        accountAcc = currentClient.getListAccount().get(selectedAccountId);
        if (!accountAcc.getListOfCards().isEmpty()){
            comboBoxCards.getItems().clear();
            noCard(false);
            for (Card currCard:accountAcc.getListOfCards()) {
                comboBoxCards.getItems().add(currCard.getId());
            }
            comboBoxCards.getSelectionModel().select(0);
            cardsComboBoxAction(new ActionEvent());
        }else {
            noCard(true);
        }

            sccId.setText(String.valueOf(accountAcc.getIdAcc()));
            accNumLab.setText(accountAcc.getAccNum());
            amountLab.setText(String.valueOf(accountAcc.getAmount()));

    }

    public void confirmDepot(ActionEvent event) {   //depot=vklad
        try {
//            updateCurrentClientAndAccountAndCard();
            double depotNumDouble=Double.parseDouble(depotInput.getText());
            depotNumDouble = Math.round(depotNumDouble*100.0)/100.0;
            boolean passed = Globals.db.changeAmount(depotNumDouble,accountAcc.getIdAcc());
            updateDATA();
            amountLab.setText(String.valueOf(accountAcc.getAmount()));
            depotInput.setText("");
            if (passed){
                depFalse=true;
                depotInput.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
                depotInput.setText("successfully done");
            }else{
                depFalse=true;
                depotInput.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
                depotInput.setText("Can't pass this");
            }
        }catch (NumberFormatException e){
            depFalse=true;
            depotInput.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            depotInput.setText("Wrong valid number");
            e.printStackTrace();
        }
    }

    public void confirmWithdraw(ActionEvent event) {  //withdraw=vyber
        try {
//            updateCurrentClientAndAccountAndCard();
            double withdrawNumDouble=Double.parseDouble(withdrawInput.getText());
            withdrawNumDouble = Math.round(withdrawNumDouble*100.0)/100.0;
            boolean passed = Globals.db.changeAmount(withdrawNumDouble*-1,accountAcc.getIdAcc());
            updateDATA();
            amountLab.setText(String.valueOf(accountAcc.getAmount()));
            withdrawInput.setText("");
            if (passed){
                drawFalse=true;
                withdrawInput.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
                withdrawInput.setText("successfully done");
            }else{
                drawFalse=true;
                withdrawInput.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
                withdrawInput.setText("Can't pass this");
            }
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

    public void updateCurrentClientAndAccountAndCard(){
        listClients = FXCollections
                .observableArrayList(
                        Globals.db.getAllClients());
        if (!noClient){
            dropdownMenu();
//        selectClientId=comboBox.getSelectionModel().getSelectedIndex();
//        currentClient=listClients.get(selectClientId);
//
//        selectedAccountId=accComboBox.getSelectionModel().getSelectedIndex();
//        accountAcc=currentClient.getListAccount().get(selectedAccountId);
            clientfname.setText(currentClient.getFname());
            clientlname.setText(currentClient.getLname());
            clientemail.setText(currentClient.getEmail());
            if (!noAcc){
                sccId.setText(String.valueOf(accountAcc.getIdAcc()));
                accNumLab.setText(accountAcc.getAccNum());
                amountLab.setText(String.valueOf(accountAcc.getAmount()));
            }else{
                noAccount(true);
            }

        }
    }

    public void updateDATA(){
        listClients = FXCollections
                .observableArrayList(
                        Globals.db.getAllClients());

        selectClientId=comboBox.getSelectionModel().getSelectedIndex();
        currentClient=listClients.get(selectClientId);

        selectedAccountId=accComboBox.getSelectionModel().getSelectedIndex();
        accountAcc=currentClient.getListAccount().get(selectedAccountId);


    }


    public void addClient(ActionEvent event) {
        closeScene(event);
        Parent accountView=loadFXMLoader("../createClient/createClient.fxml");
        newStage(accountView);

    }

    private void noAccount(boolean bool){
        if (noClient){
            buttonCreateAcc.setDisable(bool);
            buttonCreateAcc.setVisible(bool);
        }else{
            buttonCreateAcc.setDisable(!bool);
            buttonCreateAcc.setVisible(bool);
        }
            accComboBox.setDisable(bool);
            accComboBox.setVisible(!bool);

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

            comboBoxAccCards.setVisible(!bool);
            comboBoxAccCards.setDisable(bool);

            if (bool){
                sccId.setText("NONE Account Id");
                accNumLab.setText("NONE Account Number");
                amountLab.setText("NONE Account Amount");
                noAcc=true;
            }
    }

    public void noCard(boolean bool){
        if (!noAcc){
            buttonFirstAddCard.setVisible(bool);
            buttonFirstAddCard.setDisable(!bool);
        }else{
            buttonFirstAddCard.setVisible(bool);
            buttonFirstAddCard.setDisable(bool);
        }

            vBoxCardInfo.setVisible(!bool);
            vBoxCardInfo.setDisable(bool);

            comboBoxCards.setVisible(!bool);
            comboBoxCards.setDisable(bool);

            buttonAddCard.setVisible(!bool);
            buttonAddCard.setDisable(bool);

            blockCard.setVisible(!bool);
            blockCard.setDisable(bool);
    }

    public void noClient(boolean bool){
        noClient = bool;
        comboBox.getItems().clear();
        noAccount(true);
        noCard(true);
    }

    public void addAccount(ActionEvent event) throws IOException {
        closeScene(event);
        Parent createAcc = loadFXMLoader("../createAccount/confirmAcc.fxml");
        newStage(createAcc);
    }

    //card method
    public void cardsComboBoxAction(ActionEvent event) {
        selectedCardId = comboBoxCards.getSelectionModel().getSelectedIndex();
        if (selectedCardId<0){
            selectedCardId++;
        }
        currentCard = accountAcc.getListOfCards().get(selectedCardId);
        pinLabel.setText(currentCard.getPin());
        dateLabel.setText(currentCard.getExpireM()+". "+currentCard.getExpireY());
        activeLabel.setText(String.valueOf(currentCard.isActive()));
        accNumLabel.setText(accountAcc.getAccNum());
        if (currentCard.isActive()){
            blockCard.setSelected(false);
        }else {
            blockCard.setSelected(true);
        }
    }

    public void buttonAddCardAction(ActionEvent event) {
        closeScene(event);
        Parent returnAcc = loadFXMLoader("../createCard/ConfirmCard.fxml");
        newStage(returnAcc);

    }

    public void changePINAction(ActionEvent event) {
        closeScene(event);
        Parent createAcc = loadFXMLoader("../createCard/changePIN.fxml");
        newStage(createAcc);
    }

    public void resetPassword(ActionEvent event) {
        closeScene(event);
        Parent createAcc = loadFXMLoader("../createClient/resetPassword.fxml");
        newStage(createAcc);
    }

    public void blockingIB(ActionEvent event) {
        if (Globals.db.isIBblocked(currentLoginClient.getId())){
            Globals.db.unblockInternetBanking(currentLoginClient.getId());
        }else {
            Globals.db.blockInternetBanking(currentLoginClient.getId());
        }
    }

    public void blockCardEvent(ActionEvent event) {
        if (currentCard.isActive()){
            Globals.db.blockingCard(currentCard.getId(),false);
            updateCard();
            activeLabel.setText(String.valueOf(currentCard.isActive()));
        }else{
            Globals.db.blockingCard(currentCard.getId(),true);
            updateCard();
            activeLabel.setText(String.valueOf(currentCard.isActive()));
        }
    }

    public void updateCard(){
        listClients = FXCollections
                .observableArrayList(
                        Globals.db.getAllClients());
        currentClient = listClients.get(selectClientId);
        accountAcc = currentClient.getListAccount().get(selectedAccountId);
        currentCard = accountAcc.getListOfCards().get(selectedCardId);
    }

    public void comboBoxAccOnCards(ActionEvent event) {
        //toto je comboBox v card zalozke
        selectedAccountId = comboBoxAccCards.getSelectionModel().getSelectedIndex();
        if (selectedAccountId<0){
            selectedAccountId++;
        }

        accountAcc = currentClient.getListAccount().get(selectedAccountId);
        if (!accountAcc.getListOfCards().isEmpty()){
            comboBoxCards.getItems().clear();
            noCard(false);
            for (Card currCard:accountAcc.getListOfCards()) {
                comboBoxCards.getItems().add(currCard.getId());
            }
            comboBoxCards.getSelectionModel().select(0);
            cardsComboBoxAction(new ActionEvent());
        }else {
            noCard(true);
        }
    }

    public void sendTrans(ActionEvent event) {
        String trgAcc=targetAccount.getText();
        Double amount=0.0;
        if (validationSendAmount() && validationTargetAcc(trgAcc)){
            amount=Double.parseDouble(amountToSend.getText());
            amount=Math.round(amount*100.0)/100.0;
            successfulProc.setVisible(true);
            System.out.println("AMOUNT IS: "+amount+" TO "+trgAcc);
        }
    }

    public boolean validationTargetAcc(String accnum){
        if (Globals.db.isExistingAccount(accnum)){
            return true;
        }else{
            eraseTargetAcc = true;
            targetAccount.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            targetAccount.setText("Non existing accnumber");
            return false;
        }
    }

    public boolean validationSendAmount(){
        Double amount=0.0;
        try {
            amount=Double.parseDouble(amountToSend.getText());
        }catch(NumberFormatException e) {
            eraseSendAmount = true;
            amountToSend.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            amountToSend.setText("Wrong valid number");
            return false;
        }
        return true;
    }

    public void eraseTarAcc(MouseEvent mouseEvent) {
        if(eraseTargetAcc){
            targetAccount.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
            targetAccount.setText("");
            eraseTargetAcc=false;
        }
        successfulProc.setVisible(false);
    }

    public void eraseSendAmount(MouseEvent mouseEvent) {
        if (eraseSendAmount){
            amountToSend.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
            amountToSend.setText("");
            eraseSendAmount=false;
        }
        successfulProc.setVisible(false);
    }
    //card method
}
