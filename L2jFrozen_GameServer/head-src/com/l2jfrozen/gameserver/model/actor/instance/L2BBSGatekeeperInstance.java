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

import com.l2jfrozen.gameserver.ai.CtrlIntention;
import com.l2jfrozen.gameserver.cache.HtmCache;
import com.l2jfrozen.gameserver.communitybbs.Manager.BaseBBSManager;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfrozen.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;

public class L2BBSGatekeeperInstance extends L2FolkInstance
{
  
  public L2BBSGatekeeperInstance(int objectId, L2NpcTemplate template)
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
	  	String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/gatekeeper/1.htm");
		BaseBBSManager.separateAndSend(text, activeChar);
    
  }
}