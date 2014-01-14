package org.imogene.client.bugreport;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.imogene.client.i18n.Messages;

public class BugReportHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), Messages.bugreport_send_dialog_title,
				Messages.bugreport_send_dialog_message, Messages.bugreport_send_dialog_message_hint, null) {

			/**
			 * Override this method to make the text field multilined and give it a scroll bar. But...
			 */
			@Override
			protected int getInputTextStyle() {
				return SWT.MULTI | SWT.BORDER | SWT.V_SCROLL;
			}

			/**
			 * ...it still is just one line high. This hack is not very nice, but at least it gets the job done... ;o)
			 */
			@Override
			protected Control createDialogArea(Composite parent) {
				Control res = super.createDialogArea(parent);
				((GridData) getText().getLayoutData()).heightHint = 100;
				return res;
			}

		};

		if (dialog.open() == InputDialog.OK) {
			BugReportJob job = new BugReportJob(dialog.getValue());
			job.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					int severity = event.getResult().getSeverity();
					if (severity != IStatus.OK) {
						return;
					}
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							MessageDialog.openInformation(getShell(), Messages.bugreport_send_dialog_title,
									Messages.bugreport_send_dialog_success);
						}
					});
				}
			});
			job.setUser(true);
			job.schedule();
		}
		return null;
	}

	/**
	 * Return a shell appropriate for parenting dialogs of this handler.
	 * 
	 * @return a Shell
	 */
	private Shell getShell() {
		return PlatformUI.getWorkbench().getModalDialogShellProvider().getShell();
	}

}
