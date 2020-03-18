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
package com.l2jfrozen.gameserver.handler.admincommandhandlers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javolution.util.FastMap;

import com.l2jfrozen.gameserver.thread.ThreadPoolManager;
import com.l2jfrozen.gameserver.handler.IAdminCommandHandler;
import com.l2jfrozen.gameserver.model.L2Object;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.network.serverpackets.SpecialCamera;

/**
 * @author KKnD
 * modify tukune
 */
public class AdminMovieMaker implements IAdminCommandHandler
{
	private static final long DELAY_MENU = 2000;

	private static final String[] ADMIN_COMMANDS =
	{
		"admin_mmaker"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
	//	if (Config.ENABLE_SAFE_ADMIN_PROTECTION)
	//	{
	//		if (!activeChar.isSafeAdmin())
	//		{
	//			activeChar.logout();
	//			_log.warning("Character " + activeChar.getName() + "(" + activeChar.getObjectId() + ") tryed to use an admin command.");
	//			return false;
	//		}
	//	}
		
		String[] args = command.substring(command.indexOf(' ') + 1).split(" ");
		final String subCmd = args[0]; /*args[0] = null;*/
		try
		{
			if (subCmd.equals("admin_mmaker"))
			{
				showMainWindow(activeChar);
			}
			else if (subCmd.equals("showNewWindow"))
			{
				showNewWindow(activeChar);
			}
			else if (subCmd.equals("addSequence"))
			{
				if (args.length != 11)
				{
					activeChar.sendMessage("Not all arguments was set");
					showNewWindow(activeChar);
					return false;
				}
				L2Object target = activeChar.getTarget();
				if (target == null)
				{
					activeChar.sendMessage("Target for camera is missing");
					showNewWindow(activeChar);
					return false;
				}
				addSequence(activeChar, Integer.parseInt(args[1]), target.getObjectId(), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), Integer.parseInt(args[10]));
			}
			else if (subCmd.equals("showModifyWindow"))
			{
				showModifyWindow(activeChar, Integer.parseInt(args[1]));
			}
			else if (subCmd.equals("updateSequence"))
			{
				args = command.split(" *\\| *");
				if (args.length != 11)
				{
					throw new RuntimeException();
				}
				L2Object target = activeChar.getTarget();
				if (target == null)
				{
					activeChar.sendMessage("Target for camera is missing");
					showModifyWindow(activeChar, Integer.parseInt(args[1]));
					return false;
				}
				updateSequence(activeChar, Integer.parseInt(args[1]), target.getObjectId(), args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]);
			}
		//	else if (subCmd.equals("preview"))
		//	{
		//		if (args.length != 11)
		//		{
		//			activeChar.sendMessage("Not all arguments was set");
		//			return false;
		//		}
		//		L2Object target = activeChar.getTarget();
		//		if (target == null)
		//		{
		//			activeChar.sendMessage("Target for camera is missing");
		//			showMainWindow(activeChar);
		//			return false;
		//		}
		//		activeChar.sendPacket(new SpecialCamera(target.getObjectId(), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), Integer.parseInt(args[10])));
		//	}
			else if (subCmd.equals("deleteSequence"))
			{
				deleteSequence(activeChar, Integer.parseInt(args[1]));
			}
			
			else if (subCmd.equals("play"))
			{
				if (args.length == 1)
					playMovie(activeChar, false);
				else /*if (args.length == 2)*/
					playSequence(activeChar, false, Integer.parseInt(args[1]));
			}
			
			else if (subCmd.equals("brodcast"))
			{
				if (args.length == 1)
					playMovie(activeChar, true);
				else /*if (args.length == 2)*/
					playSequence(activeChar, true, Integer.parseInt(args[1]));
			}
			else if (subCmd.equals("dump"))
			{
				dump(activeChar);
			}
			else
			{
				System.out.println("AdminMovieMaker.useAdminCommand(" + activeChar.getName() + ", \"" + command + "\")");/*@IF DEBUG@*/
			//	_log.warning("AdminMovieMaker.useAdminCommand(" + activeChar.getName() + ", \"" + command + "\")");
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			activeChar.sendMessage("Not all arguments was set");
			showMainWindow(activeChar);
			return false;
		}
		catch (NumberFormatException e)
		{
			activeChar.sendMessage("Illegal number format");
			showMainWindow(activeChar);
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			showMainWindow(activeChar);
			return false;
		}
		return true;
	}

	//-----------------------------------------------------//

	protected class Sequence
	{
		int sequenceId;
		int objctId;
		int distance;
		int yaw;
		int pitch;
		int time;
		int duration;
		int turn;
		int rise;
		int widescreen;
		int unknown;
		//TODO	int delay;	/*@IF DEBUG@*/

		SpecialCamera specialCamera()
		{ 
			return new SpecialCamera(objctId, distance, yaw, pitch, time, duration);
		}
	}
	
	private Map<String, SortedMap<Integer, Sequence>> _sequencePool = new FastMap<>();
	
	protected SortedMap<Integer, Sequence> getMovie(L2PcInstance player)
	{
		return _sequencePool.get(player.getAccountName());
	}
	private Sequence getSequence(L2PcInstance player, int sequenceId)
	{
		SortedMap<Integer, Sequence> movie = _sequencePool.get(player.getAccountName());
		if (movie == null) return null;
		return movie.get(sequenceId);
	}
	private void putSequence(L2PcInstance player, Sequence s)
	{
		SortedMap<Integer, Sequence> movie = _sequencePool.get(player.getAccountName());
		if (movie == null) {
			movie = new TreeMap<>();
			_sequencePool.put(player.getAccountName(), movie);
		}
		movie.put(s.sequenceId, s);
	}
	private Sequence removeSequence(L2PcInstance player, int sequenceId)
	{
		SortedMap<Integer, Sequence> movie = _sequencePool.get(player.getAccountName());
		if (movie == null) return null;
		Sequence result = movie.remove(sequenceId);
		if (movie.size() == 0) _sequencePool.remove(player.getAccountName());
		return result;
	}

	//-----------------------------------------------------//

	static final int SEQUENCES_WIDTH = 26;
	protected void showMainWindow(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(5);
		StringBuilder sb = new StringBuilder(2000);
		sb.append("<html><title>KKnD's Movie Maker</title><body>Sequences:");
		SortedMap<Integer, Sequence> movie = getMovie(player);
		if (movie != null && movie.size() > 0)
		{
			sb.append("<table width=100%>");
			for (Sequence s : movie.values())
			{
				StringBuilder param = new StringBuilder(SEQUENCES_WIDTH + 16);
				for (int v : new int[]{ s.distance, s.yaw, s.pitch, s.time, s.duration/*, s.turn, s.rise, s.widescreen, s.unknown*/}) {
					int length = param.length();
					param.append(' ').append(v);
					if (param.length() >= SEQUENCES_WIDTH) {
						param.delete(length, param.length());
						break;
					}
				}
				sb.append("<tr><td>Sequence Id: ").append(s.sequenceId).append("<font color=606060>").append(param).append("</font></td></tr>");
			}
			sb.append("</table>"
					+ "<BR>"
					+ "<TABLE width=100%  bgcolor=444444><TR><TD>"
					+ "<table width=150>"
					+ "<tr>"
					+ "<td>Sequence Id:</td>"
					+ "<td height=22><edit var=tsId></td>"
					+ "</tr>"
					+ "</table>"
					+ "<table width=100%>"
					+ "<tr>"
					+ "<td><button value=\"Edit\" width=80 action=\"bypass -h admin_mmaker showModifyWindow $tsId\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
					+ "<td><button value=\"Delete\" width=80 action=\"bypass -h admin_mmaker deleteSequence $tsId\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td><button value=\"Brodcast\" width=80 action=\"bypass -h admin_mmaker brodcast $tsId\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
					+ "<td><button value=\"Play\" width=80 action=\"bypass -h admin_mmaker play $tsId\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
					+ "</tr>"
					+ "<tr><td></td></tr>"
					+ "</table>"
					+ "</TD></TR></TABLE>"
					);
		}
		sb.append("<BR>"
				+ "<center><table width=100%>"
				+ "<tr>"
				+ "<td><button value=\"Add sequence\" width=105 action=\"bypass -h admin_mmaker showNewWindow\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
				+ "</tr>"
				+ "</table></center></body></html>");
		html.setHtml(sb.toString());
		player.sendPacket(html);
	}
	
	protected void showMainWindow(final L2PcInstance player, final long delay)
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable(){@Override public void run() { showMainWindow(player); }}, delay);
	}
	
	private void playSequence(L2PcInstance player, boolean brodcast, int sequenceId)
	{
		Sequence s = getSequence(player, sequenceId);
		if (s != null)
		{
			if (brodcast)
				brodcastCamera(player, s.specialCamera());
			else
				sendCamera(player, s.specialCamera());
			showMainWindow(player, s.duration + DELAY_MENU);
		}
		else
		{
			player.sendMessage("Wrong sequence #" + sequenceId);
			showMainWindow(player);
		}
	}
	
	private void addSequence(L2PcInstance player, int sequenceId, int objectId, int distance, int yaw, int pitch, int time, int duration, int turn, int rise, int screen, int unknown)
	{
		SortedMap<Integer, Sequence> movie = getMovie(player);
		if (movie != null && movie.containsKey(sequenceId))
		{
			player.sendMessage("Sequence #" + sequenceId + " already exists.");
			showNewWindow(player);
		}
		else
		{
			Sequence s = new Sequence();
			s.sequenceId = sequenceId;
			s.objctId = objectId;
			s.distance = distance;
			s.yaw = yaw;
			s.pitch = pitch;
			s.time = time;
			s.duration = duration;
			s.turn = turn;
			s.rise = rise;
			s.widescreen = screen;
			s.unknown = unknown;
			putSequence(player, s);
			showMainWindow(player);
		}
	}
	
	private void showNewWindow(L2PcInstance player)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(5);
		html.setHtml("<html><title>KKnD's Movie Maker</title><body>"
				+ "New Sequence:"
				+ "<center><table cellspacing=0 cellpadding=2 width=200>"
				+ "<tr><td>Sequence Id:</td><td></td><td><edit var=tsId></td></tr>"
				+ "<tr><td>Distance:</td><td></td><td><edit var=tdist></td></tr>"
				+ "<tr><td>Yaw:</td><td></td><td><edit var=tyaw></td></tr>"
				+ "<tr><td>Pitch:</td><td></td><td><edit var=tpitch></td></tr>"
				+ "<tr><td>Time:</td><td></td><td><edit var=ttime></td></tr>"
				+ "<tr><td>Duration:</td><td></td><td><edit var=tdur></td></tr>"
				+ "<tr><td>Turn:</td><td></td><td><edit var=tturn></td></tr>"
				+ "<tr><td>Rise:</td><td></td><td><edit var=trise></td></tr>"
				+ "<tr><td>WideScreen:</td><td></td><td><combobox width=75 var=tscreen list=0;1></td></tr>"
				+ "<tr><td>Unknown:</td><td></td><td><combobox width=75 var=tunk list=0;1></td></tr>"
				+ "<tr><td height=40></td></tr>"
				+ "</table></center>"
				+ "<br>"
				+ "<center><table width=100%>"
				+ "<tr>"
				+ "<td><button value=\"Add\" width=105 action=\"bypass -h admin_mmaker addSequence $tsId $tdist $tyaw $tpitch $ttime $tdur $tturn $trise $tscreen $tunk\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
			//	+ "<td><button value=\"Play\" width=105 action=\"bypass -h admin_mmaker preview * $tdist $tyaw $tpitch $ttime $tdur $tturn $trise $tscreen $tunk\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
				+ "<td><button value=\"Cancel\" width=105 action=\"bypass -h admin_mmaker\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
				+ "</tr>"
				+ "</table></center></body></html>");
		player.sendPacket(html);
	}
	
	private void showModifyWindow(L2PcInstance player, int sequenceId)
	{
		Sequence s = getSequence(player, sequenceId);
		if (s != null)
		{
			NpcHtmlMessage html = new NpcHtmlMessage(5);
			StringBuilder sb = new StringBuilder(2000);
			sb.append("<html><title>KKnD's Movie Maker</title><body>"
					+ "Modify Sequence:"
					+ "<center><table cellspacing=0 cellpadding=2 width=200>"
					+ "<tr><td height=21>Sequence Id:</td><td>").append(s.sequenceId).append("</td></tr>"
					+ "<tr><td height=21>Distance:</td><td>").append(s.distance).append("</td><td><edit var=tdist></td></tr>"
					+ "<tr><td height=21>Yaw:</td><td>").append(s.yaw).append("</td><td><edit var=tyaw></td></tr>"
					+ "<tr><td height=21>Pitch:</td><td>").append(s.pitch).append("</td><td><edit var=tpitch></td></tr>"
					+ "<tr><td height=21>Time:</td><td>").append(s.time).append("</td><td><edit var=ttime></td></tr>"
					+ "<tr><td height=21>Duration:</td><td>").append(s.duration).append("</td><td><edit var=tdur></td></tr>"
					+ "<tr><td height=21>Turn:</td><td>").append(s.turn).append("</td><td><edit var=tturn></td></tr>"
					+ "<tr><td height=21>Rise:</td><td>").append(s.rise).append("</td><td><edit var=trise></td></tr>"
					+ "<tr><td height=21>WideScreen:</td><td>").append(s.widescreen).append("</td><td><combobox width=75 var=tscreen list=").append(s.widescreen).append(";0;1></td></tr>"
					+ "<tr><td height=21>Unknown:</td><td>").append(s.unknown).append("</td><td><combobox width=75 var=tunk list=").append(s.unknown).append(";0;1></td></tr>"
					+ "<tr><td height=40></td></tr>"
					+ "</table></center>"
					+ "<br>"
					+ "<center><table width=100%>"
					+ "<tr>"
					+ "<td><button value=\"Update\" width=105 action=\"bypass -h admin_mmaker updateSequence | ").append(s.sequenceId).append(" | $tdist | $tyaw | $tpitch | $ttime | $tdur | $tturn | $trise | $tscreen | $tunk\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
				//	+ "<td><button value=\"Play\" width=105 action=\"bypass -h admin_mmaker preview * $tdist $tyaw $tpitch $ttime $tdur $tturn $trise $tscreen $tunk\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
					+ "<td><button value=\"Cancel\" width=105 action=\"bypass -h admin_mmaker\" height=21 back=\"L2UI_ct1.button_df_down\" fore=\"L2UI_ct1.button_df\"></td>"
					+ "</tr>"
					+ "</table></center></body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
		}
		else
		{
			player.sendMessage("Sequence #" + sequenceId + " dont exists.");
			showMainWindow(player);
		}
	}
	
	private void updateSequence(L2PcInstance player, int sequenceId, int objectId, String distance, String yaw, String pitch, String time, String duration, String turn, String rise, String screen, String unknown)
	{
		Sequence s = getSequence(player, sequenceId);
		if (s != null)
		{
		/*	s.sequenceId = sequenceId;	*/
			s.objctId = objectId;
			s.distance = parseInt(distance, s.distance);
			s.yaw = parseInt(yaw, s.yaw);
			s.pitch = parseInt(pitch, s.pitch);
			s.time = parseInt(time, s.time);
			s.duration = parseInt(duration, s.duration);
			s.turn = parseInt(turn, s.turn);
			s.rise = parseInt(rise, s.rise);
			s.widescreen = parseInt(screen, s.widescreen);
			s.unknown = parseInt(unknown, s.unknown);
			putSequence(player, s);
			showMainWindow(player);
		}
		else
		{
			player.sendMessage("Sequence #" + sequenceId + " dont exists.");
			showMainWindow(player);
		}
	}
	private int parseInt(String str, int defaultValue)
	{
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	private void deleteSequence(L2PcInstance player, int sequenceId)
	{
		if (removeSequence(player, sequenceId) != null)
		{
			showMainWindow(player);
		}
		else
		{
			player.sendMessage("Sequence #" + sequenceId + " dont exists.");
			showMainWindow(player);
		}
	}
	
	private void playMovie(L2PcInstance player, boolean brodcast)
	{
		SortedMap<Integer, Sequence> movie = getMovie(player);
		if (movie != null && movie.size() > 0)
		{
			//-------------------------------------------------------
			class Play implements Runnable
			{
				final private L2PcInstance player;
				final private boolean brodcast;
				final private Iterator<Sequence> it;
				private int sequenceId;
				Play(L2PcInstance player, boolean brodcast)
				{
					this.brodcast = brodcast;
					this.player = player;
					this.it = getMovie(player).values().iterator();
					this.sequenceId = 0;
				}
				@Override
				public void run()
				{
					if (it.hasNext())
					{
						Sequence s = it.next();
						sequenceId = s.sequenceId;
						if (brodcast)
							brodcastCamera(player, s.specialCamera());
						else
							sendCamera(player, s.specialCamera());
						ThreadPoolManager.getInstance().scheduleGeneral(this, s.duration - 100);
					}
					else
					{
						player.sendMessage("Movie ended on #" + sequenceId + " Sequence.");
						showMainWindow(player, DELAY_MENU);
					}
				}
			}
			//-------------------------------------------------------
			
			ThreadPoolManager.getInstance().scheduleGeneral(new Play(player, brodcast), 500);
		}
		else
		{
			player.sendMessage("There is nothing to play");
			showMainWindow(player);
		}
	}
	
	@SuppressWarnings("unused")/*@IF DEBUG@*/
	protected void brodcastCamera(L2PcInstance player, SpecialCamera packet)
	{
		if (true)
		{
		player.broadcastPacket(packet);
		}
		else
		{
		Collection<L2Object> objs = player.getKnownList().getKnownObjects().values();
		
		for (L2Object object : objs)
		{
			if (object instanceof L2PcInstance)
			{
				((L2PcInstance) object).sendPacket(packet);
			}
		}
		player.sendPacket(packet);
		}
	}
	protected void sendCamera(L2PcInstance player, SpecialCamera packet)
	{
		player.sendPacket(packet);
	}
	
	private void dump(L2PcInstance player)
	{
		SortedMap<Integer, Sequence> movie = getMovie(player);
		if (movie == null || movie.size() == 0) return;
		Sequence[] a = movie.values().toArray(new Sequence[0]);
		System.out.println("/** AdminMovieMaker auto created script. */");
		System.out.println("private class Camera1 implements Runnable {");
		System.out.println(" private int id = " + a[0].sequenceId + ";");
		System.out.println(" public void run() {");
		System.out.println("  switch (id) {");
		for (int i = 0; i < a.length; i++) {
			Sequence s = a[i];
			System.out.println("  case " + s.sequenceId + ":");
			System.out.println("   npc = npc" + s.objctId + ";");
			System.out.println("   npc.broadcastPacket(new SpecialCamera(npc.getObjectId(), " + s.distance + ", " + s.yaw + ", " + s.pitch + ", " + s.time + ", " + s.duration + ", " + s.turn + ", " + s.rise + ", " + s.widescreen + ", " + s.unknown + "));");
			if (i + 1 < a.length) {
				System.out.println("   id = " + a[i + 1].sequenceId + ";");
				System.out.println("   ThreadPoolManager.getInstance().scheduleGeneral(this, " + s.duration + " - 100);");
			}
			System.out.println("   break;");
		}
		System.out.println("  }");
		System.out.println(" }");
		System.out.println("}");
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}