package org.aswing.flyfish;

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

import org.aswing.*;
import org.aswing.flyfish.awml.*;
import org.aswing.flyfish.css.*;

    public class FlyFish extends Object
    {
        public static  String CSS_PATHS ="css_paths";
        private static TextRuntimeLoader textLoader ;

        public  FlyFish ()
        {
            return;
        }//end

        public static void  initialise (IRunspace param1 )
        {
            runspace = param1;
            Definition.getIns();
            ResourceManager .ins .setWorkspace (new WorkspaceWrap (runspace ),void  ()
            {
                return;
            }//end
            );
            textLoader = new TextRuntimeLoader();
            return;
        }//end

        public static void  loadAWML (String param1 ,Function param2 ,Array param3 =null )
        {
            path = param1;
            callback = param2;
            cssStrs = param3;
            textLoader .loadFiles (.get(path) ,void  (Array param1 )
            {
                XML _loc_2 =null ;
                FlyFishPane _loc_3 =null ;
                if (param1 !=null)
                {
                    _loc_2 = new XML(param1.get(0));
                    _loc_3 = new FlyFishPane(_loc_2);
                    parseAndApplyCSS(cssStrs, _loc_3.getRoot());
                    callback(_loc_3);
                }
                else
                {
                    callback(null);
                }
                return;
            }//end
            );
            return;
        }//end

        public static void  loadAndApplyCSS (Component param1 ,Function param2 ,Array param3 =null )
        {
            Array paths ;
            int i ;
            String path ;
            pane = param1;
            callback = param2;
            fixedPaths = param3;
            if (fixedPaths == null)
            {
                fixedPaths = pane.getClientProperty(CSS_PATHS, []);
                loadAndApplyCSS(pane, callback, fixedPaths);
            }
            else
            {
                paths = new Array();
                i = 0;
                while (i < fixedPaths.length())
                {

                    path = fixedPaths.get(i);
                    paths.push(ResourceManager.ins.getSrcUrl(path));
                    i = (i + 1);
                }
                textLoader .loadFiles (paths ,void  (Array param1 )
            {
                LazyLoadRequestList _loc_2 =null ;
                if (param1 !=null)
                {
                    _loc_2 = parseAndApplyCSS(param1, pane);
                    if (callback != null)
                    {
                        callback(_loc_2);
                    }
                }
                else if (callback != null)
                {
                    callback(null);
                }
                return;
            }//end
            );
            }
            return;
        }//end

        public static LazyLoadRequestList  parseAndApplyCSS (Array param1 ,Component param2 )
        {
            String _loc_4 =null ;
            _loc_3 = new StyleSheetList ();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_4 = param1.get(i0);

                _loc_3.combine(StyleSheetParser.ins.parse(_loc_4));
            }
            return applyCSS(param2, _loc_3);
        }//end

        public static LazyLoadRequestList  applyCSS (Component param1 ,StyleSheetList param2 )
        {
            Container _loc_4 =null ;
            Array _loc_5 =null ;
            _loc_3 = param2.apply(param1 );
            _loc_3.combine(applyInlineCSS(param1));
            if (param1 instanceof Container)
            {
                _loc_4 =(Container) param1;
                _loc_5 = _loc_4.getChildren();
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		param1 = _loc_5.get(i0);

                    _loc_3.combine(applyCSS(param1, param2));
                }
            }
            return _loc_3;
        }//end

        public static LazyLoadRequestList  applyInlineCSS (Component param1 )
        {
            return StyleSheetParser.ins.applyInlineCSS(param1);
        }//end

    }

import flash.display.*;
import flash.system.*;
import org.aswing.flyfish.*;
import org.aswing.*;
import org.aswing.flyfish.awml.*;
import org.aswing.flyfish.css.*;

class WorkspaceWrap extends Object implements IWorkspace
    private IRunspace rspace ;

     WorkspaceWrap (IRunspace param1 )
    {
        this.rspace = param1;
        return;
    }//end

    public ApplicationDomain  getApplicationDomain ()
    {
        return this.rspace.getApplicationDomain();
    }//end

    public DisplayObject  getAsset (String param1 )
    {
        return this.rspace.getAsset(param1);
    }//end

    public Component  getCustomComponent (String param1 )
    {
        return this.rspace.getCustomComponent(param1);
    }//end

    public Class  getClass (String param1 )
    {
        return this.rspace.getClass(param1);
    }//end

    public String  getImageUrl (String param1 )
    {
        return this.rspace.getImageUrl(param1);
    }//end

    public String  getSrcUrl (String param1 )
    {
        return this.rspace.getSrcUrl(param1);
    }//end

    public void  reloadLibs (Function param1 )
    {
        return;
    }//end

    public String  getRootPath ()
    {
        return null;
    }//end

    public boolean  isCreated ()
    {
        return false;
    }//end

    public String  getTipMessage ()
    {
        return null;
    }//end

    public boolean  create ()
    {
        return false;
    }//end

    public IWorkspaceFiles  files ()
    {
        return null;
    }//end

    public WorkspaceModel  model ()
    {
        return null;
    }//end

    public void  saveModel ()
    {
        return;
    }//end

    public ILazyLoadManager  lazyLoadManager ()
    {
        return this.rspace.lazyLoadManager();
    }//end

}



