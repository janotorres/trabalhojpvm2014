package br.com.trabalhothreads.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.trabalhothreads.jomp.Processar;
import br.com.trabalhothreads.objects.Texto;

public class AlgorithmLexical {

	private List<Texto> textos;
	
	public void setTextos(List<Texto> textos) {
		this.textos = textos;
	}

	public void start() {
		System.out.println(textos.size());
		Processar processar = new Processar();
		processar.processar(textos);
		//System.out.println(textos.size());
		
		//System.out.println(textos.size());
		int palavras = processar.contabilizarPalavras(textos);
		//System.out.println(palavras);
		
	   
		//System.out.println(textos.size());
		int palavrasInvalidas =  processar.contabilizarPalavrasInvalidas(textos);
		//System.out.println(palavrasInvalidas);
		
		//System.out.println(textos.size());
		processar.imprimirRelatorio(textos);
		//System.out.println(textos.size());
		
		imprimirTotalizador(palavras, palavrasInvalidas, textos.size(), textos.get(0).getFile());
	}

	private void imprimirTotalizador(int palavras, int palavrasInvalidas, int size, File file) {
		BufferedWriter bufferedWriter = null;
		try {
			File pastaAnalise = new File(file.getParentFile().getPath()+ File.separator + "analise");
			if (!pastaAnalise.exists())
				pastaAnalise.mkdir();
			bufferedWriter = new BufferedWriter(new FileWriter(new File(pastaAnalise.getPath()+ File.separator + "totalizador.txt")));
			bufferedWriter.write("Relatorio Geral de analise.\r\n");
			bufferedWriter.write("Palavras Contidas nos textos: "+ palavras +"\r\n");
			bufferedWriter.write("Palavras Inválidas: "+ palavrasInvalidas +"\r\n");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {	
					throw new RuntimeException(e);
				}
			}
		}
	}
}
