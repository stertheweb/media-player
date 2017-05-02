package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainController implements Initializable {
	@FXML private MediaView mediaView;
	private MediaPlayer mediaPlayer;
	private Media media;
	@FXML Slider volumeSlider;
	@FXML Slider timeSlider;
	@FXML Text timeElapsed;
	private String minutesAsString;
	private String secondsAsString;
	private String filePath;
	private final List listeners = new ArrayList();
	private double durInSec;
	private boolean isPlaying = false;
			
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void openFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File", "*.mp4");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showOpenDialog(null);
		filePath = file.toURI().toString();
		if(filePath != null) {
			media = new Media(filePath);
			mediaPlayer = new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			mediaPlayer.play();
			DoubleProperty width = mediaView.fitWidthProperty();
			DoubleProperty height = mediaView.fitHeightProperty();
			width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
			height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
			mediaView.setPreserveRatio(true);
			mediaPlayer.setOnReady(new Runnable() {
				@Override
				public void run() {
					durInSec = media.getDuration().toSeconds();
					timeSlider.setMax(durInSec);
					mediaPlayer.play();
					
				}
			});
		}
		volumeSlider.setValue(mediaPlayer.getVolume() * 100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable){
				mediaPlayer.setVolume(volumeSlider.getValue() / 100);
			}
		});
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {

				
		            timeSlider.setValue(newValue.toSeconds());

				//synchronized (listeners) {
		            //listeners.add("a");
					double time = newValue.toSeconds();
					int minutes =  (int) Math.floor(time/60);
					int seconds = (int) Math.floor(time - minutes*60);
					if(minutes > 99) {minutesAsString = String.format ("%03d", minutes);}
					else minutesAsString = String.format ("%02d", minutes);
					secondsAsString = String.format ("%02d", seconds);
		            timeSlider.setValue(time);
		            timeElapsed.setText(minutesAsString + ":" + secondsAsString);
		        //}

				
				
			}
			
		});
		
		timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
			}	
			
	});
		timeSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
			}	
			
	});
		timeSlider.setPrefHeight(50);
		mediaPlayer.play();
	}
	
	public void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("This is a video player build by a team of four extremely talented students. It is able to play mp4 video files."
				+ " Basic functionality includes the ability to play, pause, stop, fast forward, slow down and switch to full screen by double"
				+ " clicking the frame.");

		alert.showAndWait();
	}
	
	public void donate() throws IOException, URISyntaxException{
		if(Desktop.isDesktopSupported())
		{
		  Desktop.getDesktop().browse(new URI("https://www.gofundme.com/worlds-best-media-player"));
		}
	}
	public void play(ActionEvent event) {
		mediaPlayer.play();
		isPlaying = true;
		mediaPlayer.setRate(1);
	}
	public void pause(ActionEvent event) {
		isPlaying = false;
		mediaPlayer.pause();
	}
	public void fastForward(ActionEvent event) {
		mediaPlayer.setRate(2);
	}
	public void slowDown(ActionEvent event) {
		mediaPlayer.setRate(.5);
	}
	public void stop(ActionEvent event) {
		mediaPlayer.seek(mediaPlayer.getTotalDuration());
		mediaPlayer.stop();
	}
	public void playOrStop(){
		if (isPlaying){
			mediaPlayer.pause();
			isPlaying = false;
		}
		else {
			mediaPlayer.play();
			isPlaying = true;
		}
	}
}
