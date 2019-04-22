package createClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import persons.Client;
import persons.LoginClient;
import sample.Globals;

import java.io.IOException;
import java.util.Random;

public class ClientLogin {
    public Label loginLabel;
    public Label passwordLabel;

    public void initial(Client newClient){
        LoginClient loginClient = Globals.db.insertNewLogin(generateLogin(newClient.getFname(),newClient.getLname()),generatePassword(),newClient.getId());
        loginLabel.setText(loginClient.getLogin());
        passwordLabel.setText(loginClient.getPassword());
    }

    public String generateLogin(String fname,String lname){
        Random rand = new Random();
        String loginName=fname.substring(0,3)+"_"+lname.substring(0,3);
        if (Globals.db.isLoginClientNameNotAlreadyUsed(loginName)){
            return loginName;
        }else{
            while (true){
                int num = rand.nextInt(10);
                loginName=loginName+num;
                if (Globals.db.isLoginClientNameNotAlreadyUsed(loginName)){
                    return loginName;
                }
            }
        }
    }

    public String generatePassword(){
        String loginPass="";
        Random rand = new Random();
        for (int a=0;a<10;a++){
            int choser = rand.nextInt(2+1);
            if (choser==1){
                int alph = rand.nextInt(25+1);
                loginPass+=(char)(alph+'a');
            }else if (choser==2){
                int alph = rand.nextInt(25+1);
                loginPass+=(char)(alph+'A');
            }else {
                int num = rand.nextInt(9);
                loginPass+=(char)(num+'0');
            }

        }
        System.out.println(loginPass);
        return loginPass;
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

    public void backToAcc(ActionEvent event) {
        closeScene(event);
        Parent backAcc = loadFXMLoader("../account/account.fxml");
        newStage(backAcc);
    }
}
