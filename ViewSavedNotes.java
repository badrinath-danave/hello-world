package com.wordpress.learnwithpassion.notesandmemosapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSavedNotes extends Activity{
	private static final String DATABASE_TABLE = null;
	private static final String NOTE = null;
	ListView list1;
	TextView text1;
	ArrayList al=new ArrayList();
	final Context context=this;
	 Dialog dialog;
	ListView lv;
	DBAdaptor db;
	 Cursor c2;
	 
	 String deleteName="DELETE";
	 String updateName="UPDATE";
	 
	 String data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_saved_notes);
	
        //create an object of DBAdapter
         db=new DBAdaptor(this);
        lv=(ListView) findViewById(R.id.list1);
        al=new ArrayList();
        db.open();
        Cursor c1=db.getall();
        
        if(c1.moveToFirst())
    	{
    		do {
    			 data=c1.getString(0);
    			al.add(data);
    			} while (c1.moveToNext());
    	}
        
        /*if(c1 !=null)
        {
        	if(c1.moveToFirst())
        	{
        		do {
        			String data=c1.getString(0);
        			al.add(data);
        			} while (c1.moveToNext());
        	}
        }else
        {
        	TextView textView=(TextView) findViewById(R.id.text1);
        	textView.setText("Sorry You Have Not Created Any Notes.");
        }
        */
        lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, al));
       

        //register for context menu
        registerForContextMenu(lv);
        
        
 
        
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
        menu.setHeaderTitle("Select The Action");    
        menu.add(0, v.getId(), 0, updateName);//groupId, itemId, order, title   
        menu.add(0, v.getId(), 0, deleteName);  
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getTitle() ==updateName)
		{
				
				db.open();
		
				long id = db.UpdateContact(data);
				//Intent in=new Intent(ViewSavedNotes.this, CreateNewNotes.class);
				//startActivity(in); 
				
				
				
				db.close();
			
				
		}else if(item.getTitle() == deleteName)
		{
			
			
			/**
			 * List Item Index/position
			 * What is Title on position
			 * Delete that title from db 
			 */

			
			db.open();
			Boolean b = db.deleteContact(data);	// deletes data/record
			 if (b==false)
			 {
				 db.deleteContact(data);
				 Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_SHORT).show();
				 Intent in=new Intent(ViewSavedNotes.this, MainActivity.class);
				 startActivity(in);
						 
			 }
			 else
			 {	
				 db.deleteContact(data);
				 Toast.makeText(getApplicationContext(), "Note not found", Toast.LENGTH_SHORT).show();
				 Intent in=new Intent(ViewSavedNotes.this, MainActivity.class);
				 startActivity(in);
			 }
			db.close();
			
		}else
		{
			return false;
		}
		
		//return super.onContextItemSelected(item);
		return true;
		
		}
	
}
