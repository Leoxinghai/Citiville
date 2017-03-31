package Modules.storage.ui;

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

import org.aswing.*;
import org.aswing.ext.*;

    public class StoredItemCellFactory implements GridListCellFactory
    {

        public  StoredItemCellFactory ()
        {
            return;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            return new StoredItemCell(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -6, SoftBoxLayout.TOP));
        }//end

        public static int  getDefaultCellHeight ()
        {
            return StoredItemCell.PREFERRED_HEIGHT;
        }//end

        public static int  getDummyOpenItem ()
        {
            return StoredItemCell.STATE_OPEN;
        }//end

        public static int  getDummyLockedItem ()
        {
            return StoredItemCell.STATE_LOCKED;
        }//end

    }



