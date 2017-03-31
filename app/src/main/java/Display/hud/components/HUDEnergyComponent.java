package Display.hud.components;

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

import Classes.*;
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Display.hud.*;
import Engine.Managers.*;
import GameMode.*;
import Modules.stats.types.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.geom.*;
import Classes.sim.*;
import Classes.sim.*;
import com.xinghai.Debug;

    public class HUDEnergyComponent extends HUDComponent
    {
        private JPanel m_energyHolder ;
        public JTextField m_energyText ;
        public JTextField m_energyMoreText ;
        private boolean m_bEnergyWindowDown =false ;
        private JWindow m_moreEnergyWindow ;
        private ColorTransform m_colorTransform =new ColorTransform(0.2,1,0.2);
        private ColorTransform m_normalTransform =new ColorTransform(1,1,1);
        private Timer m_disableTimer ;
        private DisplayObject m_disableSprite ;
        protected int m_currentEnergy =0;
        public DisplayObject m_energySlider =HUDThemeManager.getAsset(HUDThemeManager.ENERGY_BAR );
        private Timer m_energyMoreTimer ;

        public  HUDEnergyComponent (Function param1)
        {
            //this.m_colorTransform = new ColorTransform(0.2, 1, 0.2);
            //this.m_normalTransform = new ColorTransform(1, 1, 1);
            //this.m_energySlider = HUDThemeManager.getAsset(HUDThemeManager.ENERGY_BAR);
            return;
        }//end

         protected void  buildComponent ()
        {
            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 0);
            Sprite _loc_1 =new Sprite ();
            addChild(_loc_1);
            this.m_moreEnergyWindow = new JWindow(_loc_1);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            _loc_3 = TextFieldUtil.getLocaleFontSize(16,12,.get({localesize"ja",11)});
            _loc_4 = ASwingHelper.makeTextField(ZLoc.t("Main","GetEnergy"),EmbeddedArt.defaultFontNameBold ,_loc_3 ,16777215,-1);
            _loc_4.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            //_loc_4.setBorder(new EmptyBorder(null, new Insets(0, 15, 5)));
            _loc_4.setBorder(new EmptyBorder(null, new Insets(3)));

            _loc_2.append(_loc_4);
            DisplayObject _loc_5 =new EmbeddedArt.getMoreEnergyPanel ()as DisplayObject ;
            ASwingHelper.setSizedBackground(_loc_2, _loc_5);
            this.m_moreEnergyWindow.setContentPane(_loc_2);
            ASwingHelper.prepare(this.m_moreEnergyWindow);
            this.m_moreEnergyWindow.setX(30);
            this.m_moreEnergyWindow.setY(-10);
            this.m_moreEnergyWindow.show();
            Sprite _loc_6 =new Sprite ();
            addChild(_loc_6);
            _loc_6.graphics.beginFill(0);
            _loc_6.graphics.drawRect(30, 35, _loc_5.width, 50);
            _loc_6.graphics.endFill();
            _loc_1.mask = _loc_6;
            this.m_disableSprite =(DisplayObject) new EmbeddedArt.hud_energyUnlimited();
            this.m_disableSprite.visible = false;
            this.m_disableSprite.y = this.m_disableSprite.y + 4;
            addChildAt(this.m_disableSprite, numChildren);
            _loc_7 = TextFieldUtil.getLocaleFontSize(11,11,.get({localesize"ja",9)});
            TextFormat _loc_8 =new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD ,18,16777215,true ,false ,false ,null ,null ,TextFormatAlign.LEFT );
            TextFormat _loc_9 =new TextFormat(EmbeddedArt.defaultFontNameBold ,_loc_7 ,16777215,true ,false ,false ,null ,null ,TextFormatAlign.LEFT );
            _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_energyMoreText = ASwingHelper.makeTextField(" ", EmbeddedArt.defaultFontNameBold, _loc_7, EmbeddedArt.brownTextColor, 0, _loc_9);
            _loc_10.setPreferredWidth(120);
            _loc_10.appendAll(ASwingHelper.horizontalStrut(10), this.m_energyMoreText);
            this.m_energyHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM, -6);
            this.m_energyHolder.setPreferredWidth(120);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_energyText = ASwingHelper.makeTextField("0", EmbeddedArt.DEFAULT_FONT_NAME_BOLD, 18, 16777215, 0, _loc_8);
            this.m_energyText.filters = .get(new GlowFilter(0, 0.5, 3, 3, 10));
            _loc_11.append(this.m_energyText);
            this.m_energyHolder.appendAll(_loc_10, _loc_11, ASwingHelper.verticalStrut(23));
            JPanel _loc_12 =ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_12.setBackgroundDecorator(new MarginBackground(this.m_energySlider, new Insets(8)));
            _loc_12.setPreferredSize(new IntDimension(this.m_energySlider.width + 5, this.m_energySlider.height + 8));
            _loc_12.setMaximumSize(new IntDimension(this.m_energySlider.width + 5, this.m_energySlider.height + 8));
            _loc_12.setMinimumSize(new IntDimension(this.m_energySlider.width + 5, this.m_energySlider.height + 8));
            _loc_12.appendAll(ASwingHelper.horizontalStrut(50), this.m_energyHolder);
            m_jPanel.append(_loc_12);
            return;
        }//end

         public void  refresh (boolean param1 )
        {
            super.refresh(param1);
            this.m_energySlider.scaleX = 1;
            return;
        }//end

         protected void  attachToolTip ()
        {
            m_toolTip = new ToolTip(this.chooseToolTip);
            m_toolTip.attachToolTip(this);
            m_toolTip.hideCursor = true;
            return;
        }//end

        protected String  chooseToolTip ()
        {
            String _loc_1 =null ;
            if (!Global.player.canDeductEnergy())
            {
                _loc_1 = this.getUnlimitedTooltipStr();
            }
            else
            {
                _loc_1 = ZLoc.t("Main", "Energy_ToolTip", {energy:String(this.m_currentEnergy), maxenergy:String(Global.player.energyMax), message:this.getEnergyTooltipMessage()});
            }
            return _loc_1;
        }//end

        private String  getUnlimitedTooltipStr ()
        {
            String _loc_1 =null ;
            _loc_2 = GameUtil.formatMinutesSeconds(Global.player.getFreeEnergyTimeRemaining());
            if (_loc_2.charAt(0) == "0")
            {
                _loc_2 = _loc_2.slice(1);
            }
            _loc_1 = ZLoc.t("Main", "Energy_ToolTip_Unlimited", {duration:_loc_2});
            return _loc_1;
        }//end

        protected String  getEnergyTooltipMessage ()
        {
            Dictionary _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            ItemInstance _loc_8 =null ;
            String _loc_9 =null ;
            int _loc_10 =0;
            Array _loc_11 =null ;
            String _loc_12 =null ;
            String _loc_1 ="";
            String _loc_2 ="";
            if (Global.player)
            {
                if (Global.player.getFlag(HUD.SHOW_ENERGY_MODIFIER_FLAG).value == 1)
                {
                    _loc_3 = new Dictionary();
                    _loc_4 = new Array();
                    for(int i0 = 0; i0 < Global.player.m_energyModifiers.size(); i0++)
                    {
                    	_loc_5 = Global.player.m_energyModifiers.get(i0);

                        _loc_7 = int(_loc_5);
                        _loc_8 = null;
                        if (_loc_7 != 0)
                        {
                            _loc_8 =(ItemInstance) Global.world.getObjectById(_loc_7);
                        }
                        _loc_10 = 0;
                        if (_loc_8 && _loc_8.getItem())
                        {
                            _loc_9 = _loc_8.getItemFriendlyName();
                            _loc_10 = _loc_8.getItem().getPositiveStreakMaxEffect();
                            if (_loc_8 instanceof PowerStation && _loc_2 == "")
                            {
                                _loc_2 = ZLoc.t("Dialogs", "Energy_ToolTip_Message_PowerStation") + "  \n";
                            }
                            if (_loc_4.indexOf(_loc_9) == -1)
                            {
                                _loc_4.unshift(_loc_9);
                            }
                        }
                        else
                        {
                            _loc_9 = ZLoc.t("Main", "Energy_ToolTip_BonusRecievedFromOther");
                            _loc_10 = Global.player.getEnergyModifierValueByName(_loc_5);
                            if (_loc_4.indexOf(_loc_9) == -1)
                            {
                                _loc_4.push(_loc_9);
                            }
                        }
                        if (!_loc_3.get(_loc_9))
                        {
                            _loc_3.put(_loc_9,  .get(Global.player.getEnergyModifierValueByName(_loc_5), _loc_10));
                            continue;
                        }
                        _loc_11 = _loc_3.get(_loc_9);
                        _loc_11.put(0,  _loc_11.get(0) + Global.player.getEnergyModifierValueByName(_loc_5));
                        _loc_11.put(1,  _loc_11.get(1) + _loc_10);
                    }
                    _loc_6 = 0;
                    while (_loc_6 < _loc_4.length())
                    {

                        _loc_12 = _loc_4.get(_loc_6);
                        new EnergyToggleDeco("deco_basketballcourt");
                        _loc_1 = _loc_1 + (ZLoc.t("Main", "Energy_ToolTip_BonusRecievedFrom", {source:_loc_12, bonus:_loc_3.get(_loc_12).get(0), bonusMax:_loc_3.get(_loc_12).get(1)}) + "  \n");
                        _loc_6++;
                    }
                }
            }
            _loc_1 = _loc_1 + _loc_2;
            return _loc_1;
        }//end

         protected void  setCounter ()
        {
            m_counter =new HUDCounter (void  (double param1 )
            {
                if (m_moreEnergyWindow)
                {
                    if (param1 <= Global.gameSettings().getInt("energyWarningAmount"))
                    {
                        m_bEnergyWindowDown = true;
                        m_moreEnergyWindow.addEventListener(MouseEvent.CLICK, onMoreEnergyClick, false, 0, true);
                        TweenLite.to(m_moreEnergyWindow, 0.5, {y:30});
                    }
                    else
                    {
                        m_bEnergyWindowDown = false;
                        m_moreEnergyWindow.removeEventListener(MouseEvent.CLICK, onMoreEnergyClick);
                        TweenLite.to(m_moreEnergyWindow, 0.5, {y:-10});
                    }
                }
                if (Global.player.getFlag(HUD.SHOW_ENERGY_MODIFIER_FLAG).value == 1)
                {
                    m_energyText.setText(String(param1) + "/" + String(Global.player.energyMax));
                }
                else
                {
                    m_energyText.setText(String(param1));
                }
                if (!Global.player.canDeductEnergy())
                {
                    m_energyText.setText(" ");
                }
                m_currentEnergy = param1;
                calcSliderSize(param1);
                return;
            }//end
            );
            m_counter.value = Global.player.energy;
            this.refresh(false);
            this.m_energyMoreTimer = new Timer(1000);
            this.m_energyMoreTimer.addEventListener(TimerEvent.TIMER, this.updateEnergyCounter);
            this.m_energyMoreTimer.start();
            return;
        }//end

        private void  calcSliderSize (double param1 )
        {
            double _loc_4 =0;
            TextFormat _loc_6 =null ;
            int _loc_2 =35;
            _loc_3 =Global.player.energyMax ;
            _loc_5 =(MovieClip) this.m_energySlider.get( "sliderMask");
            if (param1 > Global.player.energyMax)
            {
                _loc_6 = new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD, 18, 65280, true, false, false, null, null, TextFormatAlign.LEFT);
                this.m_energyText.setTextFormat(_loc_6);
                _loc_4 = 1;
            }
            else
            {
                _loc_6 = new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD, 18, 16777215, true, false, false, null, null, TextFormatAlign.LEFT);
                this.m_energyText.setTextFormat(_loc_6);
                _loc_4 = param1 / _loc_3;
            }
            _loc_5.x = (_loc_4 - 1) * _loc_5.width + _loc_2;
            return;
        }//end

        public void  updateEnergyCounter (TimerEvent event )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            String _loc_6 =null ;
            String _loc_7 =null ;
            _loc_2 =Global.player ;
            if (_loc_2.energy < _loc_2.energyMax)
            {
                _loc_3 = Global.gameSettings().getNumber("energyRegenerationSeconds");
                _loc_4 = int(Math.floor(GlobalEngine.getTimer() / 1000) - Global.player.lastEnergyCheck);
                _loc_5 = _loc_3 - _loc_4;
                if (_loc_5 < 0)
                {
                    _loc_5 = _loc_5 + _loc_3;
                    _loc_2.regenerateEnergy();
                }
                _loc_6 = GameUtil.formatMinutesSeconds(_loc_5);
                if (_loc_6.charAt(0) == "0")
                {
                    _loc_6 = _loc_6.slice(1);
                }
                this.m_energyMoreText.setText(ZLoc.t("Main", "EnergyCounter", {time:_loc_6}));
                if (this.m_energyMoreText.getText() == "")
                {
                    this.m_energyMoreText.setText(" ");
                }
                this.m_energyMoreText.visible = true;
                this.calcSliderSize(_loc_2.energy);
            }
            else
            {
                this.m_energyMoreText.visible = false;
                this.m_energyMoreText.setText("");
            }
            if (!_loc_2.canDeductEnergy())
            {
                this.m_energyMoreText.visible = true;
                _loc_7 = GameUtil.formatMinutesSeconds(_loc_2.getFreeEnergyTimeRemaining(), true);
                this.m_energyMoreText.setText("  " + _loc_7);
                if (this.m_disableTimer == null)
                {
                    this.m_disableTimer = new Timer(_loc_2.getFreeEnergyTimeRemaining() * 1000 + 100);
                    this.m_disableTimer.addEventListener(TimerEvent.TIMER, this.onEnergyTimer);
                    this.m_disableTimer.start();
                    this.m_disableSprite.visible = true;
                    this.m_energySlider.visible = false;
                    this.m_moreEnergyWindow.visible = false;
                    this.m_energyText.setText(" ");
                }
            }
            return;
        }//end

        private void  onEnergyTimer (TimerEvent event )
        {
            this.m_disableTimer.stop();
            this.m_disableTimer.removeEventListener(TimerEvent.TIMER, this.onEnergyTimer);
            this.m_energySlider.visible = true;
            this.m_disableSprite.visible = false;
            this.setCounter();
            this.m_disableTimer = null;
            return;
        }//end

        private void  onMoreEnergyClick (MouseEvent event )
        {
            if (Global.world.getTopGameMode() instanceof GMRemodel)
            {
                Global.world.addGameMode(new GMPlay(), true);
            }
            UI.displayCatalog(new CatalogParams("extras").setItemName("energy_2"), false, true);
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, "add_energy");
            return;
        }//end

    }



