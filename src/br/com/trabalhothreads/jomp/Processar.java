package br.com.trabalhothreads.jomp;

import java.util.List;

import br.com.trabalhothreads.objects.Texto;

public class Processar {

	public void processar(List<Texto> textos) {
		for (int i = 0; i < textos.size(); i++) {
			Texto texto = textos.get(i);
			texto.processar();
		}		
	}
	
	public int contabilizarPalavras(List<Texto> textos) {
        int palavras = 0;
		for (int i = 0; i < textos.size(); i++){
			palavras +=  textos.get(i).getPalavrasValidas();
			palavras +=  textos.get(i).getPalavrasInvalidas();
		}
		return palavras;
	}
	
	public int contabilizarPalavrasInvalidas(List<Texto> textos) {
		int palavrasInvalidas = 0;
		for (int i = 0; i < textos.size(); i++){
			palavrasInvalidas +=  textos.get(i).getPalavrasInvalidas();
		}
		return palavrasInvalidas;
	}
	
	public void imprimirRelatorio(List<Texto> textos) {
		for (int j = 0; j < textos.size(); j++){
			Texto texto = textos.get(j);
			texto.imprimirRelatorio();
		}
	}
}
