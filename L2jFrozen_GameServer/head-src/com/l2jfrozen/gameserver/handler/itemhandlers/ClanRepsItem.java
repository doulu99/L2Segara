package com.l2jfrozen.gameserver.handler.itemhandlers;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.handler.IItemHandler;
import com.l2jfrozen.gameserver.model.actor.instance.L2ItemInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PlayableInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.network.serverpackets.MagicSkillUser;

public class ClanRepsItem implements IItemHandler
{
    private static final int ITEM_IDS[] =
    {
       Config.CR_ITEM_REPS_ITEM_ID
    };

    @Override
	public void useItem(L2PlayableInstance playable, L2ItemInstance item)
    {
            if (!(playable instanceof L2PcInstance))
            {
                return;
            }

            L2PcInstance activeChar = (L2PcInstance)playable;

            if (!activeChar.isClanLeader())
            {
                activeChar.sendMessage("This can be used only by Clan Leaders!");
                return;
            }
           
            else if (!(activeChar.getClan().getLevel() >= Config.CR_ITEM_MIN_CLAN_LVL))
            {
               activeChar.sendMessage("Your Clan Level is not big enough to use this item!");
               return;
            }
            else
            {
               activeChar.getClan().setReputationScore(activeChar.getClan().getReputationScore()+Config.CR_ITEM_REPS_TO_BE_AWARDED, true);
               activeChar.sendMessage("Your clan has earned "+ Config.CR_ITEM_REPS_TO_BE_AWARDED +" rep points!");
               MagicSkillUser  MSU = new MagicSkillUser(activeChar, activeChar, 2024, 1, 1, 0);
               activeChar.broadcastPacket(MSU);
               playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
            }
        }

    @Override
	public int[] getItemIds()
    {
        return ITEM_IDS;
   }
}
