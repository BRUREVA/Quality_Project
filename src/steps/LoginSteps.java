package steps;

import actions.BaseActions;
import actions.LoginActions;
import core.Utils;
import core.Variables;
import io.cucumber.java.pt.Dado;

public class LoginSteps {
	
	LoginActions loginAction = new LoginActions();
	@Dado("o acesso a lojinha com o login {string}")
	public void o_acesso_a_lojinha_com_o_login(String massa) throws Exception {
		Variables.setNomeMassa(massa);
		if(BaseActions.recuperarDadosPlanilha("Run").equals("Nao")) {
			Assumptions.assumeTrue(false, "Cenario skipped");
		}
		try {
			loginAction.acessarSite();
		} catch (Throwable throwable) {
			Variables.setMensagemResultado(throwable.getMessage());
			Utils.log("Resultado: " + Variables.getMensagemResultado());
			throw new Exception(Variables.getMensagemResultado());
		}
	}
 
}

