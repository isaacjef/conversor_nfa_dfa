/*
 * Objetivo cria função para converter Json em um
 * JSONObject para manipularmos no construtor da classe NFA.java
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class converterJsonEmObjeto {

    public static void main(String[] args) {
		JSONObject jsonObject;
		//Cria o parse de tratamento
		JSONParser parser = new JSONParser();
		//Variaveis que irao armazenar os dados do arquivo JSON
		String nome;
		String sobrenome;
		String estado;
		String pais;

		try {
			//Salva no objeto JSONObject o que o parse tratou do arquivo
			jsonObject = (JSONObject) parser.parse(new FileReader(
					"exemploNFA.json"));

			//Salva nas variaveis os dados retirados do arquivo
			nome = (String) jsonObject.get("nome");
			sobrenome = (String) jsonObject.get("sobrenome");
			estado = (String) jsonObject.get("estado");
			pais = (String) jsonObject.get("pais");

			System.out.printf(
					"Nome: %s\nSobrenome: %s\nEstado: %s\nPais: %s\n",
					nome, sobrenome, estado, pais);
		}
		//Trata as exceptions que podem ser lançadas no decorrer do processo
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}