package mx.tecnm.tepic.ladm_u5_geomapametro_antiovertime

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import android.location.LocationListener
import android.location.LocationManager
import android.opengl.Visibility
import android.util.TypedValue
import android.view.View
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import mx.tecnm.tepic.ladm_u5_geomapametro_antiovertime.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    var baseRemota = FirebaseFirestore.getInstance()
    var posicion = ArrayList<Data>()
    lateinit var locacion : LocationManager
    lateinit var locacion2 : LocationManager
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
                    data.descripcion = document.getString("descripcion").toString()

                    resultado+= data.toString()+"\n\n"
                    posicion.add(data)
                }
                //binding.textView2.setText(resultado)
            }

            binding.imagen2.setOnClickListener {
                cambiarimagen()
            }
            binding.imagen7.setOnClickListener {
                cambiarimagen()
            }
        locacion = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var oyente = Oyente(this)
        locacion.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,01f,oyente)

        //REDONDEAR ESQUINAS
        // get bitmap from assets folder
        val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
            R.drawable.metropolitano);

        // convert the bitmap to rounded corners bitmap drawable
        bitmap?.apply {
            val drawable = roundedCorners(
                context = applicationContext,
                radius = 50.dpToPixels(applicationContext)
            )
            // show the transparent rounded corners bitmap drawable
            binding.imagen3.setImageDrawable(drawable)
        }
    }
    fun cambiarimagen(){
        if(binding.textView4.text.toString()=="Estás en cancha") {
            if (bandera) {

                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.cancha2);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }

                binding.imagen2.setImageResource(R.drawable.cancha1)

                bandera = false
            } else {

                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.cancha1);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }

                binding.imagen2.setImageResource(R.drawable.cancha2)

                bandera = true
            }
        }
        if(binding.textView4.text.toString()=="Estás en negocios") {
            if (bandera) {

                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.negocios2);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.negocios1)
                bandera = false
            } else {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.negocios1);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.negocios2)
                bandera = true
            }
        }
        if(binding.textView4.text.toString()=="Estás en sombrero") {
            if (bandera) {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.sombrero2);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.sombrero1)
                bandera = false
            } else {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.sombrero1);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.sombrero2)
                bandera = true
            }
        }
        if(binding.textView4.text.toString()=="Estás en estacionamiento") {
            if (bandera) {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.estacionamiento2);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.estacionamiento1)
                bandera = false
            } else {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.estacionamiento1);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.estacionamiento2)
                bandera = true
            }
        }
        if(binding.textView4.text.toString()=="Estás en entrada") {
            if (bandera) {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.entrada2);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.entrada1)
                bandera = false
            } else {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.entrada1);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.entrada2)
                bandera = true
            }
        }
        if(binding.textView4.text.toString()=="Estás en fuentes") {
            if (bandera) {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.fuentes2);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.fuentes1)
                bandera = false
            } else {
                //REDONDEAR ESQUINAS
                // get bitmap from assets folder
                val bitmap: Bitmap? = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.fuentes1);

                // convert the bitmap to rounded corners bitmap drawable
                bitmap?.apply {
                    val drawable = roundedCorners(
                        context = applicationContext,
                        radius = 50.dpToPixels(applicationContext)
                    )
                    // show the transparent rounded corners bitmap drawable
                    binding.imagen1.setImageDrawable(drawable)
                }
                binding.imagen2.setImageResource(R.drawable.fuentes2)
                bandera = true
            }
        }
    }
    //PARA REDONDEAR ESQUINAS
    // extension function to get rounded corners bitmap drawable from bitmap
    fun Bitmap.roundedCorners(
        context: Context,
        radius: Float = 25F
    ): RoundedBitmapDrawable {
        return RoundedBitmapDrawableFactory.create(
            context.resources, this
        ).apply {
            setAntiAlias(true)
            // sets the corner radius to be applied when drawing the bitmap
            cornerRadius = radius
        }
    }


    // extension function to convert dp to equivalent pixels
    fun Int.dpToPixels(context: Context):Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    )


    // extension function to get bitmap from assets
    fun Context.assetsToBitmap(fileName: String): Bitmap?{
        return try {
            with(assets.open(fileName)){
                BitmapFactory.decodeStream(this)
            }
        } catch (e: IOException) { null }
    }
}
class Oyente(puntero:MainActivity) : LocationListener {
    val p = puntero

    override fun onLocationChanged(location: Location) {
        p.bandera = true
        p.binding.imagen1.visibility = View.GONE
        p.binding.imagen2.visibility = View.GONE
        p.binding.imagen3.visibility = View.VISIBLE
        //p.binding.textView3.setText("${location.latitude}, ${location.longitude}")
        p.binding.textView4.setText("No estás en ningún \n área mapeada.")
        p.binding.descripcion2.setText("El parque Metropolitano es un espacio abierto para toda la sociedad nayarita, el cual cuenta con áreas verdes para niños, juegos recreativos, canchas deportivas y áreas verdes en el cual se programan diversos programas culturales.\n" +
                "\nRoberto Sandoval Castañeda inauguró el parque metropolitano y además contó con la presencia del medallista olímpico mexicano, Ernesto Cano, quien se mostró contento ante el anuncio hecho por el alcalde, a través del cual comunicó la construcción de una alberca semi-olímpica en las instalaciones de este centro recreativo.")
        p.binding.imagen7.visibility = View.GONE
        var geoPosicionGPS = GeoPoint(location.latitude, location.longitude)

        for(item in p.posicion){
            if(item.estoyEn(geoPosicionGPS)){
                p.binding.textView4.setText("Estás en ${item.nombre}")
                p.binding.descripcion2.setText("${item.descripcion}")
                p.binding.imagen7.visibility = View.VISIBLE
            }
        }

        agregarfotos(p.binding.textView4.text.toString())
    }

    private fun agregarfotos(texto: String) {
        if(texto=="Estás en cancha"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE


            //REDONDEAR ESQUINAS
            // get bitmap from assets folder
            val bitmap: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.cancha1);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 50.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen1.setImageDrawable(drawable)
            }
            //REDONDEAR ESQUINAS 2
            // get bitmap from assets folder
            val bitmap2: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.cancha2);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap2?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 80.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen2.setImageDrawable(drawable)
            }

        }
        if(texto=="Estás en negocios"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE



            //REDONDEAR ESQUINAS
            // get bitmap from assets folder
            val bitmap: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.negocios1);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 50.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen1.setImageDrawable(drawable)
            }
            //REDONDEAR ESQUINAS 2
            // get bitmap from assets folder
            val bitmap2: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.negocios2);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap2?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 80.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen2.setImageDrawable(drawable)
            }

        }
        if(texto=="Estás en sombrero"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE


            //REDONDEAR ESQUINAS
            // get bitmap from assets folder
            val bitmap: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.sombrero1);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 50.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen1.setImageDrawable(drawable)
            }
            //REDONDEAR ESQUINAS 2
            // get bitmap from assets folder
            val bitmap2: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.sombrero2);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap2?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 80.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen2.setImageDrawable(drawable)
            }
        }
        if(texto=="Estás en estacionamiento"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE


            //REDONDEAR ESQUINAS
            // get bitmap from assets folder
            val bitmap: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.estacionamiento1);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 50.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen1.setImageDrawable(drawable)
            }
            //REDONDEAR ESQUINAS 2
            // get bitmap from assets folder
            val bitmap2: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.estacionamiento2);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap2?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 80.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen2.setImageDrawable(drawable)
            }
        }
        if(texto=="Estás en entrada"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE


            //REDONDEAR ESQUINAS
            // get bitmap from assets folder
            val bitmap: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.entrada1);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 50.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen1.setImageDrawable(drawable)
            }
            //REDONDEAR ESQUINAS 2
            // get bitmap from assets folder
            val bitmap2: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.entrada2);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap2?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 80.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen2.setImageDrawable(drawable)
            }
        }
        if(texto=="Estás en fuentes"){
            p.binding.imagen1.visibility = View.VISIBLE
            p.binding.imagen2.visibility = View.VISIBLE
            p.binding.imagen3.visibility = View.GONE


            //REDONDEAR ESQUINAS
            // get bitmap from assets folder
            val bitmap: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.fuentes1);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 50.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen1.setImageDrawable(drawable)
            }
            //REDONDEAR ESQUINAS 2
            // get bitmap from assets folder
            val bitmap2: Bitmap? = BitmapFactory.decodeResource(p.getResources(),
                R.drawable.fuentes2);

            // convert the bitmap to rounded corners bitmap drawable
            bitmap2?.apply {
                val drawable = roundedCorners(
                    context = p.applicationContext,
                    radius = 80.dpToPixels(p.applicationContext)
                )
                // show the transparent rounded corners bitmap drawable
                p.binding.imagen2.setImageDrawable(drawable)
            }
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



    //PARA REDONDEAR ESQUINAS
    // extension function to get rounded corners bitmap drawable from bitmap
    fun Bitmap.roundedCorners(
        context: Context,
        radius: Float = 25F
    ): RoundedBitmapDrawable {
        return RoundedBitmapDrawableFactory.create(
            context.resources, this
        ).apply {
            setAntiAlias(true)
            // sets the corner radius to be applied when drawing the bitmap
            cornerRadius = radius
        }
    }


    // extension function to convert dp to equivalent pixels
    fun Int.dpToPixels(context: Context):Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    )


    // extension function to get bitmap from assets
    fun Context.assetsToBitmap(fileName: String): Bitmap?{
        return try {
            with(assets.open(fileName)){
                BitmapFactory.decodeStream(this)
            }
        } catch (e: IOException) { null }
    }

}