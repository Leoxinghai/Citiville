package by.blooddy.crypto.image;

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

//import flash.display.*;
//import flash.system.*;
//import flash.utils.*;

    public class JPEGEncoder
    {

        public void  JPEGEncoder ()
        {
            return;
        }//end

        public static ByteArray  encode (BitmapData param1 ,int param2 =60)
        {
            int _loc_7 =0;
            int _loc_9 =0;
            int _loc_16 =0;
            int _loc_17 =0;
            int _loc_18 =0;
            int _loc_19 =0;
            int _loc_20 =0;
            int _loc_21 =0;
            int _loc_22 =0;
            double _loc_23 =0;
            double _loc_24 =0;
            double _loc_25 =0;
            double _loc_26 =0;
            double _loc_27 =0;
            double _loc_28 =0;
            double _loc_29 =0;
            double _loc_30 =0;
            double _loc_31 =0;
            double _loc_32 =0;
            double _loc_33 =0;
            double _loc_34 =0;
            double _loc_35 =0;
            double _loc_36 =0;
            double _loc_37 =0;
            double _loc_38 =0;
            double _loc_39 =0;
            double _loc_40 =0;
            double _loc_41 =0;
            double _loc_42 =0;
            double _loc_43 =0;
            double _loc_44 =0;
            double _loc_45 =0;
            double _loc_46 =0;
            double _loc_47 =0;
            double _loc_48 =0;
            double _loc_49 =0;
            double _loc_50 =0;
            int _loc_51 =0;
            int _loc_52 =0;
            int _loc_53 =0;
            int _loc_54 =0;
            int _loc_55 =0;
            int _loc_56 =0;
            int _loc_57 =0;
            if (param1 == null)
            {
                Error.throwError(TypeError, 2007, "image");
            }
            if (param2 > 100)
            {
                Error.throwError(RangeError, 2006, "quality");
            }
            _loc_3 = ApplicationDomain.currentDomain.domainMemory;
            _loc_4 = param1.width ;
            _loc_5 = param1.height ;
            ByteArray _loc_6 =new ByteArray ();
            _loc_6.position = 1792;
            _loc_6.writeBytes(JPEGTable.getTable(param2));
            _loc_6.length = _loc_6.length + (680 + _loc_4 * _loc_5 * 3);
            if (_loc_6.length < ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH)
            {
                _loc_6.length = ApplicationDomain.MIN_DOMAIN_MEMORY_LENGTH;
            }
            ApplicationDomain.currentDomain.domainMemory = _loc_6;
            _loc_7 = 201611;
            _loc_7 = 201629;
            _loc_6.position = _loc_7 + 36;
            _loc_6.writeMultiByte("by.blooddy.crypto.image.JPEGEncoder", "x-ascii");
            _loc_7 = 201701;
            _loc_6.position = _loc_7 + 4;
            _loc_6.writeBytes(_loc_6, 1792, 130);
            _loc_7 = 201835;
            _loc_8 = param1.width ;
            _loc_9 = param1.height;
            _loc_7 = 201854;
            _loc_6.position = _loc_7 + 4;
            _loc_6.writeBytes(_loc_6, 3010, 416);
            _loc_7 = 202274;
            int _loc_10 =202288;
            int _loc_11 =7;
            int _loc_12 =0;
            int _loc_13 =0;
            int _loc_14 =0;
            int _loc_15 =0;
            _loc_8 = 0;
            do
            {

                _loc_7 = 0;
                do
                {

                    _loc_9 = 0;
                    _loc_16 = _loc_7 + 8;
                    _loc_17 = _loc_8 + 8;
                    do
                    {

                        do
                        {

                            _loc_18 = param1.getPixel(_loc_7, _loc_8);
                            _loc_19 = _loc_18 >>> 16;
                            _loc_20 = _loc_18 >> 8 & 255;
                            _loc_21 = _loc_18 & 255;
                            _loc_9 = _loc_9 + 8;
                        }while (_loc_7++ < _loc_16)
                        _loc_7 = _loc_7++ - 8;
                    }while (_loc_8++ < _loc_17)
                    _loc_8 = _loc_8++ - 8;
                    _loc_9 = 256;
                    _loc_22 = _loc_13;
                    _loc_16 = 3426;
                    _loc_17 = 3462;
                    _loc_18 = 0;
                    do
                    {

                        _loc_23 = _loc_9 + _loc_18;
                        _loc_24 = _loc_9 + _loc_18 + 8;
                        _loc_25 = _loc_9 + _loc_18 + 16;
                        _loc_26 = _loc_9 + _loc_18 + 24;
                        _loc_27 = _loc_9 + _loc_18 + 32;
                        _loc_28 = _loc_9 + _loc_18 + 40;
                        _loc_29 = _loc_9 + _loc_18 + 48;
                        _loc_30 = _loc_9 + _loc_18 + 56;
                        _loc_31 = _loc_23 + _loc_30;
                        _loc_38 = _loc_23 - _loc_30;
                        _loc_32 = _loc_24 + _loc_29;
                        _loc_37 = _loc_24 - _loc_29;
                        _loc_33 = _loc_25 + _loc_28;
                        _loc_36 = _loc_25 - _loc_28;
                        _loc_34 = _loc_26 + _loc_27;
                        _loc_35 = _loc_26 - _loc_27;
                        _loc_39 = _loc_31 + _loc_34;
                        _loc_42 = _loc_31 - _loc_34;
                        _loc_40 = _loc_32 + _loc_33;
                        _loc_41 = _loc_32 - _loc_33;
                        _loc_43 = (_loc_41 + _loc_42) * 0.707107;
                        _loc_39 = _loc_35 + _loc_36;
                        _loc_40 = _loc_36 + _loc_37;
                        _loc_41 = _loc_37 + _loc_38;
                        _loc_47 = (_loc_39 - _loc_41) * 0.382683;
                        _loc_44 = 0.541196 * _loc_39 + _loc_47;
                        _loc_46 = 1.30656 * _loc_41 + _loc_47;
                        _loc_45 = _loc_40 * 0.707107;
                        _loc_48 = _loc_38 + _loc_45;
                        _loc_49 = _loc_38 - _loc_45;
                        _loc_18 = _loc_18 + 64;
                    }while (_loc_18 < 512)
                    _loc_18 = 0;
                    do
                    {

                        _loc_23 = _loc_9 + _loc_18;
                        _loc_24 = _loc_9 + _loc_18 + 64;
                        _loc_25 = _loc_9 + _loc_18 + 128;
                        _loc_26 = _loc_9 + _loc_18 + 192;
                        _loc_27 = _loc_9 + _loc_18 + 256;
                        _loc_28 = _loc_9 + _loc_18 + 320;
                        _loc_29 = _loc_9 + _loc_18 + 384;
                        _loc_30 = _loc_9 + _loc_18 + 448;
                        _loc_31 = _loc_23 + _loc_30;
                        _loc_38 = _loc_23 - _loc_30;
                        _loc_32 = _loc_24 + _loc_29;
                        _loc_37 = _loc_24 - _loc_29;
                        _loc_33 = _loc_25 + _loc_28;
                        _loc_36 = _loc_25 - _loc_28;
                        _loc_34 = _loc_26 + _loc_27;
                        _loc_35 = _loc_26 - _loc_27;
                        _loc_39 = _loc_31 + _loc_34;
                        _loc_42 = _loc_31 - _loc_34;
                        _loc_40 = _loc_32 + _loc_33;
                        _loc_41 = _loc_32 - _loc_33;
                        _loc_43 = (_loc_41 + _loc_42) * 0.707107;
                        _loc_39 = _loc_35 + _loc_36;
                        _loc_40 = _loc_36 + _loc_37;
                        _loc_41 = _loc_37 + _loc_38;
                        _loc_47 = (_loc_39 - _loc_41) * 0.382683;
                        _loc_44 = 0.541196 * _loc_39 + _loc_47;
                        _loc_46 = 1.30656 * _loc_41 + _loc_47;
                        _loc_45 = _loc_40 * 0.707107;
                        _loc_48 = _loc_38 + _loc_45;
                        _loc_49 = _loc_38 - _loc_45;
                        _loc_18 = _loc_18 + 8;
                    }while (_loc_18 < 64)
                    _loc_19 = 0;
                    do
                    {

                        _loc_50 = (_loc_9 + (_loc_19 << 3)) * (1922 + (_loc_19 << 3));
                    }while (_loc_19++ < 64)
                    _loc_51 = 0;
                    _loc_52 = _loc_51 - _loc_22;
                    _loc_22 = _loc_51;
                    if (_loc_52 == 0)
                    {
                        _loc_53 = _loc_16;
                        do
                        {

                            if (((_loc_16 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    else
                    {
                        _loc_18 = (32767 + _loc_52) * 3;
                        _loc_19 = _loc_16 + (5004 + _loc_18) * 3;
                        _loc_53 = _loc_19 + 1;
                        do
                        {

                            if (((_loc_19 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                        _loc_19 = 5004 + _loc_18;
                        _loc_53 = _loc_19;
                        do
                        {

                            if (((_loc_19 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    _loc_19 = 63;
                    do
                    {

                        _loc_19 = _loc_19 - 1;
                        if (_loc_19 > 0)
                        {
                        }
                    }while (_loc_19 << 2 == 0)
                    if (_loc_19 != 0)
                    {
                        _loc_20 = 1;
                        while (_loc_20 <= _loc_19)
                        {

                            _loc_54 = _loc_20;
                            do
                            {

                                _loc_20 = _loc_20 + 1;
                                if (_loc_20 <= _loc_19)
                                {
                                }
                            }while (_loc_20 << 2 == 0)
                            _loc_55 = _loc_20 - _loc_54;
                            if (_loc_55 >= 16)
                            {
                                _loc_53 = _loc_55 >> 4;
                                _loc_56 = 1;
                                while (_loc_56 <= _loc_53)
                                {

                                    _loc_21 = _loc_17 + 720;
                                    _loc_57 = _loc_21;
                                    do
                                    {

                                        if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                        {
                                            _loc_12 = _loc_12 | 1 << _loc_11;
                                        }
                                        _loc_11--;
                                        if (_loc_11 < 0)
                                        {
                                            _loc_11 = 7;
                                            _loc_12 = 0;
                                        }
                                        _loc_57--;
                                    }while (_loc_57 >= 0)
                                    _loc_56++;
                                }
                                _loc_55 = _loc_55 & 15;
                            }
                            _loc_18 = (32767 + (_loc_20 << 2)) * 3;
                            _loc_21 = _loc_17 + (_loc_55 << 4) * 3 + (5004 + _loc_18) * 3;
                            _loc_57 = _loc_21;
                            do
                            {

                                if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                {
                                    _loc_12 = _loc_12 | 1 << _loc_11;
                                }
                                _loc_11--;
                                if (_loc_11 < 0)
                                {
                                    _loc_11 = 7;
                                    _loc_12 = 0;
                                }
                                _loc_57--;
                            }while (_loc_57 >= 0)
                            _loc_21 = 5004 + _loc_18;
                            _loc_57 = _loc_21;
                            do
                            {

                                if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                {
                                    _loc_12 = _loc_12 | 1 << _loc_11;
                                }
                                _loc_11--;
                                if (_loc_11 < 0)
                                {
                                    _loc_11 = 7;
                                    _loc_12 = 0;
                                }
                                _loc_57--;
                            }while (_loc_57 >= 0)
                            _loc_20 = _loc_20 + 1;
                        }
                    }
                    if (_loc_19 != 63)
                    {
                        _loc_53 = _loc_17;
                        do
                        {

                            if (((_loc_17 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    _loc_13 = _loc_22;
                    _loc_9 = 768;
                    _loc_22 = _loc_14;
                    _loc_16 = 4215;
                    _loc_17 = 4251;
                    _loc_18 = 0;
                    do
                    {

                        _loc_23 = _loc_9 + _loc_18;
                        _loc_24 = _loc_9 + _loc_18 + 8;
                        _loc_25 = _loc_9 + _loc_18 + 16;
                        _loc_26 = _loc_9 + _loc_18 + 24;
                        _loc_27 = _loc_9 + _loc_18 + 32;
                        _loc_28 = _loc_9 + _loc_18 + 40;
                        _loc_29 = _loc_9 + _loc_18 + 48;
                        _loc_30 = _loc_9 + _loc_18 + 56;
                        _loc_31 = _loc_23 + _loc_30;
                        _loc_38 = _loc_23 - _loc_30;
                        _loc_32 = _loc_24 + _loc_29;
                        _loc_37 = _loc_24 - _loc_29;
                        _loc_33 = _loc_25 + _loc_28;
                        _loc_36 = _loc_25 - _loc_28;
                        _loc_34 = _loc_26 + _loc_27;
                        _loc_35 = _loc_26 - _loc_27;
                        _loc_39 = _loc_31 + _loc_34;
                        _loc_42 = _loc_31 - _loc_34;
                        _loc_40 = _loc_32 + _loc_33;
                        _loc_41 = _loc_32 - _loc_33;
                        _loc_43 = (_loc_41 + _loc_42) * 0.707107;
                        _loc_39 = _loc_35 + _loc_36;
                        _loc_40 = _loc_36 + _loc_37;
                        _loc_41 = _loc_37 + _loc_38;
                        _loc_47 = (_loc_39 - _loc_41) * 0.382683;
                        _loc_44 = 0.541196 * _loc_39 + _loc_47;
                        _loc_46 = 1.30656 * _loc_41 + _loc_47;
                        _loc_45 = _loc_40 * 0.707107;
                        _loc_48 = _loc_38 + _loc_45;
                        _loc_49 = _loc_38 - _loc_45;
                        _loc_18 = _loc_18 + 64;
                    }while (_loc_18 < 512)
                    _loc_18 = 0;
                    do
                    {

                        _loc_23 = _loc_9 + _loc_18;
                        _loc_24 = _loc_9 + _loc_18 + 64;
                        _loc_25 = _loc_9 + _loc_18 + 128;
                        _loc_26 = _loc_9 + _loc_18 + 192;
                        _loc_27 = _loc_9 + _loc_18 + 256;
                        _loc_28 = _loc_9 + _loc_18 + 320;
                        _loc_29 = _loc_9 + _loc_18 + 384;
                        _loc_30 = _loc_9 + _loc_18 + 448;
                        _loc_31 = _loc_23 + _loc_30;
                        _loc_38 = _loc_23 - _loc_30;
                        _loc_32 = _loc_24 + _loc_29;
                        _loc_37 = _loc_24 - _loc_29;
                        _loc_33 = _loc_25 + _loc_28;
                        _loc_36 = _loc_25 - _loc_28;
                        _loc_34 = _loc_26 + _loc_27;
                        _loc_35 = _loc_26 - _loc_27;
                        _loc_39 = _loc_31 + _loc_34;
                        _loc_42 = _loc_31 - _loc_34;
                        _loc_40 = _loc_32 + _loc_33;
                        _loc_41 = _loc_32 - _loc_33;
                        _loc_43 = (_loc_41 + _loc_42) * 0.707107;
                        _loc_39 = _loc_35 + _loc_36;
                        _loc_40 = _loc_36 + _loc_37;
                        _loc_41 = _loc_37 + _loc_38;
                        _loc_47 = (_loc_39 - _loc_41) * 0.382683;
                        _loc_44 = 0.541196 * _loc_39 + _loc_47;
                        _loc_46 = 1.30656 * _loc_41 + _loc_47;
                        _loc_45 = _loc_40 * 0.707107;
                        _loc_48 = _loc_38 + _loc_45;
                        _loc_49 = _loc_38 - _loc_45;
                        _loc_18 = _loc_18 + 8;
                    }while (_loc_18 < 64)
                    _loc_19 = 0;
                    do
                    {

                        _loc_50 = (_loc_9 + (_loc_19 << 3)) * (2434 + (_loc_19 << 3));
                    }while (_loc_19++ < 64)
                    _loc_51 = 0;
                    _loc_52 = _loc_51 - _loc_22;
                    _loc_22 = _loc_51;
                    if (_loc_52 == 0)
                    {
                        _loc_53 = _loc_16;
                        do
                        {

                            if (((_loc_16 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    else
                    {
                        _loc_18 = (32767 + _loc_52) * 3;
                        _loc_19 = _loc_16 + (5004 + _loc_18) * 3;
                        _loc_53 = _loc_19 + 1;
                        do
                        {

                            if (((_loc_19 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                        _loc_19 = 5004 + _loc_18;
                        _loc_53 = _loc_19;
                        do
                        {

                            if (((_loc_19 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    _loc_19 = 63;
                    do
                    {

                        _loc_19 = _loc_19 - 1;
                        if (_loc_19 > 0)
                        {
                        }
                    }while (_loc_19 << 2 == 0)
                    if (_loc_19 != 0)
                    {
                        _loc_20 = 1;
                        while (_loc_20 <= _loc_19)
                        {

                            _loc_54 = _loc_20;
                            do
                            {

                                _loc_20 = _loc_20 + 1;
                                if (_loc_20 <= _loc_19)
                                {
                                }
                            }while (_loc_20 << 2 == 0)
                            _loc_55 = _loc_20 - _loc_54;
                            if (_loc_55 >= 16)
                            {
                                _loc_53 = _loc_55 >> 4;
                                _loc_56 = 1;
                                while (_loc_56 <= _loc_53)
                                {

                                    _loc_21 = _loc_17 + 720;
                                    _loc_57 = _loc_21;
                                    do
                                    {

                                        if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                        {
                                            _loc_12 = _loc_12 | 1 << _loc_11;
                                        }
                                        _loc_11--;
                                        if (_loc_11 < 0)
                                        {
                                            _loc_11 = 7;
                                            _loc_12 = 0;
                                        }
                                        _loc_57--;
                                    }while (_loc_57 >= 0)
                                    _loc_56++;
                                }
                                _loc_55 = _loc_55 & 15;
                            }
                            _loc_18 = (32767 + (_loc_20 << 2)) * 3;
                            _loc_21 = _loc_17 + (_loc_55 << 4) * 3 + (5004 + _loc_18) * 3;
                            _loc_57 = _loc_21;
                            do
                            {

                                if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                {
                                    _loc_12 = _loc_12 | 1 << _loc_11;
                                }
                                _loc_11--;
                                if (_loc_11 < 0)
                                {
                                    _loc_11 = 7;
                                    _loc_12 = 0;
                                }
                                _loc_57--;
                            }while (_loc_57 >= 0)
                            _loc_21 = 5004 + _loc_18;
                            _loc_57 = _loc_21;
                            do
                            {

                                if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                {
                                    _loc_12 = _loc_12 | 1 << _loc_11;
                                }
                                _loc_11--;
                                if (_loc_11 < 0)
                                {
                                    _loc_11 = 7;
                                    _loc_12 = 0;
                                }
                                _loc_57--;
                            }while (_loc_57 >= 0)
                            _loc_20 = _loc_20 + 1;
                        }
                    }
                    if (_loc_19 != 63)
                    {
                        _loc_53 = _loc_17;
                        do
                        {

                            if (((_loc_17 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    _loc_14 = _loc_22;
                    _loc_9 = 1280;
                    _loc_22 = _loc_15;
                    _loc_16 = 4215;
                    _loc_17 = 4251;
                    _loc_18 = 0;
                    do
                    {

                        _loc_23 = _loc_9 + _loc_18;
                        _loc_24 = _loc_9 + _loc_18 + 8;
                        _loc_25 = _loc_9 + _loc_18 + 16;
                        _loc_26 = _loc_9 + _loc_18 + 24;
                        _loc_27 = _loc_9 + _loc_18 + 32;
                        _loc_28 = _loc_9 + _loc_18 + 40;
                        _loc_29 = _loc_9 + _loc_18 + 48;
                        _loc_30 = _loc_9 + _loc_18 + 56;
                        _loc_31 = _loc_23 + _loc_30;
                        _loc_38 = _loc_23 - _loc_30;
                        _loc_32 = _loc_24 + _loc_29;
                        _loc_37 = _loc_24 - _loc_29;
                        _loc_33 = _loc_25 + _loc_28;
                        _loc_36 = _loc_25 - _loc_28;
                        _loc_34 = _loc_26 + _loc_27;
                        _loc_35 = _loc_26 - _loc_27;
                        _loc_39 = _loc_31 + _loc_34;
                        _loc_42 = _loc_31 - _loc_34;
                        _loc_40 = _loc_32 + _loc_33;
                        _loc_41 = _loc_32 - _loc_33;
                        _loc_43 = (_loc_41 + _loc_42) * 0.707107;
                        _loc_39 = _loc_35 + _loc_36;
                        _loc_40 = _loc_36 + _loc_37;
                        _loc_41 = _loc_37 + _loc_38;
                        _loc_47 = (_loc_39 - _loc_41) * 0.382683;
                        _loc_44 = 0.541196 * _loc_39 + _loc_47;
                        _loc_46 = 1.30656 * _loc_41 + _loc_47;
                        _loc_45 = _loc_40 * 0.707107;
                        _loc_48 = _loc_38 + _loc_45;
                        _loc_49 = _loc_38 - _loc_45;
                        _loc_18 = _loc_18 + 64;
                    }while (_loc_18 < 512)
                    _loc_18 = 0;
                    do
                    {

                        _loc_23 = _loc_9 + _loc_18;
                        _loc_24 = _loc_9 + _loc_18 + 64;
                        _loc_25 = _loc_9 + _loc_18 + 128;
                        _loc_26 = _loc_9 + _loc_18 + 192;
                        _loc_27 = _loc_9 + _loc_18 + 256;
                        _loc_28 = _loc_9 + _loc_18 + 320;
                        _loc_29 = _loc_9 + _loc_18 + 384;
                        _loc_30 = _loc_9 + _loc_18 + 448;
                        _loc_31 = _loc_23 + _loc_30;
                        _loc_38 = _loc_23 - _loc_30;
                        _loc_32 = _loc_24 + _loc_29;
                        _loc_37 = _loc_24 - _loc_29;
                        _loc_33 = _loc_25 + _loc_28;
                        _loc_36 = _loc_25 - _loc_28;
                        _loc_34 = _loc_26 + _loc_27;
                        _loc_35 = _loc_26 - _loc_27;
                        _loc_39 = _loc_31 + _loc_34;
                        _loc_42 = _loc_31 - _loc_34;
                        _loc_40 = _loc_32 + _loc_33;
                        _loc_41 = _loc_32 - _loc_33;
                        _loc_43 = (_loc_41 + _loc_42) * 0.707107;
                        _loc_39 = _loc_35 + _loc_36;
                        _loc_40 = _loc_36 + _loc_37;
                        _loc_41 = _loc_37 + _loc_38;
                        _loc_47 = (_loc_39 - _loc_41) * 0.382683;
                        _loc_44 = 0.541196 * _loc_39 + _loc_47;
                        _loc_46 = 1.30656 * _loc_41 + _loc_47;
                        _loc_45 = _loc_40 * 0.707107;
                        _loc_48 = _loc_38 + _loc_45;
                        _loc_49 = _loc_38 - _loc_45;
                        _loc_18 = _loc_18 + 8;
                    }while (_loc_18 < 64)
                    _loc_19 = 0;
                    do
                    {

                        _loc_50 = (_loc_9 + (_loc_19 << 3)) * (2434 + (_loc_19 << 3));
                    }while (_loc_19++ < 64)
                    _loc_51 = 0;
                    _loc_52 = _loc_51 - _loc_22;
                    _loc_22 = _loc_51;
                    if (_loc_52 == 0)
                    {
                        _loc_53 = _loc_16;
                        do
                        {

                            if (((_loc_16 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    else
                    {
                        _loc_18 = (32767 + _loc_52) * 3;
                        _loc_19 = _loc_16 + (5004 + _loc_18) * 3;
                        _loc_53 = _loc_19 + 1;
                        do
                        {

                            if (((_loc_19 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                        _loc_19 = 5004 + _loc_18;
                        _loc_53 = _loc_19;
                        do
                        {

                            if (((_loc_19 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    _loc_19 = 63;
                    do
                    {

                        _loc_19 = _loc_19 - 1;
                        if (_loc_19 > 0)
                        {
                        }
                    }while (_loc_19 << 2 == 0)
                    if (_loc_19 != 0)
                    {
                        _loc_20 = 1;
                        while (_loc_20 <= _loc_19)
                        {

                            _loc_54 = _loc_20;
                            do
                            {

                                _loc_20 = _loc_20 + 1;
                                if (_loc_20 <= _loc_19)
                                {
                                }
                            }while (_loc_20 << 2 == 0)
                            _loc_55 = _loc_20 - _loc_54;
                            if (_loc_55 >= 16)
                            {
                                _loc_53 = _loc_55 >> 4;
                                _loc_56 = 1;
                                while (_loc_56 <= _loc_53)
                                {

                                    _loc_21 = _loc_17 + 720;
                                    _loc_57 = _loc_21;
                                    do
                                    {

                                        if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                        {
                                            _loc_12 = _loc_12 | 1 << _loc_11;
                                        }
                                        _loc_11--;
                                        if (_loc_11 < 0)
                                        {
                                            _loc_11 = 7;
                                            _loc_12 = 0;
                                        }
                                        _loc_57--;
                                    }while (_loc_57 >= 0)
                                    _loc_56++;
                                }
                                _loc_55 = _loc_55 & 15;
                            }
                            _loc_18 = (32767 + (_loc_20 << 2)) * 3;
                            _loc_21 = _loc_17 + (_loc_55 << 4) * 3 + (5004 + _loc_18) * 3;
                            _loc_57 = _loc_21;
                            do
                            {

                                if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                {
                                    _loc_12 = _loc_12 | 1 << _loc_11;
                                }
                                _loc_11--;
                                if (_loc_11 < 0)
                                {
                                    _loc_11 = 7;
                                    _loc_12 = 0;
                                }
                                _loc_57--;
                            }while (_loc_57 >= 0)
                            _loc_21 = 5004 + _loc_18;
                            _loc_57 = _loc_21;
                            do
                            {

                                if (((_loc_21 + 1) & 1 << _loc_57) != 0)
                                {
                                    _loc_12 = _loc_12 | 1 << _loc_11;
                                }
                                _loc_11--;
                                if (_loc_11 < 0)
                                {
                                    _loc_11 = 7;
                                    _loc_12 = 0;
                                }
                                _loc_57--;
                            }while (_loc_57 >= 0)
                            _loc_20 = _loc_20 + 1;
                        }
                    }
                    if (_loc_19 != 63)
                    {
                        _loc_53 = _loc_17;
                        do
                        {

                            if (((_loc_17 + 1) & 1 << _loc_53) != 0)
                            {
                                _loc_12 = _loc_12 | 1 << _loc_11;
                            }
                            _loc_11--;
                            if (_loc_11 < 0)
                            {
                                _loc_11 = 7;
                                _loc_12 = 0;
                            }
                            _loc_53--;
                        }while (_loc_53 >= 0)
                    }
                    _loc_15 = _loc_22;
                    _loc_7 = _loc_7 + 8;
                }while (_loc_7 < _loc_4)
                _loc_8 = _loc_8 + 8;
            }while (_loc_8 < _loc_5)
            if (_loc_11 >= 0)
            {
                _loc_22 = _loc_11 + 1;
                do
                {

                    if (((1 << (_loc_11 + 1)) - 1 & 1 << _loc_22) != 0)
                    {
                        _loc_12 = _loc_12 | 1 << _loc_11;
                    }
                    _loc_11--;
                    if (_loc_11 < 0)
                    {
                        _loc_11 = 7;
                        _loc_12 = 0;
                    }
                    _loc_22--;
                }while (_loc_22 >= 0)
            }
            ApplicationDomain.currentDomain.domainMemory = _loc_3;
            ByteArray _loc_58 =new ByteArray ();
            _loc_58.writeBytes(_loc_6, 201609, _loc_10 - 201609 + 2);
            _loc_58.position = 0;
            return _loc_58;
        }//end

    }



