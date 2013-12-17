package org.mmarini.jquest.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.border.Border;

import org.mmarini.jquest.Constants;
import org.mmarini.jquest.Fleet;
import org.mmarini.jquest.IOwner;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;
import org.mmarini.jquest.Universe;

/**
 * @author US00852
 * @version $Id: MapPane.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class MapPane extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 284967415780096838L;
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color LINE_COLOR = Color.RED;

	private static final BasicStroke NO_SIZE_LINE_STROKE = new BasicStroke(0);
	private static final double BOUND_SIZE = Constants.UNIVERSE_CELL_COUNT;

	private MapEvent mapEvent = new MapEvent(this);
	private List<PlanetAdapter> planetShape = new ArrayList<PlanetAdapter>();
	private FleetAdapter fleetAdapter = new FleetAdapter();
	private Universe universe;
	private List<MapListener> mapListener = new ArrayList<MapListener>(2);
	private IOwner owner;
	private Line2D.Float line = new Line2D.Float();
	private MouseListener mouseListener = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			startFleetPlanTracking(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			stopFleetPlanTracking(e);
		}
	};
	private MouseMotionListener mouseMotionListener = new MouseMotionListener() {
		@Override
		public void mouseDragged(MouseEvent ev) {
			handleMouseDragging(ev);
		}

		@Override
		public void mouseMoved(MouseEvent ev) {
			handleMouseOver(ev);
		}
	};
	private Planet selectedPlanet;
	private boolean trackingFleetPlan;
	private Point mouseLocation;

	/**
	 * 
	 */
	public MapPane() {
		super();
		this.setDoubleBuffered(true);
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseMotionListener);
	}

	/**
	 * @param l
	 */
	public synchronized void addMapListener(MapListener l) {
		List<MapListener> list = this.mapListener;
		if (list.contains(l))
			return;
		list = new ArrayList<MapListener>(list);
		list.add(l);
		this.mapListener = list;
	}

	/**
	 * @return
	 */
	protected Rectangle calculatePaintBound() {
		Insets insets = new Insets(0, 0, 0, 0);
		Border border = this.getBorder();
		if (border != null)
			insets = border.getBorderInsets(this);
		int x = insets.left;
		int y = insets.top;
		Dimension size = this.getSize();
		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.left;
		Rectangle bound = new Rectangle(x, y, w, h);
		return bound;
	}

	protected void createShapes() {
		List<PlanetAdapter> shape = this.getPlanetShape();
		shape.clear();
		for (Planet planet : universe.getPlanets()) {
			PlanetAdapter adpt = new PlanetAdapter(planet);
			shape.add(adpt);
		}
	}

	/**
	 * @param ev
	 */
	public void dragMouse(MouseEvent ev) {
		Point point = this.getUniverseLocation(ev.getPoint());
		this.setMouseLocation(point);
		Planet planet = this.findPlanet(point);
		if (planet != null && planet.equals(mapEvent.getSourcePlanet()))
			planet = null;
		mapEvent.setLocation(point);
		mapEvent.setDestinationPlanet(planet);
	}

	/**
	 * @param point
	 * @return
	 */
	protected Planet findPlanet(Point point) {
		Universe universe = this.getUniverse();
		if (universe == null)
			return null;
		Planet found = null;
		for (PlanetAdapter planet : planetShape) {
			if (planet.contains(point)) {
				found = planet.getPlanet();
				break;
			}
		}
		return found;
	}

	/**
	 * @param ev
	 */
	protected void fireFleetPlanCancelled() {
		List<MapListener> list = this.mapListener;
		for (MapListener l : list) {
			l.fleetPlanCancelled(mapEvent);
		}
	}

	/**
	 * @param ev
	 */
	protected void fireFleetPlanChanged() {
		List<MapListener> list = this.mapListener;
		for (MapListener l : list) {
			l.fleetPlanChanged(mapEvent);
		}
	}

	/**
	 * @param ev
	 */
	protected void fireFleetPlanConfirmed() {
		List<MapListener> list = this.mapListener;
		for (MapListener l : list) {
			l.fleetPlanConfirmed(mapEvent);
		}
	}

	/**
	 * @param ev
	 */
	protected void firePlanetEntered() {
		List<MapListener> list = this.mapListener;
		for (MapListener l : list) {
			l.planetEntered(mapEvent);
		}
	}

	/**
	 * @param ev
	 */
	protected void firePlanetExited() {
		List<MapListener> list = this.mapListener;
		for (MapListener l : list) {
			l.planetExited(mapEvent);
		}
	}

	/**
	 * @return Returns the mouseLocation.
	 */
	protected Point getMouseLocation() {
		return mouseLocation;
	}

	/**
	 * @return Returns the owner.
	 */
	public IOwner getOwner() {
		return owner;
	}

	/**
	 * @return Returns the planetShape.
	 */
	protected List<PlanetAdapter> getPlanetShape() {
		return planetShape;
	}

	/**
	 * @return Returns the infoObject.
	 */
	protected Planet getSelectedPlanet() {
		return selectedPlanet;
	}

	/**
	 * @return Returns the universe.
	 */
	public Universe getUniverse() {
		return universe;
	}

	/**
	 * @param location
	 * @return
	 */
	protected Point getUniverseLocation(java.awt.Point location) {
		Rectangle bound = this.calculatePaintBound();
		double x = (double) (location.x - bound.x - bound.width / 2)
				/ bound.width;
		double y = (double) (location.y - bound.y - bound.height / 2)
				/ bound.height;
		x *= BOUND_SIZE;
		y *= BOUND_SIZE;
		Point point = new Point(x, y);
		return point;
	}

	/**
	 * @param ev
	 */
	protected void handleMouseDragging(MouseEvent ev) {
		if (!isTrackingFleetPlan())
			return;
		dragMouse(ev);
		this.repaint();
		this.fireFleetPlanChanged();
	}

	/**
	 * @param ev
	 */
	protected void handleMouseOver(MouseEvent ev) {
		Universe universe = this.getUniverse();
		if (universe == null)
			return;
		Point mouseLocation = this.getUniverseLocation(ev.getPoint());
		Planet foundPlanet = this.findPlanet(mouseLocation);
		Planet selectedPlanet = this.getSelectedPlanet();
		if (foundPlanet != null && foundPlanet.equals(selectedPlanet))
			return;
		this.setSelectedPlanet(foundPlanet);
		mapEvent.setLocation(mouseLocation);
		if (selectedPlanet != null) {
			mapEvent.setSourcePlanet(selectedPlanet);
			this.firePlanetExited();
		}
		if (foundPlanet != null) {
			mapEvent.setSourcePlanet(foundPlanet);
			this.firePlanetEntered();
		}
	}

	/**
	 * @return Returns the trackingFleetPlan.
	 */
	protected boolean isTrackingFleetPlan() {
		return trackingFleetPlan;
	}

	/**
	 * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
	 */
	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		Rectangle bound = this.calculatePaintBound();
		Graphics2D g2 = (Graphics2D) g.create(bound.x, bound.y, bound.width,
				bound.height);
		this.paintMap(g2);
	}

	/**
	 * @param g
	 */
	protected void paintMap(Graphics2D g) {
		Universe universe = this.getUniverse();
		if (universe == null)
			return;
		Rectangle bound = g.getClipBounds();
		g.setColor(BACKGROUND_COLOR);
		g.fill(bound);

		g.translate(bound.width / 2, bound.height / 2);
		g.scale(bound.width / BOUND_SIZE, bound.height / BOUND_SIZE);

		for (PlanetAdapter planet : planetShape) {
			planet.draw(g);
		}
		for (Fleet fleet : universe.getFleets()) {
			fleetAdapter.setFleet(fleet);
			fleetAdapter.draw(g);
		}
		if (!isTrackingFleetPlan())
			return;
		Point2D from = this.getSelectedPlanet().getLocation();
		Point2D to = this.getMouseLocation();
		line.setLine(from, to);
		g.setColor(LINE_COLOR);
		g.setStroke(NO_SIZE_LINE_STROKE);
		g.draw(line);
	}

	/**
	 * @param l
	 */
	public synchronized void removeMapListener(MapListener l) {
		List<MapListener> list = this.mapListener;
		if (!list.contains(l))
			return;
		list = new ArrayList<MapListener>(list);
		list.remove(l);
		this.mapListener = list;
	}

	/**
	 * @see java.awt.Component#repaint()
	 */
	@Override
	public void repaint() {
		if (EventQueue.isDispatchThread())
			super.repaint();
		else
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					MapPane.super.repaint();
				}
			});
	}

	/**
	 * @param mouseLocation
	 *            The mouseLocation to set.
	 */
	protected void setMouseLocation(Point mouseLocation) {
		this.mouseLocation = mouseLocation;
	}

	/**
	 * @param owner
	 *            The owner to set.
	 */
	public void setOwner(IOwner owner) {
		this.owner = owner;
	}

	/**
	 * @param infoObject
	 *            The infoObject to set.
	 */
	protected void setSelectedPlanet(Planet infoObject) {
		this.selectedPlanet = infoObject;
	}

	/**
	 * @param trackingFleetPlan
	 *            The trackingFleetPlan to set.
	 */
	protected void setTrackingFleetPlan(boolean trackingFleetPlan) {
		this.trackingFleetPlan = trackingFleetPlan;
	}

	/**
	 * @param universe
	 *            The universe to set.
	 */
	public void setUniverse(Universe universe) {
		this.universe = universe;
		this.createShapes();
		this.repaint();
	}

	/**
	 * @param e
	 */
	protected void startFleetPlanTracking(MouseEvent e) {
		Point mouseLocation = this.getUniverseLocation(e.getPoint());
		Planet planet = this.findPlanet(mouseLocation);
		if (planet == null)
			return;
		if (planet.getOwner() != this.getOwner())
			return;
		this.setSelectedPlanet(planet);
		this.setMouseLocation(mouseLocation);
		this.setTrackingFleetPlan(true);
		mapEvent.setSourcePlanet(planet);
		mapEvent.setDestinationPlanet(null);
		mapEvent.setLocation(mouseLocation);
		this.fireFleetPlanChanged();
	}

	/**
	 * @param ev
	 */
	protected void stopFleetPlanTracking(MouseEvent ev) {
		this.dragMouse(ev);
		this.setTrackingFleetPlan(false);
		this.setSelectedPlanet(null);
		this.repaint();
		Planet planet = mapEvent.getDestinationPlanet();
		if (planet == null || planet.equals(mapEvent.getSourcePlanet())) {
			this.fireFleetPlanCancelled();
			return;
		}
		this.fireFleetPlanConfirmed();
	}
}