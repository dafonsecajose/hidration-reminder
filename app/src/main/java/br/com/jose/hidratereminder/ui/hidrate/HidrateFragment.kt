package br.com.jose.hidratereminder.ui.hidrate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.core.createDialog
import br.com.jose.hidratereminder.core.createProgressDialog
import br.com.jose.hidratereminder.databinding.FragmentHidrateBinding
import br.com.jose.hidratereminder.presentation.HidrateViewModel
import br.com.jose.hidratereminder.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HidrateFragment : Fragment() {

    private val dialog by lazy { requireActivity().createProgressDialog() }
    private val viewModel by viewModel<HidrateViewModel>()
    private var _binding: FragmentHidrateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHidrateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStateObserver()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnDrink.setOnClickListener {
            viewModel.save()
        }
    }

    private fun setupStateObserver() {
        viewModel.state.observe(viewLifecycleOwner){
            when (it){
                is HidrateViewModel.State.Error -> {
                    dialog.dismiss()
                    requireActivity().createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                HidrateViewModel.State.Loading -> dialog.show()
                is  HidrateViewModel.State.Success -> {

                }
                HidrateViewModel.State.Saved -> {
                    dialog.dismiss()
                }
            }
        }

        viewModel.settings.observe(viewLifecycleOwner) {
            setupSettings(it)
        }

        viewModel.totalDrink.observe(viewLifecycleOwner){
            binding.tvDrunks.text = "${it.toInt()}"
        }
    }

    private fun setupSettings(settings: Settings) {
        binding.run {
            btnDrink.text = "${settings.amountToDrink}ml"
            tvTotalDrinks.text = "${settings.weight.times(35).toInt()}"
        }
        dialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}