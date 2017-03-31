package de.polygonal.ds;

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

        /**
         * A 'java-style' iterator interface.
         */
        public interface Iterator
        {
                /**
                 * Returns the current item and moves the iterator to the next item
                 * in the collection. Note that the next() method returns the
                 * <i>first</i> item in the collection when it's first called.
                 *
                 * @return The next item in the collection.
                 */
                Object next ()

                /**
                 * Checks if a next item exists.
                 *
                 * @return True if a next item exists, otherwise false.
                 */
                boolean  hasNext ()

                /**
                 * Moves the iterator to the first item in the collection.
                 */
                void  start ()

                /**
                 * Grants access to the current item being referenced by the iterator.
                 * This provides a quick way to read or write the current data.
                 */
                Object data ()

                /**
                 * @private
                 */
                void  data (*)obj
        }


