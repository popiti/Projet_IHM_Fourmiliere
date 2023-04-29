package jeudesFourmis.vue;

import javafx.application.Platform;
import javafx.scene.control.Button;

public class QuitButton extends Button {
	
	public QuitButton()
	{
		//Styles + Texte
		this.setStyle("-fx-border-color: black; -fx-border-width: 2;");	
		this.setText("Quit");
		
		//Action sur le bouton : Ferme l'application et arrête le processus
		this.setOnAction(e->{
			Platform.exit();
		});
	}
}