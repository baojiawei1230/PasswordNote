package com.secrete.book.view;

import com.secrete.book.model.UserInfo;
import com.secrete.book.util.DESUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.MainApplication;
import javafx.stage.Stage;

/**
 * CreateAdminController
 *
 * @Author Alex_Bao
 * @create 2018-05-06
 * create by IntelliJ IDEA
 */
public class CreateAdminController {

    /**
     * userName field
     */
    @FXML
    private TextField userNameField;
    /**
     * password field
     */
    @FXML
    private PasswordField passwordField;
    /**
     * confirm password field
     */
    @FXML
    private PasswordField confirmPasswordField;
    /**
     * mainApplication
     */
    private MainApplication mainApplication;
    /**
     * warn message
     */
    @FXML
    private Label warnMessage;
    /**
     * is clicked ok
     */
    private boolean isOkClicked = false;
    /**
     * dialog stage
     */
    private Stage dialogStage;

    /**
     * UserInfo
     */
    private UserInfo userInfo;

    @FXML
    private void handlerOK(){
        //validation
        if(checkConfirmPassword()){
            if(userInfo == null){
                userInfo = new UserInfo();
            }
            //set info.
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(userNameField.getText());
            userInfo.setPassword(DESUtil.getEncryptString(passwordField.getText()));
            //create user
            mainApplication.createUserNameAndPasswordFile(null,userInfo);
            this.isOkClicked = true;
            dialogStage.close();
        }
    }

    /**
     * check confirm password.
     *
     * @return
     */
    private boolean checkConfirmPassword() {
        if(userNameField.getText() == null || userNameField.getText().trim() == ""){
            this.warnMessage.setText("用户名不能为空!");
            return false;
        }
        if(passwordField.getText() == null || passwordField.getText().trim() == ""){
            this.warnMessage.setText("密码不能为空!");
            return false;
        }
        if(!passwordField.getText().equals(confirmPasswordField.getText())){
            this.warnMessage.setText("密码不一致!");
            return false;
        }
        return true;
    }

    @FXML
    private void handlerCancel(){
        this.dialogStage.close();
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

    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public void setConfirmPasswordField(PasswordField confirmPasswordField) {
        this.confirmPasswordField = confirmPasswordField;
    }

    public MainApplication getMainApplication() {
        return mainApplication;
    }

    public void setMainApplication(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public Label getWarnMessage() {
        return warnMessage;
    }

    public void setWarnMessage(Label warnMessage) {
        this.warnMessage = warnMessage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
