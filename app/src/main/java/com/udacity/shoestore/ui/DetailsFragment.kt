package com.udacity.shoestore.ui
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.LoginViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        initViewModel()
        initObservers()

        changePictureShoeDetailPress("model_1")
        (activity as AppCompatActivity).supportActionBar?.show()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearShoeTemplate()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        binding.detailShoeDetailViewModel = viewModel
        binding.shoe = viewModel.shoe
    }

    private fun initObservers() {
        viewModel.eventSaveShoeDetailPress.observe(viewLifecycleOwner) {
            if (it) {
                saveShoeDetail()
                viewModel.saveShoeDetailComplete()
            }
        }

        viewModel.eventCancelShoeDetailPress.observe(viewLifecycleOwner) {
            if (it) {
                cancelShoeDetail()
                viewModel.cancelShoeDetailComplete()
            }
        }

        viewModel.eventSizeViewShoeDetailPress.observe(viewLifecycleOwner) { view ->
            view?.let {
                clearSizeShoeDetail()
                changeBackgroundSizeSelectedShoeDetail(view)
            }
        }

        viewModel.eventPictureShoeDetailPress.observe(
            viewLifecycleOwner
        ) { nameModelShoe ->
            nameModelShoe?.let {
                changePictureShoeDetailPress(nameModelShoe)
            }
        }

        viewModel.eventSaveFailByNameShoeDetail.observe(viewLifecycleOwner) {
            if (it) {
                val message = "The name of the shoe is required"
                showAlert(message)
                viewModel.saveFailByNameShoeDetailComplete()
            }
        }

        viewModel.eventSaveFailBySizeShoeDetail.observe(viewLifecycleOwner) {
            if (it) {
                val message = "The shoe's size is required"
                showAlert(message)
                viewModel.saveFailBySizeShoeDetailComplete()
            }
        }

        viewModel.eventSaveFailByNameCompanyShoeDetail
            .observe(viewLifecycleOwner) {
                if (it) {
                    val message = "The name of the company is required"
                    showAlert(message)
                    viewModel.saveFailByNameCompanyShoeDetailComplete()
                }
            }
    }

    private fun saveShoeDetail() {
        findNavController().popBackStack()
    }

    private fun cancelShoeDetail() {
        findNavController().popBackStack()
    }

    private fun clearSizeShoeDetail() {
        binding.eightButton.setBackgroundResource(R.drawable.rounded_circle_enabled)
        binding.nineButton.setBackgroundResource(R.drawable.rounded_circle_enabled)
        binding.tenButton.setBackgroundResource(R.drawable.rounded_circle_enabled)
        binding.elevenButton.setBackgroundResource(R.drawable.rounded_circle_enabled)
        binding.twelveButton.setBackgroundResource(R.drawable.rounded_circle_enabled)
    }

    private fun changeBackgroundSizeSelectedShoeDetail(view: View) {
        view.setBackgroundResource(R.drawable.rounded_circle_enabled)
    }

    private fun changePictureShoeDetailPress(nameModelShoe: String) {
        when (nameModelShoe) {
            "model_1" -> binding.imageShoe.setImageResource(R.drawable.model_1)
            "model_2" -> binding.imageShoe.setImageResource(R.drawable.model_2)
            "model_3" -> binding.imageShoe.setImageResource(R.drawable.model_3)
            "model_4" -> binding.imageShoe.setImageResource(R.drawable.model_4)
            "model_5" -> binding.imageShoe.setImageResource(R.drawable.model_5)
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())

        with(builder)
        {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK", null)
            show()
        }
    }
}