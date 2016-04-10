import java.io.*;
import java.net.*;

class TCPServer
{

   public static void main(String argv[]) throws Exception
   {
      ServerSocket welcomeSocket = new ServerSocket(2541);
      Socket connectionSocket = welcomeSocket.accept();
      int choice = 0;											//choice to be used in switch statement to execute system commands
      String clientSentence;
      boolean exitFlag = false;
         
      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
      	  
      
      while(!exitFlag)
      {
    	  
         clientSentence = inFromClient.readLine();		//reads lines from client
         choice = Integer.parseInt(clientSentence);		//choice to be used in switch statement
         System.out.println(choice);
         switch(choice){								//this switch statement is used only to check for input "7" to quit program
         	
            case 7:
               try
               {
                  connectionSocket.close();				//ends connection
                  welcomeSocket.close();
                  inFromClient.close();
              
               }
               catch(Exception x)
               {
                 	 
               }

               exitFlag = true;							//exitFlag set to true to exit out of while loop
               break;
         	
            default:
            	
            	MyThread thread = new MyThread(connectionSocket, choice);	//creates a thread for all other requests other than "7"
            	thread.start();												//looks for run method to start the thread
            	
            	
               break;           
         }
      }  
   }
}

class MyThread extends Thread {

	Socket connectionSocket;
	int choice;
    Process p;
    Runtime run;
    BufferedReader br;
    String clientSentence;
    String command;
    String line;
    String localFinal;
    
    public MyThread(Socket s, int o){			//socket and choice for switch statement are passed into the thread
    	
    	this.connectionSocket = s;
    	this.choice = o;
    	this.localFinal = null;
    	
    }
    
    public void run(){
    	
    	try{
    	PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);		//create printWriter to output to client
    	
    	
    	switch(choice){
        case 1:	//get system date to be sent to client
				run = Runtime.getRuntime();
				command = "date";

				p = run.exec(command);
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				localFinal = br.readLine();
				outToClient.println(localFinal);

				localFinal = "";
				break;
                    	
        case 2:	 //get system uptime info to be sent to client
           run = Runtime.getRuntime();
				command = "uptime";

				p = run.exec(command);
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				localFinal = br.readLine();
				outToClient.println(localFinal);

				localFinal = "";
				break;

        case 3:	 //get available system memory info to be sent to client
           run = Runtime.getRuntime();
				command = "free";

				p = run.exec(command);
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));

				while ((line = br.readLine()) != null) 
					localFinal = localFinal + line;

				outToClient.println(localFinal);

				localFinal = "";
				break;
     
        case 4:	 //get netstat info to be sent to client
           run = Runtime.getRuntime();
				command = "netstat";

				p = run.exec(command);
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));

				while ((line = br.readLine()) != null) 
					localFinal = localFinal + line;

				outToClient.println(localFinal);

				localFinal = "";
				break;
     
        case 5:	 //get currently connected user info to be sent to client
           run = Runtime.getRuntime();
				command = "who";

				p = run.exec(command);
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				while ((line = br.readLine()) != null) 
					localFinal = localFinal + line;

				outToClient.println(localFinal);

				localFinal = "";
				break;
     
        case 6:	//get current running processes of system info to be sent to client
				run = Runtime.getRuntime();
				command = "ps -e";

				p = run.exec(command);

				br = new BufferedReader(new InputStreamReader(p.getInputStream()));

				while ((line = br.readLine()) != null) 
					localFinal = localFinal + line;

				outToClient.println(localFinal);

				localFinal = "";
				break;
     	
        
     	
        default:
           break;           
     }
    	}catch(IOException e){
    		
    	}
    	
    }
	
}
