package edu.orangecoastcollege.cs272.btomita.capstone.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ViewNavigator class to change scenes
 * @author Brett
 *
 */
public class ViewNavigator {
	/**
	 * Sign up scene address
	 */
	public static final String SIGN_UP_SCENE = "SignUpScene.fxml";
	/**
	 * Sign in scene address
	 */
	public static final String SIGN_IN_SCENE = "SignInScene.fxml";
	/**
	 * Restaurants Scene address
	 */
	public static final String RESTAURANT_LIST_SCENE = "RestaurantsListScene.fxml";
	/**
	 * User visited restaurants scene address
	 */
	public static final String USER_VISITED_SCENE = "ViewVisitedScene.fxml";


	/**
	 * Contains mainStage
	 */
	public static Stage mainStage;

	/**
	 * Sets main stage
	 * @param stage
	 */
	public static void setStage(Stage stage) {
		mainStage = stage;
	}
	
	/**
	 * Loads scene and sets title of scene
	 * @param title
	 * @param scene
	 */
	public static void loadScene(String title, Scene scene)
	{
		mainStage.setTitle(title);
		mainStage.setScene(scene);
		mainStage.show();
	}

	/**
	 * Loads FXML scene and sets title of FXML scene
	 * @param title
	 * @param sceneFXML
	 */
	public static void loadFXMLScene(String title, String sceneFXML) {

		try {
			mainStage.setTitle(title);
			Scene scene = new Scene(FXMLLoader.load(ViewNavigator.class.getResource(sceneFXML)));
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}

}
