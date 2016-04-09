import java.io.*;
import java.net.*;
import java.util.Scanner;

//host name is 192.168.100.111
class TCPClient
{
   public static void main(String argv[]) throws Exception
   {
      if(argv.length > 0)
      {
      	//Declare variables
         String hostname = argv[0];
      	
      	//BufferedReader to get user input
         Scanner inFromUser = new Scanner(System.in);
      	
      	//Create Socket
         Socket clientSocket = new Socket(hostname, 2541);
      	
      	//Send user input to the Server
         PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
      	
      	//Get Server output 
         BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      	
         while(true)
         {
            String Option = "0";
            int num = 1;
            double avgTime = 0;
            
            System.out.println(Thread.activeCount());
         	
         	//Option Menu
            Runnable rArray[] = new Runnable[75];
            System.out.println("1. Host current Date and Time");
            System.out.println("2. Host uptime");
            System.out.println("3. Host memory use");
            System.out.println("4. Host Netstat");
            System.out.println("5. Host current users");
            System.out.println("6. Host running processes");
            System.out.println("7. Quit");
            
            //Get user Input
            Option = inFromUser.nextLine();
            
            System.out.println("Enter Number of Threads");
         	
         	//Get number of threads
            try {
               num = inFromUser.nextInt();
            } catch (Exception e){
            
            }
            
            if(Option.equals("1") || Option.equals("2") || Option.equals("3") || Option.equals("4")
            		|| Option.equals("5") || Option.equals("6"))
            {
            	threadTime time = new threadTime(num);

   				for(int i = 1; i < num + 1; i++) {
   				
   					// Create a new thread
   					MyThread thread = new MyThread(outToServer, inFromServer, Option, time);
   					thread.start();
   					
   					// Waits for all threads
   					try	{
   						thread.join();
   					} catch(InterruptedException e) {
   						System.out.println(e);
   					}
                  
                  if(num == 1)
                     System.out.println(thread.getResults());
                  
   					if ( (i % 5 == 0) || (i == 1) ) {
   						avgTime = time.getAverage();
   						System.out.println("Average time at " + i + " threads (in milliseconds): " + avgTime);
   					}
   				}
            } else if(Option.equals("7")) {
               outToServer.println(Option);
               break;
            }
            else
               System.out.println("Invalid Input");
         }
         
         clientSocket.close();
      }
      else
      {
         System.out.println("No agruments given");
      }
   }	
}

class MyThread extends Thread {
   private PrintWriter outToServer;
   private BufferedReader inFromServer;
   private String Option;
   private threadTime time;
   private long totalTime;
   private String FullResults;

   public MyThread(PrintWriter ots, BufferedReader ifs, String o, threadTime t){
      this.outToServer = ots;
      this.inFromServer = ifs;
      this.Option =  o;
      this.time = t;
   }

   public void run(){
      long totalTime;
		long startTime;
		long endTime;
		
		startTime = System.currentTimeMillis();
		
      try {
         outToServer.println(Option);
      } 
      catch (Exception e) {
         e.printStackTrace();
      }
		
		try {
			String FullResults = inFromServer.readLine();
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			time.add(totalTime);
		} catch(IOException e) {
			System.out.println(e);
		}

      return;
   }
   
   public String getResults(){
      return FullResults;
   }
}

class threadTime {
	long[] times;
   int counter;

	public threadTime(int n) {
      times = new long[n];
		counter = 0;
	}

	public synchronized void add (long t) {
		times[counter++] = t;
	}
	
	
	public double getAverage () {
		double avg = 0;
		
		for (int i = 0; i < counter; i++)
			avg += (double)times[i];
	
		avg = avg / (double)times.length;
		
		return avg;
	}	
}