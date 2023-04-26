package jeudesFourmis.vue;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class QuitButton extends Button {
	
	public QuitButton()
	{
		this.setText("Quit");
		
		//Styles
		this.setStyle("-fx-border-color: black; -fx-border-width: 2;");		
		//Actions
		this.setOnAction(e->{
			Platform.exit();
		});
	}
}