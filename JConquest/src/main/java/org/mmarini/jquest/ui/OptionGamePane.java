package org.mmarini.jquest.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.mmarini.jquest.UniverseBuilder;

/**
 * @author US00852
 * @version $Id: OptionGamePane.java,v 1.3 2006/03/16 22:35:24 marco Exp $
 */
public class OptionGamePane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6956649285388956403L;
	public static final int SLOW_REACTION = 0;
	public static final int NORMAL_REACTION = 1;
	public static final int FAST_REACTION = 2;
	public static final int IMMEDIATE_REACTION = 3;

	private final JSpinner ownerComponent;
	private final JSpinner planetComponent;
	private final JComboBox<String> opponentReaction;

	/**
	 * 
	 */
	public OptionGamePane() {
		ownerComponent = new JSpinner();
		planetComponent = new JSpinner();
		opponentReaction = new JComboBox<>();
		init();
	}

	/**
	 * @return
	 */
	public int getOwnerCount() {
		return ((Number) ownerComponent.getValue()).intValue();
	}

	/**
	 * @return
	 */
	public int getPlanetCount() {
		return ((Number) planetComponent.getValue()).intValue();
	}

	/**
	 * 
	 * @return
	 */
	public int getReaction() {
		return opponentReaction.getSelectedIndex();
	}

	/**
	 * 
	 */
	protected void init() {
		opponentReaction
				.addItem(Messages.getString("OptionGamePane.slow.text")); //$NON-NLS-1$
		opponentReaction.addItem(Messages
				.getString("OptionGamePane.normal.text")); //$NON-NLS-1$
		opponentReaction
				.addItem(Messages.getString("OptionGamePane.fast.text")); //$NON-NLS-1$
		opponentReaction.addItem(Messages
				.getString("OptionGamePane.immediate.text")); //$NON-NLS-1$
		opponentReaction.setSelectedIndex(NORMAL_REACTION);

		ownerComponent.setModel(new SpinnerNumberModel(2, 2, UniverseBuilder
				.getInstance().getMaxOwnerCount(), 1));
		planetComponent.setModel(new SpinnerNumberModel(3, 1, UniverseBuilder
				.getInstance().getMaxPlanetCount(), 1));

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);

		final JComponent l1 = new JLabel(
				Messages.getString("OptionGamePane.reaction.text")); //$NON-NLS-1$
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(l1, gbc);
		this.add(l1);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(opponentReaction, gbc);
		this.add(opponentReaction);

		final JLabel l2 = new JLabel(
				Messages.getString("OptionGamePane.onwerCount.text")); //$NON-NLS-1$
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(l2, gbc);
		add(l2);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(ownerComponent, gbc);
		add(ownerComponent);

		final JLabel l3 = new JLabel(
				Messages.getString("OptionGamePane.planetCount.text")); //$NON-NLS-1$
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(l3, gbc);
		add(l3);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(planetComponent, gbc);
		add(planetComponent);
	}
}