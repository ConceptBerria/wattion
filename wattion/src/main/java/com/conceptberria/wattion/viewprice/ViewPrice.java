package com.conceptberria.wattion.viewprice;

import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.conceptberria.wattion.app.EnergyControlApp;
import com.conceptberria.wattion.background.GetPriceTask;
import com.conceptberria.wattion.background.GetPriceTask.IGetPriceListener;
import com.conceptberria.wattion.background.MakeNotification;
import com.conceptberria.wattion.model.EnergyPriceDay;
import com.conceptberria.wattion.service.EnergyPriceServiceImpl;
import com.conceptberria.wattion.util.CalendarUtil;
import com.conceptberria.wattion.viewprice.R.id;
import com.conceptberria.wattion.viewprice.R.layout;
import com.conceptberria.wattion.viewprice.R.string;
import com.conceptberria.wattion.viewprice.adapters.ViewPriceAdapterFactory;
import com.conceptberria.wattion.viewprice.adapters.ViewPriceAdapterFactory.ViewAdapter;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;

import java.util.Calendar;
import java.util.Date;


public class ViewPrice extends AppCompatActivity implements OnSharedPreferenceChangeListener, IGetPriceListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    ViewPrice.SectionsPagerAdapter mSectionsPagerAdapter;
    ProgressBar progressBar;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public static boolean configurationChange;
    private OnSharedPreferenceChangeListener listener;
    private Date ultimaActuacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Listener de configuracion
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);

        this.listener = new OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                ViewPrice.configurationChange = true;
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(this.listener);
        this.makeNotification();

        this.setContentView(layout.view_loading);
        this.progressBar = (ProgressBar) this.findViewById(id.loading);
        if (EnergyPriceServiceImpl.getInstance().existInfo(CalendarUtil.getInstance().getNow())) {
            this.renderView();
        } else {
            GetPriceTask obtenerPrecioEnergia = new GetPriceTask();
            obtenerPrecioEnergia.setListener(this);
            obtenerPrecioEnergia.execute(CalendarUtil.getInstance().getNow());
        }

        if (!EnergyPriceServiceImpl.getInstance().existInfo(CalendarUtil.getInstance().getTomorrow()) && CalendarUtil.getInstance().isMayorHour(18)) {
            GetPriceTask obtenerPrecioEnergiaMañana = new GetPriceTask();
            obtenerPrecioEnergiaMañana.execute(CalendarUtil.getInstance().getTomorrow());

        }

        this.track();
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ViewPrice.configurationChange) {
            ViewPrice.configurationChange = false;
            this.finish();
            this.startActivity(this.getIntent());
        }
        if (this.ultimaActuacion != null) {
            if (CalendarUtil.getInstance().isSameHour(CalendarUtil.getInstance().addOneHour(this.ultimaActuacion), CalendarUtil.getInstance().getNow())) {
                this.ultimaActuacion = CalendarUtil.getInstance().getNow();
                this.finish();
                this.startActivity(this.getIntent());

            }
        }

        // Obtain the shared Tracker instance.
        this.track();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Prepara el tracking de analytics
     */
    public void track() {
        EnergyControlApp application = (EnergyControlApp) this.getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName("ViewPrice energy");
        mTracker.send(new ScreenViewBuilder().build());
    }

    /**
     * Programa la ejecución del servicio {@link MakeNotification} cada hora
     */
    private void makeNotification() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent newIntent = new Intent(this.getApplicationContext(), MakeNotification.class);
        PendingIntent pintent = PendingIntent.getService(this.getApplicationContext(), 0, newIntent, 0);
        AlarmManager alarm = (AlarmManager) this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pintent);
        alarm.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR, pintent);
    }


    /**
     * renderiza la vista
     */
    private void renderView() {
        this.setContentView(layout.activity_view_price);

        Toolbar toolbar = (Toolbar) this.findViewById(id.toolbar);
        this.setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        this.mSectionsPagerAdapter = new ViewPrice.SectionsPagerAdapter(this.getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        this.mViewPager = (ViewPager) this.findViewById(id.pager);
        this.mViewPager.setAdapter(this.mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) this.findViewById(id.tabs);
        tabLayout.setupWithViewPager(this.mViewPager);
        this.ultimaActuacion = CalendarUtil.getInstance().getNow();

    }

    /**
     * Muestra error si no se puede obtener la información
     */
    public void ministerioError() {
        String message = getString(string.error_obtener_info);
        if (!this.checkConnectivity()) {
            message = getString(string.error_internet);
        }
        Builder dialog = new Builder(this);

        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(getString(string.ok_dialog), new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = this.getIntent();
        switch (id) {
            case R.id.action_refresh:
                this.finish();
                this.startActivity(intent);
                return true;
            case R.id.action_about:
                Intent intentAbout;
                intentAbout = new Intent(this, AboutActivity.class);
                this.startActivity(intentAbout);
                return true;
            case R.id.action_settings:
                Intent intentConfig;
                if (VERSION.SDK_INT > VERSION_CODES.HONEYCOMB) {
                    intentConfig = new Intent(this, SettingsActivity.class);
                } else {
                    intentConfig = new Intent(this, CompatibleSettingsActivity.class);
                }
                this.startActivity(intentConfig);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Comprueba si se tiene conexión a internet
     *
     * @return true si existe conexión
     */
    private boolean checkConnectivity() {
        boolean enabled = true;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if (info == null || !info.isConnected() || !info.isAvailable()) {
            enabled = false;
        }
        return enabled;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        ViewPrice.configurationChange = true;
    }

    @Override
    public void onGetPriceCompleted(Long i) {
        if (i != GetPriceTask.OK) {
            ministerioError();
        } else {
            this.progressBar.setVisibility(View.GONE);
            renderView();
        }

    }

    @Override
    public void onGetPriceStart() {
        this.progressBar.setVisibility(View.VISIBLE);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ViewPrice.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return ViewPrice.this.getString(string.title_section1);
                case 1:
                    return ViewPrice.this.getString(string.title_section2);
                case 2:
                    return ViewPrice.this.getString(string.title_section3);
            }
            return null;
        }

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
        public static ViewPrice.PlaceholderFragment newInstance(int sectionNumber) {

            ViewPrice.PlaceholderFragment fragment = new ViewPrice.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            EnergyPriceDay energyPriceDay = EnergyPriceServiceImpl.getInstance().getEnergyPriceDay(CalendarUtil.getInstance().getNow());
            EnergyPriceDay energyPriceDayTomorrow = EnergyPriceServiceImpl.getInstance().getEnergyPriceDay(CalendarUtil.getInstance().getTomorrow());
            ViewPriceAdapterFactory adapterFactory = new ViewPriceAdapterFactory();
            View vista = null;
            int fragment = this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER);


            switch (fragment) {
                case 1:

                    vista = inflater.inflate(layout.fragment_view_price, container, false);
                    adapterFactory.getAdapter(ViewAdapter.ACTUAL_FRAME_ADAPTER).render(vista, energyPriceDay);
                    break;
                case 2:
                    vista = inflater.inflate(layout.fragment_view_price_day, container, false);
                    adapterFactory.getAdapter(ViewAdapter.POR_HORAS_FRAME_ADAPTER).render(vista, energyPriceDay);
                    break;

                case 3:
                    vista = inflater.inflate(layout.fragment_view_price_tomorrow, container, false);
                    adapterFactory.getAdapter(ViewAdapter.MANANA_FRAME_ADAPTER).render(vista, energyPriceDayTomorrow);
                    break;
            }
            return vista;
        }
    }
}
