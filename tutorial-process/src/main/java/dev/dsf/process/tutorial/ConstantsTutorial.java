package dev.dsf.process.tutorial;

import static dev.dsf.process.tutorial.TutorialProcessPluginDefinition.VERSION;

public interface ConstantsTutorial
{
	String PROCESS_VERSION = VERSION.substring(4,7);
	String RESOURCE_VERSION = VERSION.substring(0, 3);
	String PROCESS_NAME_HELLO_DIC = "helloDic";
	String PROCESS_NAME_FULL_HELLO_DIC = "dsfdev_" + PROCESS_NAME_HELLO_DIC;

	String PROFILE_TUTORIAL_TASK_HELLO_DIC = "http://dsf.dev/fhir/StructureDefinition/task-hello-dic";
	String PROFILE_TUTORIAL_TASK_HELLO_DIC_AND_LATEST_VERSION = PROFILE_TUTORIAL_TASK_HELLO_DIC + "|" + VERSION;
	String PROFILE_TUTORIAL_TASK_HELLO_DIC_PROCESS_URI = "http://dsf.dev/bpe/Process/" + PROCESS_NAME_HELLO_DIC;
	String PROFILE_TUTORIAL_TASK_HELLO_DIC_INSTANTIATES_CANONICAL = PROFILE_TUTORIAL_TASK_HELLO_DIC_PROCESS_URI
			+ "|" + RESOURCE_VERSION;
	String PROFILE_TUTORIAL_TASK_HELLO_DIC_MESSAGE_NAME = "helloDic";

	String PROFILE_TUTORIAL_TASK_GOODBYE_DIC = "http://dsf.dev/fhir/StructureDefinition/task-goodbye-dic";
	String PROFILE_TUTORIAL_TASK_GOODBYE_DIC_MESSAGE_NAME = "goodbyeDic";

	String TUTORIAL_DIC_ORGANIZATION_IDENTIFIER = "Test_DIC";

	// The HELLO_COS constants are only needed for exercise 3 and above
	String PROCESS_NAME_HELLO_COS = "helloCos";
	String PROCESS_NAME_FULL_HELLO_COS = "dsfdev_" + PROCESS_NAME_HELLO_COS;

	String PROFILE_TUTORIAL_TASK_HELLO_COS = "http://dsf.dev/fhir/StructureDefinition/task-hello-cos";
	String PROFILE_TUTORIAL_TASK_HELLO_COS_AND_LATEST_VERSION = PROFILE_TUTORIAL_TASK_HELLO_COS + "|" + VERSION;
	String PROFILE_TUTORIAL_TASK_HELLO_COS_PROCESS_URI = "http://dsf.dev/bpe/Process/" + PROCESS_NAME_HELLO_COS;
	String PROFILE_TUTORIAL_TASK_HELLO_COS_INSTANTIATES_CANONICAL = PROFILE_TUTORIAL_TASK_HELLO_COS_PROCESS_URI
			+ "|" + RESOURCE_VERSION;
	String PROFILE_TUTORIAL_TASK_HELLO_COS_MESSAGE_NAME = "helloCos";

	String TUTORIAL_COS_ORGANIZATION_IDENTIFIER = "Test_COS";

	// The HELLO_HRP constants are only needed for exercise 5 and above
	String PROCESS_NAME_HELLO_HRP = "helloHrp";
	String PROCESS_NAME_FULL_HELLO_HRP = "dsfdev_" + PROCESS_NAME_HELLO_HRP;

	String PROFILE_TUTORIAL_TASK_HELLO_HRP = "http://dsf.dev/fhir/StructureDefinition/task-hello-hrp";
	String PROFILE_TUTORIAL_TASK_HELLO_HRP_AND_LATEST_VERSION = PROFILE_TUTORIAL_TASK_HELLO_HRP + "|" + VERSION;
	String PROFILE_TUTORIAL_TASK_HELLO_HRP_PROCESS_URI = "http://dsf.dev/bpe/Process/" + PROCESS_NAME_HELLO_HRP;
	String PROFILE_TUTORIAL_TASK_HELLO_HRP_INSTANTIATES_CANONICAL = PROFILE_TUTORIAL_TASK_HELLO_HRP_PROCESS_URI
			+ "|" + RESOURCE_VERSION;
	String PROFILE_TUTORIAL_TASK_HELLO_HRP_MESSAGE_NAME = "helloHrp";

	String CODESYSTEM_TUTORIAL = "http://dsf.dev/fhir/CodeSystem/tutorial";
	String CODESYSTEM_TUTORIAL_VALUE_TUTORIAL_INPUT = "tutorial-input";

	String TUTORIAL_HRP_ORGANIZATION_IDENTIFIER = "Test_HRP";
}
