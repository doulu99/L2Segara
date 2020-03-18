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
package com.l2jfrozen.gameserver.handler.voicedcommandhandlers;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import com.l2jfrozen.crypt.Base64;
import com.l2jfrozen.util.database.L2DatabaseFactory;
import com.l2jfrozen.gameserver.handler.IVoicedCommandHandler;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;

public class ChangePassword implements IVoicedCommandHandler
{
	private static final String[] _voicedCommands =
	{
		"change"
	};

	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if(command.equalsIgnoreCase("change") && target != null)
		{
			StringTokenizer st = new StringTokenizer(target);
			try
			{
				String curpass = null, newpass = null, repeatnewpass = null;

				if(st.hasMoreTokens())
					curpass = st.nextToken();
				if(st.hasMoreTokens())
					newpass = st.nextToken();
				if(st.hasMoreTokens())
					repeatnewpass = st.nextToken();

				if(!(curpass == null || newpass == null || repeatnewpass == null))
				{
					if(!newpass.equals(repeatnewpass))
					{
						activeChar.sendMessage("The new password doesn't match with the repeated one!");
						return false;
					}
					if(newpass.length() < 3)
					{
						activeChar.sendMessage("The new password is shorter than 3 characters! Please try with a longer one.");
						return false;
					}
					if(newpass.length() > 30)
					{
						activeChar.sendMessage("The new password is longer than 30 characters! Please try with a shorter one.");
						return false;
					}

					MessageDigest md = MessageDigest.getInstance("SHA");

					byte[] raw = curpass.getBytes("UTF-8");
					raw = md.digest(raw);
					String curpassEnc = Base64.encodeBytes(raw);
					String pass = null;
					int passUpdated = 0;

					Connection con = null;
					con = L2DatabaseFactory.getInstance().getConnection();
					PreparedStatement statement = con.prepareStatement("SELECT password FROM accounts WHERE login=?");
					statement.setString(1, activeChar.getAccountName());
					ResultSet rset = statement.executeQuery();

					if(rset.next())
					{
						pass = rset.getString("password");
					}

					rset.close();
					statement.close();

					if(curpassEnc.equals(pass))
					{
						byte[] password = newpass.getBytes("UTF-8");
						password = md.digest(password);

						PreparedStatement ps = con.prepareStatement("UPDATE accounts SET password=? WHERE login=?");
						ps.setString(1, Base64.encodeBytes(password));
						ps.setString(2, activeChar.getAccountName());
						passUpdated = ps.executeUpdate();
						ps.close();
						con.close();
						
						if(passUpdated > 0)
						{
							activeChar.sendMessage("You have successfully changed your password!");
						}
						else
						{
								activeChar.sendMessage("The password change was unsuccessful!");
						}
					}
					else
					{
						activeChar.sendMessage("CurrentPass doesn't match with your current one.");
						return false;
					}
				}
				else
				{
					activeChar.sendMessage("Invalid pass data! Format: .change CurrentPass NewPass NewPass");
					return false;
				}
			}
			catch(Exception e)
			{
				activeChar.sendMessage("A problem occured while changing password!");
			}
		}
		else
		{
			activeChar.sendMessage("To change your current password, you have to type the command in the following format(without the brackets []): [.change CurrentPass NewPass NewPass]. You should also know that the password is case sensitive.");
			return false;
		}

		return true;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}

}