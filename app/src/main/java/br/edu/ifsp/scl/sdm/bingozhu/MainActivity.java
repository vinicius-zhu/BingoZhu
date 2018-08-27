package br.edu.ifsp.scl.sdm.bingozhu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Coleções de números sorteados e disponíveis
    private ArrayList<Integer> numerosDisponiveis;
    private ArrayList<Integer> numerosSorteados;
    private ArrayAdapter<Integer> adapterSorteados;
    private ArrayAdapter<Integer> adapterDisponiveis;
    private Integer ultimoNumero = 0;

    // Gerador de números randômicos usado para simular o lançamento do dado
    private Random geradorRandomico;

    // Referências aos componentes de layout
    private ImageView imageViewBola;
    private TextView textViewBola;
    private TextView textViewListaSorteados;
    private TextView textViewListaDisponiveis;
    private ListView listViewSorteados;
    private ListView listViewDisponiveis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciando gerador randômico
        geradorRandomico = new Random(System.currentTimeMillis());

        // Referenciando componentes
        imageViewBola = findViewById(R.id.imageViewBola);
        textViewBola = findViewById(R.id.textViewBola);
        listViewSorteados = findViewById(R.id.listViewSorteados);
        listViewDisponiveis = findViewById(R.id.listViewDisponiveis);
        textViewListaSorteados = findViewById(R.id.textViewListaSorteados);
        textViewListaDisponiveis = findViewById(R.id.textViewListaDisponiveis);


        if (numerosDisponiveis == null) {
            numerosDisponiveis = new ArrayList<Integer>();
            for (int i = 0; i < 75; i++) {
                numerosDisponiveis.add(i + 1);
            }
        }
        if (numerosSorteados == null) {
            numerosSorteados = new ArrayList<Integer>();
        }
        if (adapterSorteados == null) {
            adapterSorteados = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numerosSorteados);
            listViewSorteados.setAdapter(adapterSorteados);
        }
        if (adapterDisponiveis == null) {
            adapterDisponiveis = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numerosDisponiveis);
            listViewDisponiveis.setAdapter(adapterDisponiveis);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Salvar os dados de estado dinâmico
        outState.putIntegerArrayList("SORTEADOS", numerosSorteados);
        outState.putIntegerArrayList("DISPONIVEIS", numerosDisponiveis);
        outState.putString("ULTIMONUMERO", ultimoNumero.toString());
    }

    protected boolean limpaContexto() {
        try {

            numerosDisponiveis = new ArrayList<Integer>();
            for (int i = 0; i < 75; i++) {
                numerosDisponiveis.add(i + 1);
            }
            numerosSorteados = new ArrayList<Integer>();
            adapterSorteados = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numerosSorteados);
            listViewSorteados.setAdapter(adapterSorteados);
            adapterDisponiveis = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numerosDisponiveis);
            listViewDisponiveis.setAdapter(adapterDisponiveis);
            ultimoNumero = 0;
            textViewBola.setText("");
            imageViewBola.setVisibility(View.GONE);
            textViewBola.setVisibility(View.GONE);
            textViewListaDisponiveis.setVisibility(View.GONE);
            textViewListaSorteados.setVisibility(View.GONE);
            listViewDisponiveis.setVisibility(View.GONE);
            listViewSorteados.setVisibility(View.GONE);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    protected boolean restauraContexto(Bundle savedInstanceState) {
        try {
            if (savedInstanceState != null) {
                numerosSorteados = savedInstanceState.getIntegerArrayList("SORTEADOS");
                numerosDisponiveis = savedInstanceState.getIntegerArrayList("DISPONIVEIS");
                ultimoNumero = Integer.parseInt(savedInstanceState.get("ULTIMONUMERO").toString());

                if (ultimoNumero > 0) {
                    imageViewBola.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("ball_" + (ultimoNumero - 1) / 15, "drawable", getPackageName())));
                    textViewBola.setText(ultimoNumero.toString());
                    imageViewBola.setVisibility(View.VISIBLE);
                    textViewBola.setVisibility(View.VISIBLE);
                    textViewListaDisponiveis.setVisibility(View.VISIBLE);
                    textViewListaSorteados.setVisibility(View.VISIBLE);
                    listViewDisponiveis.setVisibility(View.VISIBLE);
                    listViewSorteados.setVisibility(View.VISIBLE);
                } else {
                    imageViewBola.setVisibility(View.GONE);
                    textViewBola.setVisibility(View.GONE);
                    textViewListaDisponiveis.setVisibility(View.GONE);
                    textViewListaSorteados.setVisibility(View.GONE);
                    listViewDisponiveis.setVisibility(View.GONE);
                    listViewSorteados.setVisibility(View.GONE);
                }
                adapterSorteados = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numerosSorteados);
                listViewSorteados.setAdapter(adapterSorteados);
                adapterSorteados.notifyDataSetChanged();

                adapterDisponiveis = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numerosDisponiveis);
                listViewDisponiveis.setAdapter(adapterDisponiveis);
                adapterDisponiveis.notifyDataSetChanged();
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restauraContexto(savedInstanceState);
    }

    public void buttonLimpar(View botao)
    {
        if (botao.getId() == R.id.buttonLimpar) {
            limpaContexto();
        }
    }

    public void buttonSortear(View botao) {
        if (botao.getId() == R.id.buttonSortear) {
            if (numerosDisponiveis.size() > 0) {
                Integer posicao_sorteio = geradorRandomico.nextInt(numerosDisponiveis.size());
                ultimoNumero = numerosDisponiveis.get(posicao_sorteio);
                numerosSorteados.add(ultimoNumero);
                numerosDisponiveis.remove((Object) ultimoNumero);
                textViewBola.setText(ultimoNumero.toString());
                adapterSorteados.notifyDataSetChanged();
                adapterDisponiveis.notifyDataSetChanged();

                if (ultimoNumero > 0) {
                    imageViewBola.setVisibility(View.VISIBLE);
                    textViewBola.setVisibility(View.VISIBLE);
                    textViewListaDisponiveis.setVisibility(View.VISIBLE);
                    textViewListaSorteados.setVisibility(View.VISIBLE);
                    listViewDisponiveis.setVisibility(View.VISIBLE);
                    listViewSorteados.setVisibility(View.VISIBLE);
                } else {
                    imageViewBola.setVisibility(View.GONE);
                    textViewBola.setVisibility(View.GONE);
                    textViewListaDisponiveis.setVisibility(View.GONE);
                    textViewListaSorteados.setVisibility(View.GONE);
                    listViewDisponiveis.setVisibility(View.GONE);
                    listViewSorteados.setVisibility(View.GONE);
                }

                imageViewBola.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("ball_" + (ultimoNumero - 1) / 15, "drawable", getPackageName())));
            } else {
                Toast.makeText(this, "Todos os números já foram sorteados!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
