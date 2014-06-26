package br.com.trabalhothreads.objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Texto implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Frases> frases;

	private List<Frases> frasesValidas;

	private List<Frases> frasesInvalidas;

	private File file;

	private int palavrasValidas = 0;
	
	private int palavrasInvalidas = 0;

	
	public Texto() {
		super();
	}

	public Texto(File file) {
		this.frases = new ArrayList<Frases>();
		this.frasesInvalidas = new ArrayList<Frases>();
		this.frasesValidas = new ArrayList<Frases>();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void processar() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.getFile()));
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			String texto = stringBuilder.toString();
			String[] frases = texto.split("\\.");
			for (int i = 0; i < frases.length; i++) {
				this.frases.add(new Frases(frases[i], i));
			}
			processarFrases();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	public void processarFrases() {
		try {
			while (!this.frases.isEmpty()) {
				Frases frase = (Frases) this.frases.get(0);
				String conteudo = frase.getConteudo();
				if (conteudo == null || "".equals(conteudo.trim())) {
					this.frases.remove(frase);
					continue;
				}
				String[] words = conteudo.split("\\s+");
				for (int i = 0; i < words.length; i++) {
					String word = words[i];
					boolean validWord = Lexical.validateWord(word);
					if (!validWord) {
						System.out.println(word);
						frase.addInvalidWord(word);
						palavrasInvalidas++;
					} else {
						palavrasValidas++;
					}
				}

				if (frase.getQtdPalavrasInvalidas() > 0) {
					frase.setStatus(Status.INVALIDA);
					frasesInvalidas.add(frase);
					frases.remove(0);
				} else {
					frase.setStatus(Status.VALIDA);
					this.frasesValidas.add(frase);
					this.frases.remove(0);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void imprimirRelatorio() {
		BufferedWriter bufferedWriter = null;
		try {
			File pastaAnalise = new File(file.getParentFile().getPath()+ File.separator + "analise");
			if (!pastaAnalise.exists())
				pastaAnalise.mkdir();
			bufferedWriter = new BufferedWriter(new FileWriter(new File(pastaAnalise.getPath()+ File.separator + file.getName())));
			bufferedWriter.write("Relatorio de analise.\r\n");
			bufferedWriter.write("Palavras Contidas no texto: "+ (palavrasInvalidas + palavrasValidas) +"\r\n");
			bufferedWriter.write("Palavras Validas: "+ palavrasValidas +"\r\n");
			bufferedWriter.write("Palavras Inválidas: "+ palavrasInvalidas +"\r\n");

			for (int i = 0; i < frasesInvalidas.size(); i++) {
				Frases frase = (Frases) frasesInvalidas.get(i);
				bufferedWriter.write("Frase: " + frase.getConteudo() + ", lista de palavras inválidas: " + frase.palavrasInvalidas() + "\r\n");
			}
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

	public int getPalavrasValidas() {
		return palavrasValidas;
	}

	public int getPalavrasInvalidas() {
		return palavrasInvalidas;
	}
}
