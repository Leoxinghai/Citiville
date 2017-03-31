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
import Engine.Managers.*;

    public class TConstructionFinish extends TWorldState
    {
        private Object m_params ;
        protected Array m_originalTrees ;

        public  TConstructionFinish (ConstructionSite param1 ,Array param2 )
        {
            Array _loc_4 =null ;
            int _loc_5 =0;
            Object _loc_6 =null ;
            super(param1);
            this.m_params = {};
            this.m_params.put("mapOwner",  Global.world.ownerId);
            this.m_params.put("itemId",  param1.getId());
            this.m_params.put("wildernessData",  new Array());
            if (param2 == null)
            {
                param2 = new Array();
            }
            this.m_originalTrees = param2;
            int _loc_3 =0;
            while (_loc_3 < param2.length())
            {

                _loc_4 = param2.get(_loc_3);
                _loc_5 = 0;
                this.m_params.get("wildernessData").put(_loc_3,  new Array());
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_6 = _loc_4.get(i0);

                    this.m_params.get("wildernessData").get(_loc_3).put(_loc_5,  {x:_loc_6.x, y:_loc_6.y, dir:_loc_6.dir, itemName:_loc_6.itemName, id:_loc_6.id});
                    _loc_5++;
                }
                _loc_3++;
            }
            return;
        }//end

         public void  perform ()
        {
            signedWorldAction("finish", this.m_params);
            return;
        }//end

         protected void  onWorldActionComplete (Object param1 )
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            Array _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            GameObject _loc_7 =null ;
            int _loc_8 =0;
            super.onWorldActionComplete(param1);
            if (param1 != null)
            {
                _loc_2 =(Array) param1.get("wildernessObjects");
                if (!_loc_2)
                {
                    return;
                }
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    _loc_4 = _loc_2.get(_loc_3);
                    if (!_loc_4)
                    {
                    }
                    else
                    {
                        _loc_5 = 0;
                        if (this.m_originalTrees.get(_loc_3).length == _loc_4.length())
                        {
                            _loc_6 = 0;
                            while (_loc_6 < this.m_originalTrees.get(_loc_3).length())
                            {

                                _loc_7 = GameObject(this.m_originalTrees.get(_loc_3).get(_loc_6).wildernessObj);
                                if (_loc_7.getId() == _loc_4.get(_loc_6).id)
                                {
                                    _loc_8 = _loc_4.get(_loc_6).newId;
                                    if (_loc_8 != 0)
                                    {
                                        _loc_7.setId(_loc_8);
                                        _loc_5++;
                                    }
                                }
                                _loc_6++;
                            }
                        }
                        if (_loc_5 != this.m_originalTrees.get(_loc_3).length())
                        {
                            ErrorManager.addError("Warning: TConstructionFinish failed to remap ids.");
                        }
                    }
                    _loc_3++;
                }
            }
            return;
        }//end

    }



