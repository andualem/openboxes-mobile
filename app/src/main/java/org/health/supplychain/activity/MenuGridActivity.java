package org.health.supplychain.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.health.supplychain.R;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.utility.FileManager;
import org.health.supplychain.view.GridviewAdapter;

import java.util.ArrayList;


public class MenuGridActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
	
	private GridviewAdapter mAdapter;
	private ArrayList<String> menuList;
	private ArrayList<Integer> listIcon;
	private GridView gridView;
	private AlertDialog mAlertDialog;
	private static boolean EXIT = true;
	private TextView tvUserFullName, tvUserRole, tvUserOrganization;
	private DrawerLayout drawerLayout;

	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
//		menu.removeItem(0);
//		menu.add(0, 0, 0, getString((R.string.change_language)));
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case 0:
//        		Intent preferenceIntent = new Intent(getApplicationContext(), PreferencesActivity.class);
//        		startActivity(preferenceIntent);
////        		finish();
        }
        	return true;
    }
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: enable this part when SD card setting is done
//        try {
//     			FileManager.createDirectories();
//     		} catch (RuntimeException e) {
//     			createErrorDialog(e.getMessage(), EXIT);
//     			return;
//     		}
        
        
        setContentView(R.layout.main_menu_drawer_layout);
        
        setTitle(getString(R.string.app_name) + " > "
				+ getString(R.string.app_detail));
        
        prepareList();
        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(MenuGridActivity.this,menuList, listIcon);
        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.menuGridView);
        gridView.setAdapter(mAdapter);
		drawerLayout = findViewById(R.id.drawer_layout);
        gridView.setOnItemClickListener(getMenuItemClickListener());
		prepareNavigationMenu();
    }

	private OnItemClickListener getMenuItemClickListener() {
		return new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long arg3) {

                Intent intent;
                switch(position)
                {
                case 0:
                    intent = new Intent(getApplicationContext(), TransactionSubMenu.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(), StockKeepingSubMenu.class);
//					finish();
                    startActivity(intent);
                    break;
                case 2:
//					intent = new Intent(getApplicationContext(), ReadEntitlementActivity.class);
////					finish();
//					startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getApplicationContext(), UtilitySubMenu.class);
//					finish();
                    startActivity(intent);

//					Intent preferenceIntent = new Intent(getApplicationContext(), UtilitiesSubMenu.class);
//	        		startActivity(preferenceIntent);

                    break;
                default:
                        break;
                }
//				Toast.makeText(MenuGridActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        };
	}

	public void prepareList()
    {
    	  menuList = new ArrayList<String>();
    	  
    	  menuList.add(getString(R.string.transaction));
    	  menuList.add(getString(R.string.stock_keeping));
          menuList.add(getString(R.string.synchronization));
          menuList.add(getString(R.string.utilities));

          listIcon = new ArrayList<Integer>();
          listIcon.add(R.drawable.transaction);
          listIcon.add(R.drawable.stock);
          listIcon.add(R.drawable.synchronization);
          listIcon.add(R.drawable.utility);
    }
    
    private void createErrorDialog(String errorMsg, final boolean shouldExit) {
		mAlertDialog = new AlertDialog.Builder(this).create();
		mAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
		mAlertDialog.setMessage(errorMsg);
		DialogInterface.OnClickListener errorListener = new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int i) {
				switch (i) {
				case DialogInterface.BUTTON1:
					
					if (shouldExit) {
						finish();
					}
					break;
				}
			}
		};
		mAlertDialog.setCancelable(false);
		mAlertDialog.setButton(getString(R.string.ok), errorListener);
		mAlertDialog.show();
	}


	private void prepareNavigationMenu(){
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						// set item as selected to persist highlight
						menuItem.setChecked(true);
						drawerLayout.closeDrawers();

						Intent intent;
						switch (menuItem.getItemId()){
							case R.id.nav_sync:
//								intent = new Intent(getApplicationContext(), ReGroupedSyncActivity.class);
//								startActivity(intent);
								break;
							case R.id.nav_report:
//								intent = new Intent(getApplicationContext(), ReportForAgentActivity.class);
//								startActivity(intent);
								break;
							case R.id.nav_change_password:
//								intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
//								startActivity(intent);
								break;
							case R.id.nav_about:
//								intent = new Intent(getApplicationContext(), AboutActivity.class);
//								startActivity(intent);
								break;
							case R.id.nav_logout:
								UserSessionService userSessionService = new UserSessionService();
								userSessionService.logoutUser(MenuGridActivity.this);
								finish();
							default:
								break;
						}

						return true;
					}
				});

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer_white);

		View header = navigationView.getHeaderView(0);
		tvUserFullName = (TextView) header.findViewById(R.id.userFullName);
		tvUserRole = (TextView) header.findViewById(R.id.userRole);
		tvUserOrganization = (TextView) header.findViewById(R.id.userOrganization);

		// ------------------------ End of navigation drawer
	}

}