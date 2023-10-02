package com.example.electrorui.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.electrorui.db.PrefManager
import com.example.electrorui.ui.StartActivity
import com.example.electrorui.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null

    private lateinit var prefManager: PrefManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        prefManager = PrefManager(requireContext())

        binding.textViewUserName.setText(prefManager.getUsername())

        binding.buttonCerrarSesion.setOnClickListener {
            Toast.makeText(requireContext(), "Cerrar Sesión", Toast.LENGTH_LONG).show()

            val loginIntent = Intent(requireContext(), StartActivity::class.java)
            prefManager.removeData()
            startActivity(loginIntent)

        }
        binding.btnEditarInfo.setOnClickListener {
            Toast.makeText(requireContext(), "Funcion por implementar", Toast.LENGTH_SHORT).show()
        }
        binding.btnAsistencia.setOnClickListener {
            Toast.makeText(requireContext(), "Funcion por implementar", Toast.LENGTH_SHORT).show()
        }
        binding.btnQuestion.setOnClickListener {
            Toast.makeText(requireContext(), "Funcion por implementar", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}