package GameMode;

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
import Engine.*;
import Events.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;

    public class GMVisitBuy extends GMPlay
    {
        protected String m_hostId ;
        protected Item m_selectedItem ;

        public  GMVisitBuy (String param1 )
        {
            Global.ui.hideExpandedMainMenu();
            this.m_hostId = param1;
            setVisit(this.m_hostId);
            m_uiMode = UIEvent.CURSOR;
            return;
        }//end

         protected int  getSelectableTypes ()
        {
            return Constants.WORLDOBJECT_ALL;
        }//end

         protected boolean  canBeClicked (MouseEvent event )
        {
            Item _loc_2 =null ;
            Residence _loc_4 =null ;
            Decoration _loc_5 =null ;
            Municipal _loc_6 =null ;
            Business _loc_7 =null ;
            if (m_highlightedObject instanceof Residence)
            {
                _loc_4 =(Residence) m_highlightedObject;
                this.m_selectedItem =(Item) _loc_4.getItem();
            }
            else if (m_highlightedObject instanceof Decoration)
            {
                _loc_5 =(Decoration) m_highlightedObject;
                this.m_selectedItem =(Item) _loc_5.getItem();
            }
            else if (m_highlightedObject instanceof Municipal)
            {
                _loc_6 =(Municipal) m_highlightedObject;
                this.m_selectedItem =(Item) _loc_6.getItem();
            }
            else if (m_highlightedObject instanceof Business)
            {
                _loc_7 =(Business) m_highlightedObject;
                this.m_selectedItem =(Item) _loc_7.getItem();
            }
            _loc_3 = (IMechanicUser)m_highlightedObject(
            return m_highlightedObject != null && _loc_3;
        }//end

         protected void  updateCursor ()
        {
            if (m_currentCursor != null)
            {
                if (m_cursorId > 0)
                {
                    UI.removeCursor(m_cursorId);
                    m_cursorId = 0;
                }
                m_currentCursor = null;
            }
            return;
        }//end

        protected boolean  canInteractWithNeighborObject (MouseEvent event )
        {
            return m_highlightedObject && this.canBeClicked(event) && m_highlightedObject.isAttached() == true;
        }//end

    }



