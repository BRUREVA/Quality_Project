package core;

import core.Variables;

public class Variables {
	
	public static String timeStamp;
	private static ThreadLocal<String> nomePlanilha = new ThreadLocal<>();
	private static ThreadLocal<String> aba = new ThreadLocal<>();
	private static ThreadLocal<String> nomeMassa = new ThreadLocal<>();
	private static ThreadLocal<String> nomeCenario = new ThreadLocal<>();
	private static ThreadLocal<String> timeStamp2 = new ThreadLocal<>();
	private static ThreadLocal<Long> start = new ThreadLocal<>();
	private static ThreadLocal<Long> finish = new ThreadLocal<>();
	private static ThreadLocal<String> path = new ThreadLocal<>();
	private static ThreadLocal<String> pathValue = new ThreadLocal<>();
	private static ThreadLocal<String> msgLog = new ThreadLocal<>();
	private static ThreadLocal<Integer> count = new ThreadLocal<>();
	private static ThreadLocal<String> mensagemResultado = new ThreadLocal<>();
	private static ThreadLocal<String> numSinistro = new ThreadLocal<>();
	
	public static String getMsgLog() {
		return Variables.msgLog.get();
	}
 
	public static void setMsgLog(String msgLog) {
		Variables.msgLog.set(msgLog);
	}
	
	public static String getPath() {
		return Variables.path.get();
	}
 
	public static void setPath(String path) {
		Variables.path.set(path);
	}
 
	public static String getPathValue() {
		return Variables.pathValue.get();
	}
 
	public static void setPathValue(String pathValue) {
		Variables.pathValue.set(pathValue);
	}
 
	public static Integer getCount() {
		return Variables.count.get();
	}
 
	public static void setCount(Integer count) {
		Variables.count.set(count);
	}
 
	public String getTimeStamp() {
		return timeStamp;
	}
 
	public void setTimeStamp(String timeStamp) {
		Variables.timeStamp = timeStamp;
	}
 
	public static String getTimeStamp2() {
		return Variables.timeStamp2.get();
	}
 
	public static void setTimeStamp2(String timeStamp2) {
		Variables.timeStamp2.set(timeStamp2);
	}
 
	public static void setStart(long start) {
		Variables.start.set(start);
	}
 
	public static long getStart() {
		return Variables.start.get();
	}
 
	public static void setFinish(long finish) {
		Variables.finish.set(finish);
	}
 
	public static long getFinish() {
		return Variables.finish.get();
	}
 
	public static void setNomeCenario(String nomeCenario) {
		Variables.nomeCenario.set(nomeCenario);
	}
 
	public static String getNomeCenario() {
		return Variables.nomeCenario.get();
	}
 
	public static String getNomePlanilha() {
		return Variables.nomePlanilha.get();
	}
 
	public static void setNomePlanilha(String nomePlanilha) {
		Variables.nomePlanilha.set(nomePlanilha);
	}
 
	public static String getNomeAba() {
		return Variables.aba.get();
	}
 
	public static void setNomeAba(String aba) {
		Variables.aba.set(aba);
	}
 
	public static String getNomeMassa() {
		return Variables.nomeMassa.get();
	}
 
	public static void setNomeMassa(String nomeMassa) {
		Variables.nomeMassa.set(nomeMassa);
	}
	
	public static String getMensagemResultado() {
		return Variables.mensagemResultado.get();
	}
 
	public static void setMensagemResultado(String mensagem) {
		Variables.mensagemResultado.set(mensagem);
	}
	
	public static String getNumSinistro() {
		return Variables.numSinistro.get();
	}
 
	public static void setNumSinistro(String nomeMassa) {
		Variables.numSinistro.set(nomeMassa);
	}
	

}
