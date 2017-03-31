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
import org.aswing.geom.*;

    public class ButtonStateObject extends Sprite
    {
        protected DisplayObject defaultImage ;
        protected DisplayObject pressedImage ;
        protected DisplayObject pressedSelectedImage ;
        protected DisplayObject disabledImage ;
        protected DisplayObject selectedImage ;
        protected DisplayObject disabledSelectedImage ;
        protected DisplayObject rolloverImage ;
        protected DisplayObject rolloverSelectedImage ;
        protected DisplayObject defaultButtonImage ;
        protected boolean enabled =true ;
        protected boolean pressed =false ;
        protected boolean selected =false ;
        protected boolean rollovered =false ;
        protected boolean defaultButton =false ;
        protected DisplayObject lastViewedImage ;

        public  ButtonStateObject ()
        {
            name = "ButtonStateObject";
            mouseEnabled = false;
            mouseChildren = false;
            tabEnabled = false;
            return;
        }//end

        public void  updateRepresent (IntRectangle param1)
        {
            DisplayObject _loc_3 =null ;
            _loc_2 = this.defaultImage ;
            if (!this.enabled)
            {
                if (this.selected && this.disabledSelectedImage)
                {
                    _loc_3 = this.disabledSelectedImage;
                }
                else
                {
                    _loc_3 = this.disabledImage;
                }
            }
            else if (this.pressed)
            {
                if (this.selected && this.pressedSelectedImage)
                {
                    _loc_3 = this.pressedSelectedImage;
                }
                else
                {
                    _loc_3 = this.pressedImage;
                }
            }
            else if (this.rollovered)
            {
                if (this.selected && this.rolloverSelectedImage)
                {
                    _loc_3 = this.rolloverSelectedImage;
                }
                else
                {
                    _loc_3 = this.rolloverImage;
                }
            }
            else if (this.selected)
            {
                _loc_3 = this.selectedImage;
            }
            else if (this.defaultButton)
            {
                _loc_3 = this.defaultButtonImage;
            }
            if (_loc_3 != null)
            {
                _loc_2 = _loc_3;
            }
            if (_loc_2 != this.lastViewedImage)
            {
                if (this.lastViewedImage)
                {
                    this.lastViewedImage.visible = false;
                }
                if (_loc_2)
                {
                    _loc_2.visible = true;
                }
                this.lastViewedImage = _loc_2;
            }
            if (param1 != null)
            {
                if (_loc_2)
                {
                    _loc_2.width = param1.width;
                    _loc_2.height = param1.height;
                }
                this.x = param1.x;
                this.y = param1.y;
            }
            return;
        }//end

        public void  setEnabled (boolean param1 )
        {
            this.enabled = param1;
            return;
        }//end

        public void  setPressed (boolean param1 )
        {
            this.pressed = param1;
            return;
        }//end

        public void  setSelected (boolean param1 )
        {
            this.selected = param1;
            return;
        }//end

        public void  setRollovered (boolean param1 )
        {
            this.rollovered = param1;
            return;
        }//end

        public void  setDefaultButton (boolean param1 )
        {
            this.defaultButton = param1;
            return;
        }//end

        protected void  checkAsset (DisplayObject param1 )
        {
            if (param1 != null && contains(param1))
            {
                throw new Error("You are set a already exists asset!");
            }
            return;
        }//end

         public DisplayObject  addChild (DisplayObject param1 )
        {
            if (param1 != null)
            {
                param1.visible = false;
                return super.addChild(param1);
            }
            return null;
        }//end

        public void  setDefaultButtonImage (DisplayObject param1 )
        {
            this.checkAsset(this.defaultButtonImage);
            this.defaultButtonImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setDefaultImage (DisplayObject param1 )
        {
            this.checkAsset(this.defaultImage);
            this.defaultImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setPressedImage (DisplayObject param1 )
        {
            this.checkAsset(this.pressedImage);
            this.pressedImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setPressedSelectedImage (DisplayObject param1 )
        {
            this.checkAsset(this.pressedSelectedImage);
            this.pressedSelectedImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setDisabledImage (DisplayObject param1 )
        {
            this.checkAsset(this.disabledImage);
            this.disabledImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setSelectedImage (DisplayObject param1 )
        {
            this.checkAsset(this.selectedImage);
            this.selectedImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setDisabledSelectedImage (DisplayObject param1 )
        {
            this.checkAsset(this.disabledSelectedImage);
            this.disabledSelectedImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setRolloverImage (DisplayObject param1 )
        {
            this.checkAsset(this.rolloverImage);
            this.rolloverImage = param1;
            this.addChild(param1);
            return;
        }//end

        public void  setRolloverSelectedImage (DisplayObject param1 )
        {
            this.checkAsset(this.rolloverSelectedImage);
            this.rolloverSelectedImage = param1;
            this.addChild(param1);
            return;
        }//end

    }


