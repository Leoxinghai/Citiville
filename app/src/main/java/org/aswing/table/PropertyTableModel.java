package org.aswing.table;

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

import org.aswing.ListModel;
import org.aswing.event.ListDataEvent;
import org.aswing.event.ListDataListener;

/**
 * The table model return the properties of a row to be column data.
 * <p>
 * PropertyTableModel is very conveniently to use when your table data can be stored in a list
 * and each columns can be a property of a object of a row.
 * <br/>
 * For example, you can a data like this:<br/>
 * <pre>
 * data =
 *  [{name:"iiley", sex:1, age:26},
 *  {name:"Comeny", sex:0, age:24},
 *  {name:"Tom", sex:1, age:30},
 *  {name:"Lita", sex:0, age:16}
 *  ];
 * </pre>
 * Woool, it is very suit for PropertyTableModel to provide data to a JTable to view the datas.
 * You can create your JTable like this:
 * <pre>
 *VectorListModel dataList =new VectorListModel ();
 * dataList.appendAll(data);
 *PropertyTableModel tableModel =new PropertyTableModel(
 * 		dataList,
 * 		.get("Guy's Name", "Sex", "Age"),
 * 		.get("name", "sex", "age"),
 * 		.get(null, new SexTranslator(), null)
 * );
 *JTable table =new JTable(tableModel );
 * </pre>
 * Then the table will render a table for each object each properties like this.
 * <pre>
 * -------------------------------
 * | Guy's Name |  Sex   |  Age  |
 * |------------------------------
 * | iiley      |  male  |  26   |
 * |------------------------------
 * | Comeny     | female |  24   |
 * |------------------------------
 * | Tom        |  male  |  30   |
 * |------------------------------
 * | Lita       | female |  16   |
 * -------------------------------
 * </pre>
 * </p>
 *
 * @author iiley
 */
public class PropertyTableModel extends AbstractTableModel implements ListDataListener{

	protected ListModel list ;
	protected Array names ;
	protected Array properties ;
	protected Array translators ;
	protected Array columnsEditable ;

	/**
	 * Create a Property table model, column headers, properties names, and translators.
	 * @param listModel the list model that contains the row objects.
	 * @param names column header labels.
	 * @param properties property names for column values, "." means returns row data object directly.
	 * @param translators the translators for each column, a null translator for a columns means return the property
	 * of that name directly. translator can be a PropertyTranslator instance or a Function(info:*, key:String):*
	 */
	public  PropertyTableModel (ListModel listModel ,Array names ,Array properties ,Array translators ){
		super();
		this.setList(listModel);
		this.names = names.concat();
		this.properties = properties.concat();
		this.translators = translators.concat();
		columnsEditable = new Array();
	}

	/**
	 * Sets the row data provider, a list model.
	 * @param listModel the row object datas.
	 */
	public void  setList (ListModel listModel ){
		if(list != null){
			list.removeListDataListener(this);
		}
		list = listModel;
		if(list != null){
			list.addListDataListener(this);
		}
		fireTableDataChanged();
	}

	/**
	 * Returns the row data provider, a list model.
	 * @returns the row data provider.
	 */
	public ListModel  getList (){
		return list;
	}

	 public int  getRowCount (){
		if(list){
			return list.getSize();
		}else{
			return 0;
		}
	}

	 public int  getColumnCount (){
		return names.length;
	}

	/**
	 * Returns the translated value for specified row and column.
	 * @return the translated value for specified row and column.
	 */
	 public Object getValueAt (int rowIndex ,int columnIndex ) {
		translator = translators.get(columnIndex);
		info = list.getElementAt(rowIndex);
		String key =properties.get(columnIndex) ;
		if(translator != null){
			if(translator is PropertyTranslator){
				return PropertyTranslator(translator).translate(info, key);
			}else if(translator is Function){
				return translator(info, key);
			}else{
				throw new Error("Translator must be a PropertyTranslator or a Function : " + translator);
			}
		}else{
			if(key == "."){
				return info;
			}
			return info.get(key);
		}
	}

	/**
	 * Returns the column name for specified column.
	 */
	 public String  getColumnName (int column ){
		return names.get(column);
	}

	/**
	 * Returns is the row column editable, default is true.
	 *
	 * @param   row			 the row whose value is to be queried
	 * @param   column		  the column whose value is to be queried
	 * @return				  is the row column editable, default is true.
	 * @see #setValueAt()
	 * @see #setCellEditable()
	 * @see #setAllCellEditable()
	 */
	 public boolean  isCellEditable (int row ,int column ){
		if(columnsEditable.get(column) == undefined){
			return true;
		}else{
			return columnsEditable.get(column) == true;
		}
	}

	/**
	 * Returns is the column editable, default is true.
	 *
	 * @param   column		  the column whose value is to be queried
	 * @return				  is the column editable, default is true.
	 * @see #setValueAt()
	 * @see #setCellEditable()
	 * @see #setAllCellEditable()
	 */
	public boolean  isColumnEditable (int column ){
		return isCellEditable(0, column);
	}

	/**
	 * Sets spcecifed column editable or not.
	 * @param column the column whose value is to be queried
	 * @param editable editable or not
	 */
	public void  setColumnEditable (int column ,boolean editable ){
		columnsEditable.put(column,  editable);
	}

	/**
	 * Sets all cells editable or not.
	 * @param editable editable or not
	 */
	public void  setAllCellEditable (boolean editable ){
		for(int i =getColumnCount ()-1;i >=0;i --){
			columnsEditable.put(i,  editable);
		}
	}

	 public void  setValueAt (*aValue ,int rowIndex ,int columnIndex ){
		info = list.getElementAt(rowIndex);
		String key =properties.get(columnIndex) ;
		info.put(key,  aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	//__________Listeners for List Model, to keep table view updated when row objects changed__________

	public void  intervalAdded (ListDataEvent e ){
		fireTableRowsInserted(e.getIndex0(), e.getIndex1());
	}

	public void  intervalRemoved (ListDataEvent e ){
		fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
	}

	public void  contentsChanged (ListDataEvent e ){
		fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
	}
}


