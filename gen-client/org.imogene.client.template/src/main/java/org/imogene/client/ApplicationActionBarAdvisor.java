package org.imogene.client;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction preferencesAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);

		preferencesAction = ActionFactory.PREFERENCES.create(window);
		register(preferencesAction);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE); //$NON-NLS-1$
		MenuManager windowMenu = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW); //$NON-NLS-1$
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP); //$NON-NLS-1$

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		// Add a group marker indicating where action set menus will appear.
		helpMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		helpMenu.add(new Separator());
		menuBar.add(helpMenu);

		// File
		fileMenu.add(exitAction);

		// Window
		windowMenu.add(preferencesAction);

		// Help
		helpMenu.add(new Separator());
		helpMenu.add(aboutAction);
	}

}
