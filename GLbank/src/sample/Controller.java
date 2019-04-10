package sample;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import database.Database;
import employee.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Controller {
    public TextField login;
    public PasswordField pass;
    public Label wpassw;
    public Label wlogin;

    Database db = Database.getInstanceDatabase();
    Employee zamestnanec;
    String meno="zly";
    String heslo="zly";


    public void getData(ActionEvent actionEvent) {
        meno = login.getText();
        heslo = pass.getText();
        zamestnanec=db.getEmployee(meno,heslo);
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
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("account.fxml"));
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
