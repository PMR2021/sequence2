package com.ec.sequence2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ec.sequence2.data.model.Post
import com.ec.sequence2.R
import com.ec.sequence2.data.DataProvider
import com.ec.sequence2.ui.adapter.PostAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        loadAndDisplayPosts()
    }

    private fun loadAndDisplayPosts() {
        showProgress(true)
        DataProvider.getPostFromApi(
            onSuccess = { posts ->
                runOnUiThread {
                    postAdapter.show(posts)
                    showProgress(false)
                }
            },
            onError = {
                showProgress(false)
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        )

    }

    private fun showProgress(show: Boolean) {
        val progress = findViewById<View>(R.id.progress)
        val list = findViewById<View>(R.id.list)
        progress.isVisible = show
        list.isVisible = !show
    }

    private fun setupRecyclerView() {
        val recyclerview = findViewById<RecyclerView>(R.id.list)
        postAdapter = PostAdapter(actionListener = object : PostAdapter.ActionListener {
            override fun onItemClicked(post: Post) {

                Toast.makeText(
                    this@MainActivity,
                    "Item Clicked ${post.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onImageClicked() {
                loadAndDisplayPosts()
                Toast.makeText(this@MainActivity, "Image Clicked ", Toast.LENGTH_SHORT).show()

            }

        })

        recyclerview.adapter = postAdapter
        recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }


}