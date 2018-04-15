package com.screte.book.view;

import com.screte.book.model.SecreteBook;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import sample.Main;

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
            siteNameLabel.setText("");
            siteAddressLabel.setText("");
            userNameLabel.setText("");
            passwordLabel.setText("");
        }
    }

    /**
     * 删除密码记录
     */
    @FXML
    private void deleteSecreteBook(){
        int index = bookTableView.getSelectionModel().getSelectedIndex();
        bookTableView.getItems().remove(index);
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
        String text = this.passwordLabel.getText();
        Map<DataFormat,Object> contents = new HashMap<>();
        contents.put(DataFormat.PLAIN_TEXT,text);
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        systemClipboard.setContent(contents);
    }
}
