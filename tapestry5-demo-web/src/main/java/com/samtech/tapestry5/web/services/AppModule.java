package com.samtech.tapestry5.web.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;


public class AppModule
{
	
	// Add 3 services to those provided by Tapestry.
	// - IBusinessServicesLocator and CountryNames are used by pages which ask Tapestry to @Inject them.
	// - ProtectedPageGateKeeper is contributed, below, to Tapestry's MasterDispatcher.

	/*public static void bind(ServiceBinder binder) {
		binder.bind(IBusinessServicesLocator.class, BusinessServicesLocator.class);
		binder.bind(CountryNames.class);
		binder.bind(ProtectedPageGateKeeper.class).withId("ProtectedPageGateKeeper");

		// This next line addresses an issue affecting Glassfish and JBoss - see http://blog.progs.be/?p=52
		javassist.runtime.Desc.useContextClassLoader = true;
	}*/
	
	/*@SuppressWarnings("unchecked")
	public static void contributeFieldValidatorSource(MappedConfiguration<String, Validator> configuration) {
		configuration.add("letters", new Letters());
	}
*/
	/*@SuppressWarnings("unchecked")
	public static void contributeTranslatorSource(Configuration<Translator> configuration) {
		configuration.add(new YesNoTranslator());
	}*/

	/*public void contributeValidationMessagesSource(OrderedConfiguration<String> configuration) {
		configuration.add("myValidationMessages", "jumpstart/web/validators/ValidationMessages");
		configuration.add("myTranslationMessages", "jumpstart/web/translators/TranslationMessages");
	}*/
	
  public static void contributeApplicationDefaults(MappedConfiguration<String,String> configuration)
  {
    configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,zh");
    //configuration.add(SymbolConstants.FILE_CHECK_INTERVAL, "10 m");
    configuration.add(SymbolConstants.CHARSET, "UTF-8");
    configuration.add(SymbolConstants.GZIP_COMPRESSION_ENABLED, "false");
    configuration.add(SymbolConstants.COMBINE_SCRIPTS, "false");
    
  }
  
  /*public void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
			@InjectService("ProtectedPageGateKeeper") Dispatcher protectedPageGateKeeper) {
		configuration.add("ProtectedPageGateKeeper", protectedPageGateKeeper, "before:PageRender");
	}*/

	// Tell Tapestry how to handle classpath URLs - we provide a converter to handle JBoss 5.
	// See http://wiki.apache.org/tapestry/HowToRunTapestry5OnJBoss5 .

	/*@SuppressWarnings("unchecked")
	public static void contributeServiceOverride(MappedConfiguration<Class, Object> configuration) {
		configuration.add(ClasspathURLConverter.class, new ClasspathURLConverterJBoss5());
	}*/
	
  public static void contributeIgnoredPathsFilter(Configuration<String> configuration)
  {
    configuration.add("/dwr/.*");
  }
}
