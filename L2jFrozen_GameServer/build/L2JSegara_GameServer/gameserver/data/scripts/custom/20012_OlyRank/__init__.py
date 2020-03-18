import sys
from com.l2jfrozen.gameserver.model.actor.instance import L2PcInstance
from java.util import Iterator
from com.l2jfrozen.gameserver.datatables import SkillTable
from com.l2jfrozen.util.database import L2DatabaseFactory
from com.l2jfrozen.gameserver.model.quest import State
from com.l2jfrozen.gameserver.model.quest import QuestState
from com.l2jfrozen.gameserver.model.quest.jython import QuestJython as JQuest


qn          = "20012_OlyRank"
NPC         = [31688,50500,90000]
QuestId     = 20012
QuestName   = "OlyRank"
QuestDesc   = "custom"
InitialHtml = "1.htm"
print "OlyRank started... Good"
class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

	def onTalk (self,npc,player):
		return InitialHtml

	def onEvent(self,event,st):
		htmltext = event

# duelist
		if event == "88":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=88 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
		
		# Dreadnought
		if event == "89":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=89 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext

		# Phoenix Knight
		if event == "90":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=90 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext

		# Hell Knight
		if event == "91":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=91 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext

		# Sagittarius
		if event == "92":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=92 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext

		# Adventurer
		if event == "93":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=93 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext

		# Archmage
		if event == "94":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=94 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Soultaker
		if event == "95":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=95 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Arcana Lord
		if event == "96":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=96 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Cardinal
		if event == "97":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=97 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Hierophant
		if event == "98":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=98 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Eva's Templar
		if event == "99":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=99 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Sword Muse
		if event == "100":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=100 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Wind Rider
		if event == "101":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=101 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Moonlight Sentinel
		if event == "102":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=102 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Mystic Muse
		if event == "103":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=103 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Elemental Master
		if event == "104":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=104 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Eva's Saint
		if event == "105":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=105 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Shillien Templar
		if event == "106":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=106 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Spectral Dancer
		if event == "107":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=107 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Ghost Hunter
		if event == "108":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=108 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Ghost Sentinel
		if event == "109":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=109 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Storm Screamer
		if event == "110":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=110 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Spectral Master
		if event == "111":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=111 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Shillien Saint
		if event == "112":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=112 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Titan
		if event == "113":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=113 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Grand Khavatari
		if event == "114":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=114 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Dominator
		if event == "115":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=115 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Doomcryer
		if event == "116":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=116 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext
			
		# Fortune Seeker
		if event == "117":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=117 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext

		# Maestro
		if event == "118":
			INITIAL_HTML = "<html><head><title>Grand Olympiad Ranking</title></head><body><center><font color=66cc00>Olympiad Ranking Online System</font></center><br><center><img src=\"L2UI.SquareWhite\" width=300 height=1><img src=\"L2UI.SquareBlank\" width=1 height=3></center><center><table width=300 border=0 bgcolor=\"000000\"><tr><td>Psition</td><center><td>|</td></center><td><center>Name</center></td><center><td>|</td></center><td><center>Points</center></td><center><td>|</td></center><td><center>Fights</center></td></tr>"
			HTML_INFO =""
			POSITION_VALUE_INITIAL = 0
			CONNECTION = L2DatabaseFactory.getInstance().getConnection()
			pts = CONNECTION.prepareStatement("SELECT char_name, olympiad_points, competitions_done from olympiad_nobles where class_id=118 and competitions_done>=1 order by olympiad_points desc, competitions_done desc");
			rs = pts.executeQuery()
			while (rs.next()) :
				CHAR_NAME = rs.getString("char_name")
				POINTS = rs.getString("olympiad_points")
				COMP_DONE = rs.getString("competitions_done")
				POSITION_VALUE_INITIAL = POSITION_VALUE_INITIAL + 1
				STRING_POSITION = str(POSITION_VALUE_INITIAL)
				HTML_INFO = HTML_INFO + "<tr><td><center>" + STRING_POSITION + "</td><center><td></td></center><td><center>" + CHAR_NAME +"</center></td><center><td></td></center><td><center>" + POINTS + "</center></td><center><td></td></center><td><center>" + COMP_DONE + "</center></td></tr>"
			HTML_END = "</table></body></html>"
			htmltext = INITIAL_HTML + HTML_INFO + HTML_END
			CONNECTION.close()
			return htmltext


QUEST = Quest(QuestId,str(QuestId) + "_" + QuestName,QuestDesc)

for npcId in NPC:
	QUEST.addStartNpc(npcId)
	QUEST.addTalkId(npcId)
