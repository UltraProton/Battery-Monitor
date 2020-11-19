package com.example.batterycheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
    String TAG = "Boom Boom";

    MyAdapter myAdapter;
    RecyclerView recyclerView;
    //private AppAdapter power_appAdapter;
    ListView power_apps_view;
    List<ActivityManager.RunningAppProcessInfo> processes;
    ActivityManager amg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //power_apps_view = (ListView) findViewById(R.id.app_list);

        myAdapter= new MyAdapter(this);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        //attach the adapter to the recycler view
        recyclerView.setAdapter(myAdapter);
        //Set which layout you want to see in the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        power_appAdapter = new AppAdapter(MainActivity.this, apps_list);
//        power_apps_view.setAdapter(power_appAdapter);
//        power_apps_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView,  View view, final int i, long l) {
//                ActivityManager mActivityManager = (ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
//                String[] colors = {" Open "+apps_list.get(i).getName(), " App Info", "Kill App"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder
//                        .setItems(colors, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // The 'which' argument contains the index position of the selected item
//                                if (which==0){
//                                    Intent intent = getPackageManager().getLaunchIntentForPackage(apps_list.get(i).packages);
//                                    if(intent != null){
//                                        startActivity(intent);
//                                    }
//                                    else {
//                                        Toast.makeText(MainActivity.this, apps_list.get(i).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                                if (which==1){
//                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                    intent.setData(Uri.parse("package:" + apps_list.get(i).packages));
////                                    Toast.makeText(MainActivity.this, apps_list.get(i).getName() , Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
//                                }
//                                if(which==2)
//                                {
//                                    mActivityManager.killBackgroundProcesses(apps_list.get(i).getPackages());
//                                    Log.d(TAG, "onClick: "+apps_list.get(i).getPackages());
//                                    Toast.makeText(getApplicationContext(), apps_list.get(i).getName()+" killed",Toast.LENGTH_LONG).show();
//
//                                }
//                            }
//                        });
//                builder.show();
//            }
//        });

        //Total Number of Installed-Apps(i.e. List Size)
//        String  abc = power_apps_view.getCount()+"";
//        TextView countApps = (TextView)findViewById(R.id.countApps);
//        countApps.setText("Total Installed Apps: "+abc);
//        Toast.makeText(this, abc+" Apps", Toast.LENGTH_SHORT).show();

    }

    //Get all the apps apart from system apps
//
//    public class AppAdapter extends BaseAdapter {
//
//        public LayoutInflater layoutInflater;
//        public List<app_data> listStorage;
//
//        public AppAdapter(Context context, List<app_data> customizedListView) {
//            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            listStorage = customizedListView;
//
//        }
//
//        @Override
//        public int getCount() {
//            return listStorage.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return listStorage.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        //We need to overload the getViewTypeCount() and getItemViewType() to remove the multiple
//        //colors in multiple item in the list view
//        @Override
//        public int getViewTypeCount() {
//            return getCount();
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            //int mColor= 0xFF000000;
//            ViewHolder listViewHolder;
//            if(convertView == null){
//                listViewHolder = new ViewHolder();
//                convertView = layoutInflater.inflate(R.layout.app_list, parent, false);
//                listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.app_name);
//                listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
//                listViewHolder.packageInListView=(TextView)convertView.findViewById(R.id.app_package_name);
//                listViewHolder.layout= (LinearLayout) convertView.findViewById(R.id.layout);
//                convertView.setTag(listViewHolder);
//            }
//            else{
//                listViewHolder = (ViewHolder)convertView.getTag();
//            }
//
//            listViewHolder.textInListView.setText(listStorage.get(position).getName());
//            Log.d(TAG, "getView: "+ listStorage.get(position).getName());
//            String x=listStorage.get(position).getName();
//
//            if(x.equals("Asynctask"))
//            {
//                //listViewHolder.layout.setBackgroundColor();
//                listViewHolder.layout.setBackgroundColor(Color.parseColor("#EF5350"));
//            }
//            //Log.i("itemmm",""+getItem(position).toString());
//            //if(getItem(position))
//            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
//            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());
//
//            return convertView;
//        }
//
//        class ViewHolder{
//            TextView textInListView;
//            ImageView imageInListView;
//            TextView packageInListView;
//            LinearLayout layout;
//        }
//    }


//        public String getA(){
//            return a;
//        }
//        public int getD() {
//            return d;
//        }

}

