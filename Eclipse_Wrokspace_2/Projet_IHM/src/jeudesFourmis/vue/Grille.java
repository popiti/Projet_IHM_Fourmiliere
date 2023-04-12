package jeudesFourmis.vue;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jeudesFourmis.model.*;

public class Grille extends GridPane  {
	
	private int PANE_WIDTH = 40;
	private int PANE_HEIGHT = 40;
	public static final int RADIUS = 5;
	public Grille()
	{
		Fourmiliere f = new Fourmiliere(PANE_WIDTH,PANE_HEIGHT, 4);
		int width = this.PANE_WIDTH;
		int height = this.PANE_HEIGHT;
		Circle cercle = new Circle(RADIUS);
	
		cercle.setCenterY(180);
		cercle.setLayoutX(100);
		System.out.println(cercle.getLayoutX() + " // "+cercle.getLayoutY());
	    
	    for (int row = 0; row < width; row++) 
	    {
			for (int col = 0; col < height; col++) {
				Rectangle rec = new Rectangle();
				rec.setWidth(10);
				rec.setHeight(10);
				rec.setFill(Color.WHITE);
				rec.setStroke(Color.BLACK);
				rec.setStrokeWidth(0.5);
				GridPane.setRowIndex(rec, row);
				GridPane.setColumnIndex(rec, col);
				if ((row == 20)&& (col==25))
				{
				    this.getChildren().addAll(rec,cercle);
				}
				else this.getChildren().add(rec);
	        }
	    }
	}

	public int getPANE_WIDTH() {
		return PANE_WIDTH*10;
	}

	public void setPANE_WIDTH(int pANE_WIDTH) {
		PANE_WIDTH = pANE_WIDTH;
	}

	public int getPANE_HEIGHT() {
		return PANE_HEIGHT*10;
	}

	public void setPANE_HEIGHT(int pANE_HEIGHT) {
		PANE_HEIGHT = pANE_HEIGHT;
	}
}
