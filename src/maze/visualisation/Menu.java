package maze.visualisation;

import maze.*;
import maze.routing.*;
import java.util.*;
import javafx.application.Application;
import java.io.*;
import java.io.StreamCorruptedException;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import maze.Maze.Coordinate;


/**
	* Class providing operations and creation of the RouteFinder 
	* @author Yusuf Adam
	* @version 1.0, 30th April 2021 
*/
public class Menu extends Application
{
	/**
		*the RouteFinder that has been loaded
		into from file 
	*/
	public static RouteFinder loadedRoute;

	/**
		*A Gridpane that is used to display the
		maze
	*/
	public static GridPane grid;
	@Override

	/**
		*Method that starts the JavaFX GUI
		*@param stage inputs the stage that displays the menu 
	*/
	public void start(Stage stage)
	{
		Scene mainScene= new Scene(mainScene(stage), 1080,720,Color.WHITE);
		Button start= new Button("Start");
		start.setPrefSize(500,100);
		start.setTranslateY(100);
		start.setOnAction(value ->
		{
			stage.setScene(mainScene);
		});

		Text heading= new Text(140,240, "Maze solver");
		heading.setFont(Font.font ("Verdana", 50));

		VBox root= new VBox(10);
		root.setBackground(Background.EMPTY);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(heading,start);

		Scene scene= new Scene(root,1080,720, Color.WHITE);
		stage.setScene(scene);
		stage.setTitle("Maze Solver");

		stage.show();
	}

	/**
		*Method that creates the VBox for
		the main scene for the GUI
		*@return returns the VBox used for the 
		main screen
	*/
	public static VBox mainScene(Stage stage)
	{
	
		Button loadMap= new Button("Load Map");
		loadMap.setPrefSize(100,50);
		// loadMap.setTranslateY(250);
		// loadMap.setTranslateX(-400);
		loadMap.setOnAction(value ->
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Maze File");
			fileChooser.getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("Text Files", "*.txt")
			);
			File f= fileChooser.showOpenDialog(stage);
			if(f!= null)
			{
				try
				{
					loadedRoute= new RouteFinder(Maze.fromTxt(f.getPath()));
					List<List<Tile>> tiles= Maze.fromTxt(f.getPath()).getTiles();
					updateMaze(tiles);
				}
				catch(InvalidMazeException e)
				{
					Alert alert= new Alert(AlertType.INFORMATION);
					alert.setTitle("error");
					alert.setHeaderText(null);
					alert.setContentText(e.toString()+ " Try again");
					alert.showAndWait();
				}
			}
		});


		Button loadRoute= new Button("Load Route");
		loadRoute.setPrefSize(100,50);
		loadRoute.setOnAction(value ->
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Route File");
			File f= fileChooser.showOpenDialog(stage);
			loadedRoute= RouteFinder.load(f.getPath());
			updateMaze(loadedRoute.getMaze().getTiles());
		});

		Button saveRoute= new Button("Save Route");
		saveRoute.setPrefSize(100,50);
		saveRoute.setOnAction(value ->
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Route File");
			File f= fileChooser.showSaveDialog(stage);
			loadedRoute.save(f.getPath());
		});

		Button step= new Button("Step");
		step.setPrefSize(100,50);
		step.setOnAction(value ->
		{
			if(loadedRoute != null)
			{
				try
				{
					loadedRoute.step();
				}
				catch(NoRouteFoundException e)
				{
					Alert alert= new Alert(AlertType.INFORMATION);
					alert.setTitle("error");
					alert.setHeaderText(null);
					alert.setContentText(e.toString()+ " Try again");
					alert.showAndWait();
				}
				List<List<Tile>> tiles= loadedRoute.getMaze().getTiles();
				updateMaze(tiles);
			}
			

		});

		grid= new GridPane();
		grid.setAlignment(Pos.CENTER);
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<5; j++)
			{
				Rectangle square= new Rectangle(50,50);
				square.setFill(Color.BLACK);
				square.setStroke(Color.WHITE);
				grid.add(square,j,i);
			}
			
		}

		HBox buttons= new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(loadMap,loadRoute,saveRoute,step);
		VBox root= new VBox(10);
		root.setBackground(Background.EMPTY);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(grid,buttons);

		return root;
	}

	/**
		*Method to update the maze grid
		*@return Returns a 2D array that contains tile
		objects.
	*/
	public static void updateMaze(List<List<Tile>> tiles)
	{
		grid.getChildren().clear();
		for(int i=0; i<tiles.size(); i++)
		{
			for(int j=0; j<tiles.get(i).size(); j++)
			{
				Tile tileElement= tiles.get(i).get(j);
				Coordinate coordinates= loadedRoute.getMaze().getTileLocation(tileElement);
				Rectangle square= new Rectangle(50,50);
				square.setStroke(Color.WHITE);

				if(loadedRoute.getRoute().contains(tileElement))
				{
					square.setFill(Color.PURPLE);
				}
				else if(tileElement.getIsVisited())
				{
					square.setFill(Color.PINK);
				}
				else if(tileElement.toString().equals("#"))
				{
					square.setFill(Color.GRAY);
				}
				else
				{
					square.setFill(Color.BLACK);
				}
				grid.add(square,j,i);
			}
		}
	}
}
