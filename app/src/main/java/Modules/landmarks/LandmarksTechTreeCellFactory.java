package Modules.landmarks;

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

import Display.DialogUI.*;
//import flash.utils.*;
import org.aswing.ext.*;

    public class LandmarksTechTreeCellFactory implements GridListCellFactory
    {
        protected Dictionary m_assetDict ;
        protected GenericDialogView m_techtree ;

        public  LandmarksTechTreeCellFactory (Dictionary param1 ,GenericDialogView param2 )
        {
            this.m_assetDict = param1;
            this.m_techtree = param2;
            return;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            return new LandmarksTechTreeCell(this.m_assetDict, this.m_techtree);
        }//end

    }



