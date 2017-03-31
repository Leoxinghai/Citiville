package Classes.LogicComponents;

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
import Classes.util.*;
import ZLocalization.*;

    public class MunicipalComponentUpgradableTicket extends MunicipalComponentUpgradable
    {
        private Array arr ;

        public  MunicipalComponentUpgradableTicket (Municipal param1 )
        {
            XML _loc_4 =null ;
            this.arr = new Array();
            super(param1);
            _loc_2 = Global.gameSettings().getItemByName(param1.getItemName());
            _loc_3 = _loc_2.xml;
            if (_loc_3.ticketCurrencies)
            {
                for(int i0 = 0; i0 < _loc_3.ticketCurrencies.children().size(); i0++)
                {
                	_loc_4 = _loc_3.ticketCurrencies.children().get(i0);

                    this.arr.push(_loc_4.toString());
                }
            }
            return;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            LocalizationObjectToken _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            _loc_1 = Global.gameSettings().getItemByName(m_municipal.getItemName());
            if (!Global.isVisiting() && m_municipal.getState() == HarvestableResource.STATE_PLANTED)
            {
                _loc_2 = ZLoc.t("Main", "BareResidence", {time:GameUtil.formatMinutesSeconds(m_municipal.getGrowTimeLeft())});
                for(int i0 = 0; i0 < this.arr.size(); i0++)
                {
                	_loc_3 = this.arr.get(i0);

                    _loc_4 = ZLoc.tk("Tickets", _loc_3, "", Global.ticketManager.getCount(_loc_3));
                    _loc_5 = {};
                    _loc_5.put("number",  Global.ticketManager.getCount(_loc_3));
                    _loc_5.put(_loc_3,  _loc_4);
                    _loc_6 = ZLoc.t("Main", "TicketCollection" + _loc_3, _loc_5);
                    _loc_2 = _loc_2 + ("\n" + _loc_6);
                }
                return _loc_2;
            }
            return "";
        }//end

    }



