package com.l2jfrozen.gameserver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javolution.text.TextBuilder;
import com.l2jfrozen.util.database.L2DatabaseFactory;

public class RankingOnlineManager
{
	private int _posId;

	private TextBuilder _playerList = new TextBuilder();

	public RankingOnlineManager()
	{
		loadFromDB();
	}

	@SuppressWarnings("null")
	public void loadFromDB()
	{
		Connection con = null;
		try
		{
			_posId = 0;
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT char_name, online, OnlineTime FROM characters WHERE accesslevel=0 ORDER BY OnlineTime DESC LIMIT 10");

			ResultSet result = statement.executeQuery();

			while (result.next())
			{
			_posId = _posId + 1;

				addPlayerToList(_posId, result.getString("char_name"),
						result.getInt("online"),
						result.getInt("OnlineTime"));
			}

			result.close();
			statement.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				con.close();
			} catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}

	public String loadPlayerOnline()
	{
		return _playerList.toString();
	}

	private void addPlayerToList(int objId, String name, int online, int OnlineTime)
	{
		_playerList.append("<table width=350>");
		_playerList.append("<tr>");
		_playerList.append("<td></td>");
		_playerList.append("<td FIXWIDTH=22>" + objId + ".</td>");
		_playerList.append("<td FIXWIDTH=48>" + name + "</td>");
		_playerList.append("<td FIXWIDTH=34>" + OnlineTime + "</td>");
		if (online == 1)
		_playerList.append("<td FIXWIDTH=27><font color=00FF00>ON</font></td>");
		else
		_playerList.append("<td FIXWIDTH=27><font color=FF0000>OFF</font></td>");
		_playerList.append("</tr>");
		_playerList.append("</table>");
		_playerList.append("<img src=\"L2UI.Squaregray\" width=\"300\" height=\"1\">");
	}
}