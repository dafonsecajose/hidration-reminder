package br.com.jose.hidratereminder.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.core.createDialog
import br.com.jose.hidratereminder.core.createProgressDialog
import br.com.jose.hidratereminder.core.text
import br.com.jose.hidratereminder.databinding.FragmentSettingsBinding
import br.com.jose.hidratereminder.presentation.SettingsViewModel
import com.google.android.material.snackbar.Snackbar
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
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupListeners()
        setupObservers()
        lifecycle.addObserver(viewModel)

    }

    private fun setupSetting(settings: Settings) {
        binding.run {
            tilWeightHistory.text = settings.weight.toString()
            tilBeginTimeHistory.text = settings.beginTime.toString()
            tilEndTimeHistory.text = settings.beginTime.toString()
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
                    setupSetting(it.settings)
                    dialog.dismiss()
                }
                SettingsViewModel.State.Updated -> {
                    Snackbar.make(binding.root, "Setting Atualizado", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupSpinner() {
        val amountToDrink = arrayListOf(100, 200, 300, 400, 500)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, amountToDrink)
        binding.spnQuantityDrinkHistory.setAdapter(adapter)
    }

    private fun setupListeners() {
        with(binding) {
            tilBeginTimeHistory.getTimePicker()
            tilEndTimeHistory.getTimePicker()
            btnSaveHistory.setOnClickListener {
                val settingData = arrayListOf(
                    tilWeightHistory.text,
                    tilBeginTimeHistory.text,
                    tilEndTimeHistory.text,
                    tilQuantityDrinkHistory.text
                    )
                viewModel.updateSettings(settingData)
            }
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