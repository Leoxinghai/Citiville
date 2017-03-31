package Mechanics.GameEventMechanics;

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

import Classes.inventory.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class InventoryMechanic extends BaseInventory implements IActionGameMechanic
    {
        protected IMechanicUser m_owner ;
        protected MechanicConfigData m_config ;

        public  InventoryMechanic ()
        {
            super(null);
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return this.get(param1) instanceof Function;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =new MechanicActionResult(false ,true );
            if (this.hasOverrideForGameAction(param1))
            {
                _loc_3 = (this.get(param1) as Function).apply(this, param2);
            }
            return _loc_3;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner = param1;
            this.m_config = param2;
            Object _loc_3 ={storage this.m_owner.getDataForMechanic(this.m_config.type )};
            this.loadObject(_loc_3);
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return this.m_config.params.get("visitEnabled") != "false";
        }//end

         public boolean  isValidName (String param1 )
        {
            return true;
        }//end

         public int  getCapacity (String param1 )
        {
            if (this.isValidName(param1) && this.m_config.params.get("capacity"))
            {
                return this.m_config.params.get("capacity");
            }
            return 0;
        }//end

         protected void  onDataChange (String param1 )
        {
            this.writeMechanicData();
            return;
        }//end

        protected void  writeMechanicData ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            Object _loc_1 ={};
            if (m_storage)
            {
                for(int i0 = 0; i0 < m_storage.size(); i0++)
                {
                		_loc_2 = m_storage.get(i0);

                    _loc_3 = parseInt(m_storage.get(_loc_2));
                    _loc_1.put(_loc_2,  _loc_3);
                }
            }
            this.m_owner.setDataForMechanic(this.m_config.type, _loc_1, "all");
            return;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

    }



