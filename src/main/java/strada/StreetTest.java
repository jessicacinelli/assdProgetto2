package strada;


import gps.Coordinate;

public class StreetTest {

	public static void main(String[] args) throws InterruptedException
	{
		 long startTime = System.currentTimeMillis();
		 Coordinate a= new Coordinate(41.141938, 14.763815);
		 Coordinate b= new Coordinate(41.137405, 14.783075);
		 
		 Street s = new Street(122 , 2, 5 , 3.0, 50, "Prova", 10.1, 33.3); 
			
		 s.getCoordinate().add(a);
		 s.getCoordinate().add(b);
		 
		 while(true)
		 {
			 long endTime = System.currentTimeMillis();
			 long seconds = (endTime - startTime) / 1000;
			 
			 double dist = s.Distance(seconds);
			 
			 System.out.println(dist);
			 Thread.sleep(10000);
		 }
		 
	}

}
