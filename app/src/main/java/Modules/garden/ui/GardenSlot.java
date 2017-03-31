package Modules.garden.ui;

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

    public class GardenSlot extends Sprite
    {
        private Item m_gardenSlot ;
        private DisplayObject m_flowerAsset ;
        private Sprite m_flowerHolder ;
        private Sprite m_backgroundHolder ;
        private Sprite m_background ;
        private SimpleButton m_btnClose ;
        private int m_index ;
        private String m_currFlowerStickerUrl ;
        private Item m_flowerDefinition ;

        public  GardenSlot ()
        {
            _loc_1 = new GardenDialog.assetDict.get( "enclosure_box") ;
            this.m_btnClose = new SimpleButton(new GardenDialog.assetDict.get("enclosure_close_btn_up"), new GardenDialog.assetDict.get("enclosure_close_btn_over"), new GardenDialog.assetDict.get("enclosure_close_btn_up"), new GardenDialog.assetDict.get("enclosure_close_btn_over"));
            this.m_btnClose.addEventListener(MouseEvent.CLICK, this.onCloseClick, false, 0, true);
            this.hideCloseBtn();
            this.m_background = new Sprite();
            this.m_background.addChild(_loc_1);
            this.m_backgroundHolder = new Sprite();
            this.m_backgroundHolder.addChild(this.m_background);
            this.m_flowerHolder = new Sprite();
            addChild(this.m_backgroundHolder);
            addChild(this.m_flowerHolder);
            addChild(this.m_btnClose);
            this.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
            this.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);
            return;
        }//end

        public boolean  isOccupied ()
        {
            return this.m_flowerDefinition != null;
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

        public Item  getItem ()
        {
            return this.m_flowerDefinition;
        }//end

        public void  addFlower (String param1 )
        {
            this.m_flowerDefinition = Global.gameSettings().getItemByName(param1);
            if (this.m_flowerDefinition)
            {
                this.m_currFlowerStickerUrl = this.m_flowerDefinition.getImageByName("editor");
                LoadingManager.loadFromUrl(this.m_currFlowerStickerUrl, {priority:LoadingManager.PRIORITY_HIGH, completeCallback:this.onFlowerAssetLoadCompletion});
            }
            return;
        }//end

        public void  removeFlower ()
        {
            if (this.m_flowerAsset && this.m_flowerAsset.parent)
            {
                this.m_flowerAsset.parent.removeChild(this.m_flowerAsset);
            }
            this.m_flowerDefinition = null;
            this.hideCloseBtn();
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

        private void  onFlowerAssetLoadCompletion (Event event )
        {
            _loc_2 =(LoaderInfo) event.currentTarget;
            while (this.m_flowerHolder.numChildren)
            {

                this.m_flowerHolder.removeChildAt(0);
            }
            if (this.isOccupied)
            {
                this.m_flowerAsset = _loc_2.content;
                this.m_flowerAsset.x = (this.m_background.width - this.m_flowerAsset.width) * 0.5;
                this.m_flowerAsset.y = this.m_background.height - this.m_flowerAsset.height;
                this.m_btnClose.x = this.m_flowerAsset.width - this.m_btnClose.width;
                this.m_flowerHolder.addChild(this.m_flowerAsset);
            }
            return;
        }//end

        private void  onMouseOver (MouseEvent event )
        {
            if (this.isOccupied)
            {
                this.showCloseBtn();
            }
            return;
        }//end

        private void  onMouseOut (MouseEvent event )
        {
            this.hideCloseBtn();
            return;
        }//end

        private void  onCloseClick (MouseEvent event )
        {
            dispatchEvent(new Event(Event.CLOSE));
            return;
        }//end

    }



