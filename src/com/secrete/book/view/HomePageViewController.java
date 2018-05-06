package com.secrete.book.view;

import com.secrete.book.function.UserLoginContext;
import com.secrete.book.model.UserInfo;
import com.secrete.book.model.UserInfoWrapper;
import com.secrete.book.util.ResourceUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.MainApplication;

import java.io.File;
import java.net.URL;

/**
 * home page view controller
 *
 * @Author Alex_Bao
 * @create 2018-05-03
 * create by IntelliJ IDEA
 */
public class HomePageViewController{

    /**
     * username
     */
    @FXML
    private TextField userNameField;
    /**
     * password
     */
    @FXML
    private PasswordField passwordField;
    /**
     * warn message
     */
    @FXML
    private Label warnMessage;

    /**
     * Main Application
     */
    private MainApplication mainApp;

    /**
     * root path(users' home)
     */
    private static final String ROOT_PATH = System.getProperty("user.home");
    /**
     * pass note path
     */
    private static final String PASS_NOTE_PATH = ROOT_PATH + "/PassNote/file/";
    /**
     * user info path
     */
    private static final String USER_INFO_PATH = PASS_NOTE_PATH + "userInfo.xml";
    /**
     * pass note file
     */
    private static final File PASS_NOTE_FILE = ResourceUtil.getFile(PASS_NOTE_PATH);
    /**
     * user info file
     */
    private static final File USER_INFO_FILE = ResourceUtil.getFile(USER_INFO_PATH);

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public void setUserNameField(TextField userNameField) {
        this.userNameField = userNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Label getWarnMessage() {
        return warnMessage;
    }

    public void setWarnMessage(Label warnMessage) {
        this.warnMessage = warnMessage;
    }

    @FXML
    private void checkUserNameAndPassword(){
        //check is admin.
        boolean isAdmin = checkAdmin(userNameField, passwordField);
        if(!isAdmin){
            this.warnMessage.setText("账号或者密码错误 !");
            return;
        }
        //isAdmin show over view.
        this.mainApp.showSecreteOverView();
    }


    /**
     * check username and password.
     *
     * @param userNameField userNameField
     * @param passwordField passwordField
     *
     */
    private boolean checkAdmin(TextField userNameField, PasswordField passwordField) {
        URL url = null;
        try {
            //url = ResourceUtil.getURL(PASS_NOTE_PATH);
            if (!PASS_NOTE_FILE.exists()) {
                PASS_NOTE_FILE.mkdirs();
                //mainApp.createUserNameAndPasswordFile(infoFile,);
                mainApp.showNewAdminDialog();
                this.warnMessage.setText("该用户不存在,请重新输入");
                return false;
            }
//            if(!file.exists()){
//                //TODO 弹出未设置Admin信息,然后进行设置.
//                //this.warnMessage = new Label("请先设置Admin账户 !");
//                mainApp.createUserNameAndPasswordFile(file);
//                return false;
//            }
            UserInfoWrapper userInfoWrapper = mainApp.loadUserNameAndPasswordFromFile(USER_INFO_FILE);
            //UserLoginSource userLoginSource = new UserLoginSource();
            //userLoginSource.triggerNotifiers();
            if(userInfoWrapper == null || (userInfoWrapper.getUserInfo().getUserName() == null && userInfoWrapper.getUserInfo().getPassword() == null)){
                //TODO 账户为空,没有设置.跳出设置用户密码界面
                this.warnMessage.setText("该用户不存在,请重新输入!");
                this.mainApp.showNewAdminDialog();
                return false;
            }
            UserInfo userInfo = userInfoWrapper.getUserInfo();
            //validate message
            if(userInfo.getUserName().equals(userNameField.getText()) && userInfo.getPassword().equals(passwordField.getText())){
                //set into UserLoginContext.
                UserLoginContext.setUserInfo(userInfo);
                return true;
            }
          //  warnMessage.labelForProperty().addListener(((observable, oldValue, newValue) -> showNewValue(newValue)));
            //this.mainApp.getPrimaryStage().show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void listenPasswordField(KeyEvent event){
        //listen key pressed.
        if(event.getCode() == KeyCode.ENTER){
           checkUserNameAndPassword();
       }
    }


}
