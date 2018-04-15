package sample;

import com.screte.book.model.SecreteBook;
import com.screte.book.model.SecreteBookWrapper;
import com.screte.book.view.SecreteBookController;
import com.screte.book.view.SecreteBookEditController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public final class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * 绑定关联密码本集合.
     */
    private ObservableList<SecreteBook> secreteBooks = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("简单密码本");
        initRootLayout();
        showSecreteOverView();
    }


    /**
     * 获取密码本集合
     *
     * @return
     */
    public ObservableList<SecreteBook> getSecreteBooks() {
        return secreteBooks;
    }

    /**
     * 视图
     */
    private void showSecreteOverView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("overView.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
            SecreteBookController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化布局
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * getPrimaryStage
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 显示编辑对话框
     *
     * @param secreteBook
     * @return
     */
    public boolean showSecreteEditDialog(SecreteBook secreteBook){
        try {
            //load tht fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("editSecreteBook.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            //create a dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("编辑密码信息");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            //set the secrete book into the controller
            SecreteBookEditController editController = loader.getController();
            editController.setDialogState(dialogStage);
            editController.setSecreteBook(secreteBook);
            //show the dialog and waite until the user close it .
            dialogStage.showAndWait();
            return editController.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取密码簿的文件路径
     *
     * @return
     */
    public File getSecreteBookFilePath(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        }
        return null;
    }

    /**
     * 设置存储文件路径
     *
     * @param file
     */
    public void setSecreteBookFilePath(File file){
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        if(file != null){
            preferences.put("filePath",file.getPath());
        }else{
            preferences.remove("filePath");

            //update the stage title.
            primaryStage.setTitle("用户哈哈哈");
        }
    }


    /**
     * 从文件中加载密码信息
     * loads secrete key form the specified file . the current secrete book data will be replaced.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SecreteBookWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            SecreteBookWrapper wrapper = (SecreteBookWrapper) um.unmarshal(file);

            secreteBooks.clear();
            secreteBooks.addAll(wrapper.getBookList());

            // Save the file path to the registry.
            setSecreteBookFilePath(file);

        } catch (Exception e) { // catches ANY exception
//            Dialogs.create()
//                    .title("Error")
//                    .masthead("Could not load data from file:\n" + file.getPath())
//                    .showException(e);
            e.printStackTrace();
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SecreteBookWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            SecreteBookWrapper wrapper = new SecreteBookWrapper();
            wrapper.setBookList(secreteBooks);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setSecreteBookFilePath(file);
        } catch (Exception e) { // catches ANY exception
//            Dialogs.create().title("Error")
//                    .masthead("Could not save data to file:\n" + file.getPath())
//                    .showException(e);
            e.printStackTrace();
        }
    }
}
