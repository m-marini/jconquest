package org.mmarini.jquest.ui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
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
public class JConquestApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4741211059441451554L;

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
	private Action newGameAction;
	private Action ultraFastGameAction;
	private Action veryFastGameAction;
	private Action fastGameAction;
	private Action normalGameAction;
	private Action slowGameAction;
	private MapPane mapPane = new MapPane();
	private InfoPane infoPane = new InfoPane();
	private JTextArea logPane = new JTextArea();
	private int year;
	private Timer timer;
	private MapListener fleetPlanListener;
	private OptionGamePane optGamePane;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public JConquestApplet() throws HeadlessException {
		super();
		gameSpeed = BASE_GAME_SPEED;
		mapPane = new MapPane();
		infoPane = new InfoPane();
		logPane = new JTextArea();
		optGamePane = new OptionGamePane();
		newGameAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8672219956301605560L;

			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}

		};
		ultraFastGameAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -430413568244313383L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setGameSpeed(ULTRA_FAST_GAME_SPEED);
			}

		};
		veryFastGameAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6561779475959575874L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setGameSpeed(VERY_FAST_GAME_SPEED);
			}

		};
		fastGameAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6707848262617964285L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setGameSpeed(FAST_GAME_SPEED);
			}

		};
		normalGameAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -378003210566472913L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setGameSpeed(BASE_GAME_SPEED);
			}

		};
		slowGameAction = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5018455483160039544L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setGameSpeed(SLOW_GAME_SPEED);
			}

		};

		fleetPlanListener = new MapListener() {

			@Override
			public void fleetPlanCancelled(MapEvent ev) {
				infoPane.clearInfo();
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
				infoPane.clearInfo();
			}

		};
	}

	/**
	 * 
	 */
	protected void createActions() {
		newGameAction.putValue(Action.NAME, "New Game");
		slowGameAction.putValue(Action.NAME, "Slow");
		normalGameAction.putValue(Action.NAME, "Normal");
		fastGameAction.putValue(Action.NAME, "Fast");
		veryFastGameAction.putValue(Action.NAME, "Very Fast");
		ultraFastGameAction.putValue(Action.NAME, "Ultra Fast");
	}

	/**
	 * 
	 */
	protected void createListeners() {
		mapPane.addMapListener(fleetPlanListener);
	}

	/**
	 * 
	 */
	protected void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		JMenuItem item = new JMenuItem(newGameAction);
		menu.add(item);

		menuBar.add(menu);

		menu = new JMenu("Game options");
		ButtonGroup group = new ButtonGroup();

		item = new JRadioButtonMenuItem(slowGameAction);
		menu.add(item);
		group.add(item);
		item = new JRadioButtonMenuItem(normalGameAction);
		item.setSelected(true);
		menu.add(item);
		group.add(item);
		item = new JRadioButtonMenuItem(fastGameAction);
		menu.add(item);
		group.add(item);
		item = new JRadioButtonMenuItem(veryFastGameAction);
		menu.add(item);
		group.add(item);
		item = new JRadioButtonMenuItem(ultraFastGameAction);
		menu.add(item);
		group.add(item);

		menuBar.add(menu);

		this.setJMenuBar(menuBar);
	}

	/**
	 * 
	 */
	protected void createToolBar() {
		JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
		toolBar.setFloatable(false);
		toolBar.add(new JButton(newGameAction));
		toolBar.setBorder(BorderFactory.createEtchedBorder());
		this.add(toolBar, BorderLayout.WEST);
	}

	/**
	 * @see java.applet.Applet#destroy()
	 */
	@Override
	public void destroy() {
		Timer timer = this.getTimer();
		timer.cancel();
	}

	/**
	 * @return Returns the gameSpeed.
	 */
	public double getGameSpeed() {
		return gameSpeed;
	}

	/**
	 * @return Returns the timer.
	 */
	protected Timer getTimer() {
		return timer;
	}

	/**
	 * @return Returns the year.
	 */
	protected int getYear() {
		return year;
	}

	/**
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		super.init();
		this.createActions();
		this.setLayout(new BorderLayout());
		this.createMenuBar();
		this.createToolBar();

		infoPane.setBorder(BorderFactory.createTitledBorder("Infos"));
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				mapPane, infoPane);
		splitPane.setResizeWeight(1);
		splitPane.setOneTouchExpandable(true);

		JScrollPane logScroll = new JScrollPane(logPane);
		logPane.setEditable(false);
		logPane.setRows(4);
		logScroll.setBorder(BorderFactory.createTitledBorder("Logs"));
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				splitPane, logScroll);
		splitPane1.setResizeWeight(1);
		splitPane1.setOneTouchExpandable(true);
		this.add(splitPane1, BorderLayout.CENTER);
		splitPane.setDividerLocation(.75);
		this.createListeners();
		Timer timer = new Timer("Game clock", true);
		this.setTimer(timer);
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				tickTime();
			}
		};
		timer.schedule(timerTask, 0, BASE_GAME_PERIOD);
	}

	protected void log(String text) {
		logPane.append("\n");
		logPane.append(text);
		int carPos = logPane.getText().lastIndexOf("\n");
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
	protected void lunchFleet(Planet sourcePlanet, Planet destinationPlanet) {
		int ships = 0;
		String result = JOptionPane.showInputDialog(this, "How many ships ?");
		infoPane.clearInfo();
		try {
			ships = Integer.parseInt(result);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "\"" + result
					+ "\" is not an integer number", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		this.repaint();
		if (ships == 0)
			return;
		try {
			sourcePlanet.lunchFleet(mapPane.getOwner(), destinationPlanet,
					ships);
		} catch (NoShipsException e1) {
			JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (InvalidOwnerException e1) {
			JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		log(MessageFormat.format(
				"Lunched fleet of {2} ships form {0} to {1}",
				new Object[] { sourcePlanet.getName(),
						destinationPlanet.getName(), new Integer(ships) }));
	}

	protected void newGame() {
		int ownerCount;
		int planetCount;
		int reactionIdx;
		for (;;) {
			int option = JOptionPane.showConfirmDialog(this, optGamePane,
					"Game options", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (option != JOptionPane.OK_OPTION)
				return;
			ownerCount = optGamePane.getOwnerCount();
			planetCount = optGamePane.getPlanetCount();
			if (ownerCount > planetCount) {
				JOptionPane.showMessageDialog(this, "The owner count "
						+ ownerCount + " exceeds the planet count "
						+ planetCount, "Error", JOptionPane.ERROR_MESSAGE);
			} else
				break;
		}
		reactionIdx = optGamePane.getReaction();
		infoPane.clearInfo();
		Universe universe;
		try {
			UniverseBuilder builder = UniverseBuilder.getInstance();
			universe = builder.createUniverse(ownerCount - 1, planetCount,
					REACTION_TABLE[reactionIdx]);
			mapPane.setUniverse(universe);
			mapPane.setOwner(universe.getHuman());
			logPane.setText("");
			log("Game started");
			this.setYear(0);
		} catch (PlanetCountOutOfBoundException e) {
			log(e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * @param gameSpeed
	 *            The gameSpeed to set.
	 */
	public void setGameSpeed(double gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	/**
	 * @param timer
	 *            The timer to set.
	 */
	protected void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	protected void setYear(int year) {
		this.year = year;
	}

	/**
	 * 
	 */
	protected void tickTime() {
		if (!this.isActive())
			return;
		Universe universe = mapPane.getUniverse();
		if (universe == null)
			return;
		double years = this.getGameSpeed() * BASE_GAME_PERIOD;
		universe.doTickTime(years);
		mapPane.repaint();
		int oldYear = this.getYear();
		int year = universe.getYear();
		if (year != oldYear) {
			this.setYear(year);
			log(MessageFormat.format("{0} UTC",
					new Object[] { new Integer(year) }));
		}
	}
}