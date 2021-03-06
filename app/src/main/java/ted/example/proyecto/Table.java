package ted.example.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

/**
 * Created by francis on 14/10/14.
 * Esta clase representa una tabla
 */
public class Table {
    // Variables de la clase

    private TableLayout tabla;          // Layout donde se pintará la tabla
    private ArrayList<TableRow> filas;  // Array de las filas de la tabla
    private Activity actividad;
    private Resources rs;
    private int FILAS, COLUMNAS;
    DatabaseHelper db;
    String message;
    // Filas y columnas de nuestra tabla

    /**
     * Constructor de la tabla
     *
     * @param actividad Actividad donde va a estar la tabla
     * @param tabla     TableLayout donde se pintará la tabla
     */
    public Table(Activity actividad, TableLayout tabla) {
        this.actividad = actividad;
        this.tabla = tabla;
        rs = this.actividad.getResources();
        FILAS = COLUMNAS = 0;
        filas = new ArrayList<TableRow>();

    }

    /**
     * Añade la cabecera a la tabla
     *
     * @param recursocabecera Recurso (array) donde se encuentra la cabecera de la tabla
     */
    public void agregarCabecera(int recursocabecera) {
        TableRow.LayoutParams layoutCelda;
        TableRow fila = new TableRow(actividad);
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(layoutFila);

        String[] arraycabecera = rs.getStringArray(recursocabecera);
        COLUMNAS = arraycabecera.length;

        for (int i = 0; i < arraycabecera.length; i++) {
            TextView texto = new TextView(actividad);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(arraycabecera[i]), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setText(arraycabecera[i]);
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(R.style.estilo_celda);
            texto.setBackgroundResource(R.drawable.tabla_celda_cabecera);
            texto.setLayoutParams(layoutCelda);

            fila.addView(texto);
        }

        tabla.addView(fila);
        filas.add(fila);

        FILAS++;
    }

    /**
     * Agrega una fila a la tabla
     *
     * @param elementos Elementos de la fila
     */
    public void agregarFilaTabla(ArrayList<String> elementos, Context context, int id) {
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(layoutFila);
        String conditions = "";
        for (int i = 0; i < elementos.size()-1; i++) {
            TextView texto = new TextView(actividad);
            texto.setText(String.valueOf(elementos.get(i)));
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(R.style.estilo_celda);
            texto.setBackgroundResource(R.drawable.tabla_celda);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(texto.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setLayoutParams(layoutCelda);
            fila.addView(texto);
            if (i == 6) {
                conditions = String.valueOf(elementos.get(i));
            }

        }
        if (conditions.equals("NEW")) {
        Button button = new Button(context);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(context, null, 1, null);
                try {
                    boolean res = db.updateRequest((elementos.get(elementos.size() - 1) + ""), "ACCEPTED",
                            +id + "", "Tu solicitud fue aceptada! Dentro de poco iniciar el viaje");
                    if (res)
                        alert("Solicitud de transporte aceptada!", context);
                    else
                        alert("Para aceptar una solicitud debe tener un vehiculo registrado", context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        button.setText("Aceptar");
        button.setId(Integer.parseInt(elementos.get(elementos.size() - 1)));
        fila.addView(button);
        }
        filas.add(fila);
        tabla.addView(fila);

        FILAS++;
    }

    public void agregarFilaTablaDriver(ArrayList<String> elementos, Context context, int id) {
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(layoutFila);
        String conditions = "";
        for (int i = 0; i < elementos.size() - 1; i++) {
            TextView texto = new TextView(actividad);
            texto.setText(String.valueOf(elementos.get(i)));
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(R.style.estilo_celda);
            texto.setBackgroundResource(R.drawable.tabla_celda);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(texto.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setLayoutParams(layoutCelda);
            fila.addView(texto);
            if (i == 6) {
                conditions = String.valueOf(elementos.get(i));
            }

        }
        if (!conditions.equals("FINALIZED")) {
            Button button = new Button(context);
            button.setText("Iniciar viaje");
            message = "Viaje iniciado!";
            if (conditions.equals("TRAVELING")) {
                button.setText("Finalizar viaje");
                message = "Viaje finalizado!";
                conditions = "FINALIZED";
            }else{
                conditions = "TRAVELING";
            }

            String finalConditions = conditions;
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    db = new DatabaseHelper(context, null, 1, null);
                    try {
                        db.updateRequestDriver((elementos.get(elementos.size() - 1) + ""), finalConditions, +id + "", message);
                        alert(message, context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            button.setId(Integer.parseInt(elementos.get(elementos.size() - 1)));
            fila.addView(button);
        }
        filas.add(fila);
        tabla.addView(fila);

        FILAS++;
    }

    public void alert(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //builder.setTitle("Titulo");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Elimina una fila de la tabla
     *
     * @param indicefilaeliminar Indice de la fila a eliminar
     */
    public void eliminarFila(int indicefilaeliminar) {
        if (indicefilaeliminar > 0 && indicefilaeliminar < FILAS) {
            tabla.removeViewAt(indicefilaeliminar);
            FILAS--;
        }
    }

    /**
     * Devuelve las filas de la tabla, la cabecera se cuenta como fila
     *
     * @return Filas totales de la tabla
     */
    public int getFilas() {
        return FILAS;
    }

    /**
     * Devuelve las columnas de la tabla
     *
     * @return Columnas totales de la tabla
     */
    public int getColumnas() {
        return COLUMNAS;
    }

    /**
     * Devuelve el número de celdas de la tabla, la cabecera se cuenta como fila
     *
     * @return Número de celdas totales de la tabla
     */
    public int getCeldasTotales() {
        return FILAS * COLUMNAS;
    }

    /**
     * Obtiene el ancho en píxeles de un texto en un String
     *
     * @param texto Texto
     * @return Ancho en píxeles del texto
     */
    private int obtenerAnchoPixelesTexto(String texto) {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);

        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }


}