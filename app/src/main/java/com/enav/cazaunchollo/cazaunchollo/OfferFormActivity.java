package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OfferFormActivity extends AppCompatActivity {

    private Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_form);

        button_send = (Button) findViewById(R.id.button_send);
        final Comment comment = new Comment("a", "a");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference offerReference = database.getReference(FirebaseReferences.OFFERS_REFERENCE);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = new DateFormat();
                String fecha = dateFormat.devolverFecha();

                //SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                //String fecha = formateador.format(new Date());
                //Log.d("FECHA", fecha);
                Offer offer = new Offer("Chollo Test 1", "Tienda Test 1", "0", "0", R.drawable.test_amazon, "DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST DESCRIPCIÓN TEST", "AGOTADO", comment, fecha);
                offerReference.push().setValue(offer);
            }
        });

    }
}
