package zasp.Bridge;

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
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;
import zasp.Util.*;

    public class BridgeAgent extends EventDispatcher
    {
        private  String ACTION_POLL ="Poll";
        private  String ACTION_GET_LOAD_TIME ="GetLoadTime";
        private  String ACTION_GET_MEMORY_SINGLE ="GetMemorySingle";
        private  String ACTION_GET_MEMORY_BEGIN ="GetMemoryBegin";
        private  String ACTION_GET_MEMORY_END ="GetMemoryEnd";
        private  String ACTION_GET_FPS_SINGLE ="GetFpsSingle";
        private  String ACTION_GET_FPS_BEGIN ="GetFpsBegin";
        private  String ACTION_GET_FPS_END ="GetFpsEnd";
        private  String SIGNAL_SUCCESS ="Command::Success";
        private  String SIGNAL_FAILURE ="Command::Failure";
        private  double GET_COMMAND_DELAY =1000;
        private String bridgeDomain ="http://zunit.corp.zynga.com/WebBridge/";
        private String pingUrl ;
        private String getCommandUrl ;
        private String setResultUrl ;
        private boolean isRunning =false ;
        private URLRequest request ;
        private URLVariables requestVars ;
        private URLLoader response ;
        private Timer getCommandTimer ;
        private double index =0;
        private DisplayObject displayRoot ;
        private double loadTime =0;
        private  double ALPHA =0.15;
        private  double MS_PER_S =1000;
        private String fpsCommandID ;
        private SumSampler fpsSampler ;
        private int fpsInterval ;
        private EMA ema =null ;
        private int lastTickMS =0;
        private String memoryCommandID ;
        private SumSampler memorySampler ;
        private int memoryInterval ;
        public static String EVENT_BRIDGE_LOG ="BridgeLogEvent";

        public void  BridgeAgent (DisplayObject param1 )
        {
            this.pingUrl = this.bridgeDomain + "Ping.ashx";
            this.getCommandUrl = this.bridgeDomain + "GetCommand.ashx";
            this.setResultUrl = this.bridgeDomain + "SetResult.ashx";
            this.log("::BridgeAgent");
            this.displayRoot = param1;
            return;
        }//end

        public void  start ()
        {
            try
            {
                this.log("::Start");
                this.isRunning = true;
                this.getCommandTimer = new Timer(this.GET_COMMAND_DELAY, 1);
                this.getCommandTimer.addEventListener(TimerEvent.TIMER, this.getCommand);
                this.ema = new EMA(this.ALPHA);
                this.displayRoot.addEventListener(Event.ADDED_TO_STAGE, this.handleAddedToStage);
                this.displayRoot.addEventListener(Event.ENTER_FRAME, this.handleEnterFrame);
                this.ping(null);
            }
            catch (err:Error)
            {
                this.log(err.toString());
                this.stop();
            }
            return;
        }//end

        public void  stop ()
        {
            try
            {
                this.log("::Stop");
                this.displayRoot.removeEventListener(Event.ADDED_TO_STAGE, this.handleAddedToStage);
                this.displayRoot.removeEventListener(Event.ENTER_FRAME, this.handleEnterFrame);
                this.ema = null;
            }
            catch (err:Error)
            {
                this.log(err.toString());
            }
            finally
            {
                this.isRunning = false;
            }
            return;
        }//end

        private void  ping (Event event )
        {
            if (this.isRunning)
            {
                this.log("::Ping");
                this.request = new URLRequest(this.pingUrl);
                this.requestVars = new URLVariables();
                this.response = new URLLoader();
             this.index++;
                this.requestVars.d = this.index + 1;
                this.request.method = URLRequestMethod.GET;
                this.request.data = this.requestVars;
                this.response.addEventListener(Event.COMPLETE, this.handlePingResponse);
                this.response.addEventListener(IOErrorEvent.IO_ERROR, this.handlePingError);
                this.response.load(this.request);
            }
            return;
        }//end

        private void  handlePingResponse (Event event )
        {
            event = event;
            this.log("::HandlePingResponse");
            try
            {
                this.cleanLoaders();
                if (event != null && event.target != null && event.target.data != null)
                {
                    this.log("BridgeID: " + event.target.data);
                    this.getCommand(null);
                }
            }
            catch (error:Error)
            {
                this.log(error.message);
                this.stop();
            }
            return;
        }//end

        private void  handlePingError (Event event )
        {
            this.log("::HandlePingError");
            this.log("::HandlePingError:" + event.type);
            this.cleanLoaders();
            this.stop();
            return;
        }//end

        private void  getCommand (Event event )
        {
            if (this.getCommandTimer)
            {
                this.getCommandTimer.stop();
            }
            if (this.isRunning)
            {
                this.log("::GetCommand");
                this.request = new URLRequest(this.getCommandUrl);
                this.requestVars = new URLVariables();
                this.response = new URLLoader();
             this.index++;
                this.requestVars.d = this.index + 1;
                this.request.method = URLRequestMethod.GET;
                this.request.data = this.requestVars;
                this.response.addEventListener(Event.COMPLETE, this.handleGetCommandResponse);
                this.response.addEventListener(IOErrorEvent.IO_ERROR, this.handleGetCommandError);
                this.response.load(this.request);
            }
            return;
        }//end

        private void  handleGetCommandResponse (Event event )
        {
            String commandString ;
            Array splits ;
            String commandID ;
            String commandType ;
            String commandData ;
            event = event;
            this.log("::HandleGetCommandResponse");
            try
            {
                this.cleanLoaders();
                if (event != null && event.target != null && event.target.data != null)
                {
                    commandString = event.target.data;
                    splits = commandString.split("&");
                    commandID = splits.get(0).split("=").get(1);
                    commandType = splits.get(1).split("=").get(1);
                    commandData = splits.get(2).split("=").get(1);
                    switch(commandType)
                    {
                        case this.ACTION_GET_LOAD_TIME:
                        {
                            this.handleGetLoadTime(commandID, commandType, commandData);
                            break;
                        }
                        case this.ACTION_GET_FPS_SINGLE:
                        {
                            this.handleGetFpsSingle(commandID, commandType, commandData);
                            break;
                        }
                        case this.ACTION_GET_FPS_BEGIN:
                        {
                            this.handleGetFpsBegin(commandID, commandType, commandData);
                            break;
                        }
                        case this.ACTION_GET_FPS_END:
                        {
                            this.handleGetFpsEnd(commandID, commandType, commandData);
                            break;
                        }
                        case this.ACTION_GET_MEMORY_SINGLE:
                        {
                            this.handleGetMemorySingle(commandID, commandType, commandData);
                            break;
                        }
                        case this.ACTION_GET_MEMORY_BEGIN:
                        {
                            this.handleGetMemoryBegin(commandID, commandType, commandData);
                            break;
                        }
                        case this.ACTION_GET_MEMORY_END:
                        {
                            this.handleGetMemoryEnd(commandID, commandType, commandData);
                            break;
                        }
                        default:
                        {
                            this.handlePoll();
                            break;
                            break;
                        }
                    }
                }
            }
            catch (error:Error)
            {
                this.log(error.message);
                this.handlePoll();
            }
            return;
        }//end

        private void  handleGetCommandError (Event event )
        {
            this.log("::HandleGetCommandError");
            this.cleanLoaders();
            this.getCommandTimer.start();
            return;
        }//end

        private void  setResult (String param1 ,String param2 )
        {
            _loc_3 = this.setResultUrl +"?commandID="+param1 +"&completed=true";
            this.log("::SetResult");
            this.request = new URLRequest(_loc_3);
            this.requestVars = new URLVariables();
            this.response = new URLLoader();
            this.requestVars.commandID = param1;
            this.requestVars.completed = "true";
            this.request.method = URLRequestMethod.POST;
            this.request.data = param2;
            this.response.addEventListener(Event.COMPLETE, this.handleSetResultResponse);
            this.response.addEventListener(IOErrorEvent.IO_ERROR, this.handleSetResultError);
            this.response.load(this.request);
            return;
        }//end

        private void  handleSetResultResponse (Event event )
        {
            this.log("::HandleSetResultResponse");
            this.cleanLoaders();
            this.getCommandTimer.start();
            return;
        }//end

        private void  handleSetResultError (Event event )
        {
            this.log("::HandleSetResultError");
            this.cleanLoaders();
            this.getCommandTimer.start();
            return;
        }//end

        private void  cleanLoaders ()
        {
            try
            {
                this.log("::CleanLoaders");
                this.requestVars = null;
                if (this.request != null)
                {
                    this.request.data = null;
                    this.request.url = null;
                    this.request = null;
                }
                if (this.response != null)
                {
                    this.response.close();
                    this.response.removeEventListener(Event.COMPLETE, this.handlePingResponse);
                    this.response.removeEventListener(IOErrorEvent.IO_ERROR, this.handlePingError);
                    this.response.removeEventListener(Event.COMPLETE, this.handleGetCommandResponse);
                    this.response.removeEventListener(IOErrorEvent.IO_ERROR, this.handleGetCommandError);
                    this.response.removeEventListener(Event.COMPLETE, this.handleSetResultResponse);
                    this.response.removeEventListener(IOErrorEvent.IO_ERROR, this.handleSetResultError);
                    this.response = null;
                }
            }
            catch (error:Error)
            {
                this.log("::CleanLoaders:Error:" + error.message);
            }
            return;
        }//end

        private void  handlePoll ()
        {
            this.log("::HandlePoll");
            this.getCommandTimer.start();
            return;
        }//end

        private void  handleGetLoadTime (String param1 ,String param2 ,String param3 )
        {
            this.setResult(param1, this.loadTime.toString());
            return;
        }//end

        public void  saveLoadTime (double param1 )
        {
            this.loadTime = param1;
            return;
        }//end

        private void  handleGetMemorySingle (String param1 ,String param2 ,String param3 )
        {
            System.gc();
            this.setResult(param1, System.totalMemory.toString());
            return;
        }//end

        private void  handleGetMemoryBegin (String param1 ,String param2 ,String param3 )
        {
            _loc_4 =(double)(param3 );
            System.gc();
            this.memoryCommandID = param1;
            this.memorySampler = new SumSampler(Number.MAX_VALUE);
            this.memoryInterval = setInterval(this.getMemoryAsync, _loc_4);
            this.setResult(param1, this.SIGNAL_SUCCESS);
            return;
        }//end

        private void  handleGetMemoryEnd (String param1 ,String param2 ,String param3 )
        {
            _loc_4 = this.memorySampler.value/this.memorySampler.length;
            clearInterval(this.memoryInterval);
            this.memoryCommandID = null;
            this.memorySampler = null;
            this.setResult(param1, _loc_4.toString());
            return;
        }//end

        private void  getMemoryAsync ()
        {
            this.memorySampler.sample(System.totalMemory);
            return;
        }//end

        private void  handleGetFpsSingle (String param1 ,String param2 ,String param3 )
        {
            _loc_4 = this.MS_PER_S/this.ema.value;
            this.setResult(param1, _loc_4.toString());
            return;
        }//end

        private void  handleGetFpsBegin (String param1 ,String param2 ,String param3 )
        {
            _loc_4 =(double)(param3 );
            this.fpsCommandID = param1;
            this.fpsSampler = new SumSampler(Number.MAX_VALUE);
            this.fpsInterval = setInterval(this.getFpsAsync, _loc_4);
            this.setResult(param1, this.SIGNAL_SUCCESS);
            return;
        }//end

        private void  handleGetFpsEnd (String param1 ,String param2 ,String param3 )
        {
            String _loc_4 ="";
            int _loc_5 =0;
            while (_loc_5 < this.fpsSampler.length())
            {

                _loc_4 = _loc_4 + (this.fpsSampler.sampleAt(_loc_5).toString() + ",");
                _loc_5++;
            }
            clearInterval(this.fpsInterval);
            this.fpsCommandID = null;
            this.fpsSampler = null;
            this.setResult(param1, _loc_4);
            return;
        }//end

        public void  handleAddedToStage (Event event )
        {
            this.ema = new EMA(this.ALPHA);
            this.lastTickMS = getTimer();
            return;
        }//end

        public void  handleEnterFrame (Event event )
        {
            _loc_2 = getTimer();
            _loc_3 = _loc_2-this.lastTickMS ;
            this.lastTickMS = _loc_2;
            this.ema.sample(_loc_3);
            return;
        }//end

        private void  getFpsAsync ()
        {
            _loc_1 = this.MS_PER_S/this.ema.value;
            this.fpsSampler.sample(_loc_1);
            return;
        }//end

        private void  log (String param1 )
        {
            BridgeLogEvent _loc_2 =null ;
            trace(param1);
            if (this.hasEventListener(EVENT_BRIDGE_LOG))
            {
                _loc_2 = new BridgeLogEvent(EVENT_BRIDGE_LOG, true, true);
                _loc_2.message = param1;
                this.dispatchEvent(_loc_2);
            }
            return;
        }//end

    }




