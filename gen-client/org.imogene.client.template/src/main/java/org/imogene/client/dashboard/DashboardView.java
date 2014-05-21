package org.imogene.client.dashboard;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;
import org.imogene.client.i18n.Messages;

public class DashboardView extends ViewPart implements IHyperlinkListener {

	public static final String ID = "org.imogene.client.dashboard.DashboardView"; //$NON-NLS-1$

	private static final String LAUNCH_BROWSER = "launch.browser"; //$NON-NLS-1$
	private static final String LAUNCH_COPY = "copy.address"; //$NON-NLS-1$

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private String url;

	public DashboardView() {
		setContentDescription(Messages.dashboard_view_title);
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		url = "http://localhost:port/imogenedemo".replace("port", System.getProperty("jetty.port"));

		ScrolledForm form = toolkit.createScrolledForm(parent);
		form.setText(Messages.dashboard_form_title);
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 0;
		layout.bottomMargin = 0;
		layout.leftMargin = 0;
		layout.rightMargin = 0;
		layout.horizontalSpacing = 20;
		layout.verticalSpacing = 17;
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 1;

		form.getBody().setLayout(layout);

		Section launchSection = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		launchSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		launchSection.setText(Messages.dashboard_launch_title);
		launchSection.setExpanded(true);

		FormText launchText = toolkit.createFormText(launchSection, false);
		launchText.setText(Messages.bind(Messages.dashboard_launch_description, url), true, true);
		launchText.addHyperlinkListener(this);

		launchSection.setClient(launchText);
	}

	@Override
	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

	@Override
	public void setFocus() {
		// Nothing to do
	}

	@Override
	public void linkEntered(HyperlinkEvent e) {
		// Nothing to do
	}

	@Override
	public void linkExited(HyperlinkEvent e) {
		// Nothing to do
	}

	@Override
	public void linkActivated(HyperlinkEvent e) {
		Object href = e.getHref();
		if (LAUNCH_BROWSER.equals(href)) {
			openUrl(url);
		} else if (LAUNCH_COPY.equals(href)) {
			Clipboard clipboard = new Clipboard(Display.getCurrent());
			clipboard.setContents(new Object[] { url }, new Transfer[] { TextTransfer.getInstance() });
			clipboard.dispose();
		} else {
			openUrl((String) href);
		}
	}

	private void openUrl(String url) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI(url));
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			String windowingSystem = System.getProperty("osgi.ws"); //$NON-NLS-1$
			if ("gtk".equals(windowingSystem)) { //$NON-NLS-1$
				boolean install = MessageDialog.openQuestion(getSite().getShell(), Messages.dashboard_library_title,
						Messages.dashboard_library_message);
				if (install) {
					try {
						Runtime.getRuntime().exec("software-center libgnome2-0"); //$NON-NLS-1$
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
