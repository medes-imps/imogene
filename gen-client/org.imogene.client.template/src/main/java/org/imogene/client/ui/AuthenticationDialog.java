package org.imogene.client.ui;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.imogene.client.i18n.Messages;

public class AuthenticationDialog extends TitleAreaDialog {

  private Text loginText;
  private Text passwordText;

  private String login;
  private String password;

  public AuthenticationDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  public void create() {
    super.create();
    setTitle(Messages.auth_title);
    setMessage(Messages.auth_info, IMessageProvider.INFORMATION);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite area = (Composite) super.createDialogArea(parent);
    Composite container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(GridData.FILL_BOTH));
    GridLayout layout = new GridLayout(2, false);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    container.setLayout(layout);

    createFirstName(container);
    createLastName(container);

    return area;
  }

  private void createFirstName(Composite container) {
    Label lbtFirstName = new Label(container, SWT.NONE);
    lbtFirstName.setText(Messages.auth_login);

    GridData dataFirstName = new GridData();
    dataFirstName.grabExcessHorizontalSpace = true;
    dataFirstName.horizontalAlignment = GridData.FILL;

    loginText = new Text(container, SWT.BORDER);
    loginText.setLayoutData(dataFirstName);
  }
  
  private void createLastName(Composite container) {
    Label lbtLastName = new Label(container, SWT.NONE);
    lbtLastName.setText(Messages.auth_password);
    
    GridData dataLastName = new GridData();
    dataLastName.grabExcessHorizontalSpace = true;
    dataLastName.horizontalAlignment = GridData.FILL;
    passwordText = new Text(container, SWT.BORDER);
    passwordText.setLayoutData(dataLastName);
  }



  @Override
  protected boolean isResizable() {
    return true;
  }

  // save content of the Text fields because they get disposed
  // as soon as the Dialog closes
  private void saveInput() {
    login = loginText.getText();
    password = passwordText.getText();

  }

  @Override
  protected void okPressed() {
    saveInput();
    super.okPressed();
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }
} 