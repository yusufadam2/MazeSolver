package maze.visualisation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class Menu extends Application
{
	@Override
	public void start(Stage stage)
	{

		VBox root= new VBox(10);
		root.setBackground(Background.EMPTY);
		root.setAlignment(Pos.CENTER);

		Scene scene= new Scene(root,500,600, Color.WHITE);
		stage.setScene(scene);
		stage.setTitle("Maze Solver");

		stage.show();
	}

// 	public static void main(String[] args)
// 	{
// 		launch(args);
// 	}
}