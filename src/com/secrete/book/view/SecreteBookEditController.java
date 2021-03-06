package com.secrete.book.view;

import com.secrete.book.model.SecreteBook;
import com.secrete.book.util.DESUtil;
import com.secrete.book.util.PasswordGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * SecreteBookEditController
 *
 * @Author Alex_Bao
 * @create 2018-04-15
 * create by IntelliJ IDEA
 */
public class SecreteBookEditController {

    /**
     * 网站名称
     */
    @FXML
    private TextField siteNameFiled;
    /**
     * 网站地址
     */
    @FXML
    private TextField siteAddressFiled;
    /**
     * 网站用户名
     */
    @FXML
    private TextField userNameFiled;
    /**
     * 网站密码
     */
    @FXML
    private TextField passwordFiled;
    /**
     * secreteBook
     */
    private SecreteBook secreteBook;
    /**
     * dialog
     */
    private Stage dialogStage;

    /**
     * is clicked ok.
     */
    private boolean isOkClicked = false;

    public TextField getSiteNameFiled() {
        return siteNameFiled;
    }

    public void setSiteNameFiled(TextField siteNameFiled) {
        this.siteNameFiled = siteNameFiled;
    }

    public TextField getSiteAddressFiled() {
        return siteAddressFiled;
    }

    public void setSiteAddressFiled(TextField siteAddressFiled) {
        this.siteAddressFiled = siteAddressFiled;
    }

    public TextField getUserNameFiled() {
        return userNameFiled;
    }

    public void setUserNameFiled(TextField userNameFiled) {
        this.userNameFiled = userNameFiled;
    }

    public TextField getPasswordFiled() {
        return passwordFiled;
    }

    public void setPasswordFiled(TextField passwordFiled) {
        this.passwordFiled = passwordFiled;
    }

    /**
     * 做联动效果.
     *
     * @param secreteBook
     */
    public void setSecreteBook(SecreteBook secreteBook) {
        this.secreteBook = secreteBook;
        this.siteNameFiled.setText(secreteBook.getSiteName());
        this.siteAddressFiled.setText(secreteBook.getSiteAddress());
        this.userNameFiled.setText(secreteBook.getUserName());
        this.passwordFiled.setText(secreteBook.getPassword());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize(){
        //initialize
    }

    /**
     * 如果用户点击了Ok,则返回true , 否则为 false
     *
     * @return
     */
    public boolean isOkClicked(){
        return this.isOkClicked;
    }

    /**
     * handler ok button
     */
    @FXML
    private void handlerOK(){
        //校验
        if(isInputValid()){
            secreteBook.setUserName(userNameFiled.getText());
            secreteBook.setPassword(DESUtil.getEncryptString(passwordFiled.getText()));
            secreteBook.setSiteName(siteNameFiled.getText());
            secreteBook.setSiteAddress(siteAddressFiled.getText());
            this.isOkClicked = true;
            dialogStage.close();
        }
    }

    /**
     * validate parameters
     *
     * @return
     */
    public boolean isInputValid() {
        //TODO 增加提示框
        if(this.userNameFiled.getText() == null || this.userNameFiled.getText().trim().length() == 0){
            return false;
        }
        if(this.passwordFiled.getText() == null || this.passwordFiled.getText().trim().length() == 0){
            return false;
        }
        return true;
    }

    /**
     * handler cancel button
     */
    @FXML
    private void handlerCancel(){
        dialogStage.close();
    }

    /**
     * handler generate password button
     */
    @FXML
    private void generatePassword(){
        //generate password.
        String password = PasswordGenerator.generateComplexPassword();
        //show in field.
        passwordFiled.setText(password);
    }


}
