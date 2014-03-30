/**
 * CSCIE99 TEAM 2
 */
package cscie99.team2.lingolearn.server.datastore;


import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import cscie99.team2.lingolearn.shared.Image;

/**
 * CSCIE99 TEAM 2
 */

/**
 * @author YPolyanskyy
 *
 * This class represents Image, corresponding to the individual card.
 */
@Entity(name="ObjectifyableImage")
public class ObjectifyableImage implements Serializable {
	
	private static final long serialVersionUID = 1034098092264836974L;
	
	@Id private Long imageId;
	@Serialize private String imageUri;
	
	public ObjectifyableImage() {}
	
	public ObjectifyableImage(Image image) {
		this.imageId = image.getImageId();
		this.imageUri = image.getImageUri();
	}
	
	public Image getImage() {
		Image img = new Image();
		img.setImageId(this.imageId);
		img.setImageUri(this.imageUri);
		return img;
	}
	
}
