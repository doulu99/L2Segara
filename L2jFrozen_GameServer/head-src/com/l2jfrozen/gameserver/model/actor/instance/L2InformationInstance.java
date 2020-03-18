/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package com.l2jfrozen.gameserver.model.actor.instance;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.ai.CtrlIntention;
import com.l2jfrozen.gameserver.model.L2World;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.network.serverpackets.SocialAction;
import com.l2jfrozen.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;
import com.l2jfrozen.util.random.Rnd;

public final class L2InformationInstance extends L2NpcInstance
{
  public L2InformationInstance(int objectId, L2NpcTemplate template)
  {
    super(objectId, template);
  }
  
  @Override
public void showChatWindow(L2PcInstance player)
  {
    String PARENT_DIR = "data/html/mods/ServerInfo/menu.htm";
    NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
    html.setFile(PARENT_DIR);
    html.replace("%objectId%", String.valueOf(getObjectId()));
    html.replace("%playername%", player.getName());
   	html.replace("%onlineplayers%", String.valueOf(L2World.getInstance().getAllPlayers().size()* Config.FAKE_PLAYERS));
	if (player.getClan() != null)
	{
		html.replace("%Clan%", player.getClan().getName());
		html.replace("%Alliance%", player.getClan().getAllyName());
	}
	else
	{
		html.replace("%Clan%", "<font color=FF0000>No</font>");
		html.replace("%Alliance%", "<font color=FF0000>No</font>");
	}
   	html.replace("%pvp%", player.getPvpKills());
   	html.replace("%pk%", player.getPkKills());
   	html.replace("%timeonline%", String.valueOf(ConverTime(player.getOnlineTime())));
    player.sendPacket(html);
  }
  
@Override
public void onAction(L2PcInstance player)
  {
    if (this != player.getTarget())
    {
      player.setTarget(this);
      player.sendPacket(new MyTargetSelected(getObjectId(), player.getLevel() - getLevel()));
      player.sendPacket(new ValidateLocation(this));
    }
    else if (isInsideRadius(player, 150, false, false))
    {
      SocialAction sa = new SocialAction(getObjectId(), Rnd.nextInt(8));
      broadcastPacket(sa);
      showChatWindow(player);
      player.sendPacket(ActionFailed.STATIC_PACKET);
    }
    else
    {
      player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
      player.sendPacket(ActionFailed.STATIC_PACKET);
    }
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

}
