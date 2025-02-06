package actions;

import maps.BaseMap;

public class LoginAction extends BaseMap {
	public void acessarSite() throws Exception {
		navigateToUrl("https://www.demoblaze.com/");
		
		waitPresence(lm.usuario);
		String user = BaseActions.recuperarDadosPlanilha("Usuario");
		String password = BaseActions.recuperarDadosPlanilha("Senha");
		sendKeys(lm.usuario, usuario);
		sendKeys(lm.senha, senha);
		
	}

}
