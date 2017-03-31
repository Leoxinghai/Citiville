/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing;

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


/**
 * KeyStroke is a single key definition.
 * <p>
 * Thanks Romain for his Fever{@link http://fever.riaforge.org} accelerator framworks implementation, 
 * this is a simpler implementation study from his.
 * @author iiley
 */
public class KeyStroke implements KeyType{

	private String codeString ;
	private int code ;
	
	/**
	 * Create a KeyStroke with string and key code.
	 */
	public  KeyStroke (String description ,int code ){
		this.codeString = description;
		this.code = code;
	}
	
	public String  getDescription (){
		return codeString;
	}
	
	public Array  getCodeSequence (){
		return .get(code);
	}
	
	public int  getCode (){
		return code;
	}
	
	public String  toString (){
		return "Keyboard.get(" + getDescription + ")";
	}
	
	public static  KeyStroke VK_0 =new KeyStroke("0",48);
	public static  KeyStroke VK_1 =new KeyStroke("1",49);
	public static  KeyStroke VK_2 =new KeyStroke("2",50);
	public static  KeyStroke VK_3 =new KeyStroke("3",51);
	public static  KeyStroke VK_4 =new KeyStroke("4",52);
	public static  KeyStroke VK_5 =new KeyStroke("5",53);
	public static  KeyStroke VK_6 =new KeyStroke("6",54);
	public static  KeyStroke VK_7 =new KeyStroke("7",55);
	public static  KeyStroke VK_8 =new KeyStroke("8",56);
	public static  KeyStroke VK_9 =new KeyStroke("9",57);
	
	public static  KeyStroke VK_A =new KeyStroke("A",65);
	public static  KeyStroke VK_B =new KeyStroke("B",66);
	public static  KeyStroke VK_C =new KeyStroke("C",67);
	public static  KeyStroke VK_D =new KeyStroke("D",68);
	public static  KeyStroke VK_E =new KeyStroke("E",69);
	public static  KeyStroke VK_F =new KeyStroke("F",70);
	public static  KeyStroke VK_G =new KeyStroke("G",71);
	public static  KeyStroke VK_H =new KeyStroke("H",72);
	public static  KeyStroke VK_I =new KeyStroke("I",73);
	public static  KeyStroke VK_J =new KeyStroke("J",74);
	public static  KeyStroke VK_K =new KeyStroke("K",75);
	public static  KeyStroke VK_L =new KeyStroke("L",76);
	public static  KeyStroke VK_M =new KeyStroke("M",77);
	public static  KeyStroke VK_N =new KeyStroke("N",78);
	public static  KeyStroke VK_O =new KeyStroke("O",79);
	public static  KeyStroke VK_P =new KeyStroke("P",80);
	public static  KeyStroke VK_Q =new KeyStroke("Q",81);
	public static  KeyStroke VK_R =new KeyStroke("R",82);
	public static  KeyStroke VK_S =new KeyStroke("S",83);
	public static  KeyStroke VK_T =new KeyStroke("T",84);
	public static  KeyStroke VK_U =new KeyStroke("U",85);
	public static  KeyStroke VK_V =new KeyStroke("V",86);
	public static  KeyStroke VK_W =new KeyStroke("W",87);
	public static  KeyStroke VK_X =new KeyStroke("X",88);
	public static  KeyStroke VK_Y =new KeyStroke("Y",89);
	public static  KeyStroke VK_Z =new KeyStroke("Z",90);
	
	public static  KeyStroke VK_F1 =new KeyStroke("F1",112);
	public static  KeyStroke VK_F2 =new KeyStroke("F2",113);
	public static  KeyStroke VK_F3 =new KeyStroke("F3",114);
	public static  KeyStroke VK_F4 =new KeyStroke("F4",115);
	public static  KeyStroke VK_F5 =new KeyStroke("F5",116);
	public static  KeyStroke VK_F6 =new KeyStroke("F6",117);
	public static  KeyStroke VK_F7 =new KeyStroke("F7",118);
	public static  KeyStroke VK_F8 =new KeyStroke("F8",119);
	public static  KeyStroke VK_F9 =new KeyStroke("F9",120);
	public static  KeyStroke VK_F10 =new KeyStroke("F10",121);
	public static  KeyStroke VK_F11 =new KeyStroke("F11",122);
	public static  KeyStroke VK_F12 =new KeyStroke("F12",123);
	public static  KeyStroke VK_F13 =new KeyStroke("F13",124);
	public static  KeyStroke VK_F14 =new KeyStroke("F14",125);
	public static  KeyStroke VK_F15 =new KeyStroke("F15",126);
	
	public static  KeyStroke VK_NUMPAD_0 =new KeyStroke("Numpad_0",96);
	public static  KeyStroke VK_NUMPAD_1 =new KeyStroke("Numpad_1",97);
	public static  KeyStroke VK_NUMPAD_2 =new KeyStroke("Numpad_2",98);
	public static  KeyStroke VK_NUMPAD_3 =new KeyStroke("Numpad_3",99);
	public static  KeyStroke VK_NUMPAD_4 =new KeyStroke("Numpad_4",100);
	public static  KeyStroke VK_NUMPAD_5 =new KeyStroke("Numpad_5",101);
	public static  KeyStroke VK_NUMPAD_6 =new KeyStroke("Numpad_6",102);
	public static  KeyStroke VK_NUMPAD_7 =new KeyStroke("Numpad_7",103);
	public static  KeyStroke VK_NUMPAD_8 =new KeyStroke("Numpad_8",104);
	public static  KeyStroke VK_NUMPAD_9 =new KeyStroke("Numpad_9",105);
	public static  KeyStroke VK_NUMPAD_MULTIPLY =new KeyStroke("Numpad_Multiply",106);//*
	public static  KeyStroke VK_NUMPAD_ADD =new KeyStroke("Numpad_Add",107);//+
	public static  KeyStroke VK_NUMPAD_ENTER =new KeyStroke("Numpad_Enter",108);//Enter
	public static  KeyStroke VK_NUMPAD_SUBTRACT =new KeyStroke("Numpad_Subtract",109);//-
	public static  KeyStroke VK_NUMPAD_DECIMAL =new KeyStroke("Numpad_Decimal",110);//.
	public static  KeyStroke VK_NUMPAD_DIVIDE =new KeyStroke("Numpad_Divide",111);///
	
	
	public static  KeyStroke VK_BACKSPACE =new KeyStroke("Backspace",8);//backspace
	public static  KeyStroke VK_TAB =new KeyStroke("Tab",9);//tab
	public static  KeyStroke VK_ENTER =new KeyStroke("Enter",13);//main ENTER
	public static  KeyStroke VK_SHIFT =new KeyStroke("Shift",16);//shift
	public static  KeyStroke VK_CONTROL =new KeyStroke("Ctrl",17);//ctrl
	public static  KeyStroke VK_ESCAPE =new KeyStroke("Esc",27);//esc
	public static  KeyStroke VK_SPACE =new KeyStroke("Space",32);//space
	
	public static  KeyStroke VK_CAPS_LOCK =new KeyStroke("Cap",20);//caps lock
	public static  KeyStroke VK_NUM_LOCK =new KeyStroke("Num",144);//num lock
	public static  KeyStroke VK_SCROLL_LOCK =new KeyStroke("Scroll",145);//scroll lock
	
	public static  KeyStroke VK_PAUSE =new KeyStroke("Pause",19);//pause /break
	public static  KeyStroke VK_PAGE_UP =new KeyStroke("PageUp",33);//page up
	public static  KeyStroke VK_PAGE_DOWN =new KeyStroke("PageDown",34);//page down
	public static  KeyStroke VK_END =new KeyStroke("End",35);//end
	public static  KeyStroke VK_HOME =new KeyStroke("Home",36);//home
	public static  KeyStroke VK_INSERT =new KeyStroke("Insert",45);//insert
	public static  KeyStroke VK_DELETE =new KeyStroke("Delete",46);//delete
	
	public static  KeyStroke VK_LEFT =new KeyStroke("Left",37);//left arrow
	public static  KeyStroke VK_UP =new KeyStroke("Up",38);//up arrow
	public static  KeyStroke VK_RIGHT =new KeyStroke("Right",39);//right arrow
	public static  KeyStroke VK_DOWN =new KeyStroke("Down",40);//down arrow
	
	public static  KeyStroke VK_WINDOWS =new KeyStroke("Win",91);//windows
	public static  KeyStroke VK_MENU =new KeyStroke("Menu",93);//menu
	
}


