package com.example.artbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artbook.Room.ArtDao
import com.example.artbook.Room.ArtDatabase
import com.example.artbook.Room.ArtModel
import com.example.artbook.adapters.RecylerViewAdapter
import com.example.artbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var addArtBtn : Button
    private lateinit var db : ArtDao
    private lateinit var recylerView : RecyclerView
    private lateinit var list : MutableList<ArtModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = ArtDatabase.getInstance(this)?.artDao()!!
        recylerView = binding.recyclerView
        addArtBtn = binding.addArtBtn
        addArtBtn.setOnClickListener(addArtBtnClick)
    }

    override fun onStart() {
        super.onStart()

        list = db.getAll()
        var artAdapter = RecylerViewAdapter(list)
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.adapter = artAdapter
    }

    val addArtBtnClick = View.OnClickListener {
        val intent = Intent(this@MainActivity,ArtAddActivity::class.java)
        intent.putExtra("info","new")
        startActivity(intent)
    }
}