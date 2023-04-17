package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.util.Random;
import java.util.Scanner;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	public void start(Stage stage) {
        GridPane grid = new GridPane();
        Scanner sc = new Scanner(System.in);
        System.out.println("Saisissez la taille du plateau : ");
        int i = sc.nextInt();
 
         
         
        for(int row=0; row<i; row++) {
            for(int col=0; col<i; col++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(50);
                rec.setHeight(50);
                if((row%2==0 && col%2==0) || (row%2 !=0 && col%2 !=0))
                    rec.setFill(Color.WHITE);
                else
                    rec.setFill(Color.BLACK);
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                grid.getChildren().addAll(rec);
                 
            }
        }
         
        grid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                for( Node node: grid.getChildren()) {

                    if( node instanceof Rectangle) {
                        if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                            System.out.println( "Node: " + node + " at " + grid.getRowIndex( node) + "/" + grid.getColumnIndex(node));
                        }
                    }
                }
            }
        });
		grid.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        
        Scene scene = new Scene(grid, i*50,i*50);
        
        stage.setTitle("Grid");
        stage.setScene(scene);
        stage.show();
     
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
 
}
