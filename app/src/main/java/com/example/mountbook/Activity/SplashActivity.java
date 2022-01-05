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
        listShelter.add(new Shelter(1, "Rifugio1", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.191018, 7.074824, "info@rifugio.it", "+393489970549", 2150,75,12));
        listShelter.add(new Shelter(2, "Rifugio2", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.20, 7.10, "info@rifugio.it", "+393489970549", 1789,56,7));
        listShelter.add(new Shelter(3, "Rifugio3", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.15, 7.05, "info@rifugio.it", "+393489970549", 1854,49,5));
        listShelter.add(new Shelter(4, "Rifugio4", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.23, 7.08, "info@rifugio.it", "+393489970549", 1256,87,12));
        listShelter.add(new Shelter(5, "Rifugio5", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.13, 7.11, "info@rifugio.it", "+393489970549", 2003,155, 16));
        listShelter.add(new Shelter(6, "Rifugio6", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.18, 7.14, "info@rifugio.it", "+393489970549", 1894,45,8));
        listShelter.add(new Shelter(7, "Rifugio7", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.10, 7.06, "info@rifugio.it", "+393489970549", 1996,69, 21));
        listShelter.add(new Shelter(8, "Rifugio8", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.25, 7.04, "info@rifugio.it", "+393489970549", 1568,102,25));
        listShelter.add(new Shelter(9, "Rifugio9", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.20, 7.03, "info@rifugio.it", "+393489970549", 1236,74,14));
        listShelter.add(new Shelter(10, "Rifugio10", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.22, 7.11, "info@rifugio.it", "+393489970549", 2548, 98,20));
        listShelter.add(new Shelter(11, "Rifugio11", "Da Susa imboccare (segnalazioni) la carrozzabile che sale ad Urbiano, fraz. di Mompantero; da qui si continua sulla strada ex militare, asfaltata per un buon tratto, che consente di raggiungere, con percorso tortuoso ma molto panoramico, i ruderi dell'ex forte la Riposa 2205 m (20 km circa da Sus", "Il rifugio Cà d'Asti è un rifugio situato nel comune di Mompantero, in val di Susa, nelle Alpi Graie, a 2.854 m s.l.m.", "Mompantero, 2854 mt.", "Pernottamento, pasti, affitto attrezzatura", 45.3, 7.074824, "info@rifugio.it", "+393489970549", 2100,156,21));

        AppManager.getInstance().setHutListResult(listShelter);
        AppManager.getInstance().setHutListAll(listShelter);

        List<Reservation> reservationList=new ArrayList<>();
        reservationList.add(new Reservation(new Date(2021, 12, 1), 6, listShelter.get(2), new Date(2021, 12, 5), 1, "Giampaolo1" ));
        reservationList.add(new Reservation(new Date(2022, 1, 13), 2, listShelter.get(5), new Date(2022, 1, 20), 2, "Lucia" ));

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