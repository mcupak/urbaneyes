package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import edu.toronto.ece1778.urbaneyes.model.Point;

/**
 * Managing bean for point. Performs operations on points in a database.
 * 
 * @author mcupak
 * 
 */
@Stateless
@Named
public class PointManager {

	@Inject
	private EntityManager em;

	/**
	 * Finds point by its ID.
	 * 
	 * @param id
	 *            ID of a point to find
	 * @return point with the given ID
	 */
	public Point getPoint(Long id) {
		return em.find(Point.class, id);
	}

	/**
	 * Retrieves all the points in a database.
	 * 
	 * @return list of all the points
	 */
	@Produces
	@Model
	public List<Point> getPoints() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Point.class));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Saves a new point to a database.
	 * 
	 * @param point
	 *            point to save
	 */
	public void addPoint(Point point) {
		if (point != null) {
			em.persist(point);
		}
	}

	/**
	 * Removes an existing point from a database.
	 * 
	 * @param id
	 *            ID of a point to remove
	 * @return true if the remove was performed, false otherwise
	 */
	public boolean deletePoint(Long id) {
		Point point = getPoint(id);
		if (point == null) {
			return false;
		} else {
			em.remove(point);
			return true;
		}
	}

	/**
	 * Saves changes to an existing persited point.
	 * 
	 * @param point
	 *            point to save
	 */
	public void editPoint(Point point) {
		if (getPoint(point.getId()) != null) {
			em.merge(point);
		}
	}
}
