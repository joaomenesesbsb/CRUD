package com.devsuperior.crud.services;

import com.devsuperior.crud.dto.ClientDTO;
import com.devsuperior.crud.entities.Client;
import com.devsuperior.crud.exeptions.DatabaseExeption;
import com.devsuperior.crud.exeptions.RecourceNotFoundExeption;
import com.devsuperior.crud.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = repository.findById(id).orElseThrow(
                () -> new RecourceNotFoundExeption("Recurso não encontrado"));
        return new ClientDTO(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> new ClientDTO(x));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try{
            Client entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw  new RecourceNotFoundExeption("Recurso n~so encontrado");
        }

        }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        try{
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new RecourceNotFoundExeption("Recurso não encontrado");
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseExeption("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
