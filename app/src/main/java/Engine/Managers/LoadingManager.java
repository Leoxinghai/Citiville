package Engine.Managers;

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

import com.xiyu.logic.EventDispatcher;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Engine.*;
import Engine.Classes.*;
import Engine.Events.*;
import root.GlobalEngine;

//import flash.display.*;
//import flash.errors.*;
//import flash.events.*;
//import flash.utils.*;
import com.xinghai.Debug;
import com.xiyu.util.Event;

import java.util.Vector;

public class LoadingManager implements IEventDispatcher
    {
        private Array m_loadQueue ;
        private int m_lowPriorityCount =0;
        private Dictionary m_loadQueues ;
        private Dictionary m_lowPriorityCounts ;
        private int m_loadingCount =0;
        private Dictionary m_outstandingRequests ;
        private Dictionary m_domainMap ;
        protected int m_requestsStarted =0;
        protected int m_requestsReceived =0;
        protected boolean m_worldAssetsRequested =false ;
        protected double m_bytesLoaded =0;
        protected int m_objectsStarted =0;
        protected int m_objectsLoaded =0;
        protected int m_objectsFailed =0;
        private double m_queueStart =0;
        private double m_lastQueueEmptyTime =0;
        private boolean m_startedLoading =false ;
        private boolean m_finishedLoading =false ;
        protected EventDispatcher m_dispatcher =null ;
        public static int maxLoads =8;
        public static int maxLoadsPerDomain =2;
        public static int logSampleRate =0;
        public static boolean perDomainLoadQueues =false ;
        public static  int PRIORITY_NORMAL =0;
        public static  int PRIORITY_HIGH =1;
        public static  int PRIORITY_LOW =2;
        private static LoadingManager s_instance ;
        private static boolean m_worldLoaded =false ;
        private static boolean m_useAssetPacks =false ;
        private static boolean m_trackLoadTiming =false ;
        private static Dictionary m_trackedLoadRequests =new Dictionary ();
        private static Vector<Number> m_trackedLoadTimes =new Vector<Number>();
        private static double m_trackedLoadStartTime =0;
        private static Vector m_trackedRetryCounts = new Vector(LoaderConstants.MAX_RETRIES +2);

        public void  LoadingManager ()
        {
            this.m_loadQueue = new Array();
            this.m_loadQueues = new Dictionary();
            this.m_lowPriorityCounts = new Dictionary();
            this.m_outstandingRequests = new Dictionary();
            this.m_domainMap = new Dictionary();
            this.m_dispatcher = new EventDispatcher(this);
            return;
        }//end

        public Dictionary  getOutstandingRequests ()
        {
            return this.m_outstandingRequests;
        }//end

        public void  clearAll ()
        {
            ResourceLoader _loc_1 =null ;
            if (this.m_outstandingRequests != null)
            {
                for(int i0 = 0; i0 < this.m_outstandingRequests.size(); i0++)
                {
                	_loc_1 = this.m_outstandingRequests.get(i0);

                    _loc_1.stopLoad();
                    _loc_1.removeEventListener(Event.COMPLETE, this.onComplete);
                    _loc_1.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
                }
            }
            this.m_domainMap = new Dictionary();
            this.m_outstandingRequests = new Dictionary();
            this.m_loadQueues = new Dictionary();
            this.m_lowPriorityCounts = new Dictionary();
            this.m_loadQueue = new Array();
            this.m_lowPriorityCount = 0;
            this.m_requestsStarted = 0;
            this.m_requestsReceived = 0;
            this.m_loadingCount = 0;
            this.m_bytesLoaded = 0;
            this.m_objectsStarted = 0;
            this.m_objectsLoaded = 0;
            this.m_objectsFailed = 0;
            this.m_queueStart = 0;
            this.m_lastQueueEmptyTime = 0;
            m_worldLoaded = false;
            m_useAssetPacks = false;
            m_trackLoadTiming = false;
            return;
        }//end

        public int  getObjectsStarted ()
        {
            return this.m_objectsStarted;
        }//end

        public int  getObjectsLoaded ()
        {
            return this.m_objectsLoaded;
        }//end

        public int  getObjectsFailed ()
        {
            return this.m_objectsFailed;
        }//end

        public double  getQueueEmptyTime ()
        {
            return this.m_lastQueueEmptyTime;
        }//end

        public double  getBytesLoaded ()
        {
            return this.m_bytesLoaded;
        }//end

        public void  startWorldLoad ()
        {
            if (!this.m_startedLoading)
            {
                this.m_startedLoading = true;
                if (GlobalEngine.engineOptions.sendLoadingManagerStats == true)
                {
                    StatsManager.count("d1_drop", "assetsLoading_begun", ""+(Math.min((getTimer() - GlobalEngine.startingWaitTime) / 1000, Constants.D1DROP_STATS_MAX_TIMESTAMP)));
                }
            }
            return;
        }//end

        public void  setWorldAssetsRequested ()
        {
            if (!this.m_worldAssetsRequested)
            {
                this.m_worldAssetsRequested = true;
                this.checkHighPriorityComplete();
            }
            return;
        }//end

        public Loader  load (String param1 ,Function param2 ,int param3 =0,Function param4 =null )
        {
            return this.loadFromUrl(param1, {completeCallback:param2, priority:param3, faultCallback:param4, resourceLoaderClass:ResourceLoader}) as Loader;
        }//end

        public Loader  loadFromUrl (String param1 ,Object param2 )
        {
            String _loc_8 =null ;
            String _loc_9 =null ;
            Date _loc_10 =null ;
            _loc_3 = null;
            if (param1 != null && param1 != "")
            {
                logLoadRequest(param1);
                if (this.m_loadingCount == 0)
                {
                    _loc_10 = new Date();
                    this.m_queueStart = _loc_10.getTime();
                }
                this.m_objectsStarted++;
                _loc_8 = null;
                _loc_9 = null;
                if (useAssetPacks && AssetUrlManager.instance.isPackedResource(param1))
                {
                    _loc_9 = AssetUrlManager.instance.lookupPackedResourceUrl(param1);

                    _loc_8 = AssetUrlManager.instance.lookUpUrl(param1);

                }
                else
                {
                    _loc_8 = AssetUrlManager.instance.lookUpUrl(param1);

                }

            }
            else
            {
                throw new Error("NULL url passed to loader!");
            }
            _loc_4 = param2.completeCallback ;
            _loc_5 = param2.priority ;
            _loc_6 = param2.faultCallback ;
            _loc_7 = param2.resourceLoaderClass? (param2.resourceLoaderClass) : (ResourceLoader);

            if(param1.indexOf("NP2101_mailWalk_SE_Anim.png")>0) {
            	Debug.debug4("NP2101_mailWalk_SE_Anim.png."+_loc_8);
            }
            _loc_11 = this.loadFromUrlInternal(_loc_8 ,{completeCallback _loc_4 ,priority ,faultCallback ,resourceLoaderClass ,packUrl });
            return (Loader)_loc_11;
        }//end

        protected Object loadFromUrlInternal (String param1 ,Object param2 )
        {
            Function _loc_4 =null ;
            Function _loc_5 =null ;
            int _loc_6 =0;
            Class _loc_7 =null ;
            ResourceLoader _loc_8 =null ;
            String _loc_9 =null ;
            _loc_3 = null;

            if (param1 != null && param1 != "")
            {
                _loc_4 = null;
                _loc_5 = null;
                _loc_6 = PRIORITY_NORMAL;
                _loc_7 = ResourceLoader;
                if (param2)
                {
                    if (param2.completeCallback)
                    {
                        _loc_4 = param2.completeCallback;
                    }
                    if (param2.priority)
                    {
                        _loc_6 = param2.priority;
                    }
                    if (param2.faultCallback)
                    {
                        _loc_5 = param2.faultCallback;
                    }
                    if (param2.resourceLoaderClass != null && param2.resourceLoaderClass instanceof Class)
                    {
                        _loc_7 = param2.resourceLoaderClass;
                    }
                }
                _loc_8 = null;
                _loc_9 = param1;
                if (param2.packUrl)
                {
                    if (_loc_7 == URLResourceLoader)
                    {
                        _loc_8 = new PackedURLResourceLoader(param2.packUrl, _loc_9, _loc_6, _loc_4, _loc_5);
                    }
                    else
                    {
                        _loc_8 = new PackedResourceLoader(param2.packUrl, _loc_9, _loc_6, _loc_4, _loc_5);
                    }
                }
                else
                {
                    _loc_8 = new _loc_7(_loc_9, _loc_6, _loc_4, _loc_5);
                }
                _loc_3 = _loc_8.getLoader();
                this.addToQueue(_loc_8, param1, _loc_6);
            }
            else
            {
                throw new Error("NULL url passed to loader!");
            }
            return _loc_3;
        }//end

        private void  addToQueue (ResourceLoader param1 ,String param2 ,int param3 )
        {

            if (trackLoadTiming)
            {
                addTrackedLoad(param1, param2);
            }
            if (perDomainLoadQueues)
            {
                this.addToPerDomainQueue(param1, param2, param3);
            }
            else
            {
                this.addToSingleQueue(param1, param2, param3);
            }
            if (PRIORITY_LOW != param3 || this.m_loadingCount <= 0)
            {
                this.pumpQueue();
            }
            return;
        }//end

        private void  addToPerDomainQueue (ResourceLoader param1 ,String param2 ,int param3 )
        {
            _loc_4 = param1.getDomain ();
            if (!this.m_loadQueues.hasOwnProperty(_loc_4))
            {
                this.m_loadQueues.put(_loc_4,  new Array());
                this.m_lowPriorityCounts.put(_loc_4,  0);
            }
            if (PRIORITY_LOW == param3)
            {
                _loc_5 = this.m_lowPriorityCounts ;
                _loc_6 = _loc_4;
                _loc_7 = this.m_lowPriorityCounts.get(_loc_4) +1;
                _loc_5.put(_loc_6,  _loc_7);
                this.m_loadQueues.get(_loc_4).splice(0, 0, param1);
            }
            else if (PRIORITY_HIGH == param3)
            {
                this.m_loadQueues.get(_loc_4).push(param1);
            }
            else
            {
                this.m_loadQueues.get(_loc_4).splice(this.m_lowPriorityCounts.get(_loc_4), 0, param1);
            }
            return;
        }//end

        private void  addToSingleQueue (ResourceLoader param1 ,String param2 ,int param3 )
        {
            if (PRIORITY_LOW == param3)
            {
                this.m_lowPriorityCount++;
                this.m_loadQueue.splice(0, 0, param1);
            }
            else if (PRIORITY_HIGH == param3)
            {
                this.m_loadQueue.push(param1);
            }
            else
            {
                this.m_loadQueue.splice(this.m_lowPriorityCount, 0, param1);
            }
            return;
        }//end

        public void  cancelLoad (EventDispatcher param1 )
        {
            Array _loc_3 =null ;
            boolean _loc_2 =false ;
            if (perDomainLoadQueues)
            {
                for(int i0 = 0; i0 < this.m_loadQueues.size(); i0++)
                {
                	_loc_3 = this.m_loadQueues.get(i0);

                    if (this.cancelLoadInQueue(param1, _loc_3))
                    {
                        _loc_2 = true;
                        break;
                    }
                }
            }
            else if (this.cancelLoadInQueue(param1, this.m_loadQueue))
            {
                _loc_2 = true;
            }
            if (_loc_2)
            {
                this.checkQueueEmpty();
            }
            return;
        }//end

        private boolean  cancelLoadInQueue (EventDispatcher param1 ,Array param2 )
        {
            ResourceLoader _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < param2.length())
            {

                _loc_4 =(ResourceLoader) param2.get(_loc_3);
                if (_loc_4 != null)
                {
                    if (param1 == _loc_4.getLoader())
                    {
                        param2.splice(_loc_3, 1);
                        _loc_4.stopLoad();
                        return true;
                    }
                }
                _loc_3++;
            }
            return false;
        }//end

        private boolean  canLoadFromDomain (ResourceLoader param1 )
        {
            _loc_2 = param1.getDomain ();
            _loc_3 = param1.getURL ();
            _loc_4 = this.m_domainMap.get(_loc_2) ;
            if (this.m_domainMap.get(_loc_2) == null || _loc_4.count < maxLoadsPerDomain)
            {
                return true;
            }
            if (param1.isPackedResource())
            {
                if (_loc_4.urls.get(_loc_3) != null && _loc_4.urls.get(_loc_3) > 0)
                {
                    return true;
                }
            }
            return false;
        }//end

        private void  addLoadFromDomain (ResourceLoader param1 )
        {
            _loc_2 = param1.getDomain ();
            _loc_3 = param1.getURL ();
            if (this.m_domainMap.get(_loc_2) == null)
            {
                this.m_domainMap.put(_loc_2,  {count:0, urls:new Dictionary()});
            }
            if (this.m_domainMap.get(_loc_2).urls.get(_loc_3) == null)
            {
                this.m_domainMap.get(_loc_2).urls.put(_loc_3,  0);
            }
            if (this.m_domainMap.get(_loc_2).urls.get(_loc_3) == 0 || !param1.isPackedResource())
            {
                _loc_41 = this.m_domainMap.get(_loc_2);
                _loc_51 = this.m_domainMap.get(_loc_2).count+1;
                _loc_41.count = _loc_51;
            }
            _loc_4 = this.m_domainMap.get(_loc_2).urls ;
            _loc_5 = _loc_3;
            _loc_6 = this.m_domainMap.get(_loc_2).urls.get(_loc_3) +1;
            _loc_4.put(_loc_5,  _loc_6);
            return;
        }//end

        private void  clearLoadFromDomain (ResourceLoader param1 )
        {
            String _loc_2 =param1.getDomain ();
            String _loc_3 =param1.getURL ();
            if (this.m_domainMap.get(_loc_2) != null)
            {
                _loc_4 = this.m_domainMap.get(_loc_2).urls ;
                String _loc_5 =_loc_3 ;
                _loc_6 = this.m_domainMap.get(_loc_2).urls.get(_loc_3) -1;
                _loc_4.put(_loc_5,  _loc_6);
                if (this.m_domainMap.get(_loc_2).urls.get(_loc_3) == 0)
                {
                    delete this.m_domainMap.get(_loc_2).urls.get(_loc_3);
                    _loc_41 = this.m_domainMap.get(_loc_2);
                    _loc_51 = this.m_domainMap.get(_loc_2).count-1;
                    _loc_41.count = _loc_51;
                }
                else if (!param1.isPackedResource())
                {
                    _loc_42 = this.m_domainMap.get(_loc_2);
                    _loc_52 = this.m_domainMap.get(_loc_2).count-1;
                    _loc_42.count = _loc_52;
                }
            }
            return;
        }//end

        private void  pumpQueue ()
        {
            if (perDomainLoadQueues)
            {
                this.pumpPerDomainQueue();
            }
            else
            {
                this.pumpSingleQueue();
            }
            this.checkQueueEmpty();
            return;
        }//end

        private void  pumpPerDomainQueue ()
        {
            Array _loc_1 =null ;
            int _loc_2 =0;
            ResourceLoader _loc_3 =null ;

            for(int i0 = 0; i0 < this.m_loadQueues.size(); i0++)
            {
            	_loc_1 = this.m_loadQueues.get(i0);

                _loc_2 = _loc_1.length - 1;
                while (_loc_2 >= 0)
                {

                    if (this.m_loadingCount >= maxLoads)
                    {
                        break;
                    }
                    _loc_3 =(ResourceLoader) _loc_1.get(_loc_2);
                    if (!this.canLoadFromDomain(_loc_3))
                    {
                        break;
                    }
                    _loc_1.splice(_loc_2, 1);
                    this.startLoad(_loc_3);
                    if (_loc_3.getPriority() != PRIORITY_LOW)
                    {
                        this.m_requestsStarted++;
                    }
                    else
                    {
                        this.m_lowPriorityCount--;
                    }
                    _loc_2 = _loc_2 - 1;
                }
                if (this.m_loadingCount >= maxLoads)
                {
                    break;
                }
            }
            return;
        }//end

        private void  pumpSingleQueue ()
        {
            int _loc_2 =0;
            ResourceLoader _loc_3 =null ;
            boolean _loc_1 =false ;

            while (this.m_loadQueue.length > 0 && this.m_loadingCount < maxLoads && _loc_1 == false)
            {

                _loc_1 = true;
                _loc_2 = this.m_loadQueue.length - 1;
                while (_loc_2 >= 0)
                {

                    _loc_3 =(ResourceLoader) this.m_loadQueue.get(_loc_2);
                    if (this.canLoadFromDomain(_loc_3))
                    {
                        this.m_loadQueue.splice(_loc_2, 1);
                        this.startLoad(_loc_3);
                        if (_loc_3.getPriority() != PRIORITY_LOW)
                        {
                            this.m_requestsStarted++;
                        }
                        else
                        {
                            this.m_lowPriorityCount--;
                        }
                        _loc_1 = false;
                        break;
                    }
                    _loc_2 = _loc_2 - 1;
                }
            }
            return;
        }//end

        private void  startLoad (ResourceLoader param1 )
        {
            ResourceLoader resourceLoader =param1 ;
            try
            {

                this.addLoadFromDomain(resourceLoader);
                this.m_loadingCount++;
                this.m_outstandingRequests.put(resourceLoader,  resourceLoader);
                resourceLoader.addEventListener(Event.COMPLETE, this.onComplete, false, 0, true);
                resourceLoader.addEventListener(IOErrorEvent.IO_ERROR, this.onError, false, 0, true);
                resourceLoader.startLoad();
            }
            catch (error:IOError)
            {
                GlobalEngine.log("Loader", "IOError occured: " + error.message);
                int _loc_5 =m_loadingCount -1;
                m_loadingCount = _loc_5;
                clearLoadFromDomain(resourceLoader);
            }
            return;
        }//end

        public boolean  isQueueEmpty ()
        {
            Array _loc_2 =null ;
            if (this.m_loadingCount != 0 || this.m_queueStart == 0)
            {
                return false;
            }
            boolean _loc_1 =true ;
            if (perDomainLoadQueues)
            {
                for(int i0 = 0; i0 < this.m_loadQueues.size(); i0++)
                {
                	_loc_2 = this.m_loadQueues.get(i0);

                    if (_loc_2.length != 0)
                    {
                        _loc_1 = false;
                        break;
                    }
                }
            }
            else
            {
                _loc_1 = this.m_loadQueue.length == 0;
            }
            return _loc_1;
        }//end

        protected void  checkQueueEmpty ()
        {
            Date _loc_1 =null ;
            if (this.isQueueEmpty())
            {
                _loc_1 = new Date();
                this.m_lastQueueEmptyTime = _loc_1.getTime() - this.m_queueStart;
                GlobalEngine.log("Loader", "Load queue empty, took " + this.m_lastQueueEmptyTime + "ms");
                getInstance().dispatchEvent(new LoaderEvent(LoaderEvent.LOAD_QUEUE_EMPTY));
                this.m_queueStart = 0;
            }
            return;
        }//end

        public void  onComplete (Event event )
        {
            ResourceLoader _loc_2 =(ResourceLoader)event.target;



            _loc_3 = this.m_outstandingRequests.get(_loc_2) ;
            _loc_4 = _loc_2.getDomain ();
            this.m_objectsLoaded++;
            this.m_loadingCount--;
            this.clearLoadFromDomain(_loc_2);


            this.m_bytesLoaded = this.m_bytesLoaded + _loc_2.getbytesTotal();
            _loc_2.removeEventListener(Event.COMPLETE, this.onComplete);
            _loc_2.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
            _loc_2.stopLoad();
            clearTrackedLoad(_loc_2);
            if (_loc_3 != null)
            {
                delete this.m_outstandingRequests.get(_loc_2);
                if (_loc_2.getPriority() != PRIORITY_LOW)
                {
                    this.m_requestsReceived++;
                    GlobalEngine.log("Loader", "Current loading progress: " + this.m_requestsReceived + "/" + this.m_requestsStarted);
                    getInstance().dispatchEvent(new ProgressEvent(ProgressEvent.PROGRESS, false, false, this.m_requestsReceived, this.m_requestsStarted));
                    if (this.m_requestsReceived >= this.m_requestsStarted)
                    {
                        getInstance().dispatchEvent(new LoaderEvent(LoaderEvent.ALL_HIGH_PRIORITY_LOADED));
                        if (!this.m_finishedLoading)
                        {
                            this.m_finishedLoading = true;
                            if (GlobalEngine.engineOptions.sendLoadingManagerStats == true)
                            {
                                StatsManager.count("d1_drop", "assetsLoading_complete", ""+(Math.min((getTimer() - GlobalEngine.startingWaitTime) / 1000, Constants.D1DROP_STATS_MAX_TIMESTAMP)) );
                            }
                        }
                    }
                }
                else if (this.m_requestsStarted == 0)
                {
                    getInstance().dispatchEvent(new LoaderEvent(LoaderEvent.ALL_HIGH_PRIORITY_LOADED));
                }
            }
            else
            {
                GlobalEngine.log("Loader", "Loading request not found after complete: " + event);
            }
            LoaderEvent _loc_5 =new LoaderEvent(LoaderEvent.LOADED );
            _loc_5.eventData = _loc_2.getLoader();
            getInstance().dispatchEvent(_loc_5);
            this.pumpQueue();
            return;
        }//end

        protected void  onHighPriorityAssetLoaded ()
        {
            this.m_requestsReceived++;
            GlobalEngine.log("Loader", "Progress: .get(requests=" + this.m_requestsReceived + "/" + this.m_requestsStarted + ", objects=" + this.m_objectsLoaded + "/" + this.m_objectsStarted + ", bytes=" + this.m_bytesLoaded + ")");
            this.dispatchEvent(new ProgressEvent(ProgressEvent.PROGRESS, false, false, this.m_requestsReceived, this.m_requestsStarted));
            this.checkHighPriorityComplete();
            return;
        }//end

        private void  checkHighPriorityComplete ()
        {
            if (this.m_requestsReceived >= this.m_requestsStarted && this.m_worldAssetsRequested)
            {
                GlobalEngine.log("Loader", "All high priority loaded .get(" + this.m_requestsReceived + "/" + this.m_requestsStarted + " requests, " + this.m_bytesLoaded + " bytes)");
                this.dispatchEvent(new LoaderEvent(LoaderEvent.ALL_HIGH_PRIORITY_LOADED));
                StatsManager.sample(100, "High priority assets loaded sec", (getTimer() / 1000).toString());
                this.m_worldAssetsRequested = false;
            }
            return;
        }//end

        public void  onError (IOErrorEvent event )
        {

            _loc_2 =(ResourceLoader) event.target;
            _loc_3 = this.m_outstandingRequests.get(_loc_2) ;
            _loc_4 = _loc_2.getDomain ();
            this.m_objectsFailed++;
            this.m_loadingCount--;
            this.clearLoadFromDomain(_loc_2);
            _loc_2.stopLoad();
            _loc_2.removeEventListener(Event.COMPLETE, this.onComplete);
            _loc_2.removeEventListener(IOErrorEvent.IO_ERROR, this.onError);
            clearTrackedLoad(_loc_2);
            if (_loc_3 != null)
            {
                delete this.m_outstandingRequests.get(_loc_2);
                if (_loc_2.getPriority() != PRIORITY_LOW)
                {
                    this.m_requestsReceived++;
                    GlobalEngine.log("Loader", "Current loading progress: " + this.m_requestsReceived + "/" + this.m_requestsStarted);
                    getInstance().dispatchEvent(new ProgressEvent(ProgressEvent.PROGRESS, false, false, this.m_requestsReceived, this.m_requestsStarted));
                }
            }
            else
            {
                GlobalEngine.log("Loader", "Loading request not found after error: " + event);
            }
            this.pumpQueue();
            return;
        }//end

        public void  addEventListener (String param1 ,Function param2 ,boolean param3 =false ,int param4 =0,boolean param5 =false )
        {
            this.m_dispatcher.addEventListener(param1, param2, param3, param4);
            return;
        }//end

        public boolean  dispatchEvent (Event event )
        {
            return this.m_dispatcher.dispatchEvent(event);
        }//end

        public boolean  hasEventListener (String param1 )
        {
            return this.m_dispatcher.hasEventListener(param1);
        }//end

        public void  removeEventListener (String param1 ,Function param2 ,boolean param3 =false )
        {
            this.m_dispatcher.removeEventListener(param1, param2, param3);
            return;
        }//end

        public boolean  willTrigger (String param1 )
        {
            return this.m_dispatcher.willTrigger(param1);
        }//end

        public static LoadingManager  getInstance ()
        {
            if (!s_instance)
            {
                s_instance = new GlobalEngine.engineOptions.loadingManagerClass();
            }
            return s_instance;
        }//end

        public static Dictionary  getOutstandingRequests ()
        {
            return getInstance().getOutstandingRequests();
        }//end

        public static void  clearAll ()
        {
            getInstance().clearAll();
            return;
        }//end

        public static int  getObjectsStarted ()
        {
            return getInstance().getObjectsStarted();
        }//end

        public static int  getObjectsLoaded ()
        {
            return getInstance().getObjectsLoaded();
        }//end

        public static int  getObjectsFailed ()
        {
            return getInstance().getObjectsFailed();
        }//end

        public static double  getQueueEmptyTime ()
        {
            return getInstance().getQueueEmptyTime();
        }//end

        public static double  getBytesLoaded ()
        {
            return getInstance().getBytesLoaded();
        }//end

        public static void  startWorldLoad ()
        {
            getInstance().startWorldLoad();
            return;
        }//end

        public static Loader  load (String param1 ,Function param2 ,int param3 =0,Function param4 =null )
        {
            return getInstance().load(param1, param2, param3, param4);
        }//end

        public static Object loadFromUrl (String param1 ,Object param2 )
        {
            return getInstance().loadFromUrl(param1, param2);
        }//end

        public static void  cancelLoad (EventDispatcher param1 )
        {
            getInstance().cancelLoad(param1);
            return;
        }//end

        public static boolean  isQueueEmpty ()
        {
            return getInstance().isQueueEmpty();
        }//end

        public static void  onComplete (Event event )
        {
            getInstance().onComplete(event);
            return;
        }//end

        public static void  onError (IOErrorEvent event )
        {
            getInstance().onError(event);
            return;
        }//end

        public static void  setWorldAssetsRequested ()
        {
            getInstance().setWorldAssetsRequested();
            return;
        }//end

        public static boolean  worldLoaded ()
        {
            return m_worldLoaded;
        }//end

        public static void  worldLoaded (boolean param1 )
        {
            m_worldLoaded = param1;
            return;
        }//end

        public static boolean  useAssetPacks ()
        {
            return m_useAssetPacks;
        }//end

        public static void  useAssetPacks (boolean param1 )
        {
            m_useAssetPacks = param1;
            return;
        }//end

        public static boolean  trackLoadTiming ()
        {
            return m_trackLoadTiming;
        }//end

        public static void  trackLoadTiming (boolean param1 )
        {
            if (m_trackLoadTiming === true && param1 === true)
            {
                throw new Error("Already tracking load times");
            }
            m_trackLoadTiming = param1;
            if (m_trackLoadTiming === true)
            {
                m_trackedLoadStartTime = GlobalEngine.currentTime;
                m_trackedLoadRequests = new Dictionary();
                m_trackedLoadTimes = new Vector<Number>();
            }
            return;
        }//end
        public static void  logLoadRequest (String param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            if (logSampleRate > 0 && AssetUrlManager.instance.isStaticAssetDomain(param1))
            {
                _loc_2 = param1.substring((param1.lastIndexOf("/") + 1), param1.length());
                _loc_3 = _loc_2;
                _loc_4 = "";
                if (_loc_2.length > StatsManager.MAX_CHARS)
                {
                    _loc_3 = _loc_2.substring(_loc_2.length - StatsManager.MAX_CHARS, _loc_2.length());
                    _loc_4 = _loc_2.slice(Math.max(0, _loc_2.length - StatsManager.MAX_CHARS * 2), _loc_2.length - StatsManager.MAX_CHARS);
                }
                _loc_5 = AssetUrlManager.instance.lookUpUrl(param1);
                _loc_6 = AssetUrlManager.instance.lookUpUrl(param1).substr((_loc_5.lastIndexOf("/") + 1), _loc_5.length());
                _loc_7 = _loc_6.slice(0, StatsManager.MAX_CHARS);
                _loc_8 = _loc_6.slice(StatsManager.MAX_CHARS);
                _loc_9 = (!worldLoaded).toString();
                StatsManager.sample(logSampleRate, "assets", _loc_3, _loc_4, _loc_7, _loc_8, _loc_9);
            }
            return;
        }//end

        private static boolean  areTrackedLoadsInProgress ()
        {
            Object _loc_1 =null ;
            for(int i0 = 0; i0 < m_trackedLoadRequests.size(); i0++)
            {
            	_loc_1 = m_trackedLoadRequests.get(i0);

                return true;
            }
            return false;
        }//end

        private static void  addTrackedLoad (ResourceLoader param1 ,String param2 )
        {
            if (AssetUrlManager.instance.isStaticAssetDomain(param2))
            {
                m_trackedLoadRequests.put(param1,  GlobalEngine.currentTime);
            }
            return;
        }//end

        private static void  clearTrackedLoad (ResourceLoader param1 )
        {
            double duration ;
            double totalDuration ;
            double median ;
            double average ;
            double loadTime ;
            LoaderEvent loaderEvent ;
            resourceLoader = param1;
            if (m_trackedLoadRequests.get(resourceLoader) !== undefined)
            {
                Vector _loc_3.<uint >=m_trackedRetryCounts ;
                int _loc_4 =resourceLoader.getRetryCount ();
                _loc_5 = m_trackedRetryCounts.get(resourceLoader.getRetryCount ()) +1;
                _loc_3.put(_loc_4,  _loc_5);
                duration = GlobalEngine.currentTime - m_trackedLoadRequests.get(resourceLoader);
                m_trackedLoadTimes.push(duration);
                delete m_trackedLoadRequests.get(resourceLoader);
                if (!areTrackedLoadsInProgress())
                {
                    trackLoadTiming = false;
                    totalDuration = GlobalEngine.currentTime - m_trackedLoadStartTime;
                    m_trackedLoadTimes =m_trackedLoadTimes .sort (double  (double param11 ,double param2 )
            {
                return param11 - param2;
            }//end
            );
                    median = m_trackedLoadTimes.get(int(m_trackedLoadTimes.length / 2));
                    average = 0;
                    int _loc_31 =0;
                    _loc_41 = m_trackedLoadTimes;
                    for(int i0 = 0; i0 < m_trackedLoadTimes.size(); i0++)
                    {
                    	loadTime = m_trackedLoadTimes.get(i0);


                        average = average + loadTime;
                    }
                    average = average / m_trackedLoadTimes.length;
                    loaderEvent = new LoaderEvent(LoaderEvent.ALL_TRACKED_LOADED);
                    loaderEvent.eventData = {totalLoadTime:totalDuration, medianLoadTime:median, averageLoadTime:average, retryCounts:m_trackedRetryCounts};
                    getInstance().dispatchEvent(loaderEvent);
                }
            }
            return;
        }//end

    }



