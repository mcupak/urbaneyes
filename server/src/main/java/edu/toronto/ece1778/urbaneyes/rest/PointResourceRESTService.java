package edu.toronto.ece1778.urbaneyes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.model.Point;

/**
 * RESTful service to expose points via XML.
 */
@Path("/points")
@RequestScoped
public class PointResourceRESTService {
	@Inject
	private EntityManager em;

	/**
	 * Exposes all the points in the database via XMl.
	 * 
	 * @return list of all the points
	 */
	@GET
	@Produces("text/xml")
	public List<Point> listAllPoints() {
		@SuppressWarnings("unchecked")
		final List<Point> results = em.createQuery(
				"select m from Point m order by m.name").getResultList();
		return results;
	}

	/**
	 * Looks up a point by its ID and exposes it via XML.
	 * 
	 * @param id
	 *            point id
	 * @return point with the given ID
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Point lookupPointById(@PathParam("id") long id) {
		return em.find(Point.class, id);
	}
}
