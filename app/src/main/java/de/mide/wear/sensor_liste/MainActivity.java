package de.mide.wear.sensor_liste;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class MainActivity extends WearableActivity {

    /** Liste mit allen Sensoren. */
    protected List<Sensor> _sensorListe = null;


    /**
     * Lifecycle-Methode: Nach Laden der Layout-Datei wird ein
     * ArrayAdapter für das ListView-Element erzeugt.
     * Es wird auch die Anzahl der gefundenen Sensoren als Toast
     * angezeigt.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView _sensorListView = findViewById( R.id.sensorListView );

        List<String> sensorNamenListe = getAlleSensoren();
        ArrayAdapter<String> sensorArrayAdapter =
                            new ArrayAdapter<String>(this,
                                                     R.layout.sensor_list_item,
                                                     R.id.sensorName,
                                                     sensorNamenListe);

        _sensorListView.setAdapter(sensorArrayAdapter);

        Toast toast = Toast.makeText(this,
                        "Anzahl Sensoren: " + sensorNamenListe.size(),
                        Toast.LENGTH_LONG);
        toast.show();

        setAmbientEnabled(); // Enables Always-on
    }


    /**
     * Liefert Liste mit allen auf dem WearOS-Gerät vorhandenen Sensoren
     * zurück.
     *
     * @return Liste mit allen gefundenen Sensoren; ist nie <tt>null</tt>,
     *         sondern ist höchstens ein leerer Array.
     */
    protected List<String> getAlleSensoren() {

        SensorManager sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {

            _sensorListe = sensorManager.getSensorList(Sensor.TYPE_ALL);

            ArrayList<String> ergebnisListe = new ArrayList<String>();
            for (Sensor sensor: _sensorListe) {
                ergebnisListe.add( sensor.getName() );
            }

            return ergebnisListe;

        } else {

            Toast toast = Toast.makeText(this,
                    "Sensor-Manager nicht erhalten.",
                    Toast.LENGTH_LONG);
            toast.show();

            return new ArrayList<String>(); //leere Liste
        }
    }
    
}
