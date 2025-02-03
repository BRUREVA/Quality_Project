package core;

import java.io.File;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class EvidenceManager {
	
	public synchronized void takeScreenshot(String descricao) {
		Variables.setCount(Variables.getCount() + 1);
		File screenshot = ((TakesScreenshot)WebDriverManager.driver()).getScreenshotAs(OutputType.FILE);
		try {
			String evidencePath = Variables.getPath() + "/prints/" + Variables.getCount() + "-" + descricao + ".png";
			FileUtils.copyFile(screenshot, new File(evidencePath));
		} catch (Exception e) {
			Utils.log("Erro na captura: " + e);
		}
	}
//tem menu de contexto

}
