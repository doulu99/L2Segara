/*
 * L2jFrozen Project - www.l2jfrozen.com 
 * 
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
package com.l2jfrozen.gameserver.model.entity.event.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.l2jfrozen.gameserver.model.entity.event.CTF;
import com.l2jfrozen.gameserver.model.entity.event.DM;
import com.l2jfrozen.gameserver.model.entity.event.FarmEvent;
import com.l2jfrozen.gameserver.model.entity.event.TvT;

/**
 * @author Shyla
 */
public class EventManager
{
	protected static final Logger LOGGER = Logger.getLogger(EventManager.class);
	
	private final static String EVENT_MANAGER_CONFIGURATION_FILE = "./config/frozen/eventmanager.properties";
	
	public static boolean TVT_EVENT_ENABLED;
	public static ArrayList<String> TVT_TIMES_LIST;
	
	public static boolean CTF_EVENT_ENABLED;
	public static ArrayList<String> CTF_TIMES_LIST;
	
	public static boolean DM_EVENT_ENABLED;
	public static ArrayList<String> DM_TIMES_LIST;
	
	public static boolean FARM_EVENT_ENABLED;
	public static String FARM_EVENT_DESCRIPTION, FARM_EVENT_NAME, FARM_EVENT_GATEKEEPER_LOCATION;
	public static int   FARM_EVENT_DURATION,
						FARM_EVENT_DELAY,
						FARM_EVENT_DELAYFIRSTTIME,
						// farm event location one aden town
						FARM_EVENT_GATEKEEPER_ID,
						FARM_EVENT_GATEKEEPER_X,
						FARM_EVENT_GATEKEEPER_Y,
						FARM_EVENT_GATEKEEPER_Z,
						//farm event location two giran town
						FARM_EVENT_GATEKEEPER_ID2,
						FARM_EVENT_GATEKEEPER_X2,
						FARM_EVENT_GATEKEEPER_Y2,
						FARM_EVENT_GATEKEEPER_Z2,
						//farm event location spawn raid boss
						FARM_EVENT_Raidboss_ID,
						FARM_EVENT_Raidboss_X,
						FARM_EVENT_Raidboss_Y,
						FARM_EVENT_Raidboss_Z,
						FARM_EVENT_MONSTER_ID,
						FARM_EVENT_MONSTER_X,
						FARM_EVENT_MONSTER_Y,
						FARM_EVENT_MONSTER_Z;
	public static double FARM_EVENT_MONSTER_HP;
	
	private static EventManager instance = null;
	
	private EventManager()
	{
		loadConfiguration();
	}
	
	public static EventManager getInstance()
	{
		
		if (instance == null)
		{
			instance = new EventManager();
		}
		return instance;
		
	}
	
	public static void loadConfiguration()
	{
		
		InputStream is = null;
		try
		{
			final Properties eventSettings = new Properties();
			is = new FileInputStream(new File(EVENT_MANAGER_CONFIGURATION_FILE));
			eventSettings.load(is);
			
			// ============================================================
			
			TVT_EVENT_ENABLED = Boolean.parseBoolean(eventSettings.getProperty("TVTEventEnabled", "false"));
			TVT_TIMES_LIST = new ArrayList<>();
			
			String[] propertySplit;
			propertySplit = eventSettings.getProperty("TVTStartTime", "").split(";");
			
			for (final String time : propertySplit)
			{
				TVT_TIMES_LIST.add(time);
			}
			
			CTF_EVENT_ENABLED = Boolean.parseBoolean(eventSettings.getProperty("CTFEventEnabled", "false"));
			CTF_TIMES_LIST = new ArrayList<>();
			
			propertySplit = eventSettings.getProperty("CTFStartTime", "").split(";");
			
			for (final String time : propertySplit)
			{
				CTF_TIMES_LIST.add(time);
			}
			
			DM_EVENT_ENABLED = Boolean.parseBoolean(eventSettings.getProperty("DMEventEnabled", "false"));
			DM_TIMES_LIST = new ArrayList<>();
			
			propertySplit = eventSettings.getProperty("DMStartTime", "").split(";");
			
			for (final String time : propertySplit)
			{
				DM_TIMES_LIST.add(time);
			}
			
			FARM_EVENT_ENABLED = Boolean.parseBoolean(eventSettings.getProperty("FarmEventEnabled", "false"));
			FARM_EVENT_NAME = eventSettings.getProperty("Name", "FarmEvent");
			FARM_EVENT_DESCRIPTION = eventSettings.getProperty("Description", "A evil monster have appeared go and kill him");
			FARM_EVENT_DURATION = Integer.parseInt(eventSettings.getProperty("Duration", "30"));
			FARM_EVENT_DELAY = Integer.parseInt(eventSettings.getProperty("IntervalBetweenEvent", "30"));
			FARM_EVENT_DELAYFIRSTTIME = Integer.parseInt(eventSettings.getProperty("DelayFirstTime", "30"));
			// farm event gatekeeper one location spawn in aden
			FARM_EVENT_GATEKEEPER_ID = Integer.parseInt(eventSettings.getProperty("GatekeeperID", "30"));
			FARM_EVENT_GATEKEEPER_X = Integer.parseInt(eventSettings.getProperty("GatekeeperXPos", "30"));
			FARM_EVENT_GATEKEEPER_Y = Integer.parseInt(eventSettings.getProperty("GatekeeperYPos", "30"));
			FARM_EVENT_GATEKEEPER_Z = Integer.parseInt(eventSettings.getProperty("GatekeeperZPos", "30"));
			FARM_EVENT_GATEKEEPER_LOCATION = eventSettings.getProperty("GatekeeperLoc", "Giran");
			// Raid Boss Spawn location and use here ID to config
			FARM_EVENT_Raidboss_ID = Integer.parseInt(eventSettings.getProperty("RaidbossID", "30"));
			FARM_EVENT_Raidboss_X = Integer.parseInt(eventSettings.getProperty("RaidbossXPos", "30"));
			FARM_EVENT_Raidboss_Y = Integer.parseInt(eventSettings.getProperty("RaidbossYPos", "30"));
			FARM_EVENT_Raidboss_Z = Integer.parseInt(eventSettings.getProperty("RaidbossZPos", "30"));
			// farm event gatekeeper two location spawn in giran
			FARM_EVENT_GATEKEEPER_ID2 = Integer.parseInt(eventSettings.getProperty("GatekeeperID2", "30"));
			FARM_EVENT_GATEKEEPER_X2 = Integer.parseInt(eventSettings.getProperty("GatekeeperXPos2", "30"));
			FARM_EVENT_GATEKEEPER_Y2 = Integer.parseInt(eventSettings.getProperty("GatekeeperYPos2", "30"));
			FARM_EVENT_GATEKEEPER_Z2 = Integer.parseInt(eventSettings.getProperty("GatekeeperZPos2", "30"));
			FARM_EVENT_MONSTER_ID = Integer.parseInt(eventSettings.getProperty("MonsterID", "30"));
			FARM_EVENT_MONSTER_HP = Integer.parseInt(eventSettings.getProperty("MonsterHP", "1000000"));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void startEventRegistration()
	{
		
		if (TVT_EVENT_ENABLED)
		{
			registerTvT();
		}
		
		if (CTF_EVENT_ENABLED)
		{
			registerCTF();
		}
		
		if (DM_EVENT_ENABLED)
		{
			registerDM();
		}
		
		if(FARM_EVENT_ENABLED)
		{
			registerFarm();
		}
		
	}
	
	private static void registerTvT()
	{
		
		TvT.loadData();
		if (!TvT.checkStartJoinOk())
		{
			LOGGER.error("registerTvT: TvT Event is not setted Properly");
		}
		
		// clear all tvt
		EventsGlobalTask.getInstance().clearEventTasksByEventName(TvT.get_eventName());
		
		for (final String time : TVT_TIMES_LIST)
		{
			
			final TvT newInstance = TvT.getNewInstance();
			// LOGGER.info("registerTvT: reg.time: "+time);
			newInstance.setEventStartTime(time);
			EventsGlobalTask.getInstance().registerNewEventTask(newInstance);
			
		}
		
	}
	
	private static void registerCTF()
	{
		
		CTF.loadData();
		if (!CTF.checkStartJoinOk())
		{
			LOGGER.error("registerCTF: CTF Event is not setted Properly");
		}
		
		// clear all tvt
		EventsGlobalTask.getInstance().clearEventTasksByEventName(CTF.get_eventName());
		
		for (final String time : CTF_TIMES_LIST)
		{
			
			final CTF newInstance = CTF.getNewInstance();
			// LOGGER.info("registerCTF: reg.time: "+time);
			newInstance.setEventStartTime(time);
			EventsGlobalTask.getInstance().registerNewEventTask(newInstance);
			
		}
		
	}
	
	private static void registerDM()
	{
		DM.loadData();
		if (!DM.checkStartJoinOk())
		{
			LOGGER.error("registerDM: DM Event is not setted Properly");
		}
		
		// clear all tvt
		EventsGlobalTask.getInstance().clearEventTasksByEventName(DM.get_eventName());
		
		for (final String time : DM_TIMES_LIST)
		{
			
			final DM newInstance = DM.getNewInstance();
			// LOGGER.info("registerDM: reg.time: "+time);
			newInstance.setEventStartTime(time);
			EventsGlobalTask.getInstance().registerNewEventTask(newInstance);
			
		}
	}
	
	private static void registerFarm()
	{
		//Set values of event
		FarmEvent newInstance = FarmEvent.getNewInstance();      
		newInstance.set_eventName(FARM_EVENT_NAME);
		newInstance.set_eventDescription(FARM_EVENT_DESCRIPTION);
		newInstance.set_eventDuration(FARM_EVENT_DURATION);
		// farm event location one in aden town
		newInstance.set_gatekeeperId(FARM_EVENT_GATEKEEPER_ID);
		newInstance.set_gatekeeperX(FARM_EVENT_GATEKEEPER_X);
		newInstance.set_gatekeeperY(FARM_EVENT_GATEKEEPER_Y);
		newInstance.set_gatekeeperZ(FARM_EVENT_GATEKEEPER_Z);
		// farm event location two in giran town
		newInstance.set_gatekeeperId2(FARM_EVENT_GATEKEEPER_ID2);
		newInstance.set_gatekeeperX2(FARM_EVENT_GATEKEEPER_X2);
		newInstance.set_gatekeeperY2(FARM_EVENT_GATEKEEPER_Y2);
		newInstance.set_gatekeeperZ2(FARM_EVENT_GATEKEEPER_Z2);
		newInstance.set_gatekeeperLocation(FARM_EVENT_GATEKEEPER_LOCATION);
		// farm event location spawn raid boss
		newInstance.set_RaidbossId(FARM_EVENT_Raidboss_ID);
		newInstance.set_RaidbossX(FARM_EVENT_Raidboss_X);
		newInstance.set_RaidbossY(FARM_EVENT_Raidboss_Y);
		newInstance.set_RaidbossZ(FARM_EVENT_Raidboss_Z);
		newInstance.set_monsterId(FARM_EVENT_MONSTER_ID);
		newInstance.set_eventInterval(FARM_EVENT_DELAY);
		newInstance.set_delayFirstTime(FARM_EVENT_DELAYFIRSTTIME);
		
		newInstance.start();
		System.out.println(FARM_EVENT_NAME + " initialized.");
	}
}
