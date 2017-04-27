package application;

import java.io.File;
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
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainController implements Initializable {
	@FXML private MediaView mediaView;
	private MediaPlayer mediaPlayer;
	private Media media;
	@FXML Slider volumeSlider;
	@FXML Slider timeSlider;
	private String filePath;
	private final List listeners = new ArrayList();
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
	public void play(ActionEvent event) {
		mediaPlayer.play();
		mediaPlayer.setRate(1);
	}
	public void pause(ActionEvent event) {
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
}
