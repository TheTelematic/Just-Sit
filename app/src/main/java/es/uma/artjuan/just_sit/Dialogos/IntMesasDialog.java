package es.uma.artjuan.just_sit.Dialogos;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import es.uma.artjuan.just_sit.R;

/**
 * Created by Juanca on 18/05/2017.
 */

public class IntMesasDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createIntMesas();
    }

    public AlertDialog createIntMesas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_intmesas, null);

        builder.setView(v);

        Button signup = (Button) v.findViewById(R.id.crear_boton);
        //Button signin = (Button) v.findViewById(R.id.entrar_boton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear Cuenta...
                dismiss();
            }
        });
        return builder.create();
    }
}
