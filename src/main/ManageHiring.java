package main;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import customer.CorporateCustomer;
import customer.Customer;
import customer.ICustomer;
import exception.StatusException;
import exception.odoException;
import vehical.PremiumVehicle;
import vehical.Vehicle;

public class ManageHiring {

	static String option;
	static int loop = 1;
	static int v;
	static int odo;
	static int ARRAY_SIZE = 100;
	static int ARRAY_FILLED = 0;
	static int ARRAY_POSITION = 0;
	static int ARRAY_POSITION2 = 0;
	static Vehicle[] vehs = new Vehicle[ARRAY_SIZE];
	static Customer[] cust = new Customer[100];
	static JFrame frame;

	static Scanner sc = new Scanner(System.in);

	static void initialiseVehciles() throws StatusException, odoException, IOException {
		readVehicle();
		readCustomer();

		vehs[0] = new Vehicle("QJT123", "Startlet99", 35.00, 190000);
		vehs[1] = new Vehicle("TUV178", "Toyata Starlet 02", 35.00, 180000);
		vehs[2] = new Vehicle("SAG132", "Toyata Avalon 05", 52.00, 190000);
	}

	public static void main(String args[]) throws StatusException, odoException, IOException {
		menu();

	}

	static void menu() throws StatusException, odoException, IOException {
		initialiseVehciles();

		while (loop != 0) {

//		System.out.println("\n\nSelect From Following options :");
//		System.out.println("1. View All Vehicles");
//		System.out.println("2. Select Price Range");
//		System.out.println("3. Hire Vehicle");
//
//		System.out.println("4. Add new Vehicle");
//		System.out.println("5. Add new customer");
//		System.out.println("6. Quit");
//		System.out.println("_______________________________________");
//		option = sc.next();

			String[] options = { "View All Vehicles", "Select Price Range", "Hire Vehicle", "Add new Vehicle",
					"Add new customer", "Quit" };

			String selectedOption = (String) JOptionPane.showInputDialog(null, "Select an option:", "Menu",
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (selectedOption == null) {
				// Close the input dialog and return -1

				System.out.println("Exit");
				System.exit(1);
			}

			switch (selectedOption) {

			case "View All Vehicles":
				loop = 0;
				AllVehicle();

				break;
			case "Select Price Range":
				selectedVehicle();
				break;
			case "Hire Vehicle":
				hireVehicle();
				break;

			case "Add new Vehicle":
				addVehicle();
				break;
			case "Add new customer":
				addCustomer();
				break;
			case "Quit":
				loop = 0;
				break;

			}

		}
	}

	public static void readVehicle() throws IOException {

		String VID;
		String desc;
		double rate = 0;
		int odo = 0;
		int DailyMil = 0;
		int ServiceLen = 0;
		int Lastservice = 0;

		BufferedReader b = new BufferedReader(new FileReader("Vehicle.txt"));

		String line;

		line = b.readLine();

		while (line != null) {

			int i = 0;
			String details;

			details = line;

			String[] s = new String[7];

			for (String vh : details.split(",")) {

				s[i] = vh;
				i++;
			}

			VID = s[0];
			desc = s[1];
			try {
				if (s[2] != null)
					rate = Double.parseDouble(s[2]);
				if (s[3] != null)
					odo = Integer.parseInt(s[3]);

				if (i > 4) {
					if (s[4] != null)
						DailyMil = Integer.parseInt(s[4]);
					if (s[5] != null)
						ServiceLen = Integer.parseInt(s[5]);
					if (s[6] != null)
						Lastservice = Integer.parseInt(s[6]);

					vehs[ARRAY_POSITION] = new PremiumVehicle(VID, desc, rate, odo, DailyMil, ServiceLen, Lastservice);
				} else {

					vehs[ARRAY_POSITION] = new Vehicle(VID, desc, rate, odo);
				}

			} catch (Exception e) {
				System.out.println("Error" + e);
			}
			ARRAY_FILLED++;
			ARRAY_POSITION++;

			line = b.readLine();
		}

	}

	public static void readCustomer() throws IOException {

		BufferedReader b = new BufferedReader(new FileReader("Customer.txt"));

		String id;
		String name;
		int phone;
		int experience;
		double rating;
		char custType;

		String line = b.readLine();

		while (line != null) {

			int i = 0;
			String details;

			details = line;

			String[] s = new String[5];

			for (String vh : details.split(",")) {

				s[i] = vh;
				i++;
			}

			id = s[1];
			name = s[2];
			phone = Integer.parseInt(s[3]);

			if ((s[0]).charAt(0) == 'I') {

				experience = Integer.parseInt(s[4]);
				cust[ARRAY_POSITION2] = new ICustomer(id, name, phone, experience);
			} else {
				rating = Double.parseDouble(s[4]);
				cust[ARRAY_POSITION2] = new CorporateCustomer(id, name, phone, rating);
			}

			ARRAY_POSITION2++;

			line = b.readLine();

		}
	}

	public static int custDuplicateCheck(String ID, String nam) throws IOException {
		BufferedReader bufr = new BufferedReader(new FileReader("Customer.txt"));
		String id = ID;
		String name = nam;
		String line = bufr.readLine();

		while (line != null) {
			int i = 0;
			String details;
			details = line;
			String[] s = new String[5];

			for (String vh : details.split(",")) {
				s[i] = vh;
				i++;
			}

			if (id.equals(s[1])) {
				JOptionPane.showMessageDialog(null, "ID already exists. Please enter a different ID", "Error",
						JOptionPane.ERROR_MESSAGE);
				return 1;
			} else if (name.equals(s[2])) {
				JOptionPane.showMessageDialog(null, "Name already exists. Please enter a different name", "Error",
						JOptionPane.ERROR_MESSAGE);
				return 1;
			} else {
				line = bufr.readLine();
			}
		}

		return 0;
	}

	public static void addCustomer() throws IOException {
		int expYear = 0;
		double rating = 0;
		String custID;
		String name;
		loop = 0;

		JOptionPane.showMessageDialog(null,
				"1. Press 'I' to add Individual Customer\n2. Press 'C' to add Corporate Customer");

		String custype = JOptionPane.showInputDialog(null, "Enter customer type: ");

		do {
			custID = JOptionPane.showInputDialog(null, "Enter customer ID: ");
			name = JOptionPane.showInputDialog(null, "Enter customer name: ");

			if (custDuplicateCheck(custID, name) == 1) {
				// Handle error message if needed
			} else {
				loop = 1;
			}
		} while (loop == 0);

		int phone = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter customer phone number: "));

		if (custype.charAt(0) == 'I') {
			expYear = Integer
					.parseInt(JOptionPane.showInputDialog(null, "Enter customer driving experience (In year): "));
		} else {
			rating = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter customer ratings: "));
		}

		try (FileWriter fw = new FileWriter("Customer.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.print("\n");
			out.print(custype);
			out.print(",");
			out.print(custID);
			out.print(",");
			out.print(name);
			out.print(",");
			out.print(phone);

			if (custype.charAt(0) == 'I') {
				out.print(",");
				out.print(expYear);
			} else {
				out.print(",");
				out.print(rating);
			}

		} catch (IOException e) {
			System.out.println("Error " + e);
		}

		ARRAY_POSITION2 = 0;
		readCustomer();
	}

	public static void addVehicle() throws IOException {
		int loop = 0;

		JOptionPane.showMessageDialog(null, "1. Add Vehicle\n2. Add Premium Vehicle");
		int option = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter option: "));

		do {
			String ID = JOptionPane.showInputDialog(null, "Enter vehicle ID (6 Characters): ");
			String decs;
			double rate;
			int odo;
			int DailyMil = 0;
			int ServiceLen = 0;
			int Lastservice = 0;

			if (ID.length() != 6) {
				JOptionPane.showMessageDialog(null, "LIMIT ERROR!! Only 6 characters allowed");
			} else {
				decs = JOptionPane.showInputDialog(null, "Enter Vehicle Name: ");
				rate = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Vehicle Daily-Rate: "));
				odo = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Vehicle odometer: "));

				if (option == 2) {
					DailyMil = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Daily-Mileage: "));
					ServiceLen = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Service-Length: "));
					Lastservice = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Last Service: "));
				}

				try (FileWriter fw = new FileWriter("Vehicle.txt", true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter out = new PrintWriter(bw)) {
					out.print("\n");
					out.print(ID);
					out.print(",");
					out.print(decs);
					out.print(",");
					out.print(rate);
					out.print(",");
					out.print(odo);

					if (option == 2) {
						out.print(",");
						out.print(DailyMil);
						out.print(",");
						out.print(ServiceLen);
						out.print(",");
						out.print(Lastservice);
					}

				} catch (IOException e) {
					System.out.println("Error " + e);
				}

				ARRAY_FILLED = 0;
				ARRAY_POSITION = 0;
				readVehicle();
				loop++;
			}
		} while (loop == 0);

		JOptionPane.showMessageDialog(null, "Vehicle added successfully");
	}

//	 public static void AllVehicle() {
//		    // Create the table model with column names
//		    DefaultTableModel model = new DefaultTableModel();
//		    model.setColumnIdentifiers(new Object[]{"Vehicle ID", "Description", "Status", "Daily Rate", "Odometer"});
//
//		    // Add vehicle data to the table model
//		    for (int i = 0; i < ARRAY_FILLED; i++) {
//		        Vehicle vehicle = vehs[i];
//		        Object[] rowData = new Object[]{vehicle.getID(), vehicle.getDescription1(), vehicle.getStatus(),
//		                vehicle.getdailyRate(), vehicle.getOdometer()};
//		        model.addRow(rowData);
//		    }
//
//		    // Create the JTable with the table model
//		    JTable table = new JTable(model);
//
//		    // Create a scroll pane and add the table to it
//		    JScrollPane scrollPane = new JScrollPane(table);
//
//		    // Create a JFrame to hold the table
//		    JFrame frame = new JFrame("All Vehicles");
//		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		    frame.add(scrollPane);
//		    frame.pack();
//		    frame.setVisible(true);
//		}

	public static void AllVehicle() {
		// Create the table model with column names
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] { "Vehicle ID", "Description", "Status", "Daily Rate", "Odometer" });

		// Add vehicle data to the table model
		for (int i = 0; i < ARRAY_FILLED; i++) {
			Vehicle vehicle = vehs[i];
			Object[] rowData = new Object[] { vehicle.getID(), vehicle.getDescription1(), vehicle.getStatus(),
					vehicle.getdailyRate(), vehicle.getOdometer() };
			model.addRow(rowData);
		}

		// Create the JTable with the table model
		JTable table = new JTable(model);

		// Create a scroll pane and add the table to it
		JScrollPane scrollPane = new JScrollPane(table);

		// Create a "Go Back" button
		JButton goBackButton = new JButton("Go Back");

		// Add ActionListener to the button
		goBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loop = 1;
				try {
					frame.dispose(); // Close the frame
					loop = 1;
					menu();
				} catch (StatusException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (odoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		frame = new JFrame("All Vehicles");

		// Create a JFrame to hold the table and button
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollPane);
		frame.add(goBackButton, BorderLayout.SOUTH); // Add the button to the bottom of the frame
		frame.pack();
		frame.setVisible(true);
	}

	public static int searchCustomer() {
		int i = 0;
		int loop = 0;
		String custID = null;

		while (loop == 0) {
			i=0;
			custID=null;
			String ID = JOptionPane.showInputDialog("Enter customer ID:");
			if (ID == null) {
				// Close the input dialog and return -1
				return -1;
			}

			while (i < ARRAY_POSITION2 && !ID.equals(custID)) {
				custID = cust[i].getCusID();
				if (ID.equals(custID)) {
					loop = 1;
					return i;
				}
				i++;
			}

			JOptionPane.showMessageDialog(null, "No entries found. Try again.");
		}

		return -1;
	}

	public static int search() {
		int index = -1;
		String custID = null;
		boolean loop = true;

		while (loop) {
			String ID = JOptionPane.showInputDialog("Enter customer ID:");

			for (int i = 0; i < ARRAY_POSITION2; i++) {
				custID = cust[i].getCusID();
				if (ID.equals(custID)) {
					index = i;
					loop = false;
					break;
				}
			}

			if (index == -1) {
				JOptionPane.showMessageDialog(null, "No entries found. Try again.");
			}
		}

		return index;
	}

//	public static void hireVehicle() throws StatusException, IOException {
//
//		int loop = 0;
//		double p = 1;
//		Scanner in = new Scanner(System.in);
//		try {
//
//			System.out.print("Are you a registered Customer(Y/N): ");
//			String option = sc.next();
//
//			while (loop == 0) {
//				if (option.equals("N") || option.equals("n")) {
//					System.out.println("Please register to continue: ");
//					addCustomer();
//					System.out.println(
//							"Now you are ready to hire vehicle..........\n----------------------------------\n");
//					option = "Y";
//				} else {
//
//					int custIndex = SearchCustomer();
//					String ID = cust[custIndex].getCusID();
//
//					if (cust[custIndex] instanceof ICustomer) {
//
//						if (((ICustomer) cust[custIndex]).getCarRented() == 1) {
//							System.out.println("You have already hired one vehicle. Operating unsuccessful!!!");
//							break;
//						} else {
//							((ICustomer) cust[custIndex]).setCarRented(1);
//							int v = ArrayLoop();
//
//							vehs[v].hire(ID);
//							loop = 1;
//							System.out.println("\nHow many KM do you want to Go ?");
//							p = in.nextDouble();
//							System.out.println("Total Payable Amount: " + cust[custIndex].getDiscount(p * 30));
//
//						}
//
//					} else {
//
//						int v = ArrayLoop();
//
//						vehs[v].hire(ID);
//						loop = 1;
//						System.out.println("\nHow many KM do you want to Go ?");
//						p = in.nextDouble();
//
//						System.out.println("Total Payable Amount: " + cust[custIndex].getDiscount(p * 30));
//
//					}
//				}
//			}
//		} catch (StatusException e) {
//
//			System.out.println("Please hire when it's back from service. Hire Another Vehicle.");
//
//		}
//
//	}

	public static void hireVehicle() throws StatusException, IOException, odoException {
		int loop = 0;
		double p = 1;

		try {
			String option = JOptionPane.showInputDialog("Are you a registered Customer? (Y/N)");

			while (loop == 0) {
				if (option.equalsIgnoreCase("N")) {
					JOptionPane.showMessageDialog(null, "Please register to continue.");
					addCustomer();
					JOptionPane.showMessageDialog(null, "Now you are ready to hire a vehicle.");
					option = "Y";
				} else {
					int custIndex = searchCustomer();
					if (custIndex == -1) {
						menu();
					}
					String ID = cust[custIndex].getCusID();

					if (cust[custIndex] instanceof ICustomer) {
						if (((ICustomer) cust[custIndex]).getCarRented() == 1) {
							JOptionPane.showMessageDialog(null,
									"You have already hired one vehicle. Operation unsuccessful!");
							break;
						} else {
							((ICustomer) cust[custIndex]).setCarRented(1);
							int v = arrayLoop();
							vehs[v].hire(ID);
							loop = 1;
							String input = JOptionPane.showInputDialog("How many KM do you want to go?");
							p = Double.parseDouble(input);
							double totalPayableAmount = cust[custIndex].getDiscount(p * 30);
							JOptionPane.showMessageDialog(null, "Total Payable Amount: " + totalPayableAmount);
						}
					} else {
						int v = arrayLoop();
						vehs[v].hire(ID);
						loop = 1;
						String input = JOptionPane.showInputDialog("How many KM do you want to go?");
						p = Double.parseDouble(input);
						double totalPayableAmount = cust[custIndex].getDiscount(p * 30);
						JOptionPane.showMessageDialog(null, "Total Payable Amount: " + totalPayableAmount);
					}
				}
			}
		} catch (StatusException e) {
			JOptionPane.showMessageDialog(null,
					"Please hire when the vehicle is back from service. Hire another vehicle.");
		}
	}

//	
//
//	public static void selectedVehicle() {
//
//		double min, max;
//		int count = 0;
//
//		System.out.println("Enter Minimun Value:");
//		min = sc.nextDouble();
//		System.out.println("Enter Maximum Value:");
//		max = sc.nextDouble();
//
//		for (int j = 0; j < ARRAY_POSITION; j++) {
//
//			double price = vehs[j].getdailyRate();
//
//			if (price >= min && price <= max) {
//				count++;
//			} else {
//
//			}
//		}
//
//		if (count != 0) {
//
//			System.out.println(
//					"\n Following vehicle for specified price range::::\n----------------------------------------------------\n");
//
//			for (int z = 0; z < ARRAY_FILLED; z++) {
//
//				double price = vehs[z].getdailyRate();
//				String description = vehs[z].getDescription1();
//				String ID = vehs[z].getID();
//
//				if (price >= min && price <= max) {
//
//					System.out
//							.println("vehicle ID: " + ID + "  Description: " + description + "  Daily-Rate  " + price);
//				} else {
//
//				}
//			}
//		} else {
//			System.out.print("No Entry Found");
//		}
//	}

	public static void selectedVehicle() {
		double min, max;
		int count = 0;

		String input1 = JOptionPane.showInputDialog("Enter Minimum Value:");
		min = Double.parseDouble(input1);
		String input2 = JOptionPane.showInputDialog("Enter Maximum Value:");
		max = Double.parseDouble(input2);

		for (int j = 0; j < ARRAY_POSITION; j++) {
			double price = vehs[j].getdailyRate();
			if (price >= min && price <= max) {
				count++;
			}
		}

		if (count != 0) {
			StringBuilder output = new StringBuilder();
			output.append("Following vehicles for the specified price range:\n");
			output.append("---------------------------------------------\n");

			for (int z = 0; z < ARRAY_FILLED; z++) {
				double price = vehs[z].getdailyRate();
				String description = vehs[z].getDescription1();
				String ID = vehs[z].getID();

				if (price >= min && price <= max) {
					output.append("Vehicle ID: ").append(ID).append("\tDescription: ").append(description)
							.append("\tDaily-Rate: ").append(price).append("\n");
				}
			}

			JOptionPane.showMessageDialog(null, output.toString());
		} else {
			JOptionPane.showMessageDialog(null, "No Entry Found");
		}
	}

	public static int arrayLoop() {
		int L = 0;

		while (L == 0) {
			int i = 0;
			String VID = null;

			String ID = JOptionPane.showInputDialog("Enter Vehicle ID:");

			while (i < ARRAY_FILLED && !ID.equals(VID)) {
				VID = ManageHiring.vehs[i].getID();
				if (ID.equals(VID)) {
					return i;
				}
				i++;
			}

			i--;
			JOptionPane.showMessageDialog(null, "No Entries Found");
			L = 0;
		}

		return -1;
	}

}