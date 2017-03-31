package Transactions;

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
import Engine.Managers.*;
import Modules.realtime.*;

import com.xinghai.Debug;

    public class TPlaceMapResource extends TWorldState
    {
        private Object m_params ;
        private MapResource m_resource ;
        protected Array m_originalTrees ;
        protected Array m_trees ;
        protected String m_itemName ;

        public  TPlaceMapResource (MapResource param1 ,boolean param2 =false ,int param3 =0,String param4 =null )
        {
            Array trees ;
            Item item ;
            String flag ;
            Object tree ;
            resource = param1;
            isGift = param2;
            source = param3;
            itemName = param4;
            this.m_originalTrees = new Array();
            this.m_trees = new Array();
            super(resource);
            this.m_resource = resource;
            this.m_params = new Object();
            this.m_params.isGift = isGift;
            this.m_params.source = source;
            this.m_params.energyCost = 0;
            this.m_params.itemOwner = Global.player.uid;
            this.m_params.mapOwner = Global.world.ownerId;
            this.m_itemName = itemName;
            this.m_originalTrees = Global.world.wildernessSim.processExpansionsOnObjectPlace(this.m_resource);
            int i ;
            int t ;
            int _loc_6 =0;
            _loc_7 = this.m_originalTrees ;
            for(int i0 = 0; i0 < this.m_originalTrees.size(); i0++)
            {
            		trees = this.m_originalTrees.get(i0);


                this.m_trees.push(new Array());
                int _loc_8 =0;
                _loc_9 = trees;
                for(int i0 = 0; i0 < trees.size(); i0++)
                {
                		tree = trees.get(i0);


                    this.m_trees.get(t).put(i,  {x:tree.x, y:tree.y, dir:tree.dir, itemName:tree.itemName, id:tree.id});
                    i = (i + 1);
                }
                t = (t + 1);
            }
            this.m_params.wildernessData = this.m_trees;
            item = this.m_resource.getItem();
            flag = item.placementSeenFlag;
            if (flag != null)
            {
                TimerUtil .callLater (void  ()
            {
                Global.player.setSeenFlag(flag, true);
                UI.refreshCatalogItems(.get(item.name));
                return;
            }//end
            , 1);
            }
            return;
        }//end

        protected Object  params ()
        {
            return this.m_params;
        }//end

         public void  perform ()
        {

            _loc_1 = this.m_resource.getItem ();
            signedWorldAction("place", this.m_params);
            return;
        }//end

         protected void  onWorldActionComplete (Object param1 )
        {

            LotSite _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            Array _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            GameObject _loc_8 =null ;
            int _loc_9 =0;
            if (this.m_resource is LotSite)
            {
                _loc_2 =(LotSite) this.m_resource;
                Global.world.citySim.lotManager.addLotSite(_loc_2);
            }
            if (param1 !=null)
            {
                if (param1.get("wildernessObjects"))
                {
                    _loc_3 =(Array) param1.get("wildernessObjects");
                    if (_loc_3)
                    {
                        _loc_4 = 0;
                        while (_loc_4 < _loc_3.length())
                        {

                            _loc_5 = _loc_3.get(_loc_4);
                            if (_loc_5)
                            {
                                _loc_6 = 0;
                                if (this.m_originalTrees.get(_loc_4).length == _loc_5.length())
                                {
                                    _loc_7 = 0;
                                    while (_loc_7 < this.m_originalTrees.get(_loc_4).length())
                                    {

                                        _loc_8 = GameObject(this.m_originalTrees.get(_loc_4).get(_loc_7).wildernessObj);
                                        if (_loc_8.getId() == _loc_5.get(_loc_7).id)
                                        {
                                            _loc_9 = _loc_5.get(_loc_7).newId;
                                            if (_loc_9 != 0)
                                            {
                                                _loc_8.setId(_loc_9);
                                                _loc_6++;
                                            }
                                        }
                                        _loc_7++;
                                    }
                                }
                                if (_loc_6 != this.m_originalTrees.get(_loc_4).length())
                                {
                                    ErrorManager.addError("Warning: TPlaceMapResource failed to remap ids. Expected " + this.m_originalTrees.get(_loc_4).length + " got " + _loc_6);
                                }
                            }
                            _loc_4++;
                        }
                    }
                }
            }
            return;
        }//end

         public RealtimeMethod  getRealtimeMethod ()
        {
            return new RealtimeMethod("showAction", "placed", this.m_resource.getItem().name);
        }//end

    }



