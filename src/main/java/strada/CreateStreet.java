package strada;
	
	import java.io.IOException;
	import java.io.Serializable;
	import java.util.ArrayList;
	import java.util.List;

	import javax.xml.bind.annotation.XmlRootElement;

	import com.fasterxml.jackson.annotation.JsonCreator;
	import com.fasterxml.jackson.annotation.JsonProperty;
	import com.fasterxml.jackson.databind.ObjectMapper;

	import gps.Coordinate;


	@XmlRootElement

public class CreateStreet implements Serializable{ 

		private double linkId; 
		private int from;
		private int to;
		private double lenght; //link lenght [espressa in metri]

		private int speedlimit;
		private String name;
		private double weight; //average travel time [s]  //tempo finale - tempo iniziale 
		
		private double ffs; // free flow speed [Km/h] (Velocit� media, dipendente dal traffico)

		private List<Coordinate> coordinates;

		 @JsonCreator
		    public CreateStreet(
		            @JsonProperty("linkId") final double linkId,
		            @JsonProperty("from") final int from,
		            @JsonProperty("to") final int to,
		            @JsonProperty("lenght") final double l,
		            @JsonProperty("speedlimit") final int speedlimit,
		            @JsonProperty("name") final String name,
		            @JsonProperty("weight") final double weight,
		            @JsonProperty("fft") final double fft,
		            @JsonProperty("coordinates") final List<Coordinate> coordinates
		    ) {
			 this.linkId = linkId;
				this.from = from;
				this.to = to;
				this.lenght =lenght;
				this.speedlimit = speedlimit;
				this.name = name;
				this.weight = weight;
				this.ffs = ffs;
				this.coordinates=coordinates;
		    }

		    public static CreateStreet parseStreetJson(String json) {
		        ObjectMapper objectMapper = new ObjectMapper();
		        try {
		            return objectMapper.readValue(json, CreateStreet.class);
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
		    }
		
}
