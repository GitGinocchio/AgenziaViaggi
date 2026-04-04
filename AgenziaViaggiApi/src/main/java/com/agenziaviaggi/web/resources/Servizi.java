package com.agenziaviaggi.web.resources;

import com.agenziaviaggi.web.entities.Servizio;
import com.agenziaviaggi.web.services.ServizioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Giulio Tognetto
 */
@Path("servizi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Servizi {

    @Inject
    private ServizioService servizioService;

    @GET
    public List<Servizio> getAll() {
        return servizioService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        Servizio s = servizioService.findById(id);
        if (s == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Servizio non trovato")
                .build();
        }
        return Response.ok(s).build();
    }

    @GET
    @Path("/pacchetto/{idPacchetto}")
    public List<Servizio> getByPacchetto(@PathParam("idPacchetto") Integer idPacchetto) {
        return servizioService.findByPacchetto(idPacchetto);
    }

    @POST
    public Response save(Servizio s) {
        try {
            servizioService.save(s);
            return Response.status(Response.Status.CREATED)
                .entity(s)
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Errore nel salvataggio: " + e.getMessage())
                .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Integer id) {
        servizioService.delete(id);
        return Response.noContent().build();
    }
}