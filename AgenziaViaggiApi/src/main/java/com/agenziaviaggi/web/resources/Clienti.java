package com.agenziaviaggi.web.resources;

import com.agenziaviaggi.web.entities.Cliente;
import com.agenziaviaggi.web.services.ClienteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Giulio Tognetto
 */
@Path("clienti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Clienti {
    @Inject
    private ClienteService clienteService;
    
    @GET
    public List<Cliente> getAll() {
        return clienteService.findAll();
    }
    
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Cliente c = clienteService.findById(id);
        if (c == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Cliente non trovato")
                .build();
        }
        return Response.ok(c).build();
    }

    @GET
    @Path("/search")
    public Response getByEmail(@QueryParam("email") String email) {
        Cliente c = clienteService.findByEmail(email);
        if (c == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Nessun cliente con questa email")
                .build();
        }
        return Response.ok(c).build();
    }

    @GET
    @Path("/prenotazione/{idPrenotazione}")
    public Response getByPrenotazione(@PathParam("idPrenotazione") int idPrenotazione) {
        Cliente c = clienteService.findByPrenotazione(idPrenotazione);
        if (c == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Nessun cliente associato a questa prenotazione")
                .build();
        }
        return Response.ok(c).build();
    }
    
    @POST
    public Response registraCliente(Cliente c) {
        try {
            clienteService.save(c);
            return Response.status(Response.Status.CREATED)
                .entity(c)
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Errore durante la registrazione: " + e.getMessage())
                .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Integer id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }
}