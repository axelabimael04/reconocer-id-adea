package com.example.reconocer_id_android

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer
import com.reconosersdk.reconosersdk.ui.bioFacial.views.LivePreviewActivity
import com.reconosersdk.reconosersdk.utils.Constants
import com.reconosersdk.reconosersdk.utils.IntentExtras
import kotlinx.android.synthetic.main.activity_main.view.*


const val FACE = 3



class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        LibraryReconoSer.init(application, this, "c6dd6e1e-2d1c-42a5-9e2a-bd065ca86844",
            "")

        findViewById<Button>(R.id.boton1).setOnClickListener{

            val intent = Intent(this, LivePreviewActivity::class.java)
            intent.putExtra(IntentExtras.GUID_CIUDADANO, "c6dd6e1e-2d1c-42a5-9e2a-bd065ca86844")
            intent.putExtra(IntentExtras.TYPE_DOCUMENT, "CURP")
            intent.putExtra(IntentExtras.NUM_DOCUMENT, "AUMA980430HSRGNX06")
            intent.putExtra(IntentExtras.SAVE_USER, "AUMA980430HSRGNX06-2022-06-14")
            intent.putExtra(IntentExtras.VALIDATE_FACE, true)
            intent.putExtra(IntentExtras.TIME, 8)

            startActivityForResult(intent, FACE)
            println("===== CLICK")

//            resultLauncher.launch(intent)

//            val intent = Intent(this, LivePreviewActivity::class.java)
//
//
//
//
//            intent.putExtra(IntentExtras.GUID_CIUDADANO, "");
//            //Obligatorio para convenio 0
//
//            //Mandar el curp
//            intent.putExtra(IntentExtras.TYPE_DOCUMENT, "CURP");  //Obligatorio para convenio 0 y 3
//
//            //AUMA980430HSRGNX06
//            intent.putExtra(IntentExtras.NUM_DOCUMENT, "AUMA980430HSRGNX06");  //Obligatorio para convenio 0 y 3
//
//
//            intent.putExtra(IntentExtras.SAVE_USER, "AUMA980430HSRGNX06-2022-06-14"); //Obligatorio
//            // para convenio 0
//            intent.putExtra(IntentExtras.VALIDATE_FACE, true);
//
////            resultLauncher.launch(intent)
//            startActivityForResult(intent, FACE);
        }
    }

//    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK){
//                result.data!!.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R) //path foto tomada
//                result.data!!.getStringExtra(IntentExtras.VALIDATE_FACE) //si enviaste true en el
//            // intent
//                // IntentExtras.VALIDATE_FACE
//            }else if (result.resultCode == IntentExtras.ERROR_INTENT){
//                val errors = result.data!!.getStringExtra(IntentExtras.ERROR_MSG)  //mensaje de
//                // error
//                println("==== $errors")
////                data!!.getExtras()!!.getParcelable(IntentExtras.ERROR_SDK) //errores generados en el
//                // SDK
//            }
//
//    }

    override fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        println("LLEGA Y EL CODIGO ES: %s $resultCode");

        :String


        if (requestCode == FACE) {
            findViewById<Button>(R.id.boton1).isEnabled = true;
            if (resultCode == 2) {
                val pathFace = if (data != null) data.getStringExtra(
                     IntentExtras
                         .PATH_FILE_PHOTO_R
                 )!! else ""
                val validation = data!!.getStringExtra(IntentExtras.VALIDATE_FACE)

//                data.let {
//                    println("==== path 2 $pathFace")
//                    Glide.with(this).load(pathFace).into(findViewById<ImageView>(R.id.imgBiometria));
//                }

                println("=== path de la foto $pathFace")
                println("=== face validation $validation")


                // IntentExtras.VALIDATE_FACE

                onRespondFace(data)
            }
//            else if (resultCode == IntentExtras.ERROR_INTENT){
//                val errors = data!!.getStringExtra(IntentExtras.ERROR_MSG)
//                println("==== error: $errors")
////                data!!.getExtras()!!.getParcelable(IntentExtras.ERROR_SDK)
//
//
//
//            }

            if (data == null) {
                val data = Intent()
                data.putExtra(IntentExtras.ERROR_MSG, Constants.ERROR_IMAGE_PROCESS)
                data.putExtra(IntentExtras.ERROR_SDK, Constants.ERROR_R106)
                onErrorIntent(data)
            }
        }
    }

    private fun onErrorIntent(data:Intent ) {
        if (data!!.getExtras()!!.containsKey(IntentExtras.ERROR_MSG)) {
            findViewById<TextView>(R.id.mensaje).mensaje.setText(data.getStringExtra(IntentExtras
                .ERROR_MSG));
        } else if (data!!.getExtras()!!.get(IntentExtras.ERROR_SDK) != null) {
//            findViewById<TextView>(R.id.mensaje).mensaje.setText(JsonUtils.stringObject(data
//                .getExtras()!!.getParcelable(IntentExtras.ERROR_SDK)));
        }
    }

    private fun onRespondFace(data: Intent?) {


            val pathFace = if (data != null) data.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R)
            else ""
            val isvalido = if (data != null) data.getStringExtra(IntentExtras.VALIDATE_FACE) else ""
            Glide.with(this).load(pathFace).into(findViewById<ImageView>(R.id.imgBiometria))
            findViewById<TextView>(R.id.mensaje).mensaje.setText(pathFace.toString() + "\n" + isvalido)


    }

//    private fun analyzeFace(data: Intent?) {
//        pathFace = data!!.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R)
//        val bitmap = BitmapFactory.decodeFile(pathFace)
//        Glide.with(this).load(pathFace).into<Target<Drawable>>(binding.imgResult)
//        if (data != null && Objects.requireNonNull(data.extras)
//                .containsKey(IntentExtras.REKOGNITION)
//        ) {
//            pathFace = if (data != null) data.getStringExtra(IntentExtras.PATH_FILE_PHOTO_R) else ""
//            val rekognition = data.getDoubleExtra(IntentExtras.REKOGNITION, 0.0)
//            val txt = "SIMILITUD : $rekognition"
//            binding.txtResult.setText(txt)
//        }
//    }
}