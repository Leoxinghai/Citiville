package Display.aswingui;

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
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.zynga.*;

    public class CustomButton extends JButton
    {
        private double origX ;
        private double origY ;
        private boolean bPosSet =false ;
        private String uiClassName ="Button.";

        public  CustomButton (String param1 ="",Icon param2 ,String param3 ="Button.")
        {
            this.uiClassName = param3;
            super(param1, param2);
            this.setCustomBackground(param3);
            return;
        }//end

         public String  getUIClassID ()
        {
            return this.uiClassName;
        }//end

         public void  setUIClassID (String param1 )
        {
            super.setUIClassID(param1);
            this.uiClassName = param1;
            this.setCustomBackground(this.uiClassName);
            return;
        }//end

        private void  setVars (AWEvent event )
        {
            if (!this.bPosSet)
            {
                this.origX = this.getLocation().x;
                this.origY = this.getLocation().y;
            }
            this.bPosSet = true;
            return;
        }//end

         public void  setEnabled (boolean param1 )
        {
            _loc_2 = this.isEnabled ();
            if (!param1 && _loc_2)
            {
                this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_grey_", Global.runspace.getApplicationDomain()));
            }
            if (param1 && !_loc_2)
            {
                this.setCustomBackground(this.uiClassName);
            }
            super.setEnabled(param1);
            return;
        }//end

        private void  setCustomBackground (String param1 )
        {
            switch(param1)
            {
                case "AddCoinsSaleButtonUI":
                {
                    this.setBackgroundDecorator(new CustomButtonBackground(EmbeddedArt.addcoinssale_up, EmbeddedArt.addcoinssale_over));
                    break;
                }
                case "AddCoinsButtonUI":
                {
                    this.setBackgroundDecorator(new CustomButtonBackground(EmbeddedArt.addcoins_up, EmbeddedArt.addcoins_over));
                    break;
                }
                case "FreeGiftSaleButtonUI":
                {
                    this.setBackgroundDecorator(new CustomButtonBackground(EmbeddedArt.freeGiftOffer_up, EmbeddedArt.freeGiftOffer_over));
                    break;
                }
                case "CashTimeEnergyButtonUI":
                {
                    this.setBackgroundDecorator(new CustomButtonBackground(EmbeddedArt.cash_time_energy_off_up, EmbeddedArt.cash_time_energy_off_over));
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (Global.runspace == null)
            {
                return;
            }
            switch(param1)
            {
                case "AskForKeyButtonUI":
                case "BigGreenButtonUI":
                case "CoinsSmallButtonUI":
                case "AskForKeyButtonUI":
                case "CoinsButtonUI":
                case "GreenSmallButtonUI":
                case "GreenButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_green_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "GreyButtonUI":
                case "CustomButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_grey_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "BlueSmallButtonUI":
                case "BigCashButtonUI":
                case "CashSmallButtonUI":
                case "CashButtonUI":
                case "BuyGateKeyButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_blue_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "RedButtonUI":
                case "RedSmallButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_red_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "OrangeSmallButtonUI":
                case "OrangeButtonUI":
                case "OrangeMediumButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_orange_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "PinkButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_pink_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "PurpleButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_purple_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "GreenWhiteButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_green_white_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "MinusButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_minus_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "PlusButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_plus_", Global.runspace.getApplicationDomain()));
                    break;
                }
                case "MarketSortButtonUI":
                {
                    this.setBackgroundDecorator(new AssetButtonBackground("ButtonAssets_market2Sort_", Global.runspace.getApplicationDomain()));
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

    }


