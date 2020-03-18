package com.l2jfrozen.gameserver.handler.voicedcommandhandlers;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.handler.IVoicedCommandHandler;
import com.l2jfrozen.gameserver.model.L2World;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;

public class menu implements IVoicedCommandHandler
{
       private static final String[] VOICED_COMMANDS ={ "menu" };
      
       @Override
       public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
       {
           	   if (command.startsWith("menu"))
           	   {
           	      NpcHtmlMessage html = new NpcHtmlMessage(1);
           	      html.setFile("data/html/mods/ServerInfo/menu.htm");
           	      html.replace("%playername%", activeChar.getName());
           	      html.replace("%onlineplayers%", String.valueOf(L2World.getInstance().getAllPlayers().size()* Config.FAKE_PLAYERS));
           	      if (activeChar.getClan() != null)
           	      {
           	    	  html.replace("%Clan%", String.valueOf(activeChar.getClan().getName()));
           	      }
           	      else
           	      {
           	    	  html.replace("%Clan%", "<font color=FF0000>No</font>");
           	      }           	      if (activeChar.getClan().getAllyName() != null)
           	      {
           	    	  html.replace("%Alliance%", String.valueOf(activeChar.getClan().getAllyName()));
           	      }
           	      else
           	      {
           	    	  html.replace("%Alliance%", "<font color=FF0000>No</font>");
           	      }
           	      html.replace("%pvp%", String.valueOf(activeChar.getPvpKills()));
           	      html.replace("%pk%", String.valueOf(activeChar.getPkKills()));
           	      html.replace("%timeonline%", String.valueOf(ConverTime(activeChar.getOnlineTime())));
           	      activeChar.sendPacket(html);
           	   }
               return true;
       }
       
       private String ConverTime(long seconds)
       {
       long remainder = seconds;

       int hours = (int) (remainder / 3600);
       remainder = remainder -(hours * 3600);
      
       seconds = remainder;

       String timeInText = "";
       
       	if (timeInText=="")
       	{
           	if(hours > 0)
               	{
           			timeInText = hours+"<font color=\"LEVEL\">h.</font>";
               	}
           		else
               	{
           			timeInText = "N/A";
               	}
       	}
       	return timeInText;
       }
       
       @Override
       public String[] getVoicedCommandList()
       {
               return VOICED_COMMANDS;
       }
}