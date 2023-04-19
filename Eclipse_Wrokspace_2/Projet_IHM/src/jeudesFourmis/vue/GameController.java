package jeudesFourmis.vue;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import jeudesFourmis.model.Fourmiliere;

public class GameController {
	private Fourmiliere f;
	private GameVue vue; 
	private Service<Void> service;
	
	
	
	public GameController(Fourmiliere f,GameVue vue)
	{
		this.f = f;
		this.vue = vue;
		
		service();
		quit();
		play_pause();
		
	}
	
	 public void  afficheMatrice() {
		  Fourmiliere f = new Fourmiliere(10,10,3);
		      System.out.println("-------------------- "+" ----------------------------------");
		      System.out.println("---------------------      --------------------------------");
		      System.out.println(f.stringMurs());
		      System.out.println(f.stringGraines());	
		      System.out.println(f.stringFourmis());
	  }
	
	private void service() {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						while(!isCancelled()) {
							Thread.sleep(1000);
							Platform.runLater(() -> f.evolue());
							Platform.runLater(() -> afficheMatrice());
						}
						return null;
					}
				};
			}
		};
		this.service=service;
	}
	
	public void bindings ()
	{
		
	}
	
	public void events()
	{
		
	}
	
	public void quit()
	{
		this.vue.getQuit().setOnAction(e->{
		Platform.exit();
	});
	}
	
	public void play_pause()
	{
		this.vue.getPlay_pause().setOnAction(e -> {
		if(!this.service.isRunning())
		{	
			System.out.print("if");
			this.vue.getPlay_pause().setText(" Pause ");
			this.vue.getPanneau().setDisable(true);
			service.stateProperty().addListener((observable, oldValue, newValue) -> {
				switch (newValue) {
					case CANCELLED:
					case SUCCEEDED:
						play_pause();
	                	service.reset();
	                	break;
	                default:
				}
			});
			service.start();
		}
		else 
		{
			System.out.print("else");
			
			this.vue.getPlay_pause().setText("Play");
			this.vue.getPanneau().setDisable(false);
			service.cancel();
		}
	});
	}
	
	/*
	this.vue.getQuit().setOnAction(e -> {
		if(this.service.isRunning())
		{	
			play_pause.setText(" Play ");
			service.cancel();
		}
		else
		{
			play_pause.setText("Pause");
			service.stateProperty().addListener((observable, oldValue, newValue) -> {
				switch (newValue) {
					case CANCELLED:
					case SUCCEEDED:
	                	panneau.setDisable(false);
	                	service.reset();
	                	break;
	                default:
				}
			});
			service.start();
		}
	});
	
	
	//Bouton Loupe 
	loupe.setOnAction(e->{
		root2.setPrefSize(330, 330);
		panneau.setPANE_HEIGHT(11);
		panneau.setPANE_WIDTH(11);
		Scene sceneZoom = new Scene(root2);
		primaryStage.setScene(sceneZoom);
		primaryStage.show();
	});*/
	
	
	// Clic sur panneau
/*	panneau.setOnMouseClicked(e -> {
		compteurX.setValue((int) e.getX());
		compteurY.setValue((int) e.getY());
	});*/
	
}
