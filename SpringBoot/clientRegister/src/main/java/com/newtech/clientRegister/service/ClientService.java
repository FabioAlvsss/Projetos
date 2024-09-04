package com.newtech.clientRegister.service;

import com.newtech.clientRegister.DTO.ClientDTO;
import com.newtech.clientRegister.entities.Client;
import com.newtech.clientRegister.repository.ClientRespository;
import com.newtech.clientRegister.service.exceptions.DataBaseException;
import com.newtech.clientRegister.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRespository clientRespository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findClientByPage(PageRequest pageRequest) {
        Page<Client> clientPage = clientRespository.findAll(pageRequest);
        return clientPage.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findClientById(Long id) {
        Optional<Client> clientOptional = clientRespository.findById(id);
        Client client = clientOptional.orElseThrow(() -> new ResourceNotFoundException("Categoria não Encontrada"));
        return new ClientDTO(client);
    }

    public ClientDTO save(ClientDTO clientDTO) {
        Client client = new Client();
        copyDTOtoEntity(clientDTO, client);
        client = clientRespository.save(client);
        return new ClientDTO(client);

    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        try {
            Client client = clientRespository.getReferenceById(id);
            copyDTOtoEntity(clientDTO, client);
            clientRespository.save(client);
            return new ClientDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("id Not Found" + id);
        }
    }

    public void delete(Long id) {
        try {
            clientRespository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id não encontrado" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDTOtoEntity(ClientDTO dto, Client client) {
        client.setName(dto.getName());
        client.setChildren(dto.getChildren());
        client.setCpf(dto.getCpf());
        client.setIncome(dto.getIncome());
        client.setBirthDate(dto.getBirthDate());
    }
}
