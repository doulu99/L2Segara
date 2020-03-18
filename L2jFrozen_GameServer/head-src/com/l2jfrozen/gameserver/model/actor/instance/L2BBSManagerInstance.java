/* This program is free software; you can redistribute it and/or modify
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

import java.text.SimpleDateFormat;
import java.util.Date;

import com.l2jfrozen.gameserver.ai.CtrlIntention;
import com.l2jfrozen.gameserver.cache.HtmCache;
import com.l2jfrozen.gameserver.communitybbs.Manager.BaseBBSManager;
import com.l2jfrozen.gameserver.model.L2Clan;
import com.l2jfrozen.gameserver.model.L2World;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfrozen.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;

public class L2BBSManagerInstance extends L2FolkInstance
{
  protected final SimpleDateFormat fmt = new SimpleDateFormat("H:mm.");
 

  
  public L2BBSManagerInstance(int objectId, L2NpcTemplate template)
  {
    super(objectId, template);
  }
  
	@Override
	public void onAction(L2PcInstance activeChar)
	{
		if (!(canTarget(activeChar)))
		{
			return;
		}
		
		if (this != activeChar.getTarget())
		{
			activeChar.setTarget(this);
			
			activeChar.sendPacket(new MyTargetSelected(getObjectId(), 0));
			
			activeChar.sendPacket(new ValidateLocation(this));
		}
		else if (!(canInteract(activeChar)))
		{
			activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
		}
		else
		{
			showHtmlWindow(activeChar, toString());
		}
		
		activeChar.sendPacket(new ActionFailed());
	}

	
  private void showHtmlWindow(L2PcInstance activeChar, String command)
  {
	  	String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
		//Custom Community Board
		text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
		text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
		text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
		if (activeChar.isNoble())
		{
			text = text.replace("%nobless%", "Yes");
		}
		else
		{
			text = text.replace("%nobless%", "No");
		}
		L2Clan clan = activeChar.getClan();
		if (clan != null)
		{
			text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
		}
		else
		{
			text = text.replace("%CharClan%", "No Clan");
		}
		text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
		text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
		text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
		//Custom Community Board
		BaseBBSManager.separateAndSend(text, activeChar);
    
  }
}