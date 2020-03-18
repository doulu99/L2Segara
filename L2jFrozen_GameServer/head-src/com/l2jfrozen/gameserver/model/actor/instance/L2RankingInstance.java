package com.l2jfrozen.gameserver.model.actor.instance;

import com.l2jfrozen.gameserver.model.RankingOnlineManager;
import com.l2jfrozen.gameserver.model.RankingPKManager;
import com.l2jfrozen.gameserver.model.RankingPvPManager;
import com.l2jfrozen.gameserver.network.SystemMessageId;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;

public class L2RankingInstance extends L2NpcInstance
{
  public L2RankingInstance(int objectId, L2NpcTemplate template)
  {
   super(objectId, template);
  }
  
  @Override
  public void onBypassFeedback(L2PcInstance player, String command)
  {
   if (player.isProcessingTransaction())
   {
    player.sendPacket(SystemMessageId.ALREADY_TRADING);
    return;
   }


   else if (command.startsWith("topPvp"))
   {
	   RankingPvPManager pl = new RankingPvPManager();
	   player.sendPacket(ActionFailed.STATIC_PACKET);
	   String filename = "data/html/mods/Ranking-no.htm";
	   
	   filename = "data/html/mods/Ranking/RankingPvP.htm";
	   
	   NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
	   html.setFile(filename);
	   html.replace("%PlayerPvP%", pl.loadPlayerPvP());
	   html.replace("%objectId%", String.valueOf(getObjectId()));
	   html.replace("%playerName%", player.getName());
	   player.sendPacket(html);
   }
   
   else if (command.startsWith("topPK"))
   {
	   RankingPKManager pl1 = new RankingPKManager();
	   player.sendPacket(ActionFailed.STATIC_PACKET);
	   String filename = "data/html/mods/Ranking-no.htm";
	   
	   filename = "data/html/mods/Ranking/RankingPK.htm";
	   
	   NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
	   html.setFile(filename);
	   html.replace("%PlayerPK%", pl1.loadPlayerPK());
	   html.replace("%objectId%", String.valueOf(getObjectId()));
	   html.replace("%playerName%", player.getName());
	   player.sendPacket(html);
   }
   
   else if (command.startsWith("Online"))
   {
	   RankingOnlineManager pl2 = new RankingOnlineManager();
	   player.sendPacket(ActionFailed.STATIC_PACKET);
	   String filename = "data/html/mods/Ranking-no.htm";
	   
	   filename = "data/html/mods/Ranking/Ranking.htm";
	   
	   NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
	   html.setFile(filename);
	   html.replace("%PlayerOnline%", pl2.loadPlayerOnline());
	   html.replace("%objectId%", String.valueOf(getObjectId()));
	   html.replace("%playerName%", player.getName());
	   player.sendPacket(html);
   }
  }
  
  @Override
  public void showChatWindow(L2PcInstance player, int val)
  {
	RankingPvPManager pl = new RankingPvPManager();
	RankingPKManager pl1 = new RankingPKManager();
	RankingOnlineManager pl2 = new RankingOnlineManager();
   player.sendPacket(ActionFailed.STATIC_PACKET);
   String filename = "data/html/mods/Ranking-no.htm";
   
   filename = "data/html/mods/Ranking/Ranking.htm";
   
   NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
   html.setFile(filename);
   html.replace("%PlayerPvP%", pl.loadPlayerPvP());
   html.replace("%PlayerPK%", pl1.loadPlayerPK());
   html.replace("%PlayerOnline%", pl2.loadPlayerOnline());
   html.replace("%objectId%", String.valueOf(getObjectId()));
   html.replace("%playerName%", player.getName());
   player.sendPacket(html);
  }
}