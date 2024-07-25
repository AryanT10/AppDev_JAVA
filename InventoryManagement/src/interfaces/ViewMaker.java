package interfaces;

import controllers.HomeController;
import javafx.scene.Scene;

public interface ViewMaker {

	Scene getScene(HomeController homeController);
}
