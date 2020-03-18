/*
 * L2jFrozen Project - www.l2jfrozen.com 
 * 
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
package com.l2jfrozen.gameserver.network.clientpackets;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;

import com.l2jfrozen.Config;
import com.l2jfrozen.crypt.nProtect;
import com.l2jfrozen.crypt.nProtect.RestrictionType;
import com.l2jfrozen.gameserver.Restart;
import com.l2jfrozen.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jfrozen.gameserver.controllers.GameTimeController;
import com.l2jfrozen.gameserver.datatables.CharSchemesTable;
import com.l2jfrozen.gameserver.datatables.GmListTable;
import com.l2jfrozen.gameserver.datatables.SkillTable;
import com.l2jfrozen.gameserver.datatables.csv.MapRegionTable;
import com.l2jfrozen.gameserver.datatables.sql.AdminCommandAccessRights;
import com.l2jfrozen.gameserver.datatables.xml.ExperienceData;
import com.l2jfrozen.gameserver.handler.custom.CustomWorldHandler;
import com.l2jfrozen.gameserver.managers.CastleManager;
import com.l2jfrozen.gameserver.managers.ClanHallManager;
import com.l2jfrozen.gameserver.managers.CoupleManager;
import com.l2jfrozen.gameserver.managers.CrownManager;
import com.l2jfrozen.gameserver.managers.DimensionalRiftManager;
import com.l2jfrozen.gameserver.managers.FortSiegeManager;
import com.l2jfrozen.gameserver.managers.PetitionManager;
import com.l2jfrozen.gameserver.managers.SiegeManager;
import com.l2jfrozen.gameserver.model.Inventory;
import com.l2jfrozen.gameserver.model.L2Character;
import com.l2jfrozen.gameserver.model.L2Clan;
import com.l2jfrozen.gameserver.model.L2Effect;
import com.l2jfrozen.gameserver.model.L2Object;
import com.l2jfrozen.gameserver.model.L2Skill;
import com.l2jfrozen.gameserver.model.L2World;
import com.l2jfrozen.gameserver.model.actor.instance.L2ClassMasterInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2ItemInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.model.base.ClassLevel;
import com.l2jfrozen.gameserver.model.base.PlayerClass;
import com.l2jfrozen.gameserver.model.entity.Announcements;
import com.l2jfrozen.gameserver.model.entity.ClanHall;
import com.l2jfrozen.gameserver.model.entity.Hero;
import com.l2jfrozen.gameserver.model.entity.Wedding;
import com.l2jfrozen.gameserver.model.entity.event.CTF;
import com.l2jfrozen.gameserver.model.entity.event.DM;
import com.l2jfrozen.gameserver.model.entity.event.L2Event;
import com.l2jfrozen.gameserver.model.entity.event.TvT;
import com.l2jfrozen.gameserver.model.entity.olympiad.Olympiad;
import com.l2jfrozen.gameserver.model.entity.sevensigns.SevenSigns;
import com.l2jfrozen.gameserver.model.entity.siege.Castle;
import com.l2jfrozen.gameserver.model.entity.siege.FortSiege;
import com.l2jfrozen.gameserver.model.entity.siege.Siege;
import com.l2jfrozen.gameserver.model.quest.Quest;
import com.l2jfrozen.gameserver.model.quest.QuestState;
import com.l2jfrozen.gameserver.network.Disconnection;
import com.l2jfrozen.gameserver.network.SystemMessageId;
import com.l2jfrozen.gameserver.network.serverpackets.ClientSetTime;
import com.l2jfrozen.gameserver.network.serverpackets.CreatureSay;
import com.l2jfrozen.gameserver.network.serverpackets.Die;
import com.l2jfrozen.gameserver.network.serverpackets.Earthquake;
import com.l2jfrozen.gameserver.network.serverpackets.EtcStatusUpdate;
import com.l2jfrozen.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jfrozen.gameserver.network.serverpackets.ExStorageMaxCount;
import com.l2jfrozen.gameserver.network.serverpackets.FriendList;
import com.l2jfrozen.gameserver.network.serverpackets.HennaInfo;
import com.l2jfrozen.gameserver.network.serverpackets.ItemList;
import com.l2jfrozen.gameserver.network.serverpackets.MagicSkillUser;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.network.serverpackets.PledgeShowMemberListAll;
import com.l2jfrozen.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import com.l2jfrozen.gameserver.network.serverpackets.PledgeSkillList;
import com.l2jfrozen.gameserver.network.serverpackets.PledgeStatusChanged;
import com.l2jfrozen.gameserver.network.serverpackets.QuestList;
import com.l2jfrozen.gameserver.network.serverpackets.ShortCutInit;
import com.l2jfrozen.gameserver.network.serverpackets.SignsSky;
import com.l2jfrozen.gameserver.network.serverpackets.SocialAction;
import com.l2jfrozen.gameserver.network.serverpackets.SpecialCamera;
import com.l2jfrozen.gameserver.network.serverpackets.SystemMessage;
import com.l2jfrozen.gameserver.network.serverpackets.UserInfo;
import com.l2jfrozen.gameserver.powerpak.PowerPakConfig;
import com.l2jfrozen.gameserver.thread.TaskPriority;
import com.l2jfrozen.gameserver.thread.ThreadPoolManager;
import com.l2jfrozen.gameserver.util.Util;

/**
 * Enter World Packet Handler
 */
public class EnterWorld extends L2GameClientPacket
{
	private static Logger LOGGER = Logger.getLogger(EnterWorld.class);
	private final SimpleDateFormat fmt = new SimpleDateFormat("H:mm.");
	private long _daysleft;

	
	SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
	
	public TaskPriority getPriority()
	{
		return TaskPriority.PR_URGENT;
	}
	
	@Override
	protected void readImpl()
	{
		// this is just a trigger packet. it has no content
	}
		
	@Override
	protected void runImpl()
	{
		final L2PcInstance activeChar = getClient().getActiveChar();
		
		if (activeChar == null)
		{
			LOGGER.warn("EnterWorld failed! activeChar is null...");
			getClient().closeNow();
			return;
		}
		       
		// Set lock at login
		activeChar.setLocked(true);
		
		// Register in flood protector
		// FloodProtector.getInstance().registerNewPlayer(activeChar.getObjectId());
		
		if (L2World.getInstance().findObject(activeChar.getObjectId()) != null)
		{
			if (Config.DEBUG)
			{
				LOGGER.warn("DEBUG " + getType() + ": User already exist in OID map! User " + activeChar.getName() + " is character clone");
				// activeChar.closeNetConnection(); // Do nothing?
			}
		}
		
		if (!activeChar.isGM() && !activeChar.isDonator() && Config.CHECK_NAME_ON_LOGIN)
		{
			if (activeChar.getName().length() < 3 || activeChar.getName().length() > 16 || !Util.isAlphaNumeric(activeChar.getName()) || !isValidName(activeChar.getName()))
			{
				LOGGER.warn("Charname: " + activeChar.getName() + " is invalid. EnterWorld failed.");
				getClient().closeNow();
				return;
			}
		}
		
		// Set online status
		activeChar.setOnlineStatus(true);
		
		activeChar.setRunning(); // running is default
		activeChar.standUp(); // standing is default
		
		activeChar.broadcastKarma(); // include UserInfo
		
		// Engage and notify Partner
		if (Config.L2JMOD_ALLOW_WEDDING)
		{
			engage(activeChar);
			notifyPartner(activeChar, activeChar.getPartnerId());
		}
		
		EnterGM(activeChar);
		
		Quest.playerEnter(activeChar);
		activeChar.sendPacket(new QuestList());
		
		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			activeChar.setProtection(true);
		
		activeChar.spawnMe(activeChar.getX(), activeChar.getY(), activeChar.getZ());
		
		if (activeChar.isClanLeader() && activeChar.getClan().getLevel() == 8 && Config.ANNOUNCE_CLAN_LEAVER)
		{
			if(activeChar.getClan() != null)
			{
			Announcements.getInstance().gameAnnounceToAll("Leader " + activeChar.getClan().getLeaderName() + " from Clan " + activeChar.getClan().getName() + " Has Login!");
			}
		}
		
		if (Config.TXT_ENABLE)
		{
		activeChar.sendPacket(new CreatureSay(0, Say2.PARTY, Config.TXT_NAME, Config.TXT_ON_ENTER));
		
		if(Config.TXT_ENABLE_CHAT1)
		activeChar.sendPacket(new CreatureSay(0, Say2.PARTY, Config.TXT_NAME, Config.TXT_ON_ENTER1));
		
		if(Config.TXT_ENABLE_CHAT2)
		activeChar.sendPacket(new CreatureSay(0, Say2.PARTY, Config.TXT_NAME, Config.TXT_ON_ENTER2));
		
		if(Config.TXT_ENABLE_CHAT3)
		activeChar.sendPacket(new CreatureSay(0, Say2.PARTY, Config.TXT_NAME, Config.TXT_ON_ENTER3));
		
		if(Config.TXT_ENABLE_CHAT4)
		activeChar.sendPacket(new CreatureSay(0, Say2.PARTY, Config.TXT_NAME, Config.TXT_ON_ENTER4));
		}
		
		/* ****************************** Camera MOD ****************************** */
        // 1 actived, 0 deactived
        int activemod = 1;
       
        if (Config.CAMERA_ENABLED && activeChar.getOnlineTime() == 0 && activeChar.isNewbie() && activemod == 1)
        {
                // 1th value: ID
                // 2nd value: Distance between char and camera
                // 3th value: Left-Right angle of camera starting position
                // 4th value: Up-Down angle of camera starting position
                // 5th value: time in which "turn" and "rise" will take effect
                // 6th value: total time of the camera effect
               
                activeChar.sendPacket(new ExShowScreenMessage("Welcome to Lineage II Legal", 6000));
               
                try
                {
                        Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                        e.printStackTrace();
                }
               
                int NPC1 = 0;
                int NPC2 = 0;
                int NPC3 = 0;
                int NPC4 = 0;
                int NPC5 = 0;
                int NPC6 = 0;
                int NPC7 = 0;
                int NPC8 = 0;
                int NPC9 = 0;
                int NPC10 = 0;
                int NPC11 = 0;
                int NPC12 = 0;
                int NPC13 = 0;
                int NPC14 = 0;
                int NPC15 = 0;
                int NPC16 = 0;
                int NPC17 = 0;
                int NPC18 = 0;
               
                // Configurable
                // Here you can change npc name and the message
                String NPCname1 = Config.CAMENA_NPC_NAME;
                String MessageNPC1 = Config.MessageNPC;
               
                String NPCname2 = Config.CAMENA_NPC_NAME2;
                String MessageNPC2 = Config.MessageNPC2;
                
                String NPCname3 = Config.CAMENA_NPC_NAME3;
                String MessageNPC3 = Config.MessageNPC3;
                
                String NPCname4 = Config.CAMENA_NPC_NAME4;
                String MessageNPC4 = Config.MessageNPC4;
                
                String NPCname5 = Config.CAMENA_NPC_NAME5;
                String MessageNPC5 = Config.MessageNPC5;
                
                String NPCname6 = Config.CAMENA_NPC_NAME6;
                String MessageNPC6 = Config.MessageNPC6;
                
                String NPCname7 = Config.CAMENA_NPC_NAME7;
                String MessageNPC7 = Config.MessageNPC7;
                
                String NPCname8 = Config.CAMENA_NPC_NAME8;
                String MessageNPC8 = Config.MessageNPC8;
                
                String NPCname9 = Config.CAMENA_NPC_NAME9;
                String MessageNPC9 = Config.MessageNPC9;
                
                String NPCname10 = Config.CAMENA_NPC_NAME10;
                String MessageNPC10 = Config.MessageNPC10;
                
                String NPCname11 = Config.CAMENA_NPC_NAME11;
                String MessageNPC11 = Config.MessageNPC11;
                
                String NPCname12 = Config.CAMENA_NPC_NAME12;
                String MessageNPC12 = Config.MessageNPC12;
                
                String NPCname13 = Config.CAMENA_NPC_NAME13;
                String MessageNPC13 = Config.MessageNPC13;
                
                String NPCname14 = Config.CAMENA_NPC_NAME14;
                String MessageNPC14 = Config.MessageNPC14;
                
                String NPCname15 = Config.CAMENA_NPC_NAME15;
                String MessageNPC15 = Config.MessageNPC15;
                
                String NPCname16 = Config.CAMENA_NPC_NAME16;
                String MessageNPC16 = Config.MessageNPC16;
                
                String NPCname17 = Config.CAMENA_NPC_NAME17;
                String MessageNPC17 = Config.MessageNPC17;
                
                String NPCname18 = Config.CAMENA_NPC_NAME18;
                String MessageNPC18 = Config.MessageNPC18;
            
                // End Config
                
                for (final L2Object npc : activeChar.getKnownList().getKnownObjects().values())
                {
                        if (!(npc instanceof L2NpcInstance))
                                continue;
   
                    if (npc.getName().equals(NPCname1) && NPC1 != 1)
                    {
                            activeChar.setTarget(npc);
                            final int gottarget = activeChar.getTarget().getObjectId();
                                                       
                            activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance, Config.Yaw, Config.Pitch, Config.Time, Config.Duration));
                            activeChar.sendPacket(new ExShowScreenMessage(MessageNPC1, 3000));
                            
                            try
                        	{
                        		Thread.sleep(5000);
                        	}
                        	catch (InterruptedException e)
                        	{
                        		e.printStackTrace();
                        	}
                            NPC1 = 1;
                            continue;
                   }              
// Camera No.2               
                 if(Config.CAMERA_ENABLED2)
                 {    
                        if (npc.getName().equals(NPCname2) && NPC2 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance2, Config.Yaw2, Config.Pitch2, Config.Time2, Config.Duration2));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC2, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC2 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.3
                 
                 if(Config.CAMERA_ENABLED3)
                 {    
                        if (npc.getName().equals(NPCname3) && NPC3 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance3, Config.Yaw3, Config.Pitch3, Config.Time3, Config.Duration3));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC3, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC3 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.4
                 
                 if(Config.CAMERA_ENABLED4)
                 {    
                        if (npc.getName().equals(NPCname4) && NPC4 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance4, Config.Yaw4, Config.Pitch4, Config.Time4, Config.Duration4));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC4, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC4 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.5
                 
                 if(Config.CAMERA_ENABLED5)
                 {    
                        if (npc.getName().equals(NPCname5) && NPC5 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance5, Config.Yaw5, Config.Pitch5, Config.Time5, Config.Duration5));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC5, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC5 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.6
                 
                 if(Config.CAMERA_ENABLED6)
                 {    
                        if (npc.getName().equals(NPCname6) && NPC6 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance6, Config.Yaw6, Config.Pitch6, Config.Time6, Config.Duration6));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC6, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC6 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.7
                 
                 if(Config.CAMERA_ENABLED7)
                 {    
                        if (npc.getName().equals(NPCname7) && NPC7 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance7, Config.Yaw7, Config.Pitch7, Config.Time7, Config.Duration7));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC7, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC7 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.8
                 
                 if(Config.CAMERA_ENABLED8)
                 {    
                        if (npc.getName().equals(NPCname8) && NPC8 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance8, Config.Yaw8, Config.Pitch8, Config.Time8, Config.Duration8));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC8, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC8 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.9
                 
                 if(Config.CAMERA_ENABLED9)
                 {    
                        if (npc.getName().equals(NPCname9) && NPC9 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance9, Config.Yaw9, Config.Pitch9, Config.Time9, Config.Duration9));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC9, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC9 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.10
                 
                 if(Config.CAMERA_ENABLED10)
                 {    
                        if (npc.getName().equals(NPCname10) && NPC10 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance10, Config.Yaw10, Config.Pitch10, Config.Time10, Config.Duration10));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC10, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC10 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.11
                 
                 if(Config.CAMERA_ENABLED11)
                 {    
                        if (npc.getName().equals(NPCname11) && NPC11 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance11, Config.Yaw11, Config.Pitch11, Config.Time11, Config.Duration11));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC11, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC11 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.12
                 
                 if(Config.CAMERA_ENABLED12)
                 {    
                        if (npc.getName().equals(NPCname12) && NPC12 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance12, Config.Yaw12, Config.Pitch12, Config.Time12, Config.Duration12));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC12, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC12 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.13
                 
                 if(Config.CAMERA_ENABLED13)
                 {    
                        if (npc.getName().equals(NPCname13) && NPC13 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance13, Config.Yaw13, Config.Pitch13, Config.Time13, Config.Duration13));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC13, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC13 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.14
                 
                 if(Config.CAMERA_ENABLED14)
                 {    
                        if (npc.getName().equals(NPCname14) && NPC14 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance14, Config.Yaw14, Config.Pitch14, Config.Time14, Config.Duration14));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC14, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC14 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.15
                 
                 if(Config.CAMERA_ENABLED15)
                 {    
                        if (npc.getName().equals(NPCname15) && NPC15 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance15, Config.Yaw15, Config.Pitch15, Config.Time15, Config.Duration15));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC15, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC15 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.16
                 
                 if(Config.CAMERA_ENABLED16)
                 {    
                        if (npc.getName().equals(NPCname16) && NPC16 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance16, Config.Yaw16, Config.Pitch16, Config.Time16, Config.Duration16));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC16, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC16 = 1;
                                continue;
                              
                        }
                 	}
// Camera No.17
                 
                 if(Config.CAMERA_ENABLED17)
                 {    
                        if (npc.getName().equals(NPCname17) && NPC17 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance17, Config.Yaw17, Config.Pitch17, Config.Time17, Config.Duration17));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC17, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC17 = 1;
                                continue;
                              
                        }
                 	}
                 
// Camera No.18
                 
                 if(Config.CAMERA_ENABLED18)
                 {    
                        if (npc.getName().equals(NPCname18) && NPC18 != 1)
                        {
                                activeChar.setTarget(npc);
                                final int gottarget = activeChar.getTarget().getObjectId();
                               
                                activeChar.sendPacket(new SpecialCamera(gottarget, Config.Distance18, Config.Yaw18, Config.Pitch18, Config.Time18, Config.Duration18));
                                activeChar.sendPacket(new ExShowScreenMessage(MessageNPC18, 3000));
                               
                                try
                                {
                                        Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
                                {
                                        e.printStackTrace();
                                }
                               
                                NPC18 = 1;
                                continue;
                              
                        }
                 	}
                }
                activeChar.setTarget(null);
                try
                {
                        Thread.sleep(3000);
                }
                catch (InterruptedException e)
                {
                        e.printStackTrace();
                }
                activeChar.sendPacket(new ExShowScreenMessage("Vote Server For Us!", 6000));
                activeChar.broadcastPacket(new SocialAction(activeChar.getObjectId(), 3));
                if(Config.START_LEVEL)
                {
                activeChar.getStat().addExp(ExperienceData.getInstance().getExpForLevel(Config.GET_LEVEL_START));
                }
        }
        /* ****************************** Camera MOD ****************************** */
        
		if (SevenSigns.getInstance().isSealValidationPeriod())
			sendPacket(new SignsSky());
		
		// Buff and Status icons
		if (Config.STORE_SKILL_COOLTIME)
		{
			activeChar.restoreEffects();
		}
		
		activeChar.sendPacket(new EtcStatusUpdate(activeChar));
		
		final L2Effect[] effects = activeChar.getAllEffects();
		
		if (effects != null)
		{
			for (final L2Effect e : effects)
			{
				if (e.getEffectType() == L2Effect.EffectType.HEAL_OVER_TIME)
				{
					activeChar.stopEffects(L2Effect.EffectType.HEAL_OVER_TIME);
					activeChar.removeEffect(e);
				}
				if (e.getEffectType() == L2Effect.EffectType.COMBAT_POINT_HEAL_OVER_TIME)
				{
					activeChar.stopEffects(L2Effect.EffectType.COMBAT_POINT_HEAL_OVER_TIME);
					activeChar.removeEffect(e);
				}
			}
		}
		
		if(Config.RESURE_SKILL)
		{
            for (L2Skill s : activeChar.getAllSkills())
            {
            	activeChar.enableSkill(s);
            }
            activeChar.sendSkillList();
		}
				
		// Apply augmentation boni for equipped items
		for (final L2ItemInstance temp : activeChar.getInventory().getAugmentedItems())
			if (temp != null && temp.isEquipped())
				temp.getAugmentation().applyBoni(activeChar);
		
		// Apply death penalty
		activeChar.restoreDeathPenaltyBuffLevel();
		
		if (L2Event.active && L2Event.connectionLossData.containsKey(activeChar.getName()) && L2Event.isOnEvent(activeChar))
			L2Event.restoreChar(activeChar);
		else if (L2Event.connectionLossData.containsKey(activeChar.getName()))
			L2Event.restoreAndTeleChar(activeChar);
		
		// SECURE FIX - Anti Overenchant Cheat!!
		if (Config.MAX_ITEM_ENCHANT_KICK > 0)
		{
			for (final L2ItemInstance i : activeChar.getInventory().getItems())
			{
				if (!activeChar.isGM())
				{
					if (i.isEquipable())
					{
						if (i.getEnchantLevel() > Config.MAX_ITEM_ENCHANT_KICK)
						{
							// Delete Item Over enchanted
							activeChar.getInventory().destroyItem(null, i, activeChar, null);
							// Message to Player
							activeChar.sendMessage("[Server]: You have over enchanted items you will be kicked from server!");
							activeChar.sendMessage("[Server]: Respect our server rules.");
							// Message with screen
							sendPacket(new ExShowScreenMessage(" You have an over enchanted item, you will be kicked from server! ", 6000));
							// Punishment e LOGGER in audit
							Util.handleIllegalPlayerAction(activeChar, "Player " + activeChar.getName() + " has Overenchanted  item! Kicked! ", Config.DEFAULT_PUNISH);
							// Logger in console
							LOGGER.info("#### ATTENTION ####");
							LOGGER.info(i + " item has been removed from " + activeChar);
						}
						
					}
				}
			}
		}
		
		// Restores custom status
		activeChar.restoreCustomStatus();
		
		ColorSystem(activeChar);
		
		// Expand Skill
		final ExStorageMaxCount esmc = new ExStorageMaxCount(activeChar);
		activeChar.sendPacket(esmc);
		
		activeChar.getMacroses().sendUpdate();
		
		// Send packets info
		sendPacket(new ClientSetTime()); // SetClientTime
		sendPacket(new UserInfo(activeChar)); //
		sendPacket(new HennaInfo(activeChar));
		sendPacket(new FriendList(activeChar));
		sendPacket(new ItemList(activeChar, false));
		sendPacket(new ShortCutInit(activeChar));
		
		// Reload inventory to give SA skill
		activeChar.getInventory().reloadEquippedItems();
		
		// Welcome to Lineage II
		sendPacket(new SystemMessage(SystemMessageId.WELCOME_TO_LINEAGE));

		SevenSigns.getInstance().sendCurrentPeriodMsg(activeChar);
		Announcements.getInstance().showAnnouncements(activeChar);
		
		loadTutorial(activeChar);
		
		// Check for crowns
		CrownManager.getInstance().checkCrowns(activeChar);
		
		// Check player skills
		if (Config.CHECK_SKILLS_ON_ENTER && !Config.ALT_GAME_SKILL_LEARN)
			activeChar.checkAllowedSkills();
		
		PetitionManager.getInstance().checkPetitionMessages(activeChar);
		
		// Send user info again .. just like the real client
		// sendPacket(ui);
		
		if (activeChar.getClanId() != 0 && activeChar.getClan() != null)
		{
			sendPacket(new PledgeShowMemberListAll(activeChar.getClan(), activeChar));
			sendPacket(new PledgeStatusChanged(activeChar.getClan()));
		}
		
		if (activeChar.isAlikeDead())
			sendPacket(new Die(activeChar)); // No broadcast needed since the player will already spawn dead to others
			
		if (Config.ALLOW_WATER)
			activeChar.checkWaterState();
		
		if (Hero.getInstance().getHeroes() != null && Hero.getInstance().getHeroes().containsKey(activeChar.getObjectId()))
			activeChar.setHero(true);
		
		setPledgeClass(activeChar);
		
		for (final String name : activeChar.getFriendList())
		{
			final L2PcInstance friend = L2World.getInstance().getPlayer(name);
			
			if (friend != null) // friend logged in.
				friend.sendPacket(new SystemMessage(SystemMessageId.FRIEND_S1_HAS_LOGGED_IN).addString(activeChar.getName()));
		}
		
		notifyClanMembers(activeChar);
		notifySponsorOrApprentice(activeChar);
		
		activeChar.setTarget(activeChar);
		
		activeChar.onPlayerEnter();
		
		if (Config.PCB_ENABLE)
			activeChar.showPcBangWindow();
		
		if (Config.ANNOUNCE_CASTLE_LORDS)
			notifyCastleOwner(activeChar);
		
		if (Olympiad.getInstance().playerInStadia(activeChar))
		{
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Town);
			activeChar.sendMessage("You have been teleported to the nearest town due to you being in an Olympiad Stadium");
		}
		
		if (DimensionalRiftManager.getInstance().checkIfInRiftZone(activeChar.getX(), activeChar.getY(), activeChar.getZ(), false))
			DimensionalRiftManager.getInstance().teleportToWaitingRoom(activeChar);
		
		if (activeChar.getClanJoinExpiryTime() > System.currentTimeMillis())
			activeChar.sendPacket(new SystemMessage(SystemMessageId.CLAN_MEMBERSHIP_TERMINATED));
		
		if (activeChar.getClan() != null)
		{
			activeChar.sendPacket(new PledgeSkillList(activeChar.getClan()));
			
			for (final Siege siege : SiegeManager.getInstance().getSieges())
			{
				if (!siege.getIsInProgress())
					continue;
				
				if (siege.checkIsAttacker(activeChar.getClan()))
				{
					activeChar.setSiegeState((byte) 1);
					break;
				}
				else if (siege.checkIsDefender(activeChar.getClan()))
				{
					activeChar.setSiegeState((byte) 2);
					break;
				}
			}
			
			for (final FortSiege fortsiege : FortSiegeManager.getInstance().getSieges())
			{
				if (!fortsiege.getIsInProgress())
					continue;
				
				if (fortsiege.checkIsAttacker(activeChar.getClan()))
				{
					activeChar.setSiegeState((byte) 1);
					break;
				}
				else if (fortsiege.checkIsDefender(activeChar.getClan()))
				{
					activeChar.setSiegeState((byte) 2);
					break;
				}
			}
			
			// Add message at connexion if clanHall not paid. Possibly this is custom...
			final ClanHall clanHall = ClanHallManager.getInstance().getClanHallByOwner(activeChar.getClan());
			
			if (clanHall != null)
				if (!clanHall.getPaid())
					activeChar.sendPacket(new SystemMessage(SystemMessageId.PAYMENT_FOR_YOUR_CLAN_HALL_HAS_NOT_BEEN_MADE_PLEASE_MAKE_PAYMENT_TO_YOUR_CLAN_WAREHOUSE_BY_S1_TOMORROW));
		}
		
		if (!activeChar.isGM() && activeChar.getSiegeState() < 2 && activeChar.isInsideZone(L2Character.ZONE_SIEGE))
		{
			// Attacker or spectator logging in to a siege zone. Actually should be checked for inside castle only?
			activeChar.teleToLocation(MapRegionTable.TeleportWhereType.Town);
			activeChar.sendMessage("You have been teleported to the nearest town due to you being in siege zone");
		}
		
		RegionBBSManager.getInstance().changeCommunityBoard();
		CustomWorldHandler.getInstance().enterWorld(activeChar);
		
		if (TvT._savePlayers.contains(activeChar.getName()))
			TvT.addDisconnectedPlayer(activeChar);
		
		if (CTF._savePlayers.contains(activeChar.getName()))
			CTF.addDisconnectedPlayer(activeChar);
		
		if (DM._savePlayers.contains(activeChar.getName()))
			DM.addDisconnectedPlayer(activeChar);
		
		// Means that it's not ok multiBox situation, so logout
		if (!activeChar.checkMultiBox())
		{
			activeChar.sendMessage("I'm sorry, but multibox is not allowed here.");
			activeChar.logout();
		}
		
		Hellows(activeChar);
		
		if (Config.ALLOW_CLASS_MASTERS && Config.ALLOW_REMOTE_CLASS_MASTERS)
		{
			final L2ClassMasterInstance master_instance = L2ClassMasterInstance.getInstance();
			
			if (master_instance != null)
			{
				
				final ClassLevel lvlnow = PlayerClass.values()[activeChar.getClassId().getId()].getLevel();
				
				if (activeChar.getLevel() >= 20 && lvlnow == ClassLevel.First)
					L2ClassMasterInstance.getInstance().onAction(activeChar);
				else if (activeChar.getLevel() >= 40 && lvlnow == ClassLevel.Second)
					L2ClassMasterInstance.getInstance().onAction(activeChar);
				else if (activeChar.getLevel() >= 76 && lvlnow == ClassLevel.Third)
					L2ClassMasterInstance.getInstance().onAction(activeChar);
				
			}
			else
			{
				
				LOGGER.info("Attention: Remote ClassMaster is Enabled, but not inserted into DataBase. Remember to install 31288 Custom_Npc ..");
				
			}
		}
		
		// Apply night/day bonus on skill Shadow Sense
		if (activeChar.getRace().ordinal() == 2)
		{
			final L2Skill skill = SkillTable.getInstance().getInfo(294, 1);
			if (skill != null && activeChar.getSkillLevel(294) == 1)
			{
				if (GameTimeController.getInstance().isNowNight())
				{
					final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.NIGHT_EFFECT_APPLIES);
					sm.addSkillName(294);
					sendPacket(sm);
				}
				else
				{
					final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.DAY_EFFECT_DISAPPEARS);
					sm.addSkillName(294);
					sendPacket(sm);
				}
			}
		}
		
		if(Config.RESTART_BY_TIME_OF_DAY)
		{
		   ShowNextRestart(activeChar);
		}
		 
		
		// NPCBuffer
		if (PowerPakConfig.BUFFER_ENABLED)
			CharSchemesTable.getInstance().onPlayerLogin(activeChar.getObjectId());
		
		if (!nProtect.getInstance().checkRestriction(activeChar, RestrictionType.RESTRICT_ENTER))
		{
			activeChar.setIsImobilised(true);
			activeChar.disableAllSkills();
			ThreadPoolManager.getInstance().scheduleGeneral(new Disconnection(activeChar), 20000);
		}
		
		// Elrokian Trap like L2OFF
		final L2ItemInstance rhand = activeChar.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);
		if (rhand != null && rhand.getItemId() == 8763)
		{
			activeChar.addSkill(SkillTable.getInstance().getInfo(3626, 1));
			activeChar.addSkill(SkillTable.getInstance().getInfo(3627, 1));
			activeChar.addSkill(SkillTable.getInstance().getInfo(3628, 1));
		}
		else
		{
			activeChar.removeSkill(3626, true);
			activeChar.removeSkill(3627, true);
			activeChar.removeSkill(3628, true);
		}
		
		// If it's a Beta server all players got GM SPEED skill for better testing
		if (Config.BETASERVER)
		{
			activeChar.addSkill(SkillTable.getInstance().getInfo(7029, 4), true);
			activeChar.sendMessage("Server is on Beta mode. Skill Gm Haste 4 added for better testing.");
		}
		
		// Send all skills to char
		activeChar.sendSkillList();
		
		// Close lock at login
		activeChar.setLocked(false);
	}
	
	private boolean isValidName(final String text)
	{
		boolean result = true;
		final String test = text;
		Pattern pattern;
		
		try
		{
			pattern = Pattern.compile(Config.CNAME_TEMPLATE);
		}
		catch (final PatternSyntaxException e) // case of illegal pattern
		{
			if (Config.ENABLE_ALL_EXCEPTIONS)
				e.printStackTrace();
			
			LOGGER.warn("ERROR " + getType() + ": Character name pattern of config is wrong!");
			pattern = Pattern.compile(".*");
		}
		
		final Matcher regexp = pattern.matcher(test);
		if (!regexp.matches())
			result = false;
		
		return result;
	}
	
	private void EnterGM(final L2PcInstance activeChar)
	{
		if (activeChar.isGM())
		{
			if (Config.GM_SPECIAL_EFFECT)
				activeChar.broadcastPacket(new Earthquake(activeChar.getX(), activeChar.getY(), activeChar.getZ(), 50, 4));
			
			if (Config.SHOW_GM_LOGIN)
				Announcements.getInstance().announceToAll("GM " + activeChar.getName() + " has logged on.");
			
			if (Config.GM_STARTUP_INVULNERABLE && AdminCommandAccessRights.getInstance().hasAccess("admin_invul", activeChar.getAccessLevel()))
				activeChar.startAbnormalEffect(L2Character.ABNORMAL_EFFECT_STEALTH);
				activeChar.setIsInvul(true);
			
			if (Config.GM_STARTUP_INVISIBLE && AdminCommandAccessRights.getInstance().hasAccess("admin_invisible", activeChar.getAccessLevel()))
				activeChar.getAppearance().setInvisible();
				activeChar.startAbnormalEffect(L2Character.ABNORMAL_EFFECT_SLEEP);
			
			if (Config.GM_STARTUP_SILENCE && AdminCommandAccessRights.getInstance().hasAccess("admin_silence", activeChar.getAccessLevel()))
				activeChar.setMessageRefusal(true);
			
			if (Config.GM_STARTUP_AUTO_LIST && AdminCommandAccessRights.getInstance().hasAccess("admin_gmliston", activeChar.getAccessLevel()))
				GmListTable.getInstance().addGm(activeChar, false);
			else
				GmListTable.getInstance().addGm(activeChar, true);
			
			if (Config.MASTERACCESS_NAME_COLOR_ENABLED)
				activeChar.getAppearance().setNameColor(Config.MASTERACCESS_NAME_COLOR);
			
			if (Config.MASTERACCESS_TITLE_COLOR_ENABLED)
				activeChar.getAppearance().setTitleColor(Config.MASTERACCESS_TITLE_COLOR);
		}
	}

	private void Hellows(final L2PcInstance activeChar)
	{
		if (Config.ALT_SERVER_NAME_ENABLED)
			sendPacket(new SystemMessage(SystemMessageId.S1_S2).addString("Welcome to " + Config.ALT_Server_Name));
		
		if (Config.ONLINE_PLAYERS_ON_LOGIN)
			sendPacket(new SystemMessage(SystemMessageId.S1_S2).addString("There are " + L2World.getInstance().getAllPlayers().size() + " players online."));
		
		if (activeChar.getFirstLog() && Config.NEW_PLAYER_EFFECT)
		{
			final L2Skill skill = SkillTable.getInstance().getInfo(2025, 1);
			if (skill != null)
			{
				final MagicSkillUser MSU = new MagicSkillUser(activeChar, activeChar, 2025, 1, 1, 0);
				activeChar.sendPacket(MSU);
				activeChar.broadcastPacket(MSU);
				activeChar.useMagic(skill, false, false);
			}
			activeChar.setFirstLog(false);
			activeChar.updateFirstLog();
		}
		
		if (Config.WELCOME_HTM && isValidName(activeChar.getName()))
		{
			final String Welcome_Path = "data/html/welcome.htm";
			final File mainText = new File(Config.DATAPACK_ROOT, Welcome_Path);
			if (mainText.exists())
			{
				final NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(Welcome_Path);
				html.replace("%name%", activeChar.getName());
				sendPacket(html);
			}
		}
		
		if ((activeChar.getClan() != null) && activeChar.getClan().isNoticeEnabled())
		{
			final String clanNotice = "data/html/clanNotice.htm";
			final File mainText = new File(Config.DATAPACK_ROOT, clanNotice);
			if (mainText.exists())
			{
				final NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(clanNotice);
				html.replace("%clan_name%", activeChar.getClan().getName());
				html.replace("%notice_text%", activeChar.getClan().getNotice().replaceAll("\r\n", "<br>"));
				sendPacket(html);
			}
		}
		
		if (Config.PM_MESSAGE_ON_START)
		{
			activeChar.sendPacket(new CreatureSay(2, Say2.HERO_VOICE, Config.PM_TEXT1, Config.PM_SERVER_NAME));
			activeChar.sendPacket(new CreatureSay(15, Say2.PARTYROOM_COMMANDER, activeChar.getName(), Config.PM_TEXT2));
		}
		
		if (Config.SERVER_TIME_ON_START)
			activeChar.sendMessage("SVR time is " + fmt.format(new Date(System.currentTimeMillis())));
	}
	
	private void ColorSystem(final L2PcInstance activeChar)
	{
		// Color System checks - Start
		// Check if the custom PvP and PK color systems are enabled and if so check the character's counters
		// and apply any color changes that must be done. Thankz Kidzor
		/** KidZor: Ammount 1 **/
		if (activeChar.getPvpKills() >= Config.PVP_AMOUNT1 && Config.PVP_COLOR_SYSTEM_ENABLED)
			activeChar.updatePvPColor(activeChar.getPvpKills());
		if (activeChar.getPkKills() >= Config.PK_AMOUNT1 && Config.PK_COLOR_SYSTEM_ENABLED)
			activeChar.updatePkColor(activeChar.getPkKills());
		
		/** KidZor: Ammount 2 **/
		if (activeChar.getPvpKills() >= Config.PVP_AMOUNT2 && Config.PVP_COLOR_SYSTEM_ENABLED)
			activeChar.updatePvPColor(activeChar.getPvpKills());
		if (activeChar.getPkKills() >= Config.PK_AMOUNT2 && Config.PK_COLOR_SYSTEM_ENABLED)
			activeChar.updatePkColor(activeChar.getPkKills());
		
		/** KidZor: Ammount 3 **/
		if (activeChar.getPvpKills() >= Config.PVP_AMOUNT3 && Config.PVP_COLOR_SYSTEM_ENABLED)
			activeChar.updatePvPColor(activeChar.getPvpKills());
		if (activeChar.getPkKills() >= Config.PK_AMOUNT3 && Config.PK_COLOR_SYSTEM_ENABLED)
			activeChar.updatePkColor(activeChar.getPkKills());
		
		/** KidZor: Ammount 4 **/
		if (activeChar.getPvpKills() >= Config.PVP_AMOUNT4 && Config.PVP_COLOR_SYSTEM_ENABLED)
			activeChar.updatePvPColor(activeChar.getPvpKills());
		if (activeChar.getPkKills() >= Config.PK_AMOUNT4 && Config.PK_COLOR_SYSTEM_ENABLED)
			activeChar.updatePkColor(activeChar.getPkKills());
		
		/** KidZor: Ammount 5 **/
		if (activeChar.getPvpKills() >= Config.PVP_AMOUNT5 && Config.PVP_COLOR_SYSTEM_ENABLED)
			activeChar.updatePvPColor(activeChar.getPvpKills());
		if (activeChar.getPkKills() >= Config.PK_AMOUNT5 && Config.PK_COLOR_SYSTEM_ENABLED)
			activeChar.updatePkColor(activeChar.getPkKills());
		// Color System checks - End
		
		// Apply color settings to clan leader when entering
		if (activeChar.getClan() != null && activeChar.isClanLeader() && Config.CLAN_LEADER_COLOR_ENABLED && activeChar.getClan().getLevel() >= Config.CLAN_LEADER_COLOR_CLAN_LEVEL)
		{
			if (Config.CLAN_LEADER_COLORED == 1)
				activeChar.getAppearance().setNameColor(Config.CLAN_LEADER_COLOR);
			else
				activeChar.getAppearance().setTitleColor(Config.CLAN_LEADER_COLOR);
		}
		
		if (Config.ALLOW_AIO_NCOLOR && activeChar.isAio())
			activeChar.getAppearance().setNameColor(Config.AIO_NCOLOR);
		
		if (Config.ALLOW_AIO_TCOLOR && activeChar.isAio())
			activeChar.getAppearance().setTitleColor(Config.AIO_TCOLOR);
		
		if (activeChar.isAio())
			onEnterAio(activeChar);
		
		activeChar.updateNameTitleColor();
		
		sendPacket(new UserInfo(activeChar));
		sendPacket(new HennaInfo(activeChar));
		sendPacket(new FriendList(activeChar));
		sendPacket(new ItemList(activeChar, false));
		sendPacket(new ShortCutInit(activeChar));
		activeChar.broadcastUserInfo();
		activeChar.sendPacket(new EtcStatusUpdate(activeChar));
	}
	
	private void onEnterAio(final L2PcInstance activeChar)
	{
		final long now = Calendar.getInstance().getTimeInMillis();
		final long endDay = activeChar.getAioEndTime();
		
		if (now > endDay)
		{
			activeChar.setAio(false);
			activeChar.setAioEndTime(0);
			activeChar.lostAioSkills();
			activeChar.sendMessage("[Aio System]: Removed your Aio stats... period ends.");
		}
		else
		{
			final Date dt = new Date(endDay);
			_daysleft = (endDay - now) / 86400000;
			if (_daysleft > 30)
				activeChar.sendMessage("[Aio System]: Aio period ends in " + df.format(dt) + ". enjoy the Game.");
			else if (_daysleft > 0)
				activeChar.sendMessage("[Aio System]: Left " + (int) _daysleft + " for Aio period ends.");
			else if (_daysleft < 1)
			{
				final long hour = (endDay - now) / 3600000;
				activeChar.sendMessage("[Aio System]: Left " + (int) hour + " hours to Aio period ends.");
			}
		}
	}
	
	/**
	 * @param cha
	 */
	private void engage(final L2PcInstance cha)
	{
		final int _chaid = cha.getObjectId();
		
		for (final Wedding cl : CoupleManager.getInstance().getCouples())
		{
			if (cl.getPlayer1Id() == _chaid || cl.getPlayer2Id() == _chaid)
			{
				if (cl.getMaried())
				{
					cha.setMarried(true);
					cha.setmarriedType(cl.getType());
				}
				
				cha.setCoupleId(cl.getId());
				
				if (cl.getPlayer1Id() == _chaid)
					cha.setPartnerId(cl.getPlayer2Id());
				else
					cha.setPartnerId(cl.getPlayer1Id());
			}
		}
	}
	
	/**
	 * @param cha
	 * @param partnerId
	 */
	private void notifyPartner(final L2PcInstance cha, final int partnerId)
	{
		if (cha.getPartnerId() != 0)
		{
			L2PcInstance partner = null;
			
			if (L2World.getInstance().findObject(cha.getPartnerId()) instanceof L2PcInstance)
				partner = (L2PcInstance) L2World.getInstance().findObject(cha.getPartnerId());
			
			if (partner != null)
				partner.sendMessage("Your partner has logged in");
		}
	}
	
	/**
	 * @param activeChar
	 */
	private void notifyClanMembers(final L2PcInstance activeChar)
	{
		final L2Clan clan = activeChar.getClan();
		if (clan != null)
		{
			clan.getClanMember(activeChar.getObjectId()).setPlayerInstance(activeChar);
			clan.broadcastToOtherOnlineMembers(new SystemMessage(SystemMessageId.CLAN_MEMBER_S1_LOGGED_IN).addString(activeChar.getName()), activeChar);
			clan.broadcastToOtherOnlineMembers(new PledgeShowMemberListUpdate(activeChar), activeChar);
		}
	}
	
	/**
	 * @param activeChar
	 */
	private void notifySponsorOrApprentice(final L2PcInstance activeChar)
	{
		if (activeChar.getSponsor() != 0)
		{
			final L2PcInstance sponsor = (L2PcInstance) L2World.getInstance().findObject(activeChar.getSponsor());
			if (sponsor != null)
				sponsor.sendPacket(new SystemMessage(SystemMessageId.YOUR_APPRENTICE_S1_HAS_LOGGED_IN).addString(activeChar.getName()));
		}
		else if (activeChar.getApprentice() != 0)
		{
			final L2PcInstance apprentice = (L2PcInstance) L2World.getInstance().findObject(activeChar.getApprentice());
			if (apprentice != null)
				apprentice.sendPacket(new SystemMessage(SystemMessageId.YOUR_SPONSOR_S1_HAS_LOGGED_IN).addString(activeChar.getName()));
		}
	}
	
	private void loadTutorial(final L2PcInstance player)
	{
		final QuestState qs = player.getQuestState("255_Tutorial");
		if (qs != null)
			qs.getQuest().notifyEvent("UC", null, player);
	}
	
	private void setPledgeClass(final L2PcInstance activeChar)
	{
		int pledgeClass = 0;
		
		if (activeChar.getClan() != null)
			pledgeClass = activeChar.getClan().getClanMember(activeChar.getObjectId()).calculatePledgeClass(activeChar);
		
		if (activeChar.isNoble() && pledgeClass < 5)
			pledgeClass = 5;
		
		if (activeChar.isHero())
			pledgeClass = 8;
		
		activeChar.setPledgeClass(pledgeClass);
	}
	
	private void notifyCastleOwner(final L2PcInstance activeChar)
	{
		final L2Clan clan = activeChar.getClan();
		if (clan != null)
		{
			if (clan.getHasCastle() > 0)
			{
				final Castle castle = CastleManager.getInstance().getCastleById(clan.getHasCastle());
				if ((castle != null) && (activeChar.getObjectId() == clan.getLeaderId()))
					Announcements.getInstance().announceToAll("Lord " + activeChar.getName() + " Ruler Of " + castle.getName() + " Castle is now Online!");
			}
		}
	}
	
    /**
    * Envia mensagem para o player do proximo restart
    * NOTE: RESTART_BY_TIME_OF_DAY = TRUE
    *
    * @param activeChar
    */
    private void ShowNextRestart(L2PcInstance activeChar)
    {
       activeChar.sendMessage("Next Restart: " + Restart.getInstance().getRestartNextTime());
    }
	 
	@Override
	public String getType()
	{
		return "[C] 03 EnterWorld";
	}
}