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

	private JSpinner ownerComponent;
	private JSpinner planetComponent;
	private JComboBox opponentReaction;

	/**
	 * 
	 */
	public OptionGamePane() {
		ownerComponent = new JSpinner();
		planetComponent = new JSpinner();
		opponentReaction = new JComboBox();
		init();
	}

	/**
	 * @return Returns the opponentReaction.
	 */
	protected JComboBox getOpponentReaction() {
		return opponentReaction;
	}

	/**
	 * @return Returns the opponentSlider.
	 */
	protected JSpinner getOwnerComponent() {
		return ownerComponent;
	}

	/**
	 * @return
	 */
	public int getOwnerCount() {
		return ((Number) this.getOwnerComponent().getValue()).intValue();
	}

	/**
	 * @return Returns the planetSlider.
	 */
	protected JSpinner getPlanetComponent() {
		return planetComponent;
	}

	/**
	 * @return
	 */
	public int getPlanetCount() {
		return ((Number) this.getPlanetComponent().getValue()).intValue();
	}

	public int getReaction() {
		return this.getOpponentReaction().getSelectedIndex();
	}

	/**
	 * 
	 */
	protected void init() {
		// this.setLayout(new GridBagLayout());
		JComboBox list = this.getOpponentReaction();
		list.addItem("Slow");
		list.addItem("Normal");
		list.addItem("Fast");
		list.addItem("Immediate");
		list.setSelectedIndex(NORMAL_REACTION);

		SpinnerNumberModel model = new SpinnerNumberModel(2, 2, UniverseBuilder
				.getInstance().getMaxOwnerCount(), 1);
		JSpinner spinner = this.getOwnerComponent();
		spinner.setModel(model);
		model = new SpinnerNumberModel(3, 1, UniverseBuilder.getInstance()
				.getMaxPlanetCount(), 1);
		spinner = this.getPlanetComponent();
		spinner.setModel(model);

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);

		JComponent comp;
		comp = new JLabel("Reaction level");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(comp, gbc);
		this.add(comp);

		comp = this.getOpponentReaction();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(comp, gbc);
		this.add(comp);

		comp = new JLabel("Owner count");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(comp, gbc);
		this.add(comp);

		comp = this.getOwnerComponent();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(comp, gbc);
		this.add(comp);

		comp = new JLabel("Planet count");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(comp, gbc);
		this.add(comp);

		comp = this.getPlanetComponent();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbl.setConstraints(comp, gbc);
		this.add(comp);
	}
}