package Classes;

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


    public class WorkersDefinition
    {
        private String m_type ;
        private int m_cashCost ;
        private Vector<WorkerDefinition> m_members;
        private int m_amount ;

        public  WorkersDefinition ()
        {
            this.m_type = null;
            this.m_cashCost = 0;
            this.m_members = new Vector<WorkerDefinition>();
            return;
        }//end

        public String  type ()
        {
            return this.m_type;
        }//end

        public int  cashCost ()
        {
            return this.m_cashCost;
        }//end

        public WorkerDefinition Vector  members ().<>
        {
            return this.m_members;
        }//end

        public int  amount ()
        {
            return this.m_amount;
        }//end

        public void  loadObject (XML param1 )
        {
            XML _loc_2 =null ;
            WorkerDefinition _loc_3 =null ;
            this.m_type = param1.@type;
            this.m_cashCost = int(param1.@cashCost);
            this.m_amount = int(param1.@amount);
            for(int i0 = 0; i0 < param1.worker.size(); i0++)
            {
            	_loc_2 = param1.worker.get(i0);

                _loc_3 = new WorkerDefinition();
                _loc_3.loadObject(_loc_2);
                this.m_members.push(_loc_3);
            }
            if (this.m_amount == 0)
            {
                this.m_amount = this.m_members.length;
            }
            return;
        }//end

    }



