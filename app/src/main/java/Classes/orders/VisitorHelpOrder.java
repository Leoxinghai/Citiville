package Classes.orders;

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

    public class VisitorHelpOrder extends AbstractOrder
    {
        private Array m_helpTargets ;
        private Array m_claimedHelpTargets ;
        private Array m_gaveMastery ;
        private String m_status ;
        public static  String UNCLAIMED ="unclaimed";
        public static  String REJECTED ="rejected";
        public static  String CLAIMED ="claimed";
        public static  String INITIATED ="initiated";
        public static  String HELP_TARGETS ="helpTargets";
        public static  String STATUS ="status";
        public static  String GAVE_MASTERY ="gaveMastery";

        public  VisitorHelpOrder (String param1 ,String param2 ,Array param3 =null ,String param4 =null )
        {
            super(param1, param2, OrderType.VISITOR_HELP, param4);
            this.m_helpTargets = param3 || new Array();
            this.m_gaveMastery = new Array();
            setState(OrderStates.PENDING);
            this.m_claimedHelpTargets = new Array();
            return;
        }//end

        public void  addHelpTarget (int param1 )
        {
            this.m_helpTargets.push(param1);
            return;
        }//end

        public void  setHelpTargets (Array param1 )
        {
            this.m_helpTargets = param1;
            return;
        }//end

        public Array  getHelpTargets ()
        {
            return this.m_helpTargets;
        }//end

        public void  claimHelpTarget (int param1 )
        {
            this.m_claimedHelpTargets.push(param1);
            return;
        }//end

        public boolean  shouldHelpTargetGiveMastery (int param1 )
        {
            if (this.m_gaveMastery.indexOf(param1) > -1)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isHelpTargetClaimed (int param1 )
        {
            if (this.m_claimedHelpTargets.indexOf(param1) > -1)
            {
                return true;
            }
            return false;
        }//end

        public String  getStatus ()
        {
            return this.m_status;
        }//end

        public void  setStatus (String param1 )
        {
            this.m_status = param1;
            return;
        }//end

         public void  setParams (Object param1 )
        {
            super.setParams(param1);
            this.m_helpTargets = param1.get(HELP_TARGETS) || new Array();
            this.m_gaveMastery = param1.get(GAVE_MASTERY) || new Array();
            this.m_status = param1.get(STATUS);
            return;
        }//end

         public Object  getParams ()
        {
            _loc_1 = super.getParams();
            _loc_1.put(HELP_TARGETS,  this.m_helpTargets);
            _loc_1.put(STATUS,  this.m_status);
            _loc_1.put(GAVE_MASTERY,  this.m_gaveMastery);
            return _loc_1;
        }//end

         public void  updateOrder (Object param1 )
        {
            this.m_helpTargets = param1.get(HELP_TARGETS) || new Array();
            return;
        }//end

    }



