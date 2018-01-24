package edu.orangecoastcollege.cs272.btomita.capstone.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.orangecoastcollege.cs272.btomita.capstone.model.Restaurant;

public class RestaurantTest
{
    private static Restaurant r;
    /**
     * Define the restaurant
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        r = new Restaurant(1, "The Black Trumpet Bistro", "$$", 548, "Mediterranean- Tapas/Small Plates", "18344 Beach Blvd",
            "Huntington Beach", "(714) 842-1122", "https://www.yelp.com/biz/the-black-trumpet-bistro-huntington-beach");
    }
    /**
     * Cleans up open resources
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {

    }
    /**
     * Executed before each test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        r.setName("Name1");
    }

    /**
     * Executed after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception
    {
        r.setName("The Black Trumpet Bistro");
    }
    /**
     * Tests getName()
     */
    @Test
    public void testGetName()
    {
        assertEquals("Name1", r.getName());
    }
    /**
     * Tests setName()
     */
    @Test
    public void testSetName()
    {
        r.setName("Name2");
        assertEquals("Name2", r.getName());
    }

}