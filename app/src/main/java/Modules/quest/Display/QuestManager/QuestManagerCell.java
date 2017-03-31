package Modules.quest.Display.QuestManager;

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
import Display.aswingui.*;
import Modules.quest.Managers.*;

//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class QuestManagerCell extends JPanel implements GridListCell
    {
        protected GridList m_gridList ;
        protected int m_index ;
        protected GameSprite m_questIcon ;
        protected JPanel m_panel ;
        protected QuestHideButton m_hideButton ;
        public static  int CELL_WIDTH =74;
        public static  int CELL_HEIGHT =74;

        public  QuestManagerCell (LayoutManager param1)
        {
            super(param1);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            if (param1 !=null)
            {
                this.m_gridList = param1;
                this.m_index = param3;
                param1.setTileWidth(this.getPreferredWidth());
                param1.setTileHeight(this.getPreferredHeight());
            }
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_questIcon = param1;
            this.initializeCell();
            this.setGridListCellStatus(this.m_gridList, false, this.m_index);
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_questIcon;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        protected void  initializeCell ()
        {
            AssetPane _loc_1 =new AssetPane(this.m_questIcon );
            this.m_panel = new JPanel(new BorderLayout());
            this.m_panel.append(_loc_1, BorderLayout.CENTER);
            ASwingHelper.prepare(this.m_panel);
            this.append(this.m_panel);
            this.addEventListener(MouseEvent.ROLL_OVER, this.onRollOver, false, 0, true);
            this.addEventListener(MouseEvent.ROLL_OUT, this.onRollOut, false, 0, true);
            if (Global.questManager.getQuestByName(this.m_questIcon.name).shouldShowHideIcon())
            {
                this.m_hideButton = new QuestHideButton((this.m_questIcon as QuestManagerSprite).onQuestHideClick);
                this.m_hideButton.x = 54;
                this.m_hideButton.y = 1;
                this.m_hideButton.scaleX = 0.85;
                this.m_hideButton.scaleY = 0.85;
                this.addChild(this.m_hideButton);
            }
            ASwingHelper.prepare(this);
            this.setPreferredWidth(CELL_WIDTH);
            this.setPreferredHeight(CELL_HEIGHT);
            return;
        }//end

        protected boolean  buildToolTip ()
        {
            Quest _loc_2 =null ;
            GameQuest _loc_3 =null ;
            _loc_1 =Global.questManager.getActiveQuests ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2 instanceof GameQuest && _loc_2.name == this.m_questIcon.name)
                {
                    _loc_3 =(GameQuest) _loc_2;
                    if (_loc_3.tasks.length == 1 && !Global.questManager.isTaskActionVisible(_loc_3.tasks.get(0).action))
                    {
                        return false;
                    }
                    UI.questManagerView.content.questToolTip.initializeToolTip(ZLoc.t("Quest", this.m_questIcon.name + "_dialog_title"), _loc_3.getProgressStrings(), _loc_3.getCurrentRewardsData(), _loc_2.isActivated());
                }
            }
            return true;
        }//end

        protected void  makeBackground ()
        {
            DisplayObject _loc_1 =null ;
            if (Global.questManager.getQuestByName(this.m_questIcon.name).hideable && !Global.questManager.getQuestByName(this.m_questIcon.name).getHidden())
            {
                _loc_1 =(DisplayObject) new QuestManagerView.assetDict.get("questManagerHideableCellBG");
            }
            else
            {
                _loc_1 =(DisplayObject) new QuestManagerView.assetDict.get("questManagerCellBG");
            }
            MarginBackground _loc_2 =new MarginBackground(_loc_1 );
            this.setBackgroundDecorator(_loc_2);
            return;
        }//end

        protected void  removeBackground ()
        {
            this.setBackgroundDecorator(null);
            return;
        }//end

        protected void  onRollOver (MouseEvent event )
        {
            if (this.buildToolTip())
            {
                UI.questManagerView.content.questToolTip.visible = true;
            }
            this.makeBackground();
            return;
        }//end

        protected void  onRollOut (MouseEvent event )
        {
            UI.questManagerView.content.questToolTip.visible = false;
            this.removeBackground();
            return;
        }//end

    }



