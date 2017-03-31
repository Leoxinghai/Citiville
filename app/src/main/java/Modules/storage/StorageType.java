package Modules.storage;

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
//import flash.utils.*;
import validation.*;

    public class StorageType
    {
        private String m_type ;
        public static  StorageType ITEM_TYPE =new StorageType("itemType");
        public static  StorageType STORAGE_ITEM_TYPE =new StorageType("storageItemType");
        private static Dictionary map =new Dictionary ();
        private static Dictionary m_dict ;

        public  StorageType (String param1 )
        {
            if (m_dict == null)
            {
                m_dict = new Dictionary();
            }
            this.m_type = param1;
            m_dict.put(param1,  this);
            return;
        }//end

        public String  type ()
        {
            return this.m_type;
        }//end

        public static boolean  validateItemType (ItemInstance param1 ,String param2 )
        {
            return param1.getItem().type == param2;
        }//end

        public static boolean  validateStorageItemType (ItemInstance param1 ,String param2 )
        {
            _loc_3 =Global.gameSettings().getItemByName(param2 ).getValidation("storage");
            return _loc_3 != null && _loc_3.validate(param1);
        }//end

        public static Function  getValidateFunction (StorageType param1 )
        {
            return map.get(param1);
        }//end

        public static StorageType  createEnum (String param1 )
        {
            return m_dict.get(param1) as StorageType;
        }//end

    }



