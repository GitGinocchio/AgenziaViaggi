package com.agenziaviaggi.web.resources;

import com.agenziaviaggi.web.entities.Pacchetto;
import com.agenziaviaggi.web.services.PacchettoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Giulio Tognetto
 */
@Path("pacchetti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Pacchetti {
    @Inject
    private PacchettoService pacchettoService;

    @GET
    public List<Pacchetto> getAll() {
        return pacchettoService.findAll();
    }
    
    @GET
    @Path("/search")
    public List<Pacchetto> getSearch(@QueryParam("q") String query) {
        return pacchettoService.search(query);
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        Pacchetto p = pacchettoService.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Pacchetto non trovato")
                .build();
        }
        return Response.ok(p).build();
    }

    @GET
    @Path("/prenotazione/{idPrenotazione}")
    public Response getByPrenotazione(@PathParam("idPrenotazione") Integer id) {
        Pacchetto p = pacchettoService.findByPrenotazione(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Nessun pacchetto trovato per questa prenotazione")
                .build();
        }
        return Response.ok(p).build();
    }

    @POST
    public Response create(Pacchetto p) {
        try {
            pacchettoService.save(p);
            return Response.status(Response.Status.CREATED)
                .entity(p)
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Errore nel salvataggio del pacchetto: " + e.getMessage())
                .build();
        }
    }
}