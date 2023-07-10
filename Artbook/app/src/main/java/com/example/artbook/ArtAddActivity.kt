package com.example.artbook

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.artbook.Room.ArtDao
import com.example.artbook.Room.ArtDatabase
import com.example.artbook.Room.ArtModel
import com.example.artbook.databinding.ActivityArtAddBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.lang.Exception

class ArtAddActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArtAddBinding
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var activityLauncher : ActivityResultLauncher<Intent>
    private var selectedBitmap : Bitmap? = null
    private lateinit var saveBtn : Button
    private lateinit var db : ArtDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        registerLauncher()
        db = ArtDatabase.getInstance(this)?.artDao()!!
        saveBtn = binding.saveArtBtn
        saveBtn.setOnClickListener(saveBtnClick)

        val info = intent.getStringExtra("info")

        if(info=="old"){
            binding.saveArtBtn.visibility = View.INVISIBLE
            binding.artNameEditText.isEnabled = false
            binding.artistNameEditText.isEnabled = false
            binding.artYearEditText.isEnabled = false
            val selectedID = intent.getIntExtra("nid",0)
            val artModel = db.getById(selectedID)

            binding.artNameEditText.setText(artModel.artName)
            binding.artistNameEditText.setText(artModel.artArtist)
            binding.artYearEditText.setText(artModel.artYear)
            val byteArray = artModel.artImage
            val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            binding.artImageView.setImageBitmap(bitmap)
        }
        else if(info == "new"){
            binding.artNameEditText.text.clear()
            binding.artistNameEditText.text.clear()
            binding.artYearEditText.text.clear()
            binding.saveArtBtn.visibility = View.VISIBLE
            binding.artImageView.setImageResource(R.drawable.noimg)
        }

    }
    fun selectImage(view : View) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_MEDIA_IMAGES)!=PackageManager.PERMISSION_GRANTED){
                //permission has not given
                //-----------
                //rationale
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(view,"Permission needed for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }).show()
                }
                else{
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
            else{
                //permission has given
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityLauncher.launch(intent)
            }
        }
        else{
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                //permission has not given
                //-----------
                //rationale
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"Permission needed for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }).show()
                }
                else{
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            else{
                //permission has given
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityLauncher.launch(intent)
            }
        }
    }

    private fun registerLauncher() {
        //take image part
        activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == RESULT_OK){
                val intentFromResult = result.data
                if(intentFromResult!=null){
                    val imageData = intentFromResult.data
                    if(imageData!=null){
                        try {
                            if(Build.VERSION.SDK_INT>28){
                                val source = ImageDecoder.createSource(this@ArtAddActivity.contentResolver,imageData)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.artImageView.setImageBitmap(selectedBitmap)
                            }
                            else{
                                selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageData)
                                binding.artImageView.setImageBitmap(selectedBitmap)
                            }
                        }
                        catch (e : Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }
        }


        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){result ->
            //permission granted ( izin verilirse)
            if(result){
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityLauncher.launch(intentToGallery)
            }
            else{
                //permission denied
                Toast.makeText(this,"Permission Needed!",Toast.LENGTH_LONG).show()
            }
        }

    }

    fun makeSmallerBitmap(image : Bitmap,maximumSize : Int) : Bitmap{
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble()/height.toDouble()

        if(bitmapRatio>1){
            width = maximumSize
            val scaledHeight = width/bitmapRatio
            height = scaledHeight.toInt()
        }
        else{
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,width,height,true)
    }

    var saveBtnClick = View.OnClickListener {
        val artName = binding.artNameEditText.text.toString()
        val artArtist = binding.artistNameEditText.text.toString()
        val artYear = binding.artYearEditText.text.toString()

        if(selectedBitmap !=null){
            val smallerBitmap = makeSmallerBitmap(selectedBitmap!!,200)
            val outputStream = ByteArrayOutputStream()
            smallerBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()

            try {
                val artModel = ArtModel(
                    artArtist = artArtist, artName = artName, artYear = artYear, artImage = byteArray,
                    nid = null)

                db.insert(artModel)
                Toast.makeText(this,"Saved Successfully",Toast.LENGTH_LONG).show()
                finish()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}