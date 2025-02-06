package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
 
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
 
import io.cucumber.core.internal.com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.cucumber.java.Scenario;


	public class DocumentGenerator {
		Utils util = new Utils();
		/**
		 * Gera o documento de evidencias
		 * @param event
		 * @throws InvalidFormatException
		 * @throws IOException
		 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
		 */
		public synchronized void generateDocument(Scenario scenario) throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
			String name = scenario.getName();
			if (scenario.getName().length()> 65) {
				name = scenario.getName().substring(0, 65);
			}
			String evidencePath = Variables.getPath() + "/" + name + ".docx";
			String pastaPrints = Variables.getPath() + "/prints";
			
			XWPFDocument document = new XWPFDocument();
			String logo = "logo/Logo.png";
			
			XWPFParagraph paragraphLogo = document.createParagraph();
			XWPFRun runLogo = paragraphLogo.createRun();
			paragraphLogo.setAlignment(ParagraphAlignment.LEFT);
			runLogo.addBreak();
			runLogo.addPicture(new FileInputStream(logo), XWPFDocument.PICTURE_TYPE_PNG, logo, Units.toEMU(56),  Units.toEMU(28));
			
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
			paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
			paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
			paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
			XWPFRun run = paragraph.createRun();
			run.setFontFamily("Arial");
			run.setFontSize(11);
			run.setText("Caso de teste: " + scenario.getName());
			// Status
			String status;
			if(!scenario.isFailed()) {
				status = "Passed";
			} else {
				status = "Failed";
			}
			newGenericParagraph(paragraph, "Status: " + status);
			newGenericParagraph(paragraph, "Data e hora da execucao: " + Variables.getTimeStamp2());
			newGenericParagraph(paragraph, "Duracao da execucao: " + util.longToTime(Variables.getFinish() - Variables.getStart()));
			newGenericParagraph(paragraph, "Resultado: " + Variables.getMensagemResultado().toString());
			//listando as imagens da pasta
			List<File> imageFiles = new ArrayList<File>();
			File[] files = new File(pastaPrints).listFiles();
			Arrays.sort(files, new Comparator<File>() {
				public int compare(File f1, File f2) {
					String name1 = f1.getName().replaceAll("[^\\d]","");
					String name2 = f2.getName().replaceAll("[^\\d]","");
					return Integer.compare(Integer.parseInt(name1), Integer.parseInt(name2));
				}
				
			});
			for (File file : files) {
				Utils.log("Arquivo: " + file.getName());
				imageFiles.add(file);
			}
			for (File imageFile : imageFiles) {
				XWPFParagraph paragraphImg = document.createParagraph();
				XWPFRun runImg = paragraphImg.createRun();
				runImg.addBreak();
				runImg.setText(imageFile.getName());
				runImg.addPicture(new FileInputStream(imageFile), XWPFDocument.PICTURE_TYPE_PNG, imageFile.getName(), Units.toEMU(450),  Units.toEMU(263));
			}
			
			//Salvar o documento num arquivo
			FileOutputStream out = new FileOutputStream(evidencePath);
			document.write(out);
			out.close();
			//Fechando o documento
			document.close();
		}
		
		/**
		 * Cria um paragrafo generico no documento
		 * @param paragraph
		 * @param text
		 */
		private synchronized void newGenericParagraph(XWPFParagraph paragraph, String text) {
			XWPFRun run = paragraph.createRun();
			run.setFontSize(11);
			run.setFontFamily("Arial");
			run.addBreak();
			run.setText(text);
		}
	}

