package com.l2jfrozen.gameserver.handler.admincommandhandlers;

import com.l2jfrozen.gameserver.handler.IAdminCommandHandler;
import com.l2jfrozen.gameserver.model.L2Object;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;

/**
 *
 * @author Elfocrash
 */
public class AdminSetTeam implements IAdminCommandHandler
{
   private static String[] _adminCommands =
   {
         "admin_setteam1", "admin_setteam2", "admin_clearteam"
   };

   @SuppressWarnings("unused")
@Override
   public boolean useAdminCommand(String command, L2PcInstance activeChar)
   {

      L2Object target = activeChar.getTarget();

      if(activeChar.getTarget() instanceof L2PcInstance)
      {
      
      
      if(command.startsWith("admin_setteam1"))
      {
         ((L2PcInstance) activeChar.getTarget()).setTeam(1);
         ((L2PcInstance) activeChar.getTarget()).sendMessage(activeChar.getName() + " selected you to be in the blue team");
         ((L2PcInstance) activeChar.getTarget()).broadcastUserInfo();
      }
      else if(command.startsWith("admin_setteam2"))
      {
         ((L2PcInstance) activeChar.getTarget()).setTeam(2);
         ((L2PcInstance) activeChar.getTarget()).sendMessage(activeChar.getName() + " selected you to be in the red team");
         ((L2PcInstance) activeChar.getTarget()).broadcastUserInfo();
      }
      else if(command.startsWith("admin_clearteam"))
      {
         ((L2PcInstance) activeChar.getTarget()).setTeam(0);
         ((L2PcInstance) activeChar.getTarget()).sendMessage("Your team has been cleared from " + activeChar.getName());
         ((L2PcInstance) activeChar.getTarget()).broadcastUserInfo();
      }
      }
      else
      {
         activeChar.sendMessage("Hey!! This is only for players!");
         return false;
      }
      return true;
   }



   @Override
   public String[] getAdminCommandList()
   {
      return _adminCommands;
   }
}