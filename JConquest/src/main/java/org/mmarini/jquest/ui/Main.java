package org.mmarini.jquest.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.mmarini.jquest.InvalidOwnerException;
import org.mmarini.jquest.NoShipsException;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.PlanetCountOutOfBoundException;
import org.mmarini.jquest.Universe;
import org.mmarini.jquest.UniverseBuilder;

/**
 * @author US00852
 * @version $Id: JConquestApplet.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Main {
	/**
	 * Game period (msec)
	 */
	public static final long BASE_GAME_PERIOD = 20;

	/**
	 * Game speed (years / msec)
	 */
	public static final double BASE_GAME_SPEED = 1. / (4. * 1000.);
	public static final double FACTORY_GAME_SPEED = 5;
	public static final double SLOW_GAME_SPEED = BASE_GAME_SPEED
			/ FACTORY_GAME_SPEED;
	public static final double FAST_GAME_SPEED = BASE_GAME_SPEED
			* FACTORY_GAME_SPEED;
	public static final double VERY_FAST_GAME_SPEED = FAST_GAME_SPEED
			* FACTORY_GAME_SPEED;
	public static final double ULTRA_FAST_GAME_SPEED = VERY_FAST_GAME_SPEED
			* FACTORY_GAME_SPEED;

	/**
	 * Reaction table
	 */
	private static final double[] REACTION_TABLE = new double[] {
			60000 * BASE_GAME_SPEED, 10000 * BASE_GAME_SPEED,
			1000 * BASE_GAME_SPEED, 0 };

	private double gameSpeed;
	private final JFrame frame;
	private final Action newGameAction;
	private final Action ultraFastGameAction;
	private final Action veryFastGameAction;
	private final Action fastGameAction;
	private final Action normalGameAction;
	private final Action slowGameAction;
	private final Action exitAction;
	private final MapPane mapPane;
	private final InfoPane infoPane;
	private final JTextArea logPane;
	private int year;
	private final Timer timer;
	private final MapListener fleetPlanListener;
	private final OptionGamePane optGamePane;

	/**
	 * 
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		new Main().run();
	}

	/**
	 * 
	 */
	private void run() {
		frame.setSize(800, 600);
		frame.setVisible(true);
		init();
	}

	/**
	 * @throws java.awt.HeadlessException
	 */
	public Main() throws HeadlessException {
		super();
		timer = new Timer("Game clock", true); //$NON-NLS-1$
		frame = new JFrame();
		mapPane = new MapPane();
		infoPane = new InfoPane();
		logPane = new JTextArea();
		gameSpeed = BASE_GAME_SPEED;
		optGamePane = new OptionGamePane();
		exitAction = new AbstractAction() {
			private static final long serialVersionUID = 7980714909197926521L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		newGameAction = new AbstractAction() {
			private static final long serialVersionUID = 8672219956301605560L;

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}

		};
		ultraFastGameAction = new AbstractAction() {
			private static final long serialVersionUID = -430413568244313383L;

			@Override
			public void actionPerformed(ActionEvent e) {
				gameSpeed = ULTRA_FAST_GAME_SPEED;
			}

		};
		veryFastGameAction = new AbstractAction() {
			private static final long serialVersionUID = 6561779475959575874L;

			@Override
			public void actionPerformed(ActionEvent e) {
				gameSpeed = VERY_FAST_GAME_SPEED;
			}

		};
		fastGameAction = new AbstractAction() {
			private static final long serialVersionUID = -6707848262617964285L;

			@Override
			public void actionPerformed(ActionEvent e) {
				gameSpeed = FAST_GAME_SPEED;
			}

		};
		normalGameAction = new AbstractAction() {
			private static final long serialVersionUID = -378003210566472913L;

			@Override
			public void actionPerformed(ActionEvent e) {
				gameSpeed = BASE_GAME_SPEED;
			}

		};
		slowGameAction = new AbstractAction() {
			private static final long serialVersionUID = -5018455483160039544L;

			@Override
			public void actionPerformed(ActionEvent e) {
				gameSpeed = SLOW_GAME_SPEED;
			}

		};

		fleetPlanListener = new MapListener() {
			@Override
			public void fleetPlanCancelled(MapEvent ev) {
				infoPane.clear();
			}

			@Override
			public void fleetPlanChanged(MapEvent ev) {
				infoPane.showInfo(ev.getSourcePlanet(),
						ev.getDestinationPlanet(), ev.getLocation());
			}

			@Override
			public void fleetPlanConfirmed(MapEvent ev) {
				lunchFleet(ev.getSourcePlanet(), ev.getDestinationPlanet());
			}

			@Override
			public void planetEntered(MapEvent ev) {
				infoPane.showInfo(ev.getSourcePlanet());
			}

			@Override
			public void planetExited(MapEvent ev) {
				infoPane.clear();
			}
		};
	}

	/**
	 * 
	 */
	private void createActions() {
		newGameAction.putValue(Action.NAME,
				Messages.getString("Main.newGame.text")); //$NON-NLS-1$
		slowGameAction.putValue(Action.NAME,
				Messages.getString("Main.slowSpeed.text")); //$NON-NLS-1$
		normalGameAction.putValue(Action.NAME,
				Messages.getString("Main.normalSpeed.text")); //$NON-NLS-1$
		fastGameAction.putValue(Action.NAME,
				Messages.getString("Main.fastSpeed.text")); //$NON-NLS-1$
		veryFastGameAction.putValue(Action.NAME,
				Messages.getString("Main.veryFastSpeed.text")); //$NON-NLS-1$
		ultraFastGameAction.putValue(Action.NAME,
				Messages.getString("Main.ultraSpeed.text")); //$NON-NLS-1$
		exitAction.putValue(Action.NAME, Messages.getString("Main.exit.text")); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	private void createListeners() {
		mapPane.addMapListener(fleetPlanListener);
	}

	/**
	 * 
	 */
	private void createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		final JMenu fileMenu = new JMenu(Messages.getString("Main.file.text")); //$NON-NLS-1$
		final JMenuItem optionsMenu = new JMenu(
				Messages.getString("Main.options.text")); //$NON-NLS-1$
		final ButtonGroup bg = new ButtonGroup();
		final JRadioButtonMenuItem slowItem = new JRadioButtonMenuItem(
				slowGameAction);
		final JRadioButtonMenuItem normalItem = new JRadioButtonMenuItem(
				normalGameAction);
		final JRadioButtonMenuItem fastItem = new JRadioButtonMenuItem(
				fastGameAction);
		final JRadioButtonMenuItem veryFastItem = new JRadioButtonMenuItem(
				veryFastGameAction);
		final JRadioButtonMenuItem ultraFastItem = new JRadioButtonMenuItem(
				ultraFastGameAction);

		menuBar.add(fileMenu);
		menuBar.add(optionsMenu);

		fileMenu.add(newGameAction);
		fileMenu.add(new JSeparator());
		fileMenu.add(exitAction);

		optionsMenu.add(slowItem);
		optionsMenu.add(normalItem);
		optionsMenu.add(fastItem);
		optionsMenu.add(veryFastItem);
		optionsMenu.add(ultraFastItem);

		bg.add(slowItem);
		bg.add(normalItem);
		bg.add(fastItem);
		bg.add(veryFastItem);
		bg.add(ultraFastItem);

		normalItem.setSelected(true);

		frame.setJMenuBar(menuBar);
	}

	/**
	 * 
	 */
	private void createToolBar() {
		final JToolBar tb = new JToolBar(SwingConstants.VERTICAL);
		tb.setFloatable(false);
		tb.add(newGameAction);
		tb.add(exitAction);
		tb.setBorder(BorderFactory.createEtchedBorder());
		frame.getContentPane().add(tb, BorderLayout.WEST);
	}

	/**
	 * 
	 */
	private void init() {
		final Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		createActions();
		createMenuBar();
		createToolBar();

		infoPane.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("Main.info.text"))); //$NON-NLS-1$
		final JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, mapPane, infoPane);
		splitPane.setResizeWeight(1);
		splitPane.setOneTouchExpandable(true);

		final JScrollPane logScroll = new JScrollPane(logPane);
		logPane.setEditable(false);
		logPane.setRows(4);
		logScroll.setBorder(BorderFactory.createTitledBorder(Messages
				.getString("Main.log.text"))); //$NON-NLS-1$
		final JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				splitPane, logScroll);
		splitPane1.setResizeWeight(1);
		splitPane1.setOneTouchExpandable(true);
		splitPane.setDividerLocation(.75);

		cp.add(splitPane1, BorderLayout.CENTER);

		createListeners();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				tickTime();
			}
		}, 0, BASE_GAME_PERIOD);
	}

	/**
	 * 
	 * @param key
	 * @param args
	 */
	private void log(final String key, final Object... args) {
		logPane.append("\n"); //$NON-NLS-1$
		final Formatter f = new Formatter();
		logPane.append(f.format(Messages.getString(key), args).toString());
		f.close();
		int carPos = logPane.getText().lastIndexOf("\n"); //$NON-NLS-1$
		if (carPos < 0)
			carPos = 0;
		else
			++carPos;
		logPane.setCaretPosition(carPos);
	}

	/**
	 * @param sourcePlanet
	 * @param destinationPlanet
	 */
	private void lunchFleet(Planet sourcePlanet, Planet destinationPlanet) {
		int ships = 0;
		String result = JOptionPane.showInputDialog(this,
				Messages.getString("Main.shipQuestion.text")); //$NON-NLS-1$
		infoPane.clear();
		try {
			ships = Integer.parseInt(result);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, new Formatter().format(
					Messages.getString("Main.numberError.text"), result), //$NON-NLS-1$
					Messages.getString("Main.error.title"), //$NON-NLS-1$
					JOptionPane.ERROR_MESSAGE);
		}
		frame.repaint();
		if (ships == 0)
			return;
		try {
			sourcePlanet.lunchFleet(mapPane.getOwner(), destinationPlanet,
					ships);
		} catch (NoShipsException e1) {
			JOptionPane
					.showMessageDialog(
							frame,
							e1.getLocalizedMessage(),
							Messages.getString("Main.error.title"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			return;
		} catch (InvalidOwnerException e1) {
			JOptionPane
					.showMessageDialog(
							frame,
							e1.getLocalizedMessage(),
							Messages.getString("Main.error.title"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			return;
		}
		log("Main.lanchFleet.text", //$NON-NLS-1$
				sourcePlanet.getName(), destinationPlanet.getName(), ships);
	}

	private void newGame() {
		int ownerCount;
		int planetCount;
		for (;;) {
			final int option = JOptionPane
					.showConfirmDialog(
							frame,
							optGamePane,
							Messages.getString("Main.options.text"), JOptionPane.OK_CANCEL_OPTION, //$NON-NLS-1$
							JOptionPane.QUESTION_MESSAGE);
			if (option != JOptionPane.OK_OPTION)
				return;
			ownerCount = optGamePane.getOwnerCount();
			planetCount = optGamePane.getPlanetCount();
			if (ownerCount > planetCount) {
				Formatter f = new Formatter();
				final String m = f.format(
						Messages.getString("Main.exceedCount.text"),//$NON-NLS-1$
						ownerCount, planetCount).toString();
				f.close();
				JOptionPane
						.showMessageDialog(
								frame,
								m,
								Messages.getString("Main.error.title"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			} else
				break;
		}
		final int reactionIdx = optGamePane.getReaction();
		infoPane.clear();
		try {
			final UniverseBuilder builder = UniverseBuilder.getInstance();
			final Universe universe = builder.createUniverse(ownerCount - 1,
					planetCount, REACTION_TABLE[reactionIdx]);
			mapPane.setUniverse(universe);
			mapPane.setOwner(universe.getHuman());
			logPane.setText(""); //$NON-NLS-1$
			log("Main.gameStart.text"); //$NON-NLS-1$
			year = 0;
		} catch (PlanetCountOutOfBoundException e) {
			log(e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, e.getMessage(),
					Messages.getString("Main.error.title"), //$NON-NLS-1$
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 
	 */
	private void tickTime() {
		final Universe universe = mapPane.getUniverse();
		if (universe == null)
			return;
		double years = gameSpeed * BASE_GAME_PERIOD;
		universe.doTickTime(years);
		mapPane.repaint();
		int oy = year;
		int y = universe.getYear();
		if (y != oy) {
			year = y;
			log("Main.changeYear.text", y); //$NON-NLS-1$
		}
	}
}