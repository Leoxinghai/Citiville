package Display.hud;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.hud.components.*;
import Engine.Managers.*;
import Modules.quest.Display.*;
import Modules.quest.Display.QuestManager.*;
import Modules.saga.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.util.*;

import Classes.*;
import Transactions.*;
import com.xinghai.gamemode.*;
import GameMode.GMPlaceBridge;
import GameMode.GMPlay;
//import flash.media.*;

import com.xinghai.Debug;

import com.xinghai.weibo.*;
import Classes.effects.*;
import Engine.IsoMath;

    public class HUD extends Sprite
    {
        private JLabel m_townNameText ;
        private TextField m_debugText ;
        private boolean m_dirty =false ;
        private boolean m_lockVisualEffects =false ;
        protected boolean m_visit =false ;
        private Dictionary m_components ;
        private Function m_trainClickCallback =null ;
        protected StreakBonus m_streakBonus ;
        protected Sprite m_leftQuestLayer ;
        protected Sprite m_rightQuestLayer ;
        private ArrayList m_persistentComponents ;
        public static  int TIMER_TICK =50;
        public static  int COUNT_RATE =30;
        public static  String COMP_COINS ="coins";
        public static  String COMP_KDBOMB ="kdbomb";
        public static  String COMP_LRBOMB ="lrbomb";
        public static  String COMP_CITYLEVEL ="level";


        public static  String COMP_CASH ="cash";
        public static  String COMP_GOODS ="goods";
        public static  String COMP_PREMIUM_GOODS ="premium_goods";
        public static  String COMP_ENERGY ="energy";
        public static  String COMP_XP ="xp";

        public static  String COMP_QUESTS ="quest";
        public static  String COMP_QUEST_MANAGER_ICON ="questManIcon";
        public static  String COMP_MINI_QUEST ="miniQuest";
        public static  String COMP_REPUTATION ="reputation";
        public static  String SHOW_ENERGY_MODIFIER_FLAG ="show_energy_modifier";
        public static  int TOP_BORDER =10;
        public static  int LEFT_BORDER =50;
        public static  Array HUD_COMPONENTS =.get(HUDCoinsComponent ,HUDCacheComponent ,HUDCashComponent ,HUDEnergyComponent ,HUDGoodsComponent ,HUDPremiumGoodsComponent ,HUDXPComponent ,HUDReputationComponent ,HUDQuestBarComponent ,HUDVisitingEnergyComponent ,HUDFranchiseRequestComponent ,HUDGetCurrencyComponent ,HUDQuestManagerComponent ,HUDQuestManagerIconComponent ,HUDDailyDripComponent ,HUDXPromoComponent ,HUDMobileXpromoComponent ,HUDWWFXpromoComponent ,HUDAdventureXPromoComponent ,HUDFarmLighthouseCoveXPromoComponent ,HUDFBBookmarkPromoComponent ,HUDVday2011Component ,HUDVerticalStackComponent ,HUDMiniGameComponent ,HUDXPromoFarmComponent ,HUDHeartInventoryComponent ,HUDGoldenPlowComponent ,HUDTimerComponent ,HUDPermsComponent ,HUDZCrossPromoComponent ,HUDVisitingBuyComponent ,HUDKDBombComponent ,HUDLRBombComponent ,HUDCityLevelComponent) ;

        public SpriteButton m_fireKDButton ;
        public SpriteButton m_fireLRButton ;

        public SpriteButton m_collectButton ;
        public SpriteButton m_flyButton ;
        public SpriteButton m_transmitButton ;
        public SpriteButton m_topupButton ;



        public  HUD (Sprite param1 ,Sprite param2 )
        {
            this.m_leftQuestLayer = param1;
            this.m_rightQuestLayer = param2;
            this.m_persistentComponents = new ArrayList();
            Global.hud = this;
            if (Global.player == null)
            {
                this.applyConfig("default");
            }
            else
            {
                this.gotoDefaultConfig();
            }
            //_loc_3 =Global.gameSettings().getXML ().dooberStreak.get(0) ;
            //this.m_streakBonus = new StreakBonus(_loc_3);
            //this.m_streakBonus.mouseEnabled = false;
            //this.addChild(this.m_streakBonus);
            //_loc_4 = this.getComponent(HUD.COMP_XP );
            //this.m_streakBonus.x = _loc_4.x + StreakBonus.STREAKBONUS_XOFFSET;
            //this.m_streakBonus.y = _loc_4.y + StreakBonus.STREAKBONUS_YOFFSET;

            return;
        }//end

        private void  gotoDefaultConfig ()
        {
            if (Global.player.isPremiumGoodsActive)
            {
                this.applyConfig("default_premiumgoods");
            }
            else
            {
                this.applyConfig("default");
            }
            this.updateCommodities();
            return;
        }//end

        private void  gotoVisitConfig ()
        {
            if (Global.player.isPremiumGoodsActive)
            {
                this.applyConfig("visiting_premiumgoods");
            }
            else
            {
                this.applyConfig("visiting");
            }
            this.updateCommodities();
            return;
        }//end

        public void  applyConfig (String param1 )
        {
            HUDComponent _loc_3 =null ;
            Dictionary _loc_4 =null ;
            HUDComponent _loc_5 =null ;
            int _loc_6 =0;
            XML _loc_7 =null ;
            int _loc_8 =0;
            XML _loc_9 =null ;
            XML _loc_10 =null ;
            HUDComponent _loc_11 =null ;
            String _loc_12 =null ;
            Class _loc_13 =null ;
            String _loc_14 =null ;
            HUDComponentContainer _loc_15 =null ;

            Debug.debug7("HUD.applyConfig");

            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_VDAY_2011);
            for(int i0 = 0; i0 < this.m_components.size(); i0++)
            {
            	_loc_3 = this.m_components.get(i0);

                _loc_3.reset();
                if (_loc_3.parent)
                {
                    _loc_3.parent.removeChild(_loc_3);
                }
            }
            _loc_4 = this.m_components ? (this.m_components) : (new Dictionary());
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                _loc_5.cleanUp();
            }
            this.m_components = null;
            this.m_components = new Dictionary();
            _loc_6 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
            _loc_7 = Global.gameSettings().getXML();
            _loc_8 = 0;
            for(int i0 = 0; i0 < _loc_7.hudConfig.config.size(); i0++)
            {
            	_loc_9 = _loc_7.hudConfig.config.get(i0);

                if (_loc_9.@name == param1)
                {
                    for(int i0 = 0; i0 < _loc_9.component.size(); i0++)
                    {
                    	_loc_10 = _loc_9.component.get(i0);

                        if (_loc_10.@type == "vday2011" && _loc_2 == 0)
                        {
                            continue;
                        }
                        _loc_12 = String(_loc_10.@type);
                        if (_loc_4.get(_loc_12) != null)
                        {
                            _loc_11 = _loc_4.get(_loc_12);
                        }
                        else if (_loc_6 == ExperimentDefinitions.USE_QUEST_MANAGER && _loc_12 == "miniQuest")
                        {
                            _loc_13 = getDefinitionByName("Display.hud.components." + _loc_10.@name) as Class;
                            _loc_11 = new _loc_13(10, 10, 0, 260, 1, 2, 80, 400);
                        }
                        else if (_loc_6 == ExperimentDefinitions.NO_QUEST_MANAGER && (_loc_12 == "miniQuest" || _loc_12 == "questManIcon" || _loc_10.@name == "HUDQuestManagerComponent") || _loc_6 == ExperimentDefinitions.USE_QUEST_MANAGER && (_loc_12 == "quest" && _loc_10.@name == "HUDQuestBarComponent"))
                        {
                            continue;
                        }
                        else
                        {
                            if(_loc_10.@name == "HUDSantasWorkshopComponent") continue;

                            _loc_11 = new (getDefinitionByName("Display.hud.components." + _loc_10.@name) as Class)();
                            _loc_11.initWithXML(_loc_10);
                        }
                        _loc_11.refresh(false);
                        _loc_11.x = _loc_10.@xPos == "prev" ? (_loc_8) : (_loc_10.@xPos);
                        _loc_11.y = _loc_10.@yPos;
                        _loc_8 = _loc_11.x + _loc_11.width;
                        this.m_components.put(_loc_12,  _loc_11);
                        if (_loc_11.isVisible())
                        {
                            if (_loc_10.hasOwnProperty("@parent"))
                            {
                                _loc_14 = _loc_10.@parent;
                                _loc_15 =(HUDComponentContainer) this.m_components.get(_loc_14);
                                _loc_15.addComponent(_loc_11);
                            }
                            else if (_loc_12 == "quest" || _loc_12 == "miniQuest" || _loc_12 == "questManIcon" || _loc_12 == "visitStack" || _loc_10.@name == "HUDQuestManagerComponent")
                            {
                                this.m_leftQuestLayer.addChild(_loc_11);
                            }
                            else if (_loc_12 == "bottom")
                            {
                                Global.ui.bottomUI.addChild(_loc_11);
                            }
                            else if (Number(_loc_10.@xPos) > 670)
                            {
                                this.m_rightQuestLayer.addChild(_loc_11);
                            }
                            else
                            {
                                addChild(_loc_11);
                            }
                        }
                        _loc_11 = null;
                    }
                    _loc_4 = null;
                }
            }
            this.displayPersistentRightStackComponents();


        m_collectButton = new SpriteButton(EmbeddedArt.hud_collect, EmbeddedArt.hud_collect, EmbeddedArt.hud_collect, this.onCollectClick);
        m_collectButton.x = 420;
        m_collectButton.y = 0;
        addChild(m_collectButton);

        m_flyButton = new SpriteButton(EmbeddedArt.hud_fly, EmbeddedArt.hud_fly, EmbeddedArt.hud_fly, this.onFlyClick);
        m_flyButton.x = 490;
        m_flyButton.y = 0;
        addChild(m_flyButton);

        m_transmitButton = new SpriteButton(EmbeddedArt.hud_transmit, EmbeddedArt.hud_transmit, EmbeddedArt.hud_transmit, this.onTransmitClick);
        m_transmitButton.x = 570;
        m_transmitButton.y = 0;
        addChild(m_transmitButton);

        m_fireKDButton = new SpriteButton(EmbeddedArt.hud_kdbomb, EmbeddedArt.hud_kdbomb, EmbeddedArt.hud_kdbomb, this.onFireKDClick);
        m_fireKDButton.x = 660;
        m_fireKDButton.y = 0;
        addChild(m_fireKDButton);

        m_fireLRButton = new SpriteButton(EmbeddedArt.hud_lrbomb, EmbeddedArt.hud_lrbomb, EmbeddedArt.hud_lrbomb, this.onFireLRClick);
        m_fireLRButton.x = 740;
        m_fireLRButton.y = 0;
        addChild(m_fireLRButton);

        m_topupButton = new SpriteButton(EmbeddedArt.hud_topup, EmbeddedArt.hud_topup, EmbeddedArt.hud_topup, this.onTopupClick);
        m_topupButton.x = 820;
        m_topupButton.y = 0;
        addChild(m_topupButton);

            return;
        }//end

        private void  onFireKDClick (MouseEvent event )
        {
            Global.world.addGameMode(new GMFireKDBomb("fire_strawberries"));
            Sounds.unpauseMusic();
            return;
        }//end

        private void  onFireLRClick (MouseEvent event )
        {

            Global.world.addGameMode(new GMFireLRBomb("fire_strawberries"));

            Sounds.pauseMusic();
            //SoundManager.stopSound("bgMusic");

            return;
        }//end

        private void  onTopupClick (MouseEvent event )
        {

            //Global.weiboManager.upgrateTrainStation();
            //Global.world.addGameMode(new GMPlaceBridge("bridge_standard", Bridge, true));

            //SoundManager.playSound("bgMusic");
            //Sounds.pauseMusic();
            //Sounds.play("cruise_fireworks");
             Sounds.play("train_arrives");

            //SoundManager.playSound("bgMusic", 0, 1, new SoundTransform(10), true)
            //SoundManager.playSound("bgMusic");

            String url = (String)GlobalEngine.getFlashVar("topup_url");
            String userid = (String)GlobalEngine.getFlashVar("userid");
            url = url + "?userid=" + userid;
            GlobalEngine.socialNetwork.redirect(url, null, "_blank", false);

            return;
        }//end

        private void  onFlyClick (MouseEvent event )
        {
            Debug.debug7("onFlyClick");
            Global.world.addGameMode(new GMFlightGo("fire_strawberries"));


            return;
        }//end

        private void  onCollectClick (MouseEvent event )
        {
            Debug.debug7("onCollectClick");

            Global.world.addGameMode(new GMCollect());
            return;
        }//end


        private void  onTransmitClick (MouseEvent event )
        {
	    Global.world.citySim.trainManager.scrollToTrainStation();
            if(Global.player.gold  < 200) {
	        _loc_0 = newWarningEffect(IsoMath.tilePosToPixelPos(-3,39),EffectType.NOMONEY.type,true,false,false);
		return;
            }

	    _loc_00 = newWarningEffect(IsoMath.tilePosToPixelPos(-3,39),EffectType.MINUS200.type,true,false,false);
            Global.player.gold = Global.player.gold - 200;
            Global.world.citySim.trainManager.purchaseWelcomeTrain();

            //Global.world.addGameMode(new GMFire("fire_strawberries"));
            return;
        }//end


        public void  debugToggleDebugTextVisibility ()
        {
            this.m_debugText.visible = !this.m_debugText.visible;
            return;
        }//end

        public void  updateCommodities ()
        {
            //if (Global.player.commodities != null)
            //{
            //    Global.hud.goodsCap = Global.player.commodities.getCapacity(Commodities.GOODS_COMMODITY);
            //    Global.hud.goods = Global.player.commodities.getCount(Commodities.GOODS_COMMODITY);
            //    Global.hud.premium_goodsCap = Global.player.commodities.getCapacity(Commodities.PREMIUM_GOODS_COMMODITY);
            //    Global.hud.premium_goods = Global.player.commodities.getCount(Commodities.PREMIUM_GOODS_COMMODITY);
            //}
            return;
        }//end

        public void  updateDebugText ()
        {
            if (this.m_debugText == null || !this.m_debugText.visible)
            {
                return;
            }

            this.m_debugText.text = "";
            return;
        }//end

        private void  onGetCoins (MouseEvent event )
        {
            UI.displayPopup(new GetCoinsDialog(ZLoc.t("Dialogs", "ImpulseMarketCash"), "", GenericDialogView.TYPE_YESNO, null, true));
            return;
        }//end

        public void  conditionallyRefreshHUD (boolean param1 =false )
        {

            HUDComponent _loc_2 =null ;
            if (this.m_dirty || param1)
            {
                for(int i0 = 0; i0 < this.m_components.size(); i0++)
                {
                	_loc_2 = this.m_components.get(i0);

                    _loc_2.refresh(this.m_lockVisualEffects);
                }
                this.m_dirty = false;
                this.m_lockVisualEffects = false;
            }
            return;
        }//end

        public void  onResize ()
        {
            return;
        }//end

        public void  lockVisualEffectsForNextUpdate ()
        {
            this.m_lockVisualEffects = true;
            return;
        }//end

        public void  unlockVisualEffects ()
        {
            this.m_lockVisualEffects = false;
            return;
        }//end

        public boolean  isVisualEffectsLocked ()
        {
            return this.m_lockVisualEffects;
        }//end

        public void  markDirty ()
        {
            this.m_dirty = true;
            return;
        }//end

        public String  townName ()
        {
            return this.m_townNameText.getText();
        }//end

        public void  townName (String param1 )
        {
            if (this.m_townNameText != null)
            {
                return this.m_townNameText.setText(param1);
            }
            return;
        }//end

        public void  gold (int param1 )
        {
            Debug.debug7("HUD.set gold. " + param1);
            if (this.m_components.get("coins"))
            {
                this.m_components.get("coins").updateCounter(param1);
            }
            return;
        }//end

        public void  cash (int param1 )
        {
            Debug.debug7("HUD.set cash. " + param1);
            if (this.m_components.get("cash"))
            {
                this.m_components.get("cash").updateCounter(param1);
            }
            return;
        }//end

        public void  goods (int param1 )
        {
            Debug.debug7("HUD.set goods. " + param1);

            this.resetGoodsHUDIfRequired();
            if (Global.player.isPremiumGoodsActive && this.m_components.get(COMP_PREMIUM_GOODS))
            {
                this.m_components.get(COMP_PREMIUM_GOODS).updateCounter1(param1);
            }
            else if (this.m_components.get(COMP_GOODS))
            {
                this.m_components.get(COMP_GOODS).updateCounter(param1);
            }
            return;
        }//end

        public void  goodsCap (int param1 )
        {
            this.resetGoodsHUDIfRequired();
            if (Global.player.isPremiumGoodsActive && this.m_components.get(COMP_PREMIUM_GOODS))
            {
                this.m_components.get(COMP_PREMIUM_GOODS).updateCounterCapacity1(param1);
            }
            else if (this.m_components.get(COMP_GOODS))
            {
                this.m_components.get(COMP_GOODS).updateCounterCapacity(param1);
            }
            return;
        }//end

        public void  energy (int param1 )
        {
            if (this.m_components.get("energy"))
            {
                this.m_components.get("energy").updateCounter(param1);
            }
            return;
        }//end

        public void  premium_goods (int param1 )
        {
            this.resetGoodsHUDIfRequired();
            if (Global.player.isPremiumGoodsActive && this.m_components.get(COMP_PREMIUM_GOODS))
            {
                this.m_components.get(COMP_PREMIUM_GOODS).updateCounter2(param1);
            }
            return;
        }//end

        public void  premium_goodsCap (int param1 )
        {
            this.resetGoodsHUDIfRequired();
            if (Global.player.isPremiumGoodsActive && this.m_components.get(COMP_PREMIUM_GOODS))
            {
                this.m_components.get(COMP_PREMIUM_GOODS).updateCounterCapacity2(param1);
            }
            return;
        }//end

        public void  reputation (int param1 )
        {
            if (this.m_components.get("reputation"))
            {
                this.m_components.get("reputation").updateCounter(param1);
            }
            return;
        }//end

        public void  xp (int param1 )
        {
            if (this.m_components.get("xp"))
            {
                this.m_components.get("xp").updateCounter(param1);
            }
            return;
        }//end

        public void  kdbomb (int param1 )
        {
            if (this.m_components.get("kdbomb"))
            {
                this.m_components.get("kdbomb").updateCounter(param1);
            }
            return;
        }//end

        public void  lrbomb (int param1 )
        {
            if (this.m_components.get("lrbomb"))
            {
                this.m_components.get("lrbomb").updateCounter(param1);
            }
            return;
        }//end

        public void  level (int param1 )
        {
            Debug.debug7("HUD.set level. " + param1);
            if (this.m_components.get("level"))
            {
                this.m_components.get("level").updateCounter(param1);
            }
            return;
        }//end

        public void  updateVdayCount ()
        {
            if (this.m_components.get("vday2011"))
            {
                this.m_components.get("vday2011").updateCounter(null);
            }
            return;
        }//end

        public void  updateMiniGame (int param1 =-1)
        {
            if (this.m_components.get("miniGame"))
            {
                this.m_components.get("miniGame").updateCounter(param1);
            }
            return;
        }//end

        public void  setTutorial (boolean param1 )
        {
            this.applyConfig(param1 ? ("tutorial") : ("default"));
            return;
        }//end

        public void  setVisiting (boolean param1 )
        {
            this.m_visit = param1;
            this.refreshGoodsHUD();
            return;
        }//end

        private void  resetGoodsHUDIfRequired ()
        {
            if (Global.player.isPremiumGoodsActive && !this.m_components.get(COMP_PREMIUM_GOODS))
            {
                this.refreshGoodsHUD();
            }
            if (!Global.player.isPremiumGoodsActive && !this.m_components.get(COMP_GOODS))
            {
                this.refreshGoodsHUD();
            }
            return;
        }//end

        public void  refreshGoodsHUD ()
        {
            if (this.m_visit == true)
            {
                this.gotoVisitConfig();
            }
            else
            {
                this.gotoDefaultConfig();
            }
            return;
        }//end

        public HUDComponent  getComponent (String param1 )
        {
            return this.m_components.get(param1);
        }//end

        public void  showGoalsProgressOverlayOnQuestIcon (String param1 ,String param2 ,boolean param3 =false ,Object param4 =null )
        {
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").showBanner(param1, param2, param3, param4);
            }
            return;
        }//end

        public boolean  hasQuestManager ()
        {
            return this.m_components.get("quest") != null;
        }//end

        public void  lockQuestManager ()
        {
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").lock();
            }
            return;
        }//end

        public void  unlockQuestManager ()
        {
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").unlock();
            }
            return;
        }//end

        public boolean  hasQuestSprite (String param1 )
        {
            boolean _loc_2 =false ;
            if (this.m_components.get("quest"))
            {
                _loc_2 = this.m_components.get("quest").hasIcon(param1);
            }
            return _loc_2;
        }//end

        public void  addQuestSprite (String param1 ,DisplayObject param2 ,String param3 )
        {
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").addIcon(param1, param3, param2, this.clickQuestIcon);
            }
            return;
        }//end

        public void  removeQuestSprite (String param1 ,boolean param2 =true ,boolean param3 =false )
        {
            int _loc_4 =0;
            QuestManagerSprite _loc_5 =null ;
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").removeIcon(param1, this.clickQuestIcon, param2, param3);
            }
            else
            {
                _loc_4 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
                if (_loc_4 == ExperimentDefinitions.USE_QUEST_MANAGER)
                {
                    _loc_5 = UI.questManagerView.content.removeIconByName(param1);
                    if (!_loc_5)
                    {
                        UI.questManagerView.content.popFirstQuestIcon();
                    }
                }
            }
            return;
        }//end

        public void  addPersistentRightStackComponent (HUDComponent param1 )
        {
            this.m_persistentComponents.append(param1);
            return;
        }//end

        public void  removeRightStackComponent (HUDComponent param1 )
        {
            if (this.m_persistentComponents)
            {
                this.m_persistentComponents.remove(param1);
            }
            return;
        }//end

        public void  displayPersistentRightStackComponents ()
        {
            HUDComponent _loc_1 =null ;
            double _loc_2 =0;
            HUDComponent _loc_3 =null ;
            if (Global.isVisiting())
            {
                return;
            }
            for(int i0 = 0; i0 < this.m_components.size(); i0++)
            {
            	_loc_1 = this.m_components.get(i0);

                if (_loc_1 instanceof HUDVerticalStackComponent)
                {
                    _loc_2 = 0;
                    while (_loc_2 < this.m_persistentComponents.size())
                    {

                        _loc_3 = this.m_persistentComponents.get(_loc_2);
                        _loc_3.refresh(false);
                        ((HUDVerticalStackComponent)_loc_1).addComponentAtIndex(0, _loc_3);
                        _loc_2 = _loc_2 + 1;
                    }
                }
            }
            return;
        }//end

        public void  showTrainQuestSprite (DisplayObject param1 ,Function param2 )
        {
            this.m_trainClickCallback = param2;
            _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
            if (_loc_3 == ExperimentDefinitions.USE_QUEST_MANAGER)
            {
                if (this.m_components.get("miniQuest"))
                {
                    this.m_components.get("miniQuest").addIcon("trainAtStation", ZLoc.t("Dialogs", "TrainUI_QuestHudTooltip"), param1, this.clickTrainIcon);
                }
            }
            else if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").addIcon("trainAtStation", ZLoc.t("Dialogs", "TrainUI_QuestHudTooltip"), param1, this.clickTrainIcon);
            }
            return;
        }//end

        public void  hideTrainQuestSprite ()
        {
            _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
            if (_loc_1 == ExperimentDefinitions.USE_QUEST_MANAGER)
            {
                if (this.m_components.get("miniQuest"))
                {
                    this.m_components.get("miniQuest").removeIcon("trainAtStation", this.clickTrainIcon);
                }
            }
            else if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").removeIcon("trainAtStation", this.clickTrainIcon);
            }
            return;
        }//end

        public boolean  showMiniQuestSprite (String param1 ,String param2 ,DisplayObject param3 ,Function param4 ,int param5 =0)
        {
            _loc_6 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
            boolean _loc_7 =false ;
            if (_loc_6 == ExperimentDefinitions.USE_QUEST_MANAGER)
            {
                if (this.m_components.get("miniQuest"))
                {
                    _loc_7 = this.m_components.get("miniQuest").addIcon(param1, param2, param3, param4, param5);
                }
            }
            else if (this.m_components.get("quest"))
            {
                _loc_7 = this.m_components.get("quest").addIcon(param1, param2, param3, param4, param5);
            }
            return _loc_7;
        }//end

        public void  hideMiniQuestSprite (String param1 ,Function param2 )
        {
            _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
            if (_loc_3 == ExperimentDefinitions.USE_QUEST_MANAGER)
            {
                if (this.m_components.get("miniQuest"))
                {
                    this.m_components.get("miniQuest").removeIcon(param1, param2);
                }
            }
            else if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").removeIcon(param1, param2);
            }
            return;
        }//end

        public boolean  showFarmvilleXpromoSprite (String param1 ,String param2 ,DisplayObject param3 ,Function param4 ,int param5 =0)
        {
            _loc_6 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FARMVILLE_GIFTING);
            boolean _loc_7 =false ;
            if (_loc_6 == ExperimentDefinitions.FARMVILLE_GIFTING)
            {
                if (this.m_components.get("miniQuest"))
                {
                    this.m_components.get("miniQuest").addIcon(param1, param2, param3, param4, param5);
                    _loc_7 = true;
                }
            }
            return _loc_7;
        }//end

        public void  hideFarmvilleXpromoSprite (String param1 ,Function param2 )
        {
            _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FARMVILLE_GIFTING);
            if (_loc_3 == ExperimentDefinitions.FARMVILLE_GIFTING)
            {
                if (this.m_components.get("miniQuest"))
                {
                    this.m_components.get("miniQuest").removeIcon(param1, param2);
                }
            }
            return;
        }//end

        public void  visitEnergy (int param1 )
        {
            if (this.m_components.get("visitStack"))
            {
                this.m_components.get("visitStack").refresh(false);
            }
            if (this.m_components.get("visitEnergy"))
            {
                this.m_components.get("visitEnergy").updateCounter(param1);
            }
            return;
        }//end

        protected void  clickTrainIcon (MouseEvent event )
        {
            this.m_trainClickCallback();
            return;
        }//end

        protected void  clickQuestIcon (MouseEvent event )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (this.m_components.get("quest"))
            {
                _loc_2 = event.currentTarget.name;
                Sounds.play("click1");
                event.stopPropagation();
                _loc_3 = SagaManager.instance.getSagaNameByQuestName(_loc_2);
                if (_loc_3 != null && !UI.questManagerView.isShown)
                {
                    SagaManager.instance.showDialogForSaga(_loc_3);
                }
                else
                {
                    Global.questManager.pumpActivePopup(_loc_2);
                }
                this.removeBanner(_loc_2);
                Global.ui.resetActionMenu();
                StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.QUEST_ICONS, _loc_2);
            }
            return;
        }//end

        public void  removeBanner (String param1 )
        {
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").removeBanner(param1);
            }
            return;
        }//end

        public void  cleanUpBanner (QuestIndicatorSprite param1 )
        {
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").cleanUpBanner(param1);
            }
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            if (!Global.ui.turnOffHighlightedObject())
            {
                if (UI.hasCursor(Global.ui.getCursorId()) == false)
                {
                    Global.ui.setCursorId(UI.setCursor(null));
                }
            }
            UI.pushBlankCursor();
            return;
        }//end

        protected void  onMouseOut (MouseEvent event )
        {
            UI.popBlankCursor();
            return;
        }//end

        public StreakBonus  streakBonus ()
        {
            return this.m_streakBonus;
        }//end

        public void  addComponent (String param1 ,HUDComponent param2 )
        {
            this.m_components.put(param1,  param2);
            return;
        }//end

        public void  setHUDComponentVisible (String param1 ,boolean param2 )
        {
            if (this.m_components.get(param1))
            {
                (this.m_components.get(param1) as HUDComponent).visible = param2;
            }
            return;
        }//end

        public void  attachComponentToUI (String param1 )
        {
            HUDComponent _loc_2 =null ;
            if (this.m_components.get(param1) && this.m_components.get(param1).parent == this)
            {
                _loc_2 = this.m_components.get(param1);
                this.removeChild(_loc_2);
                Global.ui.addChild(_loc_2);
            }
            return;
        }//end

        public void  attachComponentBackToHUD (String param1 )
        {
            HUDComponent _loc_2 =null ;
            if (this.m_components.get(param1) && this.m_components.get(param1).parent == Global.ui)
            {
                _loc_2 = this.m_components.get(param1);
                Global.ui.removeChild(_loc_2);
                this.addChild(_loc_2);
            }
            return;
        }//end

        public Point  hudGoodsPosition ()
        {
            Point _loc_1 =new Point(0,0);
            if (Global.player.isPremiumGoodsActive && this.m_components.get(COMP_PREMIUM_GOODS))
            {
                _loc_1.x = this.m_components.get(COMP_PREMIUM_GOODS).x;
                _loc_1.y = this.m_components.get(COMP_PREMIUM_GOODS).y;
            }
            else if (this.m_components.get(COMP_GOODS))
            {
                _loc_1.x = this.m_components.get(COMP_GOODS).x;
                _loc_1.y = this.m_components.get(COMP_GOODS).y;
            }
            return _loc_1;
        }//end

        public double  hudGoodsHeight ()
        {
            double _loc_1 =0;
            if (Global.player.isPremiumGoodsActive && this.m_components.get(COMP_PREMIUM_GOODS))
            {
                _loc_1 = this.m_components.get(COMP_PREMIUM_GOODS).height;
            }
            else if (this.m_components.get(COMP_GOODS))
            {
                _loc_1 = this.m_components.get(COMP_GOODS).height;
            }
            return _loc_1;
        }//end

        public void  refreshQuestIcons ()
        {
            if (this.m_components.get("quest"))
            {
                this.m_components.get("quest").refreshIcons();
            }
            return;
        }//end

        public void  removePermsComponent ()
        {
            if (this.m_components.get("permsComp"))
            {
                this.m_components.get("permsComp").hide();
            }
            return;
        }//end

    }




