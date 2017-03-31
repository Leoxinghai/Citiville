package Classes;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Transactions.*;
//import flash.utils.*;

    public class CrossPromoManager
    {
        protected boolean m_isXpromo ;
        protected String m_source ;
        protected String m_affiliate ;
        protected String m_creative ;
        protected String m_quest ;
        protected String m_action ;
        protected Dictionary m_actionParams ;

        public  CrossPromoManager (Object param1 )
        {
            XML _loc_6 =null ;
            _loc_2 = (String)(param1.src );
            _loc_3 = (String)(param1.aff );
            _loc_4 = String(param1.crt);
            _loc_5 = this.validateArgs(_loc_2 ,_loc_3 ,_loc_4 );
            if (this.validateArgs(_loc_2, _loc_3, _loc_4) != null)
            {
                this.m_isXpromo = true;
                this.m_source = _loc_2;
                this.m_affiliate = _loc_3;
                this.m_creative = _loc_4;
                if (_loc_5.attribute("quest").length() > 0)
                {
                    this.m_quest = _loc_5.@quest;
                }
                if (_loc_5.child("action").length() > 0)
                {
                    this.m_action = _loc_5.child("action").attribute("name").toString();
                    this.m_actionParams = new Dictionary();
                    for(int i0 = 0; i0 < _loc_5.child("action").child("param").size(); i0++)
                    {
                    		_loc_6 = _loc_5.child("action").child("param").get(i0);

                        this.m_actionParams.get(_loc_6.attribute("name").toString()) = _loc_6.attribute("value").toString();
                    }
                }
            }
            return;
        }//end

        public boolean  isSessionFromXpromo ()
        {
            return this.m_isXpromo;
        }//end

        public String  localizationPrefix ()
        {
            return this.m_isXpromo ? (this.m_source + "_" + this.m_affiliate + "_" + this.m_creative) : (null);
        }//end

        protected XML  validateArgs (String param1 ,String param2 ,String param3 )
        {
            XML xml ;
            src = param1;
            aff = param2;
            crt = param3;
            xml = Global.gameSettings().getXpromoXMLByName(src);
            if (xml == null)
            {
                return null;
            }
            int _loc_6 =0;
            _loc_7 = xml.entry;
            XMLList _loc_5 =new XMLList("");
            Object _loc_8;
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            		_loc_8 = _loc_7.get(i0);


                with (_loc_8)
                {
                    if (@aff == aff && @crt == crt)
                    {
                        _loc_5.put(_loc_6++,  _loc_8);
                    }
                }
            }
            entries = _loc_5;
            return entries.length() > 0 ? (entries.get(0)) : (null);
        }//end

        public void  handleStartup ()
        {
            if (!this.isSessionFromXpromo)
            {
                return;
            }
            _loc_1 = this.localizationPrefix +"_intro";
            _loc_2 = ZLoc.t("XPromo",_loc_1 );
            if (this.m_quest)
            {
                UI.displayPopup(new GenericDialog(_loc_2, "", GenericDialogView.TYPE_OK, null, _loc_1), true);
                GameTransactionManager.addTransaction(new TStartManualQuest(this.m_quest));
            }
            if (this.m_action && !Global.player.isNewPlayer)
            {
                if (CrossPromoHelper.instance.hasOwnProperty(this.m_action))
                {
                    _loc_3 = CrossPromoHelper.instance;
                    _loc_3.CrossPromoHelper.instance.get(this.m_action)(this.m_actionParams);
                }
            }
            return;
        }//end

        public void  handleEndOfTutorial ()
        {
            if (!this.isSessionFromXpromo)
            {
                return;
            }
            if (this.m_quest)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest(this.m_quest));
            }
            if (this.m_action)
            {
                if (CrossPromoHelper.instance.hasOwnProperty(this.m_action))
                {
                    _loc_1 = CrossPromoHelper.instance;
                    _loc_1.CrossPromoHelper.instance.get(this.m_action)(this.m_actionParams);
                }
            }
            return;
        }//end

        public void  handleReward (String param1 ,String param2 )
        {
            Global.questManager.callEpilogueWithUrl(param1, param2);
            return;
        }//end

    }



