/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.enav.cazaunchollo.cazaunchollo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OfferDescriptionFragment extends Fragment {

    private LinearLayout mRootView;
    private DatabaseReference ref;
    private TextView textView2;
    private TextView estado;
    private ImageView estadoIV;
    private TextView tituloTV;
    private ImageView imagen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (LinearLayout) inflater.inflate(R.layout.fragment_offer_description, container, false);

        textView2 = (TextView) mRootView.findViewById(R.id.textView2);
        estado = (TextView) mRootView.findViewById(R.id.estado);
        estadoIV = (ImageView)mRootView.findViewById(R.id.estadoIV);
        tituloTV = (TextView)mRootView.findViewById(R.id.titulotv);
        imagen = (ImageView)mRootView.findViewById(R.id.imageView3);
        String referencia = OfferScrollingActivity.getReferencia();


        // Conexión Firebase
        ref = FirebaseDatabase.getInstance().getReference().child("offers");

       ref.child(referencia).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Offer offer =  dataSnapshot.getValue(Offer.class);
               String titulo = offer.getNombre();
               String descripcion = offer.getDescripcion();
               String estado2 = offer.getEstado();
               imagen.setImageResource(offer.getImagen());
               tituloTV.setText(titulo);
               textView2.setText(descripcion);

               if(estado2.equals("DISPONIBLE")){
                   estadoIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_24dp));
                   estado.setText(estado2);
               }

               if(estado2.equals("AGOTADO")){
                   estadoIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_24dp));
                   estado.setText(estado2);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        return mRootView;

    }

    public static Fragment newInstance() {
        return new OfferDescriptionFragment();
    }

}
