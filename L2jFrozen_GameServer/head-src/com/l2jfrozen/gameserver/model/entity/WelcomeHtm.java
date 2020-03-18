package com.l2jfrozen.gameserver.model.entity;

import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.model.entity.olympiad.Olympiad;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;

import java.util.logging.Logger;

public class WelcomeHtm
{
  protected static int _period;
  protected static final Logger _log = Logger.getLogger(WelcomeHtm.class.getName());
  
  public static void ShowWelcome(L2PcInstance activeChar, String command, String target)
  {
		long milliToEnd;
		if(_period == 0)
		{
			milliToEnd = Olympiad.getMillisToOlympiadEnd();
		}
		else
		{
			milliToEnd = Olympiad.getMillisToValidationEnd();
		}

		double numSecs = milliToEnd / 1000 % 60;
		double countDown = (milliToEnd / 1000 - numSecs) / 60;
		int numMins = (int) Math.floor(countDown % 60);
		countDown = (countDown - numMins) / 60;
		int numHours = (int) Math.floor(countDown % 24);
		int numDays = (int) Math.floor((countDown - numHours) / 24);
		
    String[] chat = command.split(" ");
    if (chat[0].endsWith("menu-1"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-1.htm");
      html.replace("%playername%", activeChar.getName());
      activeChar.sendPacket(html);
    }
    else if (chat[0].endsWith("menu-2"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-2.htm");
      html.replace("%playername%", activeChar.getName());
      activeChar.sendPacket(html);
    }
    
    else if (chat[0].endsWith("menu-3"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-3.htm");
      html.replace("%playername%", activeChar.getName());
      activeChar.sendPacket(html);
    }
    
    else if (chat[0].endsWith("menu-4"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-4.htm");
      html.replace("%playername%", activeChar.getName());
	  html.replace("%OlympaidEnd%", "Olympiad period ends in " + numDays + " days," + numHours + " hours, " + numMins + " mins." );
      activeChar.sendPacket(html);
    }
    else if (chat[0].endsWith("menu-5"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-5.htm");
      html.replace("%playername%", activeChar.getName());
      activeChar.sendPacket(html);
    }
    
    else if (chat[0].endsWith("menu-6"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-6.htm");
      html.replace("%playername%", activeChar.getName());
      activeChar.sendPacket(html);
    }
    else if (chat[0].endsWith("menu-7"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-7.htm");
      html.replace("%playername%", activeChar.getName());
      activeChar.sendPacket(html);
    }
    
    else if (chat[0].endsWith("menu-8"))
    {
      NpcHtmlMessage html = new NpcHtmlMessage(1);
      html.setFile("data/html/mods/ServerInfo/menu-8.htm");
      html.replace("%playername%", activeChar.getName());
      activeChar.sendPacket(html);
    }
  }
}
