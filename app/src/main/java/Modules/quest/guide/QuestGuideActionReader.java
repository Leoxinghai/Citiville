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
import Modules.guide.actions.*;
import Modules.quest.Managers.*;

    public class QuestGuideActionReader extends GuideActionReader
    {
        protected XML m_config ;
        protected GameQuest m_quest ;

        public  QuestGuideActionReader (QuestGuide param1 ,XML param2 ,GameQuest param3 )
        {
            super(param1);
            this.m_config = param2;
            this.m_quest = param3;
            return;
        }//end

         public void  readActions ()
        {
            XML _loc_2 =null ;
            QuestGuideSequence _loc_3 =null ;
            boolean _loc_4 =false ;
            XMLList _loc_5 =null ;
            XML _loc_6 =null ;
            String _loc_7 =null ;
            Class _loc_8 =null ;
            GuideAction _loc_9 =null ;
            if (!this.m_config)
            {
                ErrorManager.addError("Missing quest settings!");
                return;
            }
            _loc_1 = this.m_config.guides.guide ;
            for(int i0 = 0; i0 < _loc_1.size(); i0++) 
            {
            		_loc_2 = _loc_1.get(i0);

                _loc_3 = new QuestGuideSequence(m_guide, this.m_quest);
                _loc_4 = _loc_3.createFromXml(_loc_2);
                if (_loc_4)
                {
                    m_guide.registerSequence(_loc_3);
                    _loc_5 = _loc_2.action;
                    for(int i0 = 0; i0 < _loc_5.size(); i0++) 
                    {
                    		_loc_6 = _loc_5.get(i0);

                        _loc_7 = String(_loc_6.@name);
                        _loc_8 = ACTIONS.get(_loc_7);
                        if (_loc_8 != null)
                        {
                            _loc_9 =(GuideAction) new _loc_8;
                            _loc_9.setGuide(m_guide, _loc_3);
                            if (_loc_9.createFromXml(_loc_6))
                            {
                                _loc_3.addAction(_loc_9);
                            }
                            else
                            {
                                ErrorManager.addError("Failed to parse tutorial step for " + _loc_7);
                            }
                            continue;
                        }
                        ErrorManager.addError("Unknown tutorial action: " + _loc_7);
                    }
                }
            }
            return;
        }//end

    }



