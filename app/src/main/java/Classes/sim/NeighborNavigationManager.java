package Classes.sim;

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
import Engine.Helpers.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;

    public class NeighborNavigationManager implements IGameWorldStateObserver
    {
        private  String VISIT_NEIGHBOR ="visitNeighbor";
        private  String CHANGE_NEIGHBOR ="changeNeighbor";
        private GameWorld m_world ;
        private Array m_neighborNavigators ;
        private NeighborNavigator m_currentActiveNeighborNavigator ;
        private Array m_friendsPictures ;
        private Array m_friendsPicturesIDs ;
        private double m_maxNeighborNavigators ;

        public  NeighborNavigationManager (GameWorld param1 )
        {
            this.m_neighborNavigators = new Array();
            this.m_friendsPictures = new Array();
            this.m_friendsPicturesIDs = new Array();
            this.m_world = param1;
            param1.addObserver(this);
            return;
        }//end

        public void  initialize ()
        {
            this.m_neighborNavigators = new Array();
            this.m_maxNeighborNavigators = 0;
            return;
        }//end

        public void  cleanUp ()
        {
            this.m_neighborNavigators = null;
            this.m_currentActiveNeighborNavigator = null;
            this.m_friendsPictures = null;
            this.m_friendsPicturesIDs = null;
            return;
        }//end

        public Array  neighborNavigators ()
        {
            return this.m_neighborNavigators;
        }//end

        public Array  friendsPictures ()
        {
            return this.m_friendsPictures;
        }//end

        public Array  friendsPicturesIDs ()
        {
            return this.m_friendsPicturesIDs;
        }//end

        public void  clearFriendsPicturesData ()
        {
            this.m_friendsPictures = new Array();
            this.m_friendsPicturesIDs = new Array();
            return;
        }//end

        public void  addFriendPictureData (DisplayObject param1 ,String param2 )
        {
            this.m_friendsPictures.push(param1);
            this.m_friendsPicturesIDs.push(param2);
            return;
        }//end

        public double  findSelectedFriendPictureIndex (Player param1 )
        {
            double _loc_2 =0;
            while (_loc_2 < this.m_friendsPicturesIDs.length())
            {

                if (this.m_friendsPicturesIDs.get(_loc_2) == param1.uid)
                {
                    return _loc_2;
                }
                _loc_2 = _loc_2 + 1;
            }
            return -1;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  loadTopFriendsIntoNavigators ()
        {
            double _loc_2 =0;
            double _loc_1 =0;
            while (_loc_1 < Global.player.topFriends.length())
            {

                if (Global.player.topFriends.get(_loc_1) != "-1")
                {
                    _loc_2 = 0;
                    while (_loc_2 < Global.player.friends.length())
                    {

                        if (Global.player.friends.get(_loc_2).uid == Global.player.topFriends.get(_loc_1))
                        {
                            this.m_neighborNavigators.get(_loc_1).currentTopNeighbor = Global.player.friends.get(_loc_2);
                            break;
                        }
                        _loc_2 = _loc_2 + 1;
                    }
                    this.m_neighborNavigators.get(_loc_1).containsNeighbor = true;
                    this.m_neighborNavigators.get(_loc_1).setItem(Global.gameSettings().getItemByName("prop_filled_top_neighbor"));
                    this.m_neighborNavigators.get(_loc_1).reloadImage();
                    this.m_neighborNavigators.get(_loc_1).loadTopFriendPicture();
                }
                _loc_1 = _loc_1 + 1;
            }
            return;
        }//end

        public void  onPlayerLoaded ()
        {
            double _loc_1 =0;
            if (Global.player.topFriends.length == 0)
            {
                _loc_1 = 0;
                while (_loc_1 < 2)
                {

                    Global.player.topFriends.push("-1");
                    _loc_1 = _loc_1 + 1;
                }
                GameTransactionManager.addTransaction(new TUpdateTopFriends(Global.player.topFriends));
            }
            this.loadTopFriendsIntoNavigators();
            return;
        }//end

        private void  createNeighborNavigator (String param1 )
        {
            NeighborNavigator neighborNavigator ;
            itemName = param1;
            neighborNavigator = new NeighborNavigator(itemName);
            neighborNavigator .setPlayActionCallback (void  ()
            {
                onNeighborNavigatorPlayAction(neighborNavigator);
                return;
            }//end
            );
            neighborNavigator.setOuter(this.m_world);
            neighborNavigator.attach();
            neighborNavigator.containsNeighbor = false;
            this.m_neighborNavigators.push(neighborNavigator);
            return;
        }//end

        private void  onNeighborNavigatorPlayAction (NeighborNavigator param1 )
        {
            Array _loc_2 =null ;
            this.m_currentActiveNeighborNavigator = param1;
            if (param1.containsNeighbor)
            {
                _loc_2 = .get({label:"Visit Neighbor", action:this.VISIT_NEIGHBOR}, {label:"Change Neighbor", action:this.CHANGE_NEIGHBOR});
                UI.displayMenu(_loc_2, this.onMenuClicked);
            }
            else
            {
                this.addTopNeighborsDialog(param1);
            }
            return;
        }//end

        private void  onMenuClicked (MouseEvent event )
        {
            _loc_2 = event.target instanceof ContextMenuItem ? ((ContextMenuItem)event.target) : (null);
            if (_loc_2 && _loc_2.action)
            {
                switch(_loc_2.action)
                {
                    case this.VISIT_NEIGHBOR:
                    {
                        UI.visitNeighbor(this.m_currentActiveNeighborNavigator.currentTopNeighbor.uid);
                        break;
                    }
                    case this.CHANGE_NEIGHBOR:
                    {
                        this.addTopNeighborsDialog(this.m_currentActiveNeighborNavigator);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        private void  addTopNeighborsDialog (NeighborNavigator param1 )
        {
            TopFriendSelector _loc_3 =null ;
            _loc_2 =Global.friendbar.length ;
            if (_loc_2 > 0)
            {
                _loc_3 = new TopFriendSelector(param1);
                UI.displayPopup(_loc_3);
            }
            else
            {
                UI.displayMessage("view chart...!!", GenericDialogView.TYPE_OK);
            }
            return;
        }//end

    }



