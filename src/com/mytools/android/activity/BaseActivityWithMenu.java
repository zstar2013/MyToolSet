package com.mytools.android.activity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivityWithMenu extends BaseActivity {
	 
    private static final int MENU_FIRST = Menu.FIRST;
    private static final int MENU_2 = Menu.FIRST + 1;
    private static final int MENU_3 = Menu.FIRST + 2;
 
    @Override
    public boolean onCreateOptionsMenu(final Menu pMenu) {
       pMenu.add(Menu.NONE, MENU_FIRST, Menu.NONE, "1");
       pMenu.add(Menu.NONE, MENU_2, Menu.NONE, "2");
       pMenu.add(Menu.NONE, MENU_3, Menu.NONE, "3");
       return super.onCreateOptionsMenu(pMenu);
    }
 
    @Override
    public boolean onPrepareOptionsMenu(final Menu pMenu) {
    //准备menu菜单，根据不同的情况更改menu菜单上的标题   //pMenu.findItem(MENU_TRACE).setTitle(this.mEngine.isMethodTracing() ? "Stop Method Tracing" : "Start Method Tracing");
       return super.onPrepareOptionsMenu(pMenu);
    }
 
    @Override
    public boolean onMenuItemSelected(final int pFeatureId, final MenuItem pItem) {
       switch(pItem.getItemId()) {
           case MENU_FIRST:
              Toast.makeText(this, "menu 1", Toast.LENGTH_SHORT).show();
              return true;
           case MENU_2:
              Toast.makeText(this, "menu 2", Toast.LENGTH_SHORT).show();
              return true;
           case MENU_3:
              Toast.makeText(this, "menu 3", Toast.LENGTH_SHORT).show();
              return true;
           default:
              return super.onMenuItemSelected(pFeatureId, pItem);
       }
    }
}
