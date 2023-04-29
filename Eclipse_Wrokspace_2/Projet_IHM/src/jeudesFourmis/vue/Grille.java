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
	
	//Initialisation des attributs 
	private int PANE_WIDTH;;
	private int PANE_HEIGHT;
	public static final int TAILLE_DEFAULT = 50; 
    public static final int TAILLE_MIN = 20;
    public static final int TAILLE_MAX = 84;
	public static final double RADIUS = 3.5;
	private Rectangle cases[][] = new Rectangle[PANE_WIDTH+2][PANE_HEIGHT+2];
	private List fourmis = new ArrayList<Circle>();

	//Constructeur qui permet de créer la grille dans la vue 
	public Grille(Fourmiliere f)
	{
		this.PANE_WIDTH = TAILLE_DEFAULT;
		this.PANE_HEIGHT = TAILLE_DEFAULT;
		
		//Alignement,etc ..
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(true);
		
		//Creation d'objets
		Rectangle casestmp[][] = new Rectangle[PANE_WIDTH+2][PANE_HEIGHT+2];
		this.cases = casestmp;
		
		//Création des rectangles 
	    for (int row = 0; row < TAILLE_DEFAULT+2; row++) // TAILLE_DEFAULT + 2 signifie que je prend en compte le border du plateau et je le considère comme un mur
	    {
			for (int col = 0; col < TAILLE_DEFAULT+2; col++) {
				Rectangle rectmp = new Rectangle();
				rectmp.setWidth(10);
				rectmp.setHeight(10);
				if(f.getMur(row, col))
				{
					rectmp.setFill(Color.BLACK); /* Si c'est la bordure il remplie les cases par une couleur noire, on fait appel au mur qui a été initialisé dans le constructeur de la fourmilière 
												  *	puis on prend les coordonnées des murs pour les placer comme bordure du GridPane
												 */
				}
				else if (f.getQteGraines(row, col)>0)
				{
					/*	
					 *  Si une case contient une graine il la colorie en rouge.
					 *	On établie ensuite un ratio qui nous permettra de modifier la couleur de la case suivant le maximum de graines que peut prendre une case. C'est à dire si le maximum
					 * 	est de 3 et que la case acutelle contient 1 graine alors la couleur sera beaucoup plus clair et plus elle se rapproche du nombre maximum la couleur s'intensifie ( c'est le ratio
					 * 	qui fait ce calcul : il prend la quantité de graines présente dans la case actuelle et la divise par le nombre maximum de graines possibles par case )
				    */
					double ratio = (double) (f.getQteGraines(row, col))/(f.getQmaxProperty().getValue()); 
					Color c = Color.rgb(255,0,0,ratio);
					rectmp.setFill(c);
				}
				
				// Si c'est une case vide, il la colorie en blanc
				else rectmp.setFill(Color.WHITE);
				rectmp.setStroke(Color.BLACK);
				
				//Place les rectangles dans les cases associés 
				GridPane.setRowIndex(rectmp, row);
				GridPane.setColumnIndex(rectmp, col);
				
				//Actions sur le plateau de jeu
				if(!f.getMur(row, col))
				{
					/*
					 * 	Action sur le clic de la souris 
					 *	On fait d'abord la verification si il y a une fourmi présente dans la case actuelle si ce n'est pas le cas on peut ajouter un mur 	
					*/
					rectmp.setOnMousePressed(e->
					{
						/* 
						 *	Si le bouton shift est enfoncé, on place dans la grille la fourmi avec addFourmi et on enregistre aussi son placement dans la Fourmiliere avec f.ajouteFourmi
						 *	getColumnIndex et getRowIndex permette de récupérer les coordonnées de l'emplacement des cases actuelles dans le GridPane
						*/
						if( (e.isShiftDown()) )
	                    {
							if (!f.getMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								this.addFourmi(this.getColumnIndex(rectmp),  this.getRowIndex(rectmp),4.0,f);
								f.ajouteFourmi(this.getRowIndex(rectmp),  this.getColumnIndex(rectmp));
							}
	                    }
						
						/*	Avec un clic sur la case je peux rajouter un mur et avec un nouveau clic sur cette meme case je peux enlever le mur
						 * (1) Quand on veut placer un mur, on a le droit de le placer où l'on souhaite dans le GridPane 
						 * 	   à l'exception que la case n'est pas déjà occupé par une fourmi ou que ce ne soit pas déjà un mur.
						 * (2) On peut placer un mur dans une case qui contient une graine, et si l'on décide de retirer ce mur à ce meme emplacement, on retrouve encore une fois la graine, je ne supprime pas la graine de cet emplacement
						 * (3) Si la case ne contenait rien au départ et qu'on décide d'enlever le mur de cette case, cette case est remis à la couleur blance
						 * */
						else 
						{
							if(!f.getMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
							{
								//(1)
								if(!f.contientFourmi(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))
								{	
									rectmp.setFill(Color.BLACK);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), true);
								}
							}
							else 
							{
								//(2)
								if (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp))>0)
								{
									double ratio = (double) (f.getQteGraines(this.getRowIndex(rectmp), this.getColumnIndex(rectmp)))/(f.getQmaxProperty().getValue());
									Color c = Color.rgb(255,0,0,ratio);
									rectmp.setFill(c);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
								}
								else 
								{
									//(3)
									rectmp.setFill(Color.WHITE);
									f.setMur(this.getRowIndex(rectmp), this.getColumnIndex(rectmp), false);
								}
							}
						}
					});
				}
				// On ajoute les rectangles créés dans le GridPane et dans la matrice case
				this.getChildren().add(rectmp);
				this.cases[col][row]=rectmp;
	        }
	    }
	    //Action sur avec le drag de la souris 
	    this.setOnMouseDragged(e->
		{
			try {
				//On enregistre 2 valeurs qui vont variés en fonction de l'emplacement de la souris, ils vont nous servir à créer les murs.
			double xs=e.getX()/11; // 10 pour le carré et 0.5 1er stroke haut bas et 0.5 pour gauche droite dépendemment de notre orientation ce qui donne 10+0,5*2 = 11
			double ys=e.getY()/11;  
			
			//On renvoie une exception si l'utilisateur essaye de drag avec sa souris en dehors des bordures du plateau 
			if((xs > this.PANE_WIDTH) || (ys > this.PANE_HEIGHT) || (xs <= 0) || (ys <= 0) )
			{
				throw new OtherExceptions();	
			}
			
			/*
			 * On fait 3 verifications : d'abord si la case ne contient pas déjà un mur 
			 * si la touche shift n'est pas déjà enfoncé pour éviter tout probleme avec la création de fourmi 
			 * et enfin que la case ne contient pas déjà une fourmi
			 * On fait appel à la fonction " changeColor " qui accède à la case sur l'emplacement exact de la souris ( on cast les variables "xs" et "ys" pour pouvoir obtenir la case exacte )
			 * et on ajoute le mur dans la matrice de la fourmilière
			 */
			else if(!f.getMur((int)ys, (int)xs) && (!e.isShiftDown() && (!f.contientFourmi((int)ys,(int)xs))))
	            {
	                    f.setMur((int)ys, (int)xs, true);
	                    this.changeColor((int)ys,(int)xs);
	            }
			//On lance une alerte si le drag est en dehors de la bordure 
			}catch(OtherExceptions exception)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR NOT_DRAGGABLE");
				alert.setContentText(OtherExceptions.NOT_DRAGGABLE);
				alert.show();
			}
		});
	    
	}
	
	/* 2eme Constructeur de grille qui va nous permettre de l'appliquer sur le bouton " Loupe "
	 * Elle prend en paramètre la fourmiliere ainsi que la largeur et la hauteur 
	 * La largeur et la hauteur en paramètre nous permettra de construire avec exactitude l'emplacement de la case où l'on souhaite faire le zoom
	 * On a besoin de 11x11 cellules que l'on met dans PANE_WIDTH && PANE_HEIGHT
	 */
	public Grille(Fourmiliere f,int w,int h)
	{
		/* On déclare 2 variables locales qui vont varier en fonction des paramètres entrés 
		 * Pour construire la grille zoomé et qui ne contient 11x11 cellules, on distingue plusieurs cas : 
		 * (1) si l'utilisateur décide de donner une coordonnée à la largeur de 0 on prendra exactement pour la largeur au minimum 0 et au maximum 10 
		 * (2) si l'utlisateur décide de se placer tout à droite à l'extrémité de la grille : pour afficher les cases autour à sa gauche, le min va prendre la taille moins 10. Sinon on soustrait 5 du min et on ajoute 5 au max
		 * (3) si l'utilisateur décide de donner une coordonnée à la hauteur de 0 on prendra exactement pour la hauteur au minimum 0 et au maximum 10 
		 * (4) si l'utlisateur décide de se placer tout en bas à l'extrémité de la grille : pour afficher les cases autour à sa droite, le min va prendre la taille moins 10. Sinon on soustrait 5 du min et on ajoute 5 au max
		 */
		this.PANE_WIDTH = 11;
		this.PANE_HEIGHT = 11;
		int minw,maxw;
		int minh,maxh;
		
		// (1)
		if (w<5)
		{
			minw=0;
			maxw=10;
		}
		// (2)
		else if (f.getLargeurProperty().getValue()-w<5)
		{
			minw=f.getLargeurProperty().getValue()-10;
			maxw=f.getLargeurProperty().getValue();
		}
		else {
			minw=w-5;
			maxw=w+5;
		}
		// (3)
		if (h<5)
		{
			minh=0;
			maxh=10;
		}
		// (4)
		else if (f.getHauteurProperty().getValue()-h<5)
		{
			minh=f.getHauteurProperty().getValue()-10;
			maxh=f.getHauteurProperty().getValue();
		}
		else {
			minh=h-5;
			maxh=h+5;
		}
		
		//Meme chose que dans le constructeur de la grille 
		
		//Alignement,etc ..
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(true);
		
		//Créations d'objets
		Rectangle casestmp[][] = new Rectangle[TAILLE_DEFAULT+2][TAILLE_DEFAULT+2];
		this.cases = casestmp;
		
		//meme chose que le constructeur grille on crée les rectangles qu'on ajoute dans le GridPane et dans la liste
	    for (int row = 0; row < PANE_WIDTH; row++) 
	    {
			for (int col = 0; col < PANE_HEIGHT; col++) {
				Rectangle rectmp = new Rectangle();
				rectmp.setWidth(30);
				rectmp.setHeight(30);
				if(f.getMur(row+minw, col+minh))
				{
					rectmp.setFill(Color.BLACK);
				}
				else if (f.getQteGraines(row+minw, col+minh)>0)
				{
					double ratio = (double) (f.getQteGraines(row+minw, col+minh))/(f.getQmaxProperty().getValue());
					Color c = Color.rgb(255,0,0,ratio);
					rectmp.setFill(c);
				}
				else rectmp.setFill(Color.WHITE);
				rectmp.setStroke(Color.BLACK);
				GridPane.setRowIndex(rectmp, row+minw);
				GridPane.setColumnIndex(rectmp, col+minh);
				this.getChildren().add(rectmp);
				this.cases[col+minh][row+minw]=rectmp;
	        }
	    }   
	}
	
	// Fonction qui permet le visuel d'une fourmi dans le GridPane : cercle de couleur vert si elle ne porte pas de graine sinon elle est de couleur bleue, on ajoute la fourmi dans une liste de Fourmi
	public void addFourmi(int col,int row,double radius, Fourmiliere f)
	{
		Circle cercle = new Circle(radius);
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

	//Permet de récupérer une cellule
	public Rectangle getCases(int x, int y) {
        return this.cases[y][x];
    }
	
	//Permet de récupérer la liste des fourmis
	public List<Circle> getFourmisList() {
		return this.fourmis;
	}
	
	//Permet de changer la couleur d'une case en noir ( créer un mur ) 
	public void changeColor(int xs, int ys) {
        this.cases[ys][xs].setFill(Color.BLACK);

    }
	
	//Permet d'enlever les bordures des cellules 
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
	
	//Permet de rajouter les bordures des cellules
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
	
	/* 
	 * Fonction qui sera utilisé pour changer la taille du plateau, elle applique exactement la meme chose que le constructeur grille sauf que l'on enleve tous les children dans le GridPane 
	 * pour pouvoir ajouté les nouvelles cellules 
	*/
	public void changeGrille(Fourmiliere f)
	{
		//Pour récupérer la nouvelle taille de la grille on passe par les property 
		this.PANE_WIDTH = f.getLargeurProperty().getValue();
		this.PANE_HEIGHT = f.getHauteurProperty().getValue();
		
		this.getChildren().clear();
		
		//Alignements + Gridlines
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(true);

		//Creations d'objets
		Rectangle casestmp[][] = new Rectangle[PANE_WIDTH+2][PANE_HEIGHT+2];
		this.cases = casestmp;
		
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
								this.addFourmi(this.getColumnIndex(rectmp),  this.getRowIndex(rectmp),4.0,f);
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
								alert.setTitle("ERROR NOT_DRAGGABLE");
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
