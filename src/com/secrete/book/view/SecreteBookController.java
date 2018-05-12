package com.secrete.book.view;

import com.secrete.book.function.CheckLoginUtil;
import com.secrete.book.model.SecreteBook;
import com.secrete.book.model.SecreteBookWrapper;
import com.secrete.book.util.DESUtil;
import com.secrete.book.util.ResourceUtil;
import com.secrete.book.util.SecreteBookUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import sample.MainApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SecreteBookController {

    /**
     * 密码本集合
     */
    @FXML
    private TableView<SecreteBook> bookTableView;

    /**
     * 用户名列
     */
    @FXML
    private TableColumn<SecreteBook,String> userNameColumn;
    /**
     * 密码列
     */
    @FXML
    private TableColumn<SecreteBook,String> passwordColumn;

    /**
     * 网站名称列
     */
    @FXML
    private TableColumn<SecreteBook,String> siteNameColumn;
    /**
     * 网站地址列
     */
    @FXML
    private TableColumn<SecreteBook,String> siteAddressColumn;

    /**
     * 网站名称标签
     */
    @FXML
    private Label siteNameLabel;
    /**
     * 用户名标签
     */
    @FXML
    private Label userNameLabel;
    /**
     * 密码标签
     */
    @FXML
    private Label passwordLabel;
    /**
     * 网站地址标签
     */
    @FXML
    private Label siteAddressLabel;

    /**
     * Main Application
     */
    private MainApplication mainApp;
    /**
     * ROOT PATH
     */
    private static final String USER_PATH = System.getProperty("user.home");
    /**
     * PASS NOTE PATH
     */
    private static final String PASS_NOTE_PATH = USER_PATH + "/PassNote/file";
    /**
     * FILE_PATH
     */
    private static final String FILE_PATH = PASS_NOTE_PATH + "/password.xml";



    @FXML
    private void initialize(){
        //TODO 是否每次初始化都要确定是否登陆

        siteNameColumn.setCellValueFactory(cellDate -> cellDate.getValue().siteNameProperty());
        siteAddressColumn.setCellValueFactory(cellData -> cellData.getValue().siteAddressProperty());

        //clear secrete book details.
        showSecreteDetails(null);

        // listen for selection changes and show the person details when changed.
        bookTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showSecreteDetails(newValue)));
    }

    /**
     * 设置mainApp
     *
     * @param mainApp
     */
    public void setMainApp(MainApplication mainApp){
        this.mainApp = mainApp;
        //set items
        bookTableView.setItems(mainApp.getSecreteBooks());
    }

    /**
     * 显示密码详情
     *
     * @param secreteBook
     */
    private void showSecreteDetails(SecreteBook secreteBook){
        //check admin is login
        if(CheckLoginUtil.checkIsLogin()){
            if(secreteBook != null){
                //fill the labels with info from the person object.
                siteNameLabel.setText(secreteBook.getSiteName());
                siteAddressLabel.setText(secreteBook.getSiteAddress());
                userNameLabel.setText(secreteBook.getUserName());
                passwordLabel.setText(DESUtil.getDecryptString(secreteBook.getPassword()));
            }else{
                //初始化属性
                siteNameLabel.setText("");
                siteAddressLabel.setText("");
                userNameLabel.setText("");
                passwordLabel.setText("");
            }
            return;
        }
        // is not login.
        this.mainApp.initHomePageView();
    }

    /**
     * 为column设置值
     *
     * @param secreteBooks
     */
    private void setColumnsPropertiesValue(ObservableList<SecreteBook> secreteBooks) {
        if(secreteBooks != null && secreteBooks.size() > 0){
        }
    }

    /**
     * 删除密码记录
     */
    @FXML
    private void deleteSecreteBook(){
        int index = bookTableView.getSelectionModel().getSelectedIndex();
        bookTableView.getItems().remove(index);
        SecreteBookWrapper secreteBookWrapper = new SecreteBookWrapper();
        secreteBookWrapper.setBookList(bookTableView.getItems());
        File file = SecreteBookUtil.loadInformationFromFile(PASS_NOTE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
        File filePath = ResourceUtil.getFile(FILE_PATH);
        mainApp.saveSecreteBookDataToFile(filePath);
    }

    /**
     * 新增密码记录
     */
    @FXML
    private void handlerNewSecreteBook(){
        SecreteBook secreteBook = new SecreteBook();
        boolean okClicked = mainApp.showNewPassNoteDialog(secreteBook);
        if(okClicked){
            //TODO 增加前是否需要判断已经存在.
            mainApp.getSecreteBooks().add(secreteBook);
            //File filePath = mainApp.getSecreteBookFilePath();
            try {
                URL url = ResourceUtil.getURL(FILE_PATH);
                File file = ResourceUtil.getFile(url);
                mainApp.saveSecreteBookDataToFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 编辑密码信息
     *
     */
    @FXML
    private void handlerEditSecreteBook(){
        SecreteBook item = bookTableView.getSelectionModel().getSelectedItem();
        if (item != null) {
            boolean okClicked = mainApp.showSecreteEditDialog(item);
            if(okClicked){
                showSecreteDetails(item);
            }
        }else{
            //TODO 打印错误日志.
            System.out.print("occurred error !!");
        }
    }

    /**
     * 复制密码
     */
    @FXML
    private void copyPassword(){
        String password = this.passwordLabel.getText();
        Map<DataFormat,Object> contents = new HashMap<>();
        contents.put(DataFormat.PLAIN_TEXT,password);
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        systemClipboard.setContent(contents);
    }

    /**
     * 复制用户名
     */
    @FXML
    private void copyUserName(){
        String userName = this.userNameLabel.getText();
        Map<DataFormat,Object> contents = new HashMap<>();
        contents.put(DataFormat.PLAIN_TEXT,userName);
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        systemClipboard.setContent(contents);
    }


}
