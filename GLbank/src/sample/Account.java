package sample;
import database.Database;
import persons.Client;
import persons.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class Account {

    public Label fnameShow;
    public Label lnameShow;
    public Label positShow;
    public Button logOut;
    public ChoiceBox<String> choiceBoxId;

    private Employee employee;
    Stage dialogStage = new Stage();

    public void showDataMethod(Employee employee,List<Client> listOfClients) {
        this.employee=employee;
        fnameShow.setText(employee.getFname());
        lnameShow.setText(employee.getLname());
        positShow.setText(employee.getNameposit());
        for (Client client:listOfClients) {
            String name = client.getFname()+" "+client.getLname();
            choiceBoxId.getItems().add(name);
            System.out.println(client.getFname()+" "+client.getLname()+" "+client.getEmail());
        }
    }

    public void logOutMethod(ActionEvent actionEvent) {
        Node node = (Node)actionEvent.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
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
}
