package entity;

import java.util.List;

public class Streets {
	  private List<Street> street;
	  
	  public Street getStreet() {
	    return street.get(0);
	  }
	 
	  
	  public void setStreet(List<Street> street) {
	    this.street = street;
	  }


	@Override
	public String toString() {
		return "Streets [street=" + street + "]";
	}
	  
}
