package jeudesFourmis.vue;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import jeudesFourmis.model.*;

public class Grille extends GridPane  {
	
	private int PANE_WIDTH = 20;
	private int PANE_HEIGHT = 20;
	public static final int RADIUS = 5;
	private GameController func = new GameController();
	private Rectangle cases[][] = new Rectangle[PANE_WIDTH+2][PANE_HEIGHT+2];
	private List fourmis = new ArrayList<Circle>();

	public Grille(Fourmiliere f)
	{
		super();
		this.setAlignment(Pos.CENTER);
	//	this.setStyle("-fx-border-color: black;");
		this.setGridLinesVisible(true);
		int width = f.getLargeurProperty().getValue();
		int height = f.getHauteurProperty().getValue();
		
	    for (int row = 0; row < f.getLargeurProperty().getValue()+2; row++) 
	    {
			for (int col = 0; col < f.getHauteurProperty().getValue()+2; col++) {
				Rectangle rectmp = new Rectangle();
				rectmp.setWidth(20);
				rectmp.setHeight(20);
				
				if(f.getMur(row, col))
				{
					rectmp.setFill(Color.BLACK);
				}
				else if (f.getQteGraines(row, col)>0)
				{
					double ratio = (double) (f.getQteGraines(row, col))/(f.getQmaxProperty().getValue());
					Color c = Color.rgb(255,0,0,ratio);
					rectmp.setFill(c);
				}
				else rectmp.setFill(Color.WHITE);
				rectmp.setStroke(Color.BLACK);
				rectmp.setStrokeWidth(0.5);
				GridPane.setRowIndex(rectmp, row);
				GridPane.setColumnIndex(rectmp, col);
				if(!f.getMur(row, col))
				{
					rectmp.setOnMouseClicked(e->
					{
						if(e.isShiftDown())
	                    {
							this.addFourmi(this.getColumnIndex(rectmp),  this.getRowIndex(rectmp));
							f.ajouteFourmi(this.getRowIndex(rectmp),  this.getColumnIndex(rectmp));
	                    }
						else 
						{
							if(!f.getMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								rectmp.setFill(Color.BLACK);
								f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), true);
							}
							else 
							{
								if (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp))>0)
								{
									double ratio = (double) (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))/(f.getQmaxProperty().getValue());
									Color c = Color.rgb(255,0,0,ratio);
									rectmp.setFill(c);
								}
								else 
								{
									rectmp.setFill(Color.WHITE);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
								}
							}
						}
					});
					rectmp.setOnDragDetected(e->
					{
						if(f.getMur(getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								rectmp.setFill(Color.WHITE);
								f.setMur(getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
							}
						e.consume();
						
					});
					rectmp.setOnDragDropped(e->
					{
						if(!f.getMur(getRowIndex(rectmp), this.getColumnIndex(rectmp)))
						{
							rectmp.setFill(Color.BLACK);
							f.setMur(getRowIndex(rectmp), this.getColumnIndex(rectmp), true);
						}
						e.consume();
					});
				}
				this.getChildren().add(rectmp);
				this.cases[col][row]=rectmp;
	        }
	    }
	    
	  //  this.func.setStage(stage);
	}
	
	/*public void pressed(MouseEvent e) {
    	for( Node node: this.getChildren()) {

            if( node instanceof Rectangle) {
                if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                    int rowIndex = this.getRowIndex( node);
                    int colIndex = this.getColumnIndex(node);

                    Circle cercle = new Circle(RADIUS);

                    cercle.setCenterY(180);
                    cercle.setLayoutX(100);
                    System.out.println( "Node: " + node + " at " + this.getRowIndex( node) + "/" + this.getColumnIndex(node));
                    this.addFourmi(cercle,this.getColumnIndex( node),  this.getRowIndex(node));
                }
            }
        }
	}*/
	
	/*public Grille(int x,int y)
	{
		Fourmiliere f = new Fourmiliere(PANE_WIDTH,PANE_HEIGHT, 4);
		int width = x;
		int height = y;
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
				this.getChildren().add(rec);
	        }
	    }
	}
*/
	public void addFourmi(int col,int row)
	{
		Circle cercle = new Circle(RADIUS);
		cercle.setStyle("-fx-fill: green; -fx-stroke: black;");
		cercle.setRadius(5);
	    this.add(cercle,col,row);
	    this.fourmis.add(cercle);
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

	/*public void getIndex(int rowIndex, int colIndex) {
		System.out.println(rowIndex + "/"+colIndex);
		Circle cercle = new Circle(RADIUS);
		cercle .setStyle("-fx-fill: green; -fx-stroke: black;");
	    this.addFourmi(rowIndex,colIndex);
	}
	*/
	public Rectangle getCases(int x, int y) {
        if(x < 0 || x >= PANE_HEIGHT+2 || y < 0 || y >= PANE_WIDTH+2)
            return null;
            
        return this.cases[y][x];
    }

	public List<Circle> getFourmisList() {
		return this.fourmis;
	}
}
