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
package com.l2jfrozen.gameserver.model;
 
public class L2ItemMarketModel {
 
private int ownerId;
private int enchLvl;
private int itemId;
private int itemGrade;
private int itemObjId;
private int price;
private int count;
private String ownerName = null;
private String itemName = null;
private String itemType = null;
private String l2Type = null;
 
public void setOwnerId(int ownerId) {
this.ownerId = ownerId;
}
 
public void setOwnerName(String ownerName) {
this.ownerName = ownerName;
}
 
public void setItemId(int itemId) {
this.itemId = itemId;
}
 
public void setItemObjId(int itemObjId) {
this.itemObjId = itemObjId;
}
 
public void setItemName(String itemName) {
this.itemName = itemName;
}
 
public void setEnchLvl(int enchLvl) {
this.enchLvl = enchLvl;
}
 
public void setItemGrade(int itemGrade) {
this.itemGrade = itemGrade;
}
 
public void setItemType(String itemType) {
this.itemType = itemType;
}
 
public void setL2Type(String l2Type) {
this.l2Type = l2Type;
}
 
public void setPrice(int price) {
this.price = price;
}
 
public void setCount(int count) {
this.count = count;
}
 
public int getOwnerId() {
return ownerId;
}
 
public String getOwnerName() {
return ownerName;
}
 
public int getItemId() {
return itemId;
}
 
public int getItemObjId() {
return itemObjId;
}
 
public String getItemName() {
return itemName;
}
 
public int getEnchLvl() {
return enchLvl;
}
 
public int getItemGrade() {
return itemGrade;
}
 
public String getItemType() {
return itemType;
}
 
public String getL2Type() {
return l2Type;
}
 
public int getPrice() {
return price;
}
 
public int getCount() {
return count;
}
 
} 