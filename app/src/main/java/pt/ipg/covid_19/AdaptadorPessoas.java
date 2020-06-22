package pt.ipg.covid_19;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdaptadorPessoas extends RecyclerView.Adapter<AdaptadorPessoas.ViewHolderPessoas> {

    private final Context context;
    private Cursor cursor = null;

    public void setCursor(Cursor cursor) {
        if (cursor != this.cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public AdaptadorPessoas(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPessoas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPessoas = LayoutInflater.from(context).inflate(R.layout.item_pessoas, parent, false);

        return new ViewHolderPessoas(itemPessoas);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderPessoas holder, int position) {
        cursor.moveToPosition(position);
        PessoasModel pessoasModel = Converte.cursorToPessoas(cursor);
        holder.setPessoas(pessoasModel);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    private ViewHolderPessoas viewHolderPessoaSelecionado = null;

    public class ViewHolderPessoas extends RecyclerView.ViewHolder implements View.OnClickListener {
        private PessoasModel pessoasModel = null;

        private final TextView NomePessoa;
        private final TextView TipoPessoa;

        public ViewHolderPessoas(@NonNull View itemView) {
            super(itemView);

            NomePessoa = (TextView)itemView.findViewById(R.id.NomePessoa);
            TipoPessoa = (TextView)itemView.findViewById(R.id.TipoPessoa);

            itemView.setOnClickListener(this);
        }

        public void setPessoas(PessoasModel pessoasModel) {
            this.pessoasModel = pessoasModel;

            NomePessoa.setText(pessoasModel.getNome());
            TipoPessoa.setText(String.valueOf(pessoasModel.getTipoPessoa()));
        }



        @Override
        public void onClick(View v) {
            if (viewHolderPessoaSelecionado == this) {
                return;
            }

            if (viewHolderPessoaSelecionado != null) {
                viewHolderPessoaSelecionado.desSeleciona();
            }

            viewHolderPessoaSelecionado = this;
            seleciona();

            menu_ver_pessoas menu_ver_pessoas = (menu_ver_pessoas) AdaptadorPessoas.this.context;
            menu_ver_pessoas.pessoaAlterada(pessoasModel);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorPrimaryDark);
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }
    }

}
