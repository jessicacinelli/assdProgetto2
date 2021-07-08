package controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;



@ApplicationPath("/assdTrafficService/rest")
public class StreetApplication extends Application {
	
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s= new HashSet<Class<?>>();
		s.add(StreetControllerImpl.class);
		return s;
	}
	
	public Set<Object> getSingletons(){
		Set<Object> s = new HashSet<Object>();
		s.add(new StreetControllerImpl());
		return s;
	}
}
