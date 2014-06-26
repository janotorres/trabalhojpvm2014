package br.com.trabalhothreads.examples;

import br.com.trabalhothreads.jpvm.jpvmBuffer;
import br.com.trabalhothreads.jpvm.jpvmEnvironment;
import br.com.trabalhothreads.jpvm.jpvmException;
import br.com.trabalhothreads.jpvm.jpvmTaskId;

public class hello_other {
	static int num_workers = 1;

	public static void main(String args[]) {
		try {
			// inicia o jpvm...
			jpvmEnvironment jpvm = new jpvmEnvironment();

			// pega o id do meu pai...
			jpvmTaskId parent = jpvm.pvm_parent();

			// envia mensagem para meu pai...
			jpvmBuffer buf = new jpvmBuffer();
			buf.pack("Hello from jpvm task, id: " + jpvm.pvm_mytid().toString());
			jpvm.pvm_send(buf, parent, 12345);

			// sai do jpvm
			jpvm.pvm_exit();
		} catch (jpvmException jpe) {
			System.out.println("Error - jpvm exception");
		}
	}
};