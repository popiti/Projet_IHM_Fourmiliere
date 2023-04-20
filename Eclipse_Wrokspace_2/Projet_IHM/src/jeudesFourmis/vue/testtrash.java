package jeudesFourmis.vue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
		VBox root = new VBox(VGROW);
		HBox bottomArea = new HBox(HGROW);
		VBox bottomLeftArea = new VBox(HGROW);
		VBox bottomRightArea = new VBox(HGROW);
		HBox bottomRightTopArea  = new HBox(VGROW);
		HBox bottomRightBottomArea  = new HBox(VGROW);
		GridPane buttonsPane = new GridPane();
		GridPane buttonsPane2 = new GridPane();
		Grille panneau = new Grille();
		Circle cercle = new Circle();
		Rectangle rec = new Rectangle();
		Button quit = new Button("Quit");
		Button loupe = new Button("loupe");
		Button play_pause = new Button("Play");
		Button reset = new Button("Reset");
		
		// Styles
		panneau.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		quit.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		reset.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		loupe.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		play_pause.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		
		// Alignements, etc...
		bottomRightArea.setAlignment(Pos.CENTER_RIGHT);
		bottomLeftArea.setAlignment(Pos.CENTER_RIGHT);
		buttonsPane.setVgap(HGROW);
		buttonsPane.setHgap(VGROW);
		root.setPadding(new Insets(10));
	    play_pause.setMaxWidth(Double.MAX_VALUE);
		root.setPrefSize(panneau.getWidth(), panneau.getHeight());
	
		
		// Emboitement des panneaux/boutons
		buttonsPane.add(loupe, 0, 0 );
		buttonsPane.add(play_pause, 0, 1);
		buttonsPane.add(reset, 4, 0);
		buttonsPane.add(quit, 4, 1);
		buttonsPane.add(new Spring(), 2, 0);
		bottomLeftArea.getChildren().addAll(buttonsPane);
		bottomRightTopArea.getChildren().add(reset);
		bottomRightBottomArea.getChildren().add(quit);
		bottomRightArea.getChildren().addAll(bottomRightTopArea, new Spring(),  bottomRightBottomArea);
		bottomArea.getChildren().addAll(bottomLeftArea, new Spring(), bottomRightArea);
		root.getChildren().addAll(panneau,new Spring(), bottomArea);
	
	Scene scene = new Scene(root);
	primaryStage.setScene(scene);
	primaryStage.show();
}
	public static void main(String[] args) {
		launch(args);
	}
}
