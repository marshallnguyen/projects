package edu.orangecoastcollege.cs272.btomita.capstone.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import edu.orangecoastcollege.cs272.btomita.capstone.model.DBModel;
import edu.orangecoastcollege.cs272.btomita.capstone.model.Restaurant;
import edu.orangecoastcollege.cs272.btomita.capstone.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * Controller for Food Finder
 * @author Brett
 *
 */
public final class Controller
{
	private static Controller theOne;

    private static final String DB_NAME = "cs272.db";
    private static final String TABLE_NAME = "oc";
    private static final String[] FIELD_NAMES = { "id", "name", "price", "reviews", "category", "street", "city", "phone", "site" };
    private static final String [] FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "INTEGER", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT" };
    
    private static final String USER_TABLE_NAME = "users";
    private static final String[] USER_FIELD_NAMES = {"id", "name", "email", "password"};
    private static final String[] USER_FIELD_TYPES = {"INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT"};
    
    private static final String USER_VISITED_TABLE_NAME = "user_visited";
    private static final String[] USER_VISITED_FIELD_NAMES = { "user_id", "restaurant_id"};
    private static final String[] USER_VISITED_FIELD_TYPES = {"INTEGER", "INTEGER"};
    
    private static final String USER_NOT_VISITED_TABLE_NAME = "user_not_visited";
    private static final String[] USER_NOT_VISITED_FIELD_NAMES = { "user_id", "restaurant_id"};
    private static final String[] USER_NOT_VISITED_FIELD_TYPES = {"INTEGER", "INTEGER"};
    
    private static final String USER_RESTAURANTS_TABLE_NAME = "user_restaurants";
    private static final String[] USER_RESTAURANTS_FIELD_NAMES = {"user_id", "restaurant_id"};
    private static final String[] USER_RESTAURANTS_FIELD_TYPES = {"INTEGER", "INTEGER"};
    
    private static final String USER_LIKED_RESTAURANTS_TABLE_NAME = "user_liked_restaurants";
    private static final String[] USER_LIKED_RESTAURANTS_FIELD_NAMES = {"user_id", "restaurant_id"};
    private static final String[] USER_LIKED_RESTAURANTS_FIELD_TYPES = {"INTEGER", "INTEGER"};
    
    private static final String USER_DISLIKED_RESTAURANTS_TABLE_NAME = "user_disliked_restaurants";
    private static final String[] USER_DISLIKED_RESTAURANTS_FIELD_NAMES = {"user_id", "restaurant_id"};
    private static final String[] USER_DISLIKED_RESTAURANTS_FIELD_TYPES = {"INTEGER", "INTEGER"};
    
    private static final String DATA_FILE = "food1.csv";

    private ObservableList<Restaurant> mAllRestaurantsList;
    private ObservableList<User> mAllUsersList;
    
//    private ObservableList<Restaurant> hbRestaurantsList;
//    private ObservableList<Restaurant> cmRestaurantsList;
//    private ObservableList<Restaurant> fvRestaurantsList;
//    private ObservableList<Restaurant> irvRestaurantsList;
//    private ObservableList<Restaurant> anaRestaurantsList;
    private User mCurrentUser;
    //private int mCurrentUserID;
    private DBModel mDB;
    private DBModel mUsersDB;
    private DBModel mUserVisitedDB;
    private DBModel mUserNotVisitedDB;
    private DBModel mUserFavoriteRestaurantsDB;
    private DBModel mUserDislikedRestaurantsDB;


    private Controller(){};

    /**
     * Initializes controller and sets up tables
     * @return
     */
    public static Controller getInstance()
    {
        if(theOne == null)
        {
            theOne = new Controller();
            theOne.mAllRestaurantsList = FXCollections.observableArrayList();
            theOne.mAllUsersList = FXCollections.observableArrayList();

            try
            {
            	theOne.setUsersDB(new DBModel(DB_NAME, USER_TABLE_NAME, USER_FIELD_NAMES, USER_FIELD_TYPES));
            	ArrayList<ArrayList<String>> resultsList = theOne.getUsersDB().getAllRecords();
            	for(ArrayList<String> values : resultsList)
            	{
            		int id = Integer.parseInt(values.get(0));
            		String name = values.get(1);
            		String email = values.get(2);
            		theOne.mAllUsersList.add(new User(id, name, email));
            	}
            	
                theOne.mDB = new DBModel(DB_NAME, TABLE_NAME, FIELD_NAMES, FIELD_TYPES);
                theOne.initializeDBFromFile();
                resultsList = theOne.mDB.getAllRecords();
                for(ArrayList<String> values : resultsList)
                {
                	int id = Integer.parseInt(values.get(0));
                	String name = values.get(1);
            		String price = values.get(2);
            		int reviews = Integer.parseInt(values.get(3));
            		String category = values.get(4);
            		String street = values.get(5);
            		String city = values.get(6);
            		String phone = values.get(7);
            		String site = values.get(8);
            		theOne.mAllRestaurantsList.add(new Restaurant(id, name, price, reviews, category, street, city, phone, site));
                }
                
                theOne.mUserVisitedDB = new DBModel(DB_NAME, USER_VISITED_TABLE_NAME, USER_VISITED_FIELD_NAMES, USER_VISITED_FIELD_TYPES);
                
//                ResultSet rs = theOne.mDB.getAllRecords();
//                if(rs != null)
//                {
//                	while(rs.next())
//                	{
//                		int id = rs.getInt(FIELD_NAMES[0]);
//                		String name = rs.getString(FIELD_NAMES[1]);
//                		String price = rs.getString(FIELD_NAMES[2]);
//                		int reviews = rs.getInt(FIELD_NAMES[3]);
//                		String category = rs.getString(FIELD_NAMES[4]);
//                		String street = rs.getString(FIELD_NAMES[5]);
//                		String city = rs.getString(FIELD_NAMES[6]);
//                		String phone = rs.getString(FIELD_NAMES[7]);
//                		String site = rs.getString(FIELD_NAMES[8]);
//                		
//                		theOne.mAllRestaurantsList.add(new Restaurant(id, name, price, reviews, category, street, city, phone, site));
//                	}
//                }
                theOne.mUserFavoriteRestaurantsDB = new DBModel(DB_NAME, USER_RESTAURANTS_TABLE_NAME, 
                		USER_RESTAURANTS_FIELD_NAMES, USER_RESTAURANTS_FIELD_TYPES);
                theOne.mUserDislikedRestaurantsDB = new DBModel(DB_NAME, USER_DISLIKED_RESTAURANTS_TABLE_NAME, 
                		USER_DISLIKED_RESTAURANTS_FIELD_NAMES, USER_DISLIKED_RESTAURANTS_FIELD_TYPES);
                theOne.mUserNotVisitedDB = new DBModel(DB_NAME, USER_NOT_VISITED_TABLE_NAME, USER_NOT_VISITED_FIELD_NAMES, USER_NOT_VISITED_FIELD_TYPES);

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return theOne;
    }
    
    /**
     * Returns all restaurants in the database as ObservableList
     * @return
     */
    public ObservableList<Restaurant> getAllRestaurants()
    {
    	return theOne.mAllRestaurantsList;
    }
    
    /**
     * Returns ID of current user
     * @return id
     */
    public int getCurrentUserID()
    {
    	return this.mCurrentUser.getId();
    }
    
    /**
     * Returns current user
     * @return
     */
    public User getCurrentUser()
    {
    	return this.mCurrentUser;
    }
    
    /**
     * Sets current user
     * @param user
     */
    public void setUser(User user)
    {
    	this.mCurrentUser = user;
    }
    
    /**
     * Signs in user if email and password match record and are valid
     * @param email
     * @param password
     * @return
     */
    public User signIn(String email, String password)
    {
    	for (User u : theOne.mAllUsersList)
			if (u.getEmail().equalsIgnoreCase(email))
			{
				try 
				{
					ArrayList<ArrayList<String>> resultsList = theOne.getUsersDB().getRecord(String.valueOf(u.getId()));
					String storedPassword = resultsList.get(0).get(3);
					if (password.equals(storedPassword))
					{
						this.mCurrentUser = u;
						return u;
						
					}
						
						
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
    	return null;
    }
    
    /**
     * Sign in method for Sign in scene
     * @param email
     * @param password
     * @return
     */
    public String signInUser(String email, String password) {
		for (User u : theOne.mAllUsersList)
			if (u.getEmail().equalsIgnoreCase(email))
			{
				try 
				{
					ArrayList<ArrayList<String>> resultsList = theOne.getUsersDB().getRecord(String.valueOf(u.getId()));
					String storedPassword = resultsList.get(0).get(3);
					if (password.equals(storedPassword))
					{
						this.mCurrentUser = u;
						return "SUCCESS";
					}
						
						
				} 
				catch (Exception e) {return "Error";}
				return "Incorrect password.  Please try again.";		
			}		
		return "Email address not found.  Please try again.";
	}
    
    /**
     * Signs up a user and adds user data to appropriate tables
     * @param name
     * @param email
     * @param password
     * @return
     */
    public String signUpUser(String name, String email, String password)
    {
    	for(User user : theOne.mAllUsersList)
    		if(user.getEmail().equalsIgnoreCase(email))
    			return "Email already exists";
    	// Add user to database
    	String[] values = {name, email, password};
    	try
    	{
    		int id = theOne.getUsersDB().createRecord(Arrays.copyOfRange
    				(USER_FIELD_NAMES, 1, USER_FIELD_NAMES.length), values);
    		User newUser = new User(id, name, email);
    		theOne.mAllUsersList.add(newUser);
    		theOne.mCurrentUser = newUser;
    		return "SUCCESS";
    	}
    	catch(SQLException e)
    	{
    		return "Account not created.  Please try again.";
    	}
    }
    
    /**
     * Returns ObservableList of all user data
     * @return
     */
    public ObservableList<User> getAllUsers()
    {
    	return theOne.mAllUsersList;
    }
    
    /**
     * Returns ObservableList of all restaurants user has visited
     * @return visited restaurants
     */
    public ObservableList<Restaurant> getRestaurantsForCurrentUser()
    {
    	ObservableList<Restaurant> userRestaurantsList = FXCollections.observableArrayList();
    	if(mCurrentUser != null)
    	{
    		try
    		{
    			ArrayList<ArrayList<String>> resultsList = theOne.mUserVisitedDB.getRecord(String.valueOf(mCurrentUser.getId()));
    			for(ArrayList<String> values : resultsList)
    			{
    				int restaurantId = Integer.parseInt(values.get(1));
    				for(Restaurant r : theOne.mAllRestaurantsList)
    					if(r.getId() == restaurantId) userRestaurantsList.add(r);
    			}
    		}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
    	}
    	return userRestaurantsList;
    }
    
    /**
     * Returns ObservableList of all cities in database
     * @return cities
     */
    public ObservableList<String> getDistinctCities()
    {
    	ObservableList<String> cities = FXCollections.observableArrayList();
    	cities.add("");
    	for(Restaurant r : theOne.mAllRestaurantsList)
    		if(!cities.contains(r.getCity())) cities.add(r.getCity());
    	FXCollections.sort(cities);
    	return cities;
    }
    
    /**
     * Returns all price values that exist in database
     * @return prices
     */
    public ObservableList<String> getDistinctPrices()
    {
    	ObservableList<String> prices = FXCollections.observableArrayList();
    	prices.add("");
    	for(Restaurant r : theOne.mAllRestaurantsList)
    		if(!prices.contains(r.getPrice())) prices.add(r.getPrice());
    	FXCollections.sort(prices);
    	return prices;
    }
    
//   
    
    /**
     * Returns ObservableList of all the different food categories in the database
     * @return distinct food categories
     */
    public ObservableList<String> getDistinctCategories()
    {
    	ObservableList<String>categories = FXCollections.observableArrayList();
    	categories.add("");
    	for(Restaurant r : theOne.mAllRestaurantsList)
    	{
//    		String[] categoriesList = r.getCategories().split("-");
//    		if(!categories.contains(categoriesList[0])) categories.add(categoriesList[0]);
    		
    		String category = r.getCategories();
    		if(category.contains(","))
    			category = category.substring(0, category.indexOf(","));
    		if(!categories.contains(category)) categories.add(category);
//    		for(int i = 0; i < categoriesList.length; i++)
//    		{
//    			if(!categories.contains(categoriesList[i])) categories.add(categoriesList[i]);
//    		}
    	}
    	FXCollections.sort(categories);
    	return categories;
    }
    
    /**
     * When a user visits a restaurant, it gets added to mUserVisitedDB and gets removed from mUserNotVisitedDB
     * @param r
     * @return
     */
    public boolean addRestaurantToVisited(Restaurant r)
    {
    	ObservableList<Restaurant> userVisitedList = theOne.getRestaurantsForCurrentUser();
    	if(userVisitedList.contains(r)) return false;
    	String[] values = {String.valueOf(mCurrentUser.getId()), String.valueOf(r.getId())};
    	try
    	{
    		this.mUserVisitedDB.createRecord(USER_VISITED_FIELD_NAMES, values);
    		this.mUserNotVisitedDB.deleteRecord(String.valueOf(r.getId()));
    	}
    	catch (SQLException e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    
    /**
     * Converts the dollar sign representation of price to an int ranging from 1-5
     * @param price
     * @return int representation of price
     */
    public int convertPriceToInt(String price)
    {
//    	if(price.equals("$")) return 1;
//    	if(price.equals("$$")) return 2;
//    	if(price.equals("$$$")) return 3;
//    	if(price.equals("$$$$")) return 4;
//    	if(price.equals("$$$$$")) return 5;
//    	return 0;
    	return price.length();
    }
    
    /**
     * Filters restaurant options based on specifications given by user
     * @param minPrice = minimum price of wanted restaurants
     * @param maxPrice = maximum price of wanted restaurants
     * @param reviews = minimum number of reviews for restaurant
     * @param city = City restaurant is located in
     * @param category = type of food/genre restaurant offers
     * @return ObservableList of restaurants meeting all specifications
     */
    public ObservableList<Restaurant> filter(int minPrice, int maxPrice, int reviews, String city, String category)
    {
    	ObservableList<Restaurant> filteredRestaurantsList = FXCollections.observableArrayList();
    	for(Restaurant r : theOne.mAllRestaurantsList)
    	{
    		int price = convertPriceToInt(r.getPrice());
//    		int lowPrice = convertPriceToInt(minPrice);
//    		int highPrice = convertPriceToInt(maxPrice);
    		if(((price >= minPrice && price <= maxPrice)) && (r.getReviews() >= reviews) && 
    				(city == null || r.getCity().contains(city)) && 
    				(category == null || r.getCategories().contains(category)))
    		{
    			filteredRestaurantsList.add(r);
    		}
    	}
    	return filteredRestaurantsList;
    }
    
    /**
     * Same as filter method above, but adds the additional specification of choosing a restaurant the user hasn't visited
     * @param minPrice
     * @param maxPrice
     * @param reviews
     * @param city
     * @param category
     * @return
     */
    public ObservableList<Restaurant> filterFromNotVisited(int minPrice, int maxPrice, int reviews, String city, String category)
    {
    	ObservableList<Restaurant> filteredRestaurantsList = FXCollections.observableArrayList();
    	ObservableList<Restaurant> notVisited = theOne.getNotVisitedRestaurantsForCurrentUser();
    	for(Restaurant r : notVisited)
    	{
    		int price = convertPriceToInt(r.getPrice());
//    		int lowPrice = convertPriceToInt(minPrice);
//    		int highPrice = convertPriceToInt(maxPrice);
    		if(((price >= minPrice && price <= maxPrice)) && (r.getReviews() >= reviews) && 
    				(city == null || r.getCity().contains(city)) && 
    				(category == null || r.getCategories().contains(category)))
    		{
    			filteredRestaurantsList.add(r);
    		}
    	}
    	return filteredRestaurantsList;
    }


    /**
     * Loads restaurant database from csv
     * @return
     * @throws SQLException
     */
    private int initializeDBFromFile() throws SQLException	//Loads all restaurants in the database
    {
        int recordsCreated = 0;

        if(theOne.mDB.getRecordCount() > 0)
            return recordsCreated;

        try
        {
            Scanner fileScanner = new Scanner(new File(DATA_FILE));
            fileScanner.nextLine();

            while(fileScanner.hasNextLine())
            {
            	String food = fileScanner.nextLine();
            	if(food.isEmpty()) continue;
                //String[] data = fileScanner.nextLine().split(",");
            	String[] data = food.split(",");
                String[] values = new String[FIELD_NAMES.length - 1];
                // "name", "price", "reviews", "category", "street", "city", "phone", "site"
//                values[0] = data[5].replaceAll("'", " ''");
//                values[1] = data[8];
//                values[2] = data[7];
//                values[3] = data[9].replaceAll("-", ",");
//                values[4] = data[13];
//                values[5] = data[14].replaceAll("\"", "");
//                values[6] = data[15];
//                values[7] = data[6];
                
                values[0] = data[0].replaceAll("'", " ''");
                values[1] = data[3];
                values[2] = data[2];
                values[3] = data[4].replaceAll("-", ",");
                values[4] = data[5].replaceAll("''", "");
                values[5] = data[6].replaceAll("''", "");
                values[6] = data[7];
                values[7] = data[1];
               
                theOne.mDB.createRecord(Arrays.copyOfRange(FIELD_NAMES, 1, FIELD_NAMES.length), values);
                recordsCreated++;
            }
            fileScanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return recordsCreated;
    }
    
    /**
     * Adds restaurant to database
     * @param name
     * @param price
     * @param reviews
     * @param category
     * @param street
     * @param city
     * @param phone
     * @param site
     * @return
     */
    public boolean addRestaurant(String name, String price, int reviews, String category, String street, String city, String phone, String site)
    {
    	String[] values = {name, price, String.valueOf(reviews), category, street, city, phone};
    	
    	try
    	{
    		int id = theOne.mDB.createRecord(Arrays.copyOfRange(FIELD_NAMES, 1, FIELD_NAMES.length), values);
    		theOne.mAllRestaurantsList.add(new Restaurant(id, name, price, reviews, category, street, city, phone, site));
    	}
    	catch (SQLException e)
    	{
    		return false;
    	}
    	return true;
    }
    
    /**
     * Removes restaurant from database
     * @param r
     * @return
     */
    public boolean deleteRestaurant(Restaurant r)
    {
    	if(r == null)
    	{
    		return false;
    	}
    	
    	theOne.mAllRestaurantsList.remove(r);
    	
    	try
    	{
    		theOne.mDB.deleteRecord(String.valueOf(r.getId()));
    	}
    	catch (SQLException e)
    	{
    		return false;
    	}
    	return true;
    }
    
    /**
     * Returns all restaurant the current user has marked as favorite
     * @return
     */
    public ObservableList<Restaurant> getFavoriteRestaurantsForCurrentUser()
    {
        ObservableList<Restaurant> userFavoriteRestaurantsList = FXCollections.observableArrayList();
        if (mCurrentUser != null)
        {
            try {
                ArrayList<ArrayList<String>> resultsList = theOne.mUserFavoriteRestaurantsDB.getRecord(String.valueOf(mCurrentUser.getId()));
                for (ArrayList<String> values : resultsList)
                {
                    int restaurantId = Integer.parseInt(values.get(1));
                    for (Restaurant r : theOne.mAllRestaurantsList)
                        if (r.getId() == restaurantId)
                            userFavoriteRestaurantsList.add(r);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        	System.out.println("Current user is set to null");
        return userFavoriteRestaurantsList;
    }
    
    /**
     * Returns restaurants current user has visited
     * @return visited
     */
    public ObservableList<Restaurant> getVisitedRestaurantsForCurrentUser()
    {
    	ObservableList<Restaurant> userVisitedRestaurantsList = FXCollections.observableArrayList();
        if (mCurrentUser != null)
        {
            try {
                ArrayList<ArrayList<String>> resultsList = theOne.mUserVisitedDB.getRecord(String.valueOf(mCurrentUser.getId()));
                for (ArrayList<String> values : resultsList)
                {
                    int restaurantId = Integer.parseInt(values.get(1));
                    for (Restaurant r : theOne.mAllRestaurantsList)
                        if (r.getId() == restaurantId)
                            userVisitedRestaurantsList.add(r);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        	System.out.println("Current user is set to null");
        return userVisitedRestaurantsList;
    }
    
    /**
     * Returns restaurants current user has not visited yet
     * @return not visited
     */
    public ObservableList<Restaurant> getNotVisitedRestaurantsForCurrentUser()
    {
    	ObservableList<Restaurant> userNotVisitedRestaurantsList = FXCollections.observableArrayList(mAllRestaurantsList);
    	if(mCurrentUser != null)
    	{
    		try
    		{
    			 ObservableList<Restaurant> visitedRestaurants = theOne.getVisitedRestaurantsForCurrentUser();
    			 for (Restaurant r : visitedRestaurants)
    			 {
    				 userNotVisitedRestaurantsList.remove(r);
    			 }
    		}
    		catch (Exception e) {
             e.printStackTrace();
         }
    	}
    	
    	return userNotVisitedRestaurantsList;
    }
    
    public ObservableList<Restaurant> getUnvisitedList()
    {
    	ObservableList<Restaurant> userNotVisitedRestaurantsList = FXCollections.observableArrayList();
        if (mCurrentUser != null)
        {
            try {
                ArrayList<ArrayList<String>> resultsList = theOne.mUserNotVisitedDB.getRecord(String.valueOf(mCurrentUser.getId()));
                for (ArrayList<String> values : resultsList)
                {
                    int restaurantId = Integer.parseInt(values.get(1));
                    for (Restaurant r : theOne.mAllRestaurantsList)
                    {
                        if (r.getId() == restaurantId)
                        {
                            userNotVisitedRestaurantsList.add(r);
                        }
                        System.out.println(r.toString());
                    }
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        	System.out.println("Current user is set to null");
        return userNotVisitedRestaurantsList;
    }
    
    
    /**
     * Adds restaurant to favorite restaurant table
     * @param selectedRestaurant
     * @return
     */
    public boolean addFavoriteRestaurant(Restaurant selectedRestaurant)  {
        ObservableList<Restaurant> userRestaurantsList = theOne.getFavoriteRestaurantsForCurrentUser();
        ObservableList<Restaurant> userDislikedList = theOne.getDislikedRestaurantsForCurrentUser();
        if (userRestaurantsList.contains(selectedRestaurant) || userDislikedList.contains(selectedRestaurant))
            return false;
        String userID = String.valueOf(theOne.mCurrentUser.getId());
        if(selectedRestaurant == null) return false;
        String restaurantID = String.valueOf(selectedRestaurant.getId());
        String[] values = {userID, restaurantID};
        //String[] values = {String.valueOf(theOne.mCurrentUser.getId()), String.valueOf(selectedRestaurant.getId())};
        try {
            this.mUserFavoriteRestaurantsDB.createRecord(USER_LIKED_RESTAURANTS_FIELD_NAMES, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Removes restaurant from favorite restaurants table
     * @param selectedRestaurant
     * @return
     */
    public boolean removeFavoriteRestaurant(Restaurant selectedRestaurant)
    {
    	ObservableList<Restaurant> userRestaurantsList = theOne.getFavoriteRestaurantsForCurrentUser();
        if (userRestaurantsList.contains(selectedRestaurant))
        {
            try {
				theOne.mUserFavoriteRestaurantsDB.removeRestaurantFromFavorite((String.valueOf(selectedRestaurant.getId())));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return true;
        }
        return false;
    }
    
    /**
     * Returns restaurants current user has disliked
     * @return disliked
     */
    public ObservableList<Restaurant> getDislikedRestaurantsForCurrentUser()
    {
        ObservableList<Restaurant> userDislikedRestaurantsList = FXCollections.observableArrayList();
        if (mCurrentUser != null)
        {
            try {
                ArrayList<ArrayList<String>> resultsList = theOne.mUserDislikedRestaurantsDB.getRecord(String.valueOf(mCurrentUser.getId()));
                for (ArrayList<String> values : resultsList)
                {
                    int restaurantId = Integer.parseInt(values.get(1));
                    for (Restaurant r : theOne.mAllRestaurantsList)
                        if (r.getId() == restaurantId)
                            userDislikedRestaurantsList.add(r);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userDislikedRestaurantsList;
    }
    
    /**
     * Adds restaurant to disliked restaurants table for current user
     * @param selectedRestaurant
     * @return
     */
    public boolean addDislikedRestaurant(Restaurant selectedRestaurant)  {
        ObservableList<Restaurant> userRestaurantsList = theOne.getDislikedRestaurantsForCurrentUser();
        ObservableList<Restaurant> userFavoritesList = theOne.getFavoriteRestaurantsForCurrentUser();
        if (userRestaurantsList.contains(selectedRestaurant) || userFavoritesList.contains(selectedRestaurant))
            return false;
        if(selectedRestaurant == null) return false;
        String[] values = {String.valueOf(theOne.mCurrentUser.getId()), String.valueOf(selectedRestaurant.getId())};
        try {
            this.mUserDislikedRestaurantsDB.createRecord(USER_DISLIKED_RESTAURANTS_FIELD_NAMES, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Removes restaurant from disliked restaurants table for current user
     * @param selectedRestaurant
     * @return
     */
    public boolean removeDislikedRestaurant(Restaurant selectedRestaurant)
    {
    	ObservableList<Restaurant> userRestaurantsList = theOne.getDislikedRestaurantsForCurrentUser();
        if (userRestaurantsList.contains(selectedRestaurant))
        {
            try {
				theOne.mUserDislikedRestaurantsDB.removeRestaurantFromDisliked(String.valueOf(selectedRestaurant.getId()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return true;
        }
        return false;
    }

    /**
     * Returns users database table
     * @return
     */
	public DBModel getUsersDB() {
		return mUsersDB;
	}

	/**
	 * Sets users database table
	 * @param usersDB
	 */
	public void setUsersDB(DBModel usersDB) {
		mUsersDB = usersDB;
	}
}
