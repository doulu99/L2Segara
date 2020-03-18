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
package com.l2jfrozen.gameserver.communitybbs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import javolution.util.FastMap;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.cache.HtmCache;
import com.l2jfrozen.gameserver.communitybbs.Manager.BaseBBSManager;
import com.l2jfrozen.gameserver.communitybbs.Manager.ClanBBSManager;
import com.l2jfrozen.gameserver.communitybbs.Manager.PostBBSManager;
import com.l2jfrozen.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jfrozen.gameserver.communitybbs.Manager.TeleBBSManager;
import com.l2jfrozen.gameserver.communitybbs.Manager.TopicBBSManager;
import com.l2jfrozen.gameserver.communitybbs.Manager.shopBBSManager;
import com.l2jfrozen.gameserver.handler.IBBSHandler;
import com.l2jfrozen.gameserver.model.L2Clan;
import com.l2jfrozen.gameserver.model.L2World;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.model.multisell.L2Multisell;
import com.l2jfrozen.gameserver.network.L2GameClient;
import com.l2jfrozen.gameserver.network.SystemMessageId;
import com.l2jfrozen.gameserver.network.serverpackets.ShowBoard;
import com.l2jfrozen.gameserver.network.serverpackets.SystemMessage;

public class CommunityBoard
{
	private static CommunityBoard _instance;
	private final Map<String, IBBSHandler> _handlers;
	protected final SimpleDateFormat fmt = new SimpleDateFormat("H:mm.");
	
	public CommunityBoard()
	{
		_handlers = new FastMap<>();
		// null;
	}
	
	public boolean checkPlayerConditions(L2PcInstance activeChar, String command)
	{
	    if (activeChar.isInOlympiadMode())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited at the Olympiad");
	        return false;
	    }
	    if (activeChar.isFlying() || activeChar.isMounted())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited at while flying or mounted!");
	        return false;
	    }
	    if (activeChar.inObserverMode())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited in ObserveMode!");
	        return false;
	    }
	    if (activeChar.isAlikeDead() || activeChar.isDead())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited While Dead");
	        return false;
	    }
	    if (activeChar.isInCombat())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited in Combat!");
	        return false;
	    }
	    if (activeChar.isCastingNow())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited while Casting!");
	        return false;
	    }
	    if (activeChar.isAttackingNow())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited while Attacking!");
	        return false;
	    }
	    if (activeChar.isInDuel())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited while Playing Duel!");
	        return false;
	    }
	    if (activeChar.isFishing())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited while Fishing!");
	        return false;
	    }
	    if (activeChar.isInStoreMode())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited in StoreMode!");
	        return false;
	    }
	    if (activeChar.isInJail() || activeChar.isCursedWeaponEquipped() || activeChar.isFlying() || activeChar.isInBoat() || activeChar.isProcessingTransaction() || activeChar.isStunned())
	    {
	    	activeChar.sendMessage("CommunityBoard use is prohibited right now!");
	        return false;
	    }

	    return true;
	}
	
	public static CommunityBoard getInstance()
	{
		if (_instance == null)
		{
			_instance = new CommunityBoard();
		}
		
		return _instance;
	}
	
	/**
	 * by Azagthtot
	 * @param handler as IBBSHandler
	 */
	public void registerBBSHandler(final IBBSHandler handler)
	{
		for (final String s : handler.getBBSCommands())
		{
			_handlers.put(s, handler);
		}
	}
	
	/**
	 * by Azagthtot
	 * @param client
	 * @param command
	 */
	public void handleCommands(final L2GameClient client, final String command)
	{
		L2PcInstance activeChar = client.getActiveChar();
		
		if (activeChar == null)
			return;
		
		if(!checkPlayerConditions(activeChar, command))
			return;
		
		if (Config.COMMUNITY_TYPE.equals("full"))
		{
			String cmd = command.substring(4);
			String params = "";
			final int iPos = cmd.indexOf(" ");
			if (iPos != -1)
			{
				params = cmd.substring(iPos + 1);
				cmd = cmd.substring(0, iPos);
				
			}
			final IBBSHandler bbsh = _handlers.get(cmd);
			if (bbsh != null)
			{
				bbsh.handleCommand(cmd, activeChar, params);
			}
			else
			{
				if (command.startsWith("_bbsclan"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);
				}
				else if (command.startsWith("_bbsmemo"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);
				}
				else if (command.startsWith("_bbsgetfav"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);	
				}
				else if (command.startsWith("_bbstopics"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);
				}
				else if (command.startsWith("_bbsposts"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);
				}
				else if (command.startsWith("_bbstop"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);
				}
				else if (command.startsWith("_bbshome"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);
				}
				else if (command.startsWith("_bbsloc"))
				{
					String text = HtmCache.getInstance().getHtm("data/html/CommunityBoard/index.htm");
					//Custom Community Board
					text = text.replace("%CharName%", String.valueOf(activeChar.getName()));
					text = text.replace("%CharClass%", String.valueOf(activeChar.getClassId().name()));
					text = text.replace("%CharLevel%", String.valueOf(activeChar.getLevel()));
					if (activeChar.isNoble())
					{
						text = text.replace("%nobless%", "Yes");
					}
					else
					{
						text = text.replace("%nobless%", "No");
					}
					L2Clan clan = activeChar.getClan();
					if (clan != null)
					{
						text = text.replace("%CharClan%", String.valueOf(activeChar.getClan().getName()));
					}
					else
					{
						text = text.replace("%CharClan%", "No Clan");
					}
					text = text.replace("%CharIP%", String.valueOf(activeChar.getClient().getConnection().getInetAddress().getHostAddress()));
					text = text.replace("%PlayerOnline%", String.valueOf(L2World.getInstance().getAllPlayers().size()* 1));
					text = text.replace("%ServerTime%", fmt.format(new Date(System.currentTimeMillis())));
					//Custom Community Board
					BaseBBSManager.separateAndSend(text, activeChar);
				}
				else if (command.startsWith("_bbstele"))
				{
					TeleBBSManager.getInstance().parsecmd(command, activeChar);
				}
				else if (command.startsWith("_bbsShop"))
				{
					shopBBSManager.getInstance().parsecmd(command, activeChar);
				}
				else if(command.startsWith("_bbsmultisell;"))
				{
				StringTokenizer st = new StringTokenizer(command, ";");
				st.nextToken();
				shopBBSManager.getInstance().parsecmd("_bbsShop;" + st.nextToken(), activeChar);
				L2Multisell.getInstance().SeparateAndSend(Integer.parseInt(st.nextToken()), activeChar, false, 0);
				}
				else
				{
					ShowBoard sb = new ShowBoard("<html><body><br><br><center>the command: " + command + " is not implemented yet</center><br><br></body></html>", "101");
					activeChar.sendPacket(sb);
					sb = null;
					activeChar.sendPacket(new ShowBoard(null, "102"));
					activeChar.sendPacket(new ShowBoard(null, "103"));
					
				}
			}
			
		}
		else if (Config.COMMUNITY_TYPE.equals("old"))
		{
			RegionBBSManager.getInstance().parsecmd(command, activeChar);
		}
		else
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.CB_OFFLINE));
		}
		
		activeChar = null;
	}
	
	/**
	 * @param client
	 * @param url
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 */
	public void handleWriteCommands(final L2GameClient client, final String url, final String arg1, final String arg2, final String arg3, final String arg4, final String arg5)
	{
		L2PcInstance activeChar = client.getActiveChar();
		
		if (activeChar == null)
			return;
		
		if (Config.COMMUNITY_TYPE.equals("full"))
		{
			if (url.equals("Topic"))
			{
				TopicBBSManager.getInstance().parsewrite(arg1, arg2, arg3, arg4, arg5, activeChar);
			}
			else if (url.equals("Post"))
			{
				PostBBSManager.getInstance().parsewrite(arg1, arg2, arg3, arg4, arg5, activeChar);
			}
			else if (url.equals("Region"))
			{
				RegionBBSManager.getInstance().parsewrite(arg1, arg2, arg3, arg4, arg5, activeChar);
			}
			else if (url.equals("Notice"))
			{
				ClanBBSManager.getInstance().parsewrite(arg1, arg2, arg3, arg4, arg5, activeChar);
			}
			else
			{
				ShowBoard sb = new ShowBoard("<html><body><br><br><center>the command: " + url + " is not implemented yet</center><br><br></body></html>", "101");
				activeChar.sendPacket(sb);
				sb = null;
				activeChar.sendPacket(new ShowBoard(null, "102"));
				activeChar.sendPacket(new ShowBoard(null, "103"));
			}
		}
		else if (Config.COMMUNITY_TYPE.equals("old"))
		{
			RegionBBSManager.getInstance().parsewrite(arg1, arg2, arg3, arg4, arg5, activeChar);
		}
		else
		{
			ShowBoard sb = new ShowBoard("<html><body><br><br><center>The Community board is currently disable</center><br><br></body></html>", "101");
			activeChar.sendPacket(sb);
			sb = null;
			activeChar.sendPacket(new ShowBoard(null, "102"));
			activeChar.sendPacket(new ShowBoard(null, "103"));
		}
		
		activeChar = null;
	}
}					