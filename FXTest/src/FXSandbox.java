import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXSandbox extends Application {

	private static final int STAR_COUNT = 20000;

	private final Rectangle[] nodes = new Rectangle[STAR_COUNT];
	private final double[] angles = new double[STAR_COUNT];
	private final long[] start = new long[STAR_COUNT];

	private final Random random = new Random();

	@Override
	public void start(final Stage primaryStage) {
		for (int i = 0; i < STAR_COUNT; i++) {
			nodes[i] = new Rectangle(1, 1, Color.WHITE);
			angles[i] = 2.0 * Math.PI * random.nextDouble();
			start[i] = random.nextInt(2000000000);
		}
		final Text text = new Text();
		text.setStroke(Color.GREEN);
		text.setFill(Color.WHITE);
		text.setFont(new Font(30));
		text.setX(100);
		text.setY(100);

		/**/

		PointLight light = new PointLight(Color.WHITE);
		light.setTranslateX(50);
		light.setTranslateY(-300);
		light.setTranslateZ(-400);
		PointLight light2 = new PointLight(Color.WHITE);
		light2.setTranslateX(400);
		light2.setTranslateY(0);
		light2.setTranslateZ(-400);
		AmbientLight ambientLight = new AmbientLight(Color.color(0.2, 0.2, 0.2));
		PerspectiveCamera camera = new PerspectiveCamera();
		camera.setTranslateZ(-10);

		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.WHITE);
		mat.setSpecularColor(Color.WHITE);
		Image map = new Image("http://img-fotki.yandex.ru/get/9802/136640652.0/0_e0110_1d7bbe1b_XL.jpg");
		mat.setDiffuseMap(map);

		final Sphere blue = new Sphere(200);
		blue.setMaterial(mat);

		Group root = new Group();
		root.getChildren().addAll(blue, light, light2, ambientLight);
		root.setTranslateX(120);
		root.setTranslateY(-80);

		// Scene scene = new Scene(root, 600, 600, true,
		// SceneAntialiasing.BALANCED);

		SimpleIntegerProperty i = new SimpleIntegerProperty(1);

		Rotate rotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);

		blue.getTransforms().add(rotate);
		root.getTransforms().add(new Rotate(20, 0, 0, 0, Rotate.Z_AXIS));

		/**/

		Group p = new Group(nodes);
		p.getChildren().addAll(text, root);
		final Scene scene = new Scene(p, 800, 600, Color.BLACK);

		root.translateXProperty().bind(scene.widthProperty().divide(2));
		root.translateYProperty().bind(scene.heightProperty().divide(2));
		{
			Duration time = new Duration(10000);

		
			KeyValue keyValue = new KeyValue(rotate.angleProperty(), -360);
			KeyFrame frame = new KeyFrame(time, keyValue);

			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(frame);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
		}
		{
			Duration time = new Duration(2000);
			KeyValue keyZRootValue = new KeyValue(root.translateZProperty(), -1000);
			KeyFrame frame = new KeyFrame(time, keyZRootValue);
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(frame);
			timeline.setAutoReverse(true);
			timeline.setCycleCount(Timeline.INDEFINITE);
			//timeline.play();
		}
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();

		scene.setCursor(Cursor.NONE);
		scene.setCamera(camera);
		scene.setFill(Color.BLACK);

		SimpleDoubleProperty x = new SimpleDoubleProperty(scene.getWidth());
		SimpleDoubleProperty y = new SimpleDoubleProperty(scene.getHeight());

		scene.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				y.set(mouseEvent.getSceneY());
				x.set(mouseEvent.getSceneX());
			}
		});

		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

			}
		});

		SimpleLongProperty startTime = new SimpleLongProperty(System.currentTimeMillis());
		SimpleLongProperty animationTime = new SimpleLongProperty(0);
		SimpleLongProperty fpsCount = new SimpleLongProperty(0);
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				fpsCount.set(fpsCount.get() + 1);
				final double width = primaryStage.getWidth();
				final double height = primaryStage.getHeight();
				final double radius = Math.sqrt(2) * Math.max(Math.max(width - x.get(), x.get()), Math.max(height - y.get(), height));
				for (int i = 0; i < STAR_COUNT; i++) {
					final Node node = nodes[i];
					final double angle = angles[i];
					final long t = (now - start[i]) % 2000000000;
					final double d = t * radius / 2000000000.0;
					node.setTranslateX(Math.cos(angle) * d + x.doubleValue());
					node.setTranslateY(Math.sin(angle) * d + y.doubleValue());
				}
				final long newValue = (System.currentTimeMillis() - startTime.get()) / 1000;
				if (newValue != animationTime.get()) {
					animationTime.set(newValue);
					text.setText("FPS: " + fpsCount.get());
					fpsCount.set(0);
				}
			}
		}.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}