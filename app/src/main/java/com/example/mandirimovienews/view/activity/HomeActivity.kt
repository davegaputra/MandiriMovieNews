package com.example.mandirimovienews.view.activity

import android.app.Fragment
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mandirimovienews.R
import com.example.mandirimovienews.databinding.ActivityHomeBinding
import com.example.mandirimovienews.view.fragment.MovieFragment
import com.example.mandirimovienews.view.fragment.SplashFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeActivity = this


        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.navView.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.fragments[0] is MovieFragment) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Are you sure you want to Exit")
            builder.setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, _ -> //TODO
                    dialog.dismiss()
                    homeActivity?.finish()
                })
            builder.setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, _ -> //TODO
                    dialog.dismiss()
                })
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            navController.popBackStack()
        }
    }

    companion object {
        lateinit var homeActivity: HomeActivity

        fun animate(hide: Boolean) {
            val bottom: BottomNavigationView = homeActivity.findViewById(R.id.nav_view)
            bottom.isVisible = homeActivity.navHostFragment.childFragmentManager.fragments[0] !is SplashFragment
            val moveY = if (hide) 3 * bottom.height else 0
            bottom.animate()
                .translationY(moveY.toFloat())
                .setStartDelay(100)
                .setDuration(400)
                .start()
        }
    }
}