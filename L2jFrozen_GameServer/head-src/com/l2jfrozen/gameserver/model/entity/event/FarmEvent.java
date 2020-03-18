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
package com.l2jfrozen.gameserver.model.entity.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.l2jfrozen.gameserver.datatables.sql.NpcTable;
import com.l2jfrozen.gameserver.datatables.sql.SpawnTable;
import com.l2jfrozen.gameserver.model.entity.Announcements;
import com.l2jfrozen.gameserver.model.entity.event.manager.EventManager;
import com.l2jfrozen.gameserver.model.spawn.L2Spawn;
import com.l2jfrozen.gameserver.network.serverpackets.MagicSkillUser;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;
import com.l2jfrozen.util.CloseUtil;
import com.l2jfrozen.util.database.L2DatabaseFactory;

/**
 * @author squallcs
 */
public class FarmEvent extends Thread
{
   
   protected static final Logger _log = Logger.getLogger(FarmEvent.class.getName());
   private L2Spawn _gatekeeperSpawn;
   private L2Spawn _gatekeeperSpawn2;
   private L2Spawn _Raidboss;
   @SuppressWarnings("unused")
private ArrayList<L2Spawn> _monsterSpawn = new ArrayList<L2Spawn>();
   
   @SuppressWarnings("unused")
private ArrayList<Integer> _mobsX = new ArrayList<Integer>();
   @SuppressWarnings("unused")
private ArrayList<Integer> _mobsY = new ArrayList<Integer>();
   @SuppressWarnings("unused")
private ArrayList<Integer> _mobsZ = new ArrayList<Integer>();
   
   private static final String GET_COORDINATES = "SELECT coorX, coorY, coorZ FROM farmevent";
   
   // Set of coordinates both gatekeeper and monster
   private int _gatekeeperId, _gatekeeperX, _gatekeeperY, _gatekeeperZ, _gatekeeperId2, _gatekeeperX2, _gatekeeperY2, _gatekeeperZ2, _RaidbossId, _RaidbossX, _RaidbossY, _RaidbossZ, _monsterId, _spawnSize;
   
   // Description, name and blablabla
   private int _eventDuration, _eventInterval, _delayFirstTime;
   private static String _eventName;
   private String _eventDescription;
   @SuppressWarnings("unused")
private String _gatekeeperLocation;
   
   // Main checks
   private boolean _inProgress, isInitiated = false;
   
   // Load coordinates from DB
   
   @SuppressWarnings("null")
   public void loadData()
   {
      Connection con = null;
      try
      {
         con = L2DatabaseFactory.getInstance().getConnection(false);
         PreparedStatement statement = con.prepareStatement(GET_COORDINATES);
         ResultSet rs = statement.executeQuery();
         if (rs != null)
         {
            rs.beforeFirst();
            rs.last();
            _spawnSize = rs.getRow();
            rs.beforeFirst();
         }
         while (rs != null && rs.next())
         {
            _mobsX.add(rs.getInt("coorX"));
            _mobsY.add(rs.getInt("coorY"));
            _mobsZ.add(rs.getInt("coorZ"));
         }
         rs.close();
         statement.close();
      }
      catch (SQLException e)
      {
         System.out.println(e.getStackTrace());
         System.out.println("Failed to load data from DB!");
      }
      finally
      {
         CloseUtil.close(con);
      }
   }
   
   // Spawn NPCs
   public void spawnNpcs()
   {
      for (int i = 0; i < _mobsX.size(); i++)
      {
      //   System.out.print(" [Farm Event]: has starting now for one hours! ");
      }
      
      L2NpcTemplate gkpr = NpcTable.getInstance().getTemplate(_gatekeeperId);
      L2NpcTemplate gkpr2 = NpcTable.getInstance().getTemplate(_gatekeeperId2);
      L2NpcTemplate rbsp = NpcTable.getInstance().getTemplate(_RaidbossId);
      L2NpcTemplate mnstr = NpcTable.getInstance().getTemplate(_monsterId);
      try
      {
         _gatekeeperSpawn = new L2Spawn(gkpr);
         _gatekeeperSpawn.setLocx(_gatekeeperX);
         _gatekeeperSpawn.setLocy(_gatekeeperY);
         _gatekeeperSpawn.setLocz(_gatekeeperZ);
         _gatekeeperSpawn.setAmount(1);
         SpawnTable.getInstance().addNewSpawn(_gatekeeperSpawn, false);
         _gatekeeperSpawn.init();
         _gatekeeperSpawn.getLastSpawn().getStatus().setCurrentHp(99999999);
         _gatekeeperSpawn.getLastSpawn().setTitle(_eventName);
         _gatekeeperSpawn.getLastSpawn().isAggressive();
         _gatekeeperSpawn.getLastSpawn().spawnMe(_gatekeeperSpawn.getLastSpawn().getX(), _gatekeeperSpawn.getLastSpawn().getY(), _gatekeeperSpawn.getLastSpawn().getZ());
         _gatekeeperSpawn.getLastSpawn().broadcastPacket(new MagicSkillUser(_gatekeeperSpawn.getLastSpawn(), _gatekeeperSpawn.getLastSpawn(), 1034, 1, 1, 1));
         
         _gatekeeperSpawn2 = new L2Spawn(gkpr2);
         _gatekeeperSpawn2.setLocx(_gatekeeperX2);
         _gatekeeperSpawn2.setLocy(_gatekeeperY2);
         _gatekeeperSpawn2.setLocz(_gatekeeperZ2);
         _gatekeeperSpawn2.setAmount(1);
         SpawnTable.getInstance().addNewSpawn(_gatekeeperSpawn2, false);
         _gatekeeperSpawn2.init();
         _gatekeeperSpawn2.getLastSpawn().getStatus().setCurrentHp(99999999);
         _gatekeeperSpawn2.getLastSpawn().setTitle(_eventName);
         _gatekeeperSpawn2.getLastSpawn().isAggressive();
         _gatekeeperSpawn2.getLastSpawn().spawnMe(_gatekeeperSpawn2.getLastSpawn().getX(), _gatekeeperSpawn2.getLastSpawn().getY(), _gatekeeperSpawn2.getLastSpawn().getZ());
         _gatekeeperSpawn2.getLastSpawn().broadcastPacket(new MagicSkillUser(_gatekeeperSpawn2.getLastSpawn(), _gatekeeperSpawn2.getLastSpawn(), 1034, 1, 1, 1));
         
         
         // raid boss spawn location
         _Raidboss = new L2Spawn(rbsp);
         _Raidboss.setLocx(_RaidbossX);
         _Raidboss.setLocy(_RaidbossY);
         _Raidboss.setLocz(_RaidbossZ);
         SpawnTable.getInstance().addNewSpawn(_Raidboss, false);
         _Raidboss.init();
         _Raidboss.doSpawn();
      // _Raidboss.getLastSpawn().spawnMe(_Raidboss.getLastSpawn().getX(), _Raidboss.getLastSpawn().getY(), _Raidboss.getLastSpawn().getZ());
         

         for (int i = 0; i < _spawnSize; i++)
         {
            _monsterSpawn.add(i, new L2Spawn(mnstr));
            _monsterSpawn.get(i).setLocx(_mobsX.get(i));
            _monsterSpawn.get(i).setLocy(_mobsY.get(i));
            _monsterSpawn.get(i).setLocz(_mobsZ.get(i));
            _monsterSpawn.get(i).setAmount(1);
            _monsterSpawn.get(i).setHeading(0);
            _monsterSpawn.get(i).setRespawnDelay(1);
            _monsterSpawn.get(i).init();
            _monsterSpawn.get(i).doSpawn();
            SpawnTable.getInstance().addNewSpawn(_monsterSpawn.get(i), false);
         }
     
        Announcements.getInstance().announceToAll("[Farm Event]" + _eventDescription);
        //Announcements.getInstance().announceToAll("Use the gatekeeper located in " + _gatekeeperLocation + " to teleport you.");
         
      }
      catch (Exception e)
      {
         System.out.println(e.getStackTrace());
         System.out.println("Failed to spawn NPCs");
      }
   }
   
   // Unspawn NPCs
   public void unspawnNpcs()
   {
      if (_gatekeeperSpawn == null || _gatekeeperSpawn.getLastSpawn() == null || _Raidboss == null || _Raidboss.getLastSpawn() == null || _gatekeeperSpawn2 == null || _gatekeeperSpawn2.getLastSpawn() == null || _monsterSpawn.isEmpty())
         return;
      
      _gatekeeperSpawn.getLastSpawn().deleteMe();
      _gatekeeperSpawn.stopRespawn();
      SpawnTable.getInstance().deleteSpawn(_gatekeeperSpawn, true);
      
      // raid boss spawn location
      
      _Raidboss.getLastSpawn().deleteMe();
      _Raidboss.stopRespawn();
      _Raidboss.setAmount(0);
      _Raidboss.setHeading(0);
      _Raidboss.setRespawnDelay(0);
      SpawnTable.getInstance().deleteSpawn(_Raidboss, true);
      
      _gatekeeperSpawn2.getLastSpawn().deleteMe();
      _gatekeeperSpawn2.stopRespawn();
      SpawnTable.getInstance().deleteSpawn(_gatekeeperSpawn2, true);
      
      for (int i = 0; i < _spawnSize; i++)
      {
         _monsterSpawn.get(i).getLastSpawn().deleteMe();
         _monsterSpawn.get(i).stopRespawn();
         _monsterSpawn.get(i).setAmount(0);
         _monsterSpawn.get(i).setHeading(0);
         _monsterSpawn.get(i).setRespawnDelay(0);
         L2Spawn aux = _monsterSpawn.get(i);
         SpawnTable.getInstance().deleteSpawn(aux, true);
      }
      
      Announcements.getInstance().announceToAll("[Farm Event] Event has end!");
      Announcements.getInstance().announceToAll("[Farm Event] pleace soe to Town!");
      Announcements.getInstance().announceToAll("[Farm Event] Event has Starting again after 5 hours!");
   }
   
// Run task
   @Override
   public void run()
   {
      int counter = 0;
      loadData();
      while (EventManager.FARM_EVENT_ENABLED)
      {
         if (!isInitiated)
         {
            try
            {
               Thread.sleep(_delayFirstTime * 60 * 1000);
               
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            spawnNpcs();
            isInitiated = true;
            _inProgress = true;
            while (counter < _eventDuration * 60)
            {
               try
               {
                  Thread.sleep(1000);
               }
               catch (InterruptedException e)
               {
                  e.printStackTrace();
               }
               counter++;
            }
            unspawnNpcs();
            _inProgress = false;
            counter = 0;
         }
         
         if (isInitiated && !_inProgress)
         {
            try
            {
               Thread.sleep(_eventInterval * 1000 * 60);
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
            spawnNpcs();
            _inProgress = true;
            
            while (counter < _eventDuration * 60)
            {
               try
               {
                  Thread.sleep(1000);
               }
               catch (InterruptedException e)
               {
                  e.printStackTrace();
               }
               counter++;
            }
         }
         _inProgress = false;
         counter = 0;
         unspawnNpcs();
      }
   }
   
   /**
    * Instantiates a new FarmEvent.
    */
   private FarmEvent()
   {
   }
   
   /**
    * @return the new instance
    */
   public static FarmEvent getNewInstance()
   {
      return new FarmEvent();
   }
   
   /**
    * @param _gatekeeperId the _gatekeeperId to set
    */
   public void set_gatekeeperId(int _gatekeeperId)
   {
      this._gatekeeperId = _gatekeeperId;
   }
   
   /**
    * @param _gatekeeperX the _gatekeeperX to set
    */
   public void set_gatekeeperX(int _gatekeeperX)
   {
      this._gatekeeperX = _gatekeeperX;
   }

   /**
    * @param _gatekeeperY the _gatekeeperY to set
    */
   public void set_gatekeeperY(int _gatekeeperY)
   {
      this._gatekeeperY = _gatekeeperY;
   }
   
   /**
    * @param _gatekeeperZ the _gatekeeperZ to set
    */
   public void set_gatekeeperZ(int _gatekeeperZ)
   {
      this._gatekeeperZ = _gatekeeperZ;
   }   
   /**
    * 
    * @param _gatekeeperId2 this spawn two gatekeeper
    */
   public void set_gatekeeperId2(int _gatekeeperId2)
   {
      this._gatekeeperId2 = _gatekeeperId2;
   }
   
   /**
    * 
    * @param _gatekeeperX2 spawn location two gatekeeper
    */
   
   public void set_gatekeeperX2(int _gatekeeperX2)
   {
      this._gatekeeperX2 = _gatekeeperX2;
   }
   
   /**
 * @param _gatekeeperY2 
    */
   public void set_gatekeeperY2(int _gatekeeperY2)
   {
      this._gatekeeperY2 = _gatekeeperY2;
   }
   
   /**
 * @param _gatekeeperZ2 
    */
   public void set_gatekeeperZ2(int _gatekeeperZ2)
   {
      this._gatekeeperZ2 = _gatekeeperZ2;
   }
   
   
   /**
    * 
    * @param _RaidbossId 
    */
   public void set_RaidbossId(int _RaidbossId)
   {
      this._RaidbossId = _RaidbossId;
   }
   
   /**
    * 
    * @param _RaidbossX 
    */
   
   public void set_RaidbossX(int _RaidbossX)
   {
      this._RaidbossX = _RaidbossX;
   }
   
   /**
    * @param _RaidbossY 
    */
   public void set_RaidbossY(int _RaidbossY)
   {
      this._RaidbossY = _RaidbossY;
   }
   
   /**
    * @param _RaidbossZ 
    */
   public void set_RaidbossZ(int _RaidbossZ)
   {
      this._RaidbossZ = _RaidbossZ;
   }
   
   /**
    * @param _monsterId the _monsterId to set
    */
   public void set_monsterId(int _monsterId)
   {
      this._monsterId = _monsterId;
   }
   
   /**
    * @param _eventDuration the _eventDuration to set
    */
   public void set_eventDuration(int _eventDuration)
   {
      this._eventDuration = _eventDuration;
   }
   
   /**
    * @param _eventName the _eventName to set
    */
   public void set_eventName(String _eventName)
   {
      FarmEvent._eventName = _eventName;
   }
   
   /**
    * @param _eventDescription the _eventDescription to set
    */
   public void set_eventDescription(String _eventDescription)
   {
      this._eventDescription = _eventDescription;
   }
   
   /**
    * @param _gatekeeperLocation the _gatekeeperLocation to set
    */
   public void set_gatekeeperLocation(String _gatekeeperLocation)
   {
      this._gatekeeperLocation = _gatekeeperLocation;
   }
   
   /**
    * @return the _eventName
    */
   public static String get_eventName()
   {
      return _eventName;
   }
   
   /**
    * @param _eventInterval the _eventInterval to set
    */
   public void set_eventInterval(int _eventInterval)
   {
      this._eventInterval = _eventInterval;
   }
   
   /**
    * @param _delayFirstTime the _delayFirstTime to set
    */
   public void set_delayFirstTime(int _delayFirstTime)
   {
      this._delayFirstTime = _delayFirstTime;
   }
   
}