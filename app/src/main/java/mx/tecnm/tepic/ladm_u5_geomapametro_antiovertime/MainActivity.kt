package mx.tecnm.tepic.ladm_u5_geomapametro_antiovertime

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import android.location.LocationListener
import android.location.LocationManager
import android.opengl.Visibility
import android.view.View
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import mx.tecnm.tepic.ladm_u5_geomapametro_antiovertime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    var baseRemota = FirebaseFirestore.getInstance()
    var posicion = ArrayList<Data>()
    lateinit var locacion : LocationManager
    var bandera = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }

        baseRemota.collection("metropolitano")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    //binding.textView2.setText("ERROR: "+firebaseFirestoreException.message)
                    return@addSnapshotListener
                }

                var resultado = ""
                posicion.clear()
                for(document in querySnapshot!!){
                    var data = Data()
                    data.nombre = document.getString("nombre").toString()
                    data.posicion1 = document.getGeoPoint("posicion1")!!
                    data.posicion2 = document.getGeoPoint("posicion2")!!

                    resultado+= data.toString()+"\n\n"
                    posicion.add(data)
                }

                //binding.textView2.setText(resultado)
            }

            binding.imagen2.setOnClickListener {
                if(binding.textView4.text.toString()=="Estás en cancha") {
                    if (bandera) {
                        binding.imagen1.setImageResource(R.drawable.cancha2)
                        binding.imagen2.setImageResource(R.drawable.cancha1)
                        bandera = false
                    } else {
                        binding.imagen1.setImageResource(R.drawable.cancha1)
                        binding.imagen2.setImageResource(R.drawable.cancha2)
                        bandera = true
                    }
                }
                if(binding.textView4.text.toString()=="Estás en negocios") {
                    if (bandera) {
                        binding.imagen1.setImageResource(R.drawable.negocios2)
                        binding.imagen2.setImageResource(R.drawable.negocios1)
                        bandera = false
                    } else {
                        binding.imagen1.setImageResource(R.drawable.negocios1)
                        binding.imagen2.setImageResource(R.drawable.negocios2)
                        bandera = true
                    }
                }
                if(binding.textView4.text.toString()=="Estás en sombrero") {
                    if (bandera) {
                        binding.imagen1.setImageResource(R.drawable.sombrero2)
                        binding.imagen2.setImageResource(R.drawable.sombrero1)
                        bandera = false
                    } else {
                        binding.imagen1.setImageResource(R.drawable.sombrero1)
                        binding.imagen2.setImageResource(R.drawable.sombrero2)
                        bandera = true
                    }
                }
                if(binding.textView4.text.toString()=="Estás en estacionamiento") {
                    if (bandera) {
                        binding.imagen1.setImageResource(R.drawable.estacionamiento2)
                        binding.imagen2.setImageResource(R.drawable.estacionamiento1)
                        bandera = false
                    } else {
                        binding.imagen1.setImageResource(R.drawable.estacionamiento1)
                        binding.imagen2.setImageResource(R.drawable.estacionamiento2)
                        bandera = true
                    }
                }
                if(binding.textView4.text.toString()=="Estás en entrada") {
                    if (bandera) {
                        binding.imagen1.setImageResource(R.drawable.entrada2)
                        binding.imagen2.setImageResource(R.drawable.entrada1)
                        bandera = false
                    } else {
                        binding.imagen1.setImageResource(R.drawable.entrada1)
                        binding.imagen2.setImageResource(R.drawable.entrada2)
                        bandera = true
                    }
                }
                if(binding.textView4.text.toString()=="Estás en fuentes") {
                    if (bandera) {
                        binding.imagen1.setImageResource(R.drawable.fuentes2)
                        binding.imagen2.setImageResource(R.drawable.fuentes1)
                        bandera = false
                    } else {
                        binding.imagen1.setImageResource(R.drawable.fuentes1)
                        binding.imagen2.setImageResource(R.drawable.fuentes2)
                        bandera = true
                    }
                }
            }
        locacion = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var oyente = Oyente(this)
        locacion.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,01f,oyente)
    }


}
class Oyente(puntero:MainActivity) : LocationListener {
    val p = puntero

    override fun onLocationChanged(location: Location) {
        p.binding.imagen1.visibility = View.GONE
        p.binding.imagen2.visibility = View.GONE
        p.binding.imagen3.visibility = View.VISIBLE
        //p.binding.textView3.setText("${location.latitude}, ${location.longitude}")
        p.binding.textView4.setText("Estás en el parque Metropolitano.")
        var geoPosicionGPS = GeoPoint(location.latitude, location.longitude)

        for(item in p.posicion){
            if(item.estoyEn(geoPosicionGPS)){
                p.binding.textView4.setText("Estás en ${item.nombre}")
            }
        }

        agregarfotos(p.binding.textView4.text.toString())
    }

    private fun agregarfotos(texto: String) {
        if(texto=="Estás en cancha"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE
            p.binding.imagen1.setImageResource(R.drawable.cancha1)
            p.binding.imagen2.setImageResource(R.drawable.cancha2)
        }
        if(texto=="Estás en negocios"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE
            p.binding.imagen1.setImageResource(R.drawable.negocios1)
            p.binding.imagen2.setImageResource(R.drawable.negocios2)
        }
        if(texto=="Estás en sombrero"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE
            p.binding.imagen1.setImageResource(R.drawable.sombrero1)
            p.binding.imagen2.setImageResource(R.drawable.sombrero2)
        }
        if(texto=="Estás en estacionamiento"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE
            p.binding.imagen1.setImageResource(R.drawable.estacionamiento1)
            p.binding.imagen2.setImageResource(R.drawable.estacionamiento2)
        }
        if(texto=="Estás en entrada"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE
            p.binding.imagen1.setImageResource(R.drawable.entrada1)
            p.binding.imagen2.setImageResource(R.drawable.entrada2)
        }
        if(texto=="Estás en fuentes"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE
            p.binding.imagen1.setImageResource(R.drawable.fuentes1)
            p.binding.imagen2.setImageResource(R.drawable.fuentes2)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }
}