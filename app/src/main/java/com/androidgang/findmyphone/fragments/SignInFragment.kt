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
import com.androidgang.findmyphone.databinding.FragmentSignInBinding
import com.androidgang.findmyphone.utils.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class SignInFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_sign_in -> {
                if (binding.etEmailSignIn.text!!.isNotEmpty()){
                    var service = NetworkService.Factory.create()
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            try {
                                val result = service.getUser(binding.etEmailSignIn.text.toString())
                                if (result != null){
                                    Log.i("ServiceNetwork", "Sign in - user is got: ${result.toString()}")
                                    navController.navigate(R.id.action_signInFragment_to_mapsFragment , bundleOf("user" to result))

                                } else{
                                    Toast.makeText(requireContext(), "Incorrect email", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception){
                                e.printStackTrace()
                                Log.i("ServiceNetwork", "Sign in - error getting user")
                            }

                        }
                    }
                } else{
                    Toast.makeText(requireContext(), "Email must be filled", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

}