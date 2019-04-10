package sample;

import database.Database;
import employee.Employee;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        }
    }
}
