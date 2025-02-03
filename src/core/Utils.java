package core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Utils {
	private static ThreadLocal<PrintStream> logStream = ThreadLocal.withInitial(() -> null);
	 
	public static synchronized void initializeLog(String logFilePath) throws IOException {
        if (logStream.get() == null) {
            PrintStream customLogStream = new PrintStream(new FileOutputStream(logFilePath, true));
            logStream.set(customLogStream);
            System.setOut(customLogStream);
            System.setErr(customLogStream);
        }
    }
 
    public static synchronized void log(String message) {
        PrintStream customLogStream = logStream.get();
        if (customLogStream != null) {
            customLogStream.println(message);
        	Variables.setMsgLog(Variables.getMsgLog().concat(message));
        }
    }
 
    public static synchronized void closeLog() {
        PrintStream customLogStream = logStream.get();
        if (customLogStream != null) {
            customLogStream.close();
            logStream.remove();
            System.setOut(System.out);
            System.setErr(System.err);
        	System.out.println(Variables.getMsgLog());
        }
    }
 
	public synchronized String dataHoje() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
 
	/**
	 * Converte milisegundos para tempo hh:mm:ss
	 *
	 * @param total
	 * @return
	 */
	public synchronized String longToTime(long total) {
		return String.format("%02d:%02d:%02d", total / 3600000, (total / 60000) % 60, (total / 1000) % 60);
	}
 
	/**
	 * Metodo para gerar numeros aleatorios de acordo com a quantidade de Digitos
	 *
	 * @author BRUREVA
	 *
	 */
	public synchronized int geradorNumeroPorDigito(int qtdDigitos) {
		int minimo = (int) Math.pow(10, qtdDigitos - 1); // Valor minimo com 2 digitos eh 10 (10^1)
		int maximo = (int) Math.pow(10, qtdDigitos) - 1; // Valor maximo com 2 digitos eh 99 (10^2 - 1)
		Random random = new Random();
		return minimo + random.nextInt((maximo - minimo) + 1);
	}
 
	/**
	 * Metodo para gerar CPF
	 *
	 */
	public synchronized String geradorCPF() {
		String CPF = "";
		// gera cada um dos 9 primeiros digitos do CPF
		int n1 = geradorNumeroPorDigito(1);
		int n2 = geradorNumeroPorDigito(1);
		int n3 = geradorNumeroPorDigito(1);
		int n4 = geradorNumeroPorDigito(1);
		int n5 = geradorNumeroPorDigito(1);
		int n6 = geradorNumeroPorDigito(1);
		int n7 = geradorNumeroPorDigito(1);
		int n8 = geradorNumeroPorDigito(1);
		int n9 = geradorNumeroPorDigito(1);
		// Multiplica cada digito gerado por seu peso e soma
		int primeiroDigito = n1 * 10 + n2 * 9 + n3 * 8 + n4 * 7 + n5 * 6 + n6 * 5 + n7 * 4 + n8 * 3 + n9 * 2;
		// se o resto da divisao da soma por 11 for menor que 2
		// primeiro digito verificador sera 0, senao subtrai-se 11 pelo resto da divisao
		int resto = primeiroDigito % 11;
		if (resto < 2) {
			primeiroDigito = 0;
		} else {
			primeiroDigito = 11 - resto;
		}
 
		int segundoDigito = n1 * 11 + n2 * 10 + n3 * 9 + n4 * 8 + n5 * 7 + n6 * 6 + n7 * 5 + n8 * 4 + n9 * 3
				+ primeiroDigito * 2;
		resto = segundoDigito % 11;
		if (resto < 2) {
			segundoDigito = 0;
		} else {
			segundoDigito = 11 - resto;
		}
 
		CPF = Integer.toString(n1) + Integer.toString(n2) + Integer.toString(n3) + Integer.toString(n4)
				+ Integer.toString(n5) + Integer.toString(n6) + Integer.toString(n7) + Integer.toString(n8)
				+ Integer.toString(n9) + Integer.toString(primeiroDigito) + Integer.toString(segundoDigito);
		Utils.log("CPF gerado: " + CPF);
		return CPF;
	}
 
	/**
	 * Metodo para gerar CNPJ
	 *
	 */
	public synchronized String geradorCNPJ() {
		String CNPJ = "";
		// gera cada um dos 12 primeiros digitos do CNPJ
		int n1 = geradorNumeroPorDigito(1);
		int n2 = geradorNumeroPorDigito(1);
		int n3 = geradorNumeroPorDigito(1);
		int n4 = geradorNumeroPorDigito(1);
		int n5 = geradorNumeroPorDigito(1);
		int n6 = geradorNumeroPorDigito(1);
		int n7 = geradorNumeroPorDigito(1);
		int n8 = geradorNumeroPorDigito(1);
		int n9 = 0;
		int n10 = 0;
		int n11 = 0;
		int n12 = 1;
		// Multiplica cada digito gerado por seu peso e soma
		int primeiroDigito = n1 * 5 + n2 * 4 + n3 * 3 + n4 * 2 + n5 * 9 + n6 * 8 + n7 * 7 + n8 * 6 + n9 * 5 + n10 * 4
				+ n11 * 3 + n12 * 2;
		// se o resto da divisao da soma por 11 for menor que 2
		// primeiro digito verificador sera 0, senao subtrai-se 11 pelo resto da divisao
		int resto = primeiroDigito % 11;
		if (resto < 2) {
			primeiroDigito = 0;
		} else {
			primeiroDigito = 11 - resto;
		}
		int segundoDigito = n1 * 6 + n2 * 5 + n3 * 4 + n4 * 3 + n5 * 2 + n6 * 9 + n7 * 8 + n8 * 7 + n9 * 6 + n10 * 5
				+ n11 * 4 + n12 * 3 + primeiroDigito * 2;
		resto = segundoDigito % 11;
		if (resto < 2) {
			segundoDigito = 0;
		} else {
			segundoDigito = 11 - resto;
		}
		CNPJ = Integer.toString(n1) + Integer.toString(n2) + Integer.toString(n3) + Integer.toString(n4)
				+ Integer.toString(n5) + Integer.toString(n6) + Integer.toString(n7) + Integer.toString(n8)
				+ Integer.toString(n9) + Integer.toString(n10) + Integer.toString(n11) + Integer.toString(n12)
				+ Integer.toString(primeiroDigito) + Integer.toString(segundoDigito);
		Utils.log("CNPJ gerado: " + CNPJ);
		return CNPJ;
	}
 
	/**
	 * Metodo para gerar numeros de telefone
	 *
	 */
	public synchronized String geradorTelefone(String tipoTelefone, Boolean DDD) {
		String telefone = "";
		String num = "";
		if (tipoTelefone.equals("celular")) {
			num = Integer.toString(geradorNumeroPorDigito(8));
			telefone = "9" + num;
		} else if (tipoTelefone.equals("fixo")) {
			num = Integer.toString(geradorNumeroPorDigito(7));
			telefone = "2" + num;
		} else {
			Utils.log("Tipo de telefone invalido");
		}
 
		if (DDD) {
			telefone = "21" + telefone;
		}
		Utils.log("Numero de telefone gerado: " + telefone);
		return telefone;
	}
 
	/**
	 * Metodo para formatar data
	 *
	 */
	public static String formataData(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = dateFormat.format(date);
		return dataFormatada;
	}
 
	/**
	 * Metodo para modificar data de acordo com qtd de dias, meses ou anos
	 *
	 * @param qtdTempo, tempo
	 *
	 */
	public synchronized String modificaData(int qtdTempo, String tempo) {
		Instant now = Instant.now(); // data de hoje
		Instant before = null;
		if (tempo.equals("D") || tempo.equals("d")) {
			before = now.plus(Duration.ofDays(qtdTempo));
		} else if (tempo.equals("M") || tempo.equals("m")) {
			qtdTempo = qtdTempo * 30;
			before = now.plus(Duration.ofDays(qtdTempo));
		} else {
			qtdTempo = qtdTempo * 365;
			before = now.plus(Duration.ofDays(qtdTempo));
		}
		Date dateBefore = Date.from(before);
		String dataModificada = formataData(dateBefore);
		Utils.log("Data Formatada: " + dataModificada);
		return dataModificada;
	}
 
	/**
	 * Retorna uma String aleatoria com o tamanho de caracteres passado por
	 * parametro
	 *
	 * @param i
	 */
	public synchronized String geradorCaracteres(int i) {
		String cadeiaCaracteres;
		StringBuilder construtor;
		cadeiaCaracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		construtor = new StringBuilder(i);
		for (int m = 0; m < i; m++) {
			// gera numerico
			int myindex = (int) (cadeiaCaracteres.length() * Math.random());
			// adiciona o caracter a String
			construtor.append(cadeiaCaracteres.charAt(myindex));
		}
		return construtor.toString();
	}
 
	/**
	 * Retorna uma placa aleatoria no formato AA1A111
	 *
	 */
	public synchronized String geradorPlaca() {
		String placa = "";
		placa = geradorCaracteres(3) + geradorNumeroPorDigito(1) +  geradorCaracteres(1) + geradorNumeroPorDigito(2);
		return placa;
	}

	

}
