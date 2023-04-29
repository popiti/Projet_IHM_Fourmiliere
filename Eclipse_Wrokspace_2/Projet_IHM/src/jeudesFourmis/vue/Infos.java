package jeudesFourmis.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Infos extends HBox {
	
	private Label graines = new Label();
	private Label fourmis = new Label();
	private Label iterations = new Label();

	public Infos()
	{	
		this.graines.setFont(new Font("Arial", 16));
		this.fourmis.setFont(new Font("Arial", 16));
		this.iterations.setFont(new Font("Arial", 16));
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
