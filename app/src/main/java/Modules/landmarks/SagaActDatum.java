package Modules.landmarks;

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

import Init.*;
import Modules.saga.*;

    public class SagaActDatum
    {
        private String m_actName ;
        private int m_unlockLevel ;
        private String m_state ;
        private String m_iconUrl ;
        public static  String STATE_UNAVAILABLE ="unavailable";
        public static  String STATE_LEVEL_LOCKED ="level_locked";
        public static  String STATE_IN_PROGRESS ="in_progress";
        public static  String STATE_COMPLETE ="complete";

        public  SagaActDatum (String param1 ,String param2 )
        {
            this.m_actName = param2;
            if (!SagaManager.instance.isActAvailable(param2))
            {
                this.m_state = STATE_UNAVAILABLE;
                return;
            }
            this.m_unlockLevel = getSagaActUnlockLevel(param1, param2);
            if (Global.player.level < this.m_unlockLevel)
            {
                this.m_state = STATE_LEVEL_LOCKED;
            }
            else
            {
                this.m_iconUrl = SagaManager.instance.getActTechTreeIconUrl(param1, param2);
                if (SagaManager.instance.isActComplete(param1, param2))
                {
                    this.m_state = STATE_COMPLETE;
                }
                else
                {
                    this.m_state = STATE_IN_PROGRESS;
                }
            }
            return;
        }//end

        public String  actName ()
        {
            return this.m_actName;
        }//end

        public int  unlockLevel ()
        {
            return this.m_unlockLevel;
        }//end

        public String  state ()
        {
            return this.m_state;
        }//end

        public String  iconUrl ()
        {
            return this.m_iconUrl;
        }//end

        private static int  getQuestUnlockLevel (String param1 )
        {
            _loc_2 = QuestSettingsInit.getQuestXMLByName(param1);
            if (_loc_2 == null)
            {
                return 0;
            }
            _loc_3 = _loc_2.@level_block.get(0);
            if (_loc_3 == null)
            {
                return 0;
            }
            return parseInt(_loc_3.toString());
        }//end

        private static int  getSagaActUnlockLevel (String param1 ,String param2 )
        {
            int result ;
            sagaName = param1;
            actName = param2;
            result;
            SagaManager .instance .forEachQuestInAct (sagaName ,actName ,void  (String param1 )
            {
                _loc_2 = getQuestUnlockLevel(param1);
                if (_loc_2 > result)
                {
                    result = _loc_2;
                }
                return;
            }//end
            );
            return result;
        }//end

    }



