package jeudesFourmis.vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Buttons extends VBox{
	
	//Initialisation des attributs
	public static final int VGROW = 15;
	public static final int HGROW = 5;
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
		VBox bottomLeftArea = new VBox(HGROW);
		VBox bottomRightArea = new VBox(HGROW);
		VBox text = new VBox(VGROW);
		
		HBox bottomArea = new HBox(HGROW);
		HBox bottomRightBottomArea  = new HBox(VGROW);
		HBox textAreaF = new HBox(HGROW);
		HBox textAreaM = new HBox(HGROW);
		HBox textAreaG = new HBox(HGROW);
		
		GridPane buttonsPane = new GridPane();
		GridPane buttonsPane2 = new GridPane();
		
		Button play_pause = new Button("Play");
		Button reset = new Button("Reset");
		Button init = new Button("Init");
		Button changexyPlateau = new Button("Changer la taille du plateau ");
		
		Label nbF = new Label("Nombre de fourmis : ");
		Label nbM = new Label("Nombre de murs : ");
		Label nbG = new Label("Nombre de graines max par cases : ");
		
		TextField nbfT = new TextField();
		TextField nbmT = new TextField();
		TextField nbgT = new TextField();
		TextField plateau = new TextField();
		
		//Styles 
        reset.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-wrap-text : true;");
        play_pause.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        init.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-wrap-text : true;");
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
		buttonsPane.add(play_pause, 1,0);
		buttonsPane.add(reset, 1,1);
		
		buttonsPane2.add(init, 0, 1);
		buttonsPane2.add(textAreaF, 1, 2);
		buttonsPane2.add(textAreaM, 1, 3);
		buttonsPane2.add(textAreaG, 1, 4);
		
		buttonsPane.add(new Spring(), 2, 0);
		buttonsPane2.add(new Spring(), 2, 0);
		
		text.getChildren().addAll(buttonsPane2);
		bottomLeftArea.getChildren().addAll(buttonsPane,text);
		bottomRightArea.getChildren().addAll(new Spring(),  bottomRightBottomArea);
		bottomArea.getChildren().addAll(bottomLeftArea, new Spring(), bottomRightArea);
		
		plateau.setDisable(false);
		
		//On force le fait que les textfields ne prennent que des int à l'intérieur
		nbfT.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue.matches("\\d*")) return;
		    nbfT.setText(newValue.replaceAll("[^\\d]", ""));
		});
		nbgT.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue.matches("\\d*")) return;
		    nbgT.setText(newValue.replaceAll("[^\\d]", ""));
		});
		nbmT.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue.matches("\\d*")) return;
		    nbmT.setText(newValue.replaceAll("[^\\d]", ""));
		});
		plateau.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue.matches("\\d*")) return;
		    plateau.setText(newValue.replaceAll("[^\\d]", ""));
		});
		
		this.play_pause = play_pause;
		this.reset= reset;	
		this.init = init;
		this.changexyPlateau = changexyPlateau;
		this.size = plateau;
		this.nbfT = nbfT;;
		this.nbgT = nbgT;
		this.nbmT = nbmT;
		this.getChildren().add(bottomArea);
		this.setAlignment(Pos.CENTER);
	}
	
	//désactive les boutons
	public void disableButtonsAll()
	{
		this.getReset().setDisable(true);
		this.getInit().setDisable(true);
		this.getChangexyPlateau().setDisable(true);
	}
		
	//active les boutons
	public void enableButtonsAll()
	{
		this.getReset().setDisable(false);
		this.getInit().setDisable(false);
		this.getChangexyPlateau().setDisable(false);
	}
	
	public Button getPlay_pause() {
		return play_pause;
	}

	public Button getReset() {
		return reset;
	}

	public Button getInit() {
		return init;
	}

	public Button getChangexyPlateau() {
		return changexyPlateau;
	}

	public TextField getSize() {
		return size;
	}
	
	public TextField getNbfT() {
		return nbfT;
	}

	public TextField getNbmT() {
		return nbmT;
	}

	public TextField getNbgT() {
		return nbgT;
	}
}
