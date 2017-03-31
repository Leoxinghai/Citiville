package Classes.sim;

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
    public class RequirementsIndex
    {
        protected Dictionary m_index ;

        public  RequirementsIndex (XMLList param1 )
        {
            XML _loc_3 =null ;
            Requirements _loc_4 =null ;
            this.m_index = new Dictionary();
            _loc_2 =Global.experimentManager.filterXmlByExperiment(param1.requirements );
            for(int i0 = 0; i0 < _loc_2.size(); i0++) 
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = new Requirements(_loc_3);
                this.m_index.put(_loc_4.name,  _loc_4);
            }
            return;
        }//end

        public Requirements  getRequirementsByName (String param1 )
        {
            Requirements _loc_2 =null ;
            if (this.m_index && this.m_index.get(param1))
            {
                _loc_2 = this.m_index.get(param1);
            }
            return _loc_2;
        }//end

    }



