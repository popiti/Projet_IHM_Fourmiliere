package jeudesFourmis.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import jeudesFourmis.model.*;


public class Infos extends HBox {
	
	private Label graines = new Label();
	private Label fourmis = new Label();

	public Infos()
	{	
		
	}

	public Label getGraines() {
		return graines;
	}

	public void setGraines(Label graines) {
		this.graines = graines;
	}

	public Label getFourmis() {
		return fourmis;
	}

	public void setFourmis(Label fourmis) {
		this.fourmis = fourmis;
	}

}
