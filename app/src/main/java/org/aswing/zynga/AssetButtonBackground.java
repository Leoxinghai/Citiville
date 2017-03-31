package org.aswing.zynga;

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

//import flash.display.*;
//import flash.system.*;
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.util.*;

    public class AssetButtonBackground extends Object implements GroundDecorator
    {
        protected ButtonStateObject stateAsset ;

        public  AssetButtonBackground (String param1 ,ApplicationDomain param2 )
        {
            this.stateAsset = new ButtonStateObject();
            if (param1 != null)
            {
                this.reflectAssets(param1, param2);
            }
            return;
        }//end

        public ButtonStateObject  getStateAsset ()
        {
            return this.stateAsset;
        }//end

        public void  reflectAssets (String param1 ,ApplicationDomain param2 )
        {
            _loc_3 = this.createInstance(param1 +"up",param2 );
            _loc_4 = this.createInstance(param1 +"down",param2 );
            _loc_5 = this.createInstance(param1 +"downSelected",param2 );
            _loc_6 = this.createInstance(param1 +"disabled",param2 );
            _loc_7 = this.createInstance(param1 +"selected",param2 );
            _loc_8 = this.createInstance(param1 +"disabledSelected",param2 );
            _loc_9 = this.createInstance(param1 +"over",param2 );
            _loc_10 = this.createInstance(param1 +"overSelected",param2 );
            this.stateAsset.setDefaultButtonImage(this.createInstance(param1 + "DefaultButton", param2));
            this.stateAsset.setDefaultImage(_loc_3);
            this.stateAsset.setPressedImage(_loc_4);
            this.stateAsset.setPressedSelectedImage(_loc_5);
            this.stateAsset.setDisabledImage(_loc_6);
            this.stateAsset.setSelectedImage(_loc_7);
            this.stateAsset.setDisabledSelectedImage(_loc_8);
            this.stateAsset.setRolloverImage(_loc_9);
            this.stateAsset.setRolloverSelectedImage(_loc_10);
            return;
        }//end

        protected DisplayObject  createInstance (String param1 ,ApplicationDomain param2 )
        {
            DisplayObject ass ;
            linkage = param1;
            domain = param2;
            try
            {
                ass = Reflection.createInstance(linkage, domain);
                return ass;
            }
            catch (er:Error)
            {
            }
            return null;
        }//end

        public void  updateDecorator (Component param1 ,Graphics2D param2 ,IntRectangle param3 )
        {
            _loc_4 = param1as AbstractButton ;
            _loc_5 = param1(as AbstractButton ).getModel ();
            this.stateAsset.setEnabled(_loc_5.isEnabled());
            this.stateAsset.setPressed(_loc_5.isPressed() && _loc_5.isArmed());
            this.stateAsset.setSelected(_loc_5.isSelected());
            this.stateAsset.setRollovered(_loc_4.isRollOverEnabled() && _loc_5.isRollOver());
            this.stateAsset.setDefaultButton(_loc_4 is JButton && JButton(_loc_4).isDefaultButton());
            this.stateAsset.updateRepresent(param3);
            return;
        }//end

        public DisplayObject  getDisplay (Component param1 )
        {
            return this.stateAsset;
        }//end

    }


