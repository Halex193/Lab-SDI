package ro.sdi.lab.core.service;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import ro.sdi.lab.core.ITConfig;
import ro.sdi.lab.core.model.Client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class ClientServiceTest
{
    @Autowired
    private ClientService clientService;

    @Test
    public void testGet() throws Exception
    {
        List<Client> students = clientService.getClients();
        assertEquals("There should be three clients", 3, students.size());
    }

    @Test
    public void testAdd() throws Exception
    {
        clientService.addClient(7, "Cineva");
        assertEquals(4, clientService.getClients().size());
    }

    @Test
    public void testDelete() throws Exception
    {
        clientService.deleteClient(1);
        assertEquals(2, clientService.getClients().size());
    }

    @Test
    public void testUpdate() throws Exception
    {
        clientService.updateClient(1, "newname");
        assertEquals(3, clientService.getClients().size());
        assertTrue(clientService.getClients().stream().anyMatch(client -> client.getName().equals("newname")));
    }
}