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

import Classes.inventory.*;
import Display.*;
import Display.RenameUI.*;
//import flash.events.*;

    public class RequestItemMQ extends MiniQuest
    {
        protected Array m_itemsToWatch ;
        protected boolean m_hasBeenShown ;
        public static  String QUEST_NAME ="requestItemMQ";

        public  RequestItemMQ ()
        {
            this.m_itemsToWatch = .get(RequestItemType.SIGNATURE);
            super(QUEST_NAME);
            this.m_hasBeenShown = false;
            m_recurrenceTime = 0;
            return;
        }//end  

        public boolean  isQuestNeeded ()
        {
            String _loc_1 =null ;
            if (!this.m_hasBeenShown)
            {
                for(int i0 = 0; i0 < this.m_itemsToWatch.size(); i0++) 
                {
                		_loc_1 = this.m_itemsToWatch.get(i0);
                    
                    if (this.typeIsNeeded(_loc_1))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end  

         protected void  initQuest ()
        {
            this.m_hasBeenShown = true;
            super.initQuest();
            return;
        }//end  

        protected boolean  typeIsNeeded (String param1 )
        {
            switch(param1)
            {
                case RequestItemType.SIGNATURE:
                {
                    return false;
                }
                default:
                {
                    return false;
                    break;
                }
            }
        }//end  

         protected void  onIconClicked (MouseEvent event )
        {
            super.onIconClicked(event);
            RenameDialog _loc_2 =new RenameDialog ();
            UI.displayPopup(_loc_2);
            return;
        }//end  

    }


