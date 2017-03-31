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
import Display.NeighborUI.helpers.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;

    public class FriendBar extends JPanel
    {
        protected  int BLANK_FRIEND_INTERVAL =6;
        protected  int BLANK_FRIEND_INITIAL =8;
        protected  int Y_POS =345;
        public Array m_slots ;
        public boolean m_nghbrActionsMenuCreated ;
        protected FriendBarScrollingList m_friendscroll ;
        private int m_startX ;
        protected boolean m_preload =false ;
        public static  int SLOT_WIDTH =70;

        public  FriendBar ()
        {
            this.m_slots = new Array();
            super(new FlowLayout(FlowLayout.LEFT, 4, 5, true));
            this.m_nghbrActionsMenuCreated = false;
            return;
        }//end

        private void  makeNavs ()
        {
            return;
        }//end

        public boolean  preload ()
        {
            return this.m_preload;
        }//end

        public void  preload (boolean param1 )
        {
            this.m_preload = param1;
            return;
        }//end

        protected void  onSlotClick (FriendBarSlotEvent event )
        {
            _loc_2 = event.slot ;
            event.stopImmediatePropagation();
            event.stopPropagation();
            if (_loc_2.uid && _loc_2.uid != Global.player.uid)
            {
                dispatchEvent(new FriendBarSlotEvent(FriendBarSlotEvent.FRIEND_BAR_SLOT_CLICK, _loc_2, event.friendPoint, "friendbar", "contextual", "slot"));
            }
            return;
        }//end

        protected void  onGiftClick (FriendBarSlotEvent event )
        {
            _loc_2 = event.slot ;
            if (_loc_2.uid != Global.player.uid)
            {
                dispatchEvent(new FriendBarSlotEvent(FriendBarSlotEvent.FRIEND_BAR_GIFT_CLICK, _loc_2, event.friendPoint, "friendbar", "contextual", "slot"));
            }
            return;
        }//end

        protected void  onVisitClick (FriendBarSlotEvent event )
        {
            _loc_2 = event.slot ;
            if (_loc_2.uid != Global.player.uid)
            {
                dispatchEvent(new FriendBarSlotEvent(FriendBarSlotEvent.FRIEND_BAR_VISIT_CLICK, _loc_2, event.friendPoint, "friendbar", "contextual", "slot"));
            }
            return;
        }//end

        public void  updateNeighbors (Array param1 )
        {
            _loc_2 = this.sortFriends(param1 );
            this.m_friendscroll.data = _loc_2;
            this.m_friendscroll.moveRight(1);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  friendVisited (String param1 )
        {
            this.m_friendscroll.friendVisited(param1);
            return;
        }//end

        protected void  onClickAddNeighbor (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            FrameManager.showTray("invite.php?ref=neighbor_ladder");
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "add_friends");
            return;
        }//end

        protected JPanel  makeLeftAddFriend ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-8);
            DisplayObject _loc_2 =new EmbeddedArt.emptyAvatar ()as DisplayObject ;
            ASwingHelper.setSizedBackground(_loc_1, _loc_2, new Insets(14));
            _loc_3 = TextFieldUtil.getLocaleFontSize(14,10,[{localesize"de",12locale},{"fr",size14},locale{"es",12},{"ja",11});
            _loc_4 = ASwingHelper.makeMultilineText(ZLoc.t("Main","AddFriend"),75,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,_loc_3 ,16777215,.get(new GlowFilter(0,0.5,3,3,10)) );
            _loc_1.appendAll(ASwingHelper.verticalStrut(78), _loc_4);
            _loc_1.addEventListener(MouseEvent.CLICK, this.onClickAddNeighbor, false, 0, true);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        public void  addPlayerToFriends (Array param1 )
        {
            int _loc_2 =0;
            Player _loc_3 =null ;
            int _loc_8 =0;
            int _loc_9 =0;
            param1.reverse();
            _loc_4 =Global.player.xp ;
            _loc_2 = 0;
            while (_loc_2 < param1.length())
            {

                _loc_3 = Global.player.findFriendById(param1.get(_loc_2).uid);
                if (_loc_3 && param1.get(_loc_2).xp > _loc_4 || param1.get(_loc_2).uid == "-2")
                {
                    _loc_2 = _loc_2;
                    break;
                }
                _loc_2++;
            }
            Friend _loc_5 =new Friend(Global.player.uid ,Global.player.gold ,Global.player.xp ,Global.player.level ,false ,null ,"",Global.player.snUser.picture ,Global.player.snUser.firstName ,null ,Global.player.socialLevel ,false ,false );
            param1.splice(_loc_2, 0, _loc_5);
            int _loc_6 =-1;
            Friend _loc_7 =null ;
            _loc_8 = 0;
            while (_loc_8 < param1.length())
            {

                _loc_7 = param1.get(_loc_8);
                if (_loc_7.m_uid != Global.player.uid && _loc_7.m_firstTimeVisit)
                {
                    _loc_6 = _loc_8;
                    break;
                }
                _loc_8++;
            }
            if (_loc_6 >= 0)
            {
                _loc_9 = (param1.length - 1) - 5;
                if (_loc_9 >= _loc_6)
                {
                    param1.splice(_loc_6, 1);
                    param1.splice(_loc_9, 0, _loc_7);
                }
            }
            return;
        }//end

        public void  populateNeighbors (Array param1 ,boolean param2 =true )
        {
            return;

            this.m_slots = new Array();
            this.addPlayerToFriends(param1);
            FriendBarLeftNav _loc_3 =new FriendBarLeftNav ();
            _loc_3.addEventListener(FriendBarEvent.MOVE, this.onFriendBarMove);
            _loc_3.addEventListener(FriendBarEvent.MOVE_END, this.onFriendBarMoveEnd);
            //this.append(ASwingHelper.horizontalStrut(1));
            //this.append(_loc_3);
            //this.append(ASwingHelper.horizontalStrut(4));
            //this.append(this.makeLeftAddFriend());
            this.m_friendscroll = new FriendBarScrollingList(param1);
            //this.append(this.m_friendscroll);
            this.m_friendscroll.addEventListener(FriendBarSlotEvent.FRIEND_BAR_SLOT_CLICK, this.onSlotClick, false, 0, true);
            this.m_friendscroll.addEventListener(FriendBarSlotEvent.FRIEND_BAR_GIFT_CLICK, this.onGiftClick, false, 0, true);
            //this.append(ASwingHelper.horizontalStrut(1));
            FriendBarRightNav _loc_4 =new FriendBarRightNav ();
            _loc_4.addEventListener(FriendBarEvent.MOVE, this.onFriendBarMove);
            _loc_4.addEventListener(FriendBarEvent.MOVE_END, this.onFriendBarMoveEnd);
            //this.append(_loc_4);
            //ASwingHelper.prepare(this);
            //ASwingHelper.prepare(this.getParent());
            dispatchEvent(new FriendBarEvent(FriendBarEvent.LOADED, undefined, param1.length * FriendBar.SLOT_WIDTH));
            return;
        }//end

        private void  onFriendBarMove (FriendBarEvent event )
        {
            if (event.delta >= 0)
            {
                this.m_friendscroll.moveRight(event.length());
            }
            else
            {
                this.m_friendscroll.moveLeft(event.length());
            }
            return;
        }//end

        public FriendBarScrollingList  scrollingList ()
        {
            return this.m_friendscroll;
        }//end

        private void  onFriendBarMoveEnd (FriendBarEvent event )
        {
            if (event.delta == 0)
            {
                this.m_friendscroll.moveLeft(0);
            }
            else
            {
                this.m_friendscroll.moveRight(0);
            }
            return;
        }//end

        protected Array  sortFriends (Array param1 )
        {
            Array _loc_2 =null ;
            int _loc_5 =0;
            Friend _loc_6 =null ;
            int _loc_7 =0;
            _loc_3 = this.performLeftWindowSort(param1 );
            if (_loc_3 && _loc_3.length > 0)
            {
                param1 = this.removeFromFriends(param1, _loc_3);
            }
            _loc_2 = this.performMainSort(param1);
            if (_loc_3 && _loc_3.length > 0)
            {
                _loc_2 = this.injectOnLeft(_loc_2, _loc_3);
            }
            _loc_4 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_ON_VISIT );
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_ON_VISIT) == ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT)
            {
                _loc_5 = this.getIndexOfCitySam(_loc_2);
                if (_loc_5 != -1)
                {
                    _loc_6 = _loc_2.get(_loc_5);
                    if (Global.citySamNeighborCard)
                    {
                        _loc_6.m_profilePic = Global.citySamNeighborCard;
                    }
                    if (!this.hasVisitedCitySam())
                    {
                        _loc_2.splice(_loc_5, 1);
                        _loc_7 = _loc_2.length;
                        if (_loc_2.get((_loc_2.length - 1)).uid == "-2")
                        {
                            _loc_7 = _loc_7 - 1;
                        }
                        _loc_2.splice(_loc_7, 0, _loc_6);
                    }
                }
            }
            return _loc_2;
        }//end

        protected int  getIndexOfCitySam (Array param1 )
        {
            _loc_2 = param1.length ;
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                if (param1.get(_loc_3).uid == "-1")
                {
                    return _loc_3;
                }
                _loc_3++;
            }
            return -1;
        }//end

        protected boolean  hasVisitedCitySam ()
        {
            _loc_1 =Global.gameSettings().getCitySamVersion ();
            _loc_2 =Global.player.visitedCitySamVersion ;
            if (_loc_2 < _loc_1)
            {
                Global.player.hasVisitedCitySam = false;
                return false;
            }
            Global.player.hasVisitedCitySam = true;
            return true;
        }//end

        protected Array  performLeftWindowSort (Array param1 )
        {
            Array _loc_2 =null ;
            FriendBarSortHelper _loc_3 =null ;
            _loc_3 = Global.gameSettings().getFriendBarSortHelperByType("leftWindow");
            if (_loc_3)
            {
                _loc_2 = _loc_3.sort(param1);
            }
            return _loc_2;
        }//end

        protected Array  performMainSort (Array param1 )
        {
            FriendBarSortHelper _loc_2 =null ;
            _loc_2 = Global.gameSettings().getFriendBarSortHelperByType("main");
            if (_loc_2)
            {
                return _loc_2.sort(param1);
            }
            return param1;
        }//end

        protected Array  injectOnLeft (Array param1 ,Array param2 )
        {
            _loc_3 = param1.length +param2.length -FriendBarScrollingList.NUM_ITEMS ;
            _loc_4 = param1.slice(0,_loc_3 );
            _loc_5 = param1.slice(_loc_3 );
            return _loc_4.concat(param2, _loc_5);
        }//end

        protected Array  removeFromFriends (Array param1 ,Array param2 )
        {
            friends = param1;
            friendsToRemove = param2;
            return friends .filter (boolean  (Friend param11 ,int param21 ,Array param3 )
            {
                return friendsToRemove.indexOf(param11) == -1;
            }//end
            , null);
        }//end

    }



