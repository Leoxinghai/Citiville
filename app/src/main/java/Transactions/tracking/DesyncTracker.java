package Transactions.tracking;

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

import Engine.Managers.*;
import Engine.Transactions.*;
import Modules.stats.experiments.*;
import Transactions.*;

//import flash.utils.*;

    public class DesyncTracker
    {
        private Dictionary m_pendingTransactions ;
        private Vector<TransactionData> m_completedTransactions;
        private int m_transactionCount =0;
        private Vector<String> m_recentTransactions;
        private Dictionary m_transactionServiceMap ;
        private static  int MAX_COMPLETED_TRANSACTIONS =20;
        private static  int MAX_RECENT_TRANSACTIONS =10;

        public  DesyncTracker ()
        {
            this.m_pendingTransactions = new Dictionary();
            this.m_completedTransactions = new Vector<TransactionData>();
            this.m_recentTransactions = new Vector<String>();
            this.m_transactionServiceMap = new Dictionary();
            return;
        }//end

        public void  onAdd (Transaction param1 )
        {
            if (!this.isEnabled())
            {
                return;
            }
            while (this.m_recentTransactions.length >= MAX_RECENT_TRANSACTIONS)
            {

                this.m_recentTransactions.shift();
            }
            this.m_recentTransactions.push(this.generateTransactionKey(param1));
            TransactionData _loc_2 =new TransactionData ();
            _loc_2.transaction = param1;
            _loc_2.initialClientState = ResourceState.getClientState();
            _loc_2.recentTransactions = this.m_recentTransactions.slice();
            this.m_pendingTransactions.put(param1.getId(),  _loc_2);
            return;
        }//end

        public void  onComplete (Transaction param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            if (!this.isEnabled())
            {
                return;
            }
            _loc_2 = this.m_pendingTransactions.get(param1.getId ()) ;
            if (_loc_2 != null)
            {
                this.m_transactionCount++;
                _loc_3 = this.generateTransactionKey(param1);
                _loc_4 = param1.rawResult.get("service");
                if (this.m_transactionServiceMap.get(_loc_3) !== undefined)
                {
                    if (this.m_transactionServiceMap.get(_loc_3) !== _loc_4)
                    {
                        throw new Error("Transaction -> Service mapping collision");
                    }
                }
                else
                {
                    this.m_transactionServiceMap.put(_loc_3,  _loc_4);
                }
                _loc_2.finalClientState = ResourceState.getClientState();
                _loc_2.initialServerState = ResourceState.getInitialServerState(param1);
                _loc_2.finalServerState = ResourceState.getFinalServerState(param1);
                while (this.m_completedTransactions.length >= MAX_COMPLETED_TRANSACTIONS)
                {

                    this.m_completedTransactions.shift();
                }
                this.m_completedTransactions.push(_loc_2);
                delete this.m_pendingTransactions.get(param1.getId());
                this.checkForDesync(param1);
            }
            return;
        }//end

        public void  onFault (Transaction param1 )
        {
            if (!this.isEnabled())
            {
                return;
            }
            _loc_2 = this.m_pendingTransactions.get(param1.getId ()) ;
            if (_loc_2 != null)
            {
                delete this.m_pendingTransactions.get(param1.getId());
            }
            return;
        }//end

        private void  checkForDesync (Transaction param1 )
        {
            String _loc_7 =null ;
            String _loc_8 =null ;
            _loc_2 = this.m_completedTransactions.get((this.m_completedTransactions.length -1)) ;
            _loc_3 = param1.rawResult.get( "actionCount") ;
            if (this.m_transactionCount != _loc_3)
            {
                this.logDesync(param1);
                return;
            }
            _loc_4 = param1.rawResult.get( "recentActions") ;
            _loc_5 = param1.rawResult.get( "recentActions").length -1;
            _loc_6 = _loc_2.recentTransactions.length -1;
            while (_loc_5 >= 0 && _loc_6 >= 0)
            {

                _loc_7 = _loc_4.get(_loc_5);
                _loc_8 = this.m_transactionServiceMap.get(_loc_2.recentTransactions.get(_loc_6));
                if (_loc_7 != _loc_8)
                {
                    this.logDesync(param1);
                    return;
                }
                _loc_5 = _loc_5 - 1;
                _loc_6 = _loc_6 - 1;
            }
            return;
        }//end

        private void  logDesync (Transaction param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            _loc_2 = this.m_completedTransactions.get((this.m_completedTransactions.length -1)) ;
            StatsManager.count("server_tranasction_oos", param1.rawResult.get("actionCount"));
            for(int i0 = 0; i0 < param1.rawResult.get("recentActions").size(); i0++)
            {
            		_loc_3 = param1.rawResult.get("recentActions").get(i0);

                StatsManager.count("server_tranasction_oos", _loc_3);
            }
            StatsManager.count("client_tranasction_oos", this.m_transactionCount.toString());
            for(int i0 = 0; i0 < _loc_2.recentTransactions.size(); i0++)
            {
            		_loc_4 = _loc_2.recentTransactions.get(i0);

                StatsManager.count("client_tranasction_oos", this.m_transactionServiceMap.get(_loc_4));
            }
            return;
        }//end

        private String  generateTransactionKey (Transaction param1 )
        {
            TWorldState _loc_3 =null ;
            _loc_2 = getQualifiedClassName(param1);
            if (param1 instanceof TWorldState)
            {
                _loc_3 =(TWorldState) param1;
                _loc_2 = _loc_2 + ("(" + getQualifiedClassName(_loc_3.getWorldObject()) + ")");
            }
            return _loc_2;
        }//end

        private boolean  isEnabled ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DOOBER_TRACKING );
            return _loc_1 ? (true) : (false);
        }//end

    }

import Engine.Managers.*;
import Engine.Transactions.*;
import Modules.stats.experiments.*;
import Transactions.*;

import flash.utils.*;
import Transactions.tracking.*;

class TransactionData
    public Transaction transaction ;
    public Vector<String> recentTransactions;
    public ResourceState initialClientState ;
    public ResourceState finalClientState ;
    public ResourceState initialServerState ;
    public ResourceState finalServerState ;

     TransactionData ()
    {
        return;
    }//end





