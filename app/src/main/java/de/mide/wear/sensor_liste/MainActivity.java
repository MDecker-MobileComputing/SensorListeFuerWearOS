package de.mide.wear.sensor_liste;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity
                          implements AdapterView.OnItemClickListener {

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
        _sensorListView.setOnItemClickListener(this);

        Toast toast = Toast.makeText(this,
                        "Anzahl Sensoren: " + sensorNamenListe.size(),
                        Toast.LENGTH_LONG);
        toast.show();


        setAmbientEnabled(); // Enables Always-on
    }


    /**
     * Event-Handler-Methode für Touch-Geste auf einen Listen-Eintrag.
     *
     * @param adapterView
     * @param view
     * @param position 0-basierter Index des Listeneintrags, der Event ausgelöst hat
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Sensor sensor = _sensorListe.get(position);
        Toast toast = Toast.makeText(this, sensor.getName(), Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * Liefert Liste mit allen auf dem WearOS-Gerät vorhandenen Sensoren
     * zurück.
     *
     * @return Liste mit allen gefundenen Sensoren; ist nie <tt>null</tt>,
     *         sondern hat höchstens keine Elemente.
     */
    protected List<String> getAlleSensoren() {

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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

            return new ArrayList<String>();
        }
    }


    /**
     * Reporting-Mode für einen Sensor als String erhalten.
     *
     * @param sensor Sensor, für den Reporting-Mode als String zurückgegeben
     *               werden soll.
     * @return String mit Reporting-Mode von Sensor
     */
    protected String getReportingModeString(Sensor sensor) {
        switch ( sensor.getReportingMode() ) {
            case Sensor.REPORTING_MODE_CONTINUOUS:
                    return "ReportingMode=Continuous";

            case Sensor.REPORTING_MODE_ONE_SHOT:
                    return "ReportingMode=OneShot";

            case Sensor.REPORTING_MODE_ON_CHANGE:
                    return "ReportingMode=OnChange";

            case Sensor.REPORTING_MODE_SPECIAL_TRIGGER:
                    return "ReportingMode=SpecialTrigger";

            default:
                return "ReportingMode=NotRecognized";
        }
    }

}
