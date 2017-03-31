package Display.MatchmakingUI;

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
import Classes.gates.*;
import Classes.util.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.layout.*;
import Display.aswingui.inline.style.*;
import Display.aswingui.inline.util.*;
import Modules.matchmaking.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class BuildingRequestDialogCell implements GridListCell
    {
        private boolean m_selected ;
        private IASwingPanel m_swingPanel ;
        private IASwingStyle m_defaultStyle ;
        private IASwingStyle m_selectedStyle ;
        private String m_imageUrl ;
        private String m_itemName ;
        private String m_requestType ;
        private int m_requestsLeft ;
        private int m_requestsTotal ;
        private String m_requestItem ;
        private static  int CELL_WIDTH =192;
        private static  int CELL_HEIGHT =172;
        private static  Object css ={panel fontSize {12textAlign ,.CENTER },textField1 {.DARK_BLUE ,fontFamily.DEFAULT_FONT_BOLD },textField2 {.ORANGE ,fontFamily.TITLE_FONT ,textTransform.UPPERCASE }};

        public  BuildingRequestDialogCell (Object param1)
        {
            this.loadCss(param1);
            this.updateStyle();
            return;
        }//end

        public boolean  selected ()
        {
            return this.m_selected;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            if (param2 != this.m_selected)
            {
                this.m_selected = param2;
                this.updateStyle();
            }
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            AbstractGate _loc_3 =null ;
            _loc_2 = param1;
            if (_loc_2)
            {
                _loc_3 =(AbstractGate) _loc_2.getGate(ConstructionSite.BUILD_GATE);
                if (_loc_3 instanceof CompositeGate)
                {
                    _loc_3 =(AbstractGate) CompositeGate(_loc_3).getGate(_loc_2, "buildables");
                }
                this.m_imageUrl = _loc_2.targetItem.icon;
                this.m_itemName = _loc_2.targetItem.name;
                this.m_requestType = _loc_3 instanceof CrewGate ? (MatchmakingManager.REQUEST_CREW) : (MatchmakingManager.REQUEST_ITEM);
                this.m_requestsLeft = _loc_3.amountNeeded;
                this.m_requestsTotal = _loc_3.totalAmount;
                this.m_requestItem = this.getFirstRequestableItemName(_loc_3);
            }
            this.updateStyle();
            return;
        }//end

        private void  updateStyle ()
        {
            IASwingStyle _loc_1 =null ;
            if (this.m_swingPanel)
            {
                _loc_1 = this.selected ? (this.m_selectedStyle) : (this.m_defaultStyle);
                _loc_1.apply(this.m_swingPanel.get("itemImg").component);
            }
            return;
        }//end

        public Object getCellValue ()
        {
            return {requestType:this.m_requestType, requestItem:this.m_requestItem, targetItem:Global.gameSettings().getItemByName(this.m_itemName)};
        }//end

        public Component  getCellComponent ()
        {
            if (!this.m_swingPanel)
            {
                this.m_swingPanel = this.buildCellComponent();
                this.updateStyle();
            }
            return this.m_swingPanel.component;
        }//end

        private void  loadCss (Object param1 )
        {
            _loc_2 = ASwingStyleFactory.getInstance();
            _loc_3 = param1.get(DelayedAssetLoader.MATCHMAKING_ASSETS) ;
            _loc_4 = _loc_3.hasOwnProperty("buildingCard_up")? (_loc_3.buildingCard_up) : (MovieClip);
            css.imagePanel = {backgroundImage:_loc_4, backgroundWidth:107, backgroundHeight:104};
            this.m_defaultStyle = _loc_2.parse(css.imagePanel);
            _loc_5 = _loc_3.hasOwnProperty("buildingCard_up_selected")? (_loc_3.buildingCard_up_selected) : (MovieClip);
            css.imageSelected = {backgroundImage:_loc_5, backgroundWidth:107, backgroundHeight:104};
            this.m_selectedStyle = _loc_2.parse(css.imageSelected);
            return;
        }//end

        private IASwingPanel  buildCellComponent ()
        {
            IASwingPanel _loc_7 =null ;
            IASwingPanel _loc_8 =null ;
            IASwingNode _loc_9 =null ;
            IASwingNode _loc_10 =null ;
            IASwingNode _loc_11 =null ;
            _loc_1 = ASwingFactory.getInstance();
            _loc_2 = _loc_1.layout.vertical.align(SoftBoxLayout.TOP );
            _loc_3 = _loc_1.layout.horizontal.align(SoftBoxLayout.CENTER );
            _loc_4 = _loc_1.panel(css.panel ).layout(_loc_2 ).size(CELL_WIDTH ,CELL_HEIGHT );
            _loc_5 = _loc_1.panel("itemImg",css.imagePanel ).layout(_loc_1.layout.center );
            _loc_6 = _loc_1.panel().layout(_loc_3).add(_loc_5);
            if (this.m_imageUrl)
            {
                _loc_5.add(_loc_1.image().source(this.m_imageUrl));
            }
            if (this.m_requestItem)
            {
                _loc_9 = _loc_1.text(this.m_itemName, css.textField1).strings("Items").textKey(this.m_itemName + "_friendlyName");
                _loc_10 = _loc_1.text("BuildingRequestDialogCell", css.textField2).replacements({amount:this.m_requestsLeft, total:this.m_requestsTotal, type:ZLoc.tk("Dialogs", this.m_requestType, "", this.m_requestsTotal)});
                _loc_7 = _loc_1.panel().layout(_loc_3).add(_loc_9);
                _loc_8 = _loc_1.panel().layout(_loc_3).add(_loc_10);
            }
            else
            {
                _loc_11 = _loc_1.text("BuildingRequestDialogCell_empty", css.textField1);
                _loc_7 = _loc_1.panel().layout(_loc_3).add(_loc_11);
            }
            return _loc_4.add(_loc_6).add(_loc_7).add(_loc_8);
        }//end

        private String  getFirstRequestableItemName (AbstractGate param1 )
        {
            String _loc_2 =null ;
            if (param1 instanceof CrewGate)
            {
                return "bonus_crew";
            }
            for(int i0 = 0; i0 < param1.getKeyArray().size(); i0++)
            {
            	_loc_2 = param1.getKeyArray().get(i0);

                if (param1.keyProgress(_loc_2) < param1.getKey(_loc_2))
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

    }



