package com.l2jfrozen.gameserver.model.entity;

import com.l2jfrozen.gameserver.datatables.SkillTable;
import com.l2jfrozen.gameserver.model.L2Augmentation;
import com.l2jfrozen.gameserver.model.actor.instance.L2ItemInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.util.database.L2DatabaseFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javolution.text.TextBuilder;

public class AugmentDatabase
{
  public static void HtmAugment(L2PcInstance player)
  {
    NpcHtmlMessage nhm = new NpcHtmlMessage(5);
    TextBuilder tb = new TextBuilder("");
    tb.append("<html><title>Augmenter Panel<body>");
    tb.append("<center><font color=\"FF0000\">Select to add Augment Skill</font></center><br>");
    tb.append("<table><tr>");
    tb.append("<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>");
    tb.append("<td width=100>Skill Id:<edit var=\"id\" width=40></td>");
    tb.append("<td width=100>Skill Level:<edit var=\"lvl\" width=40></td>");
    tb.append("<td width=100>Remove Id:<edit var=\"rm\" width=40></td>");
    tb.append("</table></tr><br>");
    tb.append("<table><tr>");
    tb.append("<td align=center><button value=\"Augment Remove Info\" action=\"bypass -h admin_augment rminfo\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment Remove\" action=\"bypass -h admin_augment remove $rm\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment Skill Info\" action=\"bypass -h admin_augment skillinfo\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("</table></tr><br>");
    tb.append("<table><tr>");
    tb.append("<td align=center><button value=\"Augment Helmet\" action=\"bypass -h admin_augment helment $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment Chest\" action=\"bypass -h admin_augment chest $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment Leggings\" action=\"bypass -h admin_augment legs $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("</table></tr><br>");
    tb.append("<table><tr>");
    tb.append("<td align=center><button value=\"Augment Gloves\" action=\"bypass -h admin_augment gloves $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment Boots\" action=\"bypass -h admin_augment boots $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment Underwear\" action=\"bypass -h admin_augment underwear $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("</table></tr><br>");
    tb.append("<table><tr>");
    tb.append("<td align=center><button value=\"Augment Weapon\" action=\"bypass -h admin_augment weapon $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment Necklace\" action=\"bypass -h admin_augment necklace $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment L-Earring\" action=\"bypass -h admin_augment learring $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("</table></tr><br>");
    tb.append("<table><tr>");
    tb.append("<td align=center><button value=\"Augment R-Ring\" action=\"bypass -h admin_augment rring $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment L-Ring\" action=\"bypass -h admin_augment lring $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("<td align=center><button value=\"Augment R-Earring\" action=\"bypass -h admin_augment rearring $id $lvl\" width=95 height=21 back=\"L2UI_ch3.bigbutton_over\" fore=\"L2UI_ch3.bigbutton\"></td>");
    tb.append("</table></tr>");
    tb.append("</body></html>");
    nhm.setHtml(tb.toString());
    player.sendPacket(nhm);
  }
  
  public static void HtmAugment2(L2PcInstance player)
  {
    NpcHtmlMessage nhm = new NpcHtmlMessage(5);
    TextBuilder tb = new TextBuilder("");
    tb.append("<html><body><center>");
    tb.append("<font color=\"FF0000\">Augment Remove ID Info</font><br><br>");
    tb.append("<font color=\"FFFF00\">Chest Remove Id:</font>1<br>");
    tb.append("<font color=\"FFFF00\">Leggings Remove Id:</font>2<br>");
    tb.append("<font color=\"FFFF00\">Gloves Remove Id:</font>3<br>");
    tb.append("<font color=\"FFFF00\">Boots Remove Id:</font>4<br>");
    tb.append("<font color=\"FFFF00\">Helmet Remove Id:</font>5<br>");
    tb.append("<font color=\"FFFF00\">L-Ring Remove Id:</font>6<br>");
    tb.append("<font color=\"FFFF00\">R-Ring Remove Id:</font>7<br>");
    tb.append("<font color=\"FFFF00\">L-Earring Remove Id:</font>8<br>");
    tb.append("<font color=\"FFFF00\">R-Earring Remove Id:</font>9<br>");
    tb.append("<font color=\"FFFF00\">Necklace Remove Id:</font>10<br>");
    tb.append("<font color=\"FFFF00\">Weapon Remove Id:</font>11<br>");
    tb.append("<font color=\"FFFF00\">Shield Remove Id:</font>12<br>");
    tb.append("<font color=\"FFFF00\">UnderWear Remove Id:</font>13</center>");
    tb.append("</body></html>");
    nhm.setHtml(tb.toString());
    player.sendPacket(nhm);
  }
  
  public static void augmentweapondatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(7);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  @SuppressWarnings("null")
public static void augmentremove(L2ItemInstance item, L2PcInstance activeChar)
  {
    if (item.isAugmented())
    {
      item.getAugmentation().removeBoni(activeChar);
      activeChar.sendMessage("Remove " + SkillTable.getInstance().getInfo(item.getAugmentation().getSkill().getId(), item.getAugmentation().getSkill().getLevel()).getName() + " Completed From " + item.getName() + ".");
      item.removeAugmentation();
    }
    if (item != null)
    {
      L2ItemInstance[] unequipped = activeChar.getInventory().unEquipItemInBodySlotAndRecord(item.getItem().getBodyPart());
      InventoryUpdate iu = new InventoryUpdate();
      for (L2ItemInstance element : unequipped) {
        iu.addModifiedItem(element);
      }
      activeChar.sendPacket(iu);
    }
  }
  
  public static void augmenthelmentdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(6);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentlegsdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(11);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentchestdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(10);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("INSERT INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentshielddatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(8);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentglovesdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(9);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentbootsdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(12);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentunderweardatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(0);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentneklacedatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(3);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentrringdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(5);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentlringdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(4);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentrearringdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(2);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
  
  public static void augmentlearringdatabase(L2PcInstance player, int attributes, int id, int level)
  {
    L2ItemInstance item = player.getInventory().getPaperdollItem(1);
    L2Augmentation augmentation = new L2Augmentation(item, attributes * 65536 + 1, id, level, false);
    augmentation.applyBoni(player);
    item.setAugmentation(augmentation);
    try
    {
      Connection con = L2DatabaseFactory.getInstance().getConnection();Throwable localThrowable2 = null;
      try
      {
        PreparedStatement statement = con.prepareStatement("REPLACE INTO augmentations VALUES(?,?,?,?)");
        statement.setInt(1, item.getObjectId());
        statement.setInt(2, attributes * 65534 + 1);
        statement.setInt(3, id);
        statement.setInt(4, level);
        InventoryUpdate iu = new InventoryUpdate();
        player.sendPacket(iu);
        statement.executeUpdate();
        statement.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (con != null) {
          if (localThrowable2 != null) {
            try
            {
              con.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            con.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println(e);
    }
  }
}