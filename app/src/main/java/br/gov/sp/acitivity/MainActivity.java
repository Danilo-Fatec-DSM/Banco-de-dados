package br.gov.sp.acitivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextView textResultado;
    TextInputEditText textNome, textIdade;
    Button btnGravar, btnConsultar, btnAtualizar, btnDeletar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        textResultado = findViewById(R.id.textResultado);
        textNome = findViewById(R.id.textNome);
        textIdade = findViewById(R.id.textIdade);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnGravar = findViewById(R.id.btnGravar);
        btnDeletar = findViewById(R.id.btnDeletar);

        // Gravar
        btnGravar.setOnClickListener(view -> {
            // Entrada de dados:
            String nome = textNome.getText().toString();
            String idade = textIdade.getText().toString();

            // Validação dos dados:
            if (nome.isEmpty() || idade.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Preencha todos os dados",
                        Toast.LENGTH_LONG).show();
                return;
            }
            // Caso seja sucesso:

            try {
                // Converte idade de Strinf para Numerico (int):
                int numIdade = Integer.parseInt(idade);

                // Chama o db e o metodo Inserir dados:
                if (dbHelper.inserirDados(nome, numIdade)){
                    // Caso a gravação foi realizada com sucesso:
                    Toast.makeText(MainActivity.this,
                            "Dados inseridos com sucesso",
                            Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,
                            "Erro ao inserir os dados",
                            Toast.LENGTH_LONG).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(MainActivity.this,
                        "ERRO",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Consultar
        btnConsultar.setOnClickListener(view -> {
            // Entrada de dados:
            String nome = textNome.getText().toString();

            // Validação dos dados:
            if (nome.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Digite o nome",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Consulta o nome do DB:
            Cursor cursor = dbHelper.obterIdadePorNome(nome);
            if (cursor != null && cursor.moveToFirst()){
                String idade = cursor.getString(0);
                textResultado.setText("Idade " + idade);
            } else {
                Toast.makeText(MainActivity.this,
                        "Nome não encontrado",
                        Toast.LENGTH_LONG).show();
            }

        });

        // Atualizar
        btnAtualizar.setOnClickListener(view -> {
            // Entrada de dados:
            String nome = textNome.getText().toString();
            String idade = textIdade.getText().toString();

            // Validação dos dados:
            if (nome.isEmpty() || idade.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Preencha todos os dados",
                        Toast.LENGTH_LONG).show();
                return;
            }
            // Caso seja sucesso:

            try {
                // Converte idade de Strinf para Numerico (int):
                int numIdade = Integer.parseInt(idade);

                // Chama o db e o metodo Atualizar dados:
                if (dbHelper.atualizarDados(nome, numIdade)){
                    // Caso a atualizaão foi realizada com sucesso:
                    Toast.makeText(MainActivity.this,
                            "Dados atualizados com sucesso",
                            Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,
                            "Nome não encontrado",
                            Toast.LENGTH_LONG).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(MainActivity.this,
                        "Idade Invalida",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Deletar
        btnDeletar.setOnClickListener(view -> {
            // Entrada de dados:
            String nome = textNome.getText().toString();

            // Validação dos dados:
            if (nome.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Digite o nome",
                        Toast.LENGTH_LONG).show();
                return;
            }
            // Caso seja sucesso:

            if (dbHelper.deletarDados(nome)){
                Toast.makeText(MainActivity.this,
                        "Dados deletados com sucesso",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Nome não encontrado",
                        Toast.LENGTH_LONG).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}