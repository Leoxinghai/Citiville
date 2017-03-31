package Mechanics.ClientDisplayMechanics;

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
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import root.Global;
//import flash.geom.*;

    public class ExpansionMarkerDisplayMechanic implements IClientGameMechanic, IEdgeModifier
    {
        private Prop m_owner ;
        private boolean m_wasHighlighted =true ;

        public  ExpansionMarkerDisplayMechanic ()
        {
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            if (this.m_owner == null || this.m_owner.content == null)
            {
                return;
            }
            _loc_2 = this.m_owner.isHighlighted ();
            if (!this.m_wasHighlighted && _loc_2)
            {
                this.toggleDisplay(true);
            }
            if (this.m_wasHighlighted && !_loc_2)
            {
                this.toggleDisplay(false);
            }
            this.m_wasHighlighted = _loc_2;
            return;
        }//end

        private void  toggleDisplay (boolean param1 )
        {
            if (param1 !=null)
            {
                this.m_owner.alpha = 1;
            }
            else
            {
                this.m_owner.alpha = 0.5;
            }
            return;
        }//end

        public void  detachDisplayObject ()
        {
            this.toggleDisplay(false);
            this.m_owner = null;
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(Prop) param1;
            if (this.m_owner == null)
            {
                ErrorManager.addError("Attempted to add an expansion marker mechanic to a non-prop object " + param1);
                return;
            }
            if (param2.params.layerName != null && this.m_owner != null)
            {
                this.m_owner.overrideLayerName(String(param2.params.layerName));
            }
            this.m_owner.isSelectionOutsideCityAllowed = true;
            this.m_owner.setEnableHighlight(false);
            return;
        }//end

        public boolean  isPixelInside (Point param1 )
        {
            Point _loc_2 =new Point(Global.stage.mouseX ,Global.stage.mouseY );
            Vector2 _loc_3 = IsoMath.screenPosToTilePos(_loc_2.x ,_loc_2.y, false);
            Vector3 _loc_4 = this.m_owner.getPositionNoClone();
            Vector3 _loc_5 = this.m_owner.getSizeNoClone();
            return _loc_3.x >= _loc_4.x && _loc_3.y >= _loc_4.y && _loc_3.x < _loc_4.x + _loc_5.x && _loc_3.y < _loc_4.y + _loc_5.y;
        }//end

        public int  getFloatOffset ()
        {
            return 0;
        }//end

    }




