package cscie99.team2.lingolearn.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This view provides an interface to upload data to the application.
 * For now this is meant to facilitate card and deck uploads.
 * 
 * @see StorageServiceImpl.java
 * @see CsvBlobstoreServlet
 * @see ImportPresenter.java
 * 
 * @author Jeff Rabe
 *
 */
public class ImportView extends Composite {
	interface Binder extends UiBinder<Widget, ImportView> { }
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField FormPanel form;
	@UiField FileUpload fileUpload;
	@UiField Button uploadButton;
	
	public ImportView(){
		initWidget(binder.createAndBindUi(this));
		
	}
	
	public Widget asWidget() {
		    return this;
	}

	public FormPanel getForm() {
		return form;
	}

	public void setForm(FormPanel form) {
		this.form = form;
	}

	public FileUpload getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public Button getUploadButton() {
		return uploadButton;
	}

	public void setUploadButton(Button uploadButton) {
		this.uploadButton = uploadButton;
	}
	
	
}
