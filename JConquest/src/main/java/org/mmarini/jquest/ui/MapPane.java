package org.mmarini.jquest.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.border.Border;

import org.mmarini.jquest.Constants;
import org.mmarini.jquest.Fleet;
import org.mmarini.jquest.Owner;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;
import org.mmarini.jquest.Universe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author US00852
 * @version $Id: MapPane.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class MapPane extends JComponent {
	private static final Logger logger = LoggerFactory.getLogger(MapPane.class);

	private static final long serialVersionUID = 284967415780096838L;
	public static final Color[] COLORS = new Color[] { Color.YELLOW,
			Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.CYAN,
			Color.ORANGE, Color.PINK };
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color LINE_COLOR = Color.RED;

	private static final BasicStroke NO_SIZE_LINE_STROKE = new BasicStroke(0);
	private static final double BOUND_SIZE = Constants.UNIVERSE_CELL_COUNT;

	private final List<PlanetAdapter> planetShape;
	private final Line2D.Float line;
	private final MouseListener mouseListener;
	private final MouseMotionListener mouseMotionListener;

	private List<MapListener> mapListener;
	private Owner owner;
	private Planet selectedPlanet;
	private boolean trackingFleetPlan;
	private Point mouseLocation;
	private Universe universe;
	private Planet targetPlanet;
	private final Map<Owner, Color> colorMap;

	/**
	 * 
	 */
	public MapPane() {
		planetShape = new ArrayList<PlanetAdapter>();
		mapListener = new ArrayList<MapListener>(2);
		colorMap = new HashMap<Owner, Color>();
		mouseListener = new MouseListener() {
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
		mouseMotionListener = new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent ev) {
				handleMouseDragging(ev);
			}

			@Override
			public void mouseMoved(MouseEvent ev) {
				handleMouseOver(ev);
			}
		};
		line = new Line2D.Float();

		setDoubleBuffered(true);
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseMotionListener);
	}

	/**
	 * @param l
	 */
	public synchronized void addMapListener(final MapListener l) {
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
	private Rectangle calculatePaintBound() {
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

	/**
	 * 
	 */
	private void createShapes() {
		colorMap.clear();
		int idx = 0;
		for (final Owner o : universe.getOwners()) {
			colorMap.put(o, COLORS[idx++]);
			if (idx >= COLORS.length)
				idx = 0;
		}

		planetShape.clear();
		for (Planet planet : universe.getPlanets())
			planetShape.add(new PlanetAdapter(planet, colorMap));
	}

	/**
	 * @param ev
	 */
	private void dragMouse(final MouseEvent ev) {
		mouseLocation = getUniverseLocation(ev.getPoint());
		targetPlanet = findPlanet(mouseLocation);
		if (targetPlanet != null && targetPlanet.equals(selectedPlanet))
			targetPlanet = null;
	}

	/**
	 * @param point
	 * @return
	 */
	private Planet findPlanet(final Point point) {
		if (universe == null)
			return null;
		Planet found = null;
		for (final PlanetAdapter pa : planetShape)
			if (pa.contains(point)) {
				found = pa.getPlanet();
				break;
			}
		return found;
	}

	/**
	 * @param ev
	 */
	private void fireFleetPlanCancelled() {
		final MapEvent e = new MapEvent(this, selectedPlanet, targetPlanet,
				mouseLocation);
		logger.debug("fireFleetPlanCancelled {}", e);
		for (final MapListener l : mapListener)
			l.fleetPlanCancelled(e);
	}

	/**
	 * @param ev
	 */
	private void fireFleetPlanChanged() {
		final MapEvent e = new MapEvent(this, selectedPlanet, targetPlanet,
				mouseLocation);
		logger.debug("fireFleetPlanChanged {}", e);
		for (final MapListener l : mapListener)
			l.fleetPlanChanged(e);
	}

	/**
	 * @param ev
	 */
	private void fireFleetPlanConfirmed() {
		final MapEvent e = new MapEvent(this, selectedPlanet, targetPlanet,
				mouseLocation);
		logger.debug("fireFleetPlanConfirmed {}", e);
		for (final MapListener l : mapListener)
			l.fleetPlanConfirmed(e);
	}

	/**
	 */
	private void firePlanetEntered() {
		final MapEvent e = new MapEvent(this, selectedPlanet, null,
				mouseLocation);
		logger.debug("firePlanetEntered {}", e);
		for (final MapListener l : mapListener)
			l.planetEntered(e);
	}

	/**
	 * 
	 */
	private void firePlanetExited() {
		final MapEvent e = new MapEvent(this, selectedPlanet, null,
				mouseLocation);
		logger.debug("firePlanetExited {}", e);
		for (final MapListener l : mapListener)
			l.planetExited(e);
	}

	/**
	 * @return Returns the owner.
	 */
	public Owner getOwner() {
		return owner;
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
	private Point getUniverseLocation(final java.awt.Point location) {
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
	private void handleMouseDragging(final MouseEvent ev) {
		if (!trackingFleetPlan)
			return;
		dragMouse(ev);
		repaint();
		fireFleetPlanChanged();
	}

	/**
	 * @param ev
	 */
	private void handleMouseOver(final MouseEvent ev) {
		if (universe == null)
			return;
		mouseLocation = getUniverseLocation(ev.getPoint());
		final Planet foundPlanet = findPlanet(mouseLocation);
		if (foundPlanet == null || !foundPlanet.equals(selectedPlanet)) {
			if (selectedPlanet != null)
				firePlanetExited();
			selectedPlanet = foundPlanet;
			if (selectedPlanet != null)
				firePlanetEntered();
		}
	}

	/**
	 * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
	 */
	@Override
	protected void paintChildren(final Graphics g) {
		super.paintChildren(g);
		final Rectangle bound = this.calculatePaintBound();
		final Graphics2D g2 = (Graphics2D) g.create(bound.x, bound.y,
				bound.width, bound.height);
		this.paintMap(g2);
	}

	/**
	 * @param g
	 */
	private void paintMap(final Graphics2D g) {
		if (universe == null)
			return;
		final Rectangle bound = g.getClipBounds();
		g.setColor(BACKGROUND_COLOR);
		g.fill(bound);

		g.translate(bound.width / 2, bound.height / 2);
		g.scale(bound.width / BOUND_SIZE, bound.height / BOUND_SIZE);

		for (final PlanetAdapter p : planetShape)
			p.draw(g);
		for (final Fleet f : universe.getFleets())
			new FleetAdapter(f, colorMap).draw(g);
		if (trackingFleetPlan) {
			line.setLine(selectedPlanet.getLocation(), mouseLocation);
			g.setColor(LINE_COLOR);
			g.setStroke(NO_SIZE_LINE_STROKE);
			g.draw(line);
		}
	}

	/**
	 * @param l
	 */
	public synchronized void removeMapListener(final MapListener l) {
		List<MapListener> list = this.mapListener;
		if (!list.contains(l))
			return;
		list = new ArrayList<MapListener>(list);
		list.remove(l);
		this.mapListener = list;
	}

	/**
	 * @param owner
	 *            The owner to set.
	 */
	public void setOwner(final Owner owner) {
		this.owner = owner;
	}

	/**
	 * @param universe
	 *            The universe to set.
	 */
	public void setUniverse(final Universe universe) {
		this.universe = universe;
		createShapes();
		repaint();
	}

	/**
	 * @param e
	 */
	private void startFleetPlanTracking(final MouseEvent e) {
		mouseLocation = getUniverseLocation(e.getPoint());
		final Planet p = findPlanet(mouseLocation);
		if (p != null && p.getOwner() == owner) {
			selectedPlanet = p;
			trackingFleetPlan = true;
			fireFleetPlanChanged();
		}
	}

	/**
	 * @param ev
	 */
	private void stopFleetPlanTracking(final MouseEvent ev) {
		dragMouse(ev);
		trackingFleetPlan = false;
		repaint();
		if (targetPlanet == null || targetPlanet.equals(selectedPlanet))
			fireFleetPlanCancelled();
		else
			fireFleetPlanConfirmed();
	}
}