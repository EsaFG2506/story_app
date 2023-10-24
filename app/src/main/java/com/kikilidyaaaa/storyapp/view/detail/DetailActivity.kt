package com.kikilidyaaaa.storyapp.view.detail

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.kikilidyaaaa.storyapp.R
import com.kikilidyaaaa.storyapp.data.response.ListStoryItem
import com.kikilidyaaaa.storyapp.databinding.ActivityDetailBinding
import com.kikilidyaaaa.storyapp.utils.Result
import com.kikilidyaaaa.storyapp.view.StoryViewModelFactory
import com.kikilidyaaaa.storyapp.view.welcome.WelcomeActivity
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupAction()
    }

    private fun setupAction() {
        val storyItem = intent.getParcelableExtra<ListStoryItem>("storyItem")
        Log.d("DetailActivity", "Received id: $storyItem")
        if (storyItem != null) {
            showLoading(true)
            detailViewModel.getDetailStories(storyItem.id).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            val storyDetail = result.data.story
                            if (storyDetail != null) {
                                Log.d("DetailActivity", "Received story detail: $storyDetail")
                                Picasso.get().load(storyDetail.photoUrl).into(binding.ivDetailPhoto)
                                binding.tvDetailName.text = storyDetail.name
                                binding.tvDetailDescription.text = storyDetail.description
                                Log.d(ContentValues.TAG, "result: ${result.data.story}")
                            }
                        }
                        is Result.Error -> {
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.action_logout -> {
                detailViewModel.logout()
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}