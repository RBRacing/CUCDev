/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.enav.cazaunchollo.cazaunchollo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private Button button_link_offer;
    MainActivity m;
    boolean entro;

    private Button mod_offer;
    private Button delete_offer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (LinearLayout) inflater.inflate(R.layout.fragment_offer_description, container, false);

        textView2 = (TextView) mRootView.findViewById(R.id.textView2);
        estado = (TextView) mRootView.findViewById(R.id.estado);
        estadoIV = (ImageView)mRootView.findViewById(R.id.estadoIV);
        tituloTV = (TextView)mRootView.findViewById(R.id.titulotv);
        imagen = (ImageView)mRootView.findViewById(R.id.imageView3);
        button_link_offer = (Button) mRootView.findViewById(R.id.button_link_offer);
        mod_offer = (Button) mRootView.findViewById(R.id.mod_offer);
        delete_offer = (Button) mRootView.findViewById(R.id.delete_offer);

        String referencia = OfferScrollingActivity.getReferencia();
        m = new MainActivity();
        entro = false;

        // Conexión Firebase
        ref = FirebaseDatabase.getInstance().getReference().child("offers");

       ref.child(referencia).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               final Offer offer =  dataSnapshot.getValue(Offer.class);
               String titulo="";
               String descripcion="";
               Boolean estado2=false;

               try{

                   titulo = offer.getNombre();
                   descripcion = offer.getDescripcion();
                   estado2 = offer.getDisponible();

               }catch (Exception e){

               }
                   try{
                       if(m.getApplicationContext() !=null){
                           Glide.with(m.getApplicationContext()).load(offer.getImagen()).fitCenter().centerCrop().into(imagen);
                           entro = false;
                       }
                   }catch (Exception e){
                       entro = false;
                   }

                   try{
                       if(getContext() !=null){
                           Glide.with(getContext()).load(offer.getImagen()).fitCenter().centerCrop().into(imagen);
                           entro = true;
                       }
                   }catch (Exception e){
                       entro = false;
                   }



                   //imagen.setImageResource(offer.getImagen());
                   tituloTV.setText(titulo);
                   textView2.setText(descripcion);
                   button_link_offer.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           String url = offer.getEnlace().toLowerCase().toString();
                           try{
                               Intent i = new Intent(Intent.ACTION_VIEW);
                               i.setData(Uri.parse(url));
                               startActivity(i);
                           }catch (Exception e){
                               Toast.makeText(getContext(),"Error al cargar la oferta, la URL no es válida.", Toast.LENGTH_SHORT).show();
                           }

                       }
                   });


                   if(estado2 && entro){
                       estadoIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_24dp));
                       estado.setText("DISPONIBLE");
                   }

                   if(!estado2 && entro){
                       estadoIV.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_24dp));
                       estado.setText("AGOTADO");
                   }

                   //
                   FirebaseAuth auth;
                   auth = FirebaseAuth.getInstance();

                   // Obtener usuario actual Firebase
                   final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                   if(offer.getUid_creator().equals(user.getUid())){
                       mod_offer.setVisibility(View.VISIBLE);
                       delete_offer.setVisibility(View.VISIBLE);


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
