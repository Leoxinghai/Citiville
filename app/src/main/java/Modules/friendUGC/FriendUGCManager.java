package Modules.friendUGC;

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

import Engine.Managers.*;
import Transactions.*;
import com.adobe.utils.*;
//import flash.utils.*;

    public class FriendUGCManager
    {
        protected Dictionary m_ugcMap ;
        protected int m_nextUGCObjID ;

        public  FriendUGCManager ()
        {
            this.m_nextUGCObjID = 0;
            return;
        }//end

        public void  loadUGCMap (Object param1 )
        {
            String _loc_2 =null ;
            double _loc_3 =0;
            Object _loc_4 =null ;
            UGCContainer _loc_5 =null ;
            this.m_ugcMap = new Dictionary();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = parseFloat(_loc_2);
                if (isNaN(_loc_3))
                {
                    continue;
                }
                _loc_3 = int(_loc_3);
                _loc_4 = param1.get(_loc_3);
                _loc_5 = new UGCContainer(_loc_4.sender, Global.world.ownerId, _loc_4.payload);
                this.m_ugcMap.put(_loc_3,  _loc_5);
            }
            this.m_nextUGCObjID = int(param1.nextUGCObjID);
            return;
        }//end

        public int  createUGC (String param1 ,String param2 ,Object param3 ,boolean param4 =false )
        {
            UGCContainer _loc_5 =new UGCContainer(param1 ,param2 ,param3 );
            if (param4)
            {
                this.sendUGC(_loc_5);
            }
            if (Global.world.ownerId != param2)
            {
                return -1;
            }
            this.m_ugcMap.put(this.m_nextUGCObjID,  _loc_5);
            _loc_6 = this.m_nextUGCObjID ;
            this.m_nextUGCObjID++;
            return _loc_6;
        }//end

        public void  sendUGC (UGCContainer param1 )
        {
            if (param1 !=null)
            {
                TransactionManager.addTransaction(new TSendUGC(param1));
            }
            return;
        }//end

        public UGCContainer  removeUGC (int param1 )
        {
            UGCContainer _loc_2 =null ;
            if (this.m_ugcMap.get(param1))
            {
                _loc_2 = this.m_ugcMap.get(param1);
                if (String(Global.player.snUser.snuid) != _loc_2.receiverID)
                {
                    return null;
                }
                delete this.m_ugcMap.get(param1);
                TransactionManager.addTransaction(new TRemoveUGC(.get(param1)));
                return _loc_2;
            }
            return null;
        }//end

        public void  clearAllUGC ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            Array _loc_1 =new Array ();
            for(int i0 = 0; i0 < DictionaryUtil.getKeys(this.m_ugcMap).size(); i0++)
            {
            		_loc_2 = DictionaryUtil.getKeys(this.m_ugcMap).get(i0);

                if ((this.m_ugcMap.get(_loc_2) as UGCContainer).receiverID == Global.player.uid)
                {
                    _loc_1.push(_loc_2);
                }
            }
            if (_loc_1.length > 0)
            {
                TransactionManager.addTransaction(new TRemoveUGC(_loc_1));
            }
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_3 = _loc_1.get(i0);

                delete this.m_ugcMap.get(_loc_3);
            }
            return;
        }//end

        public UGCContainer  getUGCContainer (int param1 )
        {
            if (this.m_ugcMap.get(param1))
            {
                return this.m_ugcMap.get(param1);
            }
            return null;
        }//end

        public Object  getUGCObject (int param1 )
        {
            if (this.m_ugcMap.get(param1) as UGCContainer)
            {
                return (this.m_ugcMap.get(param1) as UGCContainer).payload;
            }
            return null;
        }//end

        public void  updateUGCObject (int param1 ,Object param2 )
        {
            if (this.m_ugcMap.get(param1) as UGCContainer)
            {
                (this.m_ugcMap.get(param1) as UGCContainer).payload = param2;
            }
            return;
        }//end

    }



