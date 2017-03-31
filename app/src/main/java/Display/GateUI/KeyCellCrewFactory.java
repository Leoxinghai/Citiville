package Display.GateUI;

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

import org.aswing.ext.*;
    public class KeyCellCrewFactory implements GridListCellFactory
    {
        private Array m_dataList ;
        private int m_index ;

        public  KeyCellCrewFactory (Array param1 )
        {
            this.m_dataList = param1;
            this.m_index = 0;
            return;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            _loc_1 = this.m_dataList.get(this.m_index);
            this.m_index++;
            return new CrewKeyCell(_loc_1.url, _loc_1.name, _loc_1.cost, _loc_1.count, _loc_1.footerText, _loc_1.backgroundClass, _loc_1.displayObjectClass);
        }//end

    }




