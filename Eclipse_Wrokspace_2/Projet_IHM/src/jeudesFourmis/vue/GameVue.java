package jeudesFourmis.vue;

import jeudesFourmis.vue.GameController;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jeudesFourmis.model.Fourmiliere;

public class GameVue extends GridPane {
	
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	public static final int RADIUS = 5;
	public static final int PANE_WIDTH = 1000;
	public static final int PANE_HEIGHT = 1000;
	
	private Button quit;
	private Button loupe;
	private Button play_pause;
	private Button reset;
	private Grille panneau;
	private Infos infos = new Infos();
	private GameController func = new GameController();
	public GameVue(Stage stage, Fourmiliere f) {
		
		super();
 		this.setPadding(new Insets(10));
		this.func.setStage(stage);
		// Création des objets
		VBox root = new VBox();
		HBox bottomArea = new HBox(HGROW);
		VBox bottomLeftArea = new VBox(HGROW);
		VBox bottomRightArea = new VBox(HGROW);
		HBox bottomRightTopArea  = new HBox(VGROW);
		HBox bottomRightBottomArea  = new HBox(VGROW);
		GridPane buttonsPane = new GridPane();
		GridPane buttonsPane2 = new GridPane();
		Grille panneau = new Grille(f);
		Circle cercle = new Circle();
		Rectangle rec = new Rectangle();
		Button quit = new Button("Quit");
		Button loupe = new Button("loupe");
		Button play_pause = new Button("Play");
		Button reset = new Button("Reset");
		
		// Styles
		quit.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		reset.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		loupe.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		play_pause.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		
		// Alignements, etc...
		bottomRightArea.setAlignment(Pos.CENTER_RIGHT);
		bottomLeftArea.setAlignment(Pos.CENTER_RIGHT);
		buttonsPane.setVgap(HGROW);
		buttonsPane.setHgap(VGROW);
	    play_pause.setMaxWidth(Double.MAX_VALUE);
		root.setPrefSize(panneau.getWidth(), panneau.getHeight());
		this.setPrefSize(PANE_HEIGHT, PANE_WIDTH);
		
		// Emboitement des panneaux/boutons
		buttonsPane.add(loupe, 0, 0 );
		buttonsPane.add(play_pause, 0, 1);
		buttonsPane.add(new Spring(), 2, 0);
		bottomLeftArea.getChildren().addAll(buttonsPane);
		bottomRightTopArea.getChildren().add(reset);
		bottomRightBottomArea.getChildren().add(quit);
		bottomRightArea.getChildren().addAll(bottomRightTopArea, new Spring(),  bottomRightBottomArea);
		bottomArea.getChildren().addAll(bottomLeftArea, new Spring(), bottomRightArea);

		root.getChildren().addAll(infos.getIterationsL(),infos.getGrainesL(),infos.getFourmisL(),panneau,new Spring(), bottomArea);
		this.setLoupe(loupe);
		this.setQuit(quit);
		this.setPlay_pause(play_pause);
		this.setReset(reset);	
		this.setPanneau(panneau);
		this.getChildren().addAll(root);
		/*super();
		this.func.setStage(stage);
		
		this.setAlignment(Pos.BOTTOM_RIGHT);
		this.setPadding(new Insets(10));
		
		Label titre = new Label("Gomoku");
		titre.setFont(new Font("Arial", 35));
	
		GridPane bottomArea = new GridPane();
		
		Button quit = new Button("QUIT");
		quit.setOnAction(e->{
			Platform.exit();
		});
		
		bottomArea.setAlignment(Pos.BOTTOM_RIGHT);
		bottomArea.getChildren().add(quit);
		
		this.getChildren().addAll(titre,bottomArea);*/
	}

	public Infos getInfos()
	{
		return this.infos;
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
	
	public void synchroniser(Fourmiliere f)
	{
		this.graineSynchro(f);
		this.fourmiSynchro(f);
	}
	
	public void graineSynchro(Fourmiliere f)
	{
		for (int row = 0; row < f.getLargeurProperty().getValue()+2; row++) 
	    {
			for (int col = 0; col < f.getHauteurProperty().getValue()+2; col++) {
				if(f.getMur(row, col))
				{
					this.getPanneau().getCases(row, col).setFill(Color.BLACK);
				}
				else if(f.getQteGraines(row, col)==0)
				{
					this.getPanneau().getCases(row, col).setFill(Color.WHITE);
				}
				else if (f.getQteGraines(row, col)>0)
				{
					double ratio = (double) (f.getQteGraines(row, col))/(f.getQmaxProperty().getValue());
					Color c = Color.rgb(255,0,0,ratio);
					this.getPanneau().getCases(row, col).setFill(c);
				}
				else this.getPanneau().getCases(row, col).setFill(Color.WHITE);
			}
	    }
	}
	
	public void fourmiSynchro(Fourmiliere f)
	{
		for(Circle cercle : this.getPanneau().getFourmisList())
		{
			this.getPanneau().getChildren().remove(cercle);
		}
		for (int row = 0; row < f.getLargeurProperty().getValue()+2; row++) 
	    {
			for (int col = 0; col < f.getHauteurProperty().getValue()+2; col++) {
				if(f.contientFourmi(row, col))
				{
					this.getPanneau().addFourmi( col, row);
				}
			}
	    }
	}
}