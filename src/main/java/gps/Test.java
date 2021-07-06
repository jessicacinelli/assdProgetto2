package gps;


public class Test {
 public static void main (String [] args) {
	/** Coordinate a= new Coordinate(41.1597366993132, 14.50503059602507);
	 Coordinate b= new Coordinate(41.16304168304931, 14.506286303511484);
	 double distance = 381.43 ;
	 double metripercorsi=281;
	 
	  dist = 1.693,53 m 
			 mp= dist*2 -2852.7

			 da trovare 41.140508, 14.769892
			 a=41.141938, 14.763815
			 b=41.137405, 14.783075
	 */
	 Coordinate a= new Coordinate(41.141938, 14.763815);
	 Coordinate b= new Coordinate(41.137405, 14.783075);
	 double distance = 1693.53 ;
	 double metripercorsi=534.36;
	 
	 double lat= (distance-metripercorsi)/distance * (b.getLatitude()-a.getLatitude());//latitude
	 double lon=metripercorsi/distance * (b.getLongitude()- a.getLongitude()); //longitude
	 //Coordinate point= new Coordinate(a.getLatitude()+ lat, a.getLongitude() + lon);
	 //System.out.println(point.toString());
	 
	 Coordinate point2= new Coordinate(b.getLatitude()- lat, a.getLongitude() + lon);
	 System.out.println(point2.toString());
	 
	 
 }
}

