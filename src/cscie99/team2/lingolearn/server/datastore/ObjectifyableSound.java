/**
 * CSCIE99 TEAM 2  
 */
package cscie99.team2.lingolearn.server.datastore;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import cscie99.team2.lingolearn.shared.Sound;

/**
 * @author YPolyanskyy
 * 
 * This class represents Proxy for Sound
 */
@Entity(name="ObjectifyableSound")
public class ObjectifyableSound implements Serializable {
	
	private static final long serialVersionUID = -2229915246945106702L;

	@Id private Long soundId;		
	private String soundUri;	// Location of the sound file in the datastore
	public ObjectifyableSound() {}
	
	/**
	 * This method creates Objectifyable Proxy from real object
	 * @param sound		Sound object
	 */
	public ObjectifyableSound(Sound sound) {
		this.soundId = sound.getSoundId();
		this.soundUri = sound.getSoundUri();
	}
	
	/**
	 * This method reconstructs real object from Objectifyable Proxy
	 * @return Sound object
	 */
	public Sound getSound() {
		Sound sound = new Sound();
		sound.setSoundId(this.soundId);
		sound.setSoundUri(this.soundUri);
		return sound;
	}
}
