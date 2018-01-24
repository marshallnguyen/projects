package edu.orangecoastcollege.cs272.btomita.capstone.model;

/**
 * User class for Food Finder
 * @author Brett
 *
 */
public class User 
{
	private int mId;
	private String mName;
	private String mEmail;
	
	/**
	 * Default User constructor
	 */
	public User()
	{
		mId = 0;
		mName = null;
		mEmail = null;
	}
	
	/**
	 * Constructor for User object
	 * @param id
	 * @param name
	 * @param email
	 */
	public User(int id, String name, String email)
	{
		super();
		mId = id;
		mName = name;
		mEmail = email;
	}
	

	/**
	 * Returns User id
	 * @return
	 */
	public int getId() {
		return mId;
	}

	/**
	 * Sets user id
	 * @param id
	 */
	public void setId(int id) {
		mId = id;
	}

	/**
	 * Returns user name
	 * @return
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Sets user name
	 * @param name
	 */
	public void setName(String name) {
		mName = name;
	}

	/**
	 * returns user email
	 * @return
	 */
	public String getEmail() {
		return mEmail;
	}

	/**
	 * Sets user email
	 * @param email
	 */
	public void setEmail(String email) {
		mEmail = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mEmail == null) ? 0 : mEmail.hashCode());
		result = prime * result + mId;
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
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
		User other = (User) obj;
		if (mEmail == null) {
			if (other.mEmail != null)
				return false;
		} else if (!mEmail.equals(other.mEmail))
			return false;
		if (mId != other.mId)
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [mId=" + mId + ", mName=" + mName + ", mEmail=" + mEmail + "]";
	}
	
	
}
