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

import java.util.logging.Logger;

import javolution.text.TextBuilder;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.ai.CtrlIntention;
import com.l2jfrozen.gameserver.datatables.sql.NpcTable;
import com.l2jfrozen.gameserver.managers.GrandBossManager;
import com.l2jfrozen.gameserver.managers.RaidBossSpawnManager;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfrozen.gameserver.powerpak.RaidInfo.RaidInfoHandler;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;
import com.l2jfrozen.gameserver.templates.StatsSet;

public class L2RBManagerInstance extends L2FolkInstance
{
  private static Logger _log = Logger.getLogger(RaidInfoHandler.class.getName());
  public L2RBManagerInstance(int objectId, L2NpcTemplate template)
  {
    super(objectId, template);
  }

  @Override
public void onAction(L2PcInstance activeChar)
  {
    if (!canTarget(activeChar))
    {
      return;
    }

    if (this != activeChar.getTarget())
    {
    	activeChar.setTarget(this);

    	activeChar.sendPacket(new MyTargetSelected(getObjectId(), 0));

    	activeChar.sendPacket(new ValidateLocation(this));
    }
    else if (!canInteract(activeChar))
    {
    	activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
    }
    else
    {
      showHtmlWindow(activeChar);
    }

    activeChar.sendPacket(new ActionFailed());
  }
  
	private void showHtmlWindow(L2PcInstance activeChar)
	{
		TextBuilder tb = new TextBuilder();
		tb.append("<html>");
		tb.append("<body>");
		tb.append("<title>RaidBoss Manager</title>");
		tb.append("<br1>");
		tb.append("<img src=\"Community.banner\" width=300 height=80>");
		tb.append("<img src=\"L2UI.SquareGray\" width=300 height=1>");
		tb.append("<table bgcolor=\"000000\" width=310>");


		
		for(int boss : Config.RAID_INFO_IDS_LIST)
		{
			String name = "";
			L2NpcTemplate template = null;
			if((template = NpcTable.getInstance().getTemplate(boss)) != null){
				name = template.getName();
			}else{
				_log.warning("[RaidInfoHandler][sendInfo] Raid Boss with ID "+boss+" is not defined into NpcTable");
				continue;
			}
			 
			StatsSet actual_boss_stat = null;
			GrandBossManager.getInstance().getStatsSet(boss);
			long delay = 0;
			
			if(NpcTable.getInstance().getTemplate(boss).type.equals("L2RaidBoss"))
			{
				actual_boss_stat=RaidBossSpawnManager.getInstance().getStatsSet(boss);
				if(actual_boss_stat!=null)
					delay = actual_boss_stat.getLong("respawnTime");
			}
			else if(NpcTable.getInstance().getTemplate(boss).type.equals("L2GrandBoss"))
			{
				actual_boss_stat=GrandBossManager.getInstance().getStatsSet(boss);
				if(actual_boss_stat!=null)
					delay = actual_boss_stat.getLong("respawn_time");
			}else
				continue;
			
			if (delay <= System.currentTimeMillis())
			{
				tb.append("<tr>");
				tb.append("<td><font color=\"005C7E\">"+ name +"</font></td>");
				tb.append("<td><center><font color=\"00FF00\">Is Alive</font></td>");
				tb.append("</tr>");
			}
			else
			{
				int hours = (int) ((delay - System.currentTimeMillis()) / 1000 / 60 / 60);
				int mins = (int) (((delay - (hours * 60 * 60 * 1000)) - System.currentTimeMillis()) / 1000 / 60);
				int seconts = (int) (((delay - ((hours * 60 * 60 * 1000) + (mins * 60 * 1000))) - System.currentTimeMillis()) / 1000);
				tb.append("<tr>");
				tb.append("<td><font color=\"005C7E\">"+ name +"</font></td>");
				tb.append("<td><center>Respawn in: " + hours + " : " + mins + " : " + seconts + "</td>");
				tb.append("</tr>");
			}
		}
		tb.append("</table>");
		tb.append("</body>");
		tb.append("</html>");

		NpcHtmlMessage msg = new NpcHtmlMessage(getObjectId());
		msg.setHtml(tb.toString());
		activeChar.sendPacket(msg);
		
	}
	
}