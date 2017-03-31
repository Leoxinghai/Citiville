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
import Classes.effects.*;
import Display.*;
import Engine.Helpers.*;
import Events.*;
import Modules.remodel.*;
//import flash.events.*;
import org.aswing.*;

    public class GMRemodel extends GMEdit implements IToolTipTarget, ICustomToolTipTarget
    {
        protected Object m_toolTipComponents ;

        public  GMRemodel ()
        {
            this.m_toolTipComponents = new Object();
            Global.ui.hideExpandedMainMenu();
            m_cursorImage = EmbeddedArt.hud_act_remodel;
            this.m_uiMode = UIEvent.REMODEL;
            return;
        }//end

         protected Object  getCursor ()
        {
            return m_cursorImage;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            Global.stagePickManager.hideAllPicks();
            Global.stagePickManager.showPicksByType(StagePickEffect.PICK_REMODEL);
            Global.stagePickManager.attachStagePicksBatch(StagePickEffect.PICK_REMODEL, RemodelManager.isRemodelPossible);
            return;
        }//end

         public void  disableMode ()
        {
            deselectObject();
            dehighlightObject();
            Global.stagePickManager.detachStagePicksBatch(RemodelManager.isRemodelPossible);
            Global.stagePickManager.showAllPicks();
            Global.ui.hideToolTip(this);
            super.disableMode();
            return;
        }//end

         public boolean  onMouseDown (MouseEvent event )
        {
            boolean _loc_2 =false ;
            if (!UI.isMarketOpen())
            {
                _loc_2 = super.onMouseDown(event);
            }
            return _loc_2;
        }//end

         public boolean  allowHighlight (MapResource param1 )
        {
            return RemodelManager.isRemodelPossible(param1);
        }//end

         public int  getCustomMarketEvent (Item param1 )
        {
            if (param1.isRemodelSkin() || param1.getRemodelDefinition())
            {
                return MarketEvent.MECHANIC;
            }
            return super.getCustomMarketEvent(param1);
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            super.onMouseMove(event);
            if (m_highlightedObject)
            {
                Global.ui.showToolTip(this);
            }
            else
            {
                Global.ui.hideToolTip(this);
            }
            return true;
        }//end

        public String  getToolTipStatus ()
        {
            return "";
        }//end

        public String  getToolTipHeader ()
        {
            String _loc_1 ="";
            if (m_highlightedObject)
            {
                _loc_1 = m_highlightedObject.getToolTipHeader();
            }
            return _loc_1;
        }//end

        public String  getToolTipNotes ()
        {
            return "";
        }//end

        public String  getToolTipAction ()
        {
            Item _loc_1 =null ;
            if (m_highlightedObject && m_highlightedObject instanceof ItemInstance)
            {
                _loc_1 = ((ItemInstance)m_highlightedObject).getItem();
                if (_loc_1 && _loc_1.getAllRemodelDefinitions().length > 0)
                {
                    return ZLoc.t("Main", "ClickToRemodel");
                }
            }
            return "";
        }//end

        public boolean  toolTipFollowsMouse ()
        {
            return false;
        }//end

        public Vector3  getToolTipPosition ()
        {
            Vector3 _loc_1 =null ;
            if (m_highlightedObject)
            {
                _loc_1 = m_highlightedObject.getPosition();
            }
            return _loc_1;
        }//end

        public boolean  getToolTipVisibility ()
        {
            return true;
        }//end

        public Vector2  getToolTipScreenOffset ()
        {
            Vector2 _loc_1 =null ;
            if (m_highlightedObject)
            {
                _loc_1 = m_highlightedObject.getToolTipScreenOffset();
            }
            return _loc_1;
        }//end

        public Vector3  getDimensions ()
        {
            Vector3 _loc_1 =null ;
            if (m_highlightedObject)
            {
                _loc_1 = m_highlightedObject.getDimensions();
            }
            return _loc_1;
        }//end

        public int  getToolTipFloatOffset ()
        {
            int _loc_1 =0;
            if (m_highlightedObject)
            {
                _loc_1 = m_highlightedObject.getToolTipFloatOffset();
            }
            return _loc_1;
        }//end

        public ToolTipSchema  getToolTipSchema ()
        {
            ToolTipSchema _loc_1 =null ;
            if (m_highlightedObject)
            {
                _loc_1 = m_highlightedObject.getToolTipSchema();
            }
            return _loc_1;
        }//end

        public Array  getToolTipComponentList ()
        {
            return .get(this.getCustomToolTipAction());
        }//end

        public String  getCustomToolTipTitle ()
        {
            return this.getToolTipHeader();
        }//end

        public Component  getCustomToolTipImage ()
        {
            return null;
        }//end

        public Component  getCustomToolTipAction ()
        {
            return ToolTipDialog.buildToolTipComponent(ToolTipDialog.ACTION, this.m_toolTipComponents, this.getToolTipAction() ? (TextFieldUtil.formatSmallCapsString(this.getToolTipAction())) : (null), ToolTipSchema.getSchemaForObject(this));
        }//end

        public void  setSelectedObject (GameObject param1 )
        {
            selectObject(param1);
            return;
        }//end

         public void  removeMode ()
        {
            UI.closeCatalog();
            return;
        }//end

    }



