package com.example.testlogin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testlogin.R;
import com.example.testlogin.models.EmergencyContact;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmergencyContactsListAdapter extends RecyclerView.Adapter<EmergencyContactsListAdapter.ViewHolder> {
    private List<EmergencyContact> emergencyContacts;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtECNameItem;
        private TextView txtECNumberItem;

        ViewHolder(View view) {
            super(view);
            txtECNameItem = view.findViewById(R.id.txtECNameItem);
            txtECNumberItem = view.findViewById(R.id.txtECNumberItem);
        }

        TextView getTxtECNameItemView() {
            return txtECNameItem;
        }
        TextView getTxtECNumberItemView() {
            return txtECNumberItem;
        }
    }

    /**
     * Inicializa el contenido de nuestra lista
     *
     * @param emergencyContacts ArrayList<EmergencyContact> contiene la lista de contactos de emergencias
     */
    public EmergencyContactsListAdapter(List<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.emergency_contact_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        EmergencyContact emergencyContact = emergencyContacts.get(position);

        viewHolder.getTxtECNameItemView().setText(emergencyContact.getName());
        viewHolder.getTxtECNumberItemView().setText(String.valueOf(emergencyContact.getPhoneNumber()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return emergencyContacts.size();
    }
}
