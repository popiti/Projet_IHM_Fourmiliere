package jeudesFourmis.vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import jeudesFourmis.model.Fourmiliere;

public class GameVue extends BorderPane {
	
	//Initialisation des attributs
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	public static final int RADIUS = 5;
	public static final int PANE_WIDTH = 1500;
	public static final int PANE_HEIGHT = 500;
	
	private Grille panneau;
	private Infos infos = new Infos();
	private Buttons boutton;
	private Slider slider;
	
	public GameVue(Fourmiliere f) {
		
		//Insets
 		this.setPadding(new Insets(10));

 		// Création des objets
		Grille panneau = new Grille(f);
		this.panneau = panneau;
		Buttons b = new Buttons();
		this.slider = new Slider(100, 1000, 100);
		Label sliderL = new Label("Vitesse de simulation");
		HBox topPane = new HBox(HGROW);
		VBox leftPane = new VBox();
		VBox rightPane = new VBox(VGROW);
		VBox bottomPane = new VBox();
		QuitButton quit = new QuitButton();
		Loupe l = new Loupe(f);
		
		// Styles
		sliderL.setFont(new Font("Arial", 22));
		
		// Alignements, etc...
		this.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
		rightPane.setAlignment(Pos.TOP_RIGHT);
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
		rightPane.getChildren().addAll(infos.getIterationsL(),infos.getGrainesL(),infos.getFourmisL(),l);
		leftPane.getChildren().addAll(panneau);
		topPane.getChildren().addAll(sliderL,this.slider);
		bottomPane.getChildren().add(quit);
		b.getSize().setText(""+f.getLargeurProperty().getValue()+"");
		this.setBoutton(b);
	}
	
	//Permet de faire appel aux deux fonctions de synchronisation
	public void synchroniser(Fourmiliere f)
	{
		this.graineSynchro(f);
		this.fourmiSynchro(f);
	}
	
	//Cette fonction met à jour les informations du GridPane à chaque itération, elle se synchronise au model
	public void graineSynchro(Fourmiliere f)
	{
		for (int row = 0; row < f.getLargeurProperty().getValue()+2; row++) 
	    {
			for (int col = 0; col < f.getHauteurProperty().getValue()+2; col++) {
		//Si il y a un ajout d'un mur, le met à jour également dans le GridPane
				if(f.getMur(row, col))
				{
					this.getPanneau().getCases(row, col).setFill(Color.BLACK);
				}
				else if(f.getQteGraines(row, col)==0)
				{
		//Si la quantité de graines dans une case est égale à zéro, le met à jour également dans le GridPane et modifie la couleur de la cellule en blanc pour indiquer qu'il n'y a plus rien
					this.getPanneau().getCases(row, col).setFill(Color.WHITE);
				}
				else if (f.getQteGraines(row, col)>0)
				{
		//Récupère la quantité de graine actuelle dans la cellule et la met à jour également dans le GridPane, elle modifie la couleur de la cellule en fonction de la quantité actuelle de la cellule
					double ratio = (double) (f.getQteGraines(row, col))/(f.getQmaxProperty().getValue());
					Color c = Color.rgb(255,0,0,ratio);
					this.getPanneau().getCases(row, col).setFill(c);
				}
		// S'il n'y a pas de changement, on retourne la case avec la couleur blanche
				else this.getPanneau().getCases(row, col).setFill(Color.WHITE);
			}
	    }
	}
	
//Cette fonction met à jour les informations du GridPane à chaque itération, elle suit les déplacements des fourmis à chaque itération dans la fourmiliere, elle se synchronise au model
	public void fourmiSynchro(Fourmiliere f)
	{
		/* (1) On récupére la liste des fourmis grace à la fonction " getFourmisList " dans la classe Grille 
		 *     Pour suivre le déplacement, on retire le cercle de la cellule actuelle
		 * (2) Et on vérifie si une fourmi a effectué un deplacement dans la simulation,on récupère la fourmi de la simulation avec la fonction "contientFourmi" dans la classe Fourmilière 
		 * 	   et ses coordonnées ensuite on la rajoute dans la nouvelle case avec la fonction " addFourmis " dans la classe GridPane 
		 */
		//(1)
		for(Circle cercle : this.getPanneau().getFourmisList())
		{
			this.getPanneau().getChildren().remove(cercle);
		}
		for (int row = 0; row < f.getLargeurProperty().getValue()+2; row++) 
	    {
			for (int col = 0; col < f.getHauteurProperty().getValue()+2; col++) {
				//(2)
				if(f.contientFourmi(row, col))
				{
					this.getPanneau().addFourmi(col, row,4.0,f);
				}
			}
	    }
	}

	public Infos getInfos()
	{
		return this.infos;
	}

	//Récupère la grille
	public Grille getPanneau() {
		return panneau;
	}

	public void setPanneau(Grille panneau) {
		this.panneau = panneau;
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