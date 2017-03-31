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

    public class TrainCrossing extends Road
    {
        public boolean m_orientation_X =false ;

        public  TrainCrossing (String param1)
        {
            super(param1);
            return;
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            if (m_currentRule)
            {
                if (m_currentRule.tile == "straightaway_e")
                {
                    return m_item.getCachedImage("railroad_crossing_se");
                }
                if (m_currentRule.tile == "straightaway_n")
                {
                    return m_item.getCachedImage("railroad_crossing_sw");
                }
            }
            return null;
        }//end

         protected void  updateAdjacent ()
        {
            int _loc_1 =0;
            super.updateAdjacent();
            int _loc_2 =0;
            while (_loc_2 < m_adjacent.length())
            {

                _loc_1 = m_adjacent.get(_loc_2);
                if (this.m_orientation_X && (_loc_1 == UP || _loc_1 == DOWN) || !this.m_orientation_X && (_loc_1 == LEFT || _loc_1 == RIGHT))
                {
                    m_adjacent.splice(_loc_2, 1);
                    _loc_2 = _loc_2 - 1;
                    m_adjacentRoads.put(_loc_1,  null);
                }
                _loc_2++;
            }
            for(int i0 = 0; i0 < m_adjacentPartial.size(); i0++)
            {
            	_loc_1 = m_adjacentPartial.get(i0);

                m_adjacentRoads.put(_loc_1,  null);
            }
            m_adjacentPartial = new Array();
            for(int i0 = 0; i0 < m_adjacentStretch.size(); i0++)
            {
            	_loc_1 = m_adjacentStretch.get(i0);

                m_adjacentRoads.put(_loc_1,  null);
            }
            m_adjacentStretch = new Array();
            for(int i0 = 0; i0 < m_adjacentTight.size(); i0++)
            {
            	_loc_1 = m_adjacentTight.get(i0);

                m_adjacentRoads.put(_loc_1,  null);
            }
            m_adjacentTight = new Array();
            if (m_adjacent.length != 2)
            {
                m_adjacentRoads = new Array();
                m_adjacent = new Array();
            }
            return;
        }//end

    }



