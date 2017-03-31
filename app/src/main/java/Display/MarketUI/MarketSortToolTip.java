package Display.MarketUI;

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

import Display.aswingui.*;
import Events.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;

    public class MarketSortToolTip extends JPanel
    {
        protected JPanel m_panel ;
        private static  Object SORT_ORDERS ={alphabet compareItems {.byProperty("localizedName",ItemSorting.caseInsensitive(ItemSorting.ascending )),labelKey "alphabet"},population {.byProperty("populationBase",ItemSorting.descending ),labelKey "population"},{.byMarketPrice(ItemSorting.ascending ),labelKey "priceLow"},{.byMarketPrice(ItemSorting.descending ),labelKey "priceHigh"},{.byProperty("growTime",ItemSorting.ascending ),labelKey "fastCollection"},{.byProperty("growTime",ItemSorting.descending ),labelKey "slowCollection"},{.byMinCoinPayout(ItemSorting.descending ),labelKey "coinPayout"},{.byProperty("commodityReq",ItemSorting.ascending ),labelKey "lowSupply"},{.byProperty("bonusPercent",ItemSorting.descending ),labelKey "bonusPayout"},{.byMinGoodsPayout(ItemSorting.descending ),labelKey "supply"},{.byGoodsHarvestTime(ItemSorting.ascending ),labelKey "harvestTime"},{.byProperty("growTime",ItemSorting.ascending ),labelKey "travelTime"},{.byProperty("populationCapYield",ItemSorting.descending ),labelKey "populationIncrease"},{.byPremiumMarketPrice(ItemSorting.ascending ),labelKey "premium"}};
        public static  int MIN_WIDTH =127;

        public  MarketSortToolTip (String param1 )
        {
            String _loc_3 =null ;
            Object _loc_4 =null ;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            ASwingHelper.setBackground(this, new Catalog.assetDict.get("market2_sortDropDownPress"));
            this.m_panel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS);
            ASwingHelper.setEasyBorder(this.m_panel, 5, 5, 5, 5);
            this.addSortButton(null, "default");
            _loc_2 = LargeCatalogModel.instance.getCategorySortIds(param1);
            if (_loc_2.length == 1 && _loc_2.get(0) == "")
            {
                _loc_2 = .get("priceLow", "priceHigh");
            }
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                _loc_4 = SORT_ORDERS.get(_loc_3);
                if (_loc_4 != null)
                {
                    this.addSortButton(_loc_4.compareItems, _loc_4.labelKey);
                    continue;
                }
            }
            this.append(this.m_panel);
            ASwingHelper.prepare(this);
            if (this.width < MIN_WIDTH)
            {
                this.setPreferredWidth(MIN_WIDTH);
                ASwingHelper.prepare(this);
            }
            return;
        }//end

        protected void  addSortButton (Function param1 ,String param2 )
        {
            CustomDataButton _loc_3 =new CustomDataButton(ZLoc.t("Market",param2 ),null ,"MarketSortButtonUI",{compareItems param1 });
            _loc_3.addActionListener(this.sort, 0, true);
            this.m_panel.append(_loc_3);
            return;
        }//end

        protected void  sort (AWEvent event )
        {
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MARKET_SORT, event.target.data, true));
            return;
        }//end

    }



