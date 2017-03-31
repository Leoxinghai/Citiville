package Modules.quest.guide;

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
import Modules.guide.*;
import Modules.quest.Managers.*;
import Modules.quest.guide.ui.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

    public class QuestGuide extends Guide
    {
        protected GameQuest m_quest ;

        public  QuestGuide (XML param1 ,GameQuest param2 )
        {
            m_triggers = new Dictionary();
            m_callbacks = new Dictionary();
            m_names = new Dictionary();
            m_sequences = new Dictionary();
            m_arrows = new Array();
            m_dialogs = new Array();
            m_guideTiles = new Array();
            m_callbackHelper = new GuideCallbackHelper();
            m_callbackHelper.registerHandlers(this);
            m_reader = new QuestGuideActionReader(this, param1, param2);
            m_reader.readActions();
            this.m_quest = param2;
            return;
        }//end

         public void  registerSequence (GuideSequence param1 )
        {
            if (param1 != null)
            {
                if (m_sequences.get(param1.m_name) == null)
                {
                    m_sequences.put(param1.m_name,  param1);
                    registerTrigger(param1.m_name, param1);
                    ;
                }
            }
            return;
        }//end

         public void  notify (String param1 )
        {
            GuideSequence _loc_3 =null ;
            if (param1 == null || param1.length < 1)
            {
                return;
            }
            _loc_2 = m_triggers.get(param1);
            if (_loc_2 != null)
            {
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    if (_loc_3.isActive())
                    {
                        _loc_3.stop();
                    }
                }
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_3.start();
                }
            }
            return;
        }//end

         public void  displaySpotLight (DisplayObjectContainer param1 ,Point param2 ,Point param3 ,int param4 ,int param5 ,double param6 =0,boolean param7 =false ,Function param8 =null )
        {
            if (m_mask != null)
            {
                ErrorManager.addError("Spotlight applied twice!");
                return;
            }
            if (param1 == null)
            {
                ErrorManager.addError("Failed to attach spotlight!");
            }
            m_mask = new QuestGuideMask(getActiveSequence() as QuestGuideSequence);
            m_mask.displaySpotLight(param1, param2, param3, param4, param5, param6, param7, param8);
            disableZoom();
            return;
        }//end

    }



