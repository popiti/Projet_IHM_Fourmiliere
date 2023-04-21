package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import jeudesFourmis.model.Fourmiliere;
import jeudesFourmis.vue.GameController;
import jeudesFourmis.vue.GameVue;
import jeudesFourmis.vue.Infos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.util.Random;
import java.util.Scanner;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Stage stage;
	@Override
	public void start(Stage primarystage) {
		
		stage = primarystage;
		BorderPane root = new BorderPane();
		Fourmiliere f = new Fourmiliere(20, 20, 3);
		 for (int i =0; i <10; i++){
		        f.setQteGraines(i,2*i, 1);
		        f.setQteGraines(11-i,2*i , 1);
		      }
        GameVue vue = new GameVue(primarystage,f);
        f.setQteGraines(15, 1, 3);
        
        root.setBottom(vue);
		
        GameController c = new GameController(f,vue);
        primarystage.setTitle("Grid");
        primarystage.setScene(new Scene(root));
        primarystage.show();
     
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
 
}
