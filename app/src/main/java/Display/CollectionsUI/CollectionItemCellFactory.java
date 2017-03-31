package Display.CollectionsUI;

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
import org.aswing.geom.*;

    public class CollectionItemCellFactory implements GridListCellFactory
    {
        protected IntDimension m_slotSize ;

        public  CollectionItemCellFactory (IntDimension param1 )
        {
            this.m_slotSize = param1;
            return;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            return new CollectionItemCell(null, this.m_slotSize);
        }//end

    }


