package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.solder.logging.Logger;

import edu.toronto.ece1778.urbaneyes.model.Point;

@Stateless
@Named
public class PointManager {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public Point getPoint(Long id) {
		return em.find(Point.class, id);
	}

	@Produces
	@Model
	public List<Point> getPoints() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Point.class));
		return em.createQuery(cq).getResultList();
	}

	public void addPoint(Point point) {
		if (point != null) {
			em.persist(point);
		}
	}

	public boolean deletePoint(Long id) {
		Point point = getPoint(id);
		if (point == null) {
			return false;
		} else {
			em.remove(point);
			return true;
		}
	}

	public void editPoint(Point point) {
		if (getPoint(point.getId()) != null) {
			em.merge(point);
		}
	}
}
