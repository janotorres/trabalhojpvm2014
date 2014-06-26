package br.com.trabalhothreads.examples;

import br.com.trabalhothreads.jpvm.jpvmBuffer;
import br.com.trabalhothreads.jpvm.jpvmEnvironment;
import br.com.trabalhothreads.jpvm.jpvmException;
import br.com.trabalhothreads.jpvm.jpvmMessage;
import br.com.trabalhothreads.jpvm.jpvmTaskId;

/************8
 * 
 * * spawn -> É chamado pelo mestre para acordar os escravos. (nome_class, quantas_instâncias, array_com_os_ids_dos
_escravos_acordados)

* (Mestre) send ->

(Escravo) receive ->

(Escravo) send ->

(Mestre) receive ->

* Escravo pode ter outros escravos (com o spawn) "Plus"

* send -> (buffer "dados, o que será processado, texto?, objetos devem ser serializados", tag "informação de controle, o que deve ser feito com o buffer")


 * @author Usuario
 *
 */
public class exemplo_jpvm {

	static int num_workers = 4; 
	
    public static void main(String[] args) {
		try {
			jpvmEnvironment jpvm = new jpvmEnvironment();
			
			jpvmTaskId myid = jpvm.pvm_mytid();
			jpvmTaskId masterTaskId = jpvm.pvm_parent();
			System.out.println("task id = " + myid.toString());
			
			//definir se é o master, ou se é algum escravo.
			if (masterTaskId==jpvmEnvironment.PvmNoParent) {
				//é o master
				System.out.println("inicializado como master");
				jpvmTaskId tids[] = new jpvmTaskId[num_workers];
				jpvm.pvm_spawn("examples.exemplo_jpvm", num_workers, tids);
				
				System.out.println("workers taks: ");
				int i;
				for (i=0; i < num_workers; i++)
					System.out.println("\t" + tids[i].toString());
				
				for (i=0; i < num_workers; i++) {
					System.out.println("mandando mensagem para o worker " + i);
					jpvmBuffer buf = new jpvmBuffer();
					buf.pack("servidor, id: " + jpvm.pvm_mytid().toString());
					jpvm.pvm_send(buf, tids[i], i);
					
					System.out.println("recebeu resposta:");
					jpvmMessage message = jpvm.pvm_recv();
					String str = message.buffer.upkstr();
					System.out.println("recebeu : " + str);
					System.out.println("com a tag " +  message.messageTag + " from " +  message.sourceTid.toString());
				}
				
			} else {
				//é o escravo.
				System.out.println("inicializado como escravo.");
				
				jpvmMessage message = jpvm.pvm_recv();
				String str = message.buffer.upkstr();
				System.out.println("recebeu : " + str);
				System.out.println("com a tag " +  message.messageTag + " from " +  message.sourceTid.toString());
				System.out.println("preparando resposta.");
				jpvmBuffer buf = new jpvmBuffer();
				buf.pack(str + " cliente : " + jpvm.pvm_mytid().toString());
				jpvm.pvm_send(buf, masterTaskId, 0);
				System.out.println("mando resposta.");
			}
			jpvm.pvm_exit();
		} catch (jpvmException e) {
			e.printStackTrace(System.out);
		}

	}

}