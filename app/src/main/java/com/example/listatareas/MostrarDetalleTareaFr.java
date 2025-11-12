package com.example.listatareas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.listatareas.databinding.FragmentMostrarDetalleTareaBinding;

public class MostrarDetalleTareaFr extends Fragment {

    private FragmentMostrarDetalleTareaBinding binding;
    private TareaViewModel tareaViewModel;
    private Tarea tareaSeleccionada;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMostrarDetalleTareaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tareaViewModel = new ViewModelProvider(requireActivity()).get(TareaViewModel.class);
        navController = Navigation.findNavController(view);

        // Observo la tarea seleccionada y mostrar sus datos
        tareaViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Tarea>() {
            @Override
            public void onChanged(Tarea tarea) {
                if (tarea != null) {
                    tareaSeleccionada = tarea;
                    // Pongo para que se pueda modificar dicha tarea
                    binding.nombre.setText(tarea.nombre);
                    binding.descripcion.setText(tarea.descripcion);
                    binding.valoracion.setRating(tarea.valoracion);
                }
            }
        });

        // Permito modificar la valoración desde la pantalla de detalle
        binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser && tareaSeleccionada != null) {
                    tareaViewModel.actualizar(tareaSeleccionada, rating);
                }
            }
        });

        // Añado el boton guardar para las modificaciones
        binding.guardar.setOnClickListener(v -> {
            if (tareaSeleccionada != null) {
                String nuevoNombre = binding.nombre.getText().toString();
                String nuevaDesc = binding.descripcion.getText().toString();

                if (!nuevoNombre.isEmpty()) {
                    tareaViewModel.modificar(tareaSeleccionada, nuevoNombre, nuevaDesc);
                    navController.popBackStack();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}