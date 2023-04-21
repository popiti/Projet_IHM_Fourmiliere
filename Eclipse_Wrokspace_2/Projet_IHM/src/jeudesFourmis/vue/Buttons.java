package jeudesFourmis.vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Buttons extends VBox{
	public static final int VGROW = 15;
	public static final int HGROW = 5;
	
	public Buttons()
	{
		HBox bottomArea = new HBox(HGROW);
		VBox bottomLeftArea = new VBox(HGROW);
		VBox bottomRightArea = new VBox(HGROW);
		HBox bottomRightTopArea  = new HBox(VGROW);
		HBox bottomRightBottomArea  = new HBox(VGROW);
		GridPane buttonsPane = new GridPane();
		Button quit = new Button("Quit");
		Button loupe = new Button("loupe");
		Button play_pause = new Button("Play");
		Button reset = new Button("Reset");
		
		bottomRightArea.setAlignment(Pos.CENTER_RIGHT);
		bottomLeftArea.setAlignment(Pos.CENTER_RIGHT);
		buttonsPane.setVgap(HGROW);
		buttonsPane.setHgap(VGROW);
	    play_pause.setMaxWidth(Double.MAX_VALUE);
	    
	    buttonsPane.add(loupe, 0, 0 );
		buttonsPane.add(play_pause, 0, 1);
		buttonsPane.add(new Spring(), 2, 0);
		bottomLeftArea.getChildren().addAll(buttonsPane);
		bottomRightTopArea.getChildren().add(reset);
		bottomRightBottomArea.getChildren().add(quit);
		bottomRightArea.getChildren().addAll(bottomRightTopArea, new Spring(),  bottomRightBottomArea);
		bottomArea.getChildren().addAll(bottomLeftArea, new Spring(), bottomRightArea);
		this.getChildren().add(bottomArea);
		this.setAlignment(Pos.BOTTOM_RIGHT);
	}
}
