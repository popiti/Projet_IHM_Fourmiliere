package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import jeudesFourmis.model.Fourmiliere;
import jeudesFourmis.vue.GameController;
import jeudesFourmis.vue.GameVue;
import jeudesFourmis.vue.Grille;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	@Override
	public void start(Stage primarystage) {
		
        primarystage.setTitle("Grid");
        primarystage.setScene(new Scene(createcontent()));
        primarystage.show();
     
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    //Fonction qui permet de créer le jeu
    private Parent createcontent() {
		
	   // Création des objets
	   BorderPane root = new BorderPane();
	   Fourmiliere f = new Fourmiliere(Grille.TAILLE_DEFAULT, Grille.TAILLE_DEFAULT, 3);
       GameVue vue = new GameVue(f);
       GameController c = new GameController(f,vue);
       root = vue;
       return root;
    }
}
