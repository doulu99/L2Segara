package com.l2jfrozen.gameserver.model.votereward;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.model.L2World;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.network.serverpackets.CreatureSay;
import com.l2jfrozen.gameserver.thread.ThreadPoolManager;
import com.l2jfrozen.util.database.L2DatabaseFactory;

/**
 * @Rework Katara
 */
public class VoteMain
{
	private static boolean hasVotedHop;
	private static boolean hasVotedTop;
	static boolean Problemontop = false;
	static boolean Problemonhop = false;
	
	public VoteMain()
	{
	}
	
	public static void load()
	{
		System.out.println("Vote Reward Per Person Started Successfully.");
		TriesResetTask.getInstance();
	}
	
	protected static int getHopZoneVotes()
	{
		URL url = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		try
		{
			url = new URL(Config.VOTE_LINK_HOPZONE);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(false);
			con.setConnectTimeout(10 * 1000);
			con.addRequestProperty("user-agent", "fake googlebot");
			con.connect();
			isr = new InputStreamReader(con.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if (inputLine.contains("rank anonymous tooltip"))
				{
					return Integer.valueOf(inputLine.split(">")[2].replace("</span", ""));
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("[Hopzone-Vote Manager] I can't connect on Hopzone site...");
			Problemonhop = true;
		}
		return 0;
	}
	
	protected static int getTopZoneVotes()
	{
		URL url = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		try
		{
			try
			{
				url = new URL(Config.VOTE_LINK_TOPZONE);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				HttpURLConnection.setFollowRedirects(false);
				con.setConnectTimeout(5 * 1000);
				con.addRequestProperty("User-Agent", "L2TopZone");
				con.connect();
				isr = new InputStreamReader(con.getInputStream());
				in = new BufferedReader(isr);
			}
			catch (SocketTimeoutException e)
			{
				System.out.println("[Topzone-Vote Manager] I can't connect on TOPZONE site...");
				Problemontop = true;
				return 0;
			}
			 
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if (inputLine.contains("Votes:"))
				{
					for (int i=-1; i>2; i++)
						inputLine = in.readLine();

					Problemontop = false;
					return Integer.valueOf(inputLine.split(">")[3].replace("</div", ""));
				}
			}
		}	
	catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("[Topzone-Vote Manager] I can't connect on Topzone site...");
			Problemontop = true;
		}
		return 0;
	}
	
	public static String hopCd(L2PcInstance player)
	{
		long hopCdMs = 0;
		long voteDelay = 43200000L;
		PreparedStatement statement = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement("SELECT lastVoteHopzone FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				hopCdMs = rset.getLong("lastVoteHopzone");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		
		Date resultdate = new Date(hopCdMs + voteDelay);
		return sdf.format(resultdate);
	}
	
	public static String topCd(L2PcInstance player)
	{
		long topCdMs = 0;
		long voteDelay = 43200000L;
		PreparedStatement statement = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement("SELECT lastVoteTopzone FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				topCdMs = rset.getLong("lastVoteTopzone");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		
		Date resultdate = new Date(topCdMs + voteDelay);
		return sdf.format(resultdate);
	}
	
	public static String whosVoting()
	{
		for (L2PcInstance voter : L2World.getInstance().getAllPlayers())
		{
			if (voter.isVoting())
			{
				return voter.getName();
			}
		}
		return "None";
	}
	
	public static void hopvote(final L2PcInstance player)
	{
		long lastVoteHopzone = 0L;
		long voteDelay = 43200000L;
		final int firstvoteshop;
		int votesmessage = 0;
		
		firstvoteshop = getHopZoneVotes();
		votesmessage = getHopZoneVotes();
		
		class hopvotetask implements Runnable
		{
			private final L2PcInstance p;
			
			public hopvotetask(L2PcInstance player)
			{
				p = player;
			}
			
			@Override
			public void run()
			{
				if(Problemonhop == true)
				{
					p.setIsVoting(false);
					VoteMain.setHasVotedHop(p);
					p.sendMessage("Thank you for voting for us!");
					//Announcements.getInstance().gameAnnounceToAll("[Vote Manager] Vote 4 Us on HOPZONE.");
					VoteMain.updateLastVoteHopzone(p);
					Problemonhop = false;
					System.out.println("I have error on topzone but i will give the reward... Please check me katara.");
				}
				else
				{
					if (firstvoteshop < getHopZoneVotes())
					{
						p.setIsVoting(false);
						VoteMain.setHasVotedHop(player);
						p.sendMessage("Thank you for voting for us!");
						//Announcements.getInstance().gameAnnounceToAll("[Vote Manager] Vote 4 Us on HOPZONE.");
						VoteMain.updateLastVoteHopzone(p);
						VoteMain.updateVotes(p);
					}
					else
					{
						p.setIsVoting(false);
						p.sendMessage("You did not vote on hopzone. Please try again later...");
						VoteMain.setTries(player, VoteMain.getTries(p) - 1);
					}
				}
			}
		}
		
		PreparedStatement statement = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement("SELECT lastVoteHopzone FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				lastVoteHopzone = rset.getLong("lastVoteHopzone");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (getTries(player) <= 0)
		{
			player.sendMessage("Due to your multiple failures in voting you lost your chance to vote today");
		}
		else if (((lastVoteHopzone + voteDelay) < System.currentTimeMillis()) && (getTries(player) > 0))
		{
			for (L2PcInstance j : L2World.getInstance().getAllPlayers())
			{
				if (j.isVoting())
				{
					player.sendMessage("Someone is already voting.Wait for your turn please!");
					return;
				}
			}
			
			player.setIsVoting(true);
			//ExShowScreenMessageOnBroad screen = new ExShowScreenMessageOnBroad("GO at Website and vote on HOPZONE, " + player.getName(), 20000, SMPOS.TOP_CENTER, true);
			//player.sendPacket(screen);
			CreatureSay cs = new CreatureSay(player.getObjectId(), 3, "Hopzone system","Current votes are "+votesmessage+", careful with your vote!");
			player.sendPacket(cs);
			player.sendMessage("You have " + Config.SECS_TO_VOTE + " seconds.Hurry!");
			ThreadPoolManager.getInstance().scheduleGeneral(new hopvotetask(player), Config.SECS_TO_VOTE * 1000);
		}
		else if ((getTries(player) <= 0) && ((lastVoteHopzone + voteDelay) < System.currentTimeMillis()))
		{
			for (L2PcInstance j : L2World.getInstance().getAllPlayers())
			{
				if (j.isVoting())
				{
					player.sendMessage("Someone is already voting.Wait for your turn please!");
					return;
				}
			}
			
			player.setIsVoting(true);
			//ExShowScreenMessageOnBroad screen = new ExShowScreenMessageOnBroad("GO at Website and vote on HOPZONE, " + player.getName(), 20000, SMPOS.TOP_CENTER, true);
			//player.sendPacket(screen);
			CreatureSay cs = new CreatureSay(player.getObjectId(), 3, "Hopzone system","Current votes are "+votesmessage+", careful with your vote!");
			player.sendPacket(cs);
			player.sendMessage("You have " + Config.SECS_TO_VOTE + " seconds.Hurry!");
			ThreadPoolManager.getInstance().scheduleGeneral(new hopvotetask(player), Config.SECS_TO_VOTE * 1000);
			
		}
		else
		{
			player.sendMessage("12 hours have to pass till you are able to vote again.");
		}
		
	}
	
	public static void topvote(final L2PcInstance player)
	{
		long lastVoteTopzone = 0L;
		long voteDelay = 43200000L;
		final int firstvotestop;
		int votesmessage = 0;
		
		firstvotestop = getTopZoneVotes();
		votesmessage = getTopZoneVotes();
		
		class topvotetask implements Runnable
		{
			private final L2PcInstance p;
			
			public topvotetask(L2PcInstance player)
			{
				p = player;
			}
			
			@Override
			public void run()
			{
				if(Problemontop == true)
				{
					p.setIsVoting(false);
					VoteMain.setHasVotedTop(p);
					p.sendMessage("Thank you for voting for us!");
					//Announcements.getInstance().gameAnnounceToAll("[Vote Manager] Vote 4 Us on TOPZONE.");
					VoteMain.updateLastVoteTopzone(p);
					Problemontop = false;
					System.out.println("I have error on topzone but i will give the reward... Please check me katara.");
				}
				else
				{
					if (firstvotestop < getTopZoneVotes())
					{
						p.setIsVoting(false);
						VoteMain.setHasVotedTop(p);
						p.sendMessage("Thank you for voting for us!");
						//Announcements.getInstance().gameAnnounceToAll("[Vote Manager] Vote 4 Us on TOPZONE.");
						VoteMain.updateLastVoteTopzone(p);
						VoteMain.updateVotes(p);
					}
					else
					{
						p.setIsVoting(false);
						p.sendMessage("You did not vote on topzone. Please try again later...");
						VoteMain.setTries(p, VoteMain.getTries(p) - 1);
					}
				}
			}
		}
		
		PreparedStatement statement = null;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			statement = con.prepareStatement("SELECT lastVoteTopzone FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				lastVoteTopzone = rset.getLong("lastVoteTopzone");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (getTries(player) <= 0)
		{
			player.sendMessage("Due to your multiple failures in voting you lost your chance to vote today");
		}
		else if ((getTries(player) <= 0) && ((lastVoteTopzone + voteDelay) < System.currentTimeMillis()))
		{
			for (L2PcInstance j : L2World.getInstance().getAllPlayers())
			{
				if (j.isVoting())
				{
					player.sendMessage("Someone is already voting.Wait for your turn please!");
					return;
				}
			}
			player.setIsVoting(true);
			//ExShowScreenMessageOnBroad screen = new ExShowScreenMessageOnBroad("GO at Website and vote on TOPZONE, " + player.getName(), 20000, SMPOS.TOP_CENTER, true);
			//player.sendPacket(screen);
			CreatureSay cs = new CreatureSay(player.getObjectId(), 3, "Topzone system","Current votes are "+votesmessage+", careful with your vote!");
			player.sendPacket(cs);
			player.sendMessage("You have " + Config.SECS_TO_VOTE + " seconds.Hurry!");
			ThreadPoolManager.getInstance().scheduleGeneral(new topvotetask(player), Config.SECS_TO_VOTE * 1000);
		}
		else if (((lastVoteTopzone + voteDelay) < System.currentTimeMillis()) && (getTries(player) > 0))
		{
			for (L2PcInstance j : L2World.getInstance().getAllPlayers())
			{
				if (j.isVoting())
				{
					player.sendMessage("Someone is already voting.Wait for your turn please!");
					return;
				}
			}
			player.setIsVoting(true);
			//ExShowScreenMessageOnBroad screen = new ExShowScreenMessageOnBroad("GO at Website and vote on TOPZONE, " + player.getName(), 20000, SMPOS.TOP_CENTER, true);
			//player.sendPacket(screen);
			CreatureSay cs = new CreatureSay(player.getObjectId(), 3, "Topzone system","Current votes are "+votesmessage+", careful with your vote!");
			player.sendPacket(cs);
			player.sendMessage("You have " + Config.SECS_TO_VOTE + " seconds.Hurry!");
			ThreadPoolManager.getInstance().scheduleGeneral(new topvotetask(player), Config.SECS_TO_VOTE * 1000);
		}
		else
		{
			player.sendMessage("12 hours have to pass till you are able to vote again.");
		}
		
	}
	
	public static void hasVotedHop(L2PcInstance player)
	{
		int hasVotedHop = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT hasVotedHop FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				hasVotedHop = rset.getInt("hasVotedHop");
			}
			
			if (hasVotedHop == 1)
			{
				setHasVotedHop(true);
			}
			else if (hasVotedHop == 0)
			{
				setHasVotedHop(false);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void hasVotedTop(L2PcInstance player)
	{
		int hasVotedTop = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT hasVotedTop FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				hasVotedTop = rset.getInt("hasVotedTop");
			}
			
			if (hasVotedTop == 1)
			{
				setHasVotedTop(true);
			}
			else if (hasVotedTop == 0)
			{
				setHasVotedTop(false);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void updateVotes(L2PcInstance activeChar)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET totalVotes=? WHERE obj_Id=?");

			statement.setInt(1, getTotalVotes(activeChar) + 1);
			statement.setInt(2, activeChar.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setHasVotedHop(L2PcInstance activeChar)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET hasVotedHop=? WHERE obj_Id=?");
			
			statement.setInt(1, 1);
			statement.setInt(2, activeChar.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setHasVotedTop(L2PcInstance activeChar)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET hasVotedTop=? WHERE obj_Id=?");
			statement.setInt(1, 1);
			statement.setInt(2, activeChar.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
	}
	}
	
	public static void setHasNotVotedHop(L2PcInstance activeChar)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET hasVotedHop=? WHERE obj_Id=?");
			statement.setInt(1, 0);
			statement.setInt(2, activeChar.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setHasNotVotedTop(L2PcInstance activeChar)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET hasVotedTop=? WHERE obj_Id=?");
			statement.setInt(1, 0);
			statement.setInt(2, activeChar.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static int getTries(L2PcInstance player)
	{
		int tries = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT tries FROM characters WHERE obj_Id=?");
		 statement.setInt(1, player.getObjectId());
		 for (ResultSet rset = statement.executeQuery(); rset.next();)
			{
				tries = rset.getInt("tries");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tries;
	}
	
	public static void setTries(L2PcInstance player, int tries)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET tries=? WHERE obj_Id=?");
			statement.setInt(1, tries);
			statement.setInt(2, player.getObjectId());
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static int getTotalVotes(L2PcInstance player)
	{
		int totalVotes = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT totalVotes FROM characters WHERE obj_Id=?");
			statement.setInt(1, player.getObjectId());
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{	
				totalVotes = rset.getInt("totalVotes");
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return totalVotes;
	}
	
	public static int getBigTotalVotes(L2PcInstance player)
	{
		int bigTotalVotes = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("SELECT SUM(totalVotes) FROM characters");
			for (ResultSet rset = statement.executeQuery(); rset.next();)
			{
				bigTotalVotes = rset.getInt("SUM(totalVotes)");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return bigTotalVotes;
	}
	
	public static void updateLastVoteHopzone(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET lastVoteHopzone=? WHERE obj_Id=?");
			statement.setLong(1, System.currentTimeMillis());
			statement.setInt(2, player.getObjectId());
			statement.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void updateLastVoteTopzone(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement statement = con.prepareStatement("UPDATE characters SET lastVoteTopzone=? WHERE obj_Id=?");
			statement.setLong(1, System.currentTimeMillis());
			statement.setInt(2, player.getObjectId());
			statement.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Getters and Setters
	public static boolean hasVotedHop()
	{
		return hasVotedHop;
	}
	
	public static void setHasVotedHop(boolean hasVotedHop)
	{
		VoteMain.hasVotedHop = hasVotedHop;
	}
	
	public static boolean hasVotedTop()
	{
		return hasVotedTop;
	}
	
	public static void setHasVotedTop(boolean hasVotedTop)
	{
		VoteMain.hasVotedTop = hasVotedTop;
	}
}