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
import Classes.util.*;
import Display.DialogUI.*;
import Display.InventoryUI.*;
import Display.aswingui.*;
import Display.aswingui.gridlistui.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.style.*;
import Display.aswingui.inline.util.*;
import Events.*;
import Modules.matchmaking.*;
import org.aswing.*;
import org.aswing.event.*;

    public class BuildingRequestDialog extends FlyFishDialog
    {
        private String m_requestType ;
        private String m_requestItemName ;
        private Item m_requestTargetItem ;
        private EventHandler m_selectionHandler ;
        private ScrollingList m_list ;
        public Array data ;

        public  BuildingRequestDialog (Array param1 ,String param2)
        {
            super("assets/flyfish/AskBuildingBuddiesDialog.xml");
            this.data = param1;
            this.m_statsTrackingName = param2;

            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return super.getAssetDependencies().concat(DelayedAssetLoader.MATCHMAKING_ASSETS);
        }//end

         protected void  performDialogActions ()
        {
            IASwingList _loc_12 =null ;
            ScrollingList _loc_13 =null ;
            _loc_1 = m_assetDependencies;
            _loc_2 = InventoryView.assetDict;
            _loc_3 = styles.parse({upSkin_loc_2.btn_prev_up,overSkin.btn_prev_over,downSkin.btn_prev_down});
            _loc_4 = styles.parse({upSkin_loc_2.btn_next_up,overSkin.btn_next_over,downSkin.btn_next_down});
            _loc_5 = pane.getComponent("contentPanel");
            _loc_6 = pane.getComponent("titleText");
            pane.getComponent("titleText").setText(ZLoc.t("Dialogs", "BuildingRequestDialog_title"));
            ASwingHelper.setProperFont(_loc_6, ASwingFont.TITLE_FONT);
            _loc_7 = pane.getComponent("hintText");
            pane.getComponent("hintText").setText(ZLoc.t("Dialogs", "BuildingRequestDialog_hint"));
            ASwingHelper.setProperFont(_loc_7, ASwingFont.DEFAULT_FONT_BOLD);
            _loc_8 = pane.getComponent("closeButton");
            pane.getComponent("closeButton").addActionListener(this.onCloseButtonClick, 0, true);
            _loc_9 = pane.getComponent("actionButton");
            pane.getComponent("actionButton").setText(ZLoc.t("Dialogs", "BuildingRequestDialog_actionButton"));
            _loc_9.addActionListener(this.onActionButtonClick, 0, true);
            ASwingHelper.setProperFont(_loc_9, ASwingFont.TITLE_FONT);
            _loc_10 = pane.getComponent("leftScrollButton");
            _loc_3.apply(_loc_10);
            _loc_11 = pane.getComponent("rightScrollButton");
            _loc_4.apply(_loc_11);
            if (_loc_5 && this.data)
            {
                this.makePrettyList(this.data);
                _loc_12 = swing.horizontalList().columns(3).rows(2).selectable(true);
                _loc_12.size(_loc_5.getPreferredWidth(), _loc_5.getPreferredHeight());
                _loc_12.dataModel(this.data).cellFactory(new GenericAssetCellFactory(BuildingRequestDialogCell, m_assetDependencies));
                _loc_13 =(ScrollingList) _loc_12.component;
                _loc_13.prevPageButton = _loc_10;
                _loc_13.nextPageButton = _loc_11;
                this.m_selectionHandler.source = _loc_13;
                this.m_list = _loc_13;
                _loc_5.append(_loc_13);
                _loc_13.selectedIndex = 0;
            }
            return;
        }//end

        private void  makePrettyList (Array param1 )
        {
            _loc_2 = param1.length ;
            Array _loc_3 =new Array();
            _loc_4 = _loc_3.length ;
            while (_loc_2 < 6)
            {

                param1.push(null);
                _loc_2++;
            }
            if (_loc_2 % 2 == 1)
            {
                param1.push(null);
            }
            return;
        }//end

        private void  onCloseButtonClick (Object param1)
        {
            this.close();
            return;
        }//end

        private void  onActionButtonClick (Object param1)
        {
            if (this.m_list && this.m_list.selectedIndex >= 0)
            {
                if (this.m_requestItemName == "bonus_crew")
                {
                    MatchmakingManager.instance.askForCrew(true, null, "ask_for_parts", this.m_requestTargetItem);
                }
                else if (this.m_requestItemName != null)
                {
                    MatchmakingManager.instance.askForBuildable(this.m_requestItemName, "municipal_", null, "ask_for_parts");
                }
                this.close();
            }
            return;
        }//end

        private void  list_selectionHandler (SelectionEvent event )
        {
            _loc_3 = null;
            _loc_2 =(ScrollingList) event.target;
            if (_loc_2)
            {
                _loc_3 = _loc_2.selectedCell.getCellValue();
                this.m_requestType = "requestType" in _loc_3 ? (_loc_3.requestType) : (null);
                if (this.m_requestType)
                {
                    this.m_requestItemName = "requestItem" in _loc_3 ? (_loc_3.requestItem) : (null);
                    this.m_requestTargetItem = "targetItem" in _loc_3 ? (_loc_3.targetItem) : (null);
                }
            }
            return;
        }//end

    }



