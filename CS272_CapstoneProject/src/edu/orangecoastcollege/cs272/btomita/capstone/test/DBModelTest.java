package edu.orangecoastcollege.cs272.btomita.capstone.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.orangecoastcollege.cs272.btomita.capstone.model.DBModel;

/**
 * Unit Test class
 * @author Brett
 *
 */
public class DBModelTest {
	
	private static final String DB_NAME = "cs272_test.db";
    private static final String TABLE_NAME = "oc";
    private static final String[] FIELD_NAMES = { "id", "name", "price", "reviews", "category", "street", "city", "phone", "site" };
    private static final String [] FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "INTEGER", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT" };
    
    private static DBModel db;
    
    private String[] values;
    

    /**
     * Defines variables, resources, etc
     * @throws Exception
     */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		db = new DBModel(DB_NAME, TABLE_NAME, FIELD_NAMES, FIELD_TYPES);
	}

	/**
	 * Cleans up any open resources
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		db.close();
	}

	/**
	 * Executed before each individual test
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		values = new String[] {"1", "The Black Trumpet Bistro", "$$", "548", "Mediterranean- Tapas/Small Plates", "18344 Beach Blvd",
				"Huntington Beach", "(714) 842-1122", "https://www.yelp.com/biz/the-black-trumpet-bistro-huntington-beach"};
	}

	/**
	 * Executed after each individual test
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		db.deleteAllRecords();
	}

	/**
	 * Makes sure that we are able to get all the records from the database
	 */
	@Test
	public void testGetAllRecord() 
	{
		try
		{
			//db.createRecord(Arrays.copyOfRange(FIELD_NAMES, 1, FIELD_NAMES.length), Arrays.copyOfRange(values, 1, values.length));
			db.getAllRecords();
		}
		catch(SQLException e)
		{
			fail("Getting all records on empty database should not generate SQLException");
		}
	}
	
	/**
	 * Make sure we can get a single record from the database.
	 */
	@Test
    public void testGetRecord()
    {
        try
        {
            db.createRecord(Arrays.copyOfRange(FIELD_NAMES, 1, FIELD_NAMES.length), Arrays.copyOfRange(values, 1, values.length));
            assertTrue("Before deletion, count should be positive", db.getRecordCount() > 0);
            db.getRecord(FIELD_NAMES[1]);
        }
        catch (SQLException e)
        {
            fail("Getting record on empty database should not generate SQLException");
        }
    }
	
	/**
	 * Ensures correctness of the record count in the database.
	 */
	@Test
    public void testGetRecordCount()
    {
        try
        {
        	db.createRecord(Arrays.copyOfRange(FIELD_NAMES, 1, FIELD_NAMES.length), Arrays.copyOfRange(values, 1, values.length));
        	assertEquals("Testing to see if record count is 1 after creating a record.", 1, db.getRecordCount());
        	db.createRecord(Arrays.copyOfRange(FIELD_NAMES, 1, FIELD_NAMES.length), Arrays.copyOfRange(values, 1, values.length));
        	assertEquals("Testing to see if record count is 2 after creating a record.", 2, db.getRecordCount());
        	db.deleteRecord("1");
        	assertEquals("Testing to see if record count is 1 after deleting a record.", 1, db.getRecordCount());
        	db.deleteRecord("2");
        	assertEquals("Testing to see if record count is 0 after deleting a record.", 0, db.getRecordCount());
        }
        catch(SQLException e)
        {
            fail("Getting record count should not generate SQLException");
        }
    }

}
