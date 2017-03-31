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
import Display.*;
import Events.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.garden.*;

//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class GardenBackdrop extends Sprite
    {
        private Item m_gardenDefinition ;
        private Vector<GardenSlot> m_gardenSlots;
        private DisplayObject m_background ;
        private GardenSpeechBubble m_speechBubble ;
        private int m_speechBubbleHideTimeout ;
        private ISlotMechanic m_slotMechanic ;
        private Garden m_garden ;
        private static  int SPEECH_BUBBLE_HIDE_TIME =500;

        public  GardenBackdrop (Garden param1 ,ISlotMechanic param2 )
        {
            addEventListener(Event.REMOVED_FROM_STAGE, this.onRemoveFromStage, false, 0, true);
            this.m_garden = param1;
            this.m_gardenDefinition = Global.gameSettings().getItemByName(param1.getItem().name);
            this.m_gardenSlots = new Vector<GardenSlot>();
            this.m_slotMechanic = param2;
            this.prepareEnclosureSlots();
            this.prepareSpeechBubble();
            this.m_garden.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            this.onMechanicDataChanged();
            return;
        }//end

        public DisplayObject  background ()
        {
            return this.m_background;
        }//end

        private void  prepareEnclosureSlots ()
        {
            Point _loc_3 =null ;
            GardenSlot _loc_4 =null ;
            _loc_1 = this.m_gardenDefinition.displayLocations.dialogDisplayLocations ;
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 =(Point) _loc_1.get(_loc_2);
                _loc_4 = new GardenSlot();
                _loc_4.index = _loc_2;
                _loc_4.x = _loc_3.x - _loc_4.width * 0.5;
                _loc_4.y = _loc_3.y - _loc_4.height;
                _loc_4.addEventListener(Event.CLOSE, this.onGardenSlotClose, false, 0, true);
                _loc_4.addEventListener(MouseEvent.ROLL_OVER, this.onGardenSlotRollOver, false, 0, true);
                _loc_4.addEventListener(MouseEvent.ROLL_OUT, this.onGardenSlotRollOut, false, 0, true);
                addChild(_loc_4);
                this.m_gardenSlots.push(_loc_4);
                _loc_2++;
            }
            dispatchEvent(new GardenDialogEvent(GardenDialogEvent.DISPLAY_UPDATE));
            return;
        }//end

        private void  prepareSpeechBubble ()
        {
            _loc_1 = Math.random ()*4% 4;
            _loc_2 = ZLoc.t("Dialogs","GardenDialog_speechbubble_"+_loc_1 );
            this.m_speechBubble = new GardenSpeechBubble(this.m_garden.getItem(), _loc_2);
            this.m_speechBubble.x = 30;
            this.m_speechBubble.y = 50;
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

        private void  onMechanicDataChanged (Event event =null )
        {
            String _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.m_slotMechanic.numSlots)
            {

                _loc_3 = this.m_slotMechanic.getSlot(_loc_2);
                if (_loc_3)
                {
                    this.m_gardenSlots.get(_loc_2).addFlower(_loc_3);
                }
                _loc_2++;
            }
            return;
        }//end

        private void  onGardenSlotClose (Event event )
        {
            GardenSlot targetSlot ;
            Item item ;
            e = event;
            targetSlot =(GardenSlot) e.currentTarget;
            if (targetSlot.isOccupied)
            {
                item = targetSlot.getItem();
                if (GardenManager.instance.getAmountForFlower(item.gardenType, item.name) >= GardenManager.instance.getMaxFlowerCapacity(item.gardenType))
                {
                    UI .displayMessage (ZLoc .t ("Dialogs","Garden_InventoryFull"),GenericPopup .TYPE_ACCEPTCANCEL ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericPopup.ACCEPT)
                {
                    m_slotMechanic.emptySlot(targetSlot.index);
                    targetSlot.removeFlower();
                }
                return;
            }//end
            , "GardenInventory_full", true);
                }
                else
                {
                    this.m_slotMechanic.emptySlot(targetSlot.index);
                    targetSlot.removeFlower();
                }
            }
            return;
        }//end

        private void  onGardenSlotRollOver (MouseEvent event )
        {
            if ((event.currentTarget as GardenSlot).isOccupied)
            {
                clearTimeout(this.m_speechBubbleHideTimeout);
                this.m_speechBubbleHideTimeout = setTimeout(this.hideSpeechBubble, SPEECH_BUBBLE_HIDE_TIME);
            }
            return;
        }//end

        private void  onGardenSlotRollOut (MouseEvent event )
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
            this.m_garden.removeEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false);
            return;
        }//end

    }



