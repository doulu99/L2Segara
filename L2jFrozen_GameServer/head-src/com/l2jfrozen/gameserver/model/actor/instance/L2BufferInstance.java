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

import com.l2jfrozen.gameserver.ai.CtrlIntention;
import com.l2jfrozen.gameserver.datatables.SkillTable;
import com.l2jfrozen.gameserver.model.L2Character;
import com.l2jfrozen.gameserver.model.entity.event.CTF;
import com.l2jfrozen.gameserver.model.entity.event.DM;
import com.l2jfrozen.gameserver.model.entity.event.TvT;
import com.l2jfrozen.gameserver.model.entity.olympiad.Olympiad;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.MagicSkillUser;
import com.l2jfrozen.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.network.serverpackets.SocialAction;
import com.l2jfrozen.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfrozen.gameserver.powerpak.PowerPakConfig;
import com.l2jfrozen.gameserver.taskmanager.AttackStanceTaskManager;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;
import com.l2jfrozen.util.random.Rnd;

import java.util.StringTokenizer;

public final class L2BufferInstance extends L2NpcInstance
{

private static final String PARENT_DIR = "data/html/bufferInstance/";
  
  public L2BufferInstance(int objectId, L2NpcTemplate template)
  {
    super(objectId, template);
  }
  
  @Override
public void showChatWindow(L2PcInstance player, int val)
  {
    String PARENT_DIR = "data/html/bufferInstance/main.htm";
    NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
    html.setFile(PARENT_DIR);
    html.replace("%charname%", player.getName());
    html.replace("%objectId%", String.valueOf(getObjectId()));
    html.replace("%npcname%", getName());
    player.sendPacket(html);
  }
  
	private boolean checkAllowed(final L2PcInstance activeChar)
	{
		String msg = null;
		if (activeChar.isSitting())
			msg = "Can't use buffer when sitting";
		else if (activeChar.isCastingNow() || activeChar.isCastingPotionNow())
			msg = "Can't use buffer when casting";
		else if (activeChar.isAlikeDead())
			msg = "Can't use buffer while dead";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("ALL"))
			msg = "Buffer is not available in this area";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("CURSED") && activeChar.isCursedWeaponEquiped())
			msg = "Can't use Buffer with Cursed Weapon";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("ATTACK") && AttackStanceTaskManager.getInstance().getAttackStanceTask(activeChar))
			msg = "Buffer is not available during the battle";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("DUNGEON") && activeChar.isIn7sDungeon())
			msg = "Buffer is not available in the catacombs and necropolis";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("RB") && activeChar.isInsideZone(L2Character.ZONE_NOSUMMONFRIEND))
			msg = "Buffer is not available in this area";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("PVP") && activeChar.isInsideZone(L2Character.ZONE_PVP))
			msg = "Buffer is not available in this area";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("PEACE") && activeChar.isInsideZone(L2Character.ZONE_PEACE))
			msg = "Buffer is not available in this area";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("SIEGE") && activeChar.isInsideZone(L2Character.ZONE_SIEGE))
			msg = "Buffer is not available in this area";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("OLYMPIAD") && (activeChar.isInOlympiadMode() || activeChar.isInsideZone(L2Character.ZONE_OLY) || Olympiad.getInstance().isRegistered(activeChar) || Olympiad.getInstance().isRegisteredInComp(activeChar)))
			msg = "Buffer is not available in Olympiad";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("EVENT") && (activeChar.isInFunEvent()))
			msg = "Buffer is not available in this event";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("TVT") && activeChar._inEventTvT && TvT.is_started())
			msg = "Buffer is not available in TVT";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("CTF") && activeChar._inEventCTF && CTF.is_started())
			msg = "Buffer is not available in CTF";
		else if (PowerPakConfig.BUFFER_EXCLUDE_ON.contains("DM") && activeChar._inEventDM && DM.is_started())
			msg = "Buffer is not available in DM";
		else if (activeChar.isInCombat())
			msg ="You can't use buffer in Combat mode";
		
		if (msg != null)
			activeChar.sendMessage(msg);
		
		return msg == null;
	}
	
  @Override
public void onBypassFeedback(L2PcInstance player, String command)
  {
		if (!checkAllowed(player))
			return;
		
		
    StringTokenizer st = new StringTokenizer(command, " ");
    String actualCommand = st.nextToken();
    
    int buffid = 0;
    int bufflevel = 1;
    if (st.countTokens() == 2)
    {
      buffid = Integer.valueOf(st.nextToken()).intValue();
      bufflevel = Integer.valueOf(st.nextToken()).intValue();
    }
    else if (st.countTokens() == 1)
    {
      buffid = Integer.valueOf(st.nextToken()).intValue();
    }
    else if (st.countTokens() == 3)
    {
    	
    }
    
    if (actualCommand.equalsIgnoreCase("getBuff"))
    {
      if (buffid != 0)
      {
        MagicSkillUser mgc = new MagicSkillUser(this, player, buffid, bufflevel, 5, 0);
        SkillTable.getInstance().getInfo(buffid, bufflevel).getEffects(this, player);
        showChatWindow(player, "data/html/bufferInstance/Buffs.htm");
        player.broadcastPacket(mgc);
      }
    }
    
    if (actualCommand.equalsIgnoreCase("getDance"))
    {
      if (buffid != 0)
      {
        MagicSkillUser mgc = new MagicSkillUser(this, player, buffid, bufflevel, 5, 0);
        SkillTable.getInstance().getInfo(buffid, bufflevel).getEffects(this, player);
        showChatWindow(player, "data/html/bufferInstance/Dance.htm");
        player.broadcastPacket(mgc);
      }
    }
    
    if (actualCommand.equalsIgnoreCase("getSongs"))
    {
      if (buffid != 0)
      {
        MagicSkillUser mgc = new MagicSkillUser(this, player, buffid, bufflevel, 5, 0);
        SkillTable.getInstance().getInfo(buffid, bufflevel).getEffects(this, player);
        showChatWindow(player, "data/html/bufferInstance/Songs.htm");
        player.broadcastPacket(mgc);
      }
    }
    if (actualCommand.equalsIgnoreCase("getSpecial"))
    {
      if (buffid != 0)
      {
        MagicSkillUser mgc = new MagicSkillUser(this, player, buffid, bufflevel, 5, 0);
        SkillTable.getInstance().getInfo(buffid, bufflevel).getEffects(this, player);
        showChatWindow(player, "data/html/bufferInstance/Special.htm");
        player.broadcastPacket(mgc);
      }
    }
    if (actualCommand.equalsIgnoreCase("getOther"))
    {
      if (buffid != 0)
      {
        MagicSkillUser mgc = new MagicSkillUser(this, player, buffid, bufflevel, 5, 0);
        SkillTable.getInstance().getInfo(buffid, bufflevel).getEffects(this, player);
        showChatWindow(player, "data/html/bufferInstance/Other.htm");
        player.broadcastPacket(mgc);
      }
    }
    else if (actualCommand.equalsIgnoreCase("HP"))
    {
      player.setCurrentHp(player.getMaxHp());
      MagicSkillUser mgc = new MagicSkillUser(this, player, 1258, 1, 5, 0);
      showChatWindow(player);
      player.broadcastPacket(mgc);
    }
    else if (actualCommand.equalsIgnoreCase("MP"))
    {
      player.setCurrentMp(player.getMaxMp());
      MagicSkillUser mgc = new MagicSkillUser(this, player, 1013, 1, 5, 0);
      showChatWindow(player);
      player.broadcastPacket(mgc);
    }
    else if (actualCommand.equalsIgnoreCase("CP"))
    {
      player.setCurrentCp(player.getMaxCp());
      MagicSkillUser mgc = new MagicSkillUser(this, player, 1306, 1, 5, 0);
      showChatWindow(player);
      player.broadcastPacket(mgc);
    }
    else if (actualCommand.equalsIgnoreCase("cancel"))
    {
      MagicSkillUser mgc = new MagicSkillUser(this, player, 1056, 1, 5, 0);
      player.stopAllEffects();
      showChatWindow(player, 0);
      player.broadcastPacket(mgc);
    }
    else if (actualCommand.equalsIgnoreCase("Nobless"))
    {
      MagicSkillUser mgc = new MagicSkillUser(this, player, 1323, 1, 5, 0);
      SkillTable.getInstance().getInfo(1323, 1).getEffects(this, player);
      showChatWindow(player);
      player.broadcastPacket(mgc);
    }
    
	if (actualCommand.equalsIgnoreCase("FighterList"))
	{
		for (final int skillId : PowerPakConfig.FIGHTER_SKILL_LIST.keySet())
		{
	      MagicSkillUser mgc = new MagicSkillUser(this, player, skillId, PowerPakConfig.FIGHTER_SKILL_LIST.get(skillId), 5, 0);
	      SkillTable.getInstance().getInfo(skillId, PowerPakConfig.FIGHTER_SKILL_LIST.get(skillId)).getEffects(this, player);
	      showChatWindow(player);
	      player.broadcastPacket(mgc);
		}
	}
	
	if (actualCommand.equalsIgnoreCase("MageList"))
	{
		for (final int skillId : PowerPakConfig.MAGE_SKILL_LIST.keySet())
		{
	      MagicSkillUser mgc = new MagicSkillUser(this, player, skillId, PowerPakConfig.MAGE_SKILL_LIST.get(skillId), 5, 0);
	      SkillTable.getInstance().getInfo(skillId, PowerPakConfig.MAGE_SKILL_LIST.get(skillId)).getEffects(this, player);
	      showChatWindow(player);
	      player.broadcastPacket(mgc);
		}
	}

    else
    {
      super.onBypassFeedback(player, command);
    }
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

/**
 * @return the parentDir
 */
public static String getParentDir()
{
	return PARENT_DIR;
}
}
