package com.l2jfrozen.gameserver.communitybbs.Manager;

import java.util.StringTokenizer;

import com.l2jfrozen.gameserver.cache.HtmCache;
import com.l2jfrozen.gameserver.datatables.sql.TeleportLocationTable;
import com.l2jfrozen.gameserver.model.L2Character;
import com.l2jfrozen.gameserver.model.L2TeleportLocation;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.model.entity.event.CTF;
import com.l2jfrozen.gameserver.model.entity.event.DM;
import com.l2jfrozen.gameserver.model.entity.event.TvT;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.powerpak.PowerPakConfig;
import com.l2jfrozen.gameserver.taskmanager.AttackStanceTaskManager;

/**
 * @author Matim
 * @version 1.0
 */
public class TeleBBSManager extends BaseBBSManager
{
       @Override
       public void parsecmd(String command, L2PcInstance activeChar)
       {
               if (command.startsWith("_bbstele;"))
               {
                       if (mayUse(activeChar))
                       {
                               StringTokenizer st = new StringTokenizer(command, ";");
                               st.nextToken();
                               int id = Integer.parseInt(st.nextToken());
                              
                               doTeleport(activeChar, id);
                              
                               String filename = "data/html/CommunityBoard/gatekeeper/1.htm";
                               String content = HtmCache.getInstance().getHtm(filename);                              
                               separateAndSend(content, activeChar);
                       }
               }
                  
       		else if(command.startsWith("_bbsteleHtm;"))
    		{
    			StringTokenizer st = new StringTokenizer(command, ";");
    			st.nextToken();

    			int idp = Integer.parseInt(st.nextToken());

    			String content = HtmCache.getInstance().getHtm("data/html/CommunityBoard/gatekeeper/" + idp + ".htm");

    			if(content == null)
    			{
    				content = "<html><body><br><br><center>404 :File Not foud: 'data/html/CommunityBoard/" + idp + ".htm' </center></body></html>";
    			}
    			separateAndSend(content, activeChar);
    			st = null;
    			content = null;
    		}	
       }

       @Override
       public void parsewrite(String ar1, String ar2, String ar3, String ar4, String ar5, L2PcInstance activeChar)
       {
              
       }
             
   	private boolean mayUse(L2PcInstance player)
   	{
   		String msg = null;
   		if(player.isSitting())
   			msg = "Can't use Gatekeeper when sitting";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("ALL"))
   			msg = "Gatekeeper is not available in this area";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("CURSED") && player.isCursedWeaponEquiped())
   			msg = "Can't use Gatekeeper with Cursed Weapon"; 
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("ATTACK") && AttackStanceTaskManager.getInstance().getAttackStanceTask(player))
   			msg = "Gatekeeper is not available during the battle";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("DUNGEON") && player.isIn7sDungeon())
   			msg = "Gatekeeper is not available in the catacombs and necropolis";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("RB") && player.isInsideZone(L2Character.ZONE_NOSUMMONFRIEND))
   			msg = "Gatekeeper is not available in this area";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("PVP") && player.isInsideZone(L2Character.ZONE_PVP))
   			msg = "Gatekeeper is not available in this area";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("PEACE") && player.isInsideZone(L2Character.ZONE_PEACE))
   			msg = "Gatekeeper is not available in this area";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("SIEGE") && player.isInsideZone(L2Character.ZONE_SIEGE))
   			msg = "Gatekeeper is not available in this area";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("OLYMPIAD") && player.isInOlympiadMode())
   			msg = "Gatekeeper is not available in Olympiad";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("TVT") && player._inEventTvT && TvT.is_started())
   			msg = "Gatekeeper is not available in TVT";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("CTF") && player._inEventCTF && CTF.is_started())
   			msg = "Gatekeeper is not available in CTF";
   		else if(PowerPakConfig.GLOBALGK_EXCLUDE_ON.contains("DM") && player._inEventDM && DM.is_started())
   			msg = "Gatekeeper is not available in DM";
   		else if(player.isMoving())
   			msg = "Can't teleport in moving mode";

   		if(msg!=null)
   			player.sendMessage(msg);

   		return msg==null;
   	}
  
       private void doTeleport(L2PcInstance player, int val)
       {
               L2TeleportLocation list = TeleportLocationTable.getInstance().getTemplate(val);
               if (list != null)
               {
           		player.teleToLocation(list.getLocX(), list.getLocY(), list.getLocZ(), true);
               }
               else
                       System.out.println("No teleport destination with id:" + val);
              
               player.sendPacket(ActionFailed.STATIC_PACKET);
       }
      
       public static TeleBBSManager getInstance()
       {
               return SingletonHolder.INSTANCE;
       }
      
       private static class SingletonHolder
       {
               protected static final TeleBBSManager INSTANCE = new TeleBBSManager();
       }
}