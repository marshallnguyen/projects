package edu.orangecoastcollege.cs272.btomita.capstone.view;

import java.util.ArrayList;

import edu.orangecoastcollege.cs272.btomita.capstone.controller.Controller;
import edu.orangecoastcollege.cs272.btomita.capstone.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Sign in scene Class
 * @author Brett
 *
 */
public class SignInScene {

	private static Controller controller = Controller.getInstance();
	//private User mCurrentUser;

	@FXML
	private TextField emailAddressTF;
	@FXML
	private TextField passwordTF;
	@FXML
	private Label emailErrorLabel;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private Label signInErrorLabel;

	/**
	 * Signs in user to the program
	 * @return
	 */
	@FXML
	public Object signIn() {
		String email = emailAddressTF.getText();
		String password = passwordTF.getText();

		emailErrorLabel.setVisible(email.isEmpty());
		passwordErrorLabel.setVisible(password.isEmpty());

		if (emailErrorLabel.isVisible() || passwordErrorLabel.isVisible())
			return null;

		String result = controller.signInUser(email, password);
		if (result.equalsIgnoreCase("SUCCESS")) {
			signInErrorLabel.setVisible(false);
			// Set currentUser to not null
			//User u = controller.getCurrentUser();
//			ObservableList<User> allUsers = controller.getAllUsers();
//			for (User u : allUsers)
//				if(u.getEmail().equals(email))
//				{
//					try
//					{
//						ArrayList<ArrayList<String>> resultsList = controller.getUsersDB().getRecord(String.valueOf(u.getId()));
//						String storedPassword = resultsList.get(0).get(3);
//						if (password.equals(storedPassword))
//						{
//							System.out.println(u.getId());
//							controller.setUser(u);
//						}
//					}
//					catch(Exception e)
//					{
//						e.printStackTrace();
//					}
//				}
			
			//System.out.println(controller.getCurrentUserID());			
			// Go to next scene
			ViewNavigator.loadScene("Food Finder", new ViewFX().createMainScene());
			return this;
		}
		signInErrorLabel.setText(result);
		signInErrorLabel.setVisible(true);
		return null;
	}
	
	/**
	 * Loads page to sign up for program
	 * @return
	 */
	@FXML
	public Object loadSignUp()
	{
		ViewNavigator.loadFXMLScene("Sign Up", ViewNavigator.SIGN_UP_SCENE);
		return this;
	}

}
