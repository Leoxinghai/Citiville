package Modules.guide.actions;

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

//import flash.utils.*;
    public class GAWaitForTransactionOrGameMode extends GAWaitForTransaction
    {
        protected Class m_gameMode ;

        public  GAWaitForTransactionOrGameMode ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            XMLList def ;
            String modeClass ;
            Object gameMode ;
            xml = param1;
            def = checkAndGetElement(xml, "transaction");
            if (!def)
            {
                return false;
            }
            className = String(def.@className);
            count = int(def.@count);
            m_predicate = makeClassTestFn(className);
            if (count > 0)
            {
                m_requiredSuccessCount = count;
            }
            try
            {
                modeClass = String(def.@mode);
                gameMode = getDefinitionByName("GameMode." + modeClass);
                this.m_gameMode =(Class) gameMode;
            }
            catch (e:Error)
            {
                return false;
            }
            return true;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            if (Global.world.getTopGameMode() instanceof this.m_gameMode)
            {
                m_seq.stop(true);
            }
            return;
        }//end

    }



