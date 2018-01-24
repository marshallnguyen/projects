package edu.orangecoastcollege.cs272.btomita.capstone.view;
import java.util.Random;

import edu.orangecoastcollege.cs272.btomita.capstone.controller.Controller;

import edu.orangecoastcollege.cs272.btomita.capstone.model.Restaurant;
import edu.orangecoastcollege.cs272.btomita.capstone.model.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * ViewFX class
 * @author Brett
 *
 */
public class ViewFX extends Application 
{
	Scene mainScene;
	ComboBox<String> categoriesCB;
	ComboBox<String> cityCB;
	Slider minPriceSlider = new Slider(1, 5, 0);
	Slider maxPriceSlider = new Slider(1, 5, 0);
	Slider reviewsSlider = new Slider(0, 1000, 0);
	ListView<Restaurant> restaurantsLV = new ListView<>();
	
	final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
	
	Stage mainStage;
	Restaurant selectedRestaurant;
	ObservableList<Restaurant> restaurantsList;
	ObservableList<String> categoriesList;
	ObservableList<String> priceList;
	ObservableList<String> cityList;
	User mCurrentUser;
	Controller controller;// = Controller.getInstance();
	Button pickButton = new Button("Pick Restaurant");
	Button pickNewButton = new Button ("Pick A Restaurant I Haven't Been To");
	Button resetButton = new Button("Reset");
	Button logOutButton = new Button("Log Out");
	Button addFavoriteRestaurantButton = new Button("Add to Favorites");
	Button addDislikedRestaurantButton = new Button("Add to Disliked");
	Button viewFavoriteRestaurantsButton = new Button("View Favorite Restaurants");
	Button viewDislikedRestaurantsButton = new Button("View Disliked Restaurants");
	Button viewVisitedRestaurantsButton = new Button("View Visited Restaurants");
	Button viewNotVisitedRestaurantsButton = new Button("View Unvisited Restaurants");
	Button removeFavoriteRestaurantButton = new Button("Remove Favorite Restaurant");
	Button removeDislikedRestaurantButton = new Button("Remove Disliked Restaurant");
	
	private boolean isMainScene = false;
	
	
	/**
	 * Launches program
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
//		// TODO Auto-generated method stub
		//mainStage = primaryStage;
//		
//		//Setup for all Restaurants:
		
//		controller = Controller.getInstance();
//		restaurantsList = controller.getAllRestaurants();
//		categoriesList = controller.getDistinctCategories();
//		priceList = controller.getDistinctPrices();
//		
//		cityList = controller.getDistinctCities();
//		//nextButton.setDisable(true);
//		
//		mainScene = createMainScene();
//		mainStage.setTitle("Food Finder");
//		mainStage.setScene(mainScene);
//		mainStage.show();

		ViewNavigator.setStage(primaryStage);
		ViewNavigator.loadFXMLScene("Welcome to Food Finder", ViewNavigator.SIGN_IN_SCENE);
	}
	
	/**
	 * Changes to sign in scene
	 */
	//privae StringConverter<Double> dollars
	public void viewLogInScene()
	{
	    ViewNavigator.loadFXMLScene("Welcome to Food Finder", ViewNavigator.SIGN_IN_SCENE);
	}
	
	/**
	 * Sets main scene from program (Scene to pick and/or view Restaurants)
	 * @return
	 */
	public Scene createMainScene()
	{
		controller = Controller.getInstance();
		restaurantsList = controller.getAllRestaurants();
		categoriesList = controller.getDistinctCategories();
		priceList = controller.getDistinctPrices();
		
		cityList = controller.getDistinctCities();
		
		restaurantsLV.setItems(restaurantsList);
		restaurantsLV.setPrefWidth(1000);
		restaurantsLV.setOnMouseClicked(e -> selectRestaurant());
		
		categoriesCB = new ComboBox<>(categoriesList);
		categoriesCB.setOnAction(e -> returnRestaurant());
		cityCB = new ComboBox<>(cityList);
		//cityCB = new ComboBox<>();
		cityCB.setOnAction(e -> returnRestaurant());
		
		minPriceSlider.setShowTickMarks(true);
		minPriceSlider.setShowTickLabels(true);
		minPriceSlider.setSnapToTicks(true);
		minPriceSlider.setLabelFormatter(new StringConverter<Double>(){
			/**
			 * Converts integer price to dollar signs
			 */
			@Override
			public String toString(Double n){
				if(n == 1) return "$";
				if(n == 2) return "$$";
				if(n == 3) return "$$$";
				if(n == 4) return "$$$$";
				return "$$$$$";
			}

			@Override
			public Double fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		minPriceSlider.setMajorTickUnit(1.0);
		minPriceSlider.setBlockIncrement(1.0);
		minPriceSlider.setMinorTickCount(0);
		minPriceSlider.setOnMouseDragged(e -> returnRestaurant());
		minPriceSlider.setOnMouseClicked(e -> returnRestaurant());
		
		maxPriceSlider.setShowTickMarks(true);
		maxPriceSlider.setShowTickLabels(true);
		maxPriceSlider.setValue(5);
		maxPriceSlider.setMajorTickUnit(1.0);
		maxPriceSlider.setBlockIncrement(1.0);
		maxPriceSlider.setSnapToTicks(true);
		maxPriceSlider.setMinorTickCount(0);
		maxPriceSlider.setOnMouseDragged(e -> returnRestaurant());
		maxPriceSlider.setOnMouseClicked(e -> returnRestaurant());
		maxPriceSlider.setLabelFormatter(new StringConverter<Double>(){
			/**
			 * Recursively converts dollar signs to an integer representation of price
			 */
			@Override
			public String toString(Double n){
				if(n < 1) return "";
				return "$" + toString(n-1);
			}

			@Override
			public Double fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		reviewsSlider.setShowTickMarks(true);
		reviewsSlider.setShowTickLabels(true);
		reviewsSlider.setMajorTickUnit(100.0);
		reviewsSlider.setBlockIncrement(10.0);
		reviewsSlider.setOnMouseDragged(e -> returnRestaurant());
		reviewsSlider.setOnMouseClicked(e -> returnRestaurant());
		
		pickButton.setOnAction(e -> viewRestaurantInformation());
		pickNewButton.setOnAction(e -> viewNewRestaurantInformation());
		resetButton.setOnAction(e -> reset());
		logOutButton.setOnAction(e -> viewLogInScene());
		addFavoriteRestaurantButton.setOnAction(e -> addFavoriteRestaurant());
		addDislikedRestaurantButton.setOnAction(e -> addDislikedRestaurantButton());
		viewFavoriteRestaurantsButton.setOnAction(e -> viewFavoriteRestaurantsScene());
		viewVisitedRestaurantsButton.setOnAction(e -> viewVisitedRestaurantsScene());
		viewNotVisitedRestaurantsButton.setOnAction(e -> viewNotVisitedRestaurantsScene());
		viewDislikedRestaurantsButton.setOnAction(e -> viewDislikedRestaurantsScene());
		
		GridPane pane = new GridPane();
		HBox box = new HBox();
		box.setSpacing(10);
		box.getChildren().add(pickButton);
		box.getChildren().add(pickNewButton);
		//box.getChildren().add(resetButton);
//		box.getChildren().add(addFavoriteRestaurantButton);
//		box.getChildren().add(addDislikedRestaurantButton);
		box.getChildren().add(viewVisitedRestaurantsButton);
		box.getChildren().add(viewNotVisitedRestaurantsButton);
		box.getChildren().add(viewFavoriteRestaurantsButton);
		box.getChildren().add(viewDislikedRestaurantsButton);

		//box.getChildren().add(logOutButton);
		
//		VBox nodesVBox = new VBox();
//		nodesVBox.setSpacing(20);
//		nodesVBox.getChildren().add(categoriesCB);
//		nodesVBox.getChildren().add(cityCB);
//		nodesVBox.getChildren().add(minPriceSlider);
//		nodesVBox.getChildren().add(maxPriceSlider);
//		nodesVBox.getChildren().add(reviewsSlider);
//		nodesVBox.getChildren().add(restaurantsLV);
//		nodesVBox.getChildren().add(box);
		
//		VBox labelsVBox = new VBox();
//		labelsVBox.setSpacing(30);
//		//labelsVBox.getChildren().add(new Label("Filters:"));
//		labelsVBox.getChildren().add(new Label("Categories:"));
//		labelsVBox.getChildren().add(new Label("City:"));
//		labelsVBox.getChildren().add(new Label("Min Price:"));
//		labelsVBox.getChildren().add(new Label("Max Price:"));
//		labelsVBox.getChildren().add(new Label("Minimum Reviews:"));
		
		pane.setVgap(10);
//		pane.setHgap(10);
		pane.setPadding(new Insets(10, 10, 10, 10));
//		pane.add(labelsVBox, 0, 0);
//		pane.add(nodesVBox, 1, 0);
		pane.add(new Label("Filters:"),  0, 0);
		pane.add(new Label("Categories:"), 0, 1);
		pane.add(categoriesCB,  1, 1);
		
		pane.add(new Label("City:"), 0, 2);
		pane.add(cityCB, 1, 2);
		
		pane.add(new Label("Min Price:"), 0, 3);
		pane.add(minPriceSlider, 1, 3);
		
		pane.add(new Label("Max Price:"), 0, 4);
		pane.add(maxPriceSlider, 1, 4);
		
		pane.add(new Label("Min Reviews: "), 0, 5);
		pane.add(reviewsSlider, 1, 5);
		pane.add(restaurantsLV, 1, 6);
		pane.add(box, 1, 7);
		pane.add(resetButton, 1, 8);
		pane.add(logOutButton, 2, 0);
		//pane.add(vbox, 1, 1);
		
		//pane.add(restaurantsLV, 0, 6, 2, 1);
//		pane.add(pickButton, 0, 7);
//		pane.add(resetButton, 1, 7);
//		pane.add(addFavoriteRestaurantButton, 2, 7);
//		pane.add(addDislikedRestaurantButton, 3, 7);
//		pane.add(viewVisitedRestaurantsButton, 4, 7);
//		pane.add(viewFavoriteRestaurantsButton, 5, 7);
//		pane.add(viewDislikedRestaurantsButton, 6, 7);
		//pane.add(box, 2, 1);
		//pane.add(logOutButton, 2, 7);
		
		return new Scene(pane, 1200, 600);
	}
	
	private Object addDislikedRestaurantButton()
    {
	    Restaurant selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
        if (controller.addDislikedRestaurant(selectedRestaurant))
            System.out.println("Successfully added disliked restaurant.");
        else
            System.out.println("Could not add disliked restaurant.");
        return this;
    }

//    private Object addFavoriteRestaurant()
//    {
//        //selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
//        if (controller.addFavoriteRestaurant(selectedRestaurant))
//            System.out.println("Successfully added favorite restaurant.");
//        else
//            System.out.println("Could not add favorite restaurant.");
//        return this;
//        
//    }
	private Object addFavoriteRestaurant()
	{
		try
		{
			Restaurant chosenRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
			if(controller.addFavoriteRestaurant(chosenRestaurant)) System.out.println("SUCCESS");
			else System.out.println("Could not add restaurant");
			//System.out.println(controller.getCurrentUser().getId());
			return this;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
    
    private void viewFavoriteRestaurantsScene()
    {
        restaurantsList = controller.getFavoriteRestaurantsForCurrentUser();
        restaurantsLV.setItems(restaurantsList);
        restaurantsLV.setPrefWidth(1000);
        restaurantsLV.setOnMouseClicked(e -> selectRestaurant());
        
        pickButton.setText("Pick Restaurant");
        pickButton.setOnAction(e -> viewRestaurantInformation());
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> backToRestaurantsList());
        
        removeFavoriteRestaurantButton.setOnAction(e -> removeFavoriteRestaurant());
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 00, 10, 10));
        pane.add(new Label("Favorite Restaurants:"),  0, 0);
        pane.add(restaurantsLV, 0, 6, 2, 1);
        pane.add(pickButton, 0, 7);
        pane.add(removeFavoriteRestaurantButton, 0, 8);
        pane.add(backButton, 0, 9);
        
        Scene scene = new Scene(pane, 1100, 750, Color.web("#666960"));
        ViewNavigator.loadScene("Favorite Restaurants", scene);
        //return new Scene(pane, 1050, 600);
    }
    
    private void viewVisitedRestaurantsScene()
    {
    	restaurantsList = controller.getVisitedRestaurantsForCurrentUser();
    	restaurantsLV.setItems(restaurantsList);
    	restaurantsLV.setPrefWidth(1000);
    	restaurantsLV.setOnMouseClicked(e -> selectRestaurant());
    	
    	pickButton.setText("Pick Restaurant");
    	pickButton.setOnAction(e -> viewRestaurantInformation());
    	
    	Button backButton = new Button("Back");
    	backButton.setOnAction(e -> back());
    	
    	GridPane pane = new GridPane();
    	pane.setVgap(10);
    	pane.setPadding(new Insets(10, 00, 10, 10));
        pane.add(new Label("Visited Restaurants:"),  0, 0);
        pane.add(restaurantsLV, 0, 6, 2, 1);
        pane.add(pickButton, 0, 7);
        pane.add(backButton, 0, 9);
        //pane.add(removeDislikedRestaurantButton, 0, 8);
        
        Scene scene = new Scene(pane, 1100, 750, Color.web("#666960"));
        ViewNavigator.loadScene("Visited Restaurants", scene);
    }
    
    private void viewNotVisitedRestaurantsScene()
    {
    	restaurantsList = controller.getNotVisitedRestaurantsForCurrentUser();
    	restaurantsLV.setItems(restaurantsList);
    	restaurantsLV.setPrefWidth(1000);
    	restaurantsLV.setOnMouseClicked(e -> selectRestaurant());
    	
    	pickButton.setText("Pick Restaurant");
    	pickButton.setOnAction(e -> viewRestaurantInformation());
    	
    	Button backButton = new Button("Back");
    	backButton.setOnAction(e -> back());
    	
    	GridPane pane = new GridPane();
    	pane.setVgap(10);
    	pane.setPadding(new Insets(10, 00, 10, 10));
        pane.add(new Label("Restaurants Not Visited Yet:"),  0, 0);
        pane.add(restaurantsLV, 0, 6, 2, 1);
        pane.add(pickButton, 0, 7);
        pane.add(backButton, 0, 9);
        //pane.add(removeDislikedRestaurantButton, 0, 8);
        
        Scene scene = new Scene(pane, 1100, 750, Color.web("#666960"));
        ViewNavigator.loadScene("Restaurants Not Visited Yet", scene);
    }
    
    private void viewDislikedRestaurantsScene()
    {
    	restaurantsList = controller.getDislikedRestaurantsForCurrentUser();
        restaurantsLV.setItems(restaurantsList);
        restaurantsLV.setPrefWidth(1000);
        restaurantsLV.setOnMouseClicked(e -> selectRestaurant());
        
        removeDislikedRestaurantButton.setOnAction(e -> removeDislikedRestaurant() );
        pickButton.setText("Pick Restaurant");
        pickButton.setOnAction(e -> viewRestaurantInformation());
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> backToRestaurantsList());
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 00, 10, 10));
        pane.add(new Label("Disliked Restaurants:"),  0, 0);
        pane.add(restaurantsLV, 0, 6, 2, 1);
        pane.add(pickButton, 0, 7);
        pane.add(backButton, 0, 9);
        pane.add(removeDislikedRestaurantButton, 0, 8);
        
        Scene scene = new Scene(pane, 1100, 750, Color.web("#666960"));
        ViewNavigator.loadScene("Disliked Restaurants", scene);
    }
    
    private Object removeDislikedRestaurant()
	{
		try
		{
			Restaurant chosenRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
			if(controller.removeDislikedRestaurant(chosenRestaurant)) System.out.println("SUCCESS");
			else System.out.println("Could not remove disliked restaurant");
			viewDislikedRestaurantsScene();
			return this;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
    
    private Object removeFavoriteRestaurant()
    {
    	try
    	{
    		Restaurant chosenRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
    		if(controller.removeFavoriteRestaurant(chosenRestaurant)) System.out.println("SUCCESS");
			else System.out.println("Could not remove disliked restaurant");
			viewFavoriteRestaurantsScene();
			return this;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return this;
    }

    //private void logOut()
    //{
    //    ViewNavigator.loadFXMLScene("Sign In", ViewNavigator.SIGN_IN_SCENE);
    //}

//	private Scene createYelpScene()
//	{
//		Browser browser = new Browser();
//		//webEngine.load("www.yelp.com");
//		Scene webScene = new Scene(browser, 1000, 800, Color.web("#666970"));
//		return webScene;
//	}
	
	private void reset()
	{
		categoriesCB.getSelectionModel().select(-1);
		cityCB.getSelectionModel().select(-1);
		minPriceSlider.setValue(1);
		maxPriceSlider.setValue(5);
		reviewsSlider.setValue(1);
		restaurantsLV.getSelectionModel().clearSelection();
		restaurantsList = controller.filter((int) minPriceSlider.getValue(), (int) maxPriceSlider.getValue(), (int) reviewsSlider.getValue(), 
                cityCB.getSelectionModel().getSelectedItem(), categoriesCB.getSelectionModel().getSelectedItem());
        restaurantsLV.setItems(restaurantsList);
	}
	
	private void backToRestaurantsList()
	{
//		mainStage.setScene(mainScene);
//		mainStage.setTitle("Food Finder");
		ViewNavigator.loadScene("Food Finder", createMainScene());
	}
	
	/**
	 * Shows restaurant information offline and provides options to view Restaurant yelp page, get directions, and/or add
	 * Restaurant to liked/disliked tables
	 */
	public void viewRestaurantInformation()
	{
		Button backButton = new Button("Back");
        //Button yelpButton = new Button("Visit Yelp");
        Button directionsButton = new Button("Get Directions");
        backButton.setOnAction(e -> backToRestaurantsList());
        //yelpButton.setOnAction(e -> viewYelp());
        directionsButton.setOnAction(e -> viewDirections());
        //WebView browser = new WebView();
        //webEngine = browser.getEngine();
        
        if(restaurantsLV.getSelectionModel().isEmpty() && isMainScene) 
        {
            selectedRestaurant = returnRestaurant();
            if (selectedRestaurant == null) {
                // Display alert to user (no restaurants found)
                return;
            }
            else
            	controller.addRestaurantToVisited(selectedRestaurant);
        }
        else if (restaurantsLV.getSelectionModel().isEmpty())
        {
            selectedRestaurant = returnRestaurant();
            if(selectedRestaurant == null)
            	return;
            controller.addRestaurantToVisited(selectedRestaurant);
        }
        else
        {
        	selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
        	if(selectedRestaurant == null)
        	{
        		return;
        	}
        	controller.addRestaurantToVisited(selectedRestaurant);
        }
        //webEngine.load(selectedRestaurant.getSite());
        Label labelName = new Label("Name:");
        Label labelPrice = new Label("Price:");
        Label labelReviews = new Label("Reviews:");
        Label labelCategories = new Label("Categories:");
        Label labelStreet = new Label("Street:");
        Label labelCity = new Label("City:");
        Label labelPhoneNumber = new Label("Phone Number:");
        Label labelSite = new Label("Website:");
        Text textName = new Text(selectedRestaurant.getName());
        Text textPrice = new Text(selectedRestaurant.getPrice());
        Text textReviews = new Text(String.valueOf(selectedRestaurant.getReviews()));
        Text textCategories = new Text(selectedRestaurant.getCategories());
        Text textStreet = new Text(selectedRestaurant.getStreet());
        Text textCity = new Text(selectedRestaurant.getCity());
        Text textPhoneNumber = new Text(selectedRestaurant.getPhoneNumber());
        Text textSite = new Text(selectedRestaurant.getSite());
        textSite.setFill(Color.BLUE);
        textSite.setUnderline(true);
        textSite.setOnMouseClicked(e -> viewYelp());
        
        /*
         * private String mName;
            private String mPrice;
            private int mReviews;
            private String mCategories;
            private String mStreet;
            private String mCity;
            private String mPhoneNumber;
            private String mSite;
         */
        
        //Scene yelpScene = new Scene(webEngine, 1000, 800, Color.web("#666970"));//createYelpScene();
        GridPane pane = new GridPane();
        pane.setVgap(10);
//      pane.setHgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        HBox box = new HBox();
        box.getChildren().add(backButton);
        box.getChildren().add(addFavoriteRestaurantButton);
        box.getChildren().add(addDislikedRestaurantButton);
        //box.getChildren().add(yelpButton);
        box.getChildren().add(directionsButton);
        box.setSpacing(5);
        
        pane.add(labelName, 0, 1);
        pane.add(textName, 1, 1);
        pane.add(labelPrice, 0, 2);
        pane.add(textPrice, 1, 2);
        pane.add(labelReviews, 0, 3);
        pane.add(textReviews, 1, 3);
        pane.add(labelCategories, 0, 4);
        pane.add(textCategories, 1, 4);
        pane.add(labelStreet, 0, 5);
        pane.add(textStreet, 1, 5);
        pane.add(labelCity, 0, 6);
        pane.add(textCity, 1, 6);
        pane.add(labelPhoneNumber, 0, 7);
        pane.add(textPhoneNumber, 1, 7);
        pane.add(labelSite, 0, 8);
        pane.add(textSite, 1, 8);
        
        pane.add(box, 0, 10);
        pane.setAlignment(Pos.CENTER);
        //pane.add(directionsButton, 1, 2);
        //pane.setAlignment(Pos.BASELINE_CENTER);
        Scene restaurantInfoScene = new Scene(pane, 725, 500, Color.web("#666960"));
//      mainStage.setTitle("Yelp");
//      mainStage.setScene(yelpScene);
        //mainStage.setTitle("Yelp");
        //mainStage.show();
        //ViewNavigator.setStage(mainStage);
        ViewNavigator.loadScene("Restaurant Information", restaurantInfoScene);
        isMainScene = false;
	
//	    Button backButton = new Button("Back");
//        Button directionsButton = new Button("Visit Yelp");
//        backButton.setOnAction(e -> back());
//        directionsButton.setOnAction(e -> viewDirections());
//        WebView browser = new WebView();
//        WebEngine webEngine = browser.getEngine();
//        if(restaurantsLV.getSelectionModel().isEmpty()) 
//        {
//            selectedRestaurant = returnRestaurant();
//            if (selectedRestaurant == null) {
//                // Display alert to user (no restaurants found)
//                return;
//            }
//        }
//        else
//            selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
//        webEngine.load(selectedRestaurant.getSite());
//        
//        //Scene yelpScene = new Scene(webEngine, 1000, 800, Color.web("#666970"));//createYelpScene();
//        GridPane pane = new GridPane();
//        browser.setPrefSize(1100, 700);
//        pane.add(browser, 0, 1);
//        pane.setVgap(10);
////      pane.setHgap(10);
//        pane.setPadding(new Insets(10, 10, 10, 10));
//        HBox box = new HBox();
//        box.getChildren().add(backButton);
//        box.getChildren().add(directionsButton);
//        box.setSpacing(5);
//        pane.add(box, 0, 2);
//        //pane.add(directionsButton, 1, 2);
//        //pane.setAlignment(Pos.BASELINE_CENTER);
//        Scene yelpScene = new Scene(pane, 1100, 750, Color.web("#666960"));
////      mainStage.setTitle("Yelp");
////      mainStage.setScene(yelpScene);
//        //mainStage.setTitle("Yelp");
//        //mainStage.show();
//        //ViewNavigator.setStage(mainStage);
//        ViewNavigator.loadScene("Yelp", yelpScene);
	}
	
	/**
	 * Shows information of unvisited restaurants
	 */
	public void viewNewRestaurantInformation()
	{
		Button backButton = new Button("Back");
        //Button yelpButton = new Button("Visit Yelp");
        Button directionsButton = new Button("Get Directions");
        backButton.setOnAction(e -> backToRestaurantsList());
        //yelpButton.setOnAction(e -> viewYelp());
        directionsButton.setOnAction(e -> viewDirections());
        addFavoriteRestaurantButton.setOnAction(e -> addFavoriteRestaurant());
		addDislikedRestaurantButton.setOnAction(e -> addDislikedRestaurantButton());
        //WebView browser = new WebView();
        //webEngine = browser.getEngine();
        
        if(restaurantsLV.getSelectionModel().isEmpty() && isMainScene) 
        {
            selectedRestaurant = returnNewRestaurant();
            if (selectedRestaurant == null) {
                // Display alert to user (no restaurants found)
                return;
            }
            else
            	controller.addRestaurantToVisited(selectedRestaurant);
        }
        else if (restaurantsLV.getSelectionModel().isEmpty())
        {
            selectedRestaurant = returnNewRestaurant();
            if(selectedRestaurant == null)
            	return;
            controller.addRestaurantToVisited(selectedRestaurant);
        }
        else
        {
        	selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
        	if(selectedRestaurant == null)
        	{
        		return;
        	}
        	controller.addRestaurantToVisited(selectedRestaurant);
        }
        //webEngine.load(selectedRestaurant.getSite());
        Label labelName = new Label("Name:");
        Label labelPrice = new Label("Price:");
        Label labelReviews = new Label("Reviews:");
        Label labelCategories = new Label("Categories:");
        Label labelStreet = new Label("Street:");
        Label labelCity = new Label("City:");
        Label labelPhoneNumber = new Label("Phone Number:");
        Label labelSite = new Label("Website:");
        Text textName = new Text(selectedRestaurant.getName());
        Text textPrice = new Text(selectedRestaurant.getPrice());
        Text textReviews = new Text(String.valueOf(selectedRestaurant.getReviews()));
        Text textCategories = new Text(selectedRestaurant.getCategories());
        Text textStreet = new Text(selectedRestaurant.getStreet());
        Text textCity = new Text(selectedRestaurant.getCity());
        Text textPhoneNumber = new Text(selectedRestaurant.getPhoneNumber());
        Text textSite = new Text(selectedRestaurant.getSite());
        textSite.setFill(Color.BLUE);
        textSite.setUnderline(true);
        textSite.setOnMouseClicked(e -> viewYelp());
        
        /*
         * private String mName;
            private String mPrice;
            private int mReviews;
            private String mCategories;
            private String mStreet;
            private String mCity;
            private String mPhoneNumber;
            private String mSite;
         */
        
        //Scene yelpScene = new Scene(webEngine, 1000, 800, Color.web("#666970"));//createYelpScene();
        GridPane pane = new GridPane();
        pane.setVgap(10);
//      pane.setHgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        HBox box = new HBox();
        box.getChildren().add(backButton);
        //box.getChildren().add(yelpButton);
        box.getChildren().add(directionsButton);
        box.setSpacing(10);
        
        pane.add(labelName, 0, 1);
        pane.add(textName, 1, 1);
        pane.add(labelPrice, 0, 2);
        pane.add(textPrice, 1, 2);
        pane.add(labelReviews, 0, 3);
        pane.add(textReviews, 1, 3);
        pane.add(labelCategories, 0, 4);
        pane.add(textCategories, 1, 4);
        pane.add(labelStreet, 0, 5);
        pane.add(textStreet, 1, 5);
        pane.add(labelCity, 0, 6);
        pane.add(textCity, 1, 6);
        pane.add(labelPhoneNumber, 0, 7);
        pane.add(textPhoneNumber, 1, 7);
        pane.add(labelSite, 0, 8);
        pane.add(textSite, 1, 8);
        
        pane.add(box, 0, 10);
        pane.setAlignment(Pos.CENTER);
        //pane.add(directionsButton, 1, 2);
        //pane.setAlignment(Pos.BASELINE_CENTER);
        Scene restaurantInfoScene = new Scene(pane, 700, 500, Color.web("#666960"));
//      mainStage.setTitle("Yelp");
//      mainStage.setScene(yelpScene);
        //mainStage.setTitle("Yelp");
        //mainStage.show();
        //ViewNavigator.setStage(mainStage);
        ViewNavigator.loadScene("Restaurant Information", restaurantInfoScene);
        isMainScene = false;
	
//	    Button backButton = new Button("Back");
//        Button directionsButton = new Button("Visit Yelp");
//        backButton.setOnAction(e -> back());
//        directionsButton.setOnAction(e -> viewDirections());
//        WebView browser = new WebView();
//        WebEngine webEngine = browser.getEngine();
//        if(restaurantsLV.getSelectionModel().isEmpty()) 
//        {
//            selectedRestaurant = returnRestaurant();
//            if (selectedRestaurant == null) {
//                // Display alert to user (no restaurants found)
//                return;
//            }
//        }
//        else
//            selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
//        webEngine.load(selectedRestaurant.getSite());
//        
//        //Scene yelpScene = new Scene(webEngine, 1000, 800, Color.web("#666970"));//createYelpScene();
//        GridPane pane = new GridPane();
//        browser.setPrefSize(1100, 700);
//        pane.add(browser, 0, 1);
//        pane.setVgap(10);
////      pane.setHgap(10);
//        pane.setPadding(new Insets(10, 10, 10, 10));
//        HBox box = new HBox();
//        box.getChildren().add(backButton);
//        box.getChildren().add(directionsButton);
//        box.setSpacing(5);
//        pane.add(box, 0, 2);
//        //pane.add(directionsButton, 1, 2);
//        //pane.setAlignment(Pos.BASELINE_CENTER);
//        Scene yelpScene = new Scene(pane, 1100, 750, Color.web("#666960"));
////      mainStage.setTitle("Yelp");
////      mainStage.setScene(yelpScene);
//        //mainStage.setTitle("Yelp");
//        //mainStage.show();
//        //ViewNavigator.setStage(mainStage);
//        ViewNavigator.loadScene("Yelp", yelpScene);
	}
	
	/**
	 * Changes to a scene to view Yelp webpage and offers options to get directions or go back
	 */
	public void viewYelp()
	{
		Button backButton = new Button("Back");
		Button directionsButton = new Button("Get Directions");
		backButton.setOnAction(e -> back());
		directionsButton.setOnAction(e -> viewDirections());
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
//		if(restaurantsLV.getSelectionModel().isEmpty()) 
//		{
//			selectedRestaurant = returnRestaurant();
//			if (selectedRestaurant == null) 
//			{
//				// Display alert to user (no restaurants found)
//				return;
//			}
//			else 
//				controller.addRestaurantToVisited(selectedRestaurant);
//		}
//		else
//		{
//			selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
//			controller.addRestaurantToVisited(selectedRestaurant);
//		}
		webEngine.load(selectedRestaurant.getSite());
		
		//Scene yelpScene = new Scene(webEngine, 1000, 800, Color.web("#666970"));//createYelpScene();
		GridPane pane = new GridPane();
		browser.setPrefSize(1100, 700);
		pane.add(browser, 0, 1);
		pane.setVgap(10);
//		pane.setHgap(10);
		pane.setPadding(new Insets(10, 10, 10, 10));
		HBox box = new HBox();
		box.getChildren().add(backButton);
		box.getChildren().add(directionsButton);
		box.setSpacing(5);
		pane.add(box, 0, 2);
		//pane.add(directionsButton, 1, 2);
		//pane.setAlignment(Pos.BASELINE_CENTER);
		Scene yelpScene = new Scene(pane, 1100, 750, Color.web("#666960"));
//		mainStage.setTitle("Yelp");
//		mainStage.setScene(yelpScene);
		//mainStage.setTitle("Yelp");
		//mainStage.show();
		//ViewNavigator.setStage(mainStage);
		ViewNavigator.loadScene("Yelp", yelpScene);
	}
	
	/**
	 * Changes scene to a google maps webpage with restaurant location
	 */
	public void viewDirections()
	{
		//Button directionsButton = new Button("Get Directions");
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> back());
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		String[] splitName = selectedRestaurant.getName().split(" ");
		String[] splitCity = selectedRestaurant.getCity().split(" ");
		//String url = "http://www.google.com/search?q=directions+to";
		String url = "https://www.google.com/maps/place/";
		url += splitName[0];
		for(int i = 1; i < splitName.length; i++)
		{
			url += "+" + splitName[i] + "+";
		}
		//url += splitCity[splitCity.length - 1];
		
		for(int i = 0; i < splitCity.length-1; i++)
		{
			url += splitCity[i] + "+";
		}
		url += splitCity[splitCity.length - 1];
		
		webEngine.load(url);
		
		GridPane pane = new GridPane();
		browser.setPrefSize(1100, 700);
		pane.add(browser, 0, 1);
		pane.add(backButton, 0, 2);
		pane.setVgap(10);
//		pane.setHgap(10);
		pane.setPadding(new Insets(10, 10, 10, 10));
		//pane.add(directionsButton, 0, 2);
		//pane.setAlignment(Pos.BASELINE_CENTER);
		Scene directionsScene = new Scene(pane, 1100, 750, Color.web("#666960"));
//		mainStage.setScene(directionsScene);
//		mainStage.setTitle("Directions to " + selectedRestaurant.getName());
//		mainStage.show();
		//ViewNavigator.setStage(mainStage);
		ViewNavigator.loadScene("Directions to " + selectedRestaurant.getName(), directionsScene);
			
	}
	
	private void back()
	{
//		mainStage.setScene(mainScene);
//		mainStage.setTitle("Food Finder");
		ViewNavigator.loadScene("Food Finder", createMainScene());
	}
	
	/**
	 * Selects specific restaurant
	 */
	public void selectRestaurant()
	{
		selectedRestaurant = restaurantsLV.getSelectionModel().getSelectedItem();
		pickButton.setDisable(false);
	}
	
	/**
	 * Sets restaurants List View to only restaurants that match all current criteria
	 * @return
	 */
	private Restaurant returnRestaurant()
	{
		restaurantsList = controller.filter((int) minPriceSlider.getValue(), (int) maxPriceSlider.getValue(), (int) reviewsSlider.getValue(), 
				cityCB.getSelectionModel().getSelectedItem(), categoriesCB.getSelectionModel().getSelectedItem());
		restaurantsLV.setItems(restaurantsList);
		
		int length = restaurantsList.size();
		if (length == 0)
			return null;
		Random rng = new Random();
		int randPlace = rng.nextInt(length);
		
		return restaurantsList.get(randPlace);
	}
	
	private Restaurant returnNewRestaurant()
	{
		restaurantsList = controller.filterFromNotVisited((int) minPriceSlider.getValue(), (int) maxPriceSlider.getValue(), (int) reviewsSlider.getValue(), 
				cityCB.getSelectionModel().getSelectedItem(), categoriesCB.getSelectionModel().getSelectedItem());
		restaurantsLV.setItems(restaurantsList);
		
		ObservableList<Restaurant> userDislikedList = controller.getDislikedRestaurantsForCurrentUser();
		
		int length = restaurantsList.size();
		int randPlace;
		do {
		if (length == 0)
			return null;
		Random rng = new Random();
		randPlace = rng.nextInt(length);
		} while (userDislikedList.contains(restaurantsList.get(randPlace)));
		
		return restaurantsList.get(randPlace);
	}
	
	/**
	 * Launches application
	 * @param args
	 */
	public static void main(String[] args)
	{
		Application.launch(args);
	}

}

/**
 * Class for web browser used for Yelp and Directions scenes
 * @author Brett
 *
 */
class Browser extends StackPane {
 
	 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    /**
     * Sets up browser
     */
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load("www.yelp.com");
        //add the web view to the scene
        getChildren().add(browser);
 
    }
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    /**
     * Sets layout of browser
     */
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    /**
     * returns preferred width of browser
     */
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    /**
     * Returns preferred height of browser
     */
    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}
