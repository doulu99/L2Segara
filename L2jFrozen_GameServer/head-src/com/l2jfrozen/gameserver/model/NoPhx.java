package com.l2jfrozen.gameserver.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;


import com.l2jfrozen.loginserver.BruteProtector;
import com.l2jfrozen.util.CloseUtil;
import com.l2jfrozen.util.database.L2DatabaseFactory;
/**
 * @author InCube
 * @version 0.0.1 (1.0.0+ is stable.)
 */
/*
 * How to use:
 * hasItem(PLAYER ID, ITEM ID) - This checks if player actually has the item, since L2PHX is aimed to dupe the item and fake it from l2jfrozen TMP variables (l2jfrozen uses tmp variables and not live variables!)
 * So when a user deletes item you should: if(canRemoveItem(ITEM ID, PLAYER ID) > 0) { .. you can remove the item } else { L2PHX detected }
 */
public class NoPhx {

   protected static final Logger _log = Logger.getLogger(BruteProtector.class.getName());   
   /**
    *
    * @param playerId
    * @param itemId
    * @return TOTAL Item count
    * @description Check if player has the item and how much of it.
    */
   public int hasItem(int playerId, int itemId) {

      Connection con = null;
      try{
         con = L2DatabaseFactory.getInstance().getConnection(false);
         PreparedStatement statement = con.prepareStatement("SELECT count FROM items WHERE owner_id = ? AND item_id = ?");

         statement.setInt(1, playerId);
         statement.setInt(2, itemId);

         ResultSet rset = statement.executeQuery();
         if(rset.next())
         {
            return rset.getInt(1);
         }

         rset.close();
         statement.close();
         statement = null;
         rset = null;
         
         
      }catch(Exception ex){
         _log.warning("Could not get "+itemId+" item data for userId: "+ playerId +" " + ex);
         return 0;
      }
      finally
      {
         CloseUtil.close(con);
         con = null;
      }
      return 0;
   }
   /**
    *
    * @param itemId
    * @param playerId
    * @param count
    * @return Database value - count to remove
    */
   public int canRemoveItem(int itemId, int playerId, int count) {
      
      int totalItemCount = hasItem(playerId, itemId);
      return totalItemCount - count;
      
   }
}