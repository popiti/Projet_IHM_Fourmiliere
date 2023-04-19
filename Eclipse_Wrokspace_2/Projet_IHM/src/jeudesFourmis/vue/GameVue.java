package jeudesFourmis.vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jeudesFourmis.model.Fourmiliere;

public class GameVue extends VBox {
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	public static final int RADIUS = 5;
	public static final int PANE_WIDTH = 500;
	public static final int PANE_HEIGHT = 500;
	
	private Button quit;
	private Button loupe;
	private Button play_pause;
	private Button reset;
	private Grille panneau;
	
	public GameVue(Stage primaryStage) {
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
		bottomRightTopArea.setAlignment(Pos.CENTER);
		bottomRightBottomArea.setAlignment(Pos.CENTER);
		buttonsPane.setVgap(HGROW);
		buttonsPane.setHgap(VGROW);
		root.setPadding(new Insets(10));
	    play_pause.setMaxWidth(Double.MAX_VALUE);
		root.setPrefSize(panneau.getWidth(), panneau.getHeight());
					//root2.setPrefSize(panneau.getWidth(), panneau.getHeight());
		
		this.setLoupe(loupe);
		this.setQuit(quit);
		this.setPlay_pause(play_pause);
		this.setReset(reset);	
		this.setPanneau(panneau);
		
		// Emboitement des panneaux/boutons
		buttonsPane.add(loupe, 0, 0 );
		buttonsPane.add(play_pause, 0, 1);
		buttonsPane.add(new Spring(), 2, 0);
		bottomLeftArea.getChildren().addAll(buttonsPane);
		bottomRightTopArea.getChildren().add(reset);
		bottomRightBottomArea.getChildren().add(quit);
		bottomRightArea.getChildren().addAll(bottomRightTopArea, new Spring(),  bottomRightBottomArea);
		bottomArea.getChildren().addAll(bottomLeftArea, new Spring(), bottomRightArea);
					//panneau.getChildren().add(cercle);
		root.getChildren().addAll(panneau, new Spring(), bottomArea);
		this.getChildren().add(root);
	}

	public Button getQuit() {
		return quit;
	}

	public void setQuit(Button quit) {
		this.quit = quit;
	}

	public Button getLoupe() {
		return loupe;
	}

	public void setLoupe(Button loupe) {
		this.loupe = loupe;
	}

	public Button getPlay_pause() {
		return play_pause;
	}

	public void setPlay_pause(Button play_pause) {
		this.play_pause = play_pause;
	}

	public Button getReset() {
		return reset;
	}

	public void setReset(Button reset) {
		this.reset = reset;
	}

	public Grille getPanneau() {
		return panneau;
	}

	public void setPanneau(Grille panneau) {
		this.panneau = panneau;
	}
	
	
	
}