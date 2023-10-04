package vehical;

import java.util.Date;

import exception.StatusException;
import exception.odoException;

public class PremiumVehicle extends Vehicle implements basic {
	
	int dailyMilage;
	int serviceLenght;
    int lastService;
    
    

	public PremiumVehicle(String vehicleid, String desc, double Drate, int Odometer, int DailyMil, int ServiceLen, int Lastservice) {
		
		super(vehicleid, desc, Drate, Odometer);
		
		dailyMilage = DailyMil;
		serviceLenght = ServiceLen;
		lastService = Lastservice;
		
	}
	
	


	

	public void print(){
		
		
		super.print();
		System.out.println(" Daily-Milage:  " + dailyMilage + " Service-Lenght:  " + serviceLenght + " Last-Service:  " + lastService);
	}
	
}