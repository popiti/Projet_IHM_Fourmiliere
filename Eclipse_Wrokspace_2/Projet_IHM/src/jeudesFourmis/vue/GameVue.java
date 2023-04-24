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

public class GameVue extends GridPane {
	
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	public static final int RADIUS = 5;
	public static final int PANE_WIDTH = 1000;
	public static final int PANE_HEIGHT = 1000;
	
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
		VBox root = new VBox();
		Grille panneau = new Grille(f);
		this.panneau = panneau;
		Buttons b = new Buttons();
		this.slider = new Slider(100, 1000, 100);
		// Styles
		
		
		// Alignements, etc...
		root.setPrefSize(panneau.getWidth(), panneau.getHeight());
		this.setPrefSize(PANE_HEIGHT, PANE_WIDTH);
		// Emboitement des panneaux/boutons
		
		b.getSize().setText(""+f.getLargeurProperty().getValue()+"");
		root.getChildren().addAll(this.slider,infos.getIterationsL(),infos.getGrainesL(),infos.getFourmisL(),panneau,new Spring(), b);
		this.setBoutton(b);
		//this.setPanneau(panneau);
		this.getChildren().addAll(root);
		
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
					this.getPanneau().addFourmi( col, row);
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