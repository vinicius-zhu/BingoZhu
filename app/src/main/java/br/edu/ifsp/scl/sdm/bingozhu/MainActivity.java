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
import java.util.Locale;
import java.util.Objects;
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

        // Inicializando componentes e variáveis pela primeira vez

        // Criando uma lista de números disponíveis contendo números de 1 a 75
        if (numerosDisponiveis == null) {
            numerosDisponiveis = new ArrayList<>();
            for (int i = 0; i < 75; i++) {
                numerosDisponiveis.add(i + 1);
            }
        }

        if (numerosSorteados == null) {
            numerosSorteados = new ArrayList<>();
        }

        // "Bindando" os ListViews a uma variável de lista
        if (adapterSorteados == null) {
            adapterSorteados = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosSorteados);
            listViewSorteados.setAdapter(adapterSorteados);
        }
        if (adapterDisponiveis == null) {
            adapterDisponiveis = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosDisponiveis);
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

    // Método para reiniciar as listas e variáveis (reiniciar o programa)
    protected boolean limpaContexto() {

        try {
            numerosDisponiveis = new ArrayList<>();
            for (int i = 0; i < 75; i++) {
                numerosDisponiveis.add(i + 1);
            }
            numerosSorteados = new ArrayList<>();
            adapterSorteados = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosSorteados);
            listViewSorteados.setAdapter(adapterSorteados);
            adapterDisponiveis = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosDisponiveis);
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

    // Método para restaurar o contexto
    protected boolean restauraContexto(Bundle savedInstanceState) {
        try {
            if (savedInstanceState != null) {
                numerosSorteados = savedInstanceState.getIntegerArrayList("SORTEADOS");
                numerosDisponiveis = savedInstanceState.getIntegerArrayList("DISPONIVEIS");
                ultimoNumero = Integer.parseInt(Objects.requireNonNull(savedInstanceState.get("ULTIMONUMERO")).toString());

                // Verifica se os controles devem ser exibidos ou ocultados
                exibeOcultaControles();

                // "Bindando" os ListViews a uma variável de lista
                adapterSorteados = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosSorteados);
                listViewSorteados.setAdapter(adapterSorteados);
                adapterSorteados.notifyDataSetChanged();

                adapterDisponiveis = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosDisponiveis);
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

    // Reinicia os controles do aplicativo para que possa ser utilizado novamente
    public void buttonLimpar(View botao)
    {
        if (botao.getId() == R.id.buttonLimpar) {
            limpaContexto();
        }
    }

    // Verifica se os controles devem ser exibidos ou ocultados
    public void exibeOcultaControles()
    {
        if (ultimoNumero > 0) {
            // Recupera a cor da bola que deve ser exibida com base no número sorteado
            //   De 1 a 15: bola azul
            //   De 16 a 30: bola vermelha
            //   De 31 a 45: bola branca
            //   De 46 a 60: bola verde
            //   De 61 a 75: bola amarela
            // Base: https://www.amazon.co.uk/Royal-Bingo-Supplies-Replacement-Professional/dp/B00PDMZH0I
            imageViewBola.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("ball_" + (ultimoNumero - 1) / 15, "drawable", getPackageName())));

            textViewBola.setText(String.format(Locale.getDefault(),"%02d",ultimoNumero));
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
    }
    public void buttonSortear(View botao) {
        // Verifica se o botão pressionado é o botão de sorteio
        if (botao.getId() == R.id.buttonSortear) {

            // Verifica se ainda existem números disponíveis para serem sorteados
            if (numerosDisponiveis.size() > 0) {
                // Sorteia uma posição aleatória da lista
                Integer posicao_sorteio = geradorRandomico.nextInt(numerosDisponiveis.size());

                // Seleciona o número armazenado na posição aleatória sorteada acima
                ultimoNumero = numerosDisponiveis.get(posicao_sorteio);

                // Adiciona o número sorteado à lista de números sorteados
                numerosSorteados.add(ultimoNumero);

                // Remove o número sorteado da lista de números disponíveis para sorteio
                numerosDisponiveis.remove(ultimoNumero);

                // Ajusta o texto da bola para o número sorteado
                textViewBola.setText(String.format(Locale.getDefault(),"%02d",ultimoNumero));

                // Repopula os controles de lista para refletir a migração do número sorteado da
                // lista de disponíveis para a lista de sorteados
                adapterSorteados.notifyDataSetChanged();
                adapterDisponiveis.notifyDataSetChanged();


                exibeOcultaControles();
            } else {
                // Caso não haja mais bolas disponíveis, exibe uma mensagem
                Toast.makeText(this, "Todos os números já foram sorteados!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
