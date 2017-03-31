package ;

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


//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;

    public class ZProfiler
    {
        private static  int CONNECTION_STATE_ON =32;
        private static  int CONNECTION_STATE_OFF =33;
        private static  int CONNECTION_STATE_CONNECTING =34;
        private static  int CONNECTION_STATE_ERR =35;
        private static  int PROFILER_STATE_INACTIVE =-1;
        private static  int PROFILER_STATE_DEFAULT =0;
        private static  int PROFILER_STATE_PROFILING =1;
        private static  int PROFILER_STATE_NOTPROFILING =2;
        private static int profilerState =-1;
        private static int connectionState =33;
        private static  String PREFIX ="Profiler:";
        private static String _host ="localhost";
        private static int _port =42426;
        private static Socket _socket ;
        private static boolean _profilerIsIntegrated =false ;
        private static Vector<String> _stackName =new Vector<String>();
        private static Vector<int> _stackTime =new Vector<int>();
        private static Vector<Number> _stackMem =new Vector<Number>();
        private static Dictionary _xhprof =new Dictionary ();
        private static int _dumptime =0;
        private static double memoryError =0;
        private static double totalMemory =0;

        public  ZProfiler ()
        {
            return;
        }//end

        public static void  initSocket ()
        {
            if (connectionState == CONNECTION_STATE_OFF)
            {
                Security.allowDomain("*");
                trace(PREFIX, "Opening port ", _port);
                _socket = new Socket();
                _socket.addEventListener(SecurityErrorEvent.SECURITY_ERROR, fail);
                _socket.addEventListener(IOErrorEvent.IO_ERROR, fail);
                _socket.addEventListener(Event.CLOSE, close);
                _socket.addEventListener(Event.CONNECT, connect);
                try
                {
                    _socket.connect(_host, _port);
                    connectionState = CONNECTION_STATE_CONNECTING;
                }
                catch (e:Error)
                {
                    trace(PREFIX, "Unable to connect", e);
                    connectionState = CONNECTION_STATE_ERR;
                }
            }
            return;
        }//end

        private static double  getMemory ()
        {
            _loc_1 = System.totalMemory;
            _loc_2 =(double)(_loc_1 )+memoryError ;
            if (_loc_2 < totalMemory)
            {
                memoryError = memoryError + (totalMemory - _loc_2);
                _loc_2 = totalMemory;
            }
            totalMemory = _loc_2;
            return totalMemory;
        }//end

        public static void  enterFunction (String param1 )
        {
            if (profilerState == PROFILER_STATE_NOTPROFILING)
            {
                return;
            }
            if (profilerState == PROFILER_STATE_INACTIVE)
            {
                profilerState = PROFILER_STATE_DEFAULT;
            }
            _stackName.push(param1);
            _stackTime.push(getTimer());
            _stackMem.push(getMemory());
            return;
        }//end

        public static void  exitFunction (String param1 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Func _loc_7 =null ;
            Func _loc_8 =null ;
            if (profilerState == PROFILER_STATE_NOTPROFILING)
            {
                return;
            }
            if (profilerState == PROFILER_STATE_INACTIVE)
            {
                profilerState = PROFILER_STATE_DEFAULT;
            }
            _loc_2 = getTimer();
            _loc_3 = getMemory();
            while (_stackName.length > 0 && _loc_5 != param1)
            {

                _loc_5 = _stackName.pop();
                if (_stackName.length == 0)
                {
                    _loc_4 = "main()";
                }
                else
                {
                    _loc_4 = _stackName.get((_stackName.length - 1));
                }
                _loc_6 = _loc_4 + "==>" + _loc_5;
                if (_xhprof.get(_loc_6) == null)
                {
                    _loc_8 = new Func();
                    _xhprof.put(_loc_6,  _loc_8);
                }
                _loc_7 = _xhprof.get(_loc_6);
                _loc_7.time = _loc_7.time + (_loc_2 - _stackTime.pop());
                (_loc_7.count + 1);
                _loc_7.mem = _loc_7.mem + (_loc_3 - _stackMem.pop());
            }
            if (!_profilerIsIntegrated && _stackName.length == 0 && _dumptime < _loc_2)
            {
                dumpProfile(_loc_2);
            }
            return;
        }//end

        public static void  dumpProfile (int param1 )
        {
            String _loc_2 =null ;
            Func _loc_3 =null ;
            initSocket();
            if (connectionState != CONNECTION_STATE_ON)
            {
                return;
            }
            if (profilerState != PROFILER_STATE_NOTPROFILING)
            {
                _dumptime = param1 + 5 * 1000;
                for(int i0 = 0; i0 < _xhprof.size(); i0++)
                {
                		_loc_2 = _xhprof.get(i0);

                    _loc_3 = _xhprof.get(_loc_2);
                    _socket.writeUTFBytes("D");
                    _socket.writeByte(1);
                    _socket.writeUTFBytes(_loc_2);
                    _socket.writeByte(2);
                    _socket.writeUTFBytes((_loc_3.time * 1000).toString());
                    _socket.writeByte(2);
                    _socket.writeUTFBytes(_loc_3.count.toString());
                    _socket.writeByte(2);
                    _socket.writeUTFBytes(_loc_3.mem.toString());
                    _socket.writeUTFBytes("\n");
                }
                _socket.flush();
                clear();
            }
            return;
        }//end

        private static void  clear ()
        {
            _xhprof = new Dictionary();
            _stackName.length = 0;
            _stackTime.length = 0;
            _stackMem.length = 0;
            return;
        }//end

        public static void  startProfile ()
        {
            _profilerIsIntegrated = true;
            if (connectionState == CONNECTION_STATE_ERR || profilerState == PROFILER_STATE_INACTIVE || connectionState == CONNECTION_STATE_OFF)
            {
                initSocket();
            }
            if (profilerState == PROFILER_STATE_DEFAULT && connectionState == CONNECTION_STATE_ON)
            {
                discardProfile();
            }
            if (profilerState != PROFILER_STATE_PROFILING)
            {
                profilerState = PROFILER_STATE_PROFILING;
                trace("Starting profiling");
                clear();
            }
            return;
        }//end

        public static void  stopProfile ()
        {
            _profilerIsIntegrated = true;
            if (profilerState == PROFILER_STATE_INACTIVE)
            {
                return;
            }
            if (connectionState != CONNECTION_STATE_ERR)
            {
                dumpProfile(0);
            }
            profilerState = PROFILER_STATE_NOTPROFILING;
            trace("Stopping profiling");
            clear();
            return;
        }//end

        public static void  discardProfile ()
        {
            _profilerIsIntegrated = true;
            if (profilerState == PROFILER_STATE_INACTIVE)
            {
                return;
            }
            if (profilerState == PROFILER_STATE_DEFAULT)
            {
                stopProfile();
            }
            if (connectionState == CONNECTION_STATE_ON)
            {
                _socket.writeUTFBytes("C");
                _socket.writeByte(1);
                _socket.writeUTFBytes("Forget\n");
                _socket.flush();
            }
            return;
        }//end

        public static void  completeProfile ()
        {
            stopProfile();
            if (connectionState != CONNECTION_STATE_ERR)
            {
                close(null);
            }
            return;
        }//end

        public static void  sendCommand (String param1 )
        {
            if (connectionState == CONNECTION_STATE_ON)
            {
                _socket.writeUTFBytes("C");
                _socket.writeByte(1);
                _socket.writeUTFBytes(param1);
                _socket.writeUTFBytes("\n");
                _socket.flush();
            }
            return;
        }//end

        private static void  close (Event event )
        {
            stopProfile();
            _socket.close();
            connectionState = CONNECTION_STATE_OFF;
            trace(PREFIX, "Disconnected");
            return;
        }//end

        private static void  connect (Event event )
        {
            connectionState = CONNECTION_STATE_ON;
            trace(PREFIX, "Connected");
            _socket.writeUTFBytes("V");
            _socket.writeByte(1);
            _socket.writeUTFBytes("0.2.0\n");
            _socket.flush();
            return;
        }//end

        private static void  fail (Event event )
        {
            stopProfile();
            connectionState = CONNECTION_STATE_ERR;
            trace(PREFIX, "Communication failure", event);
            _socket.close();
            return;
        }//end

    }
class Func
    public int time ;
    public int count ;
    public int mem ;

     Func ()
    {
        return;
    }//end





