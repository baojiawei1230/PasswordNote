package com.screte.book.view;

import com.screte.book.model.SecreteBook;
import com.screte.book.model.SecreteBookWrapper;
import com.screte.book.util.ResourceUtil;
import com.screte.book.util.SecreteBookUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import sample.Main;

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

    private Main mainApp;

    @FXML
    private void initialize(){
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
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        bookTableView.setItems(mainApp.getSecreteBooks());
    }

    /**
     * 显示密码详情
     *
     * @param secreteBook
     */
    private void showSecreteDetails(SecreteBook secreteBook){
        if(secreteBook != null){
            //fill the labels with info from the person object.
            siteNameLabel.setText(secreteBook.getSiteName());
            siteAddressLabel.setText(secreteBook.getSiteAddress());
            userNameLabel.setText(secreteBook.getUserName());
            passwordLabel.setText(secreteBook.getPassword());
        }else{
            //初始化属性
            siteNameLabel.setText("");
            siteAddressLabel.setText("");
            userNameLabel.setText("");
            passwordLabel.setText("");
        }
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
        File file = SecreteBookUtil.loadInformationFromFile(null);
        mainApp.saveSecreteBookDataToFile(file);
    }

    /**
     * 新增密码记录
     */
    @FXML
    private void handlerNewSecreteBook(){
        SecreteBook secreteBook = new SecreteBook();
        boolean okClicked = mainApp.showSecreteEditDialog(secreteBook);
        if(okClicked){
            //TODO 增加前是否需要判断已经存在.
            mainApp.getSecreteBooks().add(secreteBook);
            //File filePath = mainApp.getSecreteBookFilePath();
            try {
                URL url = ResourceUtil.getURL("/Users/Alex_Bao/Documents/GitWorkSpace/ScreteBook/out/production/ScreteBook/com/screte/book/file/password.xml");
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
