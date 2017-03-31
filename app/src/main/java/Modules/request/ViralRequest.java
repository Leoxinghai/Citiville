package Modules.request;

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
import Display.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.external.*;

    public class ViralRequest
    {
        protected RequestType m_requestType ;
        protected Array m_recipientIds ;
        protected Array m_filteredIds ;
        protected Object m_ontology ;
        protected Function m_requestSendCallback ;
public static  String SEND_REQUEST_METHOD ="sendRequest";
        private static  int FREEZE_KEY =2;
public static  String ERROR_NONE ="none";
public static  String ERROR_JS_CALL_FAILED ="js_call_failed";
public static  String ERROR_SEND_REQUEST_FAILED ="send_requests return null";
public static  String ERROR_NO_RECIPIENTS ="no_recipients";

        public  ViralRequest (RequestType param1 ,Array param2 ,Object param3 =null ,Function param4 =null ,Object param5 =null )
        {
            this.m_requestType = param1;
            this.m_recipientIds = param2;
            this.m_requestSendCallback = param4;
            this.m_ontology = param5;
            this.setData(param3);
            this.filterIds(this.getIneligibleIds());
            return;
        }//end

        public void  onRequestSent (String param1 ,Array param2 )
        {
            UI.thawScreen(FREEZE_KEY);
            _loc_3 = param1==ERROR_NONE ;
            if (_loc_3)
            {
                this.onSendSuccessful(param2);
            }
            if (this.m_requestSendCallback != null)
            {
                this.m_requestSendCallback(_loc_3, param2);
            }
            this.m_requestSendCallback = null;
            return;
        }//end

        public boolean  canSend ()
        {
            return this.m_filteredIds.length > 0;
        }//end

        public boolean  send (int param1 )
        {
            requestId = param1;
            boolean success ;
            data = this.getData();
            data.put("requestId",  requestId);
            try
            {
                if (ExternalInterface.available)
                {
                    UI.freezeScreen(false, true, null, FREEZE_KEY);
                    success = ExternalInterface.call(SEND_REQUEST_METHOD, this.m_requestType.handler, this.m_filteredIds, data, this.m_ontology);
                    if (!success)
                    {
                        StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.GAME_ACTIONS, "sendRequest2_0", "js_call_failed");
                        if (Config.DEBUG_MODE)
                        {
                            this.onRequestSent(ERROR_NONE, this.m_filteredIds);
                        }
                        else
                        {
                            this.onRequestSent(ERROR_JS_CALL_FAILED, []);
                        }
                    }
                }
            }
            catch (e:Error)
            {
                ErrorManager.addError(ZLoc.t("Main", "Error"));
                StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.GAME_ACTIONS, "sendRequest2_0", "js_exception");
                UI.thawScreen(FREEZE_KEY);
            }
            return success;
        }//end

        protected void  onSendSuccessful (Array param1 )
        {
            Global.player.addActiveRequests(this.m_requestType, param1);
            return;
        }//end

        public Object  getData ()
        {
            return {};
        }//end

        public void  setData (Object param1 )
        {
            return;
        }//end

        protected Array  getIneligibleIds ()
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_1 =new Array();
            _loc_2 =Global.player.getActiveRequests(this.m_requestType );
            if (this.m_recipientIds)
            {
                for(int i0 = 0; i0 < this.m_recipientIds.size(); i0++)
                {
                		_loc_3 = this.m_recipientIds.get(i0);

                    for(int i0 = 0; i0 < _loc_2.size(); i0++)
                    {
                    		_loc_4 = _loc_2.get(i0);

                        if (_loc_4 == _loc_3)
                        {
                            _loc_1.push(_loc_3);
                        }
                    }
                }
            }
            return _loc_1;
        }//end

        private void  filterIds (Array param1 )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            this.m_filteredIds = this.m_recipientIds ? (this.m_recipientIds.concat()) : ([]);
            if (this.m_filteredIds.length > 0)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_2 = param1.get(i0);

                    _loc_3 = this.m_filteredIds.indexOf(_loc_2);
                    if (_loc_3 >= 0)
                    {
                        this.m_filteredIds.splice(_loc_3, 1);
                    }
                }
            }
            return;
        }//end

    }



