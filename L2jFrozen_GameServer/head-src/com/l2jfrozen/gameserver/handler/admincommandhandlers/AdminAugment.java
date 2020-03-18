package com.l2jfrozen.gameserver.handler.admincommandhandlers;

import com.l2jfrozen.gameserver.datatables.SkillTable;
import com.l2jfrozen.gameserver.handler.IAdminCommandHandler;
import com.l2jfrozen.gameserver.model.L2Object;
import com.l2jfrozen.gameserver.model.actor.instance.L2ItemInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.model.entity.AugmentDatabase;
import com.l2jfrozen.gameserver.network.SystemMessageId;
import com.l2jfrozen.util.random.Rnd;
import java.util.StringTokenizer;

public class AdminAugment
  implements IAdminCommandHandler
{
  private static final String[] ADMIN_COMMANDS = { "admin_augment" };
  
@Override
public boolean useAdminCommand(String command, L2PcInstance activeChar)
  {
    if (command.startsWith("admin_augment"))
    {
      StringTokenizer st = new StringTokenizer(command);
      st.nextToken();
      try
      {
        String type = st.nextToken();
        if (type.startsWith("weapon"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(7) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a weapon.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(7).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The weapon is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentweapondatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("legs"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(11) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Legs.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(11).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Legs is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentlegsdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("chest"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(10) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Chest.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(10).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Chest is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentchestdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("helmet"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(6) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Helmet.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(6).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Helmet is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmenthelmentdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("gloves"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(9) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Gloves.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(9).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Gloves is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentglovesdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("boots"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(12) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Boots.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(12).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Boots is already Augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentbootsdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("underwear"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(0) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Underwear.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(0).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Underwear is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentunderweardatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("rring"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(5) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a R-Ring.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(5).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The R-Ring is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentrringdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("lring"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(4) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a L-Ring.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(4).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The L-Ring is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentlringdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("necklace"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(3) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Necklace.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(3).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Necklace is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentneklacedatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("rearring"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(2) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a R-Earring.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(2).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The R-Earring is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentrearringdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("learring"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(1) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a L-Earring.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(1).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The L-Earring is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentlearringdatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("shield"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(8) == null) {
              activeChar.sendMessage(activeChar.getTarget().getName() + " have to equip a Shield.");
            }
            if (activeChar.getTarget().getActingPlayer().getInventory().getPaperdollItem(8).isAugmented())
            {
              activeChar.sendMessage(activeChar.getTarget().getName() + " The Shield is already augmented.");
            }
            else
            {
              int id = Integer.parseInt(st.nextToken());
              int level = Integer.parseInt(st.nextToken());
              int attributes = Rnd.get(12177);
              AugmentDatabase.augmentshielddatabase(activeChar.getTarget().getActingPlayer(), attributes, id, level);
              activeChar.getTarget().getActingPlayer().sendMessage("Successfully To Add " + SkillTable.getInstance().getInfo(id, level).getName() + " By " + activeChar.getName() + ".");
            }
            activeChar = (L2PcInstance)target;
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
        else if (type.startsWith("rminfo"))
        {
          AugmentDatabase.HtmAugment2(activeChar);
        }
        else if (type.startsWith("remove"))
        {
          L2Object target = activeChar.getTarget();
          if ((target instanceof L2PcInstance))
          {
            L2ItemInstance item = null;
            int items = Integer.parseInt(st.nextToken());
            switch (items)
            {
            case 1: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(10);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 2: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(11);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 3: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(9);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 4: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(12);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 5: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(6);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 6: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(4);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 7: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(5);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 8: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(1);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 9: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(2);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 10: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(3);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 11: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(7);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 12: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(8);
              AugmentDatabase.augmentremove(item, activeChar);
              break;
            case 13: 
              activeChar = (L2PcInstance)target;
              item = activeChar.getInventory().getPaperdollItem(0);
              AugmentDatabase.augmentremove(item, activeChar);
            }
          }
          else
          {
            activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
            return false;
          }
        }
      }
      catch (Exception e)
      {
        AugmentDatabase.HtmAugment(activeChar);
      }
    }
    return false;
  }
  
  @Override
public String[] getAdminCommandList()
  {
    return ADMIN_COMMANDS;
  }
}