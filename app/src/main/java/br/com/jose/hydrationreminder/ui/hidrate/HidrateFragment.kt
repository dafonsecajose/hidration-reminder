package br.com.jose.hydrationreminder.ui.hidrate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.core.addNumberDrink
import br.com.jose.hydrationreminder.core.createDialog
import br.com.jose.hydrationreminder.core.createProgressDialog
import br.com.jose.hydrationreminder.databinding.FragmentHidrateBinding
import br.com.jose.hydrationreminder.presentation.HidrateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HidrateFragment : Fragment() {

    private val dialog by lazy { requireActivity().createProgressDialog() }
    private val viewModel by viewModel<HidrateViewModel>()
    private var _binding: FragmentHidrateBinding? = null
    private var drunk: Double = 0.0
    private var drinkTurn: Int = 0


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

        lifecycle.addObserver(viewModel)
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

                HidrateViewModel.State.Saved -> {
                    dialog.dismiss()
                }
            }
        }

        viewModel.settings.observe(viewLifecycleOwner) {
            setupSettings(it)
        }

        viewModel.totalDrink.observe(viewLifecycleOwner){
            if(drunk < it){
                binding.tvDrunks.addNumberDrink(drunk.toInt(), it.toInt())
                drunk = it
            } else if (drunk < 1.0){
                binding.tvDrunks.text = "0"
            }

        }
    }

    private fun setupSettings(settings: Settings) {
        binding.run {
            btnDrink.text = "${settings.amountToDrink}ml"
            drinkTurn = settings.amountToDrink
            tvTotalDrinks.addNumberDrink(0, settings.weight.times(35).toInt())
        }
        dialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}