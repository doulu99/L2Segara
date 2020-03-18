package com.l2jfrozen.gameserver;

import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import com.l2jfrozen.Config;
import com.l2jfrozen.gameserver.model.L2Character;
import com.l2jfrozen.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfrozen.gameserver.network.clientpackets.Say2;
import com.l2jfrozen.gameserver.network.serverpackets.CreatureSay;
import com.l2jfrozen.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jfrozen.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfrozen.gameserver.thread.ThreadPoolManager;
import com.l2jfrozen.util.random.Rnd;

/**
 * @author: fissban
 */
public class L2AntiBot
{
   public static final Logger _log = Logger.getLogger(L2AntiBot.class.getName());
   private static L2AntiBot _instance;
   public static ScheduledFuture<?> _antiBotTask;
  
   private int _antibot;
   private static String _code = "";
  
   public L2AntiBot()
   {
   }
  
   public static L2AntiBot getInstance()
   {
      if (_instance == null)
      {
         _instance = new L2AntiBot();
      }
      return _instance;
   }
  
   private static final String[] _IMG =
   {
      "L2UI_CH3.calculate2_0",
      "L2UI_CH3.calculate2_1",
      "L2UI_CH3.calculate2_2",
      "L2UI_CH3.calculate2_3",
      "L2UI_CH3.calculate2_4",
      "L2UI_CH3.calculate2_5",
      "L2UI_CH3.calculate2_6",
      "L2UI_CH3.calculate2_7",
      "L2UI_CH3.calculate2_8",
      "L2UI_CH3.calculate2_9"
   };
  
   public void antibot(L2Character player)
   {
      _antibot++;
      
      if (_antibot >= Config.ANTIBOT_KILL_MOBS)
      {
         _antibot = 0;
         _antiBotTask = ThreadPoolManager.getInstance().scheduleGeneral(new StartAntiBotTask(player), Config.ANTIBOT_TIME_VOTE * 1000);
      }
   }
  
   public static class StartAntiBotTask implements Runnable
   {
      L2Character _player_cha;
      NpcHtmlMessage _npcHtmlMessage = new NpcHtmlMessage(0);
      
      protected StartAntiBotTask(L2Character player)
      {
         if (player != null)
         {
            _player_cha = player;
            
            _player_cha.getActingPlayer().setIsParalyzed(true);
            _player_cha.getActingPlayer().setIsInvul(true);
            _player_cha.getActingPlayer().startAbnormalEffect(L2Character.ABNORMAL_EFFECT_ROOT);
            _player_cha.getActingPlayer().sendPacket(new ExShowScreenMessage("Have " + Config.ANTIBOT_TIME_VOTE + " seconds to confirm the Catpcha!", 10000));
            _player_cha.getActingPlayer().getActingPlayer().sendPacket(new CreatureSay(0, Say2.BATTLEFIELD, "[AntiBot]", "Have " + Config.ANTIBOT_TIME_VOTE + " seconds to confirm the Catpcha!"));
            _npcHtmlMessage.setHtml(ShowHtml_Start(_player_cha));
            _player_cha.getActingPlayer().sendPacket(_npcHtmlMessage);
         }
         else
         {
            return;
         }
      }
      
      @Override
      public void run()
      {
         if (!_player_cha.getActingPlayer().isInJail())
         {
            _player_cha.getActingPlayer().sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Your time limit has elapsed!"));
            _player_cha.getActingPlayer().increaseAttempt();
            
            if (_player_cha.getActingPlayer().getAttempt() >= 3)
            {
               _player_cha.getActingPlayer().setIsParalyzed(false);
               _player_cha.getActingPlayer().setIsInvul(false);
               _player_cha.getActingPlayer().stopAbnormalEffect(L2Character.ABNORMAL_EFFECT_ROOT);
               _player_cha.getActingPlayer().getActingPlayer().setPunishLevel(L2PcInstance.PunishLevel.JAIL, Config.ANTIBOT_TIME_JAIL);
               _player_cha.getActingPlayer().getActingPlayer().sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Character " + _player_cha.getActingPlayer().getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!"));
               _log.warning("[AntiBot] Character " + _player_cha.getActingPlayer().getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!");
            }
            else
            {
               _antiBotTask = ThreadPoolManager.getInstance().scheduleGeneral(new StartAntiBotTask(_player_cha), Config.ANTIBOT_TIME_VOTE * 1000);
            }
         }
      }
   }
  
   public static String ShowHtml_Start(L2Character player)
   {
	  String htmltext = "";
      htmltext += "<html>";
      htmltext += "<title>AntiBot System</title>";
      htmltext += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"292\" height=\"350\" background=\"L2UI_CH3.refinewnd_back_Pattern\">";
      htmltext += "<tr><td valign=\"top\" align=\"center\">";
      htmltext += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
      htmltext += "</table><br><br>";
      htmltext += "<center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br></center>";
      htmltext += "<center><font color=\"00C3FF\">" + player.getName() + "<font color=\"LEVEL\"> enter the captcha</center><br><br>";
      htmltext += "<center><font color=\"LEVEL\">you get " + (3 - player.getActingPlayer().getAttempt()) + " chances to complete the captcha</center><br><br>";
      htmltext += "<br>";
      
      htmltext += "<table>";
      htmltext += "<tr>";
      _code = "";
      for (int cont = 1; cont < 5; cont++)
      {
         int number = Rnd.get(_IMG.length - 1);
         _code += String.valueOf(number);
        
         htmltext += "<td><right><img src=" + _IMG[number] + " width=64 height=64 ></right></td>";
      }
      
      player.getActingPlayer().setCode(_code);
      htmltext += "</tr>";
      htmltext += "</table><br>";
      
      htmltext += "<center><edit type=\"captcha\" var=\"captcha\" width=\"150\"></center>";
      htmltext += "<center><button value=\"Confirm\" action=\"bypass -h antibot $captcha\" width=200 height=32 back=\"L2UI_CT1.OlympiadWnd_DF_HeroConfirm_Down\" fore=\"L2UI_CT1.OlympiadWnd_DF_HeroConfirm\"></center><br1>";
      htmltext += "<center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32></center><br>";
      htmltext += "</html></body>";
      
      return htmltext;
   }
  
   public static String ShowHtml_End(L2Character player)
   {
	  String htmltext = "";
      htmltext += "<html>";
      htmltext += "<title>AntiBot System</title>";
      htmltext += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"292\" height=\"350\" background=\"L2UI_CH3.refinewnd_back_Pattern\">";
      htmltext += "<tr><td valign=\"top\" align=\"center\">";
      htmltext += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
      htmltext += "</table><br><br>";
      htmltext += "<center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br></center>";
      htmltext += "<center><font color=\"00C3FF\">" + player.getName() + "<font color=\"LEVEL\"> FAIL enter the captcha</center><br><br>";
      htmltext += "<br><br>";
      htmltext += "<center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32></center><br>";
      htmltext += "</html></body>";
      return htmltext;
   }
  
   // bypass
   public void checkCode(L2PcInstance player, String code)
   {
      if (code.equals(player.getCode()))
      {
         stopAntiBotTask();
         player.setCheckCode(true);
         player.resetAttemp();
        
         player.sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Congratulations, has passed control!"));
         player.setIsParalyzed(false);
         player.setIsInvul(false);
         player.stopAbnormalEffect(L2Character.ABNORMAL_EFFECT_ROOT);
      }
      else
      {
         stopAntiBotTask();
         player.setCheckCode(false);
         player.increaseAttempt();
        
         _antiBotTask = ThreadPoolManager.getInstance().scheduleGeneral(new StartAntiBotTask(player), Config.ANTIBOT_TIME_VOTE * 1000);
      }
      
      if (player.getAttempt() >= 3)
      {
         stopAntiBotTask();
         player.resetAttemp();
        
         player.setIsParalyzed(false);
         player.setIsInvul(false);
         player.stopAbnormalEffect(L2Character.ABNORMAL_EFFECT_ROOT);
        
         player.getActingPlayer().setPunishLevel(L2PcInstance.PunishLevel.JAIL, Config.ANTIBOT_TIME_JAIL);
         player.getActingPlayer().sendPacket(new CreatureSay(0, Say2.TELL, "[AntiBot]", "Character " + player.getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!"));
         _log.warning("[AntiBot] Character " + player.getName() + " jailed for " + Config.ANTIBOT_TIME_JAIL + " minutes!");
      }
   }
  
   public static void stopAntiBotTask()
   {
      if (_antiBotTask != null)
      {
         _antiBotTask.cancel(false);
         _antiBotTask = null;
      }
   }
}