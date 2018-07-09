//package br.gov.caixa.negocio.test;
//
//import java.io.File;
//
//import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
//
//import br.gov.caixa.ccr.util.MockCreator;
//
//@ArquillianSuiteDeployment
//public class Deployments {
//
//	@Deployment
//	public static Archive<?> deploy() {
//		File[] libs = Maven.configureResolver().withClassPathResolution(true).loadPomFromFile("pom.xml")
//				.importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME, ScopeType.PROVIDED, ScopeType.TEST).resolve()
//				.withTransitivity().asFile();
//
//		Archive<?> ejbArchive = ShrinkWrap.create(JavaArchive.class, "ccr-ejb-test.jar")
//				.addClasses(Deployments.class, ConvenioBeanIT.class, MockCreator.class)
//				.addPackages(true, "br/gov/caixa/ccr")
//				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
//
//		return ShrinkWrap.create(EnterpriseArchive.class, "ccr-test.ear").addAsModule(ejbArchive).addAsLibraries(libs);
//
//	}
//}
