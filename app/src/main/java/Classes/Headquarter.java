package Classes;

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

import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Display.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Modules.franchise.display.*;
import Transactions.*;

//import flash.display.*;
//import flash.geom.*;

    public class Headquarter extends MapResource
    {
        protected  String TYPE_NAME ="headquarter";
        protected  String STATE_BASE ="base";
        protected  int SCAFFOLD_BASE_COUNT =4;
        protected  int MIN_BUILT_FLOOR_COUNT =1;
        protected  String IMAGE_NAME_FLOOR ="floor";
        protected  String IMAGE_NAME_ROOFTOP ="rooftop";
        protected  String IMAGE_NAME_PENTHOUSE ="penthouse";
        protected  String IMAGE_NAME_BILLBOARD ="billboard";
        protected Array m_contentImageNames ;
        protected boolean m_isContentAssetsLoaded =false ;
        protected Object m_itemImageHash ;
        protected Object m_bitmapDataHash ;
        protected BitmapData m_originalBitmapData ;
        protected BitmapData m_canvasBitmapData ;
        protected String m_itemName ;
        protected double m_heightLimit ;
        protected double m_builtFloorCount =0;
        protected ScaffoldEffect m_scaffoldEffect ;
        protected boolean m_isBuildingNewFloor =false ;

        public  Headquarter (String param1 )
        {
            this.m_contentImageNames = .get(this.IMAGE_NAME_FLOOR, this.IMAGE_NAME_ROOFTOP, this.IMAGE_NAME_PENTHOUSE, this.IMAGE_NAME_BILLBOARD);
            this.m_itemImageHash = {};
            this.m_bitmapDataHash = {};
            super(param1);
            this.m_itemName = param1;
            m_objectType = WorldObjectTypes.HEADQUARTER;
            m_typeName = this.TYPE_NAME;
            this.m_heightLimit = this.getHeightLimit();
            this.m_builtFloorCount = this.MIN_BUILT_FLOOR_COUNT;
            setState(this.STATE_BASE);
            return;
        }//end

        public double  heightLimit ()
        {
            return this.m_heightLimit;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_builtFloorCount = param1.builtFloorCount || 0;
            this.m_builtFloorCount = Math.min(this.m_builtFloorCount, this.m_heightLimit);
            if (this.m_builtFloorCount < this.MIN_BUILT_FLOOR_COUNT)
            {
                this.m_builtFloorCount = this.MIN_BUILT_FLOOR_COUNT;
            }
            return;
        }//end

         public boolean  isSellable ()
        {
            return true;
        }//end

         public String  getItemFriendlyName ()
        {
            String _loc_1 ="";
            if (m_item)
            {
                _loc_1 = Global.franchiseManager.getHeadquartersFriendlyName(m_item.name);
            }
            return _loc_1;
        }//end

         public void  drawDisplayObject ()
        {
            super.drawDisplayObject();
            if (m_content && !this.m_isContentAssetsLoaded)
            {
                this.m_originalBitmapData = this.contentBitmap.bitmapData.clone();
                this.loadContentAssets();
            }
            else if (this.m_canvasBitmapData)
            {
                this.setContentBitmap();
                this.updateScaffold();
            }
            return;
        }//end

         public void  cleanUp ()
        {
            ItemImage _loc_1 =null ;
            super.cleanUp();
            this.cleanUpScaffold();
            for(int i0 = 0; i0 < this.m_itemImageHash.size(); i0++)
            {
            		_loc_1 = this.m_itemImageHash.get(i0);

                _loc_1.removeEventListener(LoaderEvent.LOADED, this.itemImageLoadedHandler);
            }
            this.m_itemImageHash = null;
            this.m_bitmapDataHash = null;
            this.m_contentImageNames = null;
            this.m_originalBitmapData = null;
            return;
        }//end

        protected void  cleanUpScaffold ()
        {
            if (this.m_scaffoldEffect)
            {
                this.m_scaffoldEffect.cleanUp();
                this.m_scaffoldEffect = null;
            }
            return;
        }//end

        private String  getHeadquartersName ()
        {
            _loc_1 = Global.franchiseManager.getFranchiseTypeFromHeadquarters(this.m_itemName);
            _loc_2 = Global.franchiseManager.worldOwnerModel.getFranchiseName(_loc_1);
            _loc_3 = ZLoc.t("Items","headquarters",{item_loc_2});
            return _loc_3;
        }//end

         public String  getToolTipHeader ()
        {
            _loc_1 = this.getHeadquartersName ();
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            String _loc_1 ="";
            if (this.m_isBuildingNewFloor)
            {
                _loc_1 = "";
            }
            else if (this.isScaffoldNeeded && !Global.isVisiting())
            {
                _loc_1 = ZLoc.t("Main", "BuildNewFloor");
            }
            else
            {
                _loc_1 = super.getToolTipAction();
            }
            return _loc_1;
        }//end

         public void  onPlayAction ()
        {
            String _loc_1 =null ;
            FranchiseMenu _loc_2 =null ;
            super.onPlayAction();
            if (Global.isVisiting() || actionBar && actionBar.visible)
            {
                return;
            }
            if (!this.isScaffoldNeeded)
            {
                _loc_1 = Global.franchiseManager.getFranchiseTypeFromHeadquarters(this.m_itemName);
                _loc_2 = UI.displayNewFranchise();
                _loc_2.switchBusiness(_loc_1);
            }
            else
            {
                this.buildNewFloor();
            }
            return;
        }//end

         public void  updateAlpha ()
        {
            if (m_actionBar && m_actionBar.visible || this.m_isBuildingNewFloor)
            {
                this.alpha = 1;
            }
            else
            {
                super.updateAlpha();
            }
            return;
        }//end

        public void  drawContent ()
        {
            BitmapData _loc_26 =null ;
            Rectangle _loc_27 =null ;
            _loc_1 = this.m_originalBitmapData.clone ();
            _loc_2 = _loc_1.height ;
            _loc_3 = _loc_1.width ;
            _loc_4 = this.getImageBitmapData(this.IMAGE_NAME_FLOOR );
            _loc_5 = this.getContentItemImage(this.IMAGE_NAME_FLOOR );
            _loc_6 = _loc_4.height ;
            _loc_7 = _loc_4.width ;
            _loc_8 = _loc_5.offsetX;
            _loc_9 = _loc_5.offsetY;
            _loc_10 = _loc_5.forcedHeight;
            _loc_11 = this.isPenthouse ? (this.IMAGE_NAME_PENTHOUSE) : (this.IMAGE_NAME_ROOFTOP);
            _loc_12 = this.getImageBitmapData(_loc_11 );
            _loc_13 = this.getContentItemImage(_loc_11 );
            _loc_14 = _loc_12.height;
            _loc_15 = _loc_12.width;
            _loc_16 = _loc_13.offsetX;
            _loc_17 = _loc_13.offsetY;
            _loc_18 = this.getImageBitmapData(this.IMAGE_NAME_BILLBOARD );
            _loc_19 = this.getContentItemImage(this.IMAGE_NAME_BILLBOARD );
            _loc_20 = _loc_18.height;
            _loc_21 = _loc_18.width;
            _loc_22 = _loc_19.offsetX;
            _loc_23 = _loc_19.offsetY;
            _loc_24 = _loc_2-_loc_9+_loc_10*(this.m_builtFloorCount-1)-_loc_17;
            _loc_25 = Math.max(_loc_3,_loc_7,_loc_15);
            this.m_canvasBitmapData = new BitmapData(_loc_25, _loc_24, true, 0);
            Point _loc_28 =new Point ();
            double _loc_29 =0;
            _loc_30 = _loc_24-_loc_2;
            _loc_26 = _loc_1;
            _loc_27 = _loc_1.rect;
            _loc_28.x = _loc_29;
            _loc_28.y = _loc_30;
            this.m_canvasBitmapData.copyPixels(_loc_26, _loc_27, _loc_28, null, null, true);
            _loc_31 = _loc_29+_loc_8;
            _loc_32 = _loc_30+_loc_9;
            _loc_26 = _loc_4;
            _loc_27 = _loc_4.rect;
            _loc_28.x = _loc_31;
            _loc_28.y = _loc_32;
            _loc_33 = this.m_builtFloorCount;
            while (_loc_33--)
            {

                this.m_canvasBitmapData.copyPixels(_loc_26, _loc_27, _loc_28, null, null, true);
                _loc_28.y = _loc_28.y - _loc_10;
            }
            _loc_34 = _loc_31+_loc_16;
            _loc_35 = _loc_28.y+_loc_10+_loc_17;
            _loc_26 = _loc_12;
            _loc_27 = _loc_12.rect;
            _loc_28.x = _loc_34;
            _loc_28.y = _loc_35;
            this.m_canvasBitmapData.copyPixels(_loc_26, _loc_27, _loc_28, null, null, true);
            _loc_36 = _loc_34+_loc_22;
            _loc_37 = _loc_35+_loc_23;
            _loc_26 = _loc_18;
            _loc_27 = _loc_18.rect;
            _loc_28.x = _loc_36;
            _loc_28.y = _loc_37;
            this.m_canvasBitmapData.copyPixels(_loc_26, _loc_27, _loc_28, null, null, true);
            this.setContentBitmap();
            return;
        }//end

        private void  setContentBitmap ()
        {
            _loc_3 = this.m_canvasBitmapData.clone ();
            m_maskBitmap.bitmapData = this.m_canvasBitmapData.clone();
            this.contentBitmap.bitmapData = _loc_3;
            _loc_1 = displayObjectOffsetY(-)/displayObject.scaleY;
            _loc_2 = _loc_1-this.m_canvasBitmapData.height ;
            this.contentBitmap.y = _loc_2;
            return;
        }//end

        private BitmapData  getImageBitmapData (String param1 )
        {
            return this.m_bitmapDataHash.get(param1);
        }//end

        private double  getHeightLimit ()
        {
            return parseFloat(getItem().xml.heightLimit);
        }//end

        private ItemImage  getContentItemImage (String param1 )
        {
            return this.m_itemImageHash.get(param1);
        }//end

        private Bitmap  contentBitmap ()
        {
            Vector _loc_3.<DisplayObject >=null ;
            DisplayObject _loc_4 =null ;
            _loc_1 = (Bitmap)m_content
            _loc_2 = (DisplayObjectContainer)m_content
            if (_loc_1 == null && _loc_2)
            {
                _loc_3 = DisplayObjectContainerUtil.getChildren(_loc_2);
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    if (_loc_4 instanceof Bitmap)
                    {
                        _loc_1 =(Bitmap) _loc_4;
                        break;
                    }
                }
            }
            return _loc_1;
        }//end

        public int  floorCount ()
        {
            _loc_1 =Global.franchiseManager.getHeadquartersHeight(this.m_itemName ,m_itemOwner || Global.world.ownerId);
            return _loc_1;
        }//end

        private boolean  isPenthouse ()
        {
            _loc_1 = this.floorCount >=this.heightLimit ;
            return _loc_1;
        }//end

        private void  loadContentAssets ()
        {
            String imageName ;
            XML imageXml ;
            ItemImage itemImage ;
            rawImageXml = m_item.rawImageXml;
            int _loc_2 =0;
            _loc_3 = this.m_contentImageNames ;
            for(int i0 = 0; i0 < this.m_contentImageNames.size(); i0++)
            {
            		imageName = this.m_contentImageNames.get(i0);


                int _loc_5 =0;
                _loc_6 = rawImageXml;
                XMLList _loc_4 =new XMLList("");
                Object _loc_7;
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_7 = _loc_6.get(i0);


                    with (_loc_7)
                    {
                        if (@name == imageName)
                        {
                            _loc_4.put(_loc_5++,  _loc_7);
                        }
                    }
                }
                imageXml = _loc_4.get(0);
                itemImage = new ItemImage(imageXml);
                this.m_itemImageHash.put(imageName,  itemImage);
                itemImage.addEventListener(LoaderEvent.LOADED, this.itemImageLoadedHandler, false, 0, true);
                itemImage.load();
            }
            return;
        }//end

        private void  updateScaffold ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            if (this.isScaffoldNeeded)
            {
                _loc_1 = this.SCAFFOLD_BASE_COUNT + this.m_builtFloorCount;
                _loc_2 = _loc_1;
                _loc_3 = _loc_1 + 1;
                if (this.m_isBuildingNewFloor)
                {
                    _loc_2 = this.SCAFFOLD_BASE_COUNT + this.floorCount + 1;
                    _loc_3 = _loc_2;
                    this.cleanUpScaffold();
                }
                if (this.m_scaffoldEffect)
                {
                    this.m_scaffoldEffect.startStage = _loc_1;
                    this.m_scaffoldEffect.endStage = _loc_2;
                    this.m_scaffoldEffect.maxStage = _loc_3;
                    this.m_scaffoldEffect.reattach();
                }
                else
                {
                    this.m_scaffoldEffect = new ScaffoldEffect(this, _loc_1, _loc_2, _loc_3);
                }
            }
            else
            {
                this.cleanUpScaffold();
            }
            return;
        }//end

        private boolean  isScaffoldNeeded ()
        {
            _loc_1 = boolean(this.m_builtFloorCount<this.floorCount&& this.m_builtFloorCount <= this.m_heightLimit);
            return _loc_1;
        }//end

        private void  buildNewFloor ()
        {
            MapResource _mapResource ;
            Object workerData ;
            this.m_isBuildingNewFloor = true;
            this.updateScaffold();
            this.m_builtFloorCount = this.floorCount;
            Array workerDataArray ;
            int i ;
            do
            {

                workerData;
                workerData.pos = new Vector3(0, 0);
                workerData.isTempWorker = false;
                workerDataArray.put(workerDataArray.length,  workerData);
                i = (i - 1);
            }while (i)
            Global.world.citySim.npcManager.addConstructionWorkers(this, workerDataArray);
            _mapResource;
            m_actionQueue.removeAllStates();
            m_actionQueue .addActions (new ActionProgressBar (null ,this ,ZLoc .t ("Main","Building"),5),new ActionFn (this ,void  ()
            {
                drawContent();
                m_isBuildingNewFloor = false;
                updateScaffold();
                Global.world.citySim.npcManager.removeAllConstructionWorkers(_mapResource);
                return;
            }//end
            ),new ActionPause (this ,1),new ActionFn (this ,void  ()
            {
                Global.franchiseManager.headquarterFloorBuildCompleted(m_itemName);
                return;
            }//end
            ));
            TBuildFloorHeadquarter tBuildFloorHeadquarter =new TBuildFloorHeadquarter(this );
            GameTransactionManager.addTransaction(tBuildFloorHeadquarter);
            return;
        }//end

        private void  itemImageLoadedHandler (LoaderEvent event )
        {
            ItemImage _loc_2 =null ;
            ItemImageInstance _loc_3 =null ;
            int _loc_4 =0;
            BitmapData _loc_5 =null ;
            if (!this.m_isContentAssetsLoaded)
            {
                _loc_2 =(ItemImage) event.target;
                _loc_3 = _loc_2.getInstance();
                if (!_loc_3)
                {
                    return;
                }
                _loc_2.removeEventListener(LoaderEvent.LOADED, this.itemImageLoadedHandler);
                if (!this.m_bitmapDataHash.get(_loc_2.name))
                {
                    this.m_bitmapDataHash.put(_loc_2.name,  ((Bitmap)_loc_3.image).bitmapData);
                    _loc_4 = 0;
                    for(int i0 = 0; i0 < this.m_bitmapDataHash.size(); i0++)
                    {
                    		_loc_5 = this.m_bitmapDataHash.get(i0);

                        _loc_4 = _loc_4 + 1;
                    }
                    this.m_isContentAssetsLoaded = Boolean(_loc_4 >= this.m_contentImageNames.length());
                    if (this.m_isContentAssetsLoaded)
                    {
                        this.drawContent();
                        this.updateScaffold();
                    }
                }
            }
            return;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            return;
        }//end

        public int  builtFloorCount ()
        {
            return this.m_builtFloorCount;
        }//end

    }



