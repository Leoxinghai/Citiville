package Display;

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
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;

    public class TopFriendSelector extends SlotDialog
    {
        protected  int NUM_SLOTS =12;
        protected Array m_selectedFriends ;
        protected boolean m_multiSelect ;
        protected String m_friendSelectorTitle ="FriendSelectorTitle";
        protected GenericPopup m_errorPopup ;
        private NeighborNavigator m_neighborNavigator ;
        private Array m_friendsArray ;

        public  TopFriendSelector (NeighborNavigator param1 ,boolean param2 =false )
        {
            boolean _loc_4 =false ;
            double _loc_5 =0;
            this.m_selectedFriends = new Array();
            this.m_friendsArray = new Array();
            m_dialogAsset = "assets/dialogs/FV_FriendChooser.swf";
            this.m_multiSelect = param2;
            this.m_neighborNavigator = param1;
            double _loc_3 =0;
            while (_loc_3 < Global.friendbar.length())
            {

                _loc_4 = false;
                _loc_5 = 0;
                while (_loc_5 < Global.player.topFriends.length())
                {

                    if (Global.friendbar.get(_loc_3).uid == Global.player.topFriends.get(_loc_5))
                    {
                        _loc_4 = true;
                        break;
                    }
                    _loc_5 = _loc_5 + 1;
                }
                if (!_loc_4)
                {
                    this.m_friendsArray.push(Global.friendbar.get(_loc_3));
                }
                _loc_3 = _loc_3 + 1;
            }
            Global.world.citySim.neighborNavigationManager.clearFriendsPicturesData();
            return;
        }//end

        public void  setTitle (String param1 )
        {
            this.m_friendSelectorTitle = param1;
            if (m_window)
            {
                m_window.friendChooser_mc.title_tf.text = ZLoc.t("Dialogs", this.m_friendSelectorTitle);
            }
            return;
        }//end

        public Array  getSelectedFriends ()
        {
            return this.m_selectedFriends;
        }//end

         protected void  onLoadComplete ()
        {
            m_window =(MovieClip) m_loader.content;
            m_window.friendChooser_mc.close_bt.addEventListener(MouseEvent.CLICK, this.onCloseClick);
            m_window.friendChooser_mc.arrowLt_bt.addEventListener(MouseEvent.CLICK, onLtArrowClick);
            m_window.friendChooser_mc.arrowRt_bt.addEventListener(MouseEvent.CLICK, onRtArrowClick);
            m_window.friendChooser_mc.title_tf.text = ZLoc.t("Dialogs", this.m_friendSelectorTitle);
            GenericButton _loc_1 =new GenericButton(m_window.friendChooser_mc.next_bt ,this.onNextClick );
            _loc_1.m_overlay.name = "next_bt";
            _loc_1.enableHitState(true);
            _loc_1.text = ZLoc.t("Dialogs", "NextCap");
            m_tiles.push(m_window.friendChooser_mc.friendCard1_mc, m_window.friendChooser_mc.friendCard2_mc, m_window.friendChooser_mc.friendCard3_mc, m_window.friendChooser_mc.friendCard4_mc, m_window.friendChooser_mc.friendCard5_mc, m_window.friendChooser_mc.friendCard6_mc, m_window.friendChooser_mc.friendCard7_mc, m_window.friendChooser_mc.friendCard8_mc, m_window.friendChooser_mc.friendCard9_mc, m_window.friendChooser_mc.friendCard10_mc, m_window.friendChooser_mc.friendCard11_mc, m_window.friendChooser_mc.friendCard12_mc);
            removeTiles();
            this.pushNewData();
            int _loc_2 =0;
            while (_loc_2 < this.NUM_SLOTS && _loc_2 < m_data.length())
            {

                this.addComponent(_loc_2);
                _loc_2++;
            }
            addChild(m_window);
            return;
        }//end

         protected int  getNumSlots ()
        {
            return this.NUM_SLOTS;
        }//end

         protected void  fixArrows ()
        {
            m_window.friendChooser_mc.arrowLt_bt.visible = m_tileSet > 0 ? (true) : (false);
            m_window.friendChooser_mc.arrowRt_bt.visible = (m_tileSet + 1) * this.getNumSlots() < m_data.length ? (true) : (false);
            return;
        }//end

        private Player  findFriendByIdFake (String param1 )
        {
            Player _loc_6 =null ;
            Player _loc_2 =null ;
            _loc_3 =Global.friendbar ;
            _loc_4 =Global.player ;
            int _loc_5 =0;
            while (_loc_5 < Global.player.friends.length())
            {

                _loc_6 =(Player) Global.player.friends.get(_loc_5);
                if (Global.player.neighbors.get(_loc_5) == param1)
                {
                    _loc_2 = _loc_6;
                    break;
                }
                _loc_5++;
            }
            return _loc_2;
        }//end

        protected void  pushNewData ()
        {
            int _loc_1 =0;
            Player _loc_2 =null ;
            m_data = new Array();
            m_tileSet = 0;
            if (this.m_friendsArray)
            {
                _loc_1 = 0;
                while (_loc_1 < this.m_friendsArray.length())
                {

                    _loc_2 = Global.player.findFriendById(this.m_friendsArray.get(_loc_1).uid);
                    if (_loc_2)
                    {
                        m_data.push(_loc_2);
                    }
                    _loc_1++;
                }
            }
            this.fixArrows();
            return;
        }//end

         protected void  addComponent (int param1 ,int param2)
        {
            MovieClip tile ;
            String url ;
            Loader icon ;
            DisplayObject dispObj ;
            compId = param1;
            index = param2;
            if (m_tiles && m_data.get(index + compId) != null)
            {
                tile =(MovieClip) m_tiles.get(compId);
                tile.visible = true;
                tile.addEventListener(MouseEvent.MOUSE_OVER, this.onTileMouseOver);
                tile.addEventListener(MouseEvent.MOUSE_OUT, this.onTileMouseOut);
                tile.addEventListener(MouseEvent.CLICK, this.onTileMouseClick);
                tile.id = index + compId;
                tile.itemTitle_tf.text = m_data.get(index + compId).name;
                url = m_data.get(index + compId).snUser.picture;
                if (url)
                {
                    icon =LoadingManager .load (url ,void  (Event event )
            {
                _loc_2 = null;
                if (icon && icon.content)
                {
                    if (tile)
                    {
                        Utilities.removeAllChildren(tile.icon);
                    }
                    _loc_2 = icon.content;
                    _loc_2.width = 50;
                    _loc_2.height = 50;
                    tile.icon.addChild(_loc_2);
                    Global.world.citySim.neighborNavigationManager.addFriendPictureData(_loc_2, m_data.get(index + compId).uid);
                }
                return;
            }//end
            );
                }
                else
                {
                    if (tile)
                    {
                        Utilities.removeAllChildren(tile.icon);
                    }
                    dispObj = new EmbeddedArt.hud_no_profile_pic();
                    dispObj.width = 50;
                    dispObj.height = 50;
                    tile.icon.addChild(dispObj);
                    Global.world.citySim.neighborNavigationManager.addFriendPictureData(dispObj, m_data.get(index + compId).uid);
                }
                tile.checked = false;
                if (this.isFriendSelected(m_data.get(index + compId) as Player))
                {
                    tile.checked = true;
                }
                if (tile.checked)
                {
                    tile.gotoAndStop(3);
                }
                else
                {
                    tile.gotoAndStop(1);
                }
            }
            return;
        }//end

        protected void  onTileMouseOver (MouseEvent event )
        {
            MovieClip _loc_2 =null ;
            if (event.target.hasOwnProperty("checked"))
            {
                _loc_2 =(MovieClip) event.target;
            }
            else
            {
                _loc_2 =(MovieClip) event.target.parent;
            }
            if (_loc_2.checked)
            {
                _loc_2.gotoAndStop(4);
            }
            else
            {
                _loc_2.gotoAndStop(2);
            }
            return;
        }//end

        protected void  onTileMouseOut (MouseEvent event )
        {
            MovieClip _loc_2 =null ;
            if (event.target.hasOwnProperty("checked"))
            {
                _loc_2 =(MovieClip) event.target;
            }
            else
            {
                _loc_2 =(MovieClip) event.target.parent;
            }
            if (_loc_2.checked)
            {
                _loc_2.gotoAndStop(3);
            }
            else
            {
                _loc_2.gotoAndStop(1);
            }
            return;
        }//end

        protected void  onTileMouseClick (MouseEvent event )
        {
            MovieClip _loc_2 =null ;
            if (event.target.hasOwnProperty("checked"))
            {
                _loc_2 =(MovieClip) event.target;
            }
            else
            {
                _loc_2 =(MovieClip) event.target.parent;
            }
            _loc_2.checked = !_loc_2.checked;
            if (_loc_2.checked)
            {
                this.handleAddFriend(_loc_2.id);
                _loc_2.gotoAndStop(4);
            }
            else
            {
                this.handleRemoveFriend(_loc_2.id);
                _loc_2.gotoAndStop(2);
            }
            return;
        }//end

        protected boolean  isFriendSelected (Player param1 )
        {
            int _loc_3 =0;
            boolean _loc_2 =false ;
            if (param1 !=null)
            {
                _loc_3 = 0;
                while (_loc_3 < this.m_selectedFriends.length())
                {

                    if (this.m_selectedFriends.get(_loc_3) && this.m_selectedFriends.get(_loc_3).getId() == param1.getId())
                    {
                        _loc_2 = true;
                        break;
                    }
                    _loc_3++;
                }
            }
            return _loc_2;
        }//end

        protected void  handleAddFriend (int param1 )
        {
            int _loc_2 =0;
            MovieClip _loc_3 =null ;
            if (!this.m_multiSelect && this.m_selectedFriends.length > 0)
            {
                _loc_2 = m_data.indexOf(this.m_selectedFriends.get(0));
                if (_loc_2 >= m_tileSet * this.getNumSlots() && _loc_2 < m_tileSet * this.getNumSlots() + this.getNumSlots())
                {
                    _loc_3 =(MovieClip) m_tiles.get(_loc_2 - m_tileSet * this.getNumSlots());
                    _loc_3.checked = false;
                    _loc_3.gotoAndStop(1);
                }
                this.m_selectedFriends = new Array();
            }
            this.m_selectedFriends.push(m_data.get(param1));
            return;
        }//end

        protected void  handleRemoveFriend (int param1 )
        {
            _loc_2 = this.m_selectedFriends.indexOf(m_data.get(param1) );
            this.m_selectedFriends.splice(_loc_2, 1);
            return;
        }//end

        protected void  errorPopupClose (Event event )
        {
            this.m_errorPopup = null;
            return;
        }//end

        protected void  onNextClick (MouseEvent event )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            if (this.m_selectedFriends.length > 0)
            {
                if (this.m_neighborNavigator)
                {
                    if (this.m_neighborNavigator.containsNeighbor)
                    {
                        Global.player.topFriends.put(Global.player.topFriends.indexOf(this.m_neighborNavigator.currentTopNeighbor.uid),  "-1");
                        GameTransactionManager.addTransaction(new TUpdateTopFriends(Global.player.topFriends));
                        this.m_neighborNavigator.getDisplayObject().parent.removeChild(this.m_neighborNavigator.neighborPic);
                        this.m_neighborNavigator.neighborPic = null;
                    }
                    _loc_2 = Global.world.citySim.neighborNavigationManager.neighborNavigators.indexOf(this.m_neighborNavigator);
                    Global.player.topFriends.put(_loc_2,  this.m_selectedFriends.get(0).uid);
                    GameTransactionManager.addTransaction(new TUpdateTopFriends(Global.player.topFriends));
                    this.m_neighborNavigator.currentTopNeighbor = this.m_selectedFriends.get(0);
                    this.m_neighborNavigator.containsNeighbor = true;
                    this.m_neighborNavigator.setItem(Global.gameSettings().getItemByName("prop_filled_top_neighbor"));
                    this.m_neighborNavigator.reloadImage();
                    _loc_3 = Global.world.citySim.neighborNavigationManager.findSelectedFriendPictureIndex(this.m_selectedFriends.get(0));
                    this.m_neighborNavigator.neighborPic = Global.world.citySim.neighborNavigationManager.friendsPictures.get(_loc_3);
                    this.m_neighborNavigator.displayFriendPicOnBillboard();
                }
                if (this.m_errorPopup)
                {
                    this.m_errorPopup.close();
                }
                removeTiles(false);
                m_window.friendChooser_mc.close_bt.removeEventListener(MouseEvent.CLICK, this.onCloseClick);
                m_window.friendChooser_mc.arrowLt_bt.removeEventListener(MouseEvent.CLICK, onLtArrowClick);
                m_window.friendChooser_mc.arrowRt_bt.removeEventListener(MouseEvent.CLICK, onRtArrowClick);
                event.stopPropagation();
                close();
                dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
            }
            else if (this.m_errorPopup == null)
            {
                if (this.m_neighborNavigator)
                {
                    if (this.m_neighborNavigator.containsNeighbor)
                    {
                        Global.player.topFriends.get(Global.player.topFriends.indexOf(this.m_neighborNavigator.currentTopNeighbor.uid)) = "-1";
                        GameTransactionManager.addTransaction(new TUpdateTopFriends(Global.player.topFriends));
                        this.m_neighborNavigator.getDisplayObject().parent.removeChild(this.m_neighborNavigator.neighborPic);
                        this.m_neighborNavigator.neighborPic = null;
                    }
                    this.m_neighborNavigator.currentTopNeighbor = null;
                    this.m_neighborNavigator.containsNeighbor = false;
                    this.m_neighborNavigator.setItem(Global.gameSettings().getItemByName("prop_empty_top_neighbor"));
                    this.m_neighborNavigator.reloadImage();
                }
                if (this.m_errorPopup)
                {
                    this.m_errorPopup.close();
                }
                this.m_selectedFriends = new Array();
                removeTiles(false);
                m_window.friendChooser_mc.close_bt.removeEventListener(MouseEvent.CLICK, this.onCloseClick);
                m_window.friendChooser_mc.arrowLt_bt.removeEventListener(MouseEvent.CLICK, onLtArrowClick);
                m_window.friendChooser_mc.arrowRt_bt.removeEventListener(MouseEvent.CLICK, onRtArrowClick);
                event.stopPropagation();
                close();
                dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
            }
            return;
        }//end

        protected void  onCloseClick (MouseEvent event )
        {
            if (this.m_errorPopup)
            {
                this.m_errorPopup.close();
            }
            this.m_selectedFriends = new Array();
            removeTiles(false);
            m_window.friendChooser_mc.close_bt.removeEventListener(MouseEvent.CLICK, this.onCloseClick);
            m_window.friendChooser_mc.arrowLt_bt.removeEventListener(MouseEvent.CLICK, onLtArrowClick);
            m_window.friendChooser_mc.arrowRt_bt.removeEventListener(MouseEvent.CLICK, onRtArrowClick);
            event.stopPropagation();
            close();
            dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
            return;
        }//end

    }



