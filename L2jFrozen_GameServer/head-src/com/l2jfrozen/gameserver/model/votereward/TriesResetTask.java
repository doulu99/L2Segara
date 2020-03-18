package com.l2jfrozen.gameserver.model.votereward;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import com.l2jfrozen.gameserver.thread.ThreadPoolManager;
import com.l2jfrozen.util.database.L2DatabaseFactory;

public class TriesResetTask
{	
	public TriesResetTask()
	{
	}
	
	public static void getInstance()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				try (Connection con = L2DatabaseFactory.getInstance().getConnection())
				{
					PreparedStatement statement = con.prepareStatement("UPDATE characters SET tries=?");
					
					statement.setInt(1, 3);
					statement.execute();
					statement.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}, getValidationTime());
	}
	
	private static long getValidationTime()
	{
		Calendar cld = Calendar.getInstance();
		cld.set(11, 12);
		cld.set(12, 1);
		long time = cld.getTimeInMillis();
		if ((System.currentTimeMillis() - time) <= 0L)
		{
			long delay = cld.getTimeInMillis() - System.currentTimeMillis();
			return delay;
		}
		return 0L;
	}
}