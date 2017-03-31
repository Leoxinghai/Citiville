package Display.DialogUI;

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
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class CrewCellFactory implements GridListCellFactory
    {
        protected Dictionary m_assets ;
        protected IntDimension m_preferredCellSize ;

        public  CrewCellFactory (Dictionary param1 ,IntDimension param2 )
        {
            this.m_assets = param1;
            this.m_preferredCellSize = param2;
            return;
        }//end

        public Dictionary  assets ()
        {
            return this.m_assets;
        }//end

        public int  preferredCellWidth ()
        {
            if (this.m_preferredCellSize)
            {
                return this.m_preferredCellSize.width;
            }
            return 0;
        }//end

        public int  preferredCellHeight ()
        {
            if (this.m_preferredCellSize)
            {
                return this.m_preferredCellSize.height;
            }
            return 0;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            return new CrewCell(this, new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.LEFT));
        }//end

    }


