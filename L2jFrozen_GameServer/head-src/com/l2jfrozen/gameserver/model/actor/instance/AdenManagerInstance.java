package com.l2jfrozen.gameserver.model.actor.instance;

import com.l2jfrozen.gameserver.ai.CtrlIntention;
import com.l2jfrozen.gameserver.managers.CastleManager;
import com.l2jfrozen.gameserver.model.entity.siege.Castle;
import com.l2jfrozen.gameserver.network.serverpackets.ActionFailed;
import com.l2jfrozen.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jfrozen.gameserver.network.serverpackets.SiegeInfo;
import com.l2jfrozen.gameserver.network.serverpackets.ValidateLocation;
import com.l2jfrozen.gameserver.templates.L2NpcTemplate;

public class AdenManagerInstance extends L2FolkInstance
{
  public AdenManagerInstance(int objectId, L2NpcTemplate template)
  {
    super(objectId, template);
  }
  
  @Override
public void onAction(L2PcInstance player)
  {
    if (player.getTarget() != this)
    {
      player.setTarget(this);
    }
    player.setLastFolkNPC(this);
    if (this != player.getTarget())
    {
      player.setTarget(this);
      MyTargetSelected my = new MyTargetSelected(getObjectId(), 0);
      player.sendPacket(my);
      my = null;
      player.sendPacket(new ValidateLocation(this));
    }
    else if (!canInteract(player))
    {
      player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
    }
    else
    {
    	showSiegeInfoWindow(player, 5);
    }
    player.sendPacket(ActionFailed.STATIC_PACKET);
  }

  public void showSiegeInfoWindow(L2PcInstance player, int castleId)
  {
    Castle c = CastleManager.getInstance().getCastleById(castleId);
    if (c != null)
    {
      player.sendPacket(new SiegeInfo(c));
    }
  }
}
