package edu.orangecoastcollege.cs272.btomita.capstone.model;

import java.util.Arrays;

/**
 * Restaurant class for Food Finder
 * @author Brett
 *
 */
public class Restaurant
{
    private int mID;
    private String mName;
    private String mPrice;
    private int mReviews;
    private String mCategories;
    private String mStreet;
    private String mCity;
    private String mPhoneNumber;
    private String mSite;

    /**
     * Constructor for Restaurant object
     * @param id
     * @param name
     * @param price
     * @param reviews
     * @param categories
     * @param street
     * @param city
     * @param phone
     * @param site
     */
    public Restaurant(int id, String name, String price, int reviews, String categories, String street, String city, String phone, String site)
    {
        super();
        mID = id;
        mName = name;
        mPrice = price;
        mReviews = reviews;
        mCategories = categories;
        mStreet = street;
        mCity = city;
        mPhoneNumber = phone;
        mSite = site;
    }

    /**
     * Returns restaurant id
     * @return
     */
    public int getId()
    {
        return mID;
    }

    /**
     * 
     * @return Restaurant name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * 
     * @param mName = set Restaurant name
     */
    public void setName(String mName)
    {
        this.mName = mName;
    }

    /**
     * 
     * @return Restaurant price
     */
    public String getPrice()
    {
        return mPrice;
    }

    /**
     * 
     * @param mPrice = Set restaurant price
     */
    public void setPrice(String mPrice)
    {
        this.mPrice = mPrice;
    }

    /**
     * 
     * @return number of restaurant reviews
     */
    public int getReviews()
    {
        return mReviews;
    }

    /**
     * 
     * @param mReviews = set number of restaurant reviews
     */
    public void setReviews(int mReviews)
    {
        this.mReviews = mReviews;
    }

    /**
     * 
     * @return restaurant categories
     */
    public String getCategories()
    {
        return mCategories;
    }

    /**
     * 
     * @param mCategories = sets restaurant categories
     */
    public void setCategories(String mCategories)
    {
        this.mCategories = mCategories;
    }

   /**
    * 
    * @return Street restaurant is located on
    */
    public String getStreet()
    {
        return mStreet;
    }

    /**
     * 
     * @param mStreet = Set street restaurant is on
     */
    public void setStreet(String mStreet)
    {
        this.mStreet = mStreet;
    }

    /**
     * 
     * @return City restaurant is located in
     */
    public String getCity()
    {
        return mCity;
    }

    /**
     * 
     * @param mCity = Set city restaurant is located in
     */
    public void setCity(String mCity)
    {
        this.mCity = mCity;
    }

    /**
     * 
     * @return restaurant phone number
     */
    public String getPhoneNumber()
    {
        return mPhoneNumber;
    }

    /**
     * set restaurant phone number
     * @param mPhoneNumber
     */
    public void setPhoneNumber(String mPhoneNumber)
    {
        this.mPhoneNumber = mPhoneNumber;
    }

    /**
     * 
     * @return restaurant Yelp page
     */
	public String getSite() {
		return mSite;
	}

	/**
	 * Set restaurant Yelp page
	 * @param site
	 */
	public void setSite(String site) {
		mSite = site;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mCategories == null) ? 0 : mCategories.hashCode());
		result = prime * result + ((mCity == null) ? 0 : mCity.hashCode());
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		result = prime * result + ((mPhoneNumber == null) ? 0 : mPhoneNumber.hashCode());
		result = prime * result + ((mPrice == null) ? 0 : mPrice.hashCode());
		result = prime * result + mReviews;
		result = prime * result + ((mSite == null) ? 0 : mSite.hashCode());
		result = prime * result + ((mStreet == null) ? 0 : mStreet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		if (mCategories == null) {
			if (other.mCategories != null)
				return false;
		} else if (!mCategories.equals(other.mCategories))
			return false;
		if (mCity == null) {
			if (other.mCity != null)
				return false;
		} else if (!mCity.equals(other.mCity))
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (mPhoneNumber == null) {
			if (other.mPhoneNumber != null)
				return false;
		} else if (!mPhoneNumber.equals(other.mPhoneNumber))
			return false;
		if (mPrice == null) {
			if (other.mPrice != null)
				return false;
		} else if (!mPrice.equals(other.mPrice))
			return false;
		if (mReviews != other.mReviews)
			return false;
		if (mSite == null) {
			if (other.mSite != null)
				return false;
		} else if (!mSite.equals(other.mSite))
			return false;
		if (mStreet == null) {
			if (other.mStreet != null)
				return false;
		} else if (!mStreet.equals(other.mStreet))
			return false;
		return true;
	}

	/**
	 * Represents Restaurant in String format
	 */
	@Override
	public String toString() {
		return "Restaurant [mName=" + mName + ", mPrice=" + mPrice + ", mReviews=" + mReviews + ", mCategories="
				+ mCategories + ", mStreet=" + mStreet + ", mCity=" + mCity + ", mPhoneNumber=" + mPhoneNumber
				+ ", mSite=" + mSite + "]";
	}
	
	
    
    



}
