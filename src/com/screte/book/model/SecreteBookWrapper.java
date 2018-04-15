package com.screte.book.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * SecreteBookWrapper
 *
 * 保存密码信息
 *
 * @Author Alex_Bao
 * @create 2018-04-15
 * create by IntelliJ IDEA
 */
@XmlRootElement
public class SecreteBookWrapper {

    /**
     * 密码信息集合
     */
    private List<SecreteBook> bookList;

    @XmlElement(name = "secreteBook")
    public List<SecreteBook> getBookList() {
        return bookList;
    }

    public void setBookList(List<SecreteBook> bookList) {
        this.bookList = bookList;
    }
}
