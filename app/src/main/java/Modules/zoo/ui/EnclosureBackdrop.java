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
import Modules.zoo.events.*;

//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class EnclosureBackdrop extends Sprite
    {
        private Item m_enclosureDefinition ;
        private Vector<ZooEnclosureSlot> m_enclosureSlots;
        private DisplayObject m_background ;
        private ZooSpeechBubble m_speechBubble ;
        private int m_speechBubbleHideTimeout ;
        private static  int SPEECH_BUBBLE_HIDE_TIME =500;

        public  EnclosureBackdrop (String param1 )
        {
            addEventListener(Event.REMOVED_FROM_STAGE, this.onRemoveFromStage, false, 0, true);
            this.m_enclosureDefinition = Global.gameSettings().getItemByName(param1);
            this.m_enclosureSlots = new Vector<ZooEnclosureSlot>();
            this.loadBackground();
            _loc_2 =Global.world.getObjectsByNames(.get(param1) ).length >0;
            if (_loc_2)
            {
                this.prepareEnclosureSlots();
            }
            this.prepareSpeechBubble();
            return;
        }//end

        public DisplayObject  background ()
        {
            return this.m_background;
        }//end

        public ZooSpeechBubble  speechBubble ()
        {
            return this.m_speechBubble;
        }//end

        private void  loadBackground ()
        {
            _loc_1 = this.m_enclosureDefinition.getImageByName("enclosure");
            if (_loc_1)
            {
                LoadingManager.loadFromUrl(_loc_1, {priority:LoadingManager.PRIORITY_LOW, completeCallback:this.onLoadBackgroundComplete});
            }
            return;
        }//end

        private void  setBackground (DisplayObject param1 )
        {
            if (this.m_background && this.m_background.parent)
            {
                this.m_background.parent.removeChild(this.m_background);
            }
            this.m_background = param1;
            dispatchEvent(new ZooDialogEvent(ZooDialogEvent.DISPLAY_UPDATE));
            return;
        }//end

        private void  prepareEnclosureSlots ()
        {
            Point _loc_3 =null ;
            ZooEnclosureSlot _loc_4 =null ;
            _loc_1 = this.m_enclosureDefinition.displayLocations.dialogDisplayLocations ;
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(Point) _loc_1.get(_loc_2);
                _loc_4 = new ZooEnclosureSlot();
                _loc_4.index = _loc_2;
                _loc_4.x = _loc_3.x - _loc_4.width * 0.5;
                _loc_4.y = _loc_3.y - _loc_4.height;
                _loc_4.addEventListener(Event.CLOSE, this.onEnclosureSlotClose, false, 0, true);
                _loc_4.addEventListener(MouseEvent.ROLL_OVER, this.onEnclosureSlotRollOver, false, 0, true);
                _loc_4.addEventListener(MouseEvent.ROLL_OUT, this.onEnclosureSlotRollOut, false, 0, true);
                addChild(_loc_4);
                this.m_enclosureSlots.push(_loc_4);
                _loc_2++;
            }
            dispatchEvent(new ZooDialogEvent(ZooDialogEvent.DISPLAY_UPDATE));
            return;
        }//end

        private void  prepareSpeechBubble ()
        {
            this.m_speechBubble = new ZooSpeechBubble();
            this.m_speechBubble.x = 30;
            this.m_speechBubble.y = 20;
            addChild(this.m_speechBubble);
            this.m_speechBubble.addEventListener(Event.CLOSE, this.onSpeechBubbleClose, false, 0, true);
            return;
        }//end

        private void  hideSpeechBubble ()
        {
            if (this.m_speechBubble && this.m_speechBubble.parent)
            {
                this.m_speechBubble.parent.removeChild(this.m_speechBubble);
            }
            return;
        }//end

        public void  addAnimalToSlot (String param1 ,int param2 )
        {
            if (this.m_enclosureSlots.length > param2)
            {
                this.m_enclosureSlots.get(param2).addAnimal(param1);
            }
            return;
        }//end

        private void  onLoadBackgroundComplete (Event event )
        {
            _loc_2 =(LoaderInfo) event.currentTarget;
            this.setBackground(_loc_2.content);
            return;
        }//end

        private void  onEnclosureSlotClose (Event event )
        {
            ZooDialogEvent _loc_3 =null ;
            _loc_2 =(ZooEnclosureSlot) event.currentTarget;
            if (_loc_2.isOccupied)
            {
                _loc_3 = new ZooDialogEvent(ZooDialogEvent.REMOVE_ANIMAL_FROM_DISPLAY);
                _loc_3.animalItemName = _loc_2.animalDefinition.name;
                _loc_3.slotIndex = _loc_2.index;
                dispatchEvent(_loc_3);
                _loc_2.removeAnimal();
            }
            return;
        }//end

        private void  onEnclosureSlotRollOver (MouseEvent event )
        {
            if ((event.currentTarget as ZooEnclosureSlot).isOccupied)
            {
                clearTimeout(this.m_speechBubbleHideTimeout);
                this.m_speechBubbleHideTimeout = setTimeout(this.hideSpeechBubble, SPEECH_BUBBLE_HIDE_TIME);
            }
            return;
        }//end

        private void  onEnclosureSlotRollOut (MouseEvent event )
        {
            clearTimeout(this.m_speechBubbleHideTimeout);
            addChild(this.m_speechBubble);
            return;
        }//end

        private void  onSpeechBubbleClose (Event event )
        {
            dispatchEvent(new Event(Event.CLOSE));
            return;
        }//end

        private void  onRemoveFromStage (Event event )
        {
            clearTimeout(this.m_speechBubbleHideTimeout);
            return;
        }//end

    }



