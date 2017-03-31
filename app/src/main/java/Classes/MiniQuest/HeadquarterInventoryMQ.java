package Classes.MiniQuest;

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

//import flash.events.*;

    public class HeadquarterInventoryMQ extends MiniQuest
    {
        private Vector<String> m_newHeadquarterTypes;
        public static  String QUEST_NAME ="headquarterInventoryMQ";

        public  HeadquarterInventoryMQ ()
        {
            super(QUEST_NAME);
            m_recurrenceTime = 10;
            return;
        }//end

        public boolean  isQuestNeeded ()
        {
            boolean _loc_1 =false ;
            return _loc_1;
        }//end

        public void  showQuest ()
        {
            return;
        }//end

         protected void  onIconClicked (MouseEvent event )
        {
            super.onIconClicked(event);
            this.showQuest();
            m_recurrenceTime = 0;
            return;
        }//end

         protected void  endQuest ()
        {
            super.endQuest();
            return;
        }//end

    }


