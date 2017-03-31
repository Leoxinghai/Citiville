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

//import flash.utils.*;
    public class InventoryChecksConfig
    {
        protected XML m_rawXMLConfig ;

        public  InventoryChecksConfig (XML param1 )
        {
            this.m_rawXMLConfig = param1;
            return;
        }//end

        public String  getCallback (String param1 )
        {
            XML inventoryCheck ;
            a_type = param1;
            String result ;
            if (this.m_rawXMLConfig)
            {
                if (this.m_rawXMLConfig.inventoryChecks != undefined && this.m_rawXMLConfig.inventoryChecks.inventoryCheck != undefined)
                {
                    int _loc_4 =0;
                    _loc_5 = this.m_rawXMLConfig.inventoryChecks.inventoryCheck;
                    XMLList _loc_3 =new XMLList("");
                    Object _loc_6;
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(i0);


                        with (_loc_6)
                        {
                            if (@type == a_type)
                            {
                                _loc_3.put(_loc_4++,  _loc_6);
                            }
                        }
                    }
                    inventoryCheck = _loc_3.get(0);
                    result = String(inventoryCheck.@callback);
                }
            }
            return result;
        }//end

        public Dictionary  getParams (String param1 )
        {
            XMLList inventoryCheck ;
            XML paramsXml ;
            XML attr ;
            a_type = param1;
            Dictionary params =new Dictionary ();
            if (this.m_rawXMLConfig)
            {
                if (this.m_rawXMLConfig.inventoryChecks != undefined && this.m_rawXMLConfig.inventoryChecks.inventoryCheck != undefined)
                {
                    int _loc_4 =0;
                    _loc_5 = this.m_rawXMLConfig.inventoryChecks.inventoryCheck;
                    XMLList _loc_3 =new XMLList("");
                    Object _loc_6;
                    for(int i0 = 0; i0 < _loc_5.size(); i0++)
                    {
                    		_loc_6 = _loc_5.get(i0);


                        with (_loc_6)
                        {
                            if (@type == a_type)
                            {
                                _loc_3.put(_loc_4++,  _loc_6);
                            }
                        }
                    }
                    inventoryCheck = _loc_3;
                    paramsXml = inventoryCheck.get(0).params.get(0);
                    int _loc_31 =0;
                    _loc_41 = paramsXml.attributes();
                    for(int i0 = 0; i0 < paramsXml.attributes().size(); i0++)
                    {
                    		attr = paramsXml.attributes().get(i0);


                        params.put(String(attr.name()),  String(attr));
                    }
                }
            }
            return params;
        }//end

        public XML  rawXMLConfig ()
        {
            return this.m_rawXMLConfig;
        }//end

    }



