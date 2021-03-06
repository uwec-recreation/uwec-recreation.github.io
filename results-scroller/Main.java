import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
	
	static String fishFile = "entries.csv";
	static String prizeFile = "prizes.csv";
	static String raffleFile = "raffleprizes.csv";
	
	public static void main(String[] args) {
		File fishList = getCSVFile(fishFile);
		File prizeList = getCSVFile(prizeFile);
		File raffleList = getCSVFile(raffleFile);
		ArrayList<Prize> prizes = assemblePrizes(prizeList);
		ArrayList<Prize> raffleprizes = assemblePrizes(raffleList);
		ArrayList<Entry> entries = assembleEntries(fishList);
		ArrayList<Winner> winners = assembleWinners(entries, prizes);
		//printList(entries);
		createHTML(winners, raffleprizes);
	}
	
	private static void printList(ArrayList<Entry> entries) {
		for(int i = 0; i < entries.size(); i++) {
			Entry entry = entries.get(i);
			System.out.println("First: " + entry.getFirst() + "|Last: " + entry.getLast() + "|Fish: " + entry.getFish() + "|Weight: " + entry.getWeight() + "|Time: " + entry.getTime().toString());
		}
	}

	private static ArrayList<Entry> assembleEntries(File csv) {
		ArrayList<Entry> entries = new ArrayList<>();
		try {
			Scanner scnr = new Scanner(csv);
			scnr.nextLine();
			while(scnr.hasNextLine()) {
				String first, last, ticket, fish, weight, time;
				Scanner scnr2 = new Scanner(scnr.nextLine());
				scnr2.useDelimiter(",");
				ticket= scnr2.next();
				first = scnr2.next();
				last = scnr2.next();
				//scnr2.next();
				fish = scnr2.next();
				weight = scnr2.next();
				time = scnr2.next();
				scnr2.next();
				entries.add(new Entry(first, last, ticket, fish, weight, time));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entries;
	}
	
	private static ArrayList<Prize> assemblePrizes(File csv) {
		ArrayList<Prize> prizes = new ArrayList<>();
		try {
			Scanner scnr = new Scanner(csv);
			scnr.nextLine();
			scnr.nextLine();
			while(scnr.hasNextLine()) {
				int place = 0;
				String prize;
				Scanner scnr2 = new Scanner(scnr.nextLine());
				scnr2.useDelimiter(",");
				place = Integer.parseInt(scnr2.next());
				prize = scnr2.next();
				Prize newPrize = new Prize(place, prize);
				prizes.add(newPrize);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prizes;
	}
	
	private static ArrayList<Winner>assembleWinners(ArrayList<Entry> entries, ArrayList<Prize> prizes) {
		ArrayList<Winner> winners = new ArrayList<>();
		int i = 0;
		Iterator<Prize> prize = prizes.iterator();
		while(i < entries.size() && prize.hasNext()) {
			Winner winner = new Winner(entries.get(i), prize.next());
			winners.add(winner);
			if(i < 19) {
				i++;
			}else if(i < 479) {
				i += 10;
			} else {
				i++;
			}
		}
		return winners;
	}

	private static void createHTML(ArrayList<Winner> winners, ArrayList<Prize> raffleprizes) {
		try {
			PrintStream output = new PrintStream(new FileOutputStream("scroll.html"));
			output.println("<!doctype html>");
			output.println("<html land='en'>");
			output.println("<head>");
			output.println("\t<meta charset='utf-8'>");
			output.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css' integrity='sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS' crossorigin='anonymous'>");
			output.println("\t<title>Jigsup Winners</title>");
			output.println("</head>");
			output.println("<body style='max-width: 100%'>");
			output.println("\t<div id='table' class='row mx-0' style='display:block' width='1920px'>");
			output.println("\t\t<div class='col-10 mx-auto'>");
			output.println("\t\t\t<h1 class='text-center' style='font-size: 100px; color: #2b3e85'>Winners List</h1>");
			output.println("\t\t\t<table class='table table-bordered table-striped mt-4 mb-0'>");
			output.println("\t\t\t</table>");
			output.println("\t\t\t<div id='scrollable' style='height:500px; overflow-y:scroll; overflow-x:hidden;'>");
			output.println("\t\t\t\t<table class='table table-bordered table-striped'>");
			output.println("\t\t\t\t\t<tbody>");


			/****************Truck Winner****************/
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th colspan='6' style='font-size: x-large'>2021 Ford F150 XLT Super Crew Truck Winner</th>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<td colspan='6'></td>");
			output.println("\t\t\t\t\t\t</tr>");


			/****************Fish Prize Winners****************/
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th colspan='6' style='font-size: x-large'>Fish Prize Winners</th>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th>Place</th>");
			output.println("\t\t\t\t\t\t\t<th>Contestant</th>");
			output.println("\t\t\t\t\t\t\t<th>Ticket #</th>");
			output.println("\t\t\t\t\t\t\t<th>Weight</th>");
			output.println("\t\t\t\t\t\t\t<th>Species</th>");
			output.println("\t\t\t\t\t\t\t<th>Prize</th>");
			output.println("\t\t\t\t\t\t</tr>");
			Iterator<Winner> itr = winners.iterator();
			while(itr.hasNext()) {
				Winner winner = itr.next();
				output.println("\t\t\t\t\t\t<tr>");
				output.println("\t\t\t\t\t\t\t<th scope='row' width='5%'>" + winner.getPrize().getPlace() + "</th>");
				output.println("\t\t\t\t\t\t\t<td width='30%'>" + winner.getEntry().getFirst() + " " + winner.getEntry().getLast() + "</td>");
				output.println("\t\t\t\t\t\t\t<td width='10%'>" + winner.getEntry().getTicket() + "</td>");
				output.println("\t\t\t\t\t\t\t<td width='10%'>" + winner.getEntry().getWeight() + "</td>");
				output.println("\t\t\t\t\t\t\t<td width='10%'>" + winner.getEntry().getFish() + "</td>");
				output.println("\t\t\t\t\t\t\t<td width='35%'>" + winner.getPrize().getName() + "</td>");
				output.println("\t\t\t\t\t\t</tr>");
			}


			/****************Raffle Prize Winners****************/
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th colspan='6' style='font-size: x-large'>Raffle Prize Winners</th>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th>Place</th>");
			output.println("\t\t\t\t\t\t\t<th>Contestant</th>");
			output.println("\t\t\t\t\t\t\t<th colspan='3'>Ticket #</th>");
			output.println("\t\t\t\t\t\t\t<th>Prize</th>");
			output.println("\t\t\t\t\t\t</tr>");
			Iterator<Prize> itr1 = raffleprizes.iterator();
			while(itr1.hasNext()) {
				Prize prize = itr1.next();
				output.println("\t\t\t\t\t\t<tr>");
				output.println("\t\t\t\t\t\t\t<th>" + prize.getPlace() + "</th>");
				output.println("\t\t\t\t\t\t\t<td></td>");
				output.println("\t\t\t\t\t\t\t<td colspan='3'></td>");
				output.println("\t\t\t\t\t\t\t<td>" + prize.getName() + "</td>");
				output.println("\t\t\t\t\t\t</tr>");
			}


			/****************Species Prize Winners****************/
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th colspan='6' style='font-size: x-large'>Species Prize Winners</th>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th colspan='2'>Contestant</th>");
			output.println("\t\t\t\t\t\t\t<th>Ticket #</th>");
			output.println("\t\t\t\t\t\t\t<th colspan='2'>Species</th>");
			output.println("\t\t\t\t\t\t\t<th>Weight</th>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<td colspan='2'></td>");
			output.println("\t\t\t\t\t\t\t<td></td>");
			output.println("\t\t\t\t\t\t\t<td colspan='2'>Northern</td>");
			output.println("\t\t\t\t\t\t\t<td></td>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<td colspan='2'></td>");
			output.println("\t\t\t\t\t\t\t<td></td>");
			output.println("\t\t\t\t\t\t\t<td colspan='2'>Walleye</td>");
			output.println("\t\t\t\t\t\t\t<td></td>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<td colspan='2'></td>");
			output.println("\t\t\t\t\t\t\t<td></td>");
			output.println("\t\t\t\t\t\t\t<td colspan='2'>Crappie (Pan Fish Category)</td>");
			output.println("\t\t\t\t\t\t\t<td></td>");
			output.println("\t\t\t\t\t\t</tr>");


			/****************The One That Got Away Prize Winner****************/
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th colspan='6' style='font-size: x-large'>The One That Got Away Prize Winner</th>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<th colspan='2'>Contestant</th>");
			output.println("\t\t\t\t\t\t\t<th colspan='3'>Ticket #</th>");
			output.println("\t\t\t\t\t\t\t<th>Prize</th>");
			output.println("\t\t\t\t\t\t</tr>");
			output.println("\t\t\t\t\t\t<tr>");
			output.println("\t\t\t\t\t\t\t<td colspan='2'></td>");
			output.println("\t\t\t\t\t\t\t<td colspan='3'></td>");
			output.println("\t\t\t\t\t\t\t<td>Eskimo Mako 43cc 8 Inch Auger</td>");
			output.println("\t\t\t\t\t\t</tr>");

			output.println("\t\t\t\t\t</tbody>");
			output.println("\t\t\t\t</table>");
			output.println("\t\t\t</div>");
			output.println("\t\t</div>");
			output.println("\t</div>");
			output.println("\t<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>");
			output.println("\t<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js' integrity='sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut' crossorigin='anonymous'></script>");
			output.println("\t<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js' integrity='sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa' crossorigin='anonymous'></script>");
			output.println("\t<script src='./js/scroll.js'></script>");
			output.println("\t<style>");
			output.println("\t::-webkit-scrollbar {");
			output.println("\t\tdisplay: none;");
			output.println("\t}");
			output.println("\t</style>");
			output.println("</body>");
			output.println("</html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static File getCSVFile(String path) {
		File file = new File(path);
		return file;
	}
	
}
