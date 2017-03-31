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

    public interface ISlottedContainer
    {

        double  getId ();

        MechanicMapResource  mechanicMapResource ();

        Array  slots ();

        void  slots (Array param1 );

        boolean  hasMysteryItemInInventory (int param1 );

        String  getMysteryItemName (int param1 );

        int  getInitialHarvestBonus (int param1 );

        void  onStoreSlotObject (MapResource param1 );

        void  onRemoveSlotObject (MapResource param1 );

    }


