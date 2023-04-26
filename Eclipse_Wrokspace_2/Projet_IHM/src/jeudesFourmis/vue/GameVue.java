package jeudesFourmis.vue;

import jeudesFourmis.vue.GameController;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jeudesFourmis.model.Fourmiliere;

public class GameVue extends BorderPane {
	
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	public static final int RADIUS = 5;
	public static final int PANE_WIDTH = 1500;
	public static final int PANE_HEIGHT = 500;
	
	private Grille panneau;
	private Infos infos = new Infos();
	private GameController func = new GameController();
	private Buttons boutton;
	private Label nbFourmi;
	private Label nbGraines;
	private Label nbMurs;
	private Slider slider;
	
	public GameVue(Fourmiliere f) {
		
		//super();
 		this.setPadding(new Insets(10));

 		// Création des objets
		Grille panneau = new Grille(f);
		this.panneau = panneau;
		Buttons b = new Buttons();
		this.slider = new Slider(100, 1000, 100);
		Label sliderL = new Label("Vitesse de simulation");
		HBox topPane = new HBox(HGROW);
		VBox leftPane = new VBox();
		VBox rightPane = new VBox();
		VBox bottomPane = new VBox();
		QuitButton quit = new QuitButton();
		// Styles
		sliderL.setFont(new Font("Arial", 22));
		
		// Alignements, etc...
		this.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
		rightPane.setAlignment(Pos.CENTER);
		topPane.setAlignment(Pos.CENTER);
		b.setAlignment(Pos.CENTER);
		bottomPane.setAlignment(Pos.BOTTOM_RIGHT);
		this.setMargin(b, new Insets(0, 0, 0, 50));
		this.setMargin(topPane, new Insets(0, 0, 30, 0));
		this.setCenter(b);
		this.setRight(rightPane);
		this.setLeft(leftPane);
		this.setTop(topPane);
		this.setBottom(bottomPane);
		
		// Emboitement des panneaux/boutons
		rightPane.getChildren().addAll(infos.getIterationsL(),infos.getGrainesL(),infos.getFourmisL());
		leftPane.getChildren().addAll(panneau);
		topPane.getChildren().addAll(sliderL,this.slider);
		bottomPane.getChildren().add(quit);
		b.getSize().setText(""+f.getLargeurProperty().getValue()+"");
		this.setBoutton(b);
		//this.setPanneau(panneau);
	}

	public Infos getInfos()
	{
		return this.infos;
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
					this.getPanneau().addFourmi(col, row,f);
				}
			}
	    }
	}

	public Buttons getBoutton() {
		return boutton;
	}

	public void setBoutton(Buttons boutton) {
		this.boutton = boutton;
	}
	
	public double getMinSlider() {
		return this.slider.getMin();
	}
	
	public double getMaxSlider() {
		return this.slider.getMax();
	}
	
	public double getValueSlider() {
		return this.slider.getValue();
	}
	
	public Slider getSlider()
	{
		return this.slider;
	}
}