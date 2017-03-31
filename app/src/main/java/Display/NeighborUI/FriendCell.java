package Display.NeighborUI;

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
import Display.NeighborUI.config.*;
import Display.aswingui.*;
import Display.hud.*;
import Engine.Managers.*;
import Events.*;
import Modules.matchmaking.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class FriendCell extends DataItemCell
    {
        public Friend m_friend ;
        protected JPanel m_alignmentContainer ;
        public String m_uid ;
        protected DisplayObject m_avatar ;
        protected DisplayObject m_pic ;
        protected boolean m_picHasLoaded =false ;
        protected TextField m_newFriendLabel =null ;
        protected boolean m_preload =false ;
        protected DisplayObject m_avatarOver ;
        protected Sprite m_holder =null ;
        protected JWindow m_badgeWindow ;
public static DisplayObject s_savedPlayerPic =null ;

        public  FriendCell (LayoutManager param1)
        {
            super(new FlowLayout(FlowLayout.LEFT, 0));
            Global.ui.addEventListener(UIEvent.UPDATE_FRIENDBAR, this.onUpdateFriendBar);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_preload = Global.ui.m_friendBar.preload;
            param1 =(Object) param1;
            this.m_friend = param1;
            this.m_uid = this.m_friend.uid;
            this.buildCell();
            return;
        }//end

        public void  loadNeighborPortrait ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_ON_VISIT );
            if (this.m_pic != null)
            {
                return;
            }
            if (this.m_friend && this.m_friend.m_profilePic)
            {
                _loc_2 = this.m_friend.m_profilePic;
                if (_loc_2)
                {
                    if (_loc_2.substr(0, 4) != "http")
                    {
                        _loc_3 = Global.getAssetURL(_loc_2);
                    }
                    else
                    {
                        _loc_3 = _loc_2;
                    }
                    if (this.m_uid == Global.player.uid && s_savedPlayerPic)
                    {
                        this.m_pic = s_savedPlayerPic;
                    }
                    else
                    {
                        this.m_pic = LoadingManager.loadFromUrl(_loc_3, {priority:LoadingManager.PRIORITY_LOW, completeCallback:this.onLoadPicSuccess});
                        (this.m_pic as Loader).contentLoaderInfo.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onLoadPicFail, false, 2);
                    }
                    if (this.m_friend instanceof XPromoFriend)
                    {
                        this.m_holder.addChildAt(this.m_pic, this.m_holder.numChildren);
                    }
                    else if (_loc_1 == ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT && this.m_uid == "-1")
                    {
                        this.m_holder.addChildAt(this.m_pic, this.m_holder.numChildren);
                    }
                    else
                    {
                        this.m_holder.addChildAt(this.m_pic, 1);
                    }
                    if (_loc_1 != ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT || this.m_uid != "-1")
                    {
                        this.m_pic.x = 10;
                        this.m_pic.y = 6;
                    }
                }
            }
            Global.ui.updateNeighborBar(this.m_friend.uid);
            return;
        }//end

        public void  showPic ()
        {
            int _loc_1 =0;
            if (this.m_pic)
            {
                if (this.m_holder)
                {
                    if (this.m_friend instanceof XPromoFriend)
                    {
                        this.m_holder.addChildAt(this.m_pic, this.m_holder.numChildren);
                    }
                    else
                    {
                        _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_ON_VISIT);
                        if (_loc_1 == ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT)
                        {
                            if (this.m_uid == "-1")
                            {
                                this.m_holder.addChildAt(this.m_pic, this.m_holder.numChildren);
                            }
                            else
                            {
                                this.m_holder.addChildAt(this.m_pic, 1);
                            }
                        }
                        else
                        {
                            this.m_holder.addChildAt(this.m_pic, 1);
                        }
                    }
                }
            }
            else
            {
                this.loadNeighborPortrait();
            }
            return;
        }//end

        protected void  buildCell ()
        {
            double _loc_2 =0;
            AssetPane _loc_3 =null ;
            JPanel _loc_4 =null ;
            JTextField _loc_5 =null ;
            String _loc_6 =null ;
            double _loc_7 =0;
            JLabel _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            String _loc_11 =null ;
            int _loc_12 =0;
            TextFormat _loc_13 =null ;
            TextField _loc_14 =null ;
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_ON_VISIT );
            this.m_alignmentContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, -8);
            if (!this.m_friend.empty)
            {
                _loc_4 = new JPanel(new SoftBoxLayout(SoftBoxLayout.CENTER, 0, 0));
                _loc_5 = ASwingHelper.makeTextField(this.m_friend.m_name, EmbeddedArt.defaultFontNameBold, 12, 40420, 0);
                if (this.m_friend instanceof XPromoFriend)
                {
                    _loc_6 = " ";
                }
                else
                {
                    _loc_6 = this.m_friend.m_name;
                }
                _loc_7 = HUDThemeManager.getColor(HUDThemeManager.NEIGHBOR_BAR_BG, "nameColor");
                _loc_8 = ASwingHelper.makeLabel(_loc_6, EmbeddedArt.defaultFontNameBold, 12, isNaN(_loc_7) ? (EmbeddedArt.blueTextColor) : (_loc_7));
                _loc_4.append(_loc_8);
                this.m_alignmentContainer.append(_loc_4);
                this.m_holder = new Sprite();
                if (this.m_friend instanceof XPromoFriend)
                {
                    this.m_avatar =(DisplayObject) new EmbeddedArt.emptyAvatar();
                    _loc_2 = TextFieldUtil.getLocaleFontSize(14, 8, [{locale:"de", size:12}, {locale:"fr", size:14}, {locale:"es", size:10}, {locale:"ja", size:10}]);
                    _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "GetWordsWithFriends"), 75, EmbeddedArt.titleFont, TextFormatAlign.CENTER, _loc_2, 16777215, [new GlowFilter(0, 0.5, 3, 3, 10)]);
                    this.m_alignmentContainer.appendAll(ASwingHelper.verticalStrut(70), _loc_3);
                }
                else if (this.m_friend.m_online)
                {
                    this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard_online();
                    this.m_avatarOver =(DisplayObject) new EmbeddedArt.neighborCard_online_over();
                }
                else if (MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid))
                {
                    this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard_buildingBuddy();
                    this.m_avatarOver =(DisplayObject) new EmbeddedArt.neighborCard_buildingBuddy_over();
                }
                else if (this.m_friend.m_firstTimeVisit)
                {
                    this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard_new();
                    this.m_avatarOver =(DisplayObject) new EmbeddedArt.neighborCard_new_over();
                }
                else if (_loc_1 == ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT)
                {
                    if (this.m_uid == "-1")
                    {
                        this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard();
                    }
                    else
                    {
                        this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard();
                        this.m_avatarOver =(DisplayObject) new EmbeddedArt.neighborCard_over();
                    }
                }
                else
                {
                    this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard();
                    this.m_avatarOver =(DisplayObject) new EmbeddedArt.neighborCard_over();
                }
                if (_loc_1 != ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT || this.m_uid != "-1")
                {
                    _loc_10 = new EmbeddedArt.hud_no_profile_pic();
                    this.m_holder.addChildAt(_loc_10, 0);
                    _loc_10.x = 12;
                    _loc_10.y = 6;
                }
                this.m_holder.addChild(this.m_avatar);
                if (this.m_avatarOver)
                {
                    this.m_holder.addChild(this.m_avatarOver);
                    this.m_avatarOver.visible = false;
                }
                ASwingHelper.setSizedBackground(this.m_alignmentContainer, this.m_holder, new Insets(14, 2, 0, 2));
                this.m_alignmentContainer.append(ASwingHelper.verticalStrut(58));
                if (this.m_friend.m_firstTimeVisit)
                {
                    _loc_11 = "   ";
                    if (this.m_friend.m_firstTimeVisit)
                    {
                        _loc_11 = ZLoc.t("Main", "NewFriend");
                    }
                    _loc_12 = 15;
                    if (_loc_11.length > 4)
                    {
                        _loc_12 = 14;
                        if (_loc_11.length > 6)
                        {
                            _loc_12 = 12;
                            if (_loc_11.length > 7)
                            {
                                _loc_12 = 11;
                            }
                        }
                    }
                    _loc_13 = new TextFormat(EmbeddedArt.titleFont, _loc_12, 16738816, true);
                    _loc_14 = new TextField();
                    _loc_14.width = this.m_avatar.width;
                    _loc_14.wordWrap = false;
                    _loc_14.multiline = false;
                    _loc_14.embedFonts = EmbeddedArt.titleFontEmbed;
                    _loc_13.align = TextFormatAlign.CENTER;
                    _loc_14.text = _loc_11;
                    _loc_14.x = 2;
                    _loc_14.y = 62;
                    _loc_14.filters = .get(new GlowFilter(16777215, 1, 2, 2, 10), new DropShadowFilter(2, 90, 0, 0.6));
                    _loc_14.setTextFormat(_loc_13);
                    this.m_alignmentContainer.addChild(_loc_14);
                    this.m_newFriendLabel = _loc_14;
                }
                if (String(this.m_friend.level) != "-1")
                {
                    if (_loc_1 != ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT || this.m_uid != "-1")
                    {
                        this.generateLevels();
                    }
                }
                if (this.m_friend instanceof XPromoFriend)
                {
                    this.addEventListener(MouseEvent.CLICK, this.onXPromo, false, 0, true);
                }
                else
                {
                    this.addEventListener(MouseEvent.CLICK, this.onClickVisitNeighbor, false, 0, true);
                }
                this.addEventListener(MouseEvent.ROLL_OVER, this.onShowActionButtons, false, 0, true);
                this.addEventListener(MouseEvent.ROLL_OUT, this.onRollOut, false, 0, true);
            }
            else
            {
                this.m_avatar =(DisplayObject) new EmbeddedArt.emptyAvatar();
                ASwingHelper.setSizedBackground(this.m_alignmentContainer, this.m_avatar, new Insets(14));
                if (this.m_preload)
                {
                    this.alpha = 0.6;
                    _loc_2 = TextFieldUtil.getLocaleFontSize(12, 10, [{locale:"de", size:11}, {locale:"fr", size:12}, {locale:"es", size:11}, {locale:"ja", size:10}]);
                    _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "Loading"), 75, EmbeddedArt.titleFont, TextFormatAlign.CENTER, _loc_2, 16777215, [new GlowFilter(0, 0.5, 3, 3, 10)]);
                    this.m_alignmentContainer.appendAll(ASwingHelper.verticalStrut(88), _loc_3);
                }
                else
                {
                    _loc_2 = TextFieldUtil.getLocaleFontSize(14, 10, [{locale:"de", size:12}, {locale:"fr", size:14}, {locale:"es", size:12}, {locale:"ja", size:11}]);
                    _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Main", "AddFriend"), 75, EmbeddedArt.titleFont, TextFormatAlign.CENTER, _loc_2, 16777215, [new GlowFilter(0, 0.5, 3, 3, 10)]);
                    this.m_alignmentContainer.appendAll(ASwingHelper.verticalStrut(78), _loc_3);
                    this.addEventListener(MouseEvent.CLICK, this.onClickAddNeighbor, false, 0, true);
                }
            }
            this.appendAll(ASwingHelper.verticalStrut(15), this.m_alignmentContainer);
            this.m_badgeWindow = new JWindow(this);
            this.m_badgeWindow.x = 52;
            this.m_badgeWindow.y = 18;
            this.m_badgeWindow.setContentPane(this.makeBadgePanel());
            ASwingHelper.prepare(this.m_badgeWindow);
            this.addChild(this.m_badgeWindow);
            this.m_badgeWindow.show();
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  generateLevels ()
        {
            TextField _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,-10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.append(ASwingHelper.verticalStrut(22));
            _loc_2.append(ASwingHelper.horizontalStrut(2));
            _loc_2.append(ASwingHelper.verticalStrut(34));
            _loc_1.append(_loc_2);
            this.m_alignmentContainer.append(_loc_1);
            TextFormat _loc_3 =new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD ,14,16777215,true );
            _loc_4 = String(this.m_friend.level);
            if (String(this.m_friend.level).length == 1)
            {
                _loc_4 = " " + _loc_4;
            }
            int _loc_5 =10;
            _loc_6 = new TextField();
            _loc_6.multiline = false;
            _loc_6.wordWrap = false;
            _loc_6.width = 100;
            _loc_6.embedFonts = true;
            _loc_6.text = _loc_4;
            _loc_6.setTextFormat(_loc_3);
            _loc_6.x = 12;
            _loc_6.y = _loc_5;
            _loc_6.filters = .get(new DropShadowFilter(2, 90, 0, 0.9));
            _loc_2.addChild(_loc_6);
            _loc_7 = String(this.m_friend.socialLevel);
            if (String(this.m_friend.socialLevel).length == 1)
            {
                _loc_7 = " " + _loc_7;
            }
            _loc_6 = new TextField();
            _loc_6.multiline = false;
            _loc_6.wordWrap = false;
            _loc_6.width = 100;
            _loc_6.embedFonts = true;
            _loc_6.text = _loc_7;
            _loc_6.setTextFormat(_loc_3);
            _loc_6.x = 45;
            _loc_6.y = _loc_5;
            _loc_6.filters = .get(new DropShadowFilter(2, 90, 0, 0.9));
            _loc_2.addChild(_loc_6);
            return;
        }//end

        public JPanel  makeBadgePanel ()
        {
            AssetPane _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            FriendBadgeConfig _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            if (this.m_friend.rollCall)
            {
                _loc_3 =(DisplayObject) new EmbeddedArt.payroll_neighborBadge_checkin();
                _loc_2 = new AssetPane(_loc_3);
                _loc_1.append(_loc_2);
            }
            if (this.m_friend.collect)
            {
                _loc_3 =(DisplayObject) new EmbeddedArt.payroll_neighborBadge_collect();
                _loc_2 = new AssetPane(_loc_3);
                _loc_1.append(_loc_2);
            }
            _loc_4 =Global.gameSettings().getFriendBadgesByPriority ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (!_loc_5.isValid(this.m_friend))
                {
                    continue;
                }
                if (EmbeddedArt.get(_loc_5.asset))
                {
                    _loc_3 =(DisplayObject) new EmbeddedArt.get(_loc_5.asset);
                    _loc_2 = new AssetPane(_loc_3);
                    _loc_1.append(_loc_2);
                }
            }
            return _loc_1;
        }//end

        protected void  onLoadPicFail (SecurityErrorEvent event )
        {
            if (!this.m_picHasLoaded)
            {
                event.stopPropagation();
                event.stopImmediatePropagation();
                LoadingManager.cancelLoad(this.m_pic);
                this.m_picHasLoaded = true;
            }
            Global.ui.updateNeighborBar(this.m_friend.uid);
            return;
        }//end

        protected void  onLoadPicSuccess (Object param1)
        {
            this.m_picHasLoaded = true;
            if (this.m_uid == Global.player.uid)
            {
                s_savedPlayerPic = this.m_pic;
            }
            Global.ui.updateNeighborBar(this.m_friend.uid);
            return;
        }//end

        private void  onRollOut (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            if (this.m_avatarOver)
            {
                this.m_avatarOver.visible = false;
                this.m_avatar.visible = true;
            }
            return;
        }//end

        public void  onShowActionButtons (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            _loc_2 = this.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            dispatchEvent(new FriendBarSlotEvent(FriendBarSlotEvent.FRIEND_BAR_SLOT_ROLLOVER, this, _loc_3, "friendbar", "contextual", "friend", true));
            if (this.m_avatarOver)
            {
                this.m_avatarOver.visible = true;
                this.m_avatar.visible = false;
            }
            return;
        }//end

        public void  onClickAddNeighbor (MouseEvent event )
        {
            _loc_2 = this.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            event.stopImmediatePropagation();
            event.stopPropagation();
            FrameManager.showTray("invite.php?ref=neighbor_ladder");
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "add_friends");
            return;
        }//end

        public void  onXPromo (MouseEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "xpromo", this.m_friend.m_name);
            (this.m_friend as XPromoFriend).onXPromo(event);
            return;
        }//end

        public void  onClickVisitNeighbor (MouseEvent event )
        {
            if (Global.disableMenu)
            {
                return;
            }
            _loc_2 = this.getGlobalLocation ();
            Point _loc_3 =new Point(_loc_2.x ,_loc_2.y );
            event.stopImmediatePropagation();
            event.stopPropagation();
            dispatchEvent(new FriendBarSlotEvent(FriendBarSlotEvent.FRIEND_BAR_SLOT_CLICK, this, _loc_3, "friendbar", "contextual", "friend", true));
            return;
        }//end

        public String  uid ()
        {
            return this.m_friend ? (this.m_friend.uid) : (null);
        }//end

        public void  clearNewFriend ()
        {
            DisplayObjectContainer _loc_1 =null ;
            int _loc_2 =0;
            this.m_friend.m_firstTimeVisit = false;
            if (this.m_newFriendLabel)
            {
                this.m_newFriendLabel.visible = false;
            }
            if (this.m_avatar && this.m_avatar.parent)
            {
                _loc_1 = this.m_avatar.parent;
                _loc_2 = _loc_1.getChildIndex(this.m_avatar);
                _loc_1.removeChild(this.m_avatar);
                if (MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid))
                {
                    this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard_buildingBuddy();
                }
                else
                {
                    this.m_avatar =(DisplayObject) new EmbeddedArt.neighborCard();
                }
                _loc_1.addChildAt(this.m_avatar, _loc_2);
            }
            if (this.m_avatarOver && this.m_avatarOver.parent)
            {
                _loc_1 = this.m_avatarOver.parent;
                _loc_2 = _loc_1.getChildIndex(this.m_avatarOver);
                _loc_1.removeChild(this.m_avatarOver);
                if (MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid))
                {
                    this.m_avatarOver =(DisplayObject) new EmbeddedArt.neighborCard_buildingBuddy_over();
                }
                else
                {
                    this.m_avatarOver =(DisplayObject) new EmbeddedArt.neighborCard_over();
                }
                this.m_avatarOver.visible = false;
                _loc_1.addChildAt(this.m_avatarOver, _loc_2);
            }
            return;
        }//end

        public void  onUpdateFriendBar (UIEvent event )
        {
            if (!event.label || event.label == this.m_uid)
            {
                this.m_badgeWindow.setContentPane(this.makeBadgePanel());
                ASwingHelper.prepare(this.m_badgeWindow);
            }
            return;
        }//end

    }



