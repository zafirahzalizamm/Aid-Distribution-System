import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main class for the javafx application program
 * GUI for Aid Distribution System
 * Inherited from the Application superclass
 */
public class Main extends Application {

    // DECLARE TO BE ABLE TO SWITCH SCENE
    private Stage currentstage;

    // constants
    private final String csssheet = "src/font.css";
    private final String logosrc = "src/icon.png";

    // list of users in hashmap
    private Map<String, Donor> DONORmap = DC.getdonorfromcsv();
    private Map<String, NGO> NGOmap = DC.getNGOfromcsv();
    private ArrayList<AllAid> AllAidsList = DC.getALLaidsfromcsv("ADMIN");

    // update functions
    private void updateuserfile() {
        DONORmap = DC.getdonorfromcsv();
        NGOmap = DC.getNGOfromcsv();
    }

    // update all the aids into allaidslist (no matter nullngo or nulldonor)
    private void updateaidfile(){
        AllAidsList = DC.getALLaidsfromcsv("ADMIN");
    }

    // Override the start method in the Application class
    @Override

    ////////////////////////////////// Starting of the stage
    ////////////////////////////////// ////////////////////////////////////////////////////////////////////
    /**
     * To start the stage of the javafx application
     */
    public void start(Stage primaryStage) throws Exception {
        // ASSIGN THE STAGE TO THE CURRENTSTAGE OUTSITE THE FUNCTION
        currentstage = primaryStage;

        // icon for application top left and menu bar
        Image icon = new Image(logosrc);
        primaryStage.getIcons().add(icon);

        // Scene scene1 = loginscene();
        Scene scene1 = setupScene(firstdcscene());

        // title of Scene
        primaryStage.setTitle("AID DISTRIBUTION SYSTEM");
        primaryStage.sizeToScene();
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    ////////////////////////////////// LOGIN SCENE
    ////////////////////////////////// ////////////////////////////////////////////////////////////////////

    /**
     * To create a gridpane scene that can obtain information which are username and password
     * The event handler of funcbutton can redirect the user to respective user scene (donor / ngo)
     * @param function 1 is for login scene , 2 is for register scene
     * @return Gridpane of login or register scene
     */
    public GridPane REGLOGscene(int function) {
        // 1 is for login screen
        // 2 is for register screen
        // Create a gridpane with properties
        GridPane gpane = new GridPane();
        gpane.setPadding(new Insets(12, 13, 14, 15));
        // gpane.setHgap(6);
        // gpane.setVgap(6);
        // gpane.setAlignment(Pos.CENTER);

        // Nodes inside pane
        Text msg = new Text();

        Label lusername = new Label("Username   ");
        Label lpassword1 = new Label("Password   ");
        Label lpassword2 = new Label("Password Confirmation   ");
        Label result = new Label("");

        TextField username_field = new TextField();
        TextField password1_field = new PasswordField();
        TextField password2_field = new PasswordField();

        Button RETURNBUTTON = new Button("Return");
        Button FUNCBUTTON = new Button("");

        GridPane.setHalignment(msg, HPos.CENTER);
        GridPane.setHalignment(RETURNBUTTON, HPos.CENTER);
        GridPane.setHalignment(FUNCBUTTON, HPos.CENTER);
        GridPane.setHalignment(result, HPos.CENTER);

        Alert alert = new Alert(AlertType.NONE);

        FUNCBUTTON.setMaxSize(200, 100);
        RETURNBUTTON.setMaxSize(200, 100);

        gpane.add(msg, 0, 0, 2, 1);

        // login screen
        if (function == 1) {
            msg.setText("Login");

            gpane.add(lusername, 0, 1);
            gpane.add(username_field, 1, 1);

            gpane.add(lpassword1, 0, 2);
            gpane.add(password1_field, 1, 2);

            // login button
            FUNCBUTTON.setText("Login");

            gpane.add(RETURNBUTTON, 0, 3);
            gpane.add(FUNCBUTTON, 1, 3);

            // login message label
            gpane.add(result, 0, 4, 2, 2);

            // LOGIN FUNCTION
            FUNCBUTTON.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    if (username_field.getText().equals(""))
                        result.setText("Username Is Empty");
                    else if (password1_field.getText().equals(""))
                        result.setText("Password Is Empty");
                    else if (!(NGOmap.containsKey(username_field.getText())
                            || DONORmap.containsKey(username_field.getText())))
                        result.setText("Username Do Not Exist");

                    else if (!userpass().equals(password1_field.getText()))
                        result.setText("Password Incorrect");
                    else if (getmap().equals("NGO")) {
                        alert.setAlertType(AlertType.INFORMATION);
                        alert.setContentText("NGO Account Login Successful");
                        alert.show();
                        currentstage.setScene(setupScene(DONORNGOscene(username_field.getText(), "NGO")));
                    }

                    else if (getmap().equals("DONOR")) {
                        alert.setAlertType(AlertType.INFORMATION);
                        alert.setContentText("DONOR Account Login Successful");
                        alert.show();
                        currentstage.setScene(setupScene(DONORNGOscene(username_field.getText(), "DONOR")));
                    }
                    currentstage.sizeToScene();
                }

                public String userpass() {
                    if (NGOmap.containsKey(username_field.getText()))
                        return NGOmap.get(username_field.getText()).getpassword();
                    else if (DONORmap.containsKey(username_field.getText()))
                        return DONORmap.get(username_field.getText()).getpassword();
                    return " ";
                }

                public String getmap() {
                    if (NGOmap.containsKey(username_field.getText()))
                        return "NGO";
                    else if (DONORmap.containsKey(username_field.getText()))
                        return "DONOR";
                    return " ";
                }
            });
        }

        else if (function == 2) {
            msg.setText("Register");

            // Add toggle feature for register to the gui
            ToggleGroup REGgroup = new ToggleGroup();
            ToggleButton REGdonor = new ToggleButton("Donor");
            ToggleButton REGngo = new ToggleButton(" NGO ");

            REGngo.setToggleGroup(REGgroup);
            REGdonor.setToggleGroup(REGgroup);

            REGngo.setMaxSize(200, 100);
            REGdonor.setMaxSize(200,100);

            REGgroup.selectToggle(REGdonor);
            gpane.add(REGdonor, 0, 1);
            gpane.add(REGngo, 1, 1);
            GridPane.setHalignment(REGdonor, HPos.CENTER);
            GridPane.setHalignment(REGngo, HPos.CENTER);

            gpane.add(lusername, 0, 2);
            gpane.add(username_field, 1, 2);

            gpane.add(lpassword1, 0, 3);
            gpane.add(password1_field, 1, 3);

            // col, row

            gpane.add(lpassword2, 0, 4);
            gpane.add(password2_field, 1, 4);

            // details of the registration

            // name for both ngo and donors
            Label detail1 = new Label("Name ");
            TextField detail1_field = new TextField();
            gpane.add(detail1, 0, 5);
            gpane.add(detail1_field, 1, 5);

            // manpower for ngo and phone number for user
            Label detail2 = new Label("Phone Number");
            TextField detail2_field = new TextField();
            gpane.add(detail2, 0, 6);
            gpane.add(detail2_field, 1, 6);

            // login button
            FUNCBUTTON.setText("Register");
            gpane.add(RETURNBUTTON, 0, 7);
            gpane.add(FUNCBUTTON, 1, 7);

            // login message label
            gpane.add(result, 0, 8, 2, 2);

            REGgroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle toggled) {
                    if (toggled != null) {
                        String toggleBtn = ((ToggleButton) toggled).getText();
                        if (toggleBtn.equals("Donor")) {
                            detail2.setText("Phone Number");
                        }

                        else if (toggleBtn.equals(" NGO ")) {
                            detail2.setText("Manpower Count");
                        }
                    }
                }
            });

            // Register FUNCTION
            FUNCBUTTON.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    
                    if (username_field.getText().equals(""))
                        result.setText("Username Is Empty");
                    else if (password1_field.getText().equals("") || password2_field.getText().equals(""))
                        result.setText("Password Is Empty");
                    else if (!password1_field.getText().equals(password2_field.getText()))
                        result.setText("Password Confirmation Incorrect");
                    else if (detail1_field.getText().equals(""))
                        result.setText("Name Is Empty");
                    else if (detail2_field.getText().equals(""))
                        result.setText(detail2.getText() + " Is Empty");
                    else if (DONORmap.containsKey(username_field.getText())
                            || NGOmap.containsKey(username_field.getText()))
                        result.setText("Username Exist");
                    else if (username_field.getText().equals("") || password1_field.getText().equals("-") || 
                            detail1_field.getText().equals("-")|| detail2_field.getText().equals("-"))
                        result.setText("Invalid Input. (-) is not Allowed");
                    else {

                        ToggleButton selectedRadioButton = (ToggleButton) REGgroup.getSelectedToggle();
                        String toggled = selectedRadioButton.getText();
                        alert.setAlertType(AlertType.INFORMATION);

                        try{ 
                            Integer count = Integer.parseInt(detail2_field.getText());
                            // register take place
   
                            if (toggled.equals("Donor")) {
                                DONORmap.put(username_field.getText(), new Donor(username_field.getText(),
                                        password1_field.getText(), detail1_field.getText(), detail2_field.getText()));
    
                                alert.setContentText("Registration for Donor successful !!!");
                            } else if (toggled.equals(" NGO ")) {
                                NGOmap.put(username_field.getText(), new NGO(username_field.getText(),
                                        password1_field.getText(), detail1_field.getText(), detail2_field.getText()));
                                alert.setContentText("Registration for  NGO  successful !!!");
                            }
                            try {
                                DC.RegNewUser(NGOmap, DONORmap);
                                updateuserfile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            alert.show();
                            currentstage.setScene(setupScene(firstdcscene()));
                            currentstage.sizeToScene();
                        }
                        catch(Exception e){
                            if (toggled.equals("Donor")) 
                                result.setText("Only number is allowed for Phone Number");
                            else if (toggled.equals(" NGO "))
                                result.setText("Only number is allowed for Manpower Count");
                        }
                    }
                }
            });
        }

        // Return to main
        RETURNBUTTON.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                currentstage.setScene(setupScene(firstdcscene()));
                currentstage.sizeToScene();
            }
        });
        return gpane;
    }

    ////////////////////////////////// DONOR, NGO and DC Table SCENE
    ////////////////////////////////// /////////////////////////////////

    /**
     * To create a VBox for construction the scene for  distribution center (DC)
     * Including a table that can view all the aids matching (no matter nullngo or nulldonor)
     * @return VBox layout for distribution center (DC)
     */
    public VBox DCscene() {
        updateaidfile();

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        Label label = new Label("Distribution Center");

        Label label1 = new Label("NOTE: - refers to aids that have not been matched yet");

        Button RETURNBUTTON = new Button("Return");
        RETURNBUTTON.setMaxSize(200, 100);

        // Return to main
        RETURNBUTTON.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                currentstage.setScene(setupScene(firstdcscene()));
                currentstage.sizeToScene();
            }
        });

        // Create a tableview
        TableView<AllAid> table = new TableView<>();
        // Add Rows to the tableview
        table.getItems().addAll(TableViewHelper.getAllAidTableList(AllAidsList));
        // add columns to the TableView
        table.getColumns().addAll(TableViewHelper.getDONORname(), TableViewHelper.getDONORphone(),
                TableViewHelper.getAIDname(), TableViewHelper.getAIDqty(),
                TableViewHelper.getNGOname(), TableViewHelper.getNGOmanpower());

        // Set the column resize policy to constrained resize policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the Placeholder for an empty table
        table.setPlaceholder(new Label("No data exist yet."));
        // Nodes
        // CREATE TABLEVIEW WITH A LIST OF DISTRIBUTED AIDS
        vb.getChildren().add(label);
        VBox.setMargin(label, new Insets(10));
        vb.getChildren().add(label1);
        VBox.setMargin(label, new Insets(10));
        vb.getChildren().add(table);
        VBox.setMargin(table, new Insets(0, 10, 0, 10));
        vb.getChildren().add(RETURNBUTTON);
        VBox.setMargin(RETURNBUTTON, new Insets(10));

        return vb;

    }

    /**
     * To create a VBox for construction the scene for  donor or ngo account after they have logged in
     * Including a table for user to view their respective aid that is donated // requested
     * @param username username of the user(unique) that is logged in
     * @param usertype types of user (DONOR or NGO)
     * @return VBox layout for donor and NGO
     */
    public VBox DONORNGOscene(String username, String usertype) {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        Label label = new Label("");

        Button RETURNBUTTON = new Button("LogOut");
        RETURNBUTTON.setMaxSize(200, 100);

        Button FUNCBUTTON = new Button("");
        FUNCBUTTON.setMaxSize(200, 100);

        if(usertype.equals("DONOR")){
            label.setText("WELCOME DONOR : " + username);
            FUNCBUTTON.setText("Donate More");
        }
        else if (usertype.equals("NGO")){
            label.setText("WELCOME NGO : " + username);
            FUNCBUTTON.setText("Request More");
        }

        HBox p = new HBox();
        p.getChildren().add(FUNCBUTTON);
        p.getChildren().add(RETURNBUTTON);
        p.setAlignment(Pos.CENTER);

        // Return to main
        RETURNBUTTON.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                currentstage.setScene(setupScene(firstdcscene()));
                currentstage.sizeToScene();
            }
        });

        if (usertype.equals("DONOR") || usertype.equals("NGO"))

            FUNCBUTTON.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    currentstage.setScene(setupScene(FUNCaddrequest(username,usertype)));
                    currentstage.sizeToScene();
                }
            });

        // Create a tableview
        TableView<AllAid> table = new TableView<>();
        // Add Rows to the tableview
        table.getItems().addAll(TableViewHelper.getAllAidTableList(DC.getALLaidsfromcsv(username)));
        // add columns to the TableView
        table.getColumns().addAll(TableViewHelper.getDONORname(), TableViewHelper.getDONORphone(),
                TableViewHelper.getAIDname(), TableViewHelper.getAIDqty(),
                TableViewHelper.getNGOname(), TableViewHelper.getNGOmanpower());

        // Set the column resize policy to constrained resize policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the Placeholder for an empty table
        table.setPlaceholder(new Label("No data exist yet."));
        // Nodes
        // CREATE TABLEVIEW WITH A LIST OF DISTRIBUTED AIDS
        vb.getChildren().add(label);
        VBox.setMargin(label, new Insets(10));
        vb.getChildren().add(table);
        VBox.setMargin(table, new Insets(0, 10, 0, 10));
        vb.getChildren().add(p);
        VBox.setMargin(p, new Insets(10));

        return vb;

    }

    ///////////////////////////////// DONATE MORE OR REQUEST MORE SCENE
    ////////////////////////////////////////////////////////////////////
    /**
     * To create a gridpane for the creation of scene of donate or request more scene
     * @param username the username of the current user in the system 
     * @param usertype the type of the current user in the system (DONOR or NGO)
     * @return Gridpane of layout for donate or request more scene
     */
    public GridPane FUNCaddrequest(String username, String usertype){
        GridPane newp = new GridPane();

        // Nodes
        Text msg = new Text();

        Label lnewaid = new Label("AID");
        Label lnewaidqty = new Label("Quantity");
        Label result = new Label("");

        TextField lnewaid_field = new TextField();
        TextField lnewaidqty_field = new TextField();

        Button EXITBUTTON = new Button("Return");
        Button FUNCBUTTON = new Button("");

        GridPane.setHalignment(msg, HPos.CENTER);
        GridPane.setHalignment(EXITBUTTON, HPos.CENTER);
        GridPane.setHalignment(FUNCBUTTON, HPos.CENTER);
        GridPane.setHalignment(result, HPos.CENTER);

        Alert alert = new Alert(AlertType.NONE);
        alert.setAlertType(AlertType.INFORMATION);
        
        FUNCBUTTON.setMaxSize(200, 100);
        EXITBUTTON.setMaxSize(200, 100);

        if(usertype.equals("DONOR")){
            msg.setText("DONATE AID FROM " + username);
            FUNCBUTTON.setText("DONATE");
        }
        else if(usertype.equals("NGO")){
            msg.setText("REQUEST AID FROM "+ username);
            FUNCBUTTON.setText("REQUEST");
        }

        newp.setPadding(new Insets(13,13,13,13));
        HBox.setMargin(msg,new Insets(12));
        HBox.setMargin(lnewaid,new Insets(12));
        HBox.setMargin(lnewaid_field,new Insets(1,12,1,12));
        HBox.setMargin(lnewaidqty,new Insets(12));
        HBox.setMargin(lnewaidqty_field,new Insets(1,12,1,12));
        HBox.setMargin(result,new Insets(12));
        HBox.setMargin(EXITBUTTON,new Insets(1,12,12,12));
        HBox.setMargin(FUNCBUTTON,new Insets(1,12,12,12));


        newp.add(msg,0,0,2,1);
        newp.add(lnewaid,0,1);
        newp.add(lnewaid_field,1,1);
        newp.add(lnewaidqty,0,2);
        newp.add(lnewaidqty_field,1,2);
        newp.add(result, 0, 3, 2, 2);
        newp.add(EXITBUTTON,0,5);
        newp.add(FUNCBUTTON,1,5);

        FUNCBUTTON.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            try{
                Integer quantity = Integer.parseInt(lnewaidqty_field.getText());
            if (lnewaid_field.getText().equals(""))
                result.setText("AID Is Empty");
            else if (lnewaidqty_field.getText().equals(""))
                result.setText("AID QUANTITY Is Empty");
            else if (quantity <= 0)
                result.setText("Only positive integer allowed for quantity");
            else if (lnewaid_field.getText().equals("-") || lnewaidqty_field.getText().equals("-"))
                result.setText("Invalid Input. (-)  is not Allowed");
			else{
                if(usertype.equals("DONOR")){
                    AllAidsList.add(new AllAid(new Donor(username,DONORmap.get(username).getDonorname(),DONORmap.get(username).getPhonenum()),
                                                NGO.nullngo(),
                                                lnewaid_field.getText(),quantity));
                    alert.setContentText("SUCCESSFUL DONATED: \n" + quantity + "    " + lnewaid_field.getText());
                }
                else if (usertype.equals("NGO")){
                    AllAidsList.add(new AllAid(Donor.nulldonor(),
                                                new NGO(username,NGOmap.get(username).getNgoname(),NGOmap.get(username).getManpower()),
                                                lnewaid_field.getText(),quantity));
                    alert.setContentText("SUCCESSFUL REQUESTED FOR: \n" + quantity + "    " + lnewaid_field.getText());
                }
				try {
					DC.updateaid(AllAidsList);
                    DC.matching();
                    updateaidfile();
                    alert.show();
                    lnewaid_field.setText("");
                    lnewaidqty_field.setText("");
                    result.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}            
            }}
                catch(Exception e){
                    result.setText("Quantity must be positive integer only");
                }
            
            }
        });


        // EXIT AFTER COMPLETED
        EXITBUTTON.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            currentstage.setScene(setupScene(DONORNGOscene(username, usertype)));
            currentstage.sizeToScene();
        }
        });
        return newp;

    }

    ////////////////////////////////// MAIN DC SCENE
    ////////////////////////////////// /////////////////////////////////

    /**
     * Act as the main screen / scene when the user start the program
     * Able to redirect user to Distribution Center  /  login   / Register
     * @return the VBox layouts for the main scene (just like menu system)
     */
    public VBox firstdcscene() {
        // create gridpane with properties
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 5, 5, 5));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        // nodes inside GridPane
        vbox.getChildren().add(getWelcomeLogoText());

        Text txtdiscription = new Text();
        txtdiscription.setText("Please Choose A Functionality");
        vbox.getChildren().add(txtdiscription);

        Button DCBTN = new Button("Distribution Center");
        // DCBTN.setId("font-button");
        vbox.getChildren().add(DCBTN);
        // action event
        DCBTN.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                currentstage.setScene(setupScene(DCscene()));
                bigscreen();
            }
        });

        Button LOGINBTN = new Button("Login");
        vbox.getChildren().add(LOGINBTN);
        // action event
        LOGINBTN.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {

                currentstage.setScene(setupScene(REGLOGscene(1)));
                currentstage.sizeToScene();
            }
        });

        Button REGISTERBTN = new Button("Register");
        vbox.getChildren().add(REGISTERBTN);
        // action event
        REGISTERBTN.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                currentstage.setScene(setupScene(REGLOGscene(2)));
                currentstage.sizeToScene();
            }
        });

        DCBTN.setMaxSize(200, 100);
        LOGINBTN.setMaxSize(200, 100);
        REGISTERBTN.setMaxSize(200, 100);

        return vbox;
    }

    /**
     * To display the welcome text of the system
     * @return the vBox layouts of the welcome text of the system
     */
    private VBox getWelcomeLogoText() {
        ImageView logo = new ImageView(new Image(logosrc));
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(12, 13, 14, 15));
        vBox.getChildren().add(logo);
        vBox.getChildren().add(new Label("Welcome To AID Distribution Center"));

        return vBox;
    }

    ////////////////////////////////// SetUp the scene need to be used by the
    ////////////////////////////////// program ///////////////////////////////

    /**
     * Used to return the scene given a designed vbox
     * @param tempscene VBox that is constructed and intended to make it into a scene
     * @return scene that can be used in stage.setScene() function
     */
    public Scene setupScene(VBox tempscene) {
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(tempscene);

        scene.getStylesheets().add(getClass().getResource(csssheet).toExternalForm());
        return scene;
    }

    /**
     * Used to return the scene given a designed gridpane
     * Overloaded function for setupScene(VBox)
     * @param tempscene Gridpane that is constructed and intended to make it into a scene
     * @return scene that can be used in stage.setScene() function
     */
    public Scene setupScene(GridPane tempscene) {
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane);
        pane.setAlignment(Pos.CENTER);

        GridPane display = tempscene;

        pane.getChildren().add(display);

        scene.getStylesheets().add(getClass().getResource(csssheet).toExternalForm());
        return scene;
    }

    /**
     * Enlarge the current stage to the 900*600 size
     * To be able to view the full table in DC
     * Better apperances
     */
    public void bigscreen(){
        currentstage.setWidth(900);
        currentstage.setHeight(600);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Launch the javafx application
     * @param args array of sequence of characters that are passed to main function
     */
    public static void main(String[] args) {
        launch(args);
    }
}
