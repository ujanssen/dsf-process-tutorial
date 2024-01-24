package dev.dsf.process.tutorial.exercise_5.profile;

import static dev.dsf.process.tutorial.TutorialProcessPluginDefinition.RELEASE_DATE;
import static dev.dsf.process.tutorial.TutorialProcessPluginDefinition.VERSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import dev.dsf.fhir.authorization.process.ProcessAuthorizationHelper;
import dev.dsf.fhir.authorization.process.ProcessAuthorizationHelperImpl;
import dev.dsf.fhir.validation.ResourceValidator;
import dev.dsf.fhir.validation.ResourceValidatorImpl;
import dev.dsf.fhir.validation.ValidationSupportRule;
import org.hl7.fhir.r4.model.ActivityDefinition;
import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Type;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;

public class ActivityDefinitionProfileTest
{
	private static final Logger logger = LoggerFactory.getLogger(ActivityDefinitionProfileTest.class);

	@ClassRule
	public static final ValidationSupportRule validationRule = new ValidationSupportRule(VERSION, RELEASE_DATE,
			Arrays.asList("dsf-activity-definition-1.0.0.xml", "dsf-extension-process-authorization-1.0.0.xml",
					"dsf-extension-process-authorization-parent-organization-role-1.0.0.xml",
					"dsf-extension-process-authorization-parent-organization-role-practitioner-1.0.0.xml",
					"dsf-extension-process-authorization-organization-1.0.0.xml",
					"dsf-extension-process-authorization-organization-practitioner-1.0.0.xml",
					"dsf-extension-process-authorization-practitioner-1.0.0.xml",
					"dsf-coding-process-authorization-local-all-1.0.0.xml",
					"dsf-coding-process-authorization-local-all-practitioner-1.0.0.xml",
					"dsf-coding-process-authorization-local-parent-organization-role-1.0.0.xml",
					"dsf-coding-process-authorization-local-parent-organization-role-practitioner-1.0.0.xml",
					"dsf-coding-process-authorization-local-organization-1.0.0.xml",
					"dsf-coding-process-authorization-local-organization-practitioner-1.0.0.xml",
					"dsf-coding-process-authorization-remote-all-1.0.0.xml",
					"dsf-coding-process-authorization-remote-parent-organization-role-1.0.0.xml",
					"dsf-coding-process-authorization-remote-organization-1.0.0.xml"),
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-process-authorization-1.0.0.xml"),
			Arrays.asList("dsf-read-access-tag-1.0.0.xml", "dsf-process-authorization-recipient-1.0.0.xml",
					"dsf-process-authorization-requester-1.0.0.xml"));

	private final ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	private final ProcessAuthorizationHelper processAuthorizationHelper = new ProcessAuthorizationHelperImpl();

	@Test
	public void testHelloDicValid() throws Exception
	{
		ActivityDefinition ad = validationRule
				.readActivityDefinition(Paths.get("src/main/resources/fhir/ActivityDefinition/hello-dic.xml"));

		ValidationResult result = resourceValidator.validate(ad);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());

		assertTrue(processAuthorizationHelper.isValid(ad, taskProfile -> true, practitionerRole -> true, orgIdentifier -> true, role -> true));
	}

	@Test
	public void testHelloCosValid() throws Exception
	{
		ActivityDefinition ad = validationRule
				.readActivityDefinition(Paths.get("src/main/resources/fhir/ActivityDefinition/hello-cos.xml"));

		ValidationResult result = resourceValidator.validate(ad);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());

		assertTrue(processAuthorizationHelper.isValid(ad, taskProfile -> true, practitionerRole -> true, orgIdentifier -> true, role -> true));
	}

	@Test
	public void testHelloHrpValid() throws Exception
	{
		ActivityDefinition ad = validationRule
				.readActivityDefinition(Paths.get("src/main/resources/fhir/ActivityDefinition/hello-hrp.xml"));

		ValidationResult result = resourceValidator.validate(ad);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());

		assertTrue(processAuthorizationHelper.isValid(ad, taskProfile -> true, practitionerRole -> true, orgIdentifier -> true, role -> true));
	}

	@Test
	public void testHelloDicRequester() throws Exception
	{
		ActivityDefinition ad = validationRule
				.readActivityDefinition(Paths.get("src/main/resources/fhir/ActivityDefinition/hello-dic.xml"));

		List<Extension> extensionsByUrl = ad
				.getExtensionsByUrl("http://dsf.dev/fhir/StructureDefinition/extension-process-authorization");
		assertNotNull(extensionsByUrl);
		assertEquals(2, extensionsByUrl.size());

		Extension processAuthorization0 = extensionsByUrl.get(0);
		Extension requester = processAuthorization0.getExtensionsByUrl("requester").stream()
				.filter(extension -> ((Coding) extension.getValue()).getCode().equals("LOCAL_ALL"))
				.findFirst().get();
		assertNotNull(requester);

		Type value = requester.getValue();
		assertTrue(value instanceof Coding);

		Coding coding = (Coding) value;
		assertEquals("http://dsf.dev/fhir/CodeSystem/process-authorization", coding.getSystem());
		assertEquals("LOCAL_ALL", coding.getCode());

		Extension processAuthorization1 = extensionsByUrl.get(1);

		Extension extMessageName = processAuthorization1.getExtensionByUrl("message-name");
		assertNotNull(extMessageName);
		assertTrue(extMessageName.getValue() instanceof StringType);
		assertEquals("goodbyeDic", ((StringType) extMessageName.getValue()).getValue());

		Extension extTaskProfile = processAuthorization1.getExtensionByUrl("task-profile");
		assertNotNull(extTaskProfile);
		assertTrue(extTaskProfile.getValue() instanceof CanonicalType);
		assertEquals("http://dsf.dev/fhir/StructureDefinition/task-goodbye-dic|" + VERSION,
				((CanonicalType) extTaskProfile.getValue()).getValue());

		Extension extRequester = processAuthorization1.getExtensionByUrl("requester");
		assertNotNull(extRequester);
		assertTrue(extRequester.getValue() instanceof Coding);
		assertEquals("http://dsf.dev/fhir/CodeSystem/process-authorization",
				((Coding) extRequester.getValue()).getSystem());
		assertEquals("REMOTE_ROLE", ((Coding) extRequester.getValue()).getCode());
		Extension extRequesterExtRole = ((Coding) extRequester.getValue()).getExtensionByUrl(
				"http://dsf.dev/fhir/StructureDefinition/extension-process-authorization-parent-organization-role");
		assertNotNull(extRequesterExtRole);
		Extension extRequesterExtRoleConsortium = extRequesterExtRole.getExtensionByUrl("parent-organization");
		assertNotNull(extRequesterExtRoleConsortium);
		assertTrue(extRequesterExtRoleConsortium.getValue() instanceof Identifier);
		assertEquals("http://dsf.dev/sid/organization-identifier",
				((Identifier) extRequesterExtRoleConsortium.getValue()).getSystem());
		assertEquals("medizininformatik-initiative.de",
				((Identifier) extRequesterExtRoleConsortium.getValue()).getValue());
		Extension extRequesterExtRoleRole = extRequesterExtRole.getExtensionByUrl("organization-role");
		assertNotNull(extRequesterExtRoleRole);
		assertTrue(extRequesterExtRoleRole.getValue() instanceof Coding);
		assertEquals("http://dsf.dev/fhir/CodeSystem/organization-role",
				((Coding) extRequesterExtRoleRole.getValue()).getSystem());
		assertEquals("HRP", ((Coding) extRequesterExtRoleRole.getValue()).getCode());

		Extension extRecipient = processAuthorization1.getExtensionByUrl("recipient");
		assertNotNull(extRecipient);
		assertEquals("http://dsf.dev/fhir/CodeSystem/process-authorization",
				((Coding) extRecipient.getValue()).getSystem());
		assertEquals("LOCAL_ROLE", ((Coding) extRecipient.getValue()).getCode());
		Extension extRecipientExtRole = ((Coding) extRecipient.getValue()).getExtensionByUrl(
				"http://dsf.dev/fhir/StructureDefinition/extension-process-authorization-parent-organization-role");
		assertNotNull(extRecipientExtRole);
		Extension extRecipientExtRoleConsortium = extRecipientExtRole.getExtensionByUrl("parent-organization");
		assertNotNull(extRecipientExtRoleConsortium);
		assertTrue(extRecipientExtRoleConsortium.getValue() instanceof Identifier);
		assertEquals("http://dsf.dev/sid/organization-identifier",
				((Identifier) extRecipientExtRoleConsortium.getValue()).getSystem());
		assertEquals("medizininformatik-initiative.de",
				((Identifier) extRecipientExtRoleConsortium.getValue()).getValue());
		Extension extRecipientExtRoleRole = extRecipientExtRole.getExtensionByUrl("organization-role");
		assertNotNull(extRecipientExtRoleRole);
		assertTrue(extRecipientExtRoleRole.getValue() instanceof Coding);
		assertEquals("http://dsf.dev/fhir/CodeSystem/organization-role",
				((Coding) extRecipientExtRoleRole.getValue()).getSystem());
		assertEquals("DIC", ((Coding) extRecipientExtRoleRole.getValue()).getCode());
	}
}
