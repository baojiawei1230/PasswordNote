package sample;

import com.secrete.book.function.LoginListener;
import com.secrete.book.function.UserLoginEvent;
import com.secrete.book.model.SecreteBook;
import com.secrete.book.model.SecreteBookWrapper;
import com.secrete.book.model.UserInfo;
import com.secrete.book.model.UserInfoWrapper;
import com.secrete.book.util.ResourceUtil;
import com.secrete.book.util.SecreteBookUtil;
import com.secrete.book.view.CreateAdminController;
import com.secrete.book.view.HomePageViewController;
import com.secrete.book.view.SecreteBookController;
import com.secrete.book.view.SecreteBookEditController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.prefs.Preferences;

public final class MainApplication extends Application implements LoginListener<UserLoginEvent>{

    /**
     * stage
     */
    private Stage primaryStage;
    /**
     * BorderPane
     */
    private BorderPane rootLayout;
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

    /**
     * 绑定关联密码本集合.
     */
    private ObservableList<SecreteBook> secreteBooks = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        //set title
        this.primaryStage.setTitle("PassNote");
        //set resize false
        this.primaryStage.setResizable(false);
        // init layout
        initRootLayout();
        //init press password
        initHomePageView();
        //init overview
        //showSecreteOverView();
    }


    /**
     * init home page view to press password.
     */
    public void initHomePageView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("homePageView.fxml"));
        try {
            GridPane gridPane = loader.load();
            rootLayout.setCenter(gridPane);
            HomePageViewController homePageViewController = loader.getController();
            homePageViewController.setMainApp(this);
            //check username and password .
            //homePageViewController.checkUserNameAndPassword();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void showSecreteOverView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("overView.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
            SecreteBookController controller = loader.getController();
            try {
                //load information from file
                File file = SecreteBookUtil.loadInformationFromFile(null);
                this.loadSecreteBookDataFromFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            //SecreteBookMenuController menuController = loader.getController();
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

    //main method
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
            AnchorPane pane = loader.load();
            //create a dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Pass Note");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            //set the secrete book into the controller
            SecreteBookEditController editController = loader.getController();
            editController.setDialogStage(dialogStage);
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
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
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
        Preferences preferences = Preferences.userNodeForPackage(MainApplication.class);
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
     *
     * loads secrete key form the specified file . the current secrete book data will be replaced.
     *
     * @param file
     */
    public void loadSecreteBookDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SecreteBookWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            SecreteBookWrapper wrapper = (SecreteBookWrapper) um.unmarshal(file);

            secreteBooks.clear();
            if(wrapper.getBookList() != null && wrapper.getBookList().size() > 0){
                secreteBooks.addAll(wrapper.getBookList());
                //add sort measure
                secreteBooks.sort(new Comparator<SecreteBook>() {
                    @Override
                    public int compare(SecreteBook firstBook, SecreteBook secondBook) {
                        return firstBook.getSiteName().compareTo(secondBook.getSiteName());
                    }
                });
            }

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
     * load username and password from configuration file.
     *
     * @param file
     */
    public UserInfoWrapper loadUserNameAndPasswordFromFile(File file){
        try {
            if(!file.exists()){
                return null;
            }
            JAXBContext context = JAXBContext.newInstance(UserInfoWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            UserInfoWrapper wrapper = (UserInfoWrapper) um.unmarshal(file);
            return wrapper;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * create an user info from user input message.
     *
     * @param file
     */
    public void createUserNameAndPasswordFile(File file , UserInfo userInfo){
        try {
            if(file == null){
                file = USER_INFO_FILE;
            }
            if(userInfo == null){
                return;
            }
            JAXBContext context = JAXBContext.newInstance(UserInfoWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            UserInfoWrapper userInfoWrapper = new UserInfoWrapper();
            userInfoWrapper.setUserInfo(userInfo);
            FileWriter fileWriter = new FileWriter(file);
            marshaller.marshal(userInfoWrapper,fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存密码信息到文件中
     *
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void saveSecreteBookDataToFile(File file) {
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

    /**
     * new pass note record dialog
     *
     * @param secreteBook
     */
    public boolean showNewPassNoteDialog(SecreteBook secreteBook) {
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newSecreteBook.fxml"));
        AnchorPane pane = loader.load();
        //create a dialog stage
        Stage dialogStage = new Stage();
        dialogStage.setTitle("New Pass Note");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);

        //set the secrete book into the controller
        SecreteBookEditController editController = loader.getController();
        editController.setDialogStage(dialogStage);
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
     * new admin dialog
     *
     * @return
     */
    public void showNewAdminDialog(){
        try {
            FXMLLoader loader  = new FXMLLoader();
            loader.setLocation(getClass().getResource("createAdminDialog.fxml"));
            AnchorPane pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Admin");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            CreateAdminController adminController = loader.getController();
            adminController.setMainApplication(this);
            adminController.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onLoginEvent(UserLoginEvent loginEvent) {
        return false;
    }


}
