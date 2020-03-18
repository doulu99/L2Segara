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
package com.l2jfrozen.gameserver.handler.itemhandlers;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.handler.IItemHandler;
import com.l2jfrozen.gameserver.model.actor.instance.L2ItemInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PlayableInstance;

/**
 * This class provides handling for items that should display a map when double clicked.
 * @version $Revision: 1.1.4.3 $ $Date: 2005/03/27 15:30:07 $
 */

public class RecItem implements IItemHandler
{
	// all the items ids that this handler knowns
	private static final int[] ITEM_IDS =
	{
		Config.REC_ITEM
	};
	
	@Override
	public void useItem(final L2PlayableInstance playable, final L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		final L2PcInstance activeChar = (L2PcInstance) playable;
		final int itemId = item.getItemId();
		if (itemId == Config.REC_ITEM)
		{
			
			activeChar.setRecomHave(activeChar.getRecomHave() + Config.REC_POINT);
			activeChar.sendMessage("Rec item is use. You take " + Config.REC_POINT + " Point.");
			playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
		}
		else
		{
			activeChar.sendMessage("something happened try again later");
		}
		
		return;
	}
	
	@Override
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}
