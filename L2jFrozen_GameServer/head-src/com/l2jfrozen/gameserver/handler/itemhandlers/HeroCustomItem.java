// Hero Custom Item , Created By Stefoulis15
// Added From Stefoulis15 Into The Core.
// Visit www.MaxCheaters.com For Support 
// Source File Name:   HeroCustomItem.java
// Modded by programmos, sword dev

package com.l2jfrozen.gameserver.handler.itemhandlers;

import org.apache.log4j.Logger;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.handler.IItemHandler;
import com.l2jfrozen.gameserver.model.actor.instance.L2ItemInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PlayableInstance;
import com.l2jfrozen.gameserver.network.serverpackets.SocialAction;

public class HeroCustomItem implements IItemHandler
{
	
	public HeroCustomItem()
	{
		// null
	}
	
	protected static final Logger LOGGER = Logger.getLogger(HeroCustomItem.class);
	
	String INSERT_DATA = "REPLACE INTO characters_custom_data (obj_Id, char_name, hero, noble, donator, hero_end_date) VALUES (?,?,?,?,?,?)";
	
	@Override
	public void useItem(final L2PlayableInstance playable, final L2ItemInstance item)
	{
		if (Config.HERO_CUSTOM_ITEMS)
		{
			if (!(playable instanceof L2PcInstance))
				return;
			
			L2PcInstance activeChar = (L2PcInstance) playable;
			
			if (activeChar.isInOlympiadMode())
			{
				activeChar.sendMessage("This Item Cannot Be Used On Olympiad Games.");
			}
			
			if (activeChar.isHero())
			{
				activeChar.sendMessage("You Are Already A Hero!.");
			}
			else
			{
				activeChar.broadcastPacket(new SocialAction(activeChar.getObjectId(), 16));
				activeChar.setHero(true);
				activeChar.sendMessage("You Are Now a Hero,You Are Granted With Hero Status , Skills ,Aura.");
				activeChar.broadcastUserInfo();
				playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
				activeChar.getInventory().addItem("Wings", 6842, 1, activeChar, null);
			}
			activeChar = null;
		}
	}
	
	@Override
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
	private static final int ITEM_IDS[] =
	{
		Config.HERO_CUSTOM_ITEM_ID
	};
	
}
