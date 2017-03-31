package Modules.zoo.ui;

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

import Classes.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;

    public class ZooEnclosureSlot extends Sprite
    {
        private Item m_animalDefinition ;
        private DisplayObject m_animalAsset ;
        private Sprite m_animalHolder ;
        private Sprite m_backgroundHolder ;
        private Sprite m_background ;
        private SimpleButton m_btnClose ;
        private int m_index ;
        private String m_currAnimalStickerUrl ;

        public  ZooEnclosureSlot ()
        {
            _loc_1 = new ZooDialog.assetDict.get( "enclosure_box") ;
            this.m_btnClose = new SimpleButton(new ZooDialog.assetDict.get("enclosure_close_btn_up"), new ZooDialog.assetDict.get("enclosure_close_btn_over"), new ZooDialog.assetDict.get("enclosure_close_btn_up"), new ZooDialog.assetDict.get("enclosure_close_btn_over"));
            this.m_btnClose.x = _loc_1.width - this.m_btnClose.width;
            this.m_btnClose.addEventListener(MouseEvent.CLICK, this.onCloseClick, false, 0, true);
            this.hideCloseBtn();
            this.m_background = new Sprite();
            this.m_background.addChild(_loc_1);
            this.m_background.addChild(this.m_btnClose);
            this.m_backgroundHolder = new Sprite();
            this.m_backgroundHolder.addChild(this.m_background);
            addChild(this.m_backgroundHolder);
            this.m_animalHolder = new Sprite();
            addChild(this.m_animalHolder);
            this.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
            this.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);
            return;
        }//end

        public Item  animalDefinition ()
        {
            return this.m_animalDefinition;
        }//end

        public boolean  isOccupied ()
        {
            return this.m_animalDefinition != null;
        }//end

        public int  index ()
        {
            return this.m_index;
        }//end

        public void  index (int param1 )
        {
            this.m_index = param1;
            return;
        }//end

        public void  addAnimal (String param1 )
        {
            this.m_animalDefinition = Global.gameSettings().getItemByName(param1);
            if (this.m_animalDefinition)
            {
                this.m_currAnimalStickerUrl = this.m_animalDefinition.getImageByName("editor");
                LoadingManager.loadFromUrl(this.m_currAnimalStickerUrl, {priority:LoadingManager.PRIORITY_HIGH, completeCallback:this.onAnimalAssetLoadComplete});
            }
            this.hideBackground();
            this.showCloseBtn();
            return;
        }//end

        public void  removeAnimal ()
        {
            if (this.m_animalAsset && this.m_animalAsset.parent)
            {
                this.m_animalAsset.parent.removeChild(this.m_animalAsset);
            }
            this.m_animalDefinition = null;
            this.showBackground();
            this.hideCloseBtn();
            return;
        }//end

        private void  showBackground ()
        {
            this.m_background.alpha = 1;
            boolean _loc_1 =true ;
            this.m_background.mouseChildren = true;
            this.m_background.mouseEnabled = _loc_1;
            return;
        }//end

        private void  hideBackground ()
        {
            this.m_background.alpha = 0;
            boolean _loc_1 =false ;
            this.m_background.mouseChildren = false;
            this.m_background.mouseEnabled = _loc_1;
            return;
        }//end

        private void  hideCloseBtn ()
        {
            this.m_btnClose.alpha = 0;
            this.m_btnClose.mouseEnabled = false;
            return;
        }//end

        private void  showCloseBtn ()
        {
            this.m_btnClose.alpha = 1;
            this.m_btnClose.mouseEnabled = true;
            return;
        }//end

        private void  onAnimalAssetLoadComplete (Event event )
        {
            _loc_2 =(LoaderInfo) event.currentTarget;
            while (this.m_animalHolder.numChildren)
            {

                this.m_animalHolder.removeChildAt(0);
            }
            if (this.isOccupied)
            {
                this.m_animalAsset = _loc_2.content;
                this.m_animalAsset.x = (this.m_background.width - this.m_animalAsset.width) * 0.5;
                this.m_animalAsset.y = this.m_background.height - this.m_animalAsset.height;
                this.m_animalHolder.addChild(this.m_animalAsset);
            }
            return;
        }//end

        private void  onMouseOver (MouseEvent event )
        {
            if (this.isOccupied)
            {
                this.showBackground();
            }
            return;
        }//end

        private void  onMouseOut (MouseEvent event )
        {
            if (this.isOccupied)
            {
                this.hideBackground();
            }
            return;
        }//end

        private void  onCloseClick (MouseEvent event )
        {
            dispatchEvent(new Event(Event.CLOSE));
            return;
        }//end

    }



