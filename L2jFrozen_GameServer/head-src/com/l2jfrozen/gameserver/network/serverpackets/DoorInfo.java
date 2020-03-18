/*
 * L2jFrozen Project - www.l2jfrozen.com 
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package com.l2jfrozen.gameserver.network.serverpackets;

import com.l2jfrozen.gameserver.model.actor.instance.L2DoorInstance;

/**
 * 60 d6 6d c0 4b door id 8f 14 00 00 x b7 f1 00 00 y 60 f2 ff ff z 00 00 00 00 ?? format dddd rev 377 ID:%d X:%d Y:%d Z:%d ddddd rev 419
 * @version $Revision: 1.3.2.2.2.3 $ $Date: 2005/03/27 15:29:57 $
 */
public class DoorInfo extends L2GameServerPacket
{
	private static final String _S__60_DOORINFO = "[S] 4c DoorInfo";
	private final L2DoorInstance _door;
	private final int _staticObjectId;
	private final int _objectId;
	private final int _type;
	private final boolean _isTargetable;
	private final boolean _isEnemyOf;
	private final int _maxHp;
	private final int _currentHp;
	private final boolean _showHp;
	private final int _damageGrade; 
	
	public DoorInfo(final L2DoorInstance door, final boolean showHp)
	{
		_staticObjectId = door.getDoorId();
		_objectId = door.getObjectId();
		_type = 1;
		_door=door;
		_isTargetable = true;
		_isEnemyOf = door.isEnemyOf(_door);
		_maxHp = door.getMaxHp();
		_currentHp = (int) door.getStatus().getCurrentHp();
		_showHp = showHp;
		_damageGrade = door.getDamage(); 
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x4c);
		writeD(_door.getObjectId());
		writeD(_door.getDoorId());
		writeD(_type);
		writeD(_isTargetable ? 1 : 0);
		writeD(_door.isEnemyOf(getClient().getActiveChar() ) ? 1 : 0);
		writeD(_currentHp);
		writeD(_maxHp);
		writeD(_showHp ? 1 : 0);
		writeD(_damageGrade);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.l2jfrozen.gameserver.serverpackets.ServerBasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return _S__60_DOORINFO;
	}

	/**
	 * @return the _isEnemyOf
	 */
	public boolean is_isEnemyOf()
	{
		return _isEnemyOf;
	}

	/**
	 * @return the _objectId
	 */
	public int get_objectId()
	{
		return _objectId;
	}

	/**
	 * @return the _staticObjectId
	 */
	public int get_staticObjectId()
	{
		return _staticObjectId;
	}
	
}