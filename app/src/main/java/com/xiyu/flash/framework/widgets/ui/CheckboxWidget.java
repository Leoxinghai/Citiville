package com.xiyu.flash.framework.widgets.ui;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

import com.xiyu.flash.framework.widgets.CWidget;
import com.xiyu.flash.framework.resources.images.ImageInst;
import com.xiyu.flash.framework.graphics.Graphics2D;

    public class CheckboxWidget extends CWidget {

        private ImageInst mCheckedImage ;

         public void  draw (Graphics2D g ){
            super.draw(g);
            if (this.mChecked)
            {
                g.drawImage(this.mCheckedImage, 0, 0);
            }
            else
            {
                g.drawImage(this.mUncheckedImage, 0, 0);
            };
        }
        public void  setChecked (boolean checked ,boolean tellListener ){
            this.mChecked = checked;
            if (((tellListener) && (!((this.mListener == null)))))
            {
                this.mListener.checkboxChecked(this.mId, this.mChecked);
            };
            markDirty(null);
        }

        private ICheckboxListener mListener ;
        private boolean mChecked =false ;
        private int mId ;

         public void  onMouseDown (int x ,int y ){
            super.onMouseDown(x, y);
            this.mChecked = !(this.mChecked);
            if (this.mListener != null)
            {
                this.mListener.checkboxChecked(this.mId, this.mChecked);
            };
            markDirty(null);
        }

        private ImageInst mUncheckedImage ;

        public boolean  isChecked (){
            return (this.mChecked);
        }

        public  CheckboxWidget (int id ,ImageInst checkedImage ,ImageInst uncheckedImage ,ICheckboxListener listener ){
            this.mId = id;
            this.mCheckedImage = checkedImage;
            this.mUncheckedImage = uncheckedImage;
            this.mListener = listener;
            doFinger = true;
        }
    }


