package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {
    File currentDirectory = null;
    File defaultDirectory;
    String projectName;

    //Easy Tab
    @FXML
    Button easyCreate;
    @FXML
    Button easyFileBrowser;
    @FXML
    Label easyFilePath;
    @FXML
    ProgressBar easyLoading;
    @FXML
    TextArea easyLog;
    @FXML
    TextField easyProjectName;

    //Medium Tab
    @FXML
    Button normalFileBrowser;
    @FXML
    Label normalFilePath;
    @FXML
    Button mediumCreatePDF;
    @FXML
    ChoiceBox mediumColorMode = new ChoiceBox();
    @FXML
    TextArea normalLog;

    //Hard Tab
    @FXML
    Button hardFileBrowser;
    @FXML
    Label hardFilePath;
    @FXML
    Button hardImport;
    @FXML
    Button hardDelete;
    @FXML
    Button hardScan;
    @FXML
    Button hardRunScanTailor;
    @FXML
    Button hardCreatePDF;
    @FXML
    Spinner hardMargins;
    @FXML
    ChoiceBox hardLayoutOption = new ChoiceBox();
    @FXML
    ChoiceBox hardOrientation = new ChoiceBox();
    @FXML
    ChoiceBox hardColorMode = new ChoiceBox();

    @FXML File locateFile(ActionEvent event) {
        //TODO: Make this work
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        // Manually set the default director -- THIS IS BAD
        defaultDirectory = new File("/Users/paul/Documents/Programming/VML/Scanner-Praxis/");
        //sets initial directory to this default
        chooser.setInitialDirectory(defaultDirectory);
        currentDirectory = chooser.showDialog(new Stage());
        return currentDirectory;
    }

    //Initializes the project
    public void initialize() {
        setText();
        easyTab();
        mediumTab();
        hardTab();
    }

    private void setText(){
        //Adds options to medium tab's color mode
        mediumColorMode.getItems().addAll("Text and Line Drawings Only", "Text and Photographs", "Full Photographs");
        //Set default value of medium tab color mode options
        mediumColorMode.setValue("Text and Line Drawings Only");
        //Disable two buttons in the hard tab
        hardRunScanTailor.setDisable(true);
        hardCreatePDF.setDisable(true);
        //Adds options to hard tab's layout options
        hardLayoutOption.getItems().addAll("Auto Detect", "One Page", "Two Page");
        //Set default layout option
        hardLayoutOption.setValue("Auto Detect");
        //Adds options to hard tab's orientation options
        hardOrientation.getItems().addAll("Left", "Right", "Upsidedown");
        //Set default orientation options
        hardOrientation.setValue("Left");
        //Sets default margin in hard tab
        hardMargins.getEditor().setPromptText("0.0 mm");
        //Sets color mode options in hard tab
        hardColorMode.getItems().addAll("Text and Line Drawings Only", "Text and Photographs", "Full Photographs");
        //Sets default value in hard tab color options
        hardColorMode.setValue("Text and Line Drawings Only");
    }

    // For the easy tab of the GUI
    void easyTab(){
        // Handles event of "Download Location" button click on the easy tab of the GUI
        easyFileBrowser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Try to locate the file
                try{
                    File results = locateFile(new ActionEvent());
                    easyFilePath.setText(results.toString());
                    normalFilePath.setText(results.toString());
                    hardFilePath.setText(results.toString());
                }
                catch (Exception e){
                    System.out.println("No directory selected.");
                }
            }
        });

        //Handles event of "Download and Create PDF" button
        easyCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //If the current directory has been set, do the scanning; otherwise, show error message
                if(!(currentDirectory == null)){
                    easyLog.appendText("Downloading Pictures from Cameras...\n");
                    System.out.println("Starting");
                    easyCreate.setText("Processing...");
                    easyLog.appendText("Processing Pictures...\n");
                    scanTail();
                    //TODO: Make this not a wireframe
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please chose a place to scan to.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

    }

    // Does the scanner, using the auto scan script
    void scanTail(){
        try{
            //Sets file string to current directory
            String fileOfDirectory = currentDirectory.toString() + "/";
            System.out.println(fileOfDirectory);
            //TODO: Make this generalized
            String[] command = {"/Users/paul/Documents/Programming/VML/Scanner-Praxis/auto_scan.sh", fileOfDirectory, fileOfDirectory};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            process.waitFor();
            System.out.println("Finished");
        }
        catch (Exception e){
            System.out.println("Failed");
        }
    }

    // For the medium tab of the GUI
    void mediumTab(){
        normalFileBrowser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File results = locateFile(new ActionEvent());
                easyFilePath.setText(results.toString());
                normalFilePath.setText(results.toString());
                hardFilePath.setText(results.toString());
            }
        });
        mediumColorMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(mediumColorMode.getValue());
                if(mediumColorMode.getValue().equals("Text and Photographs")){
                    mediumCreatePDF.setDisable(true);
                }
                else{
                    mediumCreatePDF.setDisable(false);
                }
                //TODO: Handle change of options in 'backend'
            }
        });
        mediumCreatePDF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediumCreatePDF.setText("Processing!");
                //TODO: Do the processing
            }
        });
    }

    // For the hard tab of the GUI
    void hardTab() {
        hardFileBrowser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File results = locateFile(new ActionEvent());
                easyFilePath.setText(results.toString());
                normalFilePath.setText(results.toString());
                hardFilePath.setText(results.toString());
            }
        });

        hardImport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hardImport.setText("Importing!");
                //TODO: Do the importing
                hardDelete.setDisable(true);
                try {
                    ProcessBuilder pb = new ProcessBuilder("sh test");
                    pb.redirectErrorStream(true);
                    pb.start();
                }
                catch (IOException e){e.printStackTrace();}
            }
        });

        hardDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete contents on camera??", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    hardDelete.setText("Deleting!");
                    //TODO: Make the delete happen
                }
            }
        });

        hardScan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hardScan.setText("Processing!");
                //TODO: Do the processing
                hardRunScanTailor.setDisable(false);
                hardCreatePDF.setDisable(false);
            }
        });

        hardRunScanTailor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hardRunScanTailor.setText("Opening!");
                //TODO: DO the opening
            }
        });

        hardCreatePDF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hardCreatePDF.setText("Creating!");
                //TODO: DO the creating
            }
        });
    }
}
