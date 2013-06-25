package org.mmarini.jquest.ui;

import java.awt.BorderLayout;
import java.text.MessageFormat;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.mmarini.jquest.Constants;
import org.mmarini.jquest.IOwner;
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
	private JTextArea infoText;
	private IOwner owner;

	/**
	 * 
	 */
	public InfoPane() {
		super();
		infoText = new JTextArea();
		this.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(infoText);
		infoText.setColumns(20);
		infoText.setEditable(false);
		this.add(scroll, BorderLayout.CENTER);
	}

	/**
	 * 
	 *
	 */
	public void clearInfo() {
		infoText.setText("");
	}

	/**
	 * @return
	 */
	public IOwner getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 */
	public void setOwner(IOwner owner) {
		this.owner = owner;
	}

	public void showInfo(Planet planet) {
		this.clearInfo();
		showPlanetInfo(planet);
	}

	public void showInfo(Planet source, Planet destination, Point location) {
		this.clearInfo();
		infoText.append("Source planet:");
		infoText.append("\n");
		this.showPlanetInfo(source);

		if (destination != null)
			location = destination.getLocation();
		double distance = location.distance(source.getLocation());

		infoText.append("\n");
		infoText.append("\n");
		infoText.append(MessageFormat.format("Distance: {0} units",
				new Object[] { new Double(distance) }));

		double time = distance / Constants.FLEET_SPEED;

		infoText.append("\n");
		infoText.append("\n");
		infoText.append(MessageFormat.format("Time to arrival: {0} years",
				new Object[] { new Double(time) }));

		if (destination != null) {
			infoText.append("\n");
			infoText.append("\n");
			infoText.append("Destination planet:");
			this.showPlanetInfo(destination);
		}
	}

	/**
	 * @param planet
	 */
	protected void showPlanetInfo(Planet planet) {
		infoText.append(planet.getName());
		infoText.append("\n");

		String ownerName = "None";
		IOwner owner = planet.getOwner();
		if (owner != null)
			ownerName = owner.getName();
		infoText.append("owned by " + ownerName);
		infoText.append("\n");

		double shipRate = planet.getShipRate();
		infoText.append(MessageFormat.format("building rate: {0} ships/year",
				new Object[] { new Double(shipRate) }));
		infoText.append("\n");

		double killRate = planet.getKillRate();
		infoText.append(MessageFormat.format("kill rate: {0,number,percent}",
				new Object[] { new Double(killRate) }));
		infoText.append("\n");

		if (planet.getOwner() != null) {
			int shipCount = planet.getShipCount();
			infoText.append(MessageFormat.format("ship count: {0} ships",
					new Object[] { new Integer(shipCount) }));
		}
	}
}