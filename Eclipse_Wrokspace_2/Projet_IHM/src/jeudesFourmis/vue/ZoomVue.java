package jeudesFourmis.vue;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jeudesFourmis.model.Fourmiliere;

public class ZoomVue extends BorderPane {	
	private Grille panneau;
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	public static final int RADIUS = 5;
	public static final int PANE_WIDTH = 330;
	public static final int PANE_HEIGHT = 330;
	public ZoomVue(Fourmiliere f) {
		
 		this.setPadding(new Insets(10));

 		// Création des objets
 		Grille panneau = new Grille(f,f.getxNow(),f.getyNow());
		this.panneau = panneau;
		VBox leftPane = new VBox();

		// Alignements, etc...
		this.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
		this.setLeft(leftPane);
		
		// Emboitement des panneaux/boutons
		leftPane.getChildren().addAll(panneau);
	}
	
	//Permet de faire appel aux deux fonctions de synchronisation
	public void synchroniser2(Fourmiliere f)
	{
		this.graineSynchro2(f);
		this.fourmiSynchro2(f);
	}
	
	/* Elle fait la meme chose que la fonction "graineSynchro" dans la classe GameVue à l'exception qu'on modifie les fonctions "getCases" && "getMur" && "getQteGraines"
	 * qui prennent en plus dans leur paramètre le minw ou le minh 
	 * Les variables locales w et h prennent les valeurs envoyés par les textfields
	*/
	public void graineSynchro2(Fourmiliere f)
	{
		// On fait appel à la meme logique employé dans le 2eme constructeur de la classe Grille pour que ce soit cohérent
		int w=f.getxNow();
		int h=f.getyNow();
		int minw,maxw;
		int minh,maxh;
		if (w<5)
		{
			minw=0;
			maxw=10;
		}
		else if (f.getLargeurProperty().getValue()-w<5)
		{
			minw=f.getLargeurProperty().getValue()-10;
			maxw=f.getLargeurProperty().getValue();	
		}
		else 
		{
			minw=w-5;
			maxw=w+5;
		}
		if (h<5)
		{
			minh=0;
			maxh=10;
		}
		else if (f.getHauteurProperty().getValue()-h<5)
		{
			minh=f.getHauteurProperty().getValue()-10;
			maxh=f.getHauteurProperty().getValue();
		}
		else {
			minh=h-5;
			maxh=h+5;
		}
		for (int row = 0; row < 11; row++) 
	    {
			for (int col = 0; col < 11; col++) {
				if(f.getMur(row+minw, col+minh))
				{
					this.getPanneau().getCases(row+minw, col+minh).setFill(Color.BLACK);
				}
				else if(f.getQteGraines(row+minw, col+minh)==0)
				{
					this.getPanneau().getCases(row+minw, col+minh).setFill(Color.WHITE);
				}
				else if (f.getQteGraines(row+minw, col+minh)>0)
				{
					double ratio = (double) (f.getQteGraines(row+minw, col+minh))/(f.getQmaxProperty().getValue());
					Color c = Color.rgb(255,0,0,ratio);
					this.getPanneau().getCases(row+minw, col+minh).setFill(c);
				}
				else this.getPanneau().getCases(row+minw, col+minh).setFill(Color.WHITE);
			}
	    }
	}
	
	/* Elle fait la meme chose que la fonction "fourmiSynchro" dans la classe GameVue à l'exception qu'on modifie les fonctions "contientFourmi" && "addFourmi"
	 * qui prennent en plus dans leur paramètre le minw ou le minh 
	 * Les variables locales w et h prennent les valeurs envoyés par les textFields
	*/
	public void fourmiSynchro2(Fourmiliere f)
	{
		// On fait appel à la meme logique employé dans le 2eme constructeur de la classe Grille pour que ce soit cohérent
		int w=f.getxNow();
		int h=f.getyNow();
		int minw,maxw;
		int minh,maxh;
		if (w<5)
		{
			minw=0;
			maxw=10;
		}
		else if (f.getLargeurProperty().getValue()-w<5)
		{
			minw=f.getLargeurProperty().getValue()-10;
			maxw=f.getLargeurProperty().getValue();
			
		}
		else {
			minw=w-5;
			maxw=w+5;
		}
		if (h<5)
		{
			minh=0;
			maxh=10;
		}
		else if (f.getHauteurProperty().getValue()-h<5)
		{
			minh=f.getHauteurProperty().getValue()-10;
			maxh=f.getHauteurProperty().getValue();
		}
		else {
			minh=h-5;
			maxh=h+5;
		}
		for(Circle cercle : this.getPanneau().getFourmisList())
		{
			this.getPanneau().getChildren().remove(cercle);
		}
		for (int row = 0; row < 11; row++) 
	    {
			for (int col = 0; col < 11; col++) {
				if(f.contientFourmi(row+minw, col+minh))
				{
					this.getPanneau().addFourmi(col+minh, row+minw,15,f);
				}
			}
	    }
	}
	
	public Grille getPanneau() {
		return panneau;
	}

	public void setPanneau(Grille panneau) {
		this.panneau = panneau;
	}
}