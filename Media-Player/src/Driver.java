import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Driver extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		 BorderPane root = new BorderPane();
		    Scene scene = new Scene(root, 600, 510, Color.BLUE);

		    MenuBar menuBar = new MenuBar();
		    menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		    root.setTop(menuBar);

		    Rectangle rect = new Rectangle(0,0,600,420);
		    rect.setFill(Color.BLACK);
		    
		    Menu fileMenu = new Menu("File");
		    MenuItem openMenuItem = new MenuItem("Open");
		    MenuItem exitMenuItem = new MenuItem("Exit");
		    exitMenuItem.setOnAction(actionEvent -> Platform.exit());

		    fileMenu.getItems().addAll(openMenuItem, new SeparatorMenuItem(), exitMenuItem);

		    Menu playMenu = new Menu("Play");
		    MenuItem playPauseMenuItem = new CheckMenuItem("Play/Pause");
		    MenuItem stopMenuItem = new CheckMenuItem("Stop");
		    MenuItem nextMenuItem = new CheckMenuItem("Next");
		    MenuItem previousMenuItem = new CheckMenuItem("Previous");
		    playMenu.getItems().addAll(playPauseMenuItem, stopMenuItem, new SeparatorMenuItem(), nextMenuItem, previousMenuItem);

		    Menu helpMenu = new Menu("Help");
		    MenuItem donateMenuItem = new CheckMenuItem("Donate");
		    MenuItem aboutMenuItem = new CheckMenuItem("About");
		    helpMenu.getItems().addAll(donateMenuItem, new SeparatorMenuItem(), aboutMenuItem);

		    menuBar.getMenus().addAll(fileMenu, playMenu, helpMenu);

		    //slider
		    Slider slider = new Slider(0, 100, 0);
	        slider.setShowTickMarks(true);
	        slider.setShowTickLabels(true);
	        slider.setMajorTickUnit(100f);
//	        slider.setBlockIncrement(0.1f);
	        
	        root.setCenter(rect);
	        root.setBottom(slider);

		    primaryStage.setScene(scene);
		    primaryStage.show();
		
	}
}
