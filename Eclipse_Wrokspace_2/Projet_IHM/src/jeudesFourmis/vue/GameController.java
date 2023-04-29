package jeudesFourmis.vue;

import java.util.Optional;

import Exceptions.OtherExceptions;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import jeudesFourmis.model.Fourmiliere;

public class GameController {
	
	//Initialisation des attributs
	private Fourmiliere f;
	private GameVue vue; 
	private ZoomVue vueZoom; 
	private Service<Void> service;	
	
	public GameController(Fourmiliere f,GameVue vue)
	{
		this.f = f;
		this.vue = vue;

	   //Bindings
		bindings();
		
	   //Service 	
		service();
		
	   //Events 
		play_pause();
		reset();
		initialiser();
		changerTaillePlateau();
		posergraines();
	}
	
	//2eme constructeur pour synchroniser la premiere vue avec la deuxiemme vue issue du zoom 
	public GameController(ZoomVue zoomVue, Fourmiliere f)
	{
		this.f = f;
		this.vueZoom = zoomVue;
		
		//Service
		service2();
		
		//Event
		play_pause2();
	}
	
	//Creation du premier service
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
	
	//Creation d'un deuxieme service utilise pour le bouton loupe lors de la création de la deuxieme fenetre, pour synchroniser les deux fenêtres
	private void service2() {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						while(!isCancelled()) {
							Thread.sleep(100);
							Platform.runLater(() -> vueZoom.synchroniser2(f));
						}
						return null;
					}
				};
			}
		};
		this.service=service;
	}
	
	//On effectue un "bind" sur les éléments du plateau sur la nombre de graines présentes dans le plateau, le nombre de fourmi et le nombre d'itérations 
	public void bindings ()
	{
		vue.getInfos().getGrainesL().textProperty().bind(Bindings.concat("Nombre de graines : ").concat(f.getNbrGrainesProperty().asString()));
		vue.getInfos().getFourmisL().textProperty().bind(Bindings.concat("Nombre de fourmis : ").concat(f.getNbrFourmiProperty().asString()));
		vue.getInfos().getIterationsL().textProperty().bind(Bindings.concat("Nombre d'itérations : ").concat(f.getIterationProperty().asString()));
	}
	
	//Evenement pour reset tout le plateau, on reset tout le plateau de jeu et on synchronise la vue avec le model
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
	
	/* Event pour initialiser la fourmiliere, on effectue d'abord un "reset" ensuite on initialise la fourmiliere avec les valeurs issus du textfields
	 * "getNbfT" récupère le nombre de fourmi que l'on va disposer dans le plateau || "getNbgT" récupère le nombre max de graines possibles dans une case 
	 * "getNbmT" récupère le nombre de murs que l'on va disposer dans le panneau 
	 * On synchronise la vue avec le model
	*/
	
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
	
	/*
	 * Evenement pour le changement de taille du plateau
	 * On lance une alerte de confirmation à l'utilisateur pour valider son choix, on renvoie une exception si la taille choisi dépasse les tailles prédefinies
	 * On recree à nouveau la grille et une fourmilière avec la nouvelle taille ainsi que le max de de graines par case que l'on récupère avec une property  
	 * On fait appel à la fonction "changeGrille" dans la classe Grille
	 * Et enfin on rappelle à nouveaux les évenements bindings et service
	*/
	
	public void changerTaillePlateau()
	{
		this.vue.getBoutton().getChangexyPlateau().setOnAction(e->
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir changer la taille du plateau ?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			int taille = Integer.parseInt(this.vue.getBoutton().getSize().getText());
			try 
			{
				if ((taille<Grille.TAILLE_MIN) || (taille>Grille.TAILLE_MAX)) 
				{
					throw new OtherExceptions();
				}
				else {
				f = new Fourmiliere(taille,taille,f.getQmaxProperty().getValue());
				this.vue.getPanneau().changeGrille(f);
				
				//Bindings
				bindings();
				
			   //Service 	
				service();
				
			   //Events 
				play_pause();
				reset();
				initialiser();
				changerTaillePlateau();
				posergraines();
			}
			}catch(OtherExceptions exception )
			{
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING NOT_GOOD_SIZE");
				alert2.setContentText(OtherExceptions.NOT_GOOD_SIZE);
				alert2.show();
			}
			}
			});

	}

	//Event sur la molette de la souris 
	/*
	 * On établie la meme logique que pour le drag de souris dans le 1er constructeur de Grille : 
	 * On récupère la position de la souris ensuite on vérifie avec "getDeltaY" si la molette est vers le bas ou vers le haut 
	 * si la molette est vers le haut on ajoute 1 graine à l'emplacement de la souris sinon on soustrait 1
	 * Et on synchronise la vue avec le modele
	*/
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
	
	//Event sur play_pause
	public void play_pause()
	{
		this.vue.getBoutton().getPlay_pause().setOnAction(e -> {
		//Si on appuie sur le bouton pour la premiere fois => on lance le service
		if(!this.service.isRunning())
		{	
			this.vue.getBoutton().getPlay_pause().setText(" Pause ");//On met à jour le texte du bouton
			this.vue.getPanneau().setDisable(true); //On désactive la grille 
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
			service.start(); // On lance le service 
		}
		//Si on réappuie sur le bouton 
		else 
		{
			
			this.vue.getBoutton().getPlay_pause().setText("Play");//On met à jour le texte du bouton
			this.vue.getPanneau().setDisable(false);//On active la grille 
			this.vue.getBoutton().enableButtonsAll();
			vue.getPanneau().enableStroke();
			service.cancel(); // On arrete le service
		}
	});
	}
	
	//Event pour le zoom 
	//On utilise cette fonction pour que la 2eme fenetre issue du bouton loupe soit synchroniser à celui de la 1ere fenetre
	public void play_pause2()
	{
		if(!this.service.isRunning())
		{	
			this.vueZoom.getPanneau().setDisable(true);
			service.start();
		}
		else
		{		
			service.cancel();
		}
	}
	
	public Service<Void> getservice()
	{
		return this.service;
	}
}
