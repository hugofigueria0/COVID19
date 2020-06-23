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

    public AdaptadorMovimentos(Context context) {
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
    public ViewHolderMovimento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemMovimento = LayoutInflater.from(context).inflate(R.layout.item_movimentos, parent, false);

        return new ViewHolderMovimento(itemMovimento);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovimento holder, int position) {
        cursor.moveToPosition(position);
        MovimentoModel movimentoModel = Converte.cursorToMovimento(cursor);
        holder.setMovimentoModel(movimentoModel);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    public class ViewHolderMovimento extends RecyclerView.ViewHolder {
        private MovimentoModel movimentoModel = null;

        private final TextView textViewDadosNomeMovimento;
        private final TextView textViewHoraEntradaMovimento;
        private final TextView textViewHoraSaidaMovimenbto;
        private final TextView textViewDataMovimento;

        public ViewHolderMovimento(@NonNull View itemView) {
            super(itemView);

            textViewDadosNomeMovimento = (TextView)itemView.findViewById(R.id.textViewDadosNomeMovimento);
            textViewHoraEntradaMovimento = (TextView)itemView.findViewById(R.id.textViewHoraEntradaMovimento);
            textViewHoraSaidaMovimenbto = (TextView)itemView.findViewById(R.id.textViewHoraSaidaMovimenbto);
            textViewDataMovimento = (TextView)itemView.findViewById(R.id.textViewDataMovimento);


        }

        public void setMovimentoModel(MovimentoModel movimentoModel) {
            this.movimentoModel = movimentoModel;

            textViewDadosNomeMovimento.setText(String.valueOf(movimentoModel.getNome_pessoa()));
            textViewHoraEntradaMovimento.setText(movimentoModel.getHoraEntrada());
            textViewHoraSaidaMovimenbto.setText(movimentoModel.getHoraSaida());
            textViewDataMovimento.setText(movimentoModel.getData());
        }

    }

}
