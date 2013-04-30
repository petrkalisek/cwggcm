package cz.gcm.cwg.database.items;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class Users {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField(index = true, canBeNull = false)
	private String name;

	Users() {
		// needed by ormlite
	}

	public Users(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("name=").append(name);
		return sb.toString();
	}
}
