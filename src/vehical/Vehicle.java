package vehical;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import java.util.Scanner;


import exception.StatusException;
import exception.odoException;
interface basic{
	void print();
}
public class Vehicle implements basic {

	private String vehicleId;
	private static String hirerId;
	private String description;
	protected  char status = 'A';
	private Double dailyRate;
	private int odometer;
	
	
	static Scanner sc = new Scanner(System.in);
	
	
	public Vehicle(String vehicleid, String desc, double Drate, int Odometer){
		  vehicleId = vehicleid;
		  description = desc;
		  dailyRate = Drate;
		  odometer = Odometer; 
		  
	  }	
	
	
	
 public Double getdailyRate(){
	 
	 return dailyRate;
	 
 }	
 
 public int getOdometer(){
	 return odometer;
 }
	
	
  public String getHirer() {
		return hirerId;
	}

	public void hirer(String HirerID) {
		this.hirerId = HirerID;
	}

public String getID() {
		return vehicleId;
	}

	public void setID(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public char getStatus(){
		return status;
	}

	
  public boolean hire(String HireID) throws StatusException{
	   
	  if(status == 'H'){
		  throw new StatusException("Vehicle is on Hire");
	  }
		 
		
	  if(status == 'S'){ 
		  throw new StatusException("Vehicle is on Service");
	  }else{
		  
	  hirerId = HireID;
		
	
		
		
		
		
		status = 'H';
		System.out.print("Hired");
		
		
		 String details =    vehicleId + " hired by "+ hirerId + " odometer" + odometer; 
		  
		  transaction(details);
		
		
		 return true;
	  }

  }



  
  public void print(){ 
	  
	
	  
	  System.out.println("vehicle ID =" +  vehicleId + "\t" +  "description = " +  description + "\t" + "status = " + status + "\t" + "daily rate = " + dailyRate + "odometer reading = " + odometer );
	  
  }
  
  
  public static void transaction(String detail){

	  String details = detail;
	  
		try(FileWriter fw = new FileWriter("transaction.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.print(details);
			    out.print("\n");
			    
			    
			}catch (IOException e) {
			   System.out.println("TRansaction error");
			}
		
	}
  
  
  

public String getDescription1() {
	// TODO Auto-generated method stub
	return description;
} 
  
  
}