package pt.ipg.covid_19;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdaptadorInfectado extends RecyclerView.Adapter<AdaptadorInfectado.ViewHolderInfectado>{

    private final Context context;
    private Cursor cursor = null;

    public AdaptadorInfectado(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        if (cursor != this.cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolderInfectado onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemInfecatdo = LayoutInflater.from(context).inflate(R.layout.item_infectado, parent, false);

        return new ViewHolderInfectado(itemInfecatdo);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInfectado holder, int position) {
        cursor.moveToPosition(position);
        InfectadoModel infectadoModel = Converte.cursorToInfectado(cursor);
        holder.setInfectado(infectadoModel);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    public class ViewHolderInfectado extends RecyclerView.ViewHolder {
        private InfectadoModel infectadoModel = null;

        private final TextView textViewInfectado;
        private final TextView textViewNomePessoa;

        public ViewHolderInfectado(@NonNull View itemView) {
            super(itemView);

            textViewInfectado = (TextView) itemView.findViewById(R.id.PositivoNegativoInfectado);
            textViewNomePessoa = (TextView) itemView.findViewById(R.id.NomeDaPessoaInfectado);


        }

        public void setInfectado(InfectadoModel infectadoModel) {
            this.infectadoModel = infectadoModel;

            textViewInfectado.setText(infectadoModel.getInfectado());
            textViewNomePessoa.setText(String.valueOf(infectadoModel.getNome_pessoa()));
        }
    }


}
