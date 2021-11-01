package br.com.jose.hydrationreminder.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.jose.hydrationreminder.R
import br.com.jose.hydrationreminder.core.createProgressDialog
import br.com.jose.hydrationreminder.databinding.ActivityMainBinding
import br.com.jose.hydrationreminder.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val dialog by lazy { createProgressDialog() }
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog.show()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        viewModel.settings.observe(this) { settings ->
            if (settings.beginTime.isEmpty() || settings.endTime.isEmpty() || settings.weight == 0.0
                || settings.amountToDrink == 0){
                navView.selectedItemId = R.id.navigation_settings
                navView.menu.forEach { it.isEnabled = false}
                dialog.dismiss()
            } else {
                navView.menu.forEach { it.isEnabled = true}
                dialog.dismiss()
            }
        }

        navView.setupWithNavController(navController)

    }
}