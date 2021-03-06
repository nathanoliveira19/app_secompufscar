package br.com.secompufscar.app.listaAtividades;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.secompufscar.app.AtividadeDetalhes;
import br.com.secompufscar.app.adapters.AtividadesAdapter;
import br.com.secompufscar.app.R;
import br.com.secompufscar.app.data.Atividade;
import br.com.secompufscar.app.data.DatabaseHandler;
import br.com.secompufscar.app.utilities.ClickListener;
import br.com.secompufscar.app.utilities.RecyclerTouchListener;


public class ListaAtividades extends Fragment {
    private static final String ARG_PARAM1 = "offset";

    protected int offset;


    public static List<Atividade> atividadeList = new ArrayList<>();
    private RecyclerView recycler_atividades;
    private AtividadesAdapter adapter;

    public ListaAtividades() {
        // Required empty public constructor
    }

    public static ListaAtividades newInstance(String param1) {
        ListaAtividades fragment = new ListaAtividades();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            offset = getArguments().getInt(ARG_PARAM1);
//        }
        new UpdateAtividades().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_atividades, container, false);
        recycler_atividades = (RecyclerView) view.findViewById(R.id.recycler_atividades);

        adapter = new AtividadesAdapter(getActivity(), atividadeList);
        recycler_atividades.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler_atividades.setLayoutManager(mLayoutManager);

        recycler_atividades.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recycler_atividades, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Atividade atividade = atividadeList.get(position);

                Context context = view.getContext();
                Intent detalhesAtividade = new Intent(context, AtividadeDetalhes.class);
                detalhesAtividade.putExtra("id_atividade", atividade.getId());
                context.startActivity(detalhesAtividade);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new UpdateAtividades().execute();
    }

    private class UpdateAtividades extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try {
                atividadeList.clear();
                atividadeList.addAll(DatabaseHandler.getDB().getAtividadesByDay(offset));

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            adapter.notifyDataSetChanged();
        }
    }

}
