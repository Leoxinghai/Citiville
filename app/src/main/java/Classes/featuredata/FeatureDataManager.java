package Classes.featuredata;

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
    public class FeatureDataManager
    {
        private Object m_featureData ;
        private Object featureDataClassesByKey ;
        private static FeatureDataManager m_instance ;
        public static  Array FEATUREDATA_COMPONENTS =.get(CarData) ;

        public  FeatureDataManager ()
        {
            this.featureDataClassesByKey = new Object();
            return;
        }//end

        public void  loadFeatureData (Object param1 )
        {
            this.m_featureData = param1;
            if (this.m_featureData == null)
            {
                this.m_featureData = new Object();
            }
            return;
        }//end

        public Object  getFeatureDataClass (String param1 )
        {
            Class _loc_3 =null ;
            _loc_2 = Global.gameSettings().getFeatureDataClasses();
            if (!this.featureDataClassesByKey.hasOwnProperty(param1))
            {
                _loc_3 =(Class) getDefinitionByName("Classes.featuredata." + _loc_2.get(param1).get("dataClass"));
                if (!this.m_featureData.hasOwnProperty(param1))
                {
                    this.m_featureData.put(param1,  new Object());
                }
                this.featureDataClassesByKey.put(param1,  new _loc_3(this.m_featureData.get(param1)));
            }
            return this.featureDataClassesByKey.get(param1);
        }//end

        public static FeatureDataManager  instance ()
        {
            if (!FeatureDataManager.m_instance)
            {
                FeatureDataManager.m_instance = new FeatureDataManager;
            }
            return FeatureDataManager.m_instance;
        }//end

    }



