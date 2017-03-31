package Classes.Managers;

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

import Classes.virals.*;
    public class BaseMFSManager
    {
        private Object m_initData =null ;

        public  BaseMFSManager ()
        {
            return;
        }//end

        public void  displayMFS ()
        {
            return;
        }//end

        public void  initMFS (Object param1 )
        {
            this.m_initData = param1;
            return;
        }//end

        public void  sendRequest (Array param1 )
        {
            Object _loc_2 =null ;
            RequestType _loc_3 =null ;
            Object _loc_4 =null ;
            FlashMFSRecipient _loc_6 =null ;
            if (this.m_initData == null)
            {
                _loc_2 = this.getRequestData();
                _loc_3 = this.getRequestType();
                _loc_4 = this.getRequestOntology();
            }
            else
            {
                _loc_2 = this.m_initData.data;
                _loc_3 = this.m_initData.type;
                _loc_4 = this.m_initData.ontology;
            }
            Array _loc_5 =new Array();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_6 = param1.get(i0);

                if (_loc_6.selected)
                {
                    _loc_5.push(String(_loc_6.zid));
                }
            }
            Global.world.viralMgr.sendRequest(_loc_3, _loc_5, _loc_2, this.onRequestSent, _loc_4);
            return;
        }//end

        public boolean  canSendRequest (Array param1 )
        {
            Object _loc_2 =null ;
            RequestType _loc_3 =null ;
            Object _loc_4 =null ;
            FlashMFSRecipient _loc_6 =null ;
            if (this.m_initData == null)
            {
                _loc_2 = this.getRequestData();
                _loc_3 = this.getRequestType();
                _loc_4 = this.getRequestOntology();
            }
            else
            {
                _loc_2 = this.m_initData.data;
                _loc_3 = this.m_initData.type;
                _loc_4 = this.m_initData.ontology;
            }
            Array _loc_5 =new Array();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_6 = param1.get(i0);

                if (_loc_6.selected)
                {
                    _loc_5.push(String(_loc_6.zid));
                }
            }
            return Global.world.viralMgr.canSendRequest(_loc_3, _loc_5, _loc_2);
        }//end

        protected RequestType  getRequestType ()
        {
            return RequestType.GIFT_REQUEST;
        }//end

        protected Object  getRequestData ()
        {
            return {};
        }//end

        protected Object  getRequestOntology ()
        {
            return {};
        }//end

        protected void  onRequestSent (boolean param1 ,Array param2 )
        {
            return;
        }//end

        protected Object  getMFSInitData ()
        {
            return this.m_initData;
        }//end

    }


