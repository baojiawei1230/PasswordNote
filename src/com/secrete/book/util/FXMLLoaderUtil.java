package com.secrete.book.util;

import com.secrete.book.view.CreateAdminController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * FXMLLoaderUtil
 *
 * @Author Alex_Bao
 * @create 2018-05-06
 * create by IntelliJ IDEA
 */
public class FXMLLoaderUtil {


    /**
     * create main state dialog.
     *
     * @return
     */
    public static Object createNewMainStageDialog(){

        return new Object();
    }


    /**
     * create window modual dialog
     * .
     * @param filePath filePath
     * @param owner mainStage
     * @param title title
     * @return
     */
    public static Object createNewWindowModelDialog(String filePath , Stage owner, String title,Class clazz){
        FXMLLoader loader  = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemClassLoader().getResource(filePath));
        try {
            AnchorPane pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            Object controller = loader.getController();
            if(controller instanceof CreateAdminController){
                CreateAdminController adminController = (CreateAdminController)controller;
                //TODO set stage.
                //adminController.set

            }
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
