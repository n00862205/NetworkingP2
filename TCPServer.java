import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

class TCPServer
{

   public static void main(String argv[]) throws Exception
   {
      ServerSocket welcomeSocket = new ServerSocket(2541);
      Socket connectionSocket = welcomeSocket.accept();
      int choice = 0;
      Process p;
      Runtime run;
      BufferedReader br;
      String clientSentence;
      String command;
      String line;
      String localFinal = null;
      boolean exitFlag = false;
      
      PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);      
      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
      
      while(!exitFlag)
      {
      
         clientSentence = inFromClient.readLine();
         choice = Integer.parseInt(clientSentence);
         
         switch(choice){
            case 1:
					run = Runtime.getRuntime();
					command = "date";

					p = run.exec(command);
					br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					localFinal = br.readLine();
					outToClient.println(localFinal);

					localFinal = "";
					break;
                        	
            case 2:
               run = Runtime.getRuntime();
					command = "uptime";

					p = run.exec(command);
					br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					localFinal = br.readLine();
					outToClient.println(localFinal);

					localFinal = "";
					break;

            case 3:
               run = Runtime.getRuntime();
					command = "free";

					p = run.exec(command);
					br = new BufferedReader(new InputStreamReader(p.getInputStream()));

					while ((line = br.readLine()) != null) 
						localFinal = localFinal + line;

					outToClient.println(localFinal);

					localFinal = "";
					break;
         
            case 4:
               run = Runtime.getRuntime();
					command = "netstat";

					p = run.exec(command);
					br = new BufferedReader(new InputStreamReader(p.getInputStream()));

					while ((line = br.readLine()) != null) 
						localFinal = localFinal + line;

					outToClient.println(localFinal);

					localFinal = "";
					break;
         
            case 5:
               run = Runtime.getRuntime();
					command = "who";

					p = run.exec(command);
					br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					
					while ((line = br.readLine()) != null) 
						localFinal = localFinal + line;

					outToClient.println(localFinal);

					localFinal = "";
					break;
         
            case 6:
					run = Runtime.getRuntime();
					command = "ps -e";

					p = run.exec(command);
					br = new BufferedReader(new InputStreamReader(p.getInputStream()));

					while ((line = br.readLine()) != null) 
						localFinal = localFinal + line;

					outToClient.println(localFinal);

					localFinal = "";
					break;
         	
            case 7:
               try
               {
                  connectionSocket.close();
                  welcomeSocket.close();
                  inFromClient.close();
                  outToClient.close();
               }
               catch(Exception x)
               {
                 	 
               }

               exitFlag = true;
               break;
         	
            default:
               break;           
         }
      }  
   }
}