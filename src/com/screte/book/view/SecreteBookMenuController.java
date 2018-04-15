package com.screte.book.view;

import com.screte.book.model.SecreteBook;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


/**
 * SecreteBookMenuController
 *
 * @Author Alex_Bao
 * @create 2018-04-15
 * create by IntelliJ IDEA
 */
public class SecreteBookMenuController {

    /**
     * TableView 用于搜索时showDetail(secreteBook)选中.
     */
    private TableView<SecreteBook> bookTableView;

    public TableView<SecreteBook> getBookTableView() {
        return bookTableView;
    }

    public void setBookTableView(TableView<SecreteBook> bookTableView) {
        this.bookTableView = bookTableView;
    }

    /**
     * 搜索密码
     */
    @FXML
    private void searchPassword(){
        //ObservableList<SecreteBook> itemList = bookTableView.getItems();
    }





}
