package jeudesFourmis.vue;

import java.util.ArrayList;
import java.util.List;

import Exceptions.OtherExceptions;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import jeudesFourmis.model.*;

public class Grille extends GridPane  {
	
	private int PANE_WIDTH = 20;
	private int PANE_HEIGHT = 20;
	public static final int TAILLE_DEFAULT = 50;
    public static final int TAILLE_MIN = 20;
	private int oldX;
	private int oldY;
	public static final double RADIUS = 3.5;
	private GameController func = new GameController();
	private Rectangle cases[][] = new Rectangle[PANE_WIDTH+2][PANE_HEIGHT+2];
	private List fourmis = new ArrayList<Circle>();

	public Grille(Fourmiliere f)
	{
		//super();
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(true);
		this.PANE_WIDTH = TAILLE_DEFAULT;
		this.PANE_HEIGHT = TAILLE_DEFAULT;
		Rectangle casestmp[][] = new Rectangle[PANE_WIDTH+2][PANE_HEIGHT+2];
		this.cases = casestmp;
		
	    for (int row = 0; row < TAILLE_DEFAULT+2; row++) 
	    {
			for (int col = 0; col < TAILLE_DEFAULT+2; col++) {
				Rectangle rectmp = new Rectangle();
				rectmp.setWidth(10);
				rectmp.setHeight(10);
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
				GridPane.setRowIndex(rectmp, row);
				GridPane.setColumnIndex(rectmp, col);
				if(!f.getMur(row, col))
				{
					rectmp.setOnMousePressed(e->
					{
						if( (e.isShiftDown()) )
	                    {
							if (!f.getMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								this.addFourmi(this.getColumnIndex(rectmp),  this.getRowIndex(rectmp),f);
								f.ajouteFourmi(this.getRowIndex(rectmp),  this.getColumnIndex(rectmp));
							}
	                    }
						else 
						{
							if(!f.getMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								if(!f.contientFourmi(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
								{	
									rectmp.setFill(Color.BLACK);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), true);
								}
							}
							else 
							{
								if (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp))>0)
								{
									double ratio = (double) (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))/(f.getQmaxProperty().getValue());
									Color c = Color.rgb(255,0,0,ratio);
									rectmp.setFill(c);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
								}
								else 
								{
									rectmp.setFill(Color.WHITE);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
								}
							}
						}
					});
				}
				this.getChildren().add(rectmp);
				this.cases[col][row]=rectmp;
	        }
	    }
	    this.setOnMouseDragged(e->
		{
			try {
				
			double xs=e.getX()/11; // 10 pour le carré et 0.5 1er stroke haut bas et 0.5 pour gauche droite dépendemment de notre orientation
			double ys=e.getY()/11; // taille carré 10 px + 0,5*2 pour les stroke 
			
			if((xs > this.PANE_WIDTH) || (ys > this.PANE_HEIGHT) || (xs <= 0) || (ys <= 0) )
			{
				
				throw new OtherExceptions();
				
			}
			else if(!f.getMur((int)ys, (int)xs) && (!e.isShiftDown() && (!f.contientFourmi((int)ys,(int)xs))))
	            {
	                    f.setMur((int)ys, (int)xs, true);
	                    this.changeColor((int)ys,(int)xs);
	            }
			}catch(OtherExceptions exception)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR OUT OF BOUND");
				alert.setContentText(OtherExceptions.NOT_DRAGGABLE);
				alert.show();
			}
		});
	    
	}
	
	public void addFourmi(int col,int row, Fourmiliere f)
	{
		Circle cercle = new Circle(RADIUS);
		if(f.contientFourmi(row, col))
		{
			if(f.getFourmi(row, col).porte())
			{
				cercle.setStyle("-fx-fill: blue; -fx-stroke: black;");
			}
			else 
			{
				cercle.setStyle("-fx-fill: green; -fx-stroke: black;");
			}
			
		}
		else 
		{
			cercle.setStyle("-fx-fill: green; -fx-stroke: black;");
		}
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

	public Rectangle getCases(int x, int y) {
        if(x < 0 || x >= PANE_HEIGHT+2 || y < 0 || y >= PANE_WIDTH+2)
            return null;
            
        return this.cases[y][x];
    }
	
	public List<Circle> getFourmisList() {
		return this.fourmis;
	}
	
	public void changeColor(int xs, int ys) {
        this.cases[ys][xs].setFill(Color.BLACK);

    }
	public int getOldX() {
		return oldX;
	}

	public void setOldX(int oldX) {
		this.oldX = oldX;
	}

	public int getOldY() {
		return oldY;
	}

	public void setOldY(int oldY) {
		this.oldY = oldY;
	}
	
	public void disableStroke()
	{
		for (int row = 1; row < this.PANE_WIDTH+1; row++)
		{
			for (int col = 1; col < this.PANE_HEIGHT+1; col++)
			{
				Rectangle r = this.cases[row][col];
				r.setStroke(Color.WHITE);
			}
		}
	}
	
	public void enableStroke()
	{
		for (int row = 1; row < this.PANE_WIDTH+1; row++)
		{
			for (int col = 1; col < this.PANE_HEIGHT+1; col++)
			{
				Rectangle r = this.cases[row][col];
				r.setStroke(Color.BLACK);
			}
		}
	}
	
	public void changeGrille(Fourmiliere f)
	{
		this.getChildren().clear();
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(true);
		this.PANE_WIDTH = f.getLargeurProperty().getValue();
		this.PANE_HEIGHT = f.getHauteurProperty().getValue();
		Rectangle casestmp[][] = new Rectangle[PANE_WIDTH+2][PANE_HEIGHT+2];
		this.cases = casestmp;
		int width = f.getLargeurProperty().getValue();
		int height = f.getHauteurProperty().getValue();
		
	    for (int row = 0; row < f.getLargeurProperty().getValue()+2; row++) 
	    {
			for (int col = 0; col < f.getHauteurProperty().getValue()+2; col++) {
				Rectangle rectmp = new Rectangle();
				rectmp.setWidth(10);
				rectmp.setHeight(10);
				rectmp.setStroke(Color.BLACK);
				rectmp.setStrokeWidth(0.5);
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
				GridPane.setRowIndex(rectmp, row);
				GridPane.setColumnIndex(rectmp, col);
				if(!f.getMur(row, col))
				{
					rectmp.setOnMousePressed(e->
					{
						if( (e.isShiftDown()) )
	                    {
							if (!f.getMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								this.addFourmi(this.getColumnIndex(rectmp),  this.getRowIndex(rectmp),f);
								f.ajouteFourmi(this.getRowIndex(rectmp),  this.getColumnIndex(rectmp));
							}
	                    }
						else 
						{
							if(!f.getMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								if(!f.contientFourmi(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
								{	
									rectmp.setFill(Color.BLACK);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), true);
								}
							}
							else 
							{
								if (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp))>0)
								{
									double ratio = (double) (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))/(f.getQmaxProperty().getValue());
									Color c = Color.rgb(255,0,0,ratio);
									rectmp.setFill(c);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
								}
								else 
								{
									rectmp.setFill(Color.WHITE);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
								}
							}
						}
					});

				}
				 this.setOnMouseDragged(e->
					{
						
						try {
							
							double xs=e.getX()/11; // 10 pour le carré et 0.5 1er stroke haut bas et 0.5 pour gauche droite dépendemment de notre orientation
							double ys=e.getY()/11; // taille carré 10 px + 0,5*2 pour les stroke 
							
							if(!f.getMur((int)ys, (int)xs) && (!e.isShiftDown() && (!f.contientFourmi((int)ys,(int)xs))))
					            {
					                    f.setMur((int)ys, (int)xs, true);
					                    this.changeColor((int)ys,(int)xs);
					            }
							else if((xs > PANE_HEIGHT) || (ys > PANE_WIDTH) || (xs <= 0) || (ys <= 0) )
							{
								
								throw new OtherExceptions();
								
							}
							}catch(OtherExceptions exception)
							{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Error out of bound");
								alert.setContentText(OtherExceptions.NOT_DRAGGABLE);
								alert.show();
							}
					});
				this.getChildren().add(rectmp);
				this.cases[col][row]=rectmp;
	        }
	    }
	}
}
