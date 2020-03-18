package com.l2jfrozen.gameserver.model.actor.instance;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.ai.CtrlIntention;
import com.l2jfrozen.gameserver.datatables.sql.ItemTable;
import com.l2jfrozen.gameserver.model.votereward.VoteMain;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.ItemList;
import com.l2jfrozen.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;
import javolution.text.TextBuilder;

public class L2VoteManagerInstance
  extends L2FolkInstance
{
  public L2VoteManagerInstance(int objectId, L2NpcTemplate template)
  {
    super(objectId, template);
  }
  
  @Override
public void onBypassFeedback(L2PcInstance player, String command)
  {
    if (player == null) {
      return;
    }
    if (command.startsWith("votehopzone"))
    {
      VoteMain.hopvote(player);
    }
    if (command.startsWith("votetopzone"))
    {
      VoteMain.topvote(player);
    }
    if ((command.startsWith("rewards")) && VoteMain.hasVotedHop() && VoteMain.hasVotedTop())
    {
      showRewardsHtml(player);
    }
    
    if ((command.startsWith("reward1")) && VoteMain.hasVotedHop() && VoteMain.hasVotedTop())
    {
      player.getInventory().addItem("reward", Config.VOTE_REWARD_ID1, Config.VOTE_REWARD_AMOUNT1, player, null);
      player.sendMessage("Thanks you for votes. Take your reward.");
      player.sendPacket(new ItemList(player, true));
      VoteMain.setHasNotVotedHop(player);
      VoteMain.setHasNotVotedTop(player);
      VoteMain.setTries(player, VoteMain.getTries(player) + 1);
    }
    
    if ((command.startsWith("reward2")) && VoteMain.hasVotedHop() && VoteMain.hasVotedTop())
    {
      player.getInventory().addItem("reward", Config.VOTE_REWARD_ID2, Config.VOTE_REWARD_AMOUNT2, player, null);
      player.sendMessage("Thanks you for votes. Take your reward.");
      player.sendPacket(new ItemList(player, true));
      VoteMain.setHasNotVotedHop(player);
      VoteMain.setHasNotVotedTop(player);
      VoteMain.setTries(player, VoteMain.getTries(player) + 1);
    }
    
    if ((command.startsWith("reward3")) && VoteMain.hasVotedHop() && VoteMain.hasVotedTop())
    {
      player.getInventory().addItem("reward", Config.VOTE_REWARD_ID3, Config.VOTE_REWARD_AMOUNT3, player, null);
      player.sendMessage("Thanks you for votes. Take your reward.");
      player.sendPacket(new ItemList(player, true));
      VoteMain.setHasNotVotedHop(player);
      VoteMain.setHasNotVotedTop(player);
      VoteMain.setTries(player, VoteMain.getTries(player) + 1);
    }
    
    if ((command.startsWith("reward4")) && VoteMain.hasVotedHop() && VoteMain.hasVotedTop())
    {
      player.getInventory().addItem("reward", Config.VOTE_REWARD_ID4, Config.VOTE_REWARD_AMOUNT4, player, null);
      player.sendMessage("Thanks you for votes. Take your reward.");
      player.sendPacket(new ItemList(player, true));
      VoteMain.setHasNotVotedHop(player);
      VoteMain.setHasNotVotedTop(player);
      VoteMain.setTries(player, VoteMain.getTries(player) + 1);
    }
  }
  
  @Override
public void onAction(L2PcInstance player)
  {
    if (this != player.getTarget())
    {
      player.setTarget(this);
      
      player.sendPacket(new MyTargetSelected(getObjectId(), 0));
      
      player.sendPacket(new ValidateLocation(this));
    }
    else if (!canInteract(player))
    {
      player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
    }
    else
    {
      showHtmlWindow(player);
    }
    player.sendPacket(ActionFailed.STATIC_PACKET);
  }
  
  public void showHtmlWindow(L2PcInstance activeChar)
  {
    VoteMain.hasVotedHop(activeChar);
    VoteMain.hasVotedTop(activeChar);
    
    TextBuilder tb = new TextBuilder();
    NpcHtmlMessage html = new NpcHtmlMessage(1);
    
    tb.append("<html><head><title>Vote reward Panel</title></head><body><center><br><br>");
    tb.append("<table bgcolor=\"FFFFFF\"><tr><td align=\"center\"><font color=\"00ff99\">Who's voting now: </font>" + VoteMain.whosVoting() + "</td></tr>");
    tb.append("<tr><td align=\"center\"><font color=\"00ffff\">Tries left: </font>" + VoteMain.getTries(activeChar) + "</td></tr>");
    tb.append("<tr><td align=\"center\"><font color=\"00ffff\">Max seconds until vote: </font>" + Config.SECS_TO_VOTE + "(s)</td></tr>");
    if (Config.VOTE_REWARD_ID1 > 0)
    {
      tb.append("<tr><td align=\"center\"><font color=\"00ffff\">1) " + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID1).getName() + ":</font> " + Config.VOTE_REWARD_AMOUNT1 + "</td></tr>");
    }
    if (Config.VOTE_REWARD_ID2 > 0)
    {
      tb.append("<tr><td align=\"center\"><font color=\"00ffff\">2) " + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID2).getName() + ":</font> " + Config.VOTE_REWARD_AMOUNT2 + "</td></tr>");
    }
    if (Config.VOTE_REWARD_ID3 > 0)
    {
      tb.append("<tr><td align=\"center\"><font color=\"00ffff\">2) " + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID3).getName() + ":</font> " + Config.VOTE_REWARD_AMOUNT3 + "</td></tr>");
    }
    if (Config.VOTE_REWARD_ID4 > 0) 
    {
      tb.append("<tr><td align=\"center\"><font color=\"00ffff\">2) " + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID4).getName() + ":</font> " + Config.VOTE_REWARD_AMOUNT4 + "</td></tr>");
    }
    tb.append("<tr><td align=\"center\"><font color=\"FF6600\">You can vote in Hopzone at " + VoteMain.hopCd(activeChar) + "</font></td></tr>");
    tb.append("<tr><td align=\"center\"><font color=\"FF6600\">You can vote in Topzone at " + VoteMain.topCd(activeChar) + "</font></td></tr>");
    tb.append("</table>");
    tb.append("</center><br><br>");
    tb.append("<center>");
    if ((VoteMain.hasVotedHop()) && (!VoteMain.hasVotedTop()))
    {
      tb.append("<td><font color = \"00FF00\">For reward you must vote on TOPZONE TOO!</font></td>");
      tb.append("<td><button value=\"Vote Topzone\" action=\"bypass -h npc_" + getObjectId() + "_votetopzone\" width=\"94\" height=\"21\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    }
    else if ((!VoteMain.hasVotedHop()) && (VoteMain.hasVotedTop()))
    {
      tb.append("<td><font color = \"00FF00\">For reward you must vote on HOPZONE TOO!</font></td>");
      tb.append("<td><button value=\"Vote Hopzone\" action=\"bypass -h npc_" + getObjectId() + "_votehopzone\" width=\"94\" height=\"21\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    }
    else if ((!VoteMain.hasVotedHop()) && (!VoteMain.hasVotedTop()))
    {
      tb.append("<td><button value=\"Vote Hopzone\" action=\"bypass -h npc_" + getObjectId() + "_votehopzone\" width=\"94\" height=\"21\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
      tb.append("<td><button value=\"Vote Topzone\" action=\"bypass -h npc_" + getObjectId() + "_votetopzone\" width=\"94\" height=\"21\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    }
    else
    {
      tb.append("<td><button value=\"Take The REWARD\" action=\"bypass -h npc_" + getObjectId() + "_rewards\" width=\"94\" height=\"21\" back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    }
    tb.append("<table width=200>");
    if (!VoteMain.hasVotedHop())
    {
      tb.append("<tr>");
      tb.append("<td align=\"center\"><font color=\"FF6600\">Hopzone Status: </font><font color=\"00FFFF\">NOT VOTED.</font></td>");
      tb.append("</tr>");
    }
    else
    {
      tb.append("<tr>");
      tb.append("<td align=\"center\"><font color=\"FF6600\">Hopzone Status: </font><font color=\"FF00FF\">VOTED.</font></td>");
      tb.append("</tr>");
    }
    if (!VoteMain.hasVotedTop())
    {
      tb.append("<tr>");
      tb.append("<td align=\"center\"><font color=\"FF6600\">Topzone Status: </font><font color=\"00FFFF\">NOT VOTED.</font></td>");
      tb.append("</tr>");
    }
    else
    {
      tb.append("<tr>");
      tb.append("<td align=\"center\"><font color=\"FF6600\">Topzone Status: </font><font color=\"FF00FF\">VOTED.</font></td>");
      tb.append("</tr>");
    }
    tb.append("</table>");
    tb.append("<br><br>");
    tb.append("<table width=200>");
    tb.append("<tr><td align=\"center\"><font color=\"FF6600\">Your total votes in general: </font>" + VoteMain.getTotalVotes(activeChar) + "</td></tr>");
    tb.append("<tr><td align=\"center\"><font color=\"FF6600\">Players voted in general: </font>" + VoteMain.getBigTotalVotes(activeChar) + "</td></tr>");
    tb.append("</table>");
    tb.append("</center>");
    tb.append("</body></html>");
    
    html.setHtml(tb.toString());
    activeChar.sendPacket(html);
  }
  
  public void showRewardsHtml(L2PcInstance player)
  {
    TextBuilder tb = new TextBuilder();
    NpcHtmlMessage html = new NpcHtmlMessage(1);
    
    tb.append("<html><head><title>Vote Reward Panel</title></head><body>");
    tb.append("<center>");
    tb.append("<table width=\"250\" cellpadding=\"5\" bgcolor=\"000000\">");
    tb.append("<tr>");
    tb.append("<td width=\"45\" valign=\"top\" align=\"center\"><img src=\"L2ui_ch3.menubutton4\" width=\"38\" height=\"38\"></td>");
    tb.append("<td valign=\"top\"><font color=\"FF6600\">Vote Panel</font>");
    tb.append("<br1><font color=\"00FF00\">" + player.getName() + "</font>, get your reward here.</td>");
    tb.append("</tr>");
    tb.append("</table>");
    tb.append("</center>");
    tb.append("<center>");
    tb.append("<td valign=\"top\"><font color=\"FF6600\">Choose your reward " + player.getName() + ".</font>");
    if (Config.VOTE_REWARD_ID1 > 0) {
      tb.append("<button value=\"Item:" + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID1).getName() + "   Amount:" + Config.VOTE_REWARD_AMOUNT1 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward1\" width=204 height=20>");
    }
    if (Config.VOTE_REWARD_ID2 > 0) {
      tb.append("<button value=\"Item:" + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID2).getName() + "   Amount:" + Config.VOTE_REWARD_AMOUNT2 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward2\" width=204 height=20>");
    }
    if (Config.VOTE_REWARD_ID3 > 0) {
      tb.append("<button value=\"Item:" + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID3).getName() + "   Amount:" + Config.VOTE_REWARD_AMOUNT3 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward3\" width=204 height=20>");
    }
    if (Config.VOTE_REWARD_ID4 > 0) {
      tb.append("<button value=\"Item:" + ItemTable.getInstance().getTemplate(Config.VOTE_REWARD_ID4).getName() + "   Amount:" + Config.VOTE_REWARD_AMOUNT4 + "\" action=\"bypass -h npc_" + getObjectId() + "_reward4\" width=204 height=20>");
    }
    tb.append("</center>");
    
    tb.append("</body></html>");
    
    html.setHtml(tb.toString());
    player.sendPacket(html);
  }
}
