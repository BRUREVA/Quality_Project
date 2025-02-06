package core;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Sheet {
	
 
		private File sheetFile = null;
		private XSSFSheet sheet;
		private XSSFWorkbook workbook = null;
		private List<String> columns = null;
		private List<Integer> columnsId = null;
		
		
		// Definindo a exceção SheetException como subclasse de Exception
		@SuppressWarnings("serial")
		public class SheetException extends Exception {
		    // Construtores, se necessário
		    public SheetException(String message) {
		        super(message);
		    }
		 
		    public SheetException(String message, Throwable cause) {
		        super(message, cause);
		    }
		}
		
		/**
		 *  Cria uma planilha, por padrão é selecionado a primeira aba da planilha
		 *  *A planilha deve ter a primeira linha preenchida com os titulos das colunas*
		 *  *Pelo menos um titulo deve ser definido*
		 *  *Colunas sem titulos serão ignoradas*
		 *	*Titulo da coluna deve ter formato de texto*
		 *  *Caso a planilha tenha titulos duplicados apenas a primeira coluna será considerada*
		 *  *Nomes não são case sensitive, ou seja, uma coluna com nome coluna_a e outra com nome coluna_A serão consideradas como duplicadas*
		 *  *A planilha deve estar fechada durante a execução*
		 * @param file
		 * @throws IOException
		 * @throws SheetException
		 */	
		public Sheet(File file) throws SheetException, IOException {
			sheetFile = file;
			createSheet();
		}
		
		/**
		 * Instancia a planilha e seleciona a primeira aba
		 * @param sheetFile
		 * @throws SheetException
		 * @throws IOException
		 */
		private void createSheet() throws SheetException, IOException {
			if(!sheetFile.exists()) {
				throw new SheetException("Planilha informada não existe");
			}
	        FileInputStream fis = new FileInputStream(sheetFile);
	        workbook = new XSSFWorkbook (fis);
			sheet = workbook.getSheetAt(0);
			readColumns();
		}
		
		/**
		 * Le os titulos das colunas
		 * @throws SheetException
		 */
		private void readColumns() throws SheetException {
			columns = new ArrayList<String>();
			columnsId = new ArrayList<Integer>();
			Row row = sheet.getRow(0);
			if(row == null) throw new SheetException("Planilha/aba informada não possui colunas definidas.");
			for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell(i);
				if(cell == null) continue;
				if(cell.getCellType() != CellType.STRING) continue;
				String value = cell.getStringCellValue();
				if(value == null || value.length() == 0) continue;
				if(columns.contains(value.toLowerCase())) continue;
				columns.add(value);
				columnsId.add(i);
			}
		}
		
		/**
		 *  Troque a aba atual da planilha pelo indice, comecando por 0
		 * @param tab
		 * @throws Exception
		 */
		public void selectTab(int tab) throws SheetException {
			if(tab>=workbook.getNumberOfSheets()) {
				throw new SheetException("Aba informada não existe. (" + tab + "/" + workbook.getNumberOfSheets() + ")");
			}
			sheet = workbook.getSheetAt(tab);
			readColumns();
		}
		
		/**
		 * Troque a aba atual da planilha pelo nome
		 * @param tab
		 * @throws Exception
		 */
		public void selectTab(String tab) throws SheetException {
			if(workbook.getSheetIndex(tab)==-1) {
				throw new SheetException("Aba informada não existe. (" + tab + ")");
			}
			sheet = workbook.getSheet(tab);
		}
		
		/**
		 * Salva as alterações, caso existam, e fecha a planilha
		 * @throws IOException
		 */
		public synchronized void saveAndCloseSheet() throws IOException {
			if(workbook == null) return;
			FileOutputStream os = new FileOutputStream(sheetFile);
			workbook.write(os);
			os.close();
			workbook.close();
		}
		
		/**
		 * Returna o valor para a linha e coluna informada
		 * @param column
		 * @param row
		 * @return
		 * @throws SheetException
		 */
		public String getValue(String column, int row) throws SheetException {
			if(row <= 0) throw new SheetException("Linha inválida, informe valores maiores que 2 (a linha 1 se trata da linha dos títulos.");
			if(row == 1) throw new SheetException("Linha informada (1) se trata da linha dos títulos, selecione um valor maior ou igual a 2.");
			row--;
			if(!columns.contains(column)) {
				throw new SheetException("Coluna informada não encontrada ("+column+").");
			}
			int columnId = columns.indexOf(column);
			columnId = columnsId.get(columnId);
			Row currentRow = sheet.getRow(row);
			if(currentRow == null) return null;
			Cell cell = currentRow.getCell(columnId);
			if(cell == null) return null;
			switch (cell.getCellType()) {
			case _NONE:
			case BLANK:
			case ERROR:
				return null;
			case NUMERIC:
				return Double.toString(cell.getNumericCellValue());
			case BOOLEAN:
				return Boolean.toString(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			case STRING:
				return cell.getStringCellValue();
			default:
				return null;
			}
		}
		
		
		public String getValueNomeDaMassa(String nomeMassa, String columnMassa) throws SheetException {
			String colunaCasoTeste = "ID";
			String variacaoMassa;
			if (nomeMassa.equals("")) {
				variacaoMassa = Variables.getNomeCenario();
			} else {
				variacaoMassa = nomeMassa;
			}
			
			if (variacaoMassa.equals("")) {
				variacaoMassa = Variables.getNomeCenario();
			}
			int row = 0;
			if(!columns.contains(colunaCasoTeste)) {
				throw new SheetException("Coluna informada não encontrada ("+colunaCasoTeste+").");
			}
			int columnId = columns.indexOf(colunaCasoTeste);
			columnId = columnsId.get(columnId);
			if(!columns.contains(columnMassa)) {
				throw new SheetException("Coluna informada não encontrada ("+columnMassa+").");
			}
			int columnIdMassa = columns.indexOf(columnMassa);
			columnIdMassa = columnsId.get(columnIdMassa);
			
			Row currentRow = sheet.getRow(row);
			while (currentRow != null) {
				Cell cell = currentRow.getCell(columnId);
				String valorLinha = cell.getStringCellValue();
				if (valorLinha.equals(variacaoMassa)) {
					cell = currentRow.getCell(columnIdMassa);
					String valorLinhaColunaMassa = cell.getStringCellValue();
					return valorLinhaColunaMassa;
				}
				row++;
				currentRow = sheet.getRow(row);
			}
			throw new SheetException("Cenário não encontrado");
		}
		
		/**
		 * Define o valor para a coluna e linha informada
		 * @param column
		 * @param row
		 * @param value
		 * @throws SheetException
		 */
		public synchronized void setValue(String column, int row, String value) throws SheetException {
			if(row <= 0) throw new SheetException("Linha inválida, informe valores maiores que 2 (a linha 1 se trata da linha dos títulos.");
			if(row == 1) throw new SheetException("Linha informada (1) se trata da linha dos títulos, selecione um valor maior ou igual a 2.");
			row--;
			if(!columns.contains(column)) {
				throw new SheetException("Coluna informada não encontrada ("+column+").");
			}
			int columnId = columns.indexOf(column);
			columnId = columnsId.get(columnId);
			Row currentRow = sheet.getRow(row);
			if(currentRow == null) {
				currentRow = sheet.createRow(row);
			}
			Cell cell = currentRow.createCell(columnId);
			cell.setCellValue(value);
		}
		
		/**
		 * Define o valor para a coluna informada na linha do nome de Massa informado
		 * @param column
		 * @param nomeMassa
		 * @param value
		 * @throws SheetException
		 */
		public synchronized void setValue(String column, String nomeMassa, String value) throws Exception {
			int row = returnRowNumber(nomeMassa);
			if(row <= 0) throw new SheetException("Linha inválida, informe valores maiores que 2 (a linha 1 se trata da linha dos títulos.");
			if(row == 1) throw new SheetException("Linha informada (1) se trata da linha dos títulos, selecione um valor maior ou igual a 2.");
			row--;
			if(!columns.contains(column)) {
				throw new SheetException("Coluna informada não encontrada ("+column+").");
			}
			int columnId = columns.indexOf(column);
			columnId = columnsId.get(columnId);
			Row currentRow = sheet.getRow(row);
			if(currentRow == null) {
				currentRow = sheet.createRow(row);
			}
			Cell cell = currentRow.createCell(columnId);
			cell.setCellValue(value);
		}
		
		/**
		 * Retorna o numero da linha onde uma massa se encontra
		 * @param nomeMassa
		 * @throws SheetException
		 */
		public int returnRowNumber(String nomeMassa) throws Exception {
			String colunaCasoTeste = "ID";
			String variacaoMassa;
			if (nomeMassa.equals("")) {
				throw new Exception("Nome da Massa Vazio!");
			} else {
				variacaoMassa = nomeMassa;
			}
			int row = 0;
			if(!columns.contains(colunaCasoTeste)) {
				throw new SheetException("Coluna informada não encontrada ("+colunaCasoTeste+").");
			}
			int columnId = columns.indexOf(colunaCasoTeste);
			columnId = columnsId.get(columnId);
			Row currentRow = sheet.getRow(row);
			while (currentRow != null) {
				Cell cell = currentRow.getCell(columnId);
				String valorLinha = cell.getStringCellValue();
				if (valorLinha.equals(variacaoMassa)) {
					return row + 1;
				}
				row++;
				currentRow = sheet.getRow(row);
			}
			throw new SheetException("Cenário não encontrado");
		}
		
		/**
		 * Retorna o numero ultima preenchida linha da planilha
		 * (A ultima coluna retornada pode conter valores nulos ou vazios)
		 * @return
		 */
		public int getLastRowNumber() {
			return sheet.getLastRowNum();
		}
}
