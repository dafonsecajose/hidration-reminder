package br.com.jose.hydrationreminder.ui.settings

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.jose.hydrationreminder.R
import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.core.createDialog
import br.com.jose.hydrationreminder.core.createProgressDialog
import br.com.jose.hydrationreminder.core.text
import br.com.jose.hydrationreminder.databinding.FragmentSettingsBinding
import br.com.jose.hydrationreminder.databinding.QuantityPickerBinding
import br.com.jose.hydrationreminder.databinding.WeightPickerBinding
import br.com.jose.hydrationreminder.presentation.SettingsViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingsFragment : Fragment() {

    private val dialog by lazy { requireActivity().createProgressDialog() }
    private val viewModel by viewModel<SettingsViewModel>()
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupSetting(settings: Settings) {
        binding.run {
            tilWeightHistory.text = settings.weight.toString()
            tilBeginTimeHistory.text = settings.beginTime.toString()
            tilEndTimeHistory.text = settings.endTime.toString()
            tilQuantityDrinkHistory.text = settings.amountToDrink.toString()
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner){
            when (it){
                is SettingsViewModel.State.Error -> {
                    dialog.dismiss()
                    requireActivity().createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                SettingsViewModel.State.Loading -> dialog.show()
                is SettingsViewModel.State.Success -> {
                    dialog.dismiss()
                }
                SettingsViewModel.State.Updated -> {
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Configurações Atualizadas", Toast.LENGTH_SHORT).show()
                    goToHidrate()
                }
            }
        }
        viewModel.settings.observe(viewLifecycleOwner){
            setupSetting(it)
        }
    }

    private fun goToHidrate() {
        val direction = SettingsFragmentDirections.actionNavigationSettingsToNavigationHidrate()
        val controller = findNavController()
        controller.navigate(direction)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListeners() {
        with(binding) {
            tilWeightHistory.getWeightPicker()
            tilBeginTimeHistory.getTimePicker()
            tilEndTimeHistory.getTimePicker()
            tilQuantityDrinkHistory.getQuantityDrinkPicker()


            btnSaveHistory.setOnClickListener {
                val settingData = arrayListOf(
                    tilWeightHistory.text,
                    tilBeginTimeHistory.text,
                    tilEndTimeHistory.text,
                    tilQuantityDrinkHistory.text
                    )
                if(validateField()) {
                    viewModel.updateSettings(settingData)
                }
            }
        }
    }

    private fun validateField(): Boolean {
       binding.run {
           return when {
               tilWeightHistory.text.isNullOrEmpty() || tilWeightHistory.text == "0.0" -> {
                   handleError(tilWeightHistory, getString(R.string.warning_weight))
               }
               tilBeginTimeHistory.text.isNullOrEmpty() -> {
                   handleError(tilBeginTimeHistory, getString(R.string.warning_begin_time))
               }
               tilEndTimeHistory.text.isNullOrEmpty() -> {
                   handleError(tilBeginTimeHistory, getString(R.string.warning_end_time))
               }
               tilQuantityDrinkHistory.text.isNullOrEmpty() || tilQuantityDrinkHistory.text == "0" -> {
                   handleError(tilQuantityDrinkHistory, getString(R.string.warning_quantity_drink))
               }
               else -> true
           }
       }
    }
    private fun handleError(field: TextInputLayout, message: String): Boolean {
        field.error = message
        return false
    }

    private fun TextInputLayout.getWeightPicker() {
        this.editText?.setOnClickListener {
            val weightBind = WeightPickerBinding.inflate(layoutInflater)
            weightBind.firstWeight.minValue = 0
            weightBind.firstWeight.maxValue = 300
            weightBind.secondWeight.minValue = 0
            weightBind.secondWeight.maxValue = 10
            val weight = this.text.split(".").toTypedArray()
            weightBind.firstWeight.value = weight[0].toInt()
            weightBind.secondWeight.value = weight[1].toInt()
            requireActivity().createDialog {
                this.setTitle(getString(R.string.label_weight))
                this.setView(weightBind.root)
                this.setPositiveButton(getString(android.R.string.ok)) { dialog, which ->
                    this@getWeightPicker.text = "${weightBind.firstWeight.value}.${weightBind.secondWeight.value}"
                }
                this.setNegativeButton(getString(android.R.string.cancel), null)
            }.show()
        }
    }


    private fun TextInputLayout.getQuantityDrinkPicker() {
        this.editText?.setOnClickListener {
            val quantityBind = QuantityPickerBinding.inflate(layoutInflater)
            quantityBind.quantityPicker.minValue = 0
            quantityBind.quantityPicker.maxValue = 1000
            val steeps = 100
            quantityBind.quantityPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                picker.value = when {
                    newVal < oldVal -> oldVal - steeps
                    else -> oldVal + steeps
                }
            }
            quantityBind.quantityPicker.value = this.text.toInt()
            requireActivity().createDialog {
                this.setTitle(getString(R.string.label_quantity_drink))
                this.setView(quantityBind.root)
                this.setPositiveButton(getString(android.R.string.ok)) { dialog, which ->
                    this@getQuantityDrinkPicker.text = quantityBind.quantityPicker.value.toString()
                }
                this.setNegativeButton(getString(android.R.string.cancel), null)
            }.show()
        }
    }

    private fun TextInputLayout.getTimePicker() {
        this.editText?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timeInputPicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build()
            timeInputPicker.addOnPositiveButtonClickListener {
                val minute = if (timeInputPicker.minute in 0..9) "0${timeInputPicker.minute}" else timeInputPicker.minute
                val hour = if (timeInputPicker.hour in 0..9) "0${timeInputPicker.hour}" else timeInputPicker.hour
                this.text = "$hour:$minute"
            }

            timeInputPicker.show(requireActivity().supportFragmentManager, null)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
