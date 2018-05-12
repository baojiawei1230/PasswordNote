package com.secrete.book.view;

import com.secrete.book.function.UserLoginContext;
import com.secrete.book.model.UserInfo;
import com.secrete.book.model.UserInfoWrapper;
import com.secrete.book.util.DESUtil;
import com.secrete.book.util.ResourceUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.MainApplication;

import java.io.File;

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
        try {
            if (!PASS_NOTE_FILE.exists()) {
                PASS_NOTE_FILE.mkdirs();
                mainApp.showNewAdminDialog();
                this.warnMessage.setText("该用户不存在,请重新输入");
                return false;
            }
            UserInfoWrapper userInfoWrapper = mainApp.loadUserNameAndPasswordFromFile(USER_INFO_FILE);
            if(userInfoWrapper == null || (userInfoWrapper.getUserInfo().getUserName() == null && userInfoWrapper.getUserInfo().getPassword() == null)){
                //TODO 账户为空,没有设置.跳出设置用户密码界面
                this.warnMessage.setText("该用户不存在,请重新输入!");
                this.mainApp.showNewAdminDialog();
                return false;
            }
            UserInfo userInfo = userInfoWrapper.getUserInfo();
            if (validateLoginInfo(userNameField, passwordField, userInfo)) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * validate login info
     *
     * @param userNameField
     * @param passwordField
     * @param userInfo
     * @return
     */
    private boolean validateLoginInfo(TextField userNameField, PasswordField passwordField, UserInfo userInfo) {
        //validate message
        if(userInfo.getUserName().equals(userNameField.getText()) && DESUtil.getDecryptString(userInfo.getPassword()).equals(passwordField.getText())){
            //set into UserLoginContext.
            UserLoginContext.setUserInfo(userInfo);
            return true;
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
