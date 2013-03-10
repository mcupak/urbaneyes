package edu.toronto.ece1778.urbaneyes.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.toronto.ece1778.urbaneyes.data.UserRegistration;
import edu.toronto.ece1778.urbaneyes.model.User;
import edu.toronto.ece1778.urbaneyes.util.Resources;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(User.class, UserRegistration.class, Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}

	@Inject
	UserRegistration memberRegistration;

	@Inject
	Logger log;

	@Test
	public void testRegister() throws Exception {
		User newMember = memberRegistration.getNewMember();
		newMember.setName("Jane Doe");
		newMember.setEmail("jane@mailinator.com");
		newMember.setPassword("test");
		memberRegistration.register();
		assertNotNull(newMember.getId());
		log.info(newMember.getName() + " was persisted with id "
				+ newMember.getId());
	}

}
