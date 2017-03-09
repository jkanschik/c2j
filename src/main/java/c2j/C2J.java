package c2j;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.proleap.cobol.asg.applicationcontext.CobolParserContext;
import io.proleap.cobol.asg.applicationcontext.CobolParserContextFactory;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import c2j.dataStructures.DataStructure;
import c2j.dataStructures.DataStructureContainer;
import c2j.utils.StringConverter;

public class C2J {

	private static Configuration cfg;
	
	public static void main(String[] args) throws IOException, TemplateException {

		CobolParserContextFactory.configureDefaultApplicationContext();

//		// generate ASG from plain COBOL code
//		java.io.File inputFile = new File("src/test/resources/cobol/dataStructures1.cbl");
//		CobolSourceFormatEnum format = CobolSourceFormatEnum.VARIABLE;
//		Program program = CobolParserContext.getInstance().getParserRunner().analyzeFile(inputFile, format);
//
//
//		for (CompilationUnit compilationUnit2 : program.getCompilationUnits()) {
//			CompilationUnitContainer container = new CompilationUnitContainer("c2j.example", compilationUnit2.getName());
//			new TopLevelDataStructureVisitor(container).visit(compilationUnit2.getCtx());
////			new CompilationUnitVisitor(container).visit(compilationUnit2.getCtx());
//			new CUPrinter().print(container);
//		}
	
		
	       /* Create and adjust the configuration singleton */
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassicCompatible(true); // null will not crash.
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

		processFiles(
				"src/test/resources/cobol/dataStructures1.cbl"
//				, "src/test/resources/cobol/dataStructures2.cbl"
				);
	}

	private static void processFiles(String... fileNames) throws IOException, TemplateException {
		String packageName = "c2j.output";
		String dataStructurePackageName = packageName + ".dataStructures";
		String outputDirectory = "../output/src/";

		for (String fileName : fileNames) {
			java.io.File inputFile = new File(fileName);
			CobolSourceFormatEnum format = CobolSourceFormatEnum.VARIABLE;
			Program program = CobolParserContext.getInstance().getParserRunner().analyzeFile(inputFile, format);
			for (CompilationUnit cu : program.getCompilationUnits()) {
				DataStructureContainer dsc = new DataStructureContainer();
				dsc.collectDatastructures(cu);
				
		        Template template = cfg.getTemplate("main.ftlh");
		        
		        File outputFile = composeOutputFile(outputDirectory, packageName, cu.getName());
		        Writer out = new OutputStreamWriter(new FileOutputStream(outputFile));

		        HashMap data = new HashMap();
		        data.put("dataStructureContainer", dsc);
		        data.put("dataStructurePackageName", dataStructurePackageName);
		        data.put("packageName", packageName);
		        data.put("className", cu.getName());
		        template.process(data, out);
		        
		        for (DataStructure ds : dsc.getTopLevelDataStructures()) {
		        	if (ds.hasChildren()) {
				        template = cfg.getTemplate("dataStructure.ftlh");

				       
				        outputFile = composeOutputFile(outputDirectory, dataStructurePackageName, ds.getName());
				        out = new OutputStreamWriter(new FileOutputStream(outputFile));

				        data = new HashMap();
				        data.put("dataStructure", ds);
				        data.put("dataStructurePackageName", dataStructurePackageName);
				        data.put("className", ds.getName());
				        template.process(data, out);
		        	}
		        }
		        

			}
		}
	}
	
	private static File composeOutputFile(String outputDir, String packageName, String className) {
        String packageDir = outputDir + StringConverter.packageToDirectory(packageName);
        new File(packageDir).mkdirs();
        return new File(packageDir + "/" + className + ".java");		
	}
	
}
