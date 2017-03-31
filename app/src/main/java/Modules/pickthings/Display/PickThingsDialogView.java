package Modules.pickthings.Display;

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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.pickthings.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.flyfish.util.*;
import org.aswing.geom.*;
import org.bytearray.display.*;
import Classes.sim.*;

    public class PickThingsDialogView extends JPanel implements IGameWorldUpdateObserver
    {
        private String m_minigameId ;
        private Array m_things ;
        private Dictionary m_assets ;
        private Array m_thingTypes ;
        private Array m_tracks ;
        private boolean m_again =false ;
        private CustomButton playButton ;
        private boolean m_playing =false ;
        private boolean m_grabbing =false ;
        private boolean m_completed =false ;
        private Bitmap m_grabbedThing ;
        private double m_grabIndex ;
        private JPanel m_field ;
        private RewardCell m_lastReward =null ;
        private TimelineLite m_pulse ;
        private Dictionary m_data ;
        private Dictionary m_config ;
        private double m_cursorId ;
        private Dictionary m_rewardsPanels ;
        private Bitmap m_crane ;
        private Bitmap m_bgCrane ;
        private JPanel upperLeftPanel ;
        private JPanel winPanel ;
        private JPanel actionPanel ;
        private JPanel playPanel ;
        private JPanel lowerBubbleWinHolder ;
        private JPanel lowerBubble ;
        private JPanel cashHolder ;
        private JPanel playButtonHolder ;
        private AssetPane m_rewardText ;
        private AssetPane m_actionText ;
        private JPanel lastRewardHolder ;
        private GenericDialog noticeDlg =null ;
        public static  int NUM_THINGS =25;
        public static  int NUM_ROWS =5;
        public static  int THINGS_PER_ROW =5;
        public static  double CLOSE_BUTTON_OFFSET_X =716;
        public static  double CLOSE_BUTTON_OFFSET_Y =5;
        public static  double BG_OFFSET_X =-146;
        public static  double BG_OFFSET_Y =-200;
        public static  double BG_WIDTH =754;
        public static  double BG_HEIGHT =576;
        public static  double CONVEYER_OFFSET_Y =-26;
        public static  boolean SNEAKY_SCROLL =true ;
        public static  double PLAY_BUTTON_X =177;
        public static  double PLAY_BUTTON_Y =250;
        public static  double PLAY_FIELD_LEFT =325;
        public static  double PLAY_FIELD_RIGHT =680;
        public static  double PLAY_FIELD_TOP =72;
        public static  double PLAY_FIELD_BOTTOM =385;
        public static  double PLAY_FIELD_WIDTH =355;
        public static  double PLAY_FIELD_HEIGHT =313;
        public static  double LOCALIZED_REWARD_FIT_BUFFER =20;
        public static  double THING_WIDTH =64;
        public static  double THING_HEIGHT =64;
        public static  double GAP_X =20;
        public static  double GAP_Y =-5;
        public static  double CRANE_OFFSET =-41;
        public static  double SPEED =20;
        public static  double UPPER_LEFT_WIDTH =248;
        public static  double UPPER_LEFT_HEIGHT =313;
        public static  double REWARD_TEXT_WIDTH =148.8;
        public static  double WIN_BUBBLE_HEIGHT =230;
        public static  double PLAY_BUBBLE_HEIGHT =210;
        public static  double ACTION_BUBBLE_HEIGHT =105;

        public  PickThingsDialogView (Dictionary param1 ,String param2 ,Dictionary param3 ,Dictionary param4 )
        {
            JLabel _loc_16 =null ;
            JPanel _loc_38 =null ;
            JPanel _loc_39 =null ;
            Bitmap _loc_40 =null ;
            String _loc_41 =null ;
            RewardCell _loc_42 =null ;
            this.m_assets = param1;
            this.m_minigameId = param2;
            this.m_data = param3;
            this.m_config = param4;
            this.m_thingTypes = new Array();
            this.m_rewardsPanels = new Dictionary();
            this.m_things = new Array();
            this.m_tracks = new Array();
            this.m_pulse = new TimelineLite();
            this.m_bgCrane = this.getAsset("claw");
            this.m_bgCrane.x = 75;
            this.m_bgCrane.y = -350;
            this.addChild(this.m_bgCrane);
            double _loc_5 =0;
            while (_loc_5 < this.m_config.get("pieceCount"))
            {

                this.m_thingTypes.push(this.getAsset("p" + _loc_5));
                _loc_5 = _loc_5 + 1;
            }
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            this.setPreferredSize(new IntDimension(BG_WIDTH, BG_HEIGHT));
            ASwingHelper.setBackground(this, param1.get("bg"));
            JPanel _loc_6 =new JPanel(new FlowLayout ());
            _loc_6.append(ASwingHelper.horizontalStrut(705));
            _loc_7 = ASwingHelper.makeMarketCloseButton ();
            _loc_6.append(_loc_7);
            _loc_7.addEventListener(MouseEvent.CLICK, this.closeMe);
            this.append(_loc_6);
            this.append(ASwingHelper.verticalStrut(30));
            JPanel _loc_8 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ));
            _loc_8.setPreferredHeight(UPPER_LEFT_HEIGHT);
            _loc_8.append(ASwingHelper.horizontalStrut(30));
            this.upperLeftPanel = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            this.upperLeftPanel.setPreferredSize(new IntDimension(UPPER_LEFT_WIDTH, UPPER_LEFT_HEIGHT));
            this.upperLeftPanel.append(ASwingHelper.verticalStrut(0));
            Array _loc_9 =new Array ();
            Array _loc_10 =new Array ();
            Array _loc_11 =.get(WIN_BUBBLE_HEIGHT ,PLAY_BUBBLE_HEIGHT ,ACTION_BUBBLE_HEIGHT) ;
            double _loc_12 =0;
            while (_loc_12 < 3)
            {

                _loc_38 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
                _loc_10.push(_loc_38);
                _loc_39 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
                _loc_9.push(_loc_39);
                _loc_40 = new ScaleBitmap(this.getAsset("bubble").bitmapData, "auto", true);
                _loc_40.scale9Grid = new Rectangle(18, 18, 225, 93);
                ASwingHelper.setBackground(_loc_39, _loc_40);
                _loc_38.setPreferredSize(new IntDimension(UPPER_LEFT_WIDTH, UPPER_LEFT_HEIGHT));
                _loc_39.setPreferredSize(new IntDimension(UPPER_LEFT_WIDTH, _loc_11.get(_loc_12)));
                _loc_38.append(ASwingHelper.verticalStrut(Math.max(0, UPPER_LEFT_HEIGHT - _loc_11.get(_loc_12) - 90)));
                _loc_38.append(_loc_39);
                _loc_12 = _loc_12 + 1;
            }
            this.winPanel = _loc_10.get(0);
            this.playPanel = _loc_10.get(1);
            this.actionPanel = _loc_10.get(2);
            _loc_13 = _loc_9.get(0);
            _loc_14 = _loc_9.get(1);
            _loc_15 = _loc_9.get(2);
            Array _loc_17 =.get(new GlowFilter(16023072,1,3,3,8,BitmapFilterQuality.LOW ),new DropShadowFilter(1,90,2446451,1,5,5,10)) ;
            if (this.isFtue() || this.m_data.get("freeTurns") > 0)
            {
                _loc_16 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "MiniGame_free_play_bubble_title"), EmbeddedArt.titleFont, 18, EmbeddedArt.titleColor);
            }
            else
            {
                _loc_16 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "MiniGame_play_bubble_title"), EmbeddedArt.titleFont, 20, EmbeddedArt.titleColor);
            }
            _loc_16.setTextFilters(_loc_17);
            _loc_14.append(ASwingHelper.verticalStrut(5));
            _loc_14.append(_loc_16);
            _loc_18 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","MiniGame_play_bubble_description"),UPPER_LEFT_WIDTH-10,EmbeddedArt.defaultFontNameBold,"center",16,5747199);
            _loc_18.setPreferredHeight(60);
            _loc_14.append(_loc_18);
            JPanel _loc_19 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ));
            this.lowerBubble = new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS));
            this.lowerBubble.append(ASwingHelper.horizontalStrut(10));
            this.cashHolder = new JPanel(new CenterLayout());
            JPanel _loc_20 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ));
            DisplayObject _loc_21 =new EmbeddedArt.icon_cash ()as DisplayObject ;
            ASwingHelper.setBackground(this.cashHolder, this.getAsset("cashbubble"));
            this.cashHolder.setPreferredWidth(80);
            _loc_20.append(new AssetPane(_loc_21));
            this.cashHolder.append(_loc_20);
            _loc_22 = ASwingHelper.makeLabel(this.m_config.get( "replayCost") ,"rhino",16,16777215);
            _loc_22.setTextFilters(new Array(new GlowFilter(0, 0.4, 4, 4, 20)));
            _loc_20.append(ASwingHelper.horizontalStrut(2));
            _loc_20.append(_loc_22);
            this.playButtonHolder = new JPanel(new CenterLayout());
            this.lowerBubble.append(this.cashHolder);
            this.lowerBubble.append(ASwingHelper.horizontalStrut(5));
            this.lowerBubble.append(this.playButtonHolder);
            this.makePlayButton();
            _loc_19.append(this.lowerBubble);
            _loc_14.append(_loc_19);
            _loc_23 = this.getAsset("prizeburst");
            _loc_13.append(ASwingHelper.verticalStrut(6));
            JPanel _loc_24 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ));
            _loc_24.setPreferredHeight(_loc_23.height + LOCALIZED_REWARD_FIT_BUFFER);
            JPanel _loc_25 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ));
            _loc_25.setPreferredWidth(REWARD_TEXT_WIDTH);
            _loc_24.append(_loc_25);
            _loc_26 = ASwingHelper.shrinkFontSizeToFit(REWARD_TEXT_WIDTH*0.8,ZLoc.t("Dialogs","MiniGame_win_bubble_title"),EmbeddedArt.titleFont,20,_loc_17,null,null,8);
            _loc_16 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "MiniGame_win_bubble_title"), EmbeddedArt.titleFont, _loc_26, EmbeddedArt.titleColor);
            _loc_16.setTextFilters(_loc_17);
            _loc_25.append(_loc_16);
            this.m_rewardText = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "MiniGame_win_bubble_description", {items:"    "}), REWARD_TEXT_WIDTH, EmbeddedArt.defaultFontNameBold, "center", 16, 5747199);
            _loc_25.append(this.m_rewardText);
            _loc_24.append(ASwingHelper.horizontalStrut(-10));
            this.lastRewardHolder = new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS));
            this.lastRewardHolder.setPreferredHeight(this.getAsset("prizeburst").height);
            this.m_lastReward = new RewardCell(this.m_config.get("rewards").get(0), null, this.getAsset(this.m_config.get("rewards").get(0)), this.getAsset("prizeburst"), null, null);
            this.lastRewardHolder.append(this.m_lastReward);
            _loc_24.append(this.lastRewardHolder);
            _loc_13.append(_loc_24);
            this.lowerBubbleWinHolder = new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS));
            _loc_13.append(this.lowerBubbleWinHolder);
            _loc_16 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "MiniGame_action_bubble_title"), EmbeddedArt.titleFont, 18, EmbeddedArt.titleColor);
            _loc_16.setTextFilters(_loc_17);
            _loc_15.append(ASwingHelper.verticalStrut(5));
            _loc_15.append(_loc_16);
            this.m_actionText = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "MiniGame_action_bubble_description"), UPPER_LEFT_WIDTH - 10, EmbeddedArt.defaultFontNameBold, "center", 16, 5747199);
            this.m_actionText.setPreferredHeight(80);
            _loc_15.append(this.m_actionText);
            this.upperLeftPanel.append(this.playPanel);
            _loc_8.append(this.upperLeftPanel);
            _loc_8.append(ASwingHelper.horizontalStrut(287 - UPPER_LEFT_WIDTH));
            JPanel _loc_27 =new JPanel(new FlowLayout ());
            this.m_field = new JPanel(new FlowLayout());
            _loc_27.setPreferredSize(new IntDimension(PLAY_FIELD_WIDTH + 10, PLAY_FIELD_HEIGHT));
            this.m_field.setPreferredSize(new IntDimension(PLAY_FIELD_WIDTH, PLAY_FIELD_HEIGHT));
            _loc_27.append(this.m_field);
            this.setupField();
            ASwingHelper.prepare(this.m_field);
            ASwingHelper.setEasyBorder(this.m_field, 0, 0, 0, 0);
            _loc_8.append(_loc_27);
            this.append(_loc_8);
            JPanel _loc_28 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ,0));
            _loc_28.append(ASwingHelper.horizontalStrut(166));
            JPanel _loc_29 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,0,SoftBoxLayout.CENTER ));
            _loc_30 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","MiniGame_reward_panel_title"),EmbeddedArt.titleFont,24,30109,AsWingConstants.CENTER);
            _loc_31 = this.getAsset("window");
            ASwingHelper.setBackground(_loc_29, _loc_31);
            _loc_29.setPreferredSize(new IntDimension(_loc_31.width, _loc_31.height));
            _loc_29.append(ASwingHelper.verticalStrut(6));
            _loc_29.append(_loc_30);
            JPanel _loc_32 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            _loc_33 = ZLoc.t("Dialogs","MiniGame_reward_label_common");
            _loc_34 = ZLoc.t("Dialogs","MiniGame_reward_label_uncommon");
            _loc_35 = ZLoc.t("Dialogs","MiniGame_reward_label_rare");
            Object _loc_36 ={common _loc_33 ,uncommon ,rare };
            int _loc_37 =0;
            while (_loc_37 < ((Array)this.m_config.get("rewards")).length())
            {

                _loc_41 = this.m_config.get("rewards").get(_loc_37);
                _loc_42 = new RewardCell(_loc_41, _loc_36.get(this.m_config.get("rarities").get(_loc_37)), this.getAsset(_loc_41), this.getAsset("border"), this.getAsset("counter"), this.getAsset("unknown"));
                _loc_32.append(_loc_42);
                ASwingHelper.prepare(_loc_42);
                this.m_rewardsPanels.put(_loc_41,  _loc_42);
                _loc_37++;
            }
            _loc_29.append(_loc_32);
            _loc_28.append(_loc_29);
            this.append(_loc_28);
            this.updateRewards();
            this.m_crane = this.getAsset("claw");
            this.addChild(this.m_crane);
            this.m_crane.visible = false;
            this.m_crane.y = -550;
            this.m_crane.x = 300;
            Global.world.addObserver(this);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  getCash (Event event )
        {
            if (this.noticeDlg)
            {
                this.noticeDlg.close();
                this.noticeDlg = null;
            }
            FrameManager.navigateTo("money.php?ref=tab");
            return;
        }//end

        public void  makePlayButton ()
        {
            AssetIcon _loc_2 =null ;
            if (this.playButton)
            {
                this.playButtonHolder.remove(this.playButton);
                this.playButton.removeEventListener(MouseEvent.CLICK, this.conditionallyEnablePlayMode);
                this.playButton = null;
            }
            if (this.lowerBubble.parent)
            {
                (this.lowerBubble.parent as JPanel).remove(this.lowerBubble);
            }
            this.lowerBubble = new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS));
            this.lowerBubble.setPreferredHeight(80);
            this.lowerBubble.append(ASwingHelper.horizontalStrut(10));
            _loc_1 = ZLoc.t("Dialogs","MiniGame_play_free_button");
            if (this.m_data.get("freeTurns") > 0)
            {
                this.lowerBubble.append(ASwingHelper.horizontalStrut(this.cashHolder.getPreferredWidth()));
                this.playButton = new CustomButton(_loc_1, null, "GreenButtonUI");
            }
            else
            {
                this.lowerBubble.append(this.cashHolder);
                _loc_2 = new AssetIcon(new EmbeddedArt.icon_cash());
                this.playButton = new CustomButton(_loc_1, _loc_2, "BlueButtonUI");
            }
            this.lowerBubble.append(ASwingHelper.horizontalStrut(5));
            this.lowerBubble.append(this.playButtonHolder);
            this.playButton.addEventListener(MouseEvent.CLICK, this.conditionallyEnablePlayMode, false, 0, false);
            this.playButton.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, 16));
            ASwingHelper.setForcedSize(this.playButton, new IntDimension(135, 30));
            this.playButtonHolder.append(this.playButton);
            return;
        }//end

        private void  conditionallyEnablePlayMode (Event event )
        {
            double turnCost ;
            e = event;
            freeTurnCount = this.m_data.get( "freeTurns") ;
            if (freeTurnCount > 0)
            {
                this.enablePlayMode(e);
            }
            else
            {
                turnCost = this.m_config.get("replayCost");
                if (Global.player.canBuyCash(turnCost, true, false))
                {
                    this .noticeDlg =new GenericDialog (ZLoc .t ("Dialogs","MiniGame_play_purchase_question",{turnCost amount }),"",GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    if (Global.player.canBuyCash(turnCost, true, false))
                    {
                        enablePlayMode(event);
                    }
                }
                return;
            }//end
            );
                    this.noticeDlg.show();
                }
            }
            return;
        }//end

        private void  enablePlayMode (Event event )
        {
            Bitmap _loc_2 =null ;
            Sprite _loc_3 =null ;
            if (this.noticeDlg)
            {
                this.noticeDlg.close();
                this.noticeDlg = null;
            }
            if (event.type == GenericPopupEvent.SELECTED)
            {
                if (((GenericPopupEvent)event).button == 0)
                {
                    return;
                }
            }
            this.updateRewards();
            if (!this.m_playing && !this.m_grabbing)
            {
                if (this.m_completed)
                {
                    this.setupField(true);
                    this.m_completed = false;
                }
                if (this.m_data.get("freeTurns") > 0 || Global.player.cash >= this.m_config.get("replayCost"))
                {
                    this.m_playing = true;
                    _loc_2 = this.getAsset("pickState");
                    _loc_3 = new Sprite();
                    _loc_3.addChild(_loc_2);
                    _loc_3.mouseEnabled = false;
                    _loc_3.mouseChildren = false;
                    _loc_2.x = (-_loc_2.width) / 2;
                    _loc_2.y = -_loc_2.height;
                    this.m_cursorId = UI.setCursor(_loc_3);
                    if (this.playPanel.parent)
                    {
                        this.upperLeftPanel.remove(this.playPanel);
                    }
                    else
                    {
                        this.upperLeftPanel.remove(this.winPanel);
                    }
                    this.upperLeftPanel.append(this.actionPanel);
                    ASwingHelper.prepare(this.upperLeftPanel.parent);
                }
                else
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                }
            }
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public boolean  isFtue ()
        {
            return this.m_data["numPlays"] == 1 && this.m_data.get("rewardCount") == 0;
        }//end

        public void  getDatThing (MouseEvent event )
        {
            if (this.m_grabbing)
            {
                return;
            }
            if (!this.m_playing)
            {
                if (!this.m_pulse.active)
                {
                    this.m_pulse = new TimelineLite();
                    this.m_pulse.insert(TweenLite.to(this.playButton, 0.15, {alpha:0.7}), 0);
                    this.m_pulse.insert(TweenLite.to(this.playButton, 0.15, {alpha:1}), 0.45);
                    this.m_pulse.play();
                }
                return;
            }
            this.m_grabIndex = this.m_things.indexOf(event.currentTarget as PickableThing);
            this.m_playing = false;
            this.m_grabbing = true;
            _loc_2 =(PickableThing) event.currentTarget;
            StatsManager.count("mystery_game", this.m_minigameId, "pick", String(_loc_2.type));
            this.m_crane.visible = true;
            UI.removeCursor(this.m_cursorId);
            this.m_cursorId = 0;
            return;
        }//end

        private void  giveReward ()
        {
            this.m_again = true;
            this.m_grabbing = false;
            _loc_1 = PickThingsManager.instance.getNumRewards(this.m_minigameId);
            _loc_2 = PickThingsManager.instance.getNewReward(this.m_minigameId,this.m_grabIndex);
            _loc_3 = _loc_2.get(0);
            _loc_4 = MathUtils.parseInteger(_loc_2.get(1) );
            _loc_5 = ZLoc.t("Items",_loc_3 +"_friendlyName");
            _loc_6 = _loc_4>1? (_loc_4.toString()) : ("a");
            this.upperLeftPanel.remove(this.actionPanel);
            this.makePlayButton();
            this.m_rewardText.setAsset(ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "MiniGame_win_bubble_description", {qty:_loc_6, item:_loc_5}), REWARD_TEXT_WIDTH, EmbeddedArt.defaultFontNameBold, "center", 16, 5747199).getAsset());
            this.lowerBubbleWinHolder.append(this.lowerBubble);
            this.upperLeftPanel.append(this.winPanel);
            this.m_lastReward.changeAsset(this.getAsset(_loc_3));
            if (this.isFtue())
            {
                if (Global.player.cash >= this.m_config.get("replayCost"))
                {
                    this.noticeDlg = new GenericDialog(ZLoc.t("Dialogs", "MiniGame_ftue_firstwin_with_cash", {item:_loc_5}), "", GenericDialogView.TYPE_CUSTOM_OK, this.conditionallyEnablePlayMode, "", "", true, 0, "", null, ZLoc.t("Dialogs", "MiniGame_ftue_cash_btn", {price:this.m_config.get("replayCost")}));
                }
                else
                {
                    this.noticeDlg = new GenericDialog(ZLoc.t("Dialogs", "MiniGame_ftue_firstwin_no_cash", {item:_loc_5}), "", GenericDialogView.TYPE_CUSTOM_OK, this.getCash, "", "", true, 0, "", null, ZLoc.t("Dialogs", "MiniGame_ftue_no_cash_btn"));
                }
                this.noticeDlg.show();
            }
            if ((_loc_1 + 1) < NUM_THINGS)
            {
                this.updateRewards();
            }
            else
            {
                this.m_completed = true;
                this.getRewardPanel(_loc_3).updateCount(this.m_config.get("distribution").get(_loc_3));
            }
            ASwingHelper.prepare(this.upperLeftPanel.parent);
            return;
        }//end

        public PickableThing  getThing (int param1 )
        {
            return this.m_things.get(param1);
        }//end

        public void  update (double param1 )
        {
            PickableThing _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            Bitmap _loc_9 =null ;
            PickableThing _loc_10 =null ;
            _loc_2 = param1*SPEED ;
            if (this.noticeDlg && !this.noticeDlg.parent)
            {
                Global.stage.addChild(this.noticeDlg);
            }
            if (!this.m_playing && !this.m_grabbing)
            {
                this.m_bgCrane.y = this.m_bgCrane.y + _loc_2 * 8;
                if (this.m_bgCrane.y > -350)
                {
                    this.m_bgCrane.y = -350;
                }
                return;
            }
            boolean _loc_3 =true ;
            int _loc_4 =0;
            if (this.m_bgCrane.visible)
            {
                this.m_bgCrane.y = this.m_bgCrane.y - _loc_2 * 12;
                if (this.m_bgCrane.y < -550)
                {
                    this.m_bgCrane.visible = false;
                    this.m_crane.visible = true;
                }
            }
            else if (this.m_grabbing)
            {
                _loc_6 =(PickableThing) this.m_things.get(this.m_grabIndex);
                if (_loc_6.visible)
                {
                    this.m_crane.x = _loc_6.x + PLAY_FIELD_LEFT + CRANE_OFFSET;
                    this.m_crane.y = this.m_crane.y + _loc_2 * 12;
                    if (this.m_crane.y > _loc_6.y - 407)
                    {
                        _loc_6.visible = false;
                        this.m_grabbedThing = new Bitmap(((Bitmap)this.m_thingTypes.get(_loc_6.type)).bitmapData.clone());
                        this.addChild(this.m_grabbedThing);
                        this.m_crane.y = _loc_6.y - 407;
                        this.m_grabbedThing.x = _loc_6.x + PLAY_FIELD_LEFT - 2;
                        this.m_grabbedThing.y = _loc_6.y + PLAY_FIELD_TOP;
                    }
                }
                else
                {
                    this.m_crane.y = this.m_crane.y - _loc_2 * 12;
                    this.m_grabbedThing.y = this.m_grabbedThing.y - _loc_2 * 12;
                    if (this.m_crane.y < -550)
                    {
                        this.m_crane.visible = false;
                        this.m_bgCrane.visible = true;
                        this.m_bgCrane.y = -550;
                        this.removeChild(this.m_grabbedThing);
                        this.m_grabbedThing = null;
                        this.giveReward();
                    }
                }
            }
            double _loc_5 =0;
            while (_loc_5 < NUM_ROWS)
            {

                _loc_7 = 0;
                while (_loc_7 < 5)
                {

                    _loc_9 = this.m_tracks.get(5 * _loc_5 + _loc_7);
                    if (_loc_3)
                    {
                        _loc_9.x = _loc_9.x - _loc_2 * (_loc_5 / 8 + 1);
                        if (_loc_9.x < -_loc_9.width)
                        {
                            _loc_9.x = _loc_9.x + _loc_9.width * 5;
                        }
                    }
                    else
                    {
                        _loc_9.x = _loc_9.x + _loc_2 * (_loc_5 / 8 + 1);
                        if (_loc_9.x > PLAY_FIELD_WIDTH)
                        {
                            _loc_9.x = _loc_9.x - _loc_9.width * 5;
                        }
                    }
                    _loc_7 = _loc_7 + 1;
                }
                _loc_8 = 0;
                while (_loc_8 < THINGS_PER_ROW)
                {

                    _loc_10 =(PickableThing) this.m_things.get(_loc_4);
                    if (_loc_10.visible)
                    {
                        if (_loc_3)
                        {
                            _loc_10.x = _loc_10.x - _loc_2 * (_loc_5 / 4 + 1);
                            if (_loc_10.x < -THING_WIDTH)
                            {
                                _loc_10.x = _loc_10.x + (THING_WIDTH + GAP_X) * THINGS_PER_ROW;
                            }
                        }
                        else
                        {
                            _loc_10.x = _loc_10.x + _loc_2 * (_loc_5 / 4 + 1);
                            if (_loc_10.x > PLAY_FIELD_WIDTH)
                            {
                                _loc_10.x = _loc_10.x - (THING_WIDTH + GAP_X) * THINGS_PER_ROW;
                            }
                        }
                    }
                    _loc_4++;
                    _loc_8 = _loc_8 + 1;
                }
                _loc_3 = !_loc_3;
                _loc_5 = _loc_5 + 1;
            }
            if (this.noticeDlg && !this.noticeDlg.parent)
            {
                Global.stage.addChild(this.noticeDlg);
            }
            return;
        }//end

        private void  clearField ()
        {
            int _loc_1 =0;
            while (_loc_1 < NUM_THINGS)
            {

                (this.m_things.get(_loc_1) as PickableThing).cleanup();
                _loc_1++;
            }
            this.m_things = new Array();
            return;
        }//end

        private void  setupField (boolean param1 =false )
        {
            double _loc_5 =0;
            Bitmap _loc_6 =null ;
            double _loc_7 =0;
            int _loc_8 =0;
            double _loc_9 =0;
            Bitmap _loc_10 =null ;
            PickableThing _loc_11 =null ;
            this.m_things = new Array();
            this.m_tracks = new Array();
            double _loc_2 =0;
            boolean _loc_3 =true ;
            double _loc_4 =0;
            while (_loc_4 < NUM_ROWS)
            {

                _loc_5 = Math.floor(Math.random() * THING_WIDTH);
                _loc_6 = this.getAsset("conveyer");
                this.m_field.addChild(_loc_6);
                _loc_6.y = 10 + _loc_4 * (THING_HEIGHT + GAP_Y) + THING_HEIGHT + CONVEYER_OFFSET_Y;
                _loc_7 = 0;
                while (_loc_7 < 5)
                {

                    _loc_10 = this.getAsset("tracks");
                    this.m_field.addChild(_loc_10);
                    _loc_10.y = 10 + _loc_4 * (THING_HEIGHT + GAP_Y) + THING_HEIGHT + CONVEYER_OFFSET_Y;
                    if (_loc_3)
                    {
                        _loc_10.x = _loc_10.width * _loc_7;
                    }
                    else
                    {
                        _loc_10.x = _loc_10.width * (_loc_7 - 1);
                    }
                    this.m_tracks.push(_loc_10);
                    _loc_7 = _loc_7 + 1;
                }
                _loc_8 = 0;
                if (param1 !=null)
                {
                    _loc_8 = 325;
                }
                _loc_9 = 0;
                while (_loc_9 < THINGS_PER_ROW)
                {

                    _loc_11 = new PickableThing((Bitmap)this.m_thingTypes.get(this.m_data.get("pieces").get(_loc_2)), this.m_data.get("pieces").get(_loc_2));
                    _loc_11.addEventListener(MouseEvent.MOUSE_DOWN, this.getDatThing);
                    _loc_11.addEventListener(MouseEvent.MOUSE_OVER, this.highlightThing);
                    _loc_11.addEventListener(MouseEvent.MOUSE_OUT, this.unhighlightThing);
                    if (_loc_3)
                    {
                        _loc_11.x = _loc_5 + _loc_9 * (THING_WIDTH + GAP_X) + _loc_8;
                    }
                    else
                    {
                        _loc_11.x = PLAY_FIELD_WIDTH - THING_WIDTH - _loc_5 - _loc_9 * (THING_WIDTH + GAP_X) - _loc_8;
                    }
                    _loc_11.y = _loc_4 * (THING_HEIGHT + GAP_Y);
                    this.m_field.addChild(_loc_11);
                    this.m_things.push(_loc_11);
                    _loc_11.visible = !this.m_data.get("boardState").get(_loc_2);
                    _loc_2 = _loc_2 + 1;
                    _loc_9 = _loc_9 + 1;
                }
                _loc_3 = !_loc_3;
                _loc_4 = _loc_4 + 1;
            }
            return;
        }//end

        public Bitmap  getAsset (String param1 )
        {
            return new Bitmap(((Bitmap)this.m_assets.get(param1)).bitmapData.clone());
        }//end

        public RewardCell  getRewardPanel (String param1 )
        {
            return this.m_rewardsPanels.get(param1);
        }//end

        public void  closeMe (MouseEvent event )
        {
            if (this.noticeDlg)
            {
                this.noticeDlg.close();
                this.noticeDlg = null;
            }
            if (this.m_cursorId)
            {
                UI.removeCursor(this.m_cursorId);
                this.m_cursorId = 0;
            }
            dispatchEvent(new Event(Event.CLOSE, false, false));
            return;
        }//end

        public void  updateRewards ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_data.get("rewardsEarned").size(); i0++)
            {
            		_loc_1 = this.m_data.get("rewardsEarned").get(i0);

                this.getRewardPanel(_loc_1).updateCount(this.m_data.get("rewardsEarned").get(_loc_1));
            }
            return;
        }//end

        public void  checkFTUE ()
        {
            if (this.isFtue())
            {
                if (this.noticeDlg)
                {
                    this.noticeDlg.close();
                    this.noticeDlg = null;
                }
                this.noticeDlg = new GenericDialog(ZLoc.t("Dialogs", "MiniGame_ftue_intro"), "", GenericDialogView.TYPE_CUSTOM_OK, this.conditionallyEnablePlayMode, "", "", true, 0, "", null, ZLoc.t("Dialogs", "MiniGame_ftue_free_btn"));
                this.noticeDlg.show();
            }
            return;
        }//end

        public void  highlightThing (MouseEvent event )
        {
            if (this.m_playing)
            {
                (event.currentTarget as PickableThing).filters = .get(new GlowFilter(EmbeddedArt.HIGHLIGHT_COLOR, 1, 8, 8, 20));
            }
            return;
        }//end

        public void  unhighlightThing (MouseEvent event )
        {
            (event.currentTarget as PickableThing).filters = new Array();
            return;
        }//end

        public int  playColor ()
        {
            return this.playButton.getForeground().getRGB();
        }//end

        public void  playColor (int param1 )
        {
            this.playButton.setForeground(new ASColor(param1));
            return;
        }//end

    }



