package com.l2jfrozen.gameserver.handler.voicedcommandhandlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.StringTokenizer;

import javolution.text.TextBuilder;

import com.l2jfrozen.gameserver.handler.IVoicedCommandHandler;
import com.l2jfrozen.gameserver.model.L2World;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author -=DoctorNo=-
 */
public class DonateCmd implements IVoicedCommandHandler
{
       private static final String[] VOICED_COMMANDS =
       {
               "donate",
               "donateCmd",
               "dlist",
       };
      
       @Override
       public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
       {
               if (command.startsWith("donate"))
               {
            	   showHtmlWindow(activeChar);
               }
               return true;
       }
       
  	  public void info(L2PcInstance player, int val)
  	  {
  	    String PARENT_DIR = "data/donates/list.htm";
  	    NpcHtmlMessage html = new NpcHtmlMessage(5);
  	    html.setFile(PARENT_DIR);
  	    player.sendPacket(html);
  	  }
  	  
   	public void onBypassFeedback(final L2PcInstance player, String command)
   	 {
   		      if(player == null)
   		      {
   		         return;
   		      }
   		      
   		      if(command.startsWith("dlist"))
   		      {
   		    	  info(player, 0);
   		      }
   		      if(command.startsWith("donateCmd"))
   		      {
   		    	  StringTokenizer st = new StringTokenizer(command);
   		    	  st.nextToken();
   		    	  String amount = null;
   		    	  int pin1 = 0;
   		    	  int pin2 = 0;
   		    	  int pin3 = 0;
   		    	  int pin4 = 0;
   		    	  String message = "";
   		    	  
   		    	  try
   		    	  {
   		    		 amount = st.nextToken();
   		    		 pin1 = Integer.parseInt(st.nextToken());
   		    		 pin2 = Integer.parseInt(st.nextToken());
   		    		 pin3 = Integer.parseInt(st.nextToken());
   		    		 pin4 = Integer.parseInt(st.nextToken());
   		    		 while(st.hasMoreTokens())
   		    			 message = message + st.nextToken() + " ";
   		    		 
   		    		 String fname = "data/donates/"+player.getName()+".txt";
   		    		 File file = new File(fname);
   		    		 boolean exist = file.createNewFile();
   		    		 if(!exist)
   		    		 {
   		    		     player.sendMessage("You have already sent a donation , GMs must check it first");
   		    		     return;
   		    		 }
   		    		 FileWriter fstream = new FileWriter(fname);
   		    		 BufferedWriter out = new BufferedWriter(fstream);
   		    		 out.write("Character Info: [Character: "+ player.getName() +"["+ player.getObjectId()+"] - Account: "+ player.getAccountName()+" - IP: "+player.getClient().getConnection().getInetAddress().getHostAddress()+"]\nMessage : donate "+ amount +" "+ message);
   		    		 out.close();
   		    		 player.sendMessage("Donation sent. GMs will check it soon. Thanks...");
   		    		 
   		    		 Collection<L2PcInstance> pls = L2World.getInstance().getAllPlayers();
   		    			 for (L2PcInstance gms : pls)
   		    				{
   		    				 	if(gms.isGM())
   		    				 	gms.sendMessage(player.getName() +" sent a donation.");
   		    				}
   		    	  }
   		    	  catch(Exception e)
   		    	  {
   		    		  e.printStackTrace();
   		    	  }
   		      }
   		      
   	  }
   	
 	  private void showHtmlWindow(L2PcInstance activeChar)
 	  {
 			TextBuilder tb = new TextBuilder();
 			NpcHtmlMessage html = new NpcHtmlMessage(1);
 			        
 				tb.append("<html>");
 				tb.append("<head>");
 				tb.append("<title>Donation Manager</title>");
 				tb.append("</head>");
 				tb.append("<body>");
 				tb.append("<center>");
 				tb.append("<table width=\"250\" bgcolor=\"000000\">");
 				tb.append("<tr>");
 				tb.append("<td align=center><font color=\"6fd3d1\">Easy Donation Segara</font></td>");
 				tb.append("</tr>");
 				tb.append("</table>");
 				tb.append("_______________________________________");
 				tb.append("<br>");
 				tb.append("<br>");
 				tb.append("<table width=\"250\">");
 				tb.append("<tr>");
 				tb.append("<td><font color=\"ddc16d\">Select Donation Amount:</font></td>");
 				tb.append("</tr>");
 				tb.append("<tr>");
 				tb.append("<td><combobox width=80 height=17 var=amount list=5-SGC;10-SGC;25-SGC;50-SGC;100-SGC;></td>");
 				tb.append("</tr>");
 				tb.append("</table>");
 				tb.append("<br>");
 				tb.append("<br>");
 				tb.append("<font color=\"ddc16d\">Message for GM's:</font>");
 				tb.append("<br>");
 				tb.append("<multiedit var=\"message\" width=240 height=40>");
 				tb.append("<br>");
 				tb.append("<br>");
 				tb.append("<button value=\"Donate!\" action=\"bypass -h .donateCmd $amount $message\" width=95 height=21 back=\"bigbutton_over\" fore=\"bigbutton\">");
 				tb.append("<br>");
 				tb.append("<button value=\"Donation List\" action=\"bypass -h voice .dlist\" width=95 height=21 back=\"bigbutton_over\" fore=\"bigbutton\">");
 				tb.append("<br>");
 				tb.append("<font color=\"a1df64\">http://L2Segara.com</font>");
 				tb.append("</center>");
 				tb.append("</body>");
 				tb.append("</html>");
 			        
 			html.setHtml(tb.toString());
 			activeChar.sendPacket(html);
 	  }
 	  
       @Override
       public String[] getVoicedCommandList()
       {
               return VOICED_COMMANDS;
       }
}