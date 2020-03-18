package com.l2jfrozen.gameserver.network.serverpackets;

import com.l2jfrozen.gameserver.model.actor.instance.L2HennaInstance;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;

public class GMViewHennaInfo extends L2GameServerPacket
{
  private final L2PcInstance _activeChar;
  private final L2HennaInstance[] _hennas = new L2HennaInstance[3];
  private int _count;

  public GMViewHennaInfo(L2PcInstance activeChar)
  {
    this._activeChar = activeChar;

    int j = 0;
    for (int i = 0; i < 3; i++)
    {
      L2HennaInstance h = this._activeChar.getHennas(i + 1);
      if (h != null)
        this._hennas[(j++)] = h;
    }
    this._count = j;
  }

@Override
  protected void writeImpl()
  {
    writeC(234);

    writeC(this._activeChar.getHennaStatINT());
    writeC(this._activeChar.getHennaStatSTR());
    writeC(this._activeChar.getHennaStatCON());
    writeC(this._activeChar.getHennaStatMEN());
    writeC(this._activeChar.getHennaStatDEX());
    writeC(this._activeChar.getHennaStatWIT());

    writeD(3);

    writeD(this._count);
    for (int i = 0; i < this._count; i++)
    {
      writeD(this._hennas[i].getSymbolId());
      writeD(1);
    }
  }

@Override
  public String getType()
  {
    return "[S] 0xea GMHennaInfo";
  }
}