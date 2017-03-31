package Managers;

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
import Engine.Events.*;
import Engine.Managers.*;
import Engine.Transactions.*;
import Events.*;
import Init.*;
import java.util.Vector;

//import flash.events.*;

    public class QuestManager extends EventDispatcher
    {
        private boolean m_initialized =false ;
        private Vector<Quest> m_activeQuests;
        private static QuestManager m_instance ;

        public  QuestManager ()
        {
            this.m_activeQuests = new Vector<Quest>();
            return;
        }//end

        public void  initialize ()
        {
            if (this.m_initialized == false)
            {
                this.m_initialized = true;
                TransactionManager.getInstance().addEventListener(TransactionEvent.ADDED, this.onTransactionAdded);
                TransactionManager.getInstance().addEventListener(TransactionEvent.COMPLETED, this.onTransactionComplete);
            }
            return;
        }//end

        public Vector<Quest> getActiveQuests ()
        {
            Object _loc_2 =null ;
            Quest _loc_3 =null ;
            Vector<Quest> _loc_1=new Vector<Quest>();
            for(int i0 = 0; i0 < this.m_activeQuests.size(); i0++)
            {
            		_loc_2 = this.m_activeQuests.get(i0);

                _loc_3 = QuestSettingsInit.getItemByName(_loc_2.name);
                if (_loc_3 != null)
                {
                    _loc_1.add(_loc_3);
                }
            }
            return _loc_1;
        }//end

        public Vector<Quest> getIncompleteActiveQuests()
        {
            Object _loc_2 =null ;
            Quest _loc_3 =null ;
            Vector<Quest> _loc_1=new Vector<Quest>();
            for(int i0 = 0; i0 < this.m_activeQuests.size(); i0++)
            {
            		_loc_2 = this.m_activeQuests.get(i0);

                if (!_loc_2.complete)
                {
                    _loc_3 = QuestSettingsInit.getItemByName(_loc_2.name);
                    if (_loc_3 != null)
                    {
                        _loc_1.add(_loc_3);
                    }
                }
            }
            return _loc_1;
        }//end

        public void  removeActiveQuestByName (String param1 )
        {
            int _loc_2 =getQuestProgressIndexByName(param1 );
            if (_loc_2 >= 0)
            {
                this.m_activeQuests.remove(_loc_2);
            }
            return;
        }//end

        public Object getQuestProgressByName (String param1 )
        {
            int _loc_2 = getQuestProgressIndexByName(param1 );
            return _loc_2 >= 0 ? this.m_activeQuests.get(_loc_2) : (null);
        }//end

        public void  setQuestTaskProgress (String param1 ,int param2 ,int param3 )
        {
            if (!QuestSettingsInit.isClientTestingEnabled)
            {
                return;
            }
            _loc_4 = this.getQuestProgressIndexByName(param1);
            _loc_5 = this.m_activeQuests.get(_loc_4) ;
            this.m_activeQuests.get(_loc_4).progress.put(param2,  param3);
            _loc_6 = QuestSettingsInit.getItemByName(param1);
            this.performClientProgressUpdate(_loc_6, _loc_5, {progressChange:true, taskCompleted:param2}, GlobalEngine.serverTime);
            return;
        }//end

        protected void  onTransactionAdded (TransactionEvent event )
        {
            Object _loc_4 =null ;
            Quest _loc_5 =null ;
            Object _loc_6 =null ;
            if (!QuestSettingsInit.isClientTestingEnabled)
            {
                return;
            }
            int _loc_2 = event.transaction.enqueueTime ;
            int _loc_3 =0;
            while (_loc_3 < this.m_activeQuests.size())
            {

                _loc_4 = this.m_activeQuests.get(_loc_3);
                _loc_5 = QuestSettingsInit.getItemByName(_loc_4.name);
                if (_loc_5 == null || _loc_4.complete || !_loc_5.isActivated())
                {
                }
                else
                {
                    _loc_6 = this.updateProgress(_loc_5, _loc_4, event.transaction);
                    if (_loc_6.progressChange == true || _loc_6.allTasksCompleted == true)
                    {
                        event.transaction.addQuestToHeader(_loc_4.name);
                    }
                    this.performClientProgressUpdate(_loc_5, _loc_4, _loc_6, _loc_2);
                }
                _loc_3++;
            }
            return;
        }//end

        protected void  performClientProgressUpdate (Quest param1 ,Object param2 ,Object param3 ,double param4 )
        {
            QuestEvent _loc_5 =null ;
            QuestEvent _loc_6 =null ;
            if (this.checkQuestCompletion(param2, param1))
            {
                param2.complete = true;
                _loc_5 = new QuestEvent(QuestEvent.COMPLETED, param1, param4);
                _loc_5.taskCompleted = param3.taskCompleted;
                dispatchEvent(_loc_5);
            }
            else if (param3.progressChange)
            {
                _loc_6 = new QuestEvent(QuestEvent.PROGRESS, param1, param4);
                _loc_6.taskCompleted = param3.taskCompleted;
                dispatchEvent(_loc_6);
            }
            return;
        }//end

        protected void  onTransactionComplete (TransactionEvent event )
        {
            int _loc_5 =0;
            Object _loc_6 =null ;
            Quest _loc_7 =null ;
            Array _loc_8 =null ;
            int _loc_9 =0;
            int _loc_10 =0;
            boolean _loc_11 =false ;
            QuestEvent _loc_12 =null ;
            int _loc_13 =0;
            QuestEvent _loc_14 =null ;
            _loc_2 =             !QuestSettingsInit.isClientTestingEnabled;
            Object _loc_3 =null ;
            if (event.transaction.rawResult)
            {
                if (event.transaction.rawResult.metadata == null)
                {
                    return;
                }
                _loc_3 = event.transaction.rawResult.metadata.QuestComponent;
            }
            _loc_4 = event.transaction.enqueueTime;
            if (_loc_3 != null)
            {
                _loc_5 = 0;
                while (_loc_5 < _loc_3.length())
                {

                    _loc_6 = _loc_3.get(_loc_5);
                    _loc_7 = QuestSettingsInit.getItemByName(_loc_6.name);
                    if (_loc_7 == null)
                    {
                    }
                    else
                    {
                        if (_loc_6.get("extraData"))
                        {
                            _loc_7.addExtraData(_loc_6.extraData);
                        }
                        if (_loc_6.get("activatedTime"))
                        {
                            _loc_7.setActivatedTime(_loc_6.activatedTime);
                        }
                        else if (_loc_7.autoActivate && !_loc_7.isActivated())
                        {
                            _loc_7.activate();
                        }
                        _loc_8 = _loc_3.get(_loc_5).purchased;
                        if (_loc_8)
                        {
                            _loc_10 = 0;
                            while (_loc_10 < _loc_8.length())
                            {

                                _loc_11 = _loc_8.get(_loc_10);
                                _loc_7.purchased.put(_loc_10,  _loc_11);
                                _loc_10++;
                            }
                        }
                        _loc_9 = -1;
                        if (_loc_6.expired == true)
                        {
                            dispatchEvent(new QuestEvent(QuestEvent.EXPIRED, _loc_7, _loc_4));
                        }
                        else if (_loc_6.complete == true && _loc_2)
                        {
                            _loc_9 = this.checkTaskCompletion(this.m_activeQuests.get(_loc_13).progress, _loc_6.progress, _loc_7);
                            _loc_12 = new QuestEvent(QuestEvent.COMPLETED, _loc_7, _loc_4);
                            _loc_12.taskCompleted = _loc_9;
                            dispatchEvent(_loc_12);
                        }
                        else
                        {
                            _loc_13 = this.getQuestProgressIndexByName(_loc_6.name);
                            if (_loc_13 < 0)
                            {
                                this.m_activeQuests.push(_loc_6);
                                if (_loc_6.get("isNew"))
                                {
                                    dispatchEvent(new QuestEvent(QuestEvent.STARTED, _loc_7, _loc_4));
                                }
                            }
                            if (_loc_2)
                            {
                                if (_loc_13 >= 0)
                                {
                                    _loc_9 = this.checkTaskCompletion(this.m_activeQuests.get(_loc_13).progress, _loc_6.progress, _loc_7);
                                    this.m_activeQuests.put(_loc_13,  _loc_6);
                                }
                                _loc_14 = new QuestEvent(QuestEvent.PROGRESS, _loc_7, _loc_4);
                                _loc_14.taskCompleted = _loc_9;
                                dispatchEvent(_loc_14);
                            }
                        }
                    }
                    _loc_5++;
                }
            }
            return;
        }//end

        protected int  getQuestProgressIndexByName (String param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_activeQuests.length())
            {

                if (this.m_activeQuests.get(_loc_2).name == param1)
                {
                    return _loc_2;
                }
                _loc_2++;
            }
            return -1;
        }//end

        protected int  checkTaskCompletion (Array param1 ,Array param2 ,Quest param3 )
        {
            int _loc_5 =0;
            int _loc_4 =0;
            while (_loc_4 < param3.tasks.length())
            {

                _loc_5 = param3.tasks.get(_loc_4).total;
                if (param1.get(_loc_4) != _loc_5 && param2.get(_loc_4) >= _loc_5)
                {
                    return _loc_4;
                }
                _loc_4++;
            }
            return -1;
        }//end

        protected boolean  checkQuestCompletion (Object param1 ,Quest param2 )
        {
            Object _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < param2.tasks.length())
            {

                _loc_4 = param2.tasks.get(_loc_3);
                if (param1.progress.get(_loc_3) < _loc_4.total)
                {
                    return false;
                }
                _loc_3++;
            }
            return true;
        }//end

        protected Object  updateProgress (Quest param1 ,Object param2 ,Transaction param3 )
        {
            Object task ;
            double value ;
            Function fn ;
            quest = param1;
            status = param2;
            transaction = param3;
            if (transaction == null || status == null || status.expired == true || status.complete == true)
            {
                return {};
            }
            Array newProgress =new Array(quest.tasks.length );
            boolean progressChange ;
            int taskCompleted ;
            int i ;
            while (i < quest.tasks.length())
            {

                task = quest.tasks.get(i);
                if (task.action == null)
                {
                }
                else
                {
                    value;
                    if (quest.purchased.get(i))
                    {
                        value = task.total;
                    }
                    else
                    {
                        try
                        {
                            fn = QuestSettingsInit.getClientTestingClass.get(task.action);
                            value = fn.apply(null, [transaction, task.type, quest.extraData]);
                        }
                        catch (err:Error)
                        {
                            trace(err);
                        }
                    }
                    switch(task.stickyType)
                    {
                        case StickyType.MAX:
                        {
                            newProgress.put(i,  this.getMaxProgress(value, status.progress.get(i)));
                            break;
                        }
                        case StickyType.ACCUM:
                        {
                            newProgress.put(i,  this.getAccumProgress(value, status.progress.get(i)));
                            break;
                        }
                        case StickyType.NONE:
                        {
                        }
                        default:
                        {
                            if (task.sticky == true)
                            {
                                newProgress.put(i,  this.getAccumProgress(value, status.progress.get(i)));
                            }
                            else
                            {
                                newProgress.put(i,  this.getNormalProgress(value));
                            }
                            break;
                            break;
                        }
                    }
                    if (newProgress.get(i) != status.progress.get(i))
                    {
                        progressChange;
                    }
                    if (status.progress.get(i) < task.total && newProgress.get(i) >= task.total)
                    {
                        taskCompleted = i;
                    }
                }
                i = (i + 1);
            }
            status.progress = newProgress;
            return {progressChange:progressChange, taskCompleted:taskCompleted, allTasksCompleted:value >= task.total};
        }//end

        private int  getNormalProgress (int param1 )
        {
            return param1;
        }//end

        private int  getMaxProgress (int param1 ,int param2 )
        {
            return Math.max(param1, param2);
        }//end

        private int  getAccumProgress (int param1 ,int param2 )
        {
            return param2 + param1;
        }//end

        public void  activateQuest (String param1 )
        {
            Object _loc_2 =null ;
            Quest _loc_3 =null ;
            for(int i0 = 0; i0 < this.m_activeQuests.size(); i0++)
            {
            	_loc_2 = this.m_activeQuests.get(i0);

                if (_loc_2.name == param1)
                {
                    _loc_3 = QuestSettingsInit.getItemByName(_loc_2.name);
                    _loc_3.activate();
                }
            }
            return;
        }//end

        public static QuestManager  getInstance ()
        {
            if (m_instance == null)
            {
                m_instance = new QuestManager;
            }
            return m_instance;
        }//end

    }




