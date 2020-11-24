package com.example.batterycheck;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = "Boom";
    ArrayList<app_data> apps_list;
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    ListView power_apps_view;
    List<ActivityManager.RunningAppProcessInfo> processes;
    ActivityManager amg;

    TextView battery_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apps_list=get_apps_list();
        myAdapter= new MyAdapter(this,apps_list);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        //attach the adapter to the recycler view
        recyclerView.setAdapter(myAdapter);
        //Set which layout you want to see in the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        battery_txt = (TextView) findViewById(R.id.battery_lvl);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextView app_name = (TextView) findViewById(R.id.app_name);
                Log.i("App name: ",""+apps_list.get(position).name);

                ActivityManager mActivityManager = (ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
                String[] colors = {" Open "+apps_list.get(position).getName(), " App Info", "Kill App"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder
                        .setItems(colors, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                if (which==0){
                                    Intent intent = getPackageManager().getLaunchIntentForPackage(apps_list.get(position).getPackages());
                                    if(intent != null){
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, apps_list.get(position).getPackages() + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (which==1){
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + apps_list.get(position).packages));
//                                    Toast.makeText(MainActivity.this, apps_list.get(i).getName() , Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                if(which==2)
                                {
                                    mActivityManager.killBackgroundProcesses(apps_list.get(position).getPackages());
                                    Log.d(TAG, "onClick: "+apps_list.get(position).getPackages());
                                    Toast.makeText(getApplicationContext(), apps_list.get(position).getName()+" killed",Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                builder.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context,Intent intent) {
            int bat_level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int bat = intent.getIntExtra(BatteryManager.ACTION_CHARGING, 0);
            //            Toast.makeText(getApplicationContext(), "Batt"+bat_level, Toast.LENGTH_LONG).show();
            battery_txt.setText("Batktery level: "+String.valueOf(bat_level) + String.valueOf(bat));
        }
    };

    public class app_data {
        private String name;
        Drawable icon;
        private String packages;

        public app_data(String name, Drawable icon, String packages) {   //}, String a, int d) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;

        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackages() {
            return packages;
        }
    }

    private ArrayList<app_data> get_apps_list() {
        PackageManager pm = this.getPackageManager();
        ArrayList<app_data> apps = new ArrayList<app_data>();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((notSystemApp(p))) {
                String appName = p.applicationInfo.loadLabel(pm).toString();
                Drawable icon = p.applicationInfo.loadIcon(pm);
                String packages = p.applicationInfo.packageName;
                apps.add(new app_data(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean notSystemApp(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
    }
}





