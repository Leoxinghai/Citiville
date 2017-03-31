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
import Display.MarketUI.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;

    public class TExpandFarm extends TFarmTransaction
    {
        protected String m_itemName ;
        protected Vector2 m_location ;
        protected Array m_trees ;
        protected Array m_originalTrees ;
        private boolean m_isSpecialExpansion ;

        public  TExpandFarm (String param1 ,Vector2 param2 ,Array param3 ,boolean param4 =false )
        {
            Object tree ;
            itemName = param1;
            location = param2;
            trees = param3;
            isSpecialExpansion = param4;
            this.m_itemName = itemName;
            this.m_location = location;
            this.m_originalTrees = trees;
            this.m_trees = new Array();
            this.m_isSpecialExpansion = isSpecialExpansion;
            int i ;
            int _loc_6 =0;
            _loc_7 = trees;
            for(int i0 = 0; i0 < trees.size(); i0++)
            {
            		tree = trees.get(i0);


                this.m_trees.put(i,  {x:tree.x, y:tree.y, dir:tree.dir, itemName:tree.itemName, id:tree.id});
                i = (i + 1);
            }
            UI .displayMessage (ZLoc .t ("Main","ExpandFarmComplete"),GenericPopup .TYPE_OK ,void  ()
            {
                if (isSpecialExpansion)
                {
                    Global.guide.notify("ShowPierUpgrade");
                }
                return;
            }//end
            , "", true);
            Sounds.play("land_expand");
            Global.player.expireLicense(this.m_itemName);
            MarketCell.reLockCells(this.m_itemName);
            return;
        }//end

         public void  perform ()
        {
            signedCall("FarmService.expandCity", this.m_itemName, this.m_location, this.m_trees, this.m_isSpecialExpansion);
            return;
        }//end

        protected void  onExpandFarmFeed (GenericPopupEvent event )
        {
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =0;
            GameObject _loc_5 =null ;
            int _loc_6 =0;
            if (param1 != null)
            {
                _loc_2 =(Array) param1;
                _loc_3 = 0;
                if (_loc_2)
                {
                    if (this.m_originalTrees.length == _loc_2.length())
                    {
                        _loc_4 = 0;
                        while (_loc_4 < this.m_originalTrees.length())
                        {

                            _loc_5 = GameObject(this.m_originalTrees.get(_loc_4).wildernessObj);
                            if (_loc_5.getId() == _loc_2.get(_loc_4).id)
                            {
                                _loc_6 = _loc_2.get(_loc_4).newId;
                                if (_loc_6 != 0)
                                {
                                    _loc_5.setId(_loc_6);
                                    _loc_3++;
                                }
                            }
                            _loc_4++;
                        }
                    }
                }
                if (_loc_3 != this.m_originalTrees.length())
                {
                    ErrorManager.addError("Warning: TExpandFarm failed to remap ids.");
                }
                StatsManager.count(StatsCounterType.EXPANSION, this.m_itemName, TrackedActionType.PLACE, "(" + this.m_location.x + "," + this.m_location.y + ")");
            }
            return;
        }//end

    }



