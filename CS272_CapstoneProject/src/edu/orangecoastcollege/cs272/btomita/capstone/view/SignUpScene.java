package edu.orangecoastcollege.cs272.btomita.capstone.view;

import edu.orangecoastcollege.cs272.btomita.capstone.controller.Controller;
import edu.orangecoastcollege.cs272.btomita.capstone.view.ViewFX;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * SignUpScene class
 * @author Brett
 *
 */
public class SignUpScene 
{
	private static Controller controller = Controller.getInstance();

	@FXML
	private TextField nameTF;
	@FXML
	private TextField emailAddressTF;
	@FXML
	private TextField passwordTF;
	@FXML
	private Label nameErrorLabel;
	@FXML
	private Label emailErrorLabel;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private Label signUpErrorLabel;

	/**
	 * Method to sign up a user
	 * @return
	 */
	@FXML
	public Object signUp() {

		String name = nameTF.getText();
		String email = emailAddressTF.getText();
		String password = passwordTF.getText();
		
		// Use controller to sign up user
		String result = controller.signUpUser(name, email, password);
		if(result.equalsIgnoreCase("SUCCESS"))
		{
			ViewNavigator.loadScene("Food Finder", new ViewFX().createMainScene());
		}
		else
		{
			signUpErrorLabel.setText(result);
			signUpErrorLabel.setVisible(true);
		}
		return this;
	}
	
	/**
	 * Loads sign in scene
	 * @return
	 */
	@FXML
	public Object loadSignIn()
	{
		//TODO: Complete this method
		ViewNavigator.loadFXMLScene("Sign In", ViewNavigator.SIGN_IN_SCENE);
		return this;
	}
}