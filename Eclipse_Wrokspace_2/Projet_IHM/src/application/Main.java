package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import jeudesFourmis.model.Fourmiliere;
import jeudesFourmis.vue.GameController;
import jeudesFourmis.vue.GameVue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.util.Random;
import java.util.Scanner;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Stage stage;
	@Override
	public void start(Stage primarystage) {
		stage = primarystage;
        GameVue vue = new GameVue(primarystage); 

        GameController c = new GameController(new Fourmiliere(10, 10, 3),vue);
        
        primarystage.setTitle("Grid");
        primarystage.setScene(new Scene(vue));
        primarystage.show();
     
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
 
}
