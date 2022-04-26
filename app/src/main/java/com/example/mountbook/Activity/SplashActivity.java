package com.example.mountbook.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mountbook.AppManager;
import com.example.mountbook.Model.Shelter;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryVariant, this.getTheme()));

        final Context ctx = this;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        List<Shelter> listShelter =new ArrayList<>();
//        listShelter.add(new Shelter(1, "Rifugio1", "Monpantero (Susa), seguire indicazioni La riposa", new Date(2021,12,31), new Date(2021,12,31), 45,2130,7.674824,45.191018, "393489970549", "www.rifugio.it","info@rifugio.it","Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Susa",null, 34, 6,"pernottamento"));
//        listShelter.add(new Shelter(2, "Rifugio2", "Monpantero (Susa), seguire indicazioni La riposa", new Date(2021,12,31), new Date(2021,12,31), 45,2130,7.674824,45.291018, "393489970549", "www.rifugio.it","info@rifugio.it","Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Susa",null, 34, 6,"pernottamento"));
//        listShelter.add(new Shelter(3, "Rifugio3", "Monpantero (Susa), seguire indicazioni La riposa", new Date(2021,12,31), new Date(2021,12,31), 45,2130,7.674824,45.391018, "393489970549", "www.rifugio.it","info@rifugio.it","Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Susa",null, 34, 6,"pernottamento"));
//        listShelter.add(new Shelter(4, "Rifugio4", "Monpantero (Susa), seguire indicazioni La riposa", new Date(2021,12,31), new Date(2021,12,31), 45,2130,7.674824,45.491018, "393489970549", "www.rifugio.it","info@rifugio.it","Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Susa",null, 34, 6,"pernottamento"));
//        listShelter.add(new Shelter(5, "Rifugio5", "Monpantero (Susa), seguire indicazioni La riposa", new Date(2021,12,31), new Date(2021,12,31), 45,2130,7.674824,45.591018, "393489970549", "www.rifugio.it","info@rifugio.it","Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Susa",null, 34, 6,"pernottamento"));
//        listShelter.add(new Shelter(6, "Rifugio6", "Monpantero (Susa), seguire indicazioni La riposa", new Date(2021,12,31), new Date(2021,12,31), 45,2130,7.674824,45.691018, "393489970549", "www.rifugio.it","info@rifugio.it","Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Susa",null, 34, 6,"pernottamento"));

        //set the list as empty every time app is open
        AppManager.getInstance().setHutListResult(listShelter);
        AppManager.getInstance().setHutListAll(listShelter);

        List<Reservation> reservationList=new ArrayList<>();
//        reservationList.add(new Reservation(new Date(2021, 12, 1), 6, listShelter.get(2), new Date(2021, 12, 5), 1, "Giampaolo1" ));
//        reservationList.add(new Reservation(new Date(2022, 1, 13), 2, listShelter.get(5), new Date(2022, 1, 20), 2, "Lucia" ));

        AppManager.getInstance().setReservationList(reservationList);


        Thread timer = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                Intent i;
                if(SaveSharedPreferences.getUserName(ctx).length()!=0){
                    i=new Intent(ctx, MainActivity.class);
                    i.putExtra("redirect", 0);
                }
                else{
                    i=new Intent(ctx,LoginActivity.class);
                }
                startActivity(i);
                finish();
            }
        });
        timer.start();
    }
}