/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vdictdictionary;

import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ThienDinh
 */
public class VdictDictionary extends Application {
    
    private TextField wordBox;
    private VdictWord inputWord;
    private WebView displayArea;
    
    @Override
    public void start(Stage primaryStage) {
        
        // Vertical Box
        VBox borderBox = new VBox();
        // Display text
        displayArea = new WebView();
        
        // Typing box
        wordBox = new TextField();
        wordBox.setOnAction(new EventHandler(){

            @Override
            public void handle(Event event) {
                inputWord = new VdictWord(wordBox.getText());
                displayArea.getEngine().loadContent(inputWord.getDefinition());
                Random rand= new Random();
                int index = rand.nextInt(5) + 1;
                displayArea.getEngine().setUserStyleSheetLocation(getClass().getResource("style_"+ index +".css").toExternalForm());
            }
            
        });
        borderBox.getChildren().addAll(wordBox, displayArea);
        Scene sceneGraph = new Scene(borderBox, 300, 400);
        
        sceneGraph.setOnMouseExited(new EventHandler(){

            @Override
            public void handle(Event event) {
                primaryStage.setOpacity(0.3);
            }
            
        });
        
        sceneGraph.setOnMouseEntered(new EventHandler(){

            @Override
            public void handle(Event event) {
                primaryStage.setOpacity(1.0);
            }
        
        });
        primaryStage.setTitle("Vdict Dictionary");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(sceneGraph);
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
