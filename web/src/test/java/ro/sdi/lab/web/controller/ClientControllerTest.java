package ro.sdi.lab.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import ro.sdi.lab.core.model.Client;
import ro.sdi.lab.core.service.ClientService;
import ro.sdi.lab.web.converter.ClientConverter;
import ro.sdi.lab.web.dto.ClientDto;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerTest
{
    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientConverter clientConverter;

    private Client client1;
    private Client client2;
    private ClientDto clientDto1;
    private ClientDto clientDto2;


    @Before
    public void setup() throws Exception
    {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .build();
        initData();
    }

    @Test
    public void getStudents() throws Exception
    {
        List<Client> clients = Arrays.asList(client1, client2);
        List<ClientDto> clientDtos = Arrays.asList(clientDto1, clientDto2);
        when(clientService.getClients()).thenReturn(clients);
        when(clientConverter.toDtos(clients)).thenReturn(clientDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", anyOf(is("Andrei"), is("Mihai"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();

        verify(clientService, times(1)).getClients();
        verify(clientConverter, times(1)).toDtos(clients);
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    @Test
    public void updateStudent() throws Exception
    {
        when(clientConverter.toModel(clientDto1)).thenReturn(client1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                                 .put("/clients/{id}", client1.getId())
                                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(toJsonString(clientDto1))
                )
                .andExpect(status().isOk());

        verify(clientService, times(1)).updateClient(
                client1.getId(),
                clientDto1.getName()
        );
        verify(clientConverter, times(1)).toModel(clientDto1);
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    @Test
    public void addClient() throws Exception
    {
        when(clientConverter.toModel(clientDto1)).thenReturn(client1);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                                 .post("/clients", client1.getId())
                                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(toJsonString(clientDto1))
                )
                .andExpect(status().isOk());

        verify(clientService, times(1)).addClient(
                client1.getId(),
                clientDto1.getName()
        );
        verify(clientConverter, times(1)).toModel(clientDto1);
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    @Test
    public void deleteClient() throws Exception
    {
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete("/clients/{id}", client1.getId()))
                .andExpect(status().isOk());

        verify(clientService, times(1)).deleteClient(client1.getId());
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    private String toJsonString(ClientDto clientDto)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(clientDto);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void initData()
    {
        client1 = new Client(1, "Andrei");
        client2 = new Client(1, "Mihai");

        clientDto1 = createDto(client1);
        clientDto2 = createDto(client2);

    }

    private ClientDto createDto(Client client)
    {
        return ClientDto.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .build();
    }
}