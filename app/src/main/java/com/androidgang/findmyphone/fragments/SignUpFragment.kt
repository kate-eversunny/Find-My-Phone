package com.androidgang.findmyphone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.androidgang.findmyphone.R
import com.androidgang.findmyphone.database.MetricsDB
import com.androidgang.findmyphone.database.MetricsDao
import com.androidgang.findmyphone.databinding.FragmentSignUpBinding
import com.androidgang.findmyphone.models.Device
import com.androidgang.findmyphone.models.User
import com.androidgang.findmyphone.utils.NetworkService
import com.androidgang.findmyphone.utils.metrics.MetricUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class SignUpFragment : Fragment(), View.OnClickListener, MetricUtils {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.btnSignUp.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_sign_up -> {
                if(binding.etEmailSignUp.text!!.isNotEmpty()){
                    val service = NetworkService.Factory.create()
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            try {
                                var user = User()
                                user.email = binding.etEmailSignUp.text.toString()
                                val device = Device()
                                addMetricsToDevice(device, requireContext())
                                user.devices.add(device)
                                service.postNewUser(user)
                                Log.i("ServiceNetwork", "Sign Up - user is send")
                                val metricsDao = MetricsDB.getInstance(requireContext().applicationContext).metricsDao
                                metricsDao.saveMetrics(device.metrics[0])
                                navController.navigate(R.id.action_signUpFragment_to_mapsFragment, bundleOf("user" to user))


                            } catch (e: Exception){
                                e.printStackTrace()
                                Log.i("ServiceNetwork", "FAILED")

                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Email must be filled", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}