package jeudesFourmis.vue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class testtrash extends Application {
	public static final int VGROW = 15;
public static final int HGROW = 5;
public static final int RADIUS = 5;
public static final int PANE_WIDTH = 500;
public static final int PANE_HEIGHT = 500;
	@Override
	public void start(Stage primaryStage) {
	

		// Création des objets
		BorderPane root = new BorderPane();
		//Grille panneau = new Grille(f);
		Circle cercle = new Circle();
		Infos i = new Infos();
		Buttons b = new Buttons();
		VBox rightBox = new VBox();
    	VBox leftBox = new VBox();
		// Styles
		root.setPadding(new Insets(10));
		
		// Alignements, etc...
		BorderPane.setAlignment(rightBox, Pos.CENTER_RIGHT);
    	BorderPane.setAlignment(leftBox, Pos.CENTER_LEFT);
    	rightBox.setAlignment(Pos.CENTER);
    	leftBox.setAlignment(Pos.CENTER);
		//root.setPrefSize(panneau.getWidth(), panneau.getHeight());
		root.setPrefSize(PANE_HEIGHT, PANE_WIDTH);
		// Emboitement des panneaux/boutons
		rightBox.getChildren().add(i.getFourmisL());
		rightBox.setPadding(new Insets(100));
		leftBox.getChildren().add(i.getGrainesL());
		root.setBottom(b);
		//root.setCenter(panneau);
		root.setRight(rightBox);
    	root.setLeft(leftBox);
	
	Scene scene = new Scene(root);
	primaryStage.setScene(scene);
	primaryStage.show();
}
	private Parent createcontent() {
		
		// Création des objets
		BorderPane root = new BorderPane();
		//Grille panneau = new Grille(f);
		Circle cercle = new Circle();
		Infos i = new Infos();
		Buttons b = new Buttons();
		VBox rightBox = new VBox();
    	VBox leftBox = new VBox();
		// Styles
		root.setPadding(new Insets(10));
		
		// Alignements, etc...
		BorderPane.setAlignment(rightBox, Pos.CENTER_RIGHT);
    	BorderPane.setAlignment(leftBox, Pos.CENTER_LEFT);
    	rightBox.setAlignment(Pos.CENTER);
    	leftBox.setAlignment(Pos.CENTER);
		//root.setPrefSize(panneau.getWidth(), panneau.getHeight());
		root.setPrefSize(PANE_HEIGHT, PANE_WIDTH);
		// Emboitement des panneaux/boutons
		rightBox.getChildren().add(i.getFourmisL());
		rightBox.setPadding(new Insets(100));
		leftBox.getChildren().add(i.getGrainesL());
		root.setBottom(b);
		//root.setCenter(panneau);
		root.setRight(rightBox);
    	root.setLeft(leftBox);
		return root ;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
