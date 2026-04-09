package com.agenziaviaggi.web.resources;

import com.agenziaviaggi.web.entities.Prenotazione;
import com.agenziaviaggi.web.services.PrenotazioneService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
@Path("prenotazioni")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Prenotazioni {

    @Inject
    private PrenotazioneService prenotazioneService;

    @GET
    public List<Prenotazione> getAll() {
        return prenotazioneService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        Prenotazione p = prenotazioneService.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Prenotazione non trovata")
                .build();
        }
        return Response.ok(p).build();
    }

    @GET
    @Path("/cliente/{idCliente}")
    public List<Prenotazione> getByCliente(@PathParam("idCliente") Integer idCliente) {
        return prenotazioneService.findByCliente(idCliente);
    }

    @POST
    public Response create(Prenotazione p) {
        try {
            prenotazioneService.save(p);
            return Response.status(Response.Status.CREATED)
                .entity(p)
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Errore nella creazione: " + e.getMessage())
                .build();
        }
    }

    @PUT
    public Response aggiornaPrenotazione(Prenotazione p) {
        try {
            if (p.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID della prenotazione mancante")
                    .build();
            }

            prenotazioneService.save(p);

            return Response.ok(p).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Errore durante l'aggiornamento della prenotazione: " + e.getMessage())
                .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Integer id) {
        prenotazioneService.delete(id);
        return Response.noContent().build();
    }
}