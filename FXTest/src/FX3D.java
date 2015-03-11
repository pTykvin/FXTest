
import com.sun.javafx.scene.SceneUtils;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FX3D extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("3D test");
		StackPane root = new StackPane();
		root.setPrefSize(600, 600);

		
		
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

		root.getChildren().addAll(blue, light, light2, ambientLight);
		root.setTranslateX(120);
		root.setTranslateY(-80);

		Scene scene = new Scene(root, 600, 600, true, SceneAntialiasing.BALANCED);
		scene.setCamera(camera);

		primaryStage.setScene(scene);
		primaryStage.show();
		scene.setFill(Color.BLACK);

		SimpleIntegerProperty i = new SimpleIntegerProperty(1);

		Rotate rotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);

		blue.getTransforms().add(rotate);
		root.getTransforms().add(new Rotate(20, 0, 0, 0, Rotate.Z_AXIS));

		Duration time = new Duration(10000);
		KeyValue keyValue = new KeyValue(rotate.angleProperty(), -360);
		KeyFrame frame = new KeyFrame(time, keyValue);

		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
