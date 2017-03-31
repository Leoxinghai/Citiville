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
import Engine.Transactions.*;
import Modules.GlobalTable.*;


    public class TCheckInvalidQuests extends Transaction
    {
        private Array m_invalidQuests ;

        public  TCheckInvalidQuests (Vector param1 .<Quest >)
        {
            Quest _loc_2 =null ;
            this.m_invalidQuests = new Array();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_2 = param1.get(i0);

                this.m_invalidQuests.push(_loc_2.name);
                this.clearQuestData(_loc_2);
            }
            return;
        }//end

        public void  clearQuestData (Quest param1 )
        {
            Object _loc_2 =null ;
            for(int i0 = 0; i0 < param1.tasks.size(); i0++) 
            {
            		_loc_2 = param1.tasks.get(i0);

                if (_loc_2.get("overrideTable"))
                {
                    GlobalTableOverrideManager.instance.removeGlobalData(_loc_2.get("overrideTable").get("keyword"), _loc_2.get("overrideTable").get("table"), param1.name);
                }
            }
            return;
        }//end

         public void  perform ()
        {
            signedCall("QuestService.checkInvalidQuests", this.m_invalidQuests);
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            return;
        }//end

    }



