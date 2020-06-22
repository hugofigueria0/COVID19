package pt.ipg.covid_19;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdaptadorMovimentos extends RecyclerView.Adapter<AdaptadorMovimentos.ViewHolderMovimento>{
    private final Context context;
    private Cursor cursor = null;

    public void setCursor(Cursor cursor) {
        if (cursor != this.cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public AdaptadorMovimentos(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public AdaptadorMovimentos.ViewHolderMovimento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemMovimento = LayoutInflater.from(context).inflate(R.layout.item_movimentos, parent, false);

        return new ViewHolderMovimento(itemMovimento);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovimento holder, int position) {
        cursor.moveToPosition(position);
        MovimentoModel movimentoModel = Converte.cursorToMovimento(cursor);
        holder.setMovimento(movimentoModel);
    }


    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    private ViewHolderMovimento viewHolderMovimentoSelecionado = null;

    public class ViewHolderMovimento extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MovimentoModel movimentoModel = null;

        private final TextView textViewDataEntrada;
        private final TextView textViewDataSaida;
        private final TextView textViewData;

        public ViewHolderMovimento(@NonNull View itemView) {
            super(itemView);

            textViewDataEntrada = (TextView)itemView.findViewById(R.id.dataEntradaEditar);
            textViewDataSaida = (TextView)itemView.findViewById(R.id.dataSaidaEditar);
            textViewData = (TextView)itemView.findViewById(R.id.selectDateEditar);

            itemView.setOnClickListener(this);
        }

        public void setMovimento(MovimentoModel movimentoModel) {
            this.movimentoModel = movimentoModel;

            textViewDataEntrada.setText(String.valueOf(movimentoModel.getHoraEntrada()));
            textViewDataSaida.setText(String.valueOf(movimentoModel.getHoraSaida()));
            textViewData.setText(String.valueOf(movimentoModel.getData()));
        }

        @Override
        public void onClick(View v) {
            if (viewHolderMovimentoSelecionado != null) {
                viewHolderMovimentoSelecionado.desSeleciona();
            }

            viewHolderMovimentoSelecionado = this;

            ((menu_ver_pessoas) context).atualizaOpcoesMenu();

            seleciona();


        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorAccent);
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }
    }

}
