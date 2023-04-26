package jeudesFourmis.vue;

import java.util.Optional;

import Exceptions.OtherExceptions;
import application.Main;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import jeudesFourmis.model.Fourmiliere;

public class GameController {
	private Fourmiliere f;
	private GameVue vue; 
	private Service<Void> service;
	private Stage stage = Main.stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public GameController()
	{
		
	}
	public GameController(Fourmiliere f,GameVue vue)
	{
		this.f = f;
		this.vue = vue;

	   
		bindings();
		
		service();
		play_pause();
		reset();
		initialiser();
		changerTaillePlateau();
		posergraines();
	}
	
	/*public void  afficheMatrice(Fourmiliere f) 
	  {	  	  
		      System.out.println("-------------------- "+" ----------------------------------");
		      System.out.println("---------------------      --------------------------------");
		      System.out.println(f.stringMurs());
		      System.out.println(f.stringGraines());	
		      System.out.println(f.stringFourmis());
	  }*/
	
	private void service() {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						while(!isCancelled()) {
							Thread.sleep((int)(vue.getMaxSlider()+vue.getMinSlider()-vue.getValueSlider()));
							Platform.runLater(() -> f.evolue());
							Platform.runLater(() -> vue.synchroniser(f));
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
		vue.getInfos().getGrainesL().textProperty().bind(Bindings.concat("Nombre de graines : ").concat(f.getNbrGrainesProperty().asString()));
		vue.getInfos().getFourmisL().textProperty().bind(Bindings.concat("Nombre de fourmis : ").concat(f.getNbrFourmiProperty().asString()));
		vue.getInfos().getIterationsL().textProperty().bind(Bindings.concat("Nombre d'itérations : ").concat(f.getIterationProperty().asString()));
	}
	
	public void reset()
	{
		this.vue.getBoutton().getReset().setOnAction(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir réinitialiser ?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				f.resetFourmiliere();
				vue.synchroniser(f);
			}
		});
	}
	
	public void initialiser()
	{
		this.vue.getBoutton().getInit().setOnAction(e->
		{
			int nbF = Integer.parseInt(this.vue.getBoutton().getNbfT().getText());
			int nbG = Integer.parseInt(this.vue.getBoutton().getNbgT().getText());
			int nbM = Integer.parseInt(this.vue.getBoutton().getNbmT().getText());
			
			f.resetFourmiliere();
			f.init(nbF,nbG,nbM);
			vue.synchroniser(f);
		});
	}
	
	
	public void changerTaillePlateau()
	{
		this.vue.getBoutton().getChangexyPlateau().setOnAction(e->
		{
			int taille = Integer.parseInt(this.vue.getBoutton().getSize().getText());
			try 
			{
				if ((taille<20) || (taille>70)) 
				{
					throw new OtherExceptions();
				}
				else {
				f = new Fourmiliere(taille,taille,f.getQmaxProperty().getValue());
				for (int i =0; i <(int)((taille/2)-2); i++){
			        f.setQteGraines(i,2*i, 1);
			        f.setQteGraines((taille/2)+1-i,2*i , 1);
			      }
				this.vue.getPanneau().changeGrille(f);
				System.out.println("taille = " + taille);
				bindings();
				
				service();
				play_pause();
				reset();
				initialiser();
				changerTaillePlateau();
				posergraines();
			}
			}catch(OtherExceptions exception )
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error size");
				alert.setContentText(OtherExceptions.NOT_GOOD_SIZE);
				alert.show();
			}
		});

	}

	
	public void posergraines()
	{
		this.vue.getPanneau().setOnScroll(e->
		{
			double x = (e.getX()/11);
			double y = (e.getY()/11);
			int ratioy = (int) y;
			int ratiox = (int) x;
			if(e.getDeltaY()>0)
				f.setQteGraines(ratioy, ratiox, f.getQteGraines(ratioy, ratiox)+1);
			if (e.getDeltaY()<0)
				f.setQteGraines(ratioy, ratiox, f.getQteGraines(ratioy, ratiox)-1);
			this.vue.graineSynchro(f);
		});
	}
	
	public void play_pause()
	{
		this.vue.getBoutton().getPlay_pause().setOnAction(e -> {
		if(!this.service.isRunning())
		{	
			this.vue.getBoutton().getPlay_pause().setText(" Pause ");
			this.vue.getPanneau().setDisable(true);
			this.vue.getBoutton().disableButtonsAll();
			this.vue.getPanneau().disableStroke();
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
			
			this.vue.getBoutton().getPlay_pause().setText("Play");
			this.vue.getPanneau().setDisable(false);
			this.vue.getBoutton().enableButtonsAll();
			vue.getPanneau().enableStroke();
			service.cancel();
		}
	});
	}
	
}
