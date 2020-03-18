package com.l2jfrozen.gameserver.handler.admincommandhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

import com.l2jfrozen.gameserver.handler.IAdminCommandHandler;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.util.CloseUtil;
import com.l2jfrozen.util.database.L2DatabaseFactory;

/**
 * This class handles following admin commands: - recallparty - recallclan - recallally
 * @author squallcs
 */
public class AdminFarmEvent implements IAdminCommandHandler
{
   private static String[] _adminCommands =
   {
      "admin_addspawn"
   };
   
   private static final String INSERT_SPAWN = "INSERT INTO farmevent (coorX, coorY, coorZ) VALUES (?,?,?)";
   
   @Override
   public boolean useAdminCommand(String command, L2PcInstance activeChar)
   {
      StringTokenizer st = new StringTokenizer(command);
      st.nextToken();
      
      Connection con = null;
      
      if (command.startsWith("admin_addspawn"))
      {
         int _x = activeChar.getX();
         int _y = activeChar.getY();
         int _z = activeChar.getZ();
         
         try
         {
            con = L2DatabaseFactory.getInstance().getConnection(false);
            PreparedStatement statement = con.prepareStatement(INSERT_SPAWN);
            statement.setInt(1, _x);
            statement.setInt(2, _y);
            statement.setInt(3, _z);
            statement.execute();
            statement.close();
            statement = null;
            
         }
         catch (SQLException e)
         {
            e.getStackTrace();
            System.out.println("Failed to add spawn on DB!");
         }
         finally
         {
            CloseUtil.close(con);
         }
         
         activeChar.sendMessage("Spawn added on X:" + _x + " Y: " + _y + " Z: " + _z);
      }
      
      return true;
   }
   
   @Override
   public String[] getAdminCommandList()
   {
      return _adminCommands;
   }
}