
package fr.uparis.informatique.cpoo5.richtextdemo;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.StyleClassedTextArea;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class RichTextDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

         

        /*VBox root = new VBox();
        
        StyleClassedTextArea textArea = new StyleClassedTextArea();
        textArea.replaceText("Lorem Ipsum");
        textArea.setPlaceholder(root);
       // textArea.
        textArea.setStyleClass( 5, 9, "red");
        root.getChildren().add(textArea);*/

        TextField currentWordField = new TextField();
        currentWordField.setEditable(false);

// Create a text area to display the typed text
        TextArea typedTextArea = new TextArea();
        typedTextArea.setEditable(false);



        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

       /* Text text = new Text("Hello, World!");
        TextFlow paneR = new TextFlow();
        TextFlow paneV = new TextFlow();
        text.setStroke(Color.RED);
       // text.setFill(Color.RED);
        paneR.setPadding(new Insets(100));
        paneV.setPadding(new Insets(200));
        paneR.getChildren().addAll(text,paneV);

        scene.setRoot(paneR);*/





        primaryStage.setTitle("Rich Text Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

