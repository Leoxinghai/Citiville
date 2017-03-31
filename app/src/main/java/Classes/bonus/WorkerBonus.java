package Classes.bonus;

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
import Modules.workers.*;

    public class WorkerBonus extends HarvestBonus
    {
        private String m_featureName ;
        private HarvestableResource m_harvestableResource ;

        public  WorkerBonus (XML param1 )
        {
            super(param1);
            this.m_featureName = param1.@featureName;
            return;
        }//end

         public void  init (MapResource param1 )
        {
            this.m_harvestableResource =(HarvestableResource) param1;
            _loc_2 =Global.factoryWorkerManager.getWorkers(this.m_harvestableResource.getWorkerBucket ());
            int _loc_3 =0;
            if (_loc_2)
            {
                _loc_3 = _loc_2.getWorkerCount();
            }
            m_percentModifier = initialPercentModifier * _loc_3;
            return;
        }//end

         public double  maxPercentModifier ()
        {
            _loc_1 = this.m_harvestableResource.harvestingDefinition ;
            int _loc_2 =0;
            if (_loc_1.workers && _loc_1.workers.members)
            {
                _loc_2 = _loc_1.workers.members.length;
            }
            return initialPercentModifier * _loc_2;
        }//end

    }



