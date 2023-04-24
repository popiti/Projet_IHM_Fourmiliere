package jeudesFourmis.vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Buttons extends VBox{
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	private Button quit;
	private Button loupe;
	private Button play_pause;
	private Button reset;
	private Button init;
	private Button changexyPlateau;
	private TextField size;
	private TextField nbfT;
	private TextField nbmT;
	private TextField nbgT;
	
	public Buttons()
	{
		//Creation des objets
		HBox bottomArea = new HBox(HGROW);
		VBox bottomLeftArea = new VBox(HGROW);
		VBox bottomRightArea = new VBox(HGROW);
		HBox bottomRightTopArea  = new HBox(VGROW);
		HBox bottomRightBottomArea  = new HBox(VGROW);
		VBox text = new VBox(VGROW);
		HBox textAreaF = new HBox(HGROW);
		HBox textAreaM = new HBox(HGROW);
		HBox textAreaG = new HBox(HGROW);
		GridPane buttonsPane = new GridPane();
		GridPane buttonsPane2 = new GridPane();
		Button quit = new Button("Quit");
		Button loupe = new Button("loupe");
		Button play_pause = new Button("Play");
		Button reset = new Button("Reset");
		Button init = new Button("Initialiser");
		Button changexyPlateau = new Button("Changer la taille du plateau ");
		TextField plateau = new TextField("AAA");
		Label nbF = new Label("Nombre de fourmis : ");
		Label nbM = new Label("Nombre de murs : ");
		Label nbG = new Label("Nombre de graines : ");
		TextField nbfT = new TextField();
		TextField nbmT = new TextField();
		TextField nbgT = new TextField();
		
		//Styles 
		quit.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        reset.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        loupe.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        play_pause.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        init.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        changexyPlateau.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        
		// Alignements, etc...
		bottomRightArea.setAlignment(Pos.CENTER_RIGHT);
		bottomLeftArea.setAlignment(Pos.CENTER_RIGHT);
		buttonsPane.setVgap(HGROW);
		buttonsPane.setHgap(VGROW);
		buttonsPane2.setVgap(HGROW);
		buttonsPane2.setHgap(VGROW);
	    play_pause.setMaxWidth(Double.MAX_VALUE);
	    
	    //Emboitements des boutons
	    textAreaF.getChildren().addAll(nbF,nbfT);
		textAreaM.getChildren().addAll(nbM,nbmT);
		textAreaG.getChildren().addAll(nbG,nbgT);
	    buttonsPane.add(changexyPlateau, 0, 0 );
	    buttonsPane.add(plateau, 0, 1 );
	    buttonsPane.add(loupe, 0, 2 );
		buttonsPane.add(play_pause, 1,2);
		buttonsPane2.add(init, 0, 1);
		buttonsPane2.add(textAreaF, 1, 2);
		buttonsPane2.add(textAreaM, 1, 3);
		buttonsPane2.add(textAreaG, 1, 4);
		buttonsPane.add(new Spring(), 2, 0);
		buttonsPane2.add(new Spring(), 2, 0);
		text.getChildren().addAll(buttonsPane2);
		bottomLeftArea.getChildren().addAll(buttonsPane,text);
		bottomRightTopArea.getChildren().add(reset);
		bottomRightBottomArea.getChildren().add(quit);
		bottomRightArea.getChildren().addAll(bottomRightTopArea, new Spring(),  bottomRightBottomArea);
		bottomArea.getChildren().addAll(bottomLeftArea, new Spring(), bottomRightArea);
		
		plateau.setDisable(false);
		this.setLoupe(loupe);
		this.setQuit(quit);
		this.setPlay_pause(play_pause);
		this.setReset(reset);	
		this.setInit(init);
		this.setChangexyPlateau(changexyPlateau);
		this.setSize(plateau);
		this.setNbfT(nbfT);
		this.setNbgT(nbgT);
		this.setNbmT(nbmT);
		this.getChildren().add(bottomArea);
		this.setAlignment(Pos.BOTTOM_RIGHT);
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
	public Button getInit() {
		return init;
	}
	public void setInit(Button init) {
		this.init = init;
	}
	public Button getChangexyPlateau() {
		return changexyPlateau;
	}
	public void setChangexyPlateau(Button changexyPlateau) {
		this.changexyPlateau = changexyPlateau;
	}
	public TextField getSize() {
		return size;
	}
	public void setSize(TextField size) {
		this.size = size;
	}
	public TextField getNbfT() {
		return nbfT;
	}
	public void setNbfT(TextField nbfT) {
		this.nbfT = nbfT;
	}
	public TextField getNbmT() {
		return nbmT;
	}
	public void setNbmT(TextField nbmT) {
		this.nbmT = nbmT;
	}
	public TextField getNbgT() {
		return nbgT;
	}
	public void setNbgT(TextField nbgT) {
		this.nbgT = nbgT;
	}
}
