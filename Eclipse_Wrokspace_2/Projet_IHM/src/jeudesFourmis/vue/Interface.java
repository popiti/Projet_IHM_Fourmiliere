	package jeudesFourmis.vue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
	
	import jeudesFourmis.model.Fourmiliere;
	

	public class Interface extends Application {
		public static final int VGROW = 15;
		public static final int HGROW = 5;
		public static final int RADIUS = 5;
		public static final int PANE_WIDTH = 500;
		public static final int PANE_HEIGHT = 500;
		
		@Override
		public void start(Stage primaryStage) {
			// Création des objets
			VBox root = new VBox(VGROW);
			VBox root2 = new VBox(VGROW);
			HBox bottomArea = new HBox(HGROW);
			VBox bottomLeftArea = new VBox(HGROW);
			VBox bottomRightArea = new VBox(HGROW);
			HBox bottomRightTopArea  = new HBox(VGROW);
			HBox bottomRightBottomArea  = new HBox(VGROW);
			GridPane buttonsPane = new GridPane();
			GridPane buttonsPane2 = new GridPane();
			Grille panneau = new Grille();
			Circle cercle = new Circle();
			Rectangle rec = new Rectangle();
			Button quit = new Button(" Quit ");
			Button loupe = new Button(" Loupe ");
			Button play_pause = new Button(" Play ");
			Button reset = new Button(" Reset ");
			Fourmiliere f = new Fourmiliere(20,10,4);	
			
			//Creation du menu
		    Menu fileMenu = new Menu("File");
		    //Creating menu Items
		    MenuItem item1 = new MenuItem("Taille plateau");
		    MenuItem item2 = new MenuItem("Capacité d'une case");
		    MenuItem item3 = new MenuItem("Vitesse de simulation");
		    MenuItem item4 = new MenuItem("Initialisation");
		    //Adding all the menu items to the menu
		    fileMenu.getItems().addAll(item1, item2, item3,item4);
		    //Creating a menu bar and adding menu to it.
		    MenuBar menuBar = new MenuBar(fileMenu);
		    menuBar.setTranslateX(10);
		    menuBar.setTranslateY(10);
		    
		    
			// Placement du cercle
			
		    
		    
			//
		    
			
			
			// Styles
			panneau.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			quit.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			reset.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			loupe.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			play_pause.setStyle("-fx-border-color: black; -fx-border-width: 2;");
			
			// Alignements, etc...
			bottomRightTopArea.setAlignment(Pos.CENTER);
			bottomRightBottomArea.setAlignment(Pos.CENTER);
			buttonsPane.setVgap(HGROW);
			buttonsPane.setHgap(VGROW);
			root.setPadding(new Insets(10));
			root2.setPadding(new Insets(10));
			play_pause.setMaxWidth(Double.MAX_VALUE);
			root.setPrefSize(panneau.getWidth(), panneau.getHeight());
			//root2.setPrefSize(panneau.getWidth(), panneau.getHeight());
			
			// Emboitement des panneaux/boutons
			buttonsPane.add(loupe, 0, 0 );
			buttonsPane.add(play_pause, 0, 1);
			buttonsPane.add(new Spring(), 2, 0);
			bottomLeftArea.getChildren().addAll(buttonsPane);
			bottomRightTopArea.getChildren().add(reset);
			bottomRightBottomArea.getChildren().add(quit);
			bottomRightArea.getChildren().addAll(bottomRightTopArea, new Spring(),  bottomRightBottomArea);
			bottomArea.getChildren().addAll(bottomLeftArea, new Spring(), bottomRightArea);
			//panneau.getChildren().add(cercle);
			root.getChildren().addAll(panneau,new Spring(),menuBar, new Spring(), bottomArea);
			
			Service<Void> service = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							int maxiterations=100000;
							for(int i = 0; i<maxiterations;i++){
								if (isCancelled())
								{
									System.out.println("Pause");
									break;
								}
								Thread.sleep(100);
								System.out.println(i);
							}
							return null;
						}
					};
				}
			};
			
			/*/Panneau
			panneau.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {

	                for( Node node: panneau.getChildren()) {

	                    if( node instanceof Rectangle) {
	                        if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
	                        	int rowIndex = panneau.getRowIndex( node);
	                        	int colIndex = panneau.getColumnIndex(node);
	                			Circle cercle = new Circle();
	                            System.out.println( "Node: " + node + " at " + panneau.getRowIndex( node) + "/" + panneau.getColumnIndex(node));
	                        }
	                    }
	                }
	            }
	        });*/
			
			// Bouton start
			play_pause.setOnAction(e -> {
				if(service.isRunning())
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
			});
			//Bouton Quit 
			quit.setOnAction(e->{
				Platform.exit();
			});
			
			// Clic sur panneau
		/*	panneau.setOnMouseClicked(e -> {
				compteurX.setValue((int) e.getX());
				compteurY.setValue((int) e.getY());
			});*/
			
			// Bindings
		/*/	cercle.centerXProperty().bind(compteurX.getValueProperty());
		//	cercle.centerYProperty().bind(compteurY.getValueProperty());
			label.textProperty().bind(Bindings.concat("   La tortue est à la position ")
					.concat(compteurX.getValueProperty().asString())
					.concat(" ")
					.concat(compteurY.getValueProperty().asString())
					.concat("   "));
			*/
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}

		public static void main(String[] args) {
			launch(args);
		}
	}

