package Modules.zoo;

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
import Classes.LogicComponents.*;
import Classes.effects.*;
import Classes.util.*;
import Events.*;

    public class MunicipalComponentZoo extends MunicipalComponentBase
    {
        public static  String DATA_SOURCE_TYPE ="giftSenders";

        public  MunicipalComponentZoo (Municipal param1 )
        {
            super(param1);
            m_municipal.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            return;
        }//end  

        protected void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (event.obj == DATA_SOURCE_TYPE)
            {
                m_municipal.updateStagePickEffect();
            }
            return;
        }//end  

         public boolean  enableUpdateArrow ()
        {
            return !Global.isVisiting() && (this.checkDataExists() || this.canPostFeed()) || super.enableUpdateArrow();
        }//end  

         public void  createStagePickEffect ()
        {
            if (!Global.isVisiting() && isHarvestable())
            {
                super.createStagePickEffect();
            }
            else
            {
                createStagePickEffectHelper(StagePickEffect.PICK_EXCLAMATION);
            }
            return;
        }//end  

        protected boolean  checkDataExists ()
        {
            _loc_1 = m_municipal.getDataForMechanic(DATA_SOURCE_TYPE);
            if (_loc_1 && GameUtil.countObjectLength(_loc_1) > 0)
            {
                return true;
            }
            return false;
        }//end  

        protected boolean  canPostFeed ()
        {
            return Global.world.viralMgr.canPost(m_municipal.getItem().feed);
        }//end  

    }



