package com.cps731.group11.splits;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment {


    private ViewPager viewPager;
    private ListView lv;
    private ArrayAdapter<String> aa;
    private ArrayList<String> listViewItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String type = "activity";
        String currUser = ((MainActivity) getActivity()).getCurrentUser();
        ActivityBackgroundWorker abw = new ActivityBackgroundWorker();
        abw.execute(type, currUser);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);


        //sectionsPageAdapter = new SectionsPageAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.activityViewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.activity_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
     //   lv = (ListView) view.findViewById(R.id.activity_activities_dynamic);
//        lv.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listViewItems));


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new FPActivityFragment(),"Activities");
        adapter.addFragment(new HistoryActivityFragment(),"History");
        viewPager.setAdapter(adapter);

    }

    static class SectionsPageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public SectionsPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    public class ActivityBackgroundWorker extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String activity_url = "http://splits.atwebpages.com/activity.php";
            String resultString = "";
            switch (type) {
                case "activity":

                    String user_id1 = params[1];
                    try {
                        // Connection setup
                        URL url = new URL(activity_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        // Request to server
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String client_request = URLEncoder.encode("user_id1", "UTF-8") + "=" + URLEncoder.encode(user_id1, "UTF-8");
                        bufferedWriter.write(client_request);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        // Reply from server
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        ArrayList<String> server_reply = new ArrayList<String>();

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {

                            String temp = String.join(",", line);
                            String temp2 = temp.replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                            resultString = resultString.concat(temp2);
                            resultString = resultString.concat("\n");

                            server_reply.add(line);
                            server_reply.add("\n");
                        }
                        bufferedReader.close();
                        inputStream.close();

                        // Close connection
                        httpURLConnection.disconnect();


                        return resultString;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String server_reply) {
            super.onPostExecute(server_reply);
            if (server_reply.equals("error!")) {
                // Login Failed: Alert that login failedretrieve data.");
                alertDialog.show();
            } else {
                // Login Success:
                ArrayList<String> transArr = new ArrayList<String>();
                ArrayList<String> user1Arr = new ArrayList<String>();
                ArrayList<String> user2Arr = new ArrayList<String>();
                ArrayList<String> typeArr = new ArrayList<String>();
                ArrayList<String> descArr = new ArrayList<String>();
                ArrayList<String> amtArr = new ArrayList<String>();
                ArrayList<String> dateArr = new ArrayList<String>();
                String lines[] = server_reply.split("\\r?\\n");
                for (int i = 0; i < lines.length; i++) {
                    String[] transRow = lines[i].split(",");
                    transArr.add(transRow[0]);
                    user1Arr.add(transRow[1]);
                    user2Arr.add(transRow[2]);
                    typeArr.add(transRow[3]);
                    descArr.add(transRow[4]);
                    amtArr.add(transRow[5]);
                    dateArr.add(transRow[6]);
                }

                // USE THIS TO TRANSFER DATA. SET DATA TO PARAM. THIS METHOD IS IN ActivityFragment.java
                //((FPActivityFragment) fragment).transferData(transArr, user1Arr, user2Arr, typeArr, descArr, amtArr, dateArr)
                for (int i = 0; i < transArr.size(); i++) {
                    String listViewItem = "You " + typeArr.get(i) + " " + amtArr.get(i) + " with " + user2Arr.get(i) + " for " + descArr.get(i) + " on " + dateArr.get(i) + ".";
//                    listViewItems.add(listViewItem);

                }

            }
        }
    }
}