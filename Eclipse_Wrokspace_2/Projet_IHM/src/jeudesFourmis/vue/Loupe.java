package jeudesFourmis.vue;

import Exceptions.OtherExceptions;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jeudesFourmis.model.Fourmiliere;

public class Loupe extends VBox {
	
	private Button loupe;
	public Loupe (Fourmiliere f)
	{	
		//Creation d'objets
		HBox textPane = new HBox(15);
		Button l = new Button("Loupe");
		TextField xCell = new TextField();
		TextField yCell = new TextField();
		Label x = new Label("X = ");
		Label y = new Label("Y = ");
		
		//Styles
        l.setStyle("-fx-border-color: black; -fx-border-width: 2;");
		x.setFont(new Font("Arial", 22));
		y.setFont(new Font("Arial", 22));
		
        //Actions sur le bouton loupe
        l.setOnAction(e->
		{
			/*
			 * On récupère les valeurs des textfields dans des variables locales 
			 * Ensuite on teste si la valeur entrée est cohérente, si elle ne l'est pas on renvoie une exception 
			 * sinon on crée une deuxième fenêtre qui prend en parametre la scene ZoomVue un GameController
			*/
			int getX = Integer.parseInt(xCell.getText());
			int getY = Integer.parseInt(yCell.getText());;
			try {
					if(getX >f.getLargeurProperty().getValue() || getY> f.getHauteurProperty().getValue() || getX<0 || getY<0)
					{
						throw new OtherExceptions();
					}
					else
					{
						f.setxNow(getX);
						f.setyNow(getY);
						
						//Creations des objets
						Stage stage = new Stage();
						BorderPane root  = new BorderPane();
						ZoomVue zoomvue = new ZoomVue(f);
						GameController c = new GameController(zoomvue,f);
						root.setCenter(zoomvue);
						
						//Creation de la fenetre
						stage.setTitle("Loupe");
						stage.setScene(new Scene(root));
						stage.show();
						stage.setOnCloseRequest(event->
						{
							/* 
							 * Pour eviter tout probleme, dès que la nouvelle fenetre est fermé on cancel le service une fois le mode pause activé 
							 * pour éviter qu'il ne soit encore actif en arrière-plan.
							 */
							c.getservice().cancel(); 	 
						});
					}
				}catch(OtherExceptions exception)
				{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("WARNING WRONG_VALUES");
					alert.setContentText(OtherExceptions.WRONG_VALUES);
					alert.show();
				}
		});
        
		//On force le fait que les textfields ne prennent que des int à l'intérieur
        xCell.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue.matches("\\d*")) return;
		    xCell.setText(newValue.replaceAll("[^\\d]", ""));
		});
        yCell.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue.matches("\\d*")) return;
		    yCell.setText(newValue.replaceAll("[^\\d]", ""));
		});
        
        //Emboîtements
      	textPane.getChildren().addAll(x,xCell,y,yCell);
        this.loupe = l;
        this.getChildren().addAll(l,textPane);
	}
	
	public Button getLoupe() {
		return loupe;
	}
}
