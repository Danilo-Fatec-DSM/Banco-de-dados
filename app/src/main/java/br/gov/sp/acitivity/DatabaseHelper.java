package br.gov.sp.acitivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes que representa os dados do FB e da Tabela:
    public static final String DATABASE_NAME = "dados.db";
    public static final String TABLE_NAME = "pessoas";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOME";
    public static final String COL_3 = "IDADE";

    // Construtor da Classe que chama o construtor da Superclasse
    // passando o contexto, o nome e a versao do DB:
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    // Metodo chama automaticamente ao criar o DB
    // Cria a tabela "pessoas" com as colunas "ID,NOME, IDADE"
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY, NOME TEXT, IDADE INTEGER)");
    }

    // Metodo chamado quando o db é atualizado para uma nova versao
    // Nesse caso, ele recria a tabela "pessoas"
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Metodo GRAVAR:
    public boolean inserirDados(String nome, int idade){
        // Obter o db no modo escrita
        SQLiteDatabase db = this.getWritableDatabase();

        // Armazena os valores para serem inseridos:
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nome);
        contentValues.put(COL_3, idade);

        // Insere os dados e armazena o resultado na tabela:
        long resultado = db.insert(TABLE_NAME, null, contentValues);

        // Fecha o db:
        db.close();

        // Retorna true se a inserção foi bem-sucedida
        return resultado != -1;
    }

    // Metodo CONSULTAR:
    public Cursor obterIdadePorNome(String nome){
        // Obter o db no mofo Leitura:
        SQLiteDatabase db = this.getReadableDatabase();

        // Especificar a coluna IDADE para retorno:
        String[] columns = {COL_3};

        // Define a condição de Seleção (filtra pelo nomoe):
        String selection = COL_2 + "= ?";

        // Argumento de condição
        String[] selectionArgs = {nome};

        // Execução da Consulta no db:
        return db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    // Metodo ATUALIZAR:
    public boolean atualizarDados(String nome, int novaIdade){
        // Obter o db em modo Escrita:
        SQLiteDatabase db = this.getWritableDatabase();

        // Armazena o novo valor para a coluna IDADE:
        ContentValues valores = new ContentValues();
        valores.put(COL_3, novaIdade);

        // Atualiza a tabela com a nova idade
        int linhasAfetadas = db.update(
                TABLE_NAME,
                valores,
                COL_2 + "= ?",
                new String[]{nome}
        );

        // Fecha o db
        db.close();

        // Retorna True se pelo menos uma linha foi afetada:
        return linhasAfetadas > 0;
    }

    // Metodo DELETAR
    public boolean deletarDados(String nome){
        // Obter o db em modo escrito
        SQLiteDatabase db = this.getWritableDatabase();

        // Deletar o registro pelo nome:
        int linhasAfetadas = db.delete(
                TABLE_NAME,
                COL_2 + "= ?",
                new String[]{nome}
        );

        // Fecha o db
        db.close();

        // Retorna true se pelo menos uma linha for afetada
        return linhasAfetadas > 0;
    }
}
