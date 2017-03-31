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

import Classes.*;
import Modules.remodel.*;

    public class RemodelCatalogGenerationMechanic extends CatalogGenerationMechanic
    {

        public  RemodelCatalogGenerationMechanic ()
        {
            return;
        }//end  

         public boolean  hasOverrideForGameAction (String param1 )
        {
            _loc_2 = (ItemInstance)m_owner(
            return super.hasOverrideForGameAction(param1) && !_loc_2;
        }//end  

         public boolean  canShowCatalog ()
        {
            return RemodelManager.isRemodelPossible(m_owner);
        }//end  

         protected String  getInferredCatalogType ()
        {
            String _loc_1 ="skins";
            if (m_owner)
            {
                _loc_1 = m_owner.getItemName() + "_skins";
            }
            return _loc_1;
        }//end  

         public String  getToolTipAction ()
        {
            String _loc_1 ="";
            if (RemodelManager.isRemodelPossible(m_owner))
            {
                _loc_1 = ZLoc.t("Main", "ClickToRemodel");
            }
            return _loc_1;
        }//end  

    }



