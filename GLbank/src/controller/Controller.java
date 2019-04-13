package controller;

import account.Account;
import database.Database;
import persons.Client;
import persons.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Globals;

import java.io.IOException;
import java.util.List;


public class Controller {
    public TextField login;
    public PasswordField pass;
    public Label wpassw;
    public Label wlogin;


    Employee zamestnanec;
    String meno="zly";
    String heslo="zly";
    Stage dialogStage = new Stage();


    public void getData(ActionEvent actionEvent) {
        meno = login.getText();
        heslo = pass.getText();
        zamestnanec= Globals.db.getEmployee(meno,heslo);
        if(zamestnanec==null){
            wpassw.setVisible(true);
            wlogin.setVisible(true);
        }
        if(zamestnanec!=null){
            wpassw.setVisible(false);
            wlogin.setVisible(false);
            System.out.println(zamestnanec.getEmployeeId()+"\n"+zamestnanec.getFname()+
                    "\n"+zamestnanec.getLname()+"\n"+zamestnanec.getLoginId()+"\n"+
                    zamestnanec.getLogin()+"\n"+zamestnanec.getPassword()+"\n"+
                    zamestnanec.getPositionId()+"\n"+zamestnanec.getNameposit());
            try {

                Node node = (Node)actionEvent.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../account/account.fxml"));
                Parent accountView = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(accountView));

                Account account = fxmlLoader.getController();
                account.showDataMethod(zamestnanec);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
