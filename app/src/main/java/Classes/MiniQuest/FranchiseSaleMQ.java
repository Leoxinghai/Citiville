package Classes.MiniQuest;

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

import Display.*;
import Modules.franchise.data.*;
import Modules.franchise.display.*;

//import flash.events.*;

    public class FranchiseSaleMQ extends MiniQuest
    {
        private Array m_needSaleBusinesses ;
        public static  String QUEST_NAME ="franchiseSaleMQ";

        public  FranchiseSaleMQ ()
        {
            super(QUEST_NAME);
            this.m_needSaleBusinesses = new Array();
            m_recurrenceTime = 5;
            return;
        }//end

         protected void  onIconClicked (MouseEvent event )
        {
            super.onIconClicked(event);
            this.showFranchiseSale();
            m_recurrenceTime = 0;
            return;
        }//end

         protected void  endQuest ()
        {
            super.endQuest();
            this.m_needSaleBusinesses = null;
            return;
        }//end

        public void  checkFranchiseSales ()
        {
            OwnedFranchiseData _loc_2 =null ;
            String _loc_3 =null ;
            if (this.m_needSaleBusinesses.length())
            {
                this.m_needSaleBusinesses = new Array();
            }
            _loc_1 = Global.franchiseManager.model.getOwnedFranchises();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2.locations)
                {
                    for(int i0 = 0; i0 < _loc_2.locations.size(); i0++)
                    {
                    		_loc_3 = _loc_2.locations.get(i0);

                        if (_loc_2.locations.get(_loc_3).commodityLeft == 0 && GlobalEngine.getTimer() / 1000 >= _loc_2.locations.get(_loc_3).timeLastSupplied + FranchiseMenu.dailyCycleDelta)
                        {
                            this.m_needSaleBusinesses.push(_loc_2);
                        }
                    }
                }
            }
            return;
        }//end

        public int  needSaleCount ()
        {
            return this.m_needSaleBusinesses.length;
        }//end

        public void  showFranchiseSale ()
        {
            UI.displayNewFranchise(false);
            return;
        }//end

    }



