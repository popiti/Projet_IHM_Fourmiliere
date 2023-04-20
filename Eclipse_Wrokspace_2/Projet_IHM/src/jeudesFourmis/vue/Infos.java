package jeudesFourmis.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import jeudesFourmis.model.*;


public class Infos extends HBox {
	
	private Label graines = new Label();
	private Label fourmis = new Label();
	private Label iterations = new Label();

	public Infos()
	{	
		
	}
	
	public Label getGrainesL() {
		return graines;
	}

	public Label getFourmisL() {
		return fourmis;
	}

	public Label getIterationsL() {
		return this.iterations;
	}
	
	
	
}
