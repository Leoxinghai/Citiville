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

import Classes.*;
import Classes.Desires.*;

    public class DesireActionSelection implements IActionSelection
    {
        protected DesirePeep m_peep ;

        public  DesireActionSelection (DesirePeep param1 )
        {
            this.m_peep = param1;
            return;
        }//end

        public Array  getNextActions ()
        {
            Desire _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            DWanderRoads _loc_6 =null ;
            _loc_1 = SelectionResult.FAIL;
            this.m_peep.wanderer = false;
            _loc_2 = this.m_peep.getDesires ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++) 
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.getState();
                if (_loc_4 == Desire.STATE_NOT_STARTED)
                {
                    if (_loc_3.isActionable())
                    {
                        _loc_1 = _loc_3.getSelection();
                        if (SelectionResult.FAIL != _loc_1)
                        {
                            _loc_3.setState(Desire.STATE_STARTED);
                            break;
                        }
                    }
                    continue;
                }
                if (_loc_4 == Desire.STATE_STARTED)
                {
                    if (_loc_3.isActionable())
                    {
                        _loc_5 = this.m_peep.actionQueue.getStates().length;
                        if (_loc_5 < 1)
                        {
                            _loc_1 = _loc_3.getSelection();
                            if (SelectionResult.FAIL != _loc_1)
                            {
                                break;
                            }
                        }
                    }
                }
            }
            if (_loc_1 == SelectionResult.FAIL)
            {
                _loc_6 = new DWanderRoads(this.m_peep);
                _loc_1 = _loc_6.getSelection();
                this.m_peep.wanderer = true;
            }
            return _loc_1.actions;
        }//end

    }



