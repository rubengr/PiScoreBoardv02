package estg.mee.piscoreboard.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;
//import android.app.ListFragment;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Iterator;

import estg.mee.piscoreboard.R;
import estg.mee.piscoreboard.customlistview.EntryItem;
import estg.mee.piscoreboard.model.Game;
import estg.mee.piscoreboard.model.PiScoreBoard;
import estg.mee.piscoreboard.model.Team;
import estg.mee.piscoreboard.model.Game;
import estg.mee.piscoreboard.model.Graphics;
import estg.mee.piscoreboard.utils.ClientSendThread;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment objFragment = null;
    public static Graphics graphics;

    private static final int REQUEST_IMAGE = 2;

//    private static ArrayList<String> mSelectPath = null;

   // public Game jogo;

    public static String newTeamPath;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;

//    public static ArrayList<String> getmSelectPath() {
//        return mSelectPath;
//    }

////    public void setmSelectPath(ArrayList<String> mSelectPath) {
//        this.mSelectPath = mSelectPath;
//    }

    PiScoreBoard lists = PiScoreBoard.getInstance();

    Game currentGame = Game.getInstance();

    public static int getRequestImage() {
        return REQUEST_IMAGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphics = new Graphics(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

 //   try{
//        FileInputStream fIn = openFileInput("PublicityFile.txt");
//       // InputStreamReader isr = new InputStreamReader(fIn);
//        ObjectInputStream savedStream = new ObjectInputStream(fIn);
//
//        jogo.getPublictyList().add(savedStream.readObject());
//        //jogo.setPublictyList((ArrayList)savedStream.readObject());
//
//    } catch (IOException ioe)
//    {
//        ioe.printStackTrace();
//    } catch (ClassNotFoundException e) {
//        e.printStackTrace();
//    }
        mNavigationDrawerFragment.setUp( R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));



            try {
                FileInputStream fIn = openFileInput("Teams.txt");
                ObjectInputStream savedStream = new ObjectInputStream(fIn);
                while(true) {
                    try {
                        lists.getListOfTeams().add((Team) savedStream.readObject());

                    } catch (EOFException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        switch (position){
            case 0:
                objFragment = new HomeScreenFragment();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case 1:
                objFragment = new StartGameFragment();
                break;
            case 2:
                objFragment = new TeamsManagmentFragment();
                break;
            case 3:
                objFragment = new PublicityManagmentFragment();
                break;
            case 4:
                objFragment = new SettingsFragment();
                break;
            default:
                break;
        }
               // update the main content by replacing fragments

        fragmentManager.beginTransaction() .replace(R.id.container, objFragment).commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_home:

                Toast.makeText(this, "Go hard or go home!", Toast.LENGTH_SHORT).show();
                objFragment = new HomeScreenFragment();
                fragmentManager.beginTransaction().replace(R.id.container, objFragment).commit();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void sendCommand(String message){

        //EditText sMessage = (EditText) findViewById(R.id.message);
        //String message = sMessage.getEditableText().toString();

        //String message = new String("Pub@on@");
        Thread sendThread = new Thread(new ClientSendThread(message,this));
        sendThread.start();


//        ClientReceiveThread received = new ClientReceiveThread();
//        new Thread(received).start();
//
//        String response  =   received.getMessage();
//        TextView responseTV = (TextView) findViewById(R.id.editText);
//        responseTV.setText(response);




//        Log.d("Received", received.getMessage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                int id = data.getIntExtra(MultiImageSelectorActivity.EXTRA_FRAGMENT_ID, 0);

                switch (id){
                    case 3:
                        currentGame.setPublictyList(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));

                    break;
                    case 5: {
                        String fullPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).toString();
                        int index = fullPath.indexOf("[");
                        newTeamPath = fullPath.substring(index+1,fullPath.lastIndexOf("]"));

                    }break;
                }

            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        try {
            FileOutputStream savefile = openFileOutput("Teams.txt", MODE_WORLD_READABLE);
//            ObjectOutputStream saveStream = new ObjectOutputStream(savefile);
//            FileOutputStream  savefile = new FileOutputStream("Teams.txt");
            ObjectOutputStream saveStream = new ObjectOutputStream(savefile);
////            for (Iterator<String> i = jogo.getPublictyList().iterator(); i.hasNext(); ) {
////                String pubs = i.next();
////                saveStream.writeObject(pubs);
////            }
//            saveStream.writeObject(jogo.getPublictyList());
////            saveStream.writeObject("Hello World!");
//            for(Team equipa:lists.getListOfTeams()){
//                saveStream.writeObject(equipa);
//            }
            for (Iterator<Team> i = lists.getListOfTeams().iterator(); i.hasNext(); ) {
                Team teams = i.next();
                saveStream.writeObject(teams);
            }

            saveStream.flush();
            savefile.flush();
            saveStream.close();
            savefile.close();

        } catch (FileNotFoundException a) {
            System.out.println("File not found!");
        } catch (IOException b) {
            b.printStackTrace();
            System.out.println("IO Exception!");

        }
    }

}
