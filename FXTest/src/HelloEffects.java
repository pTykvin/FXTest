import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HelloEffects extends Application {

	Stage stage;
	Scene scene;

	@Override
	public void start(Stage stage) {
		stage.show();

		scene = new Scene(new Group());
		ObservableList<Node> content = ((Group) scene.getRoot()).getChildren();

		content.add(chainEffects());
		stage.setScene(scene);
	}

	static Node chainEffects() {

		Rectangle rect = new Rectangle();
		rect.setFill(Color.RED);
		rect.setWidth(200);
		rect.setHeight(100);
		rect.setX(20.0f);
		rect.setY(20.0f);

		DropShadow ds = new DropShadow();
		ds.setOffsetY(5.0);
		ds.setOffsetX(5.0);
		ds.setColor(Color.GRAY);

		Reflection reflection = new Reflection();
		ds.setInput(reflection);
		rect.setEffect(ds);

		return rect;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}