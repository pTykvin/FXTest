package bindingjavafx;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimelineEvents extends Application {

	// main timeline
	private Timeline timeline;
	private AnimationTimer timer;

	// variable for storing actual frame
	private Integer i = 0;

	@Override
	public void start(Stage stage) {
		SimpleStringProperty timeString = new SimpleStringProperty("Animation time: 0 ms");
		final SimpleLongProperty lastTime = new SimpleLongProperty(System.currentTimeMillis());
		Group p = new Group();
		Scene scene = new Scene(p);
		stage.setScene(scene);
		stage.setWidth(500);
		stage.setHeight(500);
		p.setTranslateX(80);
		p.setTranslateY(80);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(-3.0);
		dropShadow.setOffsetY(-3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

		Reflection reflection = new Reflection();

		// create a circle with effect
		final Circle circle = new Circle(80, Color.rgb(156, 216, 255));
		Lighting lighting = new Lighting();
		dropShadow.setInput(lighting);
		circle.setEffect(dropShadow);
		circle.setEffect(dropShadow);
		// create a text inside a circle
		final Text text = new Text(i.toString());
		text.setStroke(Color.BLACK);
		text.setFont(new Font(30));
		
		final Text timeText = new Text();
		timeText.setStroke(Color.BLACK);
		timeText.setFont(new Font(30));
		timeText.textProperty().bind(timeString);
		
		// create a layout for circle with text inside
		final StackPane root = new StackPane();
		final StackPane stack = new StackPane();
		stack.getChildren().addAll(circle, text);
		root.setLayoutX(80);
		root.setLayoutY(80);

		root.getChildren().addAll(stack);

		p.getChildren().addAll(root, timeText);
		stage.show();

		// create a timeline for moving the circle
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);

		// You can add a specific action when each frame is started.
		timer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				text.setText(i.toString());
				i++;
			}

		};

		stack.setScaleX(2);
		stack.setScaleY(0.5);
		Rotate rotate = new Rotate(0);
		rotate.setPivotX(80);
		rotate.setPivotY(80);
		root.getTransforms().add(rotate);

		// create a keyValue with factory: scaling the circle 2times
		KeyValue keyScaleValueX = new KeyValue(stack.scaleXProperty(), 0.5);
		KeyValue keyScaleValueY = new KeyValue(stack.scaleYProperty(), 2);
		KeyValue keyColorValue = new KeyValue(circle.fillProperty(), Color.rgb(100, 40, 0));
		KeyValue keyStrokeValue = new KeyValue(text.strokeProperty(), Color.WHITE);
		KeyValue keyForegroundValue = new KeyValue(text.fillProperty(), Color.WHITE);

		KeyValue keyEffectValue = new KeyValue(lighting.lightProperty(), new Light.Point());

		// create a keyFrame, the keyValue is reached at time 2s
		Duration duration = Duration.millis(2000);
		// one can add a specific action when the keyframe is reached
		EventHandler onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				i = 0;
				timeString.set("Animation time: " + (System.nanoTime() - lastTime.get()) + " ns");
				System.out.println(System.nanoTime());
				lastTime.set(System.nanoTime());
			}
		};

		KeyFrame keyFrame = new KeyFrame(duration, onFinished, keyScaleValueX, keyScaleValueY, keyColorValue, keyStrokeValue, keyForegroundValue, keyEffectValue);

		// add the keyframe to the timeline
		timeline.getKeyFrames().add(keyFrame);

		timeline.play();
		timer.start();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}