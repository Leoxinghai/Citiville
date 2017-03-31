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

    public class LotOrder extends AbstractOrder
    {
        public static  String LOT_ID ="lotId";
        public static  String RESOURCE_TYPE ="resourceType";
        public static  String ORDER_RESOURCE_NAME ="orderResourceName";
        public static  String CONSTRUCTION_COUNT ="constructionCount";
        public static  String OFFSET_X ="offsetX";
        public static  String OFFSET_Y ="offsetY";

        public  LotOrder (String param1 ,String param2 ,int param3 =0,String param4 ="",String param5 ="",int param6 =0,String param7 =null )
        {
            super(param1, param2, OrderType.LOT, param7);
            this.setLotId(param3);
            this.setResourceType(param4);
            this.setOrderResourceName(param5);
            this.setConstructionCount(param6);
            setState(OrderStates.PENDING);
            return;
        }//end  

        public int  getLotId ()
        {
            return m_params.get(LOT_ID);
        }//end  

        public void  setLotId (int param1 )
        {
            m_params.put(LOT_ID,  param1);
            return;
        }//end  

        public String  getResourceType ()
        {
            return m_params.get(RESOURCE_TYPE);
        }//end  

        public void  setResourceType (String param1 )
        {
            m_params.put(RESOURCE_TYPE,  param1);
            return;
        }//end  

        public String  getOrderResourceName ()
        {
            return m_params.get(ORDER_RESOURCE_NAME);
        }//end  

        public void  setOrderResourceName (String param1 )
        {
            m_params.put(ORDER_RESOURCE_NAME,  param1);
            return;
        }//end  

        public int  getConstructionCount ()
        {
            return m_params.get(CONSTRUCTION_COUNT);
        }//end  

        public void  setConstructionCount (int param1 )
        {
            m_params.put(CONSTRUCTION_COUNT,  param1);
            return;
        }//end  

        public int  getXOffset ()
        {
            return m_params.get(OFFSET_X);
        }//end  

        public void  setXOffset (int param1 )
        {
            m_params.put(OFFSET_X,  param1);
            return;
        }//end  

        public int  getYOffset ()
        {
            return m_params.get(OFFSET_Y);
        }//end  

        public void  setYOffset (int param1 )
        {
            m_params.put(OFFSET_Y,  param1);
            return;
        }//end  

         public Object  getParams ()
        {
            Object _loc_1 =new Object ();
            _loc_1.put(SENDER_ID,  m_params.get(SENDER_ID));
            _loc_1.put(RECIPIENT_ID,  m_params.get(RECIPIENT_ID));
            _loc_1.put(LOT_ID,  m_params.get(LOT_ID));
            _loc_1.put(RESOURCE_TYPE,  m_params.get(RESOURCE_TYPE));
            _loc_1.put(ORDER_RESOURCE_NAME,  m_params.get(ORDER_RESOURCE_NAME));
            _loc_1.put(CONSTRUCTION_COUNT,  m_params.get(CONSTRUCTION_COUNT));
            _loc_1.put(OFFSET_X,  m_params.get(OFFSET_X));
            _loc_1.put(OFFSET_Y,  m_params.get(OFFSET_Y));
            return _loc_1;
        }//end  

    }


