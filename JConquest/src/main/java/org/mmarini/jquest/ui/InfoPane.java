package org.mmarini.jquest.ui;

import java.awt.BorderLayout;
import java.util.Formatter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.mmarini.jquest.Constants;
import org.mmarini.jquest.Owner;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;

/**
 * @author US00852
 * @version $Id: InfoPane.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class InfoPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6737579588386037273L;
	private final JTextArea infoText;

	/**
	 * 
	 */
	public InfoPane() {
		infoText = new JTextArea();
		setLayout(new BorderLayout());
		infoText.setColumns(20);
		infoText.setEditable(false);
		add(new JScrollPane(infoText), BorderLayout.CENTER);
	}

	/**
	 * 
	 * @param key
	 * @param args
	 */
	private void append(final String key, final Object... args) {
		final Formatter f = new Formatter();
		infoText.append(f.format(Messages.getString(key), args).toString());
		f.close();
	}

	/**
	 * 
	 */
	private void appendln() {
		infoText.append("\n"); //$NON-NLS-1$
	}

	/**
	 * 
	 *
	 */
	public void clear() {
		infoText.setText(""); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param planet
	 */
	public void showInfo(final Planet planet) {
		clear();
		showPlanetInfo(planet);
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @param location
	 */
	public void showInfo(final Planet source, final Planet destination,
			final Point location) {
		clear();
		append("InfoPane.sourcePlanet.text"); //$NON-NLS-1$
		appendln();
		showPlanetInfo(source);
		appendln();
		appendln();

		final Point l = destination != null ? destination.getLocation()
				: location;
		append("InfoPane.distance.text", l.distance(source.getLocation()));//$NON-NLS-1$
		appendln();
		appendln();

		append("InfoPane.timeArrival.text", l.distance(source.getLocation()) / Constants.FLEET_SPEED); //$NON-NLS-1$

		if (destination != null) {
			appendln();
			appendln();

			append("InfoPane.destPlanet.text"); //$NON-NLS-1$
			showPlanetInfo(destination);
		}
	}

	/**
	 * @param planet
	 */
	private void showPlanetInfo(final Planet planet) {
		infoText.append(planet.getName());
		appendln();

		final Owner owner = planet.getOwner();
		append("InfoPane.owned.text", owner != null ? owner.getName() //$NON-NLS-1$
				: ""); //$NON-NLS-1$
		appendln();

		append("InfoPane.buildingRate.text", planet.getShipRate()); //$NON-NLS-1$
		appendln();

		append("InfoPane.killRate.text", planet.getKillRate() * 100); //$NON-NLS-1$
		appendln();

		if (planet.getOwner() != null)
			append("InfoPane.shipCount.text", planet.getShipCount()); //$NON-NLS-1$
	}
}