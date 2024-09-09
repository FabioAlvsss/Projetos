package com.newtech.clientRegister.resources;

import com.newtech.clientRegister.DTO.ClientDTO;
import com.newtech.clientRegister.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/client")
public class ClientResource {

    @Autowired
    ClientService clientService;

    @GetMapping("findByPage")
    public ResponseEntity<Page<ClientDTO>> findClientByPage(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                            @RequestParam(value = "linesPerPage", defaultValue = "12", required = false) Integer linesPerPage,
                                                            @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction,
                                                            @RequestParam(value = "orderBy", defaultValue = "id", required = false) String order) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), order);
        Page<ClientDTO> clientDTOPage = clientService.findClientByPage(pageRequest);
        return ResponseEntity.ok().body(clientDTOPage);
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.findClientById(id);
        return ResponseEntity.ok().body(clientDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO clientDTO) {
        ClientDTO client = clientService.save(clientDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @PutMapping("/clientUpdate/{id}")
    public ResponseEntity<ClientDTO> clientUpdate(@PathVariable Long id, @RequestBody ClientDTO client) {
        ClientDTO clientDTO = clientService.update(id, client);
        return ResponseEntity.ok().body(clientDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
