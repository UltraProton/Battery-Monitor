package com.example.batterycheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Dictionary;
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
    String TAG = "Boom Boom";
    private List<AppList> powerApps;
    private AppAdapter power_appAdapter;
    ListView power_apps_view;
    List<ActivityManager.RunningAppProcessInfo> processes;
    ActivityManager amg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        power_apps_view = (ListView) findViewById(R.id.app_list);

        powerApps = getpowerApps();
        power_appAdapter = new AppAdapter(MainActivity.this, powerApps);
        power_apps_view.setAdapter(power_appAdapter);
        power_apps_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,  View view, final int i, long l) {
                ActivityManager mActivityManager = (ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
                String[] colors = {" Open "+powerApps.get(i).getName(), " App Info", "Kill App"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder
                        .setItems(colors, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                if (which==0){
                                    Intent intent = getPackageManager().getLaunchIntentForPackage(powerApps.get(i).packages);
                                    if(intent != null){
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, powerApps.get(i).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (which==1){
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + powerApps.get(i).packages));
//                                    Toast.makeText(MainActivity.this, powerApps.get(i).getName() , Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                if(which==2)
                                {
                                    mActivityManager.killBackgroundProcesses(powerApps.get(i).getPackages());
                                    Log.d(TAG, "onClick: "+powerApps.get(i).getPackages());
                                    Toast.makeText(getApplicationContext(), powerApps.get(i).getName()+" killed",Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                builder.show();
            }
        });

        //Total Number of Installed-Apps(i.e. List Size)
//        String  abc = power_apps_view.getCount()+"";
//        TextView countApps = (TextView)findViewById(R.id.countApps);
//        countApps.setText("Total Installed Apps: "+abc);
//        Toast.makeText(this, abc+" Apps", Toast.LENGTH_SHORT).show();

    }

    private List<AppList> getpowerApps() {

        //Code from Utsav and Suraj
        PackageManager pm = getPackageManager();

        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((notSystemApp(p))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new AppList(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean notSystemApp(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
    }

    public class AppAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;

        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;

        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return listStorage.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //We need to overload the getViewTypeCount() and getItemViewType() to remove the multiple
        //colors in multiple item in the list view
        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int mColor= 0xFF000000;
//            GradientDrawable gradientDrawable = (GradientDrawable) convertView.getBackground();
            ViewHolder listViewHolder;
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.app_list, parent, false);
                listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView=(TextView)convertView.findViewById(R.id.app_package);
                listViewHolder.layout= (LinearLayout) convertView.findViewById(R.id.layout);
                convertView.setTag(listViewHolder);
            }
            else{
                listViewHolder = (ViewHolder)convertView.getTag();
            }

            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            Log.d(TAG, "getView: "+ listStorage.get(position).getName());
            String x=listStorage.get(position).getName();

            if(x.equals("Asynctask"))
            {
                listViewHolder.layout.setBackgroundColor(mColor);
            }
            //Log.i("itemmm",""+getItem(position).toString());
            //if(getItem(position))
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            return convertView;
        }

        class ViewHolder{
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
            LinearLayout layout;
        }
    }

    public class AppList {
        private String name;
        Drawable icon;
        private String packages;
//        private String a; //declare more parameters
//        private int d; //declare more parameters
        public AppList(String name, Drawable icon, String packages){   //}, String a, int d) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
//            this.a = a;
//            this.d = d;
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
//        public String getA(){
//            return a;
//        }
//        public int getD() {
//            return d;
//        }
    }
}

