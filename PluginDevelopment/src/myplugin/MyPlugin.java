package myplugin;

import java.io.File;

import javax.swing.JOptionPane;

import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

/** MagicDraw plugin that performes code generation */
public class MyPlugin extends com.nomagic.magicdraw.plugins.Plugin {

	String pluginDir = null;

	public void init() {
		JOptionPane.showMessageDialog(null, "My Plugin init");

		pluginDir = getDescriptor().getPluginDirectory().getPath();
		// pluginDir: D:\Program Files\MagicDraw 18.5 SP3\plugins\myplugin

		// Creating submenu in the MagicDraw main menu
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getSubmenuActions()));

		/**
		 * @Todo: load project options (@see myplugin.generator.options.ProjectOptions)
		 *        from ProjectOptions.xml and take ejb generator options
		 */

		//D:\Fakultet\master\MBRS\git\MBRS_2017\ProjMBRS\test
		String pathProjekat = "D:/Fakultet/master/MBRS/git/MBRS_2017/ProjMBRS/test";
		// for test purpose only:
		// GeneratorOptions ejbOptions = new GeneratorOptions("c:/temp", "ejbclass", "templates", "{0}.java", true, "ejb");

		GeneratorOptions ejbOptions = new GeneratorOptions(pathProjekat + "/app/", "model", "templates", "{0}.java", true, "models");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("EJBGenerator", ejbOptions);
		ejbOptions.setTemplateDir(pluginDir + File.separator + ejbOptions.getTemplateDir()); // apsolutna putanja
		
		GeneratorOptions controllerOptions = new GeneratorOptions(pathProjekat + "/app/", "controller", "templates", "{0}.java", true, "controllers");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("ControllerGenerator", controllerOptions);
		controllerOptions.setTemplateDir(pluginDir + File.separator + controllerOptions.getTemplateDir()); // apsolutna putanja
		
		GeneratorOptions showOptions = new GeneratorOptions(pathProjekat + "/app/views/", "show", "templates", "{0}.html", true, "views");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("ShowGenerator", showOptions);
		showOptions.setTemplateDir(pluginDir + File.separator + showOptions.getTemplateDir()); // apsolutna putanja
		
		GeneratorOptions mainOptions = new GeneratorOptions(pathProjekat + "/app/views", "main", "templates", "{0}.html", true, "views");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("MainGenerator", mainOptions);
		mainOptions.setTemplateDir(pluginDir + File.separator + mainOptions.getTemplateDir()); // apsolutna putanja
		
		GeneratorOptions standardFormOptions = new GeneratorOptions(pathProjekat + "/app/views", "standardForm", "templates", "{0}.html", true, "views");
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("StandardFormGenerator", standardFormOptions);
		standardFormOptions.setTemplateDir(pluginDir + File.separator + standardFormOptions.getTemplateDir()); // apsolutna putanja	
		
		
		
	}

	private NMAction[] getSubmenuActions() {
		return new NMAction[] { new GenerateAction("Generate"), };
	}

	public boolean close() {
		return true;
	}

	public boolean isSupported() {
		return true;
	}
}
