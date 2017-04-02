package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfferFormActivity extends AppCompatActivity {

    private Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_form);

        button_send = (Button) findViewById(R.id.button_send);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference offerReference = database.getReference(FirebaseReferences.OFFERS_REFERENCE);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Offer offer = new Offer("Chollo Electronica al 65%", "Amazon", "150", "23", R.drawable.sinfoto, "La nueva Startup española Bipi está regalando 20€ en su app para alquiler coches. Pretender transformar el sector, con mejores precios, mayor seguridad, transparencia total y trayéndote el coche a tu casa. Usando el descuento, alquilar un coche 1 día sale a 22€ y te lo traen a tu casa. Además, si invitas a un amigo a la app, os dan 20€ a cada uno, por lo que ya tendrías 40€ y te saldría por 2€ como en el ejemplo de la imagen.");
                offerReference.push().setValue(offer);
            }
        });

    }
}
