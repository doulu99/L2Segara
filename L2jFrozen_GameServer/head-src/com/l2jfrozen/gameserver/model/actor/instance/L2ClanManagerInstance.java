/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfrozen.gameserver.model.actor.instance;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;

/**
 * Change code and fixed by: Developer Luna
 */

public class L2ClanManagerInstance extends L2NpcInstance
{
       public L2ClanManagerInstance(int objectId, L2NpcTemplate template)
       {
           super(objectId, template);
       }
       
    @Override
    public void showChatWindow(L2PcInstance player, int val)
    {
        player.sendPacket(ActionFailed.STATIC_PACKET);
        String filename = "data/html/mods/ClanManager/clanManager.htm";
        NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
        html.setFile(filename);
        html.replace("%objectId%", String.valueOf(getObjectId()));
        html.replace("%playername%", player.getName());
        player.sendPacket(html);
    }
    
       @Override
       public void onBypassFeedback(L2PcInstance player, String command)
       {
               if (player.getClanId() == 0)
               {
                       player.sendMessage("You don't have a clan.");
                       return;
               }
              
               if (!player.isClanLeader())
               {
                       player.sendMessage("You aren't the leader of your clan.");
                       return;
               }
              
               if (player.getInventory().getItemByItemId(Config.CLAN_ITEM) == null)
               {
                       player.sendMessage("You don't have enough items.");
                       return;
               }
              
               if (player.getInventory().getItemByItemId(Config.CLAN_ITEM).getCount() < Config.CLAN_COST)
               {
                       player.sendMessage("You don't have enough items.");
                       return;
               }
              
               if (player.getClan().getLevel() == 8)
               {
                       player.sendMessage("Your clan's level is 8 and you can't exceed it.");
                       return;
               }
               
               else
            	   
               if (command.equals("clanLevelUp"))
               {        
            	   if (player.isClanLeader() && player.getClan().getLevel() < 8)
            	   {
                       player.getClan().changeLevel(Config.CLAN_LEVEL);
                       player.getInventory().destroyItemByItemId("ClanCoin", Config.CLAN_ITEM, Config.CLAN_COST, player, player);
                       player.sendMessage("Your clan's level has been changed to "+player.getClan().getLevel());
                       player.getClan().broadcastClanStatus();
            	   }

               }
        }
}