package myplugin;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import myplugin.analyzer.AnalyzeException;
import myplugin.analyzer.ModelAnalyzer;
import myplugin.generator.ControllerGenerator;
import myplugin.generator.EJBGenerator;
import myplugin.generator.MainGenerator;
import myplugin.generator.ShowGenerator;
import myplugin.generator.StandardFormGenerator;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/** Action that activate code generation */
@SuppressWarnings("serial")
class GenerateAction extends MDAction {

	public GenerateAction(String name) {
		super("", name, null, null);
	}

	public void actionPerformed(ActionEvent evt) {

		if (Application.getInstance().getProject() == null)
			return;
		@SuppressWarnings("deprecation")
		Model root = Application.getInstance().getProject().getModel();
		if (root == null)
			return;

		ModelAnalyzer analyzer = new ModelAnalyzer(root, "");

		try {
			analyzer.prepareModel();
			GeneratorOptions go = ProjectOptions.getProjectOptions().getGeneratorOptions().get("EJBGenerator");
			EJBGenerator generator = new EJBGenerator(go);
			generator.generate();			
			JOptionPane.showMessageDialog(null, "Code is successfully generated!\nGenerated code is in folder: "
					+ go.getOutputPath() + " \npackage: " + go.getFilePackage());
			exportToXml();

			go = ProjectOptions.getProjectOptions().getGeneratorOptions().get("ControllerGenerator");
			ControllerGenerator contollerGenerator = new ControllerGenerator(go);
			contollerGenerator.generate();			
			JOptionPane.showMessageDialog(null, "Code is successfully generated!\nGenerated code is in folder: "
					+ go.getOutputPath() + " \npackage: " + go.getFilePackage());
			exportToXml();
			
			
			go = ProjectOptions.getProjectOptions().getGeneratorOptions().get("ShowGenerator");
			ShowGenerator showGenerator = new ShowGenerator(go);
			showGenerator.generate();			
			JOptionPane.showMessageDialog(null, "Code is successfully generated!\nGenerated code is in folder: "
					+ go.getOutputPath() + " \npackage: " + go.getFilePackage());
			exportToXml();
			
			
			go = ProjectOptions.getProjectOptions().getGeneratorOptions().get("MainGenerator");
			MainGenerator mainGenerator = new MainGenerator(go);
			mainGenerator.generate();			
			JOptionPane.showMessageDialog(null, "Code is successfully generated!\nGenerated code is in folder: "
					+ go.getOutputPath() + " \npackage: " + go.getFilePackage());
			exportToXml();
			
			go = ProjectOptions.getProjectOptions().getGeneratorOptions().get("StandardFormGenerator");
			StandardFormGenerator standardFormGenerator = new StandardFormGenerator(go);
			standardFormGenerator.generate();			
			JOptionPane.showMessageDialog(null, "Code is successfully generated!\nGenerated code is in folder: "
					+ go.getOutputPath() + " \npackage: " + go.getFilePackage());
			exportToXml();			
			
		} catch (AnalyzeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void exportToXml() {
		if (JOptionPane.showConfirmDialog(null, "Do you want to extract model metadata?") == JOptionPane.OK_OPTION) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getSelectedFile().getAbsolutePath();

				XStream xstream = new XStream(new DomDriver());
				BufferedWriter out;
				try {
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
					xstream.toXML(FMModel.getInstance().getClasses(), out);
					xstream.toXML(FMModel.getInstance().getEnumerations(), out);
					JOptionPane.showMessageDialog(null, "Metadata successfully extracted!");

				} catch (UnsupportedEncodingException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
	}

}