package com.ibm.watson.developer_cloud.android.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.ConeccionClima.ConeccionClima;

import java.io.IOException;
import java.net.URL;

public class RespuestaWatson extends Activity {
    private Button boton;
    private TextView txtResult;
    private TextView txtEnfermedad;
    private TextView txtCodigo;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_watson);
        boton = (Button) findViewById(R.id.back);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        });
        txtResult=(TextView) findViewById(R.id.txtResultado);
        txtCodigo=(TextView) findViewById(R.id.codigo);
        txtEnfermedad=(TextView) findViewById(R.id.enfermedad);

        //String txtPais = txtQuery.getText().toString();
        intent=getIntent();
        String platica = intent.getStringExtra("platica");
        txtResult.setText(platica);

        String diagnostico= platica.substring(platica.lastIndexOf("es")+3);

        diagnostico=diagnostico.replace(".","");

        URL SearchURL = ConeccionClima.buildUrl(diagnostico);


        new QueryTask().execute(SearchURL);

    }

    private void regresar() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public class QueryTask extends AsyncTask<URL, Void, String>
    {
        @Override
        protected String doInBackground(URL... params) {
            String resultado="";
            try {
                resultado = ConeccionClima.getResponseFromHttpUrl(params[0]);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s!= null && !s.equals(""))
                //txtResult.setText(s);
                txtCodigo.setText(s.substring(s.indexOf("Key")+6,s.indexOf(",")));
                txtEnfermedad.setText(s.substring(s.indexOf("Value")+8,s.length()-3));
            super.onPostExecute(s);
        }
    }

    /**

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.btnSearch) {
            String txtPais = txtQuery.getText().toString();
            URL SearchURL = ConeccionClima.buildUrl(txtPais);

            new QueryTask().execute(SearchURL);
        }
        return super.onOptionsItemSelected(item);
    }
    */

}
